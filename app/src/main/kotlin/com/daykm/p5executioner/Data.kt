package com.daykm.p5executioner

import android.support.annotation.Keep
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

enum class Arcana(val label: String) {
	FOOL("Fool"), MAGICIAN("Magician"), PRIESTESS("Priestess"), EMPRESS("Empress"), EMPEROR(
			"Emperor"),
	HIEROPHANT("Hierophant"), LOVERS("Lovers"), CHARIOT("Chariot"), JUSTICE("Justice"), HERMIT(
			"Hermit"),
	FORTUNE("Fortune"), STRENGTH("Strength"), HANGED_MAN("Hanged Man"), DEATH("Death"), TEMPERANCE(
			"Temperance"),
	DEVIL("Devil"), TOWER("Tower"), STAR("Star"), MOON("Moon"), SUN("Sun"), JUDGEMENT("Judgement")
}

class ArcanaAdapter {

	companion object {
		val INSTANCE = ArcanaAdapter()
	}

	@ToJson @Keep fun toJson(arcana: Arcana): String {
		throw RuntimeException("Don't do this")
	}

	@FromJson @Keep fun fromJson(string: String): Arcana {
		when (string) {
			Arcana.FOOL.label -> return Arcana.FOOL
			Arcana.MAGICIAN.label -> return Arcana.MAGICIAN
			Arcana.PRIESTESS.label -> return Arcana.PRIESTESS
			Arcana.EMPRESS.label -> return Arcana.EMPRESS
			Arcana.EMPEROR.label -> return Arcana.EMPEROR
			Arcana.HIEROPHANT.label -> return Arcana.HIEROPHANT
			Arcana.LOVERS.label -> return Arcana.LOVERS
			Arcana.CHARIOT.label -> return Arcana.CHARIOT
			Arcana.JUSTICE.label -> return Arcana.JUSTICE
			Arcana.HERMIT.label -> return Arcana.HERMIT
			Arcana.FORTUNE.label -> return Arcana.FORTUNE
			Arcana.STRENGTH.label -> return Arcana.STRENGTH
			Arcana.HANGED_MAN.label -> return Arcana.HANGED_MAN
			Arcana.DEATH.label -> return Arcana.DEATH
			Arcana.TEMPERANCE.label -> return Arcana.TEMPERANCE
			Arcana.DEVIL.label -> return Arcana.DEVIL
			Arcana.TOWER.label -> return Arcana.TOWER
			Arcana.STAR.label -> return Arcana.STAR
			Arcana.MOON.label -> return Arcana.MOON
			Arcana.SUN.label -> return Arcana.SUN
			Arcana.JUDGEMENT.label -> return Arcana.JUDGEMENT
			else -> throw IllegalArgumentException("Not an arcana")
		}
	}
}

data class Persona(@Keep val arcana: Arcana, @Keep val level: Int, @Keep val name: String)

data class Combo(@Keep val source: Sources, @Keep val result: Arcana)

data class Sources(@Keep val one: Arcana, @Keep val two: Arcana)

class SourcesAdapter {

	@ToJson @Keep fun toJson(pair: Sources): Array<Arcana> {
		return arrayOf(pair.one, pair.two)
	}

	@FromJson @Keep fun fromJson(arcana: Array<Arcana>): Sources {
		return Sources(arcana[0], arcana[1])
	}
}