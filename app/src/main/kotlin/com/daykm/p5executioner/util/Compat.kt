package com.daykm.p5executioner.util

import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.StyleRes
import android.support.v4.content.ContextCompat
import android.widget.TextView


fun Context.color(@ColorRes color: Int): ColorStateList = ContextCompat.getColorStateList(this, color)

fun TextView.txtAppr(@StyleRes style: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        setTextAppearance(style)
    } else {
        setTextAppearance(context, style)
    }
}