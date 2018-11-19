import com.squareup.moshi.Moshi
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection

fun main(args: Array<String>) {
    println("Hello")

    val db = Database.connect("jdbc:sqlite:C:\\Dev\\P5Executioner\\dbbuilding\\db.db", driver = "org.sqlite.JDBC")

    TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
    doStuff(db)
}

fun doStuff(db: Database) {
    val moshi = Moshi.Builder().build()

    val combos = createArcanaCombos(moshi)
    val personas = createPersonas(moshi)
    val skills = createSkills(moshi)
    transaction {
        Combos.deleteAll()
        combos?.forEach { json ->
            Combos.insert {
                it[first] = json.source.get(0)
                it[second] = json.source.get(1)
                it[result] = json.result
            }
        }
        Skills.deleteAll()
        skills?.entries?.forEach { (jsonName, deets) ->
            Skills.insert {
                it[name] = jsonName
                it[effect] = deets.effect
                it[element] = deets.element
                it[cost] = deets.cost
            }
        }
        Personas.deleteAll()
        personas?.forEach { (jsonName, deets) ->
            Personas.insert {
                it[name] = jsonName
                it[level] = deets.level
                it[arcana] = deets.arcana

                it[strength] = deets.stats[0]
                it[magic] = deets.stats[1]
                it[endurance] = deets.stats[2]
                it[agility] = deets.stats[3]
                it[luck] = deets.stats[4]

                it[physical] = deets.elems[0]
                it[gun] = deets.elems[1]
                it[fire] = deets.elems[2]
                it[ice] = deets.elems[3]
                it[electric] = deets.elems[4]
                it[wind] = deets.elems[5]
                it[psychic] = deets.elems[6]
                it[nuclear] = deets.elems[7]
                it[bless] = deets.elems[8]
                it[curse] = deets.elems[9]
            }
        }
    }
}