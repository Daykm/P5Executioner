package com.daykm.p5executioner.main

import android.support.v7.widget.RecyclerView
import com.daykm.p5executioner.android.LoggingRecyclerPool
import com.daykm.p5executioner.di.InjectedActivityModule
import com.daykm.p5executioner.fusion.di.MatronFusionModule
import com.daykm.p5executioner.personas.di.MatronPersonaListModule
import com.daykm.p5executioner.skills.di.MatronSkillsModule
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Module(
        includes = [
            MatronFusionModule::class,
            MatronSkillsModule::class,
            MatronPersonaListModule::class
        ]
)

abstract class P5Module : InjectedActivityModule<P5Activity>() {

    @Binds
    abstract fun pool(pool: LoggingRecyclerPool): RecyclerView.RecycledViewPool
}

@Subcomponent
interface P5ActivityComponent : AndroidInjector<P5Activity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<P5Activity>()
}
