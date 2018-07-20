package com.daykm.p5executioner.skills

import android.content.Context
import com.airbnb.epoxy.TypedEpoxyController
import com.daykm.p5executioner.data.DataRepo
import com.daykm.p5executioner.proto.Skill
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import timber.log.Timber
import javax.inject.Inject

class P5SkillsAdapter
@Inject constructor(
        private val repo: DataRepo,
        private val ctx: Context
) : TypedEpoxyController<List<Skill>>() {

    override fun buildModels(skills: List<Skill>?) {
        Timber.i("Building %d models", skills?.size ?: 0)
        skills?.forEach {
            SkillItemModel(it, ctx).id(it.name).addTo(this)
        }
    }

    init {
        repo.DATA.observeOn(AndroidSchedulers.mainThread()).subscribe(Consumer {
            setData(it.skillsList)
        })
    }

}
