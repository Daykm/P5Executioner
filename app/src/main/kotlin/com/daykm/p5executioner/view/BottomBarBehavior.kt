package com.daykm.p5executioner.view

import android.support.design.widget.BottomNavigationView
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.view.View
import android.widget.FrameLayout

class BottomBarBehavior : CoordinatorLayout.Behavior<BottomNavigationView>() {

    override fun layoutDependsOn(parent: CoordinatorLayout?, child: BottomNavigationView?, dependency: View?): Boolean {
        val dependsOn = dependency is FrameLayout
        return dependsOn
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: BottomNavigationView, directTargetChild: View, target: View, axes: Int, type: Int): Boolean
            = axes == ViewCompat.SCROLL_AXIS_VERTICAL


    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: BottomNavigationView, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        if (dy < 0) {
            showBottomNavigationView(child!!)
        } else if (dy > 0) {
            hideBottomNavigationView(child!!)
        }
    }

    private fun hideBottomNavigationView(view: BottomNavigationView) {
        view.animate().translationY(view.height.toFloat())
    }

    private fun showBottomNavigationView(view: BottomNavigationView) {
        view.animate().translationY(0f)
    }
}
