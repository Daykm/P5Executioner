package com.daykm.p5executioner.fusion

import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Module
interface FusionModule

@Subcomponent(modules = [(FusionModule::class)])
abstract class FusionFragmentComponent : AndroidInjector<FusionFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<FusionFragment>()
}