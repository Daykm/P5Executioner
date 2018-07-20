package com.daykm.p5executioner.info

import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.daykm.p5executioner.R
import com.daykm.p5executioner.databinding.InfoItemBinding
import javax.inject.Inject

class InfoAdapter
@Inject constructor() : EpoxyController() {

    init {
        requestModelBuild()
    }

    override fun buildModels() {
        SourceModel().id(1).addTo(this)
    }
}

class SourceModel : EpoxyModelWithHolder<SourceModel.Holder>() {

    override fun getDefaultLayout(): Int = R.layout.info_item

    override fun createNewHolder() = Holder()

    class Holder : EpoxyHolder() {
        var binding: InfoItemBinding? = null

        override fun bindView(itemView: View?) {
            binding = if (itemView == null) {
                null
            } else {
                InfoItemBinding.bind(itemView)
            }
        }
    }
}

