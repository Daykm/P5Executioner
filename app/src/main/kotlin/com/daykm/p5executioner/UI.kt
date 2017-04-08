package com.daykm.p5executioner

import android.support.constraint.ConstraintLayout
import android.view.ViewManager
import org.jetbrains.anko.custom.ankoView

inline fun ViewManager.constraintLayout(theme: Int = 0)
		= constraintLayout(theme) {}

inline fun ViewManager.constraintLayout(
		theme: Int = 0, init: ConstraintLayout.() -> Unit) =
		ankoView(::ConstraintLayout, theme, init)

// TODO get anko working