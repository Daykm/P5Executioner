package com.daykm.p5executioner.info

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyModelWithView
import com.daykm.p5executioner.R
import com.daykm.p5executioner.util.Pageable
import javax.inject.Inject


class InfoAdapter
@Inject constructor() : EpoxyController(), Pageable {
    override fun manager(ctx: Context): RecyclerView.LayoutManager {
        val manager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
        manager.recycleChildrenOnDetach = true
        return manager
    }

    override fun attach() {
        requestModelBuild()
    }

    override fun adapter(): RecyclerView.Adapter<*> {
        return adapter
    }

    override fun buildModels() {
        SourceModel().id(1).addTo(this)
    }
}

class SourceModel : EpoxyModelWithView<TextView>() {

    override fun buildView(parent: ViewGroup): TextView {
        return LayoutInflater.from(parent.context)
                .inflate(R.layout.source_item, parent, false) as TextView
    }

}