package com.daykm.p5executioner.fusion

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.RecycledViewPool
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyModelWithView
import com.airbnb.epoxy.TypedEpoxyController
import com.daykm.p5executioner.database.Persona
import javax.inject.Inject

class P5FusionAdapter
@Inject constructor(
        private val pool: RecycledViewPool
) : TypedEpoxyController<List<PersonaPickerAdapter>>() {
    private val firstPicker = PersonaPickerAdapter()
    private val secondPicker = PersonaPickerAdapter()

    init {
        setData(listOf(firstPicker, secondPicker))
    }

    fun setPickerData(data: List<Persona>) {
        firstPicker.setData(data)
        secondPicker.setData(data)
    }

    override fun buildModels(data: List<PersonaPickerAdapter>?) {
        data?.forEachIndexed { i, adapter ->
            PickerModel(adapter, pool).id(i).addTo(this)
        }
    }
}

class PickerModel(
        private val picker: PersonaPickerAdapter,
        private val pool: RecycledViewPool
) : EpoxyModelWithView<RecyclerView>() {

    override fun bind(view: RecyclerView) {
        view.adapter = picker.adapter
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
