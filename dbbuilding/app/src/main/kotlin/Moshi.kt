import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okio.BufferedSource
import okio.buffer
import okio.source
import java.nio.file.Paths

class JsonSpecialCombo(val result: String, val sources: List<String>)
class JsonCombo(val result: String, val source: Array<String>)
class JsonPersonaDetail(val arcana: String, val level: Int, val skills: Map<String, Int>, val elems: List<String>, val stats: Array<Int>)
class JsonSkillDetail(val element: String, val cost: Int?, val personas: Map<String, Int>, val talks: List<String>?, val effect: String)

fun parse() {
    val moshi = Moshi.Builder().build()
    createDlcPersonae(moshi)
    createSkills(moshi)
    createArcanaCombos(moshi)
    createPersonas(moshi)
    createRareCombos(moshi)
    createSpecialCombos(moshi)
}

fun buffer(resourcePath: String): BufferedSource {
    val loader = ClassLoader.getSystemClassLoader()
    val res = loader.getResource(resourcePath)
    val path = Paths.get(res.toURI())
    return path.source().buffer()
}

fun createDlcPersonae(moshi: Moshi): List<List<String>>? {
    val adapter = moshi.adapter<List<List<String>>>(
            Types.newParameterizedType(List::class.java,
                    Types.newParameterizedType(List::class.java, String::class.java))
    )
    val json = buffer("json/dlcPersonae.json")
    val res = adapter.fromJson(json)
    println("parsed dlc personas")
    return res
}

fun createSpecialCombos(moshi: Moshi): List<JsonSpecialCombo>? {

    val type = Types.newParameterizedType(List::class.java, JsonSpecialCombo::class.java)

    val adapter = moshi.adapter<List<JsonSpecialCombo>>(type)

    val json = buffer("json/specialCombos.json")
    val res = adapter.fromJson(json)
    println("parsed special combos")
    return res
}

fun createArcanaCombos(moshi: Moshi): List<JsonCombo>? {

    val type = Types.newParameterizedType(List::class.java, JsonCombo::class.java)
    val adapter = moshi.adapter<List<JsonCombo>>(type)
    val json = buffer("json/arcanaCombos.json")
    val res = adapter.fromJson(json)
    println("parsed arcana combos")

    return res
}

fun createSkills(moshi: Moshi): Map<String, JsonSkillDetail>? {
    val type = Types.newParameterizedType(Map::class.java, String::class.java, JsonSkillDetail::class.java)

    val adapter = moshi.adapter<Map<String, JsonSkillDetail>>(type)

    val json = buffer("json/skills.json")

    val res = adapter.fromJson(json)
    println("parsed skills")
    return res
}

fun createRareCombos(moshi: Moshi): Map<String, List<Int>>? {

    val type = Types.newParameterizedType(Map::class.java, String::class.java,
            Types.newParameterizedType(List::class.java, Int::class.javaObjectType))

    val adapter = moshi.adapter<Map<String, List<Int>>>(type)

    val json = buffer("json/rareCombos.json")

    val res = adapter.fromJson(json)
    println("parsed rare combos")
    return res
}

fun createPersonas(moshi: Moshi): Map<String, JsonPersonaDetail>? {

    val type = Types.newParameterizedType(Map::class.java, String::class.java, JsonPersonaDetail::class.java)

    val adapter = moshi.adapter<Map<String, JsonPersonaDetail>>(type)

    val json = buffer("json/personae.json")

    val res = adapter.fromJson(json) ?: return null

    for (key in res.keys) {
        val detail = res[key]

        for (affinity in detail?.elems!!) {
            when (affinity) {
                "ab" -> ""
                "-" -> ""
                "wk" -> ""
                "rs" -> ""
                "rp" -> ""
                "nu" -> ""
                else -> ""
            }
        }
    }
    println("parsed personas")
    return res
}
