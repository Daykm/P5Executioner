package com.daykm.p5executioner.sandbox.di

import android.content.Context
import com.daykm.p5executioner.database.di.DataModule
import com.daykm.p5executioner.sandbox.App
import com.daykm.p5executioner.sandbox.personas.PersonasSandboxActivity
import com.daykm.p5executioner.sandbox.personas.di.PersonasActivityComponent
import com.daykm.p5executioner.sandbox.personas.di.PersonasSandboxActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Module(
        includes = [DataModule::class],
        subcomponents = [
            PersonasActivityComponent::class
        ]
)
abstract class AppModule {
    @ContributesAndroidInjector(modules = [(PersonasSandboxActivityModule::class)])
    abstract fun contributesPersonas(): PersonasSandboxActivity
}

@Singleton
@Component(modules = [AppModule::class, AndroidSupportInjectionModule::class])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>() {
        @BindsInstance
        abstract fun ctx(ctx: Context): Builder
    }
}