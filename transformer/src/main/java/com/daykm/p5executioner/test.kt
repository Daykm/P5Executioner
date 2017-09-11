package com.daykm.p5executioner

fun main(args: Array<String>) {
    val thing = listOf(listOf("0", "1"), listOf("2", "3"))

    thing.asSequence().flatten().forEach { println(it) }
}