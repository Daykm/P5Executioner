package com.daykm.p5executioner.personas

import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Module
abstract class PersonaListModule

@Subcomponent(modules = [(PersonaListModule::class)])
abstract class PersonaListFragmentComponent : AndroidInjector<PersonaListFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<PersonaListFragment>()
}
