package com.daykm.p5executioner.personas.di

import com.daykm.p5executioner.personas.PersonaListFragment
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector

@Module(subcomponents = [PersonaListComponent::class])
interface MatronPersonaListModule {
    @ContributesAndroidInjector(modules = [PersonaListModule::class])
    fun contributesPersonaListFragment(): PersonaListFragment
}

@Module
interface PersonaListModule

@Subcomponent(modules = [(PersonaListModule::class)])
abstract class PersonaListComponent : AndroidInjector<PersonaListFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<PersonaListFragment>()
}
