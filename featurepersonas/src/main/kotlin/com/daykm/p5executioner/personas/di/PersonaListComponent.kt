package com.daykm.p5executioner.personas.di

import com.daykm.p5executioner.arch.ViewModelModule
import com.daykm.p5executioner.personas.PersonaListFragment
import com.daykm.p5executioner.personas.PersonaViewModel
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
abstract class PersonaListModule : ViewModelModule<PersonaViewModel>() {

}

@Subcomponent(modules = [(PersonaListModule::class)])
abstract class PersonaListComponent : AndroidInjector<PersonaListFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<PersonaListFragment>()
}
