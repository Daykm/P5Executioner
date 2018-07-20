package com.daykm.p5executioner.util

import android.app.Fragment
import android.app.FragmentManager
import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.StyleRes
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView


fun Context.colorStateList(@ColorRes color: Int): ColorStateList = ContextCompat.getColorStateList(this, color)!!
fun Context.color(@ColorRes color: Int): Int = ContextCompat.getColor(this, color)

fun TextView.txtAppr(@StyleRes style: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        setTextAppearance(style)
    } else {
        setTextAppearance(context, style)
    }
}

class TestFragment : Fragment()

fun test() {
    val m: FragmentManager? = null

    m!!.let {
        val frag: TestFragment = it.get("it")!!
    }
}

fun <T : Fragment?> FragmentManager.get(key: String): T? = getFragment(null, key)?.let { it as? T }

val View.inflater get() = LayoutInflater.from(context)
