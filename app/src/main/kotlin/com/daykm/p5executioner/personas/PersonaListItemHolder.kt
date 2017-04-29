package com.daykm.p5executioner.personas

import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.airbnb.epoxy.EpoxyHolder
import com.daykm.p5executioner.R

class PersonaListItemHolder : EpoxyHolder() {

    @BindView(R.id.persona_name)
    lateinit var name: TextView
    @BindView(R.id.persona_arcana)
    lateinit var arcana: TextView
    @BindView(R.id.persona_level)
    lateinit var level: TextView
    @BindView(R.id.persona_stats)
    lateinit var stats: TextView
    @BindView(R.id.persona_elements)
    lateinit var elems: TextView

    override fun bindView(itemView: View) {
        ButterKnife.bind(this, itemView)
    }
}