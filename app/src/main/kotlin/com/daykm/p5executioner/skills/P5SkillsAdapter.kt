package com.daykm.p5executioner.skills

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.LayoutManager
import com.airbnb.epoxy.TypedEpoxyController
import com.daykm.p5executioner.data.DataRepo
import com.daykm.p5executioner.proto.Skill
import com.daykm.p5executioner.view.Pageable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import timber.log.Timber
import javax.inject.Inject


class P5SkillsAdapter
@Inject constructor(val repo: DataRepo, val ctx: Context)
    : TypedEpoxyController<List<Skill>>(), Pageable {

    override fun buildModels(skills: List<Skill>?) {
        Timber.i("Building %d models", skills?.size ?: 0)
        skills?.forEach {
            SkillItemModel(it, ctx).id(it.name).addTo(this)
        }
    }

    override fun manager(ctx: Context): LayoutManager =
            LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
                    .apply { recycleChildrenOnDetach = true }

    override fun attach() {
        repo.DATA.observeOn(AndroidSchedulers.mainThread()).subscribe(Consumer {
            setData(it.skillsList)
        })
    }

    override fun adapter(): Adapter<*> = adapter
}
