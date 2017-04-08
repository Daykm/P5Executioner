package com.daykm.p5executioner

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.RecyclerView.RecycledViewPool
import android.view.Gravity
import com.daykm.p5executioner.databinding.ActivityPFiveBinding
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper

class P5Activity : Activity() {

	lateinit var activityBinding: ActivityPFiveBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setTheme(R.style.AppTheme)
		activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_p_five)

		val pool = RecycledViewPool()
		pool.setMaxRecycledViews(0, 32)

		val firstPersonaView = activityBinding.firstPersona
		firstPersonaView.setHasFixedSize(true)
		firstPersonaView.recycledViewPool = pool
		GravitySnapHelper(Gravity.START).attachToRecyclerView(firstPersonaView)
		firstPersonaView.inject(App.instance.component.persona())

		val secondPersonaView = activityBinding.secondPersona
		secondPersonaView.setHasFixedSize(true)
		secondPersonaView.recycledViewPool = pool
		GravitySnapHelper(Gravity.START).attachToRecyclerView(secondPersonaView)
		secondPersonaView.inject(App.instance.component.persona())


	}
}
