package com.daykm.p5executioner

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okio.Okio
import timber.log.Timber
import javax.inject.Inject

class DataRepo @Inject constructor(ctx: Context) {

  private val moshi: Moshi =
      Moshi.Builder()
          .add(ArcanaAdapter())
          .add(SourcesAdapter())
          .build()

  private val personaAdapter = Single.fromCallable<JsonAdapter<List<Persona>>> {
    moshi.adapter(Types.newParameterizedType(List::class.java, Persona::class.java))
  }.cache().subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())

  private val comboAdapter = Single.fromCallable<JsonAdapter<List<Combo>>> {
    moshi.adapter(Types.newParameterizedType(List::class.java, Combo::class.java))
  }.cache().subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())

  var PERSONAE = personaAdapter.map {
    it.fromJson(
        Okio.buffer(Okio.source(ctx.assets.open("personae.json"))))
  }.map {
    it.apply {
      it.forEach {
        Timber.d("Name: %s, Level: %d, Arcana: %s", it.name, it.level, it.arcana.label)
      }
    }
  }.cache()

  var COMBOS = comboAdapter.map {
    it.fromJson(
        Okio.buffer(Okio.source(ctx.assets.open("combos.json"))))
  }.map {
    it.apply {
      it.forEach {
        Timber.d("One: %s, Two: %s, Result: %s",
            it.source.one.label, it.source.one.label, it.result.label)
      }
    }
  }
}