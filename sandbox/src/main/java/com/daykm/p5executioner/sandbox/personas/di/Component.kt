package com.daykm.p5executioner.sandbox.personas.di

import com.daykm.p5executioner.personas.PersonaListFragment
import com.daykm.p5executioner.personas.PersonaListFragmentComponent
import com.daykm.p5executioner.personas.PersonaListFragmentModule
import com.daykm.p5executioner.sandbox.di.InjectedActivityModule
import com.daykm.p5executioner.sandbox.personas.PersonasSandboxActivity
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector

@Module(
        subcomponents = [
            PersonaListFragmentComponent::class
        ]
)
abstract class PersonasSandboxActivityModule : InjectedActivityModule<PersonasSandboxActivity>() {

    @ContributesAndroidInjector(modules = [PersonaListFragmentModule::class])
    abstract fun contributesPersonaListFragment(): PersonaListFragment

}

@Subcomponent(modules = [PersonasSandboxActivityModule::class])
interface PersonasActivityComponent : AndroidInjector<PersonasSandboxActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<PersonasSandboxActivity>()
}
