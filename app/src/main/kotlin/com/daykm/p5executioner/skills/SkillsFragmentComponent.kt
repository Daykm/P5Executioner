package com.daykm.p5executioner.skills

import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Module
abstract class SkillsModule

@Subcomponent(modules = [(SkillsModule::class)])
abstract class SkillsFragmentComponent : AndroidInjector<P5SkillsFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<P5SkillsFragment>()
}
