package com.daykm.p5executioner.data

import android.content.Context
import com.daykm.p5executioner.proto.Data
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class DataRepo @Inject constructor(val ctx: Context) {

    val DATA =
            Single.fromCallable { parseData() }
                    .cache()
                    .subscribeOn(Schedulers.computation())

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
