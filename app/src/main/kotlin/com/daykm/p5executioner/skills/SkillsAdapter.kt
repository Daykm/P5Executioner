package com.daykm.p5executioner.skills

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.LayoutManager
import com.airbnb.epoxy.EpoxyController
import com.daykm.p5executioner.DataRepo
import com.daykm.p5executioner.Pageable
import com.daykm.p5executioner.proto.Skill
import io.reactivex.functions.Consumer
import javax.inject.Inject


class SkillsAdapter @Inject constructor(val repo: DataRepo,
                                        val ctx: Context) : EpoxyController(), Pageable {

    lateinit var skills: List<Skill>

    override fun buildModels() {
        skills.forEach {
            SkillItemModel(it, ctx).id(it.name).addTo(this)
        }
    }

    override fun manager(ctx: Context): LayoutManager {
        val manager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
        manager.recycleChildrenOnDetach = true
        return manager
    }

    override fun attach() {
        repo.DATA.subscribe(Consumer {
            skills = it.skillsList
            requestModelBuild()
        })
    }

    override fun adapter(): Adapter<*> {
        return adapter
    }
}
