package com.daykm.p5executioner.sandbox.personas.di

import androidx.appcompat.app.AppCompatActivity
import com.daykm.p5executioner.personas.di.MatronPersonaListModule
import com.daykm.p5executioner.sandbox.personas.PersonasSandboxActivity
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Module(
        includes = [
            MatronPersonaListModule::class
        ]
)
interface PersonasSandboxActivityModule {

    @Binds
    fun activity(activity: PersonasSandboxActivity): AppCompatActivity
}

@Subcomponent(modules = [PersonasSandboxActivityModule::class])
interface PersonasActivityComponent : AndroidInjector<PersonasSandboxActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<PersonasSandboxActivity>()
}
