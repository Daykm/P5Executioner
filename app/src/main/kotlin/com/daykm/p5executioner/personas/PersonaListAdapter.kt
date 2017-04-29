package com.daykm.p5executioner.personas

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.LayoutManager
import com.airbnb.epoxy.EpoxyController
import com.daykm.p5executioner.data.DataRepo
import com.daykm.p5executioner.proto.Persona
import com.daykm.p5executioner.util.Pageable
import io.reactivex.functions.Consumer
import javax.inject.Inject

class PersonaListAdapter
@Inject constructor(val repo: DataRepo, val ctx: Context)
    : EpoxyController(), Pageable {

    lateinit var personae: List<Persona>

    override fun buildModels() {
        personae.forEach {
            PersonaListItemModel(it, ctx).id(it.name).addTo(this)
        }
    }

    override fun manager(ctx: Context): LayoutManager {
        val manager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
        manager.recycleChildrenOnDetach = true
        return manager
    }

    override fun attach() {
        repo.DATA.subscribe(Consumer {
            personae = it.personasList
            requestModelBuild()
        })
    }

    override fun adapter(): Adapter<*> {
        return adapter
    }
}

