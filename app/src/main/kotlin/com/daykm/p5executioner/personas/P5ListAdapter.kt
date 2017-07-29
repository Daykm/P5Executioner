package com.daykm.p5executioner.personas

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.LayoutManager
import com.airbnb.epoxy.EpoxyController
import com.daykm.p5executioner.data.DataRepo
import com.daykm.p5executioner.proto.Persona
import com.daykm.p5executioner.view.Pageable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import javax.inject.Inject
import kotlin.properties.Delegates

class P5ListAdapter @Inject constructor(val repo: DataRepo, val ctx: Context) : EpoxyController(), Pageable {

    var personae: List<Persona> by Delegates.notNull()

    override fun buildModels() = personae.forEach {
        PersonaListItemModel(it, ctx).id(it.name).addTo(this)
    }

    override fun manager(ctx: Context): LayoutManager =
            LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
                    .apply { recycleChildrenOnDetach = true }

    override fun attach() {
        repo.DATA.observeOn(AndroidSchedulers.mainThread()).subscribe(Consumer {
            personae = it.personasList
            requestModelBuild()
        })
    }


    override fun adapter(): Adapter<*> = adapter
}

