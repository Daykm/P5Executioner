package com.daykm.p5executioner.view

import android.support.v4.view.PagerAdapter
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import javax.inject.Inject

class PageableAdapter
@Inject constructor(val pageables: Set<Pageable>, val pool: RecyclerView.RecycledViewPool)
    : PagerAdapter() {

    override fun isViewFromObject(p0: View, p1: Any): Boolean = p0 === p1

    override fun instantiateItem(g: ViewGroup, pos: Int): Any = RecyclerView(g.context).apply {
        val p = pageables
        recycledViewPool = pool
        adapter = p.adapter()
        layoutManager = p.manager(g.context)
        g.addView(this)
        p.attach()
    }

    override fun getCount(): Int = pageables.size

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as ViewGroup)
    }
}