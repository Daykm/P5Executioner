package com.daykm.p5executioner.personas

import android.app.Activity
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.LayoutManager
import com.airbnb.epoxy.TypedEpoxyController
import com.daykm.p5executioner.data.DataRepo
import com.daykm.p5executioner.proto.Persona
import com.daykm.p5executioner.view.Pageable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

class P5ListAdapter
@Inject constructor(val repo: DataRepo, val activity: Activity)
    : TypedEpoxyController<List<Persona>>(), Pageable {

    override fun buildModels(data: List<Persona>?) {
        Timber.i("Building %d models", data?.size ?: 0)
        data?.forEach {
            PersonaListItemModel(it, activity).id(it.name).addTo(this)
        }
    }

    override fun manager(ctx: Context): LayoutManager =
            LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
                    .apply { recycleChildrenOnDetach = true }

    override fun attach() {
        repo.DATA.observeOn(AndroidSchedulers.mainThread()).subscribeBy { setData(it.personasList) }
    }

    override fun adapter(): Adapter<*> = adapter
}

