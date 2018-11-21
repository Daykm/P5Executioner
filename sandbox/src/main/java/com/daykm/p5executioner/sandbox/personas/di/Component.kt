package com.daykm.p5executioner.sandbox.personas.di

import android.support.v7.app.AppCompatActivity
import com.daykm.p5executioner.personas.PersonaListFragment
import com.daykm.p5executioner.personas.PersonaListFragmentComponent
import com.daykm.p5executioner.personas.PersonaListFragmentModule
import com.daykm.p5executioner.sandbox.personas.PersonasSandboxActivity
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector

@Module(
        subcomponents = [
            PersonaListFragmentComponent::class
        ]
)
interface PersonasSandboxActivityModule {

    @Binds
    fun activity(activity: PersonasSandboxActivity): AppCompatActivity

    @ContributesAndroidInjector(modules = [PersonaListFragmentModule::class])
    fun contributesPersonaListFragment(): PersonaListFragment

}

@Subcomponent(modules = [PersonasSandboxActivityModule::class])
interface PersonasActivityComponent : AndroidInjector<PersonasSandboxActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<PersonasSandboxActivity>()
}
