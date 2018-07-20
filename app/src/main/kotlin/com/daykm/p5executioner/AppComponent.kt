package com.daykm.p5executioner

import android.content.Context
import com.daykm.p5executioner.main.P5Activity
import com.daykm.p5executioner.main.P5ActivityComponent
import com.daykm.p5executioner.main.P5Module
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Module(subcomponents = [(P5ActivityComponent::class)])
abstract class AppModule {

    @ContributesAndroidInjector(modules = [(P5Module::class)])
    abstract fun contributesP5Activity(): P5Activity
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