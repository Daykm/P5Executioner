package com.daykm.p5executioner.fusion

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.RecycledViewPool
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyModelWithView
import com.airbnb.epoxy.TypedEpoxyController
import org.jetbrains.anko.collections.forEachWithIndex
import javax.inject.Inject

class P5FusionAdapter
@Inject constructor(
        val firstPicker: PersonaPickerAdapter,
        val secondPicker: PersonaPickerAdapter,
        val pool: RecycledViewPool)
    : TypedEpoxyController<List<PersonaPickerAdapter>>() {

    override fun buildModels(data: List<PersonaPickerAdapter>?) {
        data?.forEachWithIndex { i, adapter ->
            PickerModel(adapter, pool).id(i).addTo(this)
        }
    }
}

class PickerModel(val picker: PersonaPickerAdapter, val pool: RecycledViewPool)
    : EpoxyModelWithView<RecyclerView>() {

    override fun bind(view: RecyclerView) {
        view.adapter = picker.adapter
        picker.bind()
    }

    override fun unbind(view: RecyclerView) {
        view.adapter = null
    }

    override fun buildView(parent: ViewGroup): RecyclerView {
        val recycler = RecyclerView(parent.context)
        val manager = LinearLayoutManager(parent.context, LinearLayoutManager.HORIZONTAL, false)
        recycler.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        manager.recycleChildrenOnDetach = true
        recycler.layoutManager = manager
        recycler.recycledViewPool = pool
        return recycler
    }
}