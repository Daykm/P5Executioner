package com.daykm.p5executioner.view

import android.support.annotation.IntDef
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorCompat
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.support.v7.widget.RecyclerView
import android.view.View


class BottomBarBehavior : CoordinatorLayout.Behavior<View>() {

    private var currentTranslation = 0f

    override fun layoutDependsOn(parent: CoordinatorLayout?, child: View?, dependency: View): Boolean = dependency is RecyclerView

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int): Boolean = true

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)
        currentTranslation = if (Math.abs(dyConsumed) > child.height) {
            if (dyConsumed > 0) 0.9f * child.height else 0f
        } else {
            var newTranslation = Math.max(0f, currentTranslation + dyConsumed * 0.75f)
            if (newTranslation > child.height) newTranslation = 0.9999f * child.height
            newTranslation
        }
        child.translationY = currentTranslation
    }

}

class BottomNavigationBehavior<V : View> : VerticalScrollingBehavior<V>() {

    companion object {
        val INTERPOLATOR = LinearOutSlowInInterpolator()
    }

    private var mTranslationAnimator: ViewPropertyAnimatorCompat? = null
    private var hidden = false
    private var mScrollingEnabled = true

    override fun onNestedVerticalOverScroll(coordinatorLayout: CoordinatorLayout, child: V, @ScrollDirection direction: Int, currentOverScroll: Int, totalOverScroll: Int) {}

    override fun onDirectionNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: V, target: View, dx: Int, dy: Int, consumed: IntArray, @ScrollDirection scrollDirection: Int) {
        handleDirection(child, scrollDirection)
    }

    private fun handleDirection(child: V, scrollDirection: Int) {
        if (!mScrollingEnabled) return
        if (scrollDirection == SCROLL_DIRECTION_DOWN && hidden) {
            hidden = false
            animateOffset(child, 0)
        } else if (scrollDirection == SCROLL_DIRECTION_UP && !hidden) {
            hidden = true
            animateOffset(child, child.height)
        }
    }

    override fun onNestedDirectionFling(coordinatorLayout: CoordinatorLayout, child: V, target: View, velocityX: Float, velocityY: Float, @ScrollDirection scrollDirection: Int): Boolean {
        handleDirection(child, scrollDirection)
        return true
    }

    private fun animateOffset(child: V, offset: Int) {
        ensureOrCancelAnimator(child)
        mTranslationAnimator!!.translationY(offset.toFloat()).start()
    }

    private fun ensureOrCancelAnimator(child: V) = if (mTranslationAnimator == null) {
        mTranslationAnimator = ViewCompat.animate(child).apply {
            duration = 300
            interpolator = INTERPOLATOR
        }
    } else {
        mTranslationAnimator!!.cancel()
    }
}

abstract class VerticalScrollingBehavior<V : View> : CoordinatorLayout.Behavior<V>() {

    private var totalDyUnconsumed = 0
    private var totalDy = 0

    var overScrollDirection = SCROLL_NONE
        private set(@ScrollDirection value) {
            field = value
        }
        @ScrollDirection get

    var scrollDirection = SCROLL_NONE
        private set(@ScrollDirection value) {
            field = value
        }
        @ScrollDirection get

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(SCROLL_DIRECTION_UP.toLong(), SCROLL_DIRECTION_DOWN.toLong(), SCROLL_NONE.toLong())
    annotation class ScrollDirection

    companion object {
        const val SCROLL_DIRECTION_UP = 1
        const val SCROLL_DIRECTION_DOWN: Int = -1
        const val SCROLL_NONE = 0
    }

    /**
     * @param coordinatorLayout
     * @param child
     * @param direction         Direction of the overscroll: SCROLL_DIRECTION_UP, SCROLL_DIRECTION_DOWN
     * @param currentOverScroll Unconsumed value, negative or positive based on the direction;
     * @param totalOverScroll   Cumulative value for current direction
     */
    internal abstract fun onNestedVerticalOverScroll(coordinatorLayout: CoordinatorLayout, child: V, @ScrollDirection direction: Int, currentOverScroll: Int, totalOverScroll: Int)

    /**
     * @param scrollDirection Direction of the overscroll: SCROLL_DIRECTION_UP, SCROLL_DIRECTION_DOWN
     */
    internal abstract fun onDirectionNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: V, target: View, dx: Int, dy: Int, consumed: IntArray, @ScrollDirection scrollDirection: Int)

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: V, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)
        if (dyUnconsumed > 0 && totalDyUnconsumed < 0) {
            totalDyUnconsumed = 0
            overScrollDirection = SCROLL_DIRECTION_UP
        } else if (dyUnconsumed < 0 && totalDyUnconsumed > 0) {
            totalDyUnconsumed = 0
            overScrollDirection = SCROLL_DIRECTION_DOWN
        }
        totalDyUnconsumed += dyUnconsumed
        onNestedVerticalOverScroll(coordinatorLayout, child, overScrollDirection, dyConsumed, totalDyUnconsumed)
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: V, directTargetChild: View, target: View, nestedScrollAxes: Int, type: Int): Boolean {
        return nestedScrollAxes and View.SCROLL_AXIS_VERTICAL != 0
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: V, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        if (dy > 0 && totalDy < 0) {
            totalDy = 0
            scrollDirection = SCROLL_DIRECTION_UP
        } else if (dy < 0 && totalDy > 0) {
            totalDy = 0
            scrollDirection = SCROLL_DIRECTION_DOWN
        }
        totalDy += dy
        onDirectionNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, scrollDirection)
    }


    override fun onNestedFling(coordinatorLayout: CoordinatorLayout, child: V, target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed)
        scrollDirection = if (velocityY > 0) SCROLL_DIRECTION_UP else SCROLL_DIRECTION_DOWN
        return onNestedDirectionFling(coordinatorLayout, child, target, velocityX, velocityY, scrollDirection)
    }

    internal abstract fun onNestedDirectionFling(coordinatorLayout: CoordinatorLayout, child: V, target: View, velocityX: Float, velocityY: Float, @ScrollDirection scrollDirection: Int): Boolean

}