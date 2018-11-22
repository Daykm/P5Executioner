package com.daykm.p5executioner.skills

import android.support.v7.app.AppCompatActivity
import com.airbnb.epoxy.TypedEpoxyController
import com.daykm.p5executioner.database.Skill
import timber.log.Timber
import javax.inject.Inject

class P5SkillsAdapter
@Inject constructor(
        private val ctx: AppCompatActivity
) : TypedEpoxyController<List<Skill>>() {

    override fun buildModels(skills: List<Skill>?) {
        Timber.i("Building %d models", skills?.size ?: 0)
        skills?.forEach {
            SkillItemModel(it, ctx).id(it.name).addTo(this)
        }
    }
}
