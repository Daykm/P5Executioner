package com.daykm.p5executioner.data

import android.content.Context
import com.daykm.p5executioner.proto.Data
import com.daykm.p5executioner.proto.Persona
import com.daykm.p5executioner.proto.Skill
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class DataRepo @Inject constructor(val ctx: Context) {

    val DATA = Single.fromCallable { parseData() }
            .subscribeOn(Schedulers.computation())
            .cache()

    init {
        DATA.subscribe()
    }


    private fun parseData(): Data {
        val start = System.nanoTime()
        val data = Data.parseFrom(ctx.assets.open("data.pb"))
        val end = System.nanoTime() - start
        Timber.i("Parsing took %d nanoseconds", end)
        return data
    }
}


fun Data.skillsForPersona(persona: Persona): List<Skill> = with(persona.skillsList.map { it.name }, {
    skillsList.filter {
        this.contains(it.name)
    }
})
