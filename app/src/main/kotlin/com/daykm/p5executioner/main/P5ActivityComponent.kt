package com.daykm.p5executioner.main

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import com.daykm.p5executioner.fusion.di.MatronFusionModule
import com.daykm.p5executioner.personas.di.MatronPersonaListModule
import com.daykm.p5executioner.skills.di.MatronSkillsModule
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Module(
        includes = [
            MatronFusionModule::class,
            MatronSkillsModule::class,
            MatronPersonaListModule::class,
            P5Module.Bindings::class
        ]
)
class P5Module {
    @Provides
    fun pool(): RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

    @Module
    interface Bindings {
        @Binds
        fun activity(activity: P5Activity): AppCompatActivity
    }
}

@Subcomponent
interface P5ActivityComponent : AndroidInjector<P5Activity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<P5Activity>()
}
