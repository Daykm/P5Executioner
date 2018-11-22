import org.jetbrains.exposed.sql.Table

object Combos : Table("combos") {
    val first = text("first")
    val second = text("second")
    val result = text("result")
}

object Personas : Table("personas") {
    val name = text("name")
    val level = integer("level")
    val arcana = text("arcana")

    val strength = integer("strength")
    val magic = integer("magic")
    val endurance = integer("endurance")
    val agility = integer("agility")
    val luck = integer("luck")

    val physical = text("physical")
    val gun = text("gun")
    val fire = text("fire")
    val ice = text("ice")
    val electric = text("electric")
    val wind = text("wind")
    val psychic = text("psychic")
    val nuclear = text("nuclear")
    val bless = text("bless")
    val curse = text("curse")
}

object Skills : Table("skills") {
    val name = text("name")
    val effect = text("effect")
    val element = text("element")
    val cost = integer("cost").nullable()
}