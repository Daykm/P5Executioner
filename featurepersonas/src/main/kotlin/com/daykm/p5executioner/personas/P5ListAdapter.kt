package com.daykm.p5executioner.personas

import android.support.v7.app.AppCompatActivity
import com.airbnb.epoxy.TypedEpoxyController
import com.daykm.p5executioner.database.Dao
import com.daykm.p5executioner.database.Persona
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class P5ListAdapter
@Inject constructor(
        private val dao: Dao,
        private val activity: AppCompatActivity
) : TypedEpoxyController<List<Persona>>() {

    init {

        Single.fromCallable {
            dao.personas()
        }.subscribeOn(Schedulers.computation())
                .subscribeBy {
                    setData(it)
                }
    }

    override fun buildModels(data: List<Persona>?) {
        Timber.i("Building %d models", data?.size ?: 0)
        data?.forEach {
            PersonaListItemModel(it, activity).id(it.name).addTo(this)
        }
    }

}

