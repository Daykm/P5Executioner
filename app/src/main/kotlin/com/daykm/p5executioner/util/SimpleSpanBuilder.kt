package com.daykm.p5executioner.util

import android.text.ParcelableSpan
import android.text.Spannable
import android.text.SpannableStringBuilder

class SimpleSpanBuilder {

    private val spanSections: MutableList<SpanSection> = ArrayList()
    private val stringBuilder: StringBuilder = StringBuilder()

    fun append(text: String, vararg spans: ParcelableSpan): SimpleSpanBuilder {
        if (spans != null && spans.size > 0) {
            spanSections.add(SpanSection(text, stringBuilder.length, *spans))
        }
        stringBuilder.append(text)
        return this
    }

    fun build(): SpannableStringBuilder {
        val ssb = SpannableStringBuilder(stringBuilder.toString())
        for (section in spanSections) {
            section.apply(ssb)
        }
        return ssb
    }

    override fun toString(): String {
        return stringBuilder.toString()
    }
}

class SpanSection(private val text: String, private val startIndex: Int,
                  vararg spans: ParcelableSpan) {

    val spns = arrayOf(spans)

    fun apply(spanStringBuilder: SpannableStringBuilder?) {
        if (spanStringBuilder == null) return
        for (span in spns) {
            spanStringBuilder.setSpan(span, startIndex, startIndex + text.length,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        }
    }
}