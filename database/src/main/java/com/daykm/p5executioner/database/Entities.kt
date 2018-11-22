package com.daykm.p5executioner.database

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverter

enum class Arcana {
    STRENGTH,
    JUDGEMENT,
    CHARIOT,
    DEATH,
    LOVERS,
    STAR,
    DEVIL,
    JUSTICE,
    HIEROPHANT,
    PRIESTESS,
    HERMIT,
    FORTUNE,
    TEMPERANCE,
    FOOL,
    TOWER,
    SUN,
    MAGICIAN,
    HANGEDMAN,
    MOON,
    EMPEROR,
    EMPRESS
}

enum class Element {
    PHYS,
    GUN,
    FIRE,
    ICE,
    ELECTRIC,
    WIND,
    PSY,
    NUCLEAR,
    BLESS,
    CURSE,
    HEALING,
    AILMENT,
    ALMIGHTY,
    SUPPORT,
    PASSIVE
}

enum class Affinity {
    ABSORB,
    REPEL,
    RESIST,
    WEAK,
    NULL,
    NONE
}

object Converters {

    @TypeConverter
    @JvmStatic
    fun parseAffinity(value: String?): Affinity = when (value) {
        "rs" -> Affinity.RESIST
        "nu" -> Affinity.NULL
        "rp" -> Affinity.REPEL
        "wk" -> Affinity.WEAK
        "ab" -> Affinity.ABSORB
        "-" -> Affinity.NONE
        else -> Affinity.NONE
    }

    @TypeConverter
    @JvmStatic
    fun parseArcana(value: String): Arcana = Arcana.valueOf(value.toUpperCase())

    @TypeConverter
    @JvmStatic
    fun parseElement(value: String): Element = Element.valueOf(value.toUpperCase())

    @TypeConverter
    @JvmStatic
    fun storeAffinity(value: Affinity): String = TODO("This database is read only")

    @TypeConverter
    @JvmStatic
    fun storeArcana(value: Arcana): String = TODO("This database is read only")
    @TypeConverter
    @JvmStatic
    fun storeElement(value: Element): String = TODO("This database is read only")

}

@Entity(tableName = "personas")
data class Persona(
        @PrimaryKey val name: String,
        val arcana: Arcana,
        val level: Int,
        @Embedded val stats: Stats,
        @Embedded val affinities: Affinities
) {
    data class Stats(
            val strength: Int,
            val magic: Int,
            val endurance: Int,
            val agility: Int,
            val luck: Int
    )

    data class Affinities(
            val physical: Affinity,
            val gun: Affinity,
            val fire: Affinity,
            val ice: Affinity,
            val electric: Affinity,
            val wind: Affinity,
            val psychic: Affinity,
            val nuclear: Affinity,
            val bless: Affinity,
            val curse: Affinity
    )
}

@Entity(tableName = "skills")
data class Skill(
        @PrimaryKey val name: String,
        val effect: String,
        val element: Element,
        val cost: Int?
) {

    sealed class Cost(
            val element: Element
    ) {
        class HP(element: Element, val cost: Int) : Cost(element)
        class SP(element: Element, val cost: Int) : Cost(element)
        object Passive : Cost(Element.PASSIVE)
    }

    fun adjustCost(): Cost = when {
        element == Element.PASSIVE -> Cost.Passive
        cost == null -> Cost.Passive
        cost > 100 -> Cost.SP(element, cost)
        cost < 100 -> Cost.HP(element, cost)
        else -> throw IllegalStateException("$this is an invalid skill")
    }

}

@Entity(tableName = "combos", primaryKeys = ["first", "second", "result"])
data class Combo(
        val first: Arcana,
        val second: Arcana,
        val result: Arcana
)
