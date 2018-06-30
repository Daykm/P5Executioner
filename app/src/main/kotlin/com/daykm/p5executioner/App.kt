package com.daykm.p5executioner

import android.app.Application
import android.content.Context
import com.daykm.p5executioner.main.P5Component
import com.daykm.p5executioner.main.P5Module
import com.squareup.leakcanary.LeakCanary
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Singleton

class App : Application() {

    companion object {
        lateinit var INSTANCE: App
            private set
    }

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        debug {
            Timber.plant(DebugTree())
        }

        component = DaggerAppComponent.builder().ctx(this).build()

        initLeakCanary()
    }
}

fun Application.initLeakCanary() {
    if (!LeakCanary.isInAnalyzerProcess(this)) {
        LeakCanary.install(this)
    }
}

@Module abstract class AppModule

@Singleton @Component(modules = arrayOf(AppModule::class)) interface AppComponent {

    fun persona(module: P5Module): P5Component

    @Component.Builder interface Builder {
        fun build(): AppComponent
        @BindsInstance fun ctx(ctx: Context): Builder
    }
}

fun <Unit> debug(f: () -> Unit) {
    if (BuildConfig.DEBUG) f()
}