package com.daykm.p5executioner

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.LayoutManager
import android.support.v7.widget.RecyclerView.RecycledViewPool
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyModelWithView


interface Pageable {
  fun manager(ctx: Context): LayoutManager
  fun attach()
  fun adapter(): Adapter<*>
}

class RecyclerPagerView : RecyclerView {
  constructor(context: Context) : super(context)
  constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
  constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
      super(context, attrs, defStyleAttr)

  init {
    setHasFixedSize(true)
    object : LinearLayoutManager(context, HORIZONTAL, false) {
      override fun canScrollVertically(): Boolean {
        return false
      }

      override fun canScrollHorizontally(): Boolean {
        return true
      }
    }.let {
      layoutManager = it
      it.recycleChildrenOnDetach = true
    }
    isNestedScrollingEnabled = false
    overScrollMode = View.OVER_SCROLL_NEVER
  }

  override fun onTouchEvent(e: MotionEvent?): Boolean {
    return false
  }

  override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
    return false
  }
}

class RecyclerPagerController(val pageables: Array<Pageable>) : EpoxyController() {
  val pool = RecycledViewPool()
  override fun buildModels() {
    for ((id, pageable) in pageables.withIndex()) {
      PagerModel(pageable, pool).id(id).addTo(this)
    }
  }
}

class PagerModel(val pageable: Pageable,
    val pool: RecycledViewPool) : EpoxyModelWithView<RecyclerView>() {

  override fun bind(view: RecyclerView) {
    view.adapter = pageable.adapter()
    pageable.attach()
  }

  override fun buildView(parent: ViewGroup): RecyclerView {
    return RecyclerView(parent.context).apply {
      layoutManager = pageable.manager(parent.context)
      recycledViewPool = pool
      layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.MATCH_PARENT)
      setHasFixedSize(true)
    }
  }

}

class PlaceholderController : EpoxyController(), Pageable {
  override fun manager(ctx: Context): LayoutManager {
    return LinearLayoutManager(ctx)
  }

  override fun attach() {
    requestModelBuild()
  }

  override fun adapter(): Adapter<*> {
    return adapter
  }

  override fun buildModels() {
    PlaceholderModel().id(1).addTo(this)
  }
}

class PlaceholderModel : EpoxyModelWithView<TextView>() {
  override fun buildView(parent: ViewGroup): TextView {
    return TextView(parent.context).apply {
      layoutParams = ViewGroup.LayoutParams(
          ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
        gravity = Gravity.CENTER
      }
      text = "Placeholder"
    }
  }

}