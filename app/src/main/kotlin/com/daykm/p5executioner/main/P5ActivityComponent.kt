package com.daykm.p5executioner.main

import android.support.v7.widget.RecyclerView
import com.daykm.p5executioner.android.LoggingRecyclerPool
import com.daykm.p5executioner.fusion.FusionFragment
import com.daykm.p5executioner.fusion.FusionFragmentComponent
import com.daykm.p5executioner.fusion.FusionModule
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector

@Module
class TestModule {
    @Provides
    fun test() = "Hello"
}

@Module(includes = [TestModule::class], subcomponents = [FusionFragmentComponent::class])
abstract class P5Module {

    @Binds
    abstract fun pool(pool: LoggingRecyclerPool): RecyclerView.RecycledViewPool

    @ContributesAndroidInjector(modules = [FusionModule::class])
    abstract fun contributesFusionFragment(): FusionFragment
}

@Subcomponent
interface P5ActivityComponent : AndroidInjector<P5Activity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<P5Activity>()
}
