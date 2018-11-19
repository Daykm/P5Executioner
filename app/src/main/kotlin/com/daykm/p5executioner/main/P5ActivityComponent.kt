package com.daykm.p5executioner.main

import android.support.v7.widget.RecyclerView
import com.daykm.p5executioner.android.LoggingRecyclerPool
import com.daykm.p5executioner.di.InjectedActivityModule
import com.daykm.p5executioner.fusion.FusionFragment
import com.daykm.p5executioner.fusion.FusionFragmentComponent
import com.daykm.p5executioner.fusion.FusionModule
import com.daykm.p5executioner.personas.PersonaListFragment
import com.daykm.p5executioner.personas.PersonaListFragmentComponent
import com.daykm.p5executioner.personas.PersonaListFragmentModule
import com.daykm.p5executioner.skills.P5SkillsFragment
import com.daykm.p5executioner.skills.SkillsFragmentComponent
import com.daykm.p5executioner.skills.SkillsModule
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

@Module
abstract class P5ActivityModule : InjectedActivityModule<P5Activity>()

@Module(
        includes = [
            TestModule::class,
            P5ActivityModule::class
        ],
        subcomponents = [
            FusionFragmentComponent::class,
            SkillsFragmentComponent::class,
            PersonaListFragmentComponent::class
        ])
abstract class P5Module {

    @Binds
    abstract fun pool(pool: LoggingRecyclerPool): RecyclerView.RecycledViewPool

    @ContributesAndroidInjector(modules = [FusionModule::class])
    abstract fun contributesFusionFragment(): FusionFragment

    @ContributesAndroidInjector(modules = [SkillsModule::class])
    abstract fun contributesSkillsFragment(): P5SkillsFragment

    @ContributesAndroidInjector(modules = [PersonaListFragmentModule::class])
    abstract fun contributesPersonaListFragment(): PersonaListFragment
}

@Subcomponent
interface P5ActivityComponent : AndroidInjector<P5Activity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<P5Activity>()
}
