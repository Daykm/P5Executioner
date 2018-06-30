package com.daykm.p5executioner

import com.daykm.p5executioner.proto.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okio.Okio
import java.io.File
import java.nio.file.Paths
import java.util.*

class JsonSpecialCombo(val result: String, val sources: List<String>)
class JsonCombo(val result: String, val source: Array<String>)
class JsonPersonaDetail(val arcana: String, val level: Int, val skills: Map<String, Int>, val elems: List<String>, val stats: Array<Int>)
class JsonSkillDetail(val element: String, val cost: Int?, val personas: Map<String, Int>, val talks: List<String>?, val effect: String)


val INPUT = "json/"

@Throws(Exception::class)
fun main(args: Array<String>) {
    println("Working Directory = " + System.getProperty("user.dir"))

    val data = with(Moshi.Builder().build()) {
        Data.newBuilder()
                .addAllArcanaCombos(createArcanaCombos(this))
                .addAllPersonas(createPersonas(this))
                .addAllSkills(createSkills(this))
                .addAllRareModifiers(createRareCombos(this))
                .addAllSpecialCombos(createSpecialCombos(this))
                .addAllDlcPersonae(createDlcPersonae(this))
                .build()
    }

    val bytes = data.toByteArray()

    println("Data is ${bytes.size} big")

    createOutputFile().outputStream().use { it.write(bytes) }
}

private fun createOutputFile(): File {

    val directoryPath = Paths.get("../gen/src/main/assets/")

    val directory = directoryPath.toFile().mkdirs()

    return Paths.get("../gen/src/main/assets/data.pb").toFile()
}

@Throws(Exception::class)
fun createDlcPersonae(moshi: Moshi): List<DLCPersona> = with(moshi) {
    adapter<List<List<String>>>(
            Types.newParameterizedType(List::class.java,
                    Types.newParameterizedType(List::class.java, String::class.java))
    ).fromJson(Okio.buffer(Okio.source(File(INPUT + "dlcPersonae.json"))))!!
            .mapTo(ArrayList<DLCPersona>()) {
                DLCPersona.newBuilder().addAllPersonaeInSeries(it).build()
            }
}

@Throws(Exception::class)
fun createSpecialCombos(moshi: Moshi): List<SpecialCombo> {
    val adapter = moshi.adapterForListOf(JsonSpecialCombo::class.java)

    val combos = adapter.fromJson(Okio.buffer(Okio.source(File(INPUT + "specialCombos.json"))))

    val protoCombos = ArrayList<SpecialCombo>(combos!!.size)

    combos.mapTo(protoCombos) { SpecialCombo.newBuilder().setResult(it.result).addAllSources(it.sources).build() }
    println("Parsed ${protoCombos.size} special combos")
    return protoCombos
}

@Throws(Exception::class)
fun createArcanaCombos(moshi: Moshi): List<ArcanaCombo> {
    val adapter = moshi.adapter<List<JsonCombo>>(Types.newParameterizedType(List::class.java, JsonCombo::class.java))
    val json = adapter.fromJson(Okio.buffer(Okio.source(File(INPUT + "arcanaCombos.json"))))!!
    return json.map {
        ArcanaCombo.newBuilder()
                .setFirst(Arcana.valueOf(it.source[0].toUpperCase()))
                .setSecond(Arcana.valueOf(it.source[1].toUpperCase()))
                .setResult(Arcana.valueOf(it.result.toUpperCase()))
                .build()
    }
}

@Throws(Exception::class)
fun createSkills(moshi: Moshi): List<Skill> {

    val type = Types.newParameterizedType(Map::class.java, String::class.java, JsonSkillDetail::class.java)

    val adapter = moshi.adapter<Map<String, JsonSkillDetail>>(type)

    val source = Okio.source(File(INPUT + "skills.json"))
    val skills = adapter.fromJson(Okio.buffer(source))!!

    val protoSkills = ArrayList<Skill>(skills.size)

    for (key in skills.keys) {
        val detail = skills[key]

        val builder = Skill.PersonaAndLevel.newBuilder()
        for (persona in detail!!.personas.keys) {
            builder.setPersona(persona).level = detail.personas[persona]!!
        }
        protoSkills.add(Skill.newBuilder()
                .addAllTalks(if (detail.talks != null) detail.talks else ArrayList<String>(0))
                .setCost(if (detail.cost != null) detail.cost else 0)
                .setName(key)
                .setEffect(detail.effect)
                .setElement(Skill.Element.valueOf(detail.element.toUpperCase()))
                .addPersonaeWhoLearn(builder)
                .build())
    }
    return protoSkills
}

@Throws(Exception::class)
fun createRareCombos(moshi: Moshi): List<RareComboModifier> {

    val type = Types.newParameterizedType(Map::class.java, String::class.java,
            Types.newParameterizedType(List::class.java, Int::class.javaObjectType))

    val adapter = moshi.adapter<Map<String, List<Int>>>(type)

    val source = Okio.source(File(INPUT + "rareCombos.json"))
    val rareCombos = adapter.fromJson(Okio.buffer(source))!!

    val protoModifiers = ArrayList<RareComboModifier>(rareCombos.size)

    rareCombos.keys.mapTo(protoModifiers) {
        RareComboModifier.newBuilder()
                .setArcana(Arcana.valueOf(it.toUpperCase()))
                .addAllModifiers(rareCombos[it])
                .build()
    }

    return protoModifiers
}

@Throws(Exception::class)
fun createPersonas(moshi: Moshi): List<Persona> {
    val adapter = moshi.adapterForMapOf(String::class.java, JsonPersonaDetail::class.java)

    val source = Okio.source(File(INPUT + "personae.json"))

    val personae = adapter.fromJson(Okio.buffer(source))!!

    val protoPersonas = ArrayList<Persona>(personae.size)

    for (key in personae.keys) {
        val detail = personae[key]
        val builder = Persona.newBuilder()

        val options = ArrayList<Persona.Affinities.AffinityOption>()

        for (affinity in detail?.elems!!) {
            when (affinity) {
                "ab" -> options.add(Persona.Affinities.AffinityOption.ABSORB)
                "-" -> options.add(Persona.Affinities.AffinityOption.NONE)
                "wk" -> options.add(Persona.Affinities.AffinityOption.WEAK)
                "rs" -> options.add(Persona.Affinities.AffinityOption.RESIST)
                "rp" -> options.add(Persona.Affinities.AffinityOption.REPEL)
                "nu" -> options.add(Persona.Affinities.AffinityOption.NULL)
                else -> options.add(Persona.Affinities.AffinityOption.UNRECOGNIZED)
            }
        }

        builder.setAffinities(Persona.Affinities.newBuilder()
                .setPhysical(options[0])
                .setGun(options[1])
                .setFire(options[2])
                .setIce(options[3])
                .setElectric(options[4])
                .setWind(options[5])
                .setPsychic(options[6])
                .setNuclear(options[7])
                .setBless(options[8])
                .setCurse(options[9]))

        builder.setStats(Persona.Stats.newBuilder()
                .setStrength(detail.stats[0])
                .setMagic(detail.stats[1])
                .setEndurance(detail.stats[2])
                .setAgility(detail.stats[3])
                .setLuck(detail.stats[4]))

        for (skill in detail.skills.keys) {
            builder.addSkills(
                    detail.skills[skill]?.let { Persona.LearnedSkills.newBuilder().setName(skill).setLevel(it) })
        }

        protoPersonas.add(builder.setName(key)
                .setLevel(detail.level)
                .setArcana(Arcana.valueOf(detail.arcana.toUpperCase()))
                .build())
    }
    return protoPersonas
}

fun <T> Moshi.adapterForListOf(clazz: Class<T>): JsonAdapter<List<T>> =
        adapter<List<T>>(Types.newParameterizedType(List::class.java, clazz))

fun <K, V> Moshi.adapterForMapOf(keyClass: Class<K>, valueClass: Class<V>): JsonAdapter<Map<K, V>> {
    val type = Types.newParameterizedType(Map::class.java, keyClass, valueClass)
    return adapter<Map<K, V>>(type)
}

