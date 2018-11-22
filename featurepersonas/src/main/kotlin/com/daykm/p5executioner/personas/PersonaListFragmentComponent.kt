package com.daykm.p5executioner.personas

import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Module
abstract class PersonaListFragmentModule

@Subcomponent(modules = [(PersonaListFragmentModule::class)])
abstract class PersonaListFragmentComponent : AndroidInjector<PersonaListFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<PersonaListFragment>()
}
