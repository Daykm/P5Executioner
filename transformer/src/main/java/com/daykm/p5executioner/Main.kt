package com.daykm.p5executioner

import com.daykm.p5executioner.proto.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okio.Okio
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

class JsonSpecialCombo(val result: String, val sources: List<String>)
class JsonCombo(val result: String, val source: Array<String>)
class JsonPersonaDetail(val arcana: String, val level: Int, val skills: Map<String, Int>, val elems: List<String>, val stats: Array<Int>)
class JsonSkillDetail(val element: String, val cost: Int?, val personas: Map<String, Int>, val talks: List<String>?, val effect: String)

object Main {

    val OUTPUT = "../gen/src/main/assets/"
    val INPUT = "json/"

    @Throws(Exception::class)
    @JvmStatic fun main(args: Array<String>) {
        println("Working Directory = " + System.getProperty("user.dir"))

        val moshi = Moshi.Builder().build()

        var data = Data.newBuilder()
                .addAllArcanaCombos(createArcanaCombos(moshi))
                .addAllPersonas(createPersonas(moshi))
                .addAllSkills(createSkills(moshi))
                .addAllRareModifiers(createRareCombos(moshi))
                .addAllSpecialCombos(createSpecialCombos(moshi))
                .addAllDlcPersonae(createDlcPersonae(moshi))
                .build()

        Files.write(Paths.get(OUTPUT + "data.pb"), data.toByteArray())

        data = Data.parseFrom(Files.readAllBytes(Paths.get(OUTPUT + "data.pb")))

        print("finished")
    }

    @Throws(Exception::class)
    fun createDlcPersonae(moshi: Moshi): List<DLCPersona> {
        val adapter = moshi.adapter<List<List<String>>>(Types.newParameterizedType(List::class.java,
                Types.newParameterizedType(List::class.java, String::class.java)))

        val serieses = adapter.fromJson(Okio.buffer(Okio.source(File(INPUT + "dlcPersonae.json"))))

        val protoPersonae = ArrayList<DLCPersona>(serieses.size)
        for (series in serieses) {
            protoPersonae.add(DLCPersona.newBuilder().addAllPersonaeInSeries(series).build())
        }
        return protoPersonae
    }

    @Throws(Exception::class)
    fun createSpecialCombos(moshi: Moshi): List<SpecialCombo> {

        val adapter = moshi.adapter<List<JsonSpecialCombo>>(Types.newParameterizedType(List::class.java, JsonSpecialCombo::class.java))

        val combos = adapter.fromJson(Okio.buffer(Okio.source(File(INPUT + "specialCombos.json"))))

        val protoCombos = ArrayList<SpecialCombo>(combos.size)

        for (combo in combos) {
            protoCombos.add(
                    SpecialCombo.newBuilder().setResult(combo.result).addAllSources(combo.sources).build())
        }

        return protoCombos
    }

    @Throws(Exception::class)
    fun createArcanaCombos(moshi: Moshi): List<ArcanaCombo> {
        val adapter = moshi.adapter<List<JsonCombo>>(Types.newParameterizedType(List::class.java, JsonCombo::class.java))

        val json = adapter.fromJson(Okio.buffer(Okio.source(File(INPUT + "arcanaCombos.json"))))

        val protoCombos = ArrayList<ArcanaCombo>(json.size)

        for (combo in json) {
            protoCombos.add(ArcanaCombo.newBuilder()
                    .setFirst(Arcana.valueOf(combo.source[0].toUpperCase()))
                    .setSecond(Arcana.valueOf(combo.source[1].toUpperCase()))
                    .setResult(Arcana.valueOf(combo.result.toUpperCase()))
                    .build())
        }

        return protoCombos
    }

    @Throws(Exception::class)
    fun createSkills(moshi: Moshi): List<Skill> {

        val type = Types.newParameterizedType(Map::class.java, String::class.java, JsonSkillDetail::class.java)

        val adapter = moshi.adapter<Map<String, JsonSkillDetail>>(type)

        val source = Okio.source(File(INPUT + "skills.json"))
        val skills = adapter.fromJson(Okio.buffer(source))

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
        val rareCombos = adapter.fromJson(Okio.buffer(source))

        val protoModifiers = ArrayList<RareComboModifier>(rareCombos.size)

        for (key in rareCombos.keys) {
            protoModifiers.add(RareComboModifier.newBuilder()
                    .setArcana(Arcana.valueOf(key.toUpperCase()))
                    .addAllModifiers(rareCombos[key])
                    .build())
        }

        return protoModifiers
    }

    @Throws(Exception::class)
    fun createPersonas(moshi: Moshi): List<Persona> {
        val type = Types.newParameterizedType(Map::class.java, String::class.java, JsonPersonaDetail::class.java)

        val adapter = moshi.adapter<Map<String, JsonPersonaDetail>>(type)

        val source = Okio.source(File(INPUT + "personae.json"))

        val personae = adapter.fromJson(Okio.buffer(source))

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
}
