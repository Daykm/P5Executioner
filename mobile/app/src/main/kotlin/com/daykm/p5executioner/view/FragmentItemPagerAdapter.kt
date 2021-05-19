package com.daykm.p5executioner.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

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
