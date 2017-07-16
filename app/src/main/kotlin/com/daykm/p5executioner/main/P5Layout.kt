package com.daykm.p5executioner.main

import android.support.design.widget.BottomNavigationView
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewPager
import android.view.Gravity
import com.daykm.p5executioner.R
import com.daykm.p5executioner.util.color
import com.daykm.p5executioner.view.BottomBarBehavior
import com.daykm.p5executioner.view.navPager
import com.daykm.p5executioner.view.transformer
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.design.bottomNavigationView
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.matchParent

class P5Layout : AnkoComponent<P5Activity> {
    lateinit var layout: CoordinatorLayout
    lateinit var pager: ViewPager
    lateinit var nav: BottomNavigationView

    override fun createView(ui: AnkoContext<P5Activity>) = with(ui) {
        coordinatorLayout {
            navPager {
                isScrollContainer = true
                setPageTransformer(true, transformer)
            }.lparams {
                height = matchParent
                width = matchParent
            }.let { pager = it }
            bottomNavigationView {
                itemBackgroundResource = R.color.colorPrimary
                itemIconTintList = ctx.color(R.color.white)
                itemTextColor = ctx.color(R.color.white)
                inflateMenu(R.menu.menu_nav)
            }.lparams {
                behavior = BottomBarBehavior()
                gravity = Gravity.BOTTOM
            }.let { nav = it }
        }.apply { layout = this }
    }
}