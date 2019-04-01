package com.daykm.p5executioner.personas

import androidx.appcompat.app.AppCompatActivity
import com.airbnb.epoxy.TypedEpoxyController
import com.daykm.p5executioner.database.Persona
import timber.log.Timber
import javax.inject.Inject

class PersonaEpoxyController
@Inject constructor(
        private val activity: AppCompatActivity
) : TypedEpoxyController<List<Persona>>() {

    override fun buildModels(data: List<Persona>?) {
        Timber.i("Building %d models", data?.size ?: 0)
        data?.forEach {
            PersonaListItemModel(it, activity).id(it.name).addTo(this)
        }
    }

}
