package com.daykm.p5executioner.view

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

typealias FragmentAdapterItem = () -> Fragment

fun fragmentAdapterItem(block: () -> Fragment): FragmentAdapterItem = block

class FragmentItemPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    var items: List<FragmentAdapterItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItem(position: Int): Fragment = items[position].invoke()

    override fun getCount(): Int = items.size

}
