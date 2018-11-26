package com.daykm.p5executioner.fusion.di

import com.daykm.p5executioner.fusion.FusionFragment
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector

@Module
interface MatronFusionModule {
    @ContributesAndroidInjector(modules = [FusionModule::class])
    fun contributesFusionFragment(): FusionFragment
}

@Module
interface FusionModule

@Subcomponent(modules = [(FusionModule::class)])
abstract class FusionComponent : AndroidInjector<FusionFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<FusionFragment>()
}