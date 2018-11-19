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

enum class Elements {
    PHYSICAL,
    GUN,
    FIRE,
    ICE,
    ELECTRIC,
    WIND,
    PSYCHIC,
    NUCLEAR,
    BLESS,
    CURSE
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
    fun parseAffinity(value: String?): Affinity {
        return when (value) {
            "rs" -> Affinity.RESIST
            "nu" -> Affinity.NULL
            "rp" -> Affinity.REPEL
            "wk" -> Affinity.WEAK
            "ab" -> Affinity.ABSORB
            "-" -> Affinity.NONE
            else -> Affinity.NONE
        }
    }

    @TypeConverter
    @JvmStatic
    fun parseArcana(value: String): Arcana {
        return Arcana.valueOf(value.toUpperCase())
    }

    @TypeConverter
    @JvmStatic
    fun storeAffinity(value: Affinity): String {
        return TODO()
    }

    @TypeConverter
    @JvmStatic
    fun storeArcana(value: Arcana): String {
        return TODO()
    }
}

sealed class Type {
    object Passive
    class Action(
            val affinity: Affinity,
            val cost: Int
    )
}

@Entity(tableName = "personas")
data class Persona(
        @PrimaryKey val name: String,
        val arcana: Arcana,
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
        val effect: String
)