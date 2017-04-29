package com.daykm.p5executioner.skills

import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.airbnb.epoxy.EpoxyHolder
import com.daykm.p5executioner.R

class SkillItemHolder : EpoxyHolder() {
    @BindView(R.id.skill_name)
    lateinit var name: TextView
    @BindView(R.id.skill_cost)
    lateinit var cost: TextView
    @BindView(R.id.skill_description)
    lateinit var effect: TextView
    @BindView(R.id.skill_element)
    lateinit var element: TextView

    override fun bindView(itemView: View) {
        ButterKnife.bind(this, itemView)
    }
}
