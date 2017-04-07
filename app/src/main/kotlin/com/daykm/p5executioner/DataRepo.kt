package com.daykm.p5executioner

import android.content.Context
import com.daykm.p5executioner.json.ArcanaAdapter
import com.daykm.p5executioner.json.Persona
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okio.Okio
import timber.log.Timber
import javax.inject.Inject

class DataRepo @Inject constructor(val ctx: Context) {

  var personas: List<Persona>? = null

  fun getPersonae() : List<Persona> {

    val moshi = Moshi.Builder().add(ArcanaAdapter()).build()
    val adapter: JsonAdapter<List<Persona>> =
        Moshi.Builder()
            .add(ArcanaAdapter())
            .build()
            .adapter(
                Types.newParameterizedType(List::class.java, Persona::class.java))

    if (personas != null) {
      return personas!!
    }

    personas = adapter.fromJson(
        Okio.buffer(Okio.source(ctx.assets.open("personae.json"))))

    personas?.forEach {
      Timber.i("Name: %s, Level: %d, Arcana: %s", it.name, it.level, it.arcana.jsonVal)
    }

    return personas!!
  }
}