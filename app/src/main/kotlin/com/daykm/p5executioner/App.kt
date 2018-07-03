package com.daykm.p5executioner

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import dagger.Component
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject
import javax.inject.Singleton

class App : DaggerApplication() {

    @Inject
    lateinit var injector: AndroidInjector<App>

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = injector

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        debug {
            Timber.plant(DebugTree())
        }

        // component = DaggerAppComponent.builder().ctx(this).build()

        initLeakCanary()
    }
}

private fun Application.initLeakCanary() {
    if (!LeakCanary.isInAnalyzerProcess(this)) {
        LeakCanary.install(this)
    }
}

@Module(includes = [AndroidInjectionModule::class])
abstract class AppModule

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent : AndroidInjector<App> {

    /*
    @Component.Builder interface Builder {
        fun build(): AppComponent
        @BindsInstance fun ctx(ctx: Context): Builder
    }
    */
}

fun <Unit> debug(f: () -> Unit) {
    if (BuildConfig.DEBUG) f()
}