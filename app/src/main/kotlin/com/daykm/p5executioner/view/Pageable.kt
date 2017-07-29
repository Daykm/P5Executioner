package com.daykm.p5executioner.view

import android.content.Context
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.LayoutManager

interface Pageable {
    fun manager(ctx: Context): LayoutManager
    fun attach()
    fun adapter(): Adapter<*>
}