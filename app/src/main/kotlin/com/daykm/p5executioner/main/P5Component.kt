package com.daykm.p5executioner.main

import com.daykm.p5executioner.view.PageableAdapter
import dagger.Subcomponent

@ActivityScope @Subcomponent(modules = arrayOf(P5Module::class)) interface P5Component {
    fun adapter(): PageableAdapter
}