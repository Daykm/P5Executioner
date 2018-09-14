package com.daykm.p5executioner.personas

import android.support.v7.app.AppCompatActivity
import com.airbnb.epoxy.TypedEpoxyController
import com.daykm.p5executioner.data.DataRepo
import com.daykm.p5executioner.proto.Persona
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

class P5ListAdapter
@Inject constructor(
        repo: DataRepo,
        private val activity: AppCompatActivity
) : TypedEpoxyController<List<Persona>>() {

    init {
        repo.DATA.observeOn(AndroidSchedulers.mainThread()).subscribeBy { setData(it.personasList) }
    }

    override fun buildModels(data: List<Persona>?) {
        Timber.i("Building %d models", data?.size ?: 0)
        data?.forEach {
            PersonaListItemModel(it, activity).id(it.name).addTo(this)
        }
    }

}

