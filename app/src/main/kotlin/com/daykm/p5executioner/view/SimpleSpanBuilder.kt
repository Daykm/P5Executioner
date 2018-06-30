package com.daykm.p5executioner.view

import android.text.ParcelableSpan
import android.text.Spannable
import android.text.SpannableStringBuilder

class SimpleSpanBuilder {

    private val spanSections: MutableList<SpanSection> = mutableListOf()
    private val stringBuilder: StringBuilder = StringBuilder()

    fun append(text: String, span: ParcelableSpan) {
        spanSections.add(SpanSection(text, stringBuilder.length, span))
        stringBuilder.append(text)
    }

    fun build(): SpannableStringBuilder {
        val ssb = SpannableStringBuilder(stringBuilder.toString())
        for (section in spanSections) {
            section.apply(ssb)
        }
        return ssb
    }

    override fun toString(): String = stringBuilder.toString()
}

class SpanSection(private val text: String, private val startIndex: Int,
                  val span: ParcelableSpan) {

    fun apply(spanStringBuilder: SpannableStringBuilder) {
        spanStringBuilder.setSpan(span, startIndex, startIndex + text.length,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    }
}