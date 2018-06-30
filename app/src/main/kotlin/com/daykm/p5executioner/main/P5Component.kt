package com.daykm.p5executioner.main

import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(P5Module::class)) interface P5Component {


}