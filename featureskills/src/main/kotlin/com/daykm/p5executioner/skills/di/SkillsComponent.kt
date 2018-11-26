package com.daykm.p5executioner.skills.di

import com.daykm.p5executioner.skills.SkillsFragment
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector

@Module
interface MatronSkillsModule {
    @ContributesAndroidInjector(modules = [SkillsModule::class])
    fun contributesSkillsFragment(): SkillsFragment
}

@Module
abstract class SkillsModule

@Subcomponent(modules = [(SkillsModule::class)])
abstract class SkillsComponent : AndroidInjector<SkillsFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<SkillsFragment>()
}
