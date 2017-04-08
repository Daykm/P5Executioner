package com.daykm.p5executioner

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okio.Okio
import timber.log.Timber
import javax.inject.Inject

class DataRepo @Inject constructor(val ctx: Context) {

  var personae: List<Persona>

  var combos: List<Combo>

  val moshi =
      Moshi.Builder()
          .add(ArcanaAdapter())
          .add(SourcesAdapter())
          .build()

  init {
    val personaAdapter: JsonAdapter<List<Persona>> =
        moshi.adapter(
            Types.newParameterizedType(List::class.java, Persona::class.java))

    personae = personaAdapter.fromJson(
        Okio.buffer(Okio.source(ctx.assets.open("personae.json"))))

    personae?.forEach {
      Timber.i("Name: %s, Level: %d, Arcana: %s", it.name, it.level, it.arcana.label)
    }

    val comboAdapter: JsonAdapter<List<Combo>> = moshi.adapter(
        Types.newParameterizedType(List::class.java, Combo::class.java))

    combos = comboAdapter.fromJson(
        Okio.buffer(Okio.source(ctx.assets.open("combos.json"))))

    combos?.forEach {
      Timber.i("One: %s, Two: %s, Result: %s",
          it.source.one.label, it.source.one.label, it.result.label)
    }
  }
}