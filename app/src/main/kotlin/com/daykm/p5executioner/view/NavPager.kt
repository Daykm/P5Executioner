package com.daykm.p5executioner.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewManager
import org.jetbrains.anko.custom.ankoView

class NavPager : ViewPager {
    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)

    override fun onTouchEvent(event: MotionEvent): Boolean = false
    override fun onInterceptTouchEvent(event: MotionEvent): Boolean = false
}

inline fun ViewManager.navPager(theme: Int = 0) = navPager(theme) {}

inline fun ViewManager.navPager(theme: Int = 0, init: NavPager.() -> Unit): NavPager {
    return ankoView({ NavPager(it) }, theme, init)
}