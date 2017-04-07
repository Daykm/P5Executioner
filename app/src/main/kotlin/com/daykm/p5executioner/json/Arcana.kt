package com.daykm.p5executioner.json

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

enum class Arcana(val jsonVal : String) {
  FOOL("fool"),
  MAGICIAN("Magician"),
  PRIESTESS("Priestess"),
  EMPRESS("Empress"),
  EMPEROR("Emperor"),
  HIEROPHANT("Hierophant"),
  LOVERS("Lovers"),
  CHARIOT("Chariot"),
  JUSTICE("Justice"),
  HERMIT("Hermit"),
  FORTUNE("Fortune"),
  STRENGTH("Strength"),
  HANGED_MAN("Hanged Man"),
  DEATH("Death"),
  TEMPERANCE("Temperance"),
  DEVIL("Devil"),
  TOWER("Tower"),
  STAR("Star"),
  MOON("Moon"),
  SUN("Sun"),
  JUDGEMENT("Judgement")
}

class ArcanaAdapter {
  @ToJson fun toJson(arcana : Arcana) : String{
    return arcana.jsonVal;
  }
  @FromJson fun fromJson(string : String) : Arcana {
    when(string) {
      Arcana.FOOL.jsonVal ->  return Arcana.FOOL
      Arcana.MAGICIAN.jsonVal -> return Arcana.MAGICIAN
      else -> return Arcana.FOOL
    }
  }
}