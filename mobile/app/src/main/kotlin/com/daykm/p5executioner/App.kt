package com.daykm.p5executioner

import android.app.Application
import com.daykm.p5executioner.di.DaggerAppComponent
import com.squareup.leakcanary.LeakCanary
import dagger.android.support.DaggerApplication
import timber.log.Timber
import timber.log.Timber.DebugTree

class App : DaggerApplication() {

    @Suppress("HasPlatformType")
    override fun applicationInjector() = DaggerAppComponent.builder()
            .ctx(this)
            .create(this)

    override fun onCreate() {
        super.onCreate()

        debug {
            Timber.plant(DebugTree())
        }

        initLeakCanary()
    }
}

private fun Application.initLeakCanary() {
    if (!LeakCanary.isInAnalyzerProcess(this)) {
        LeakCanary.install(this)
    }
}

fun <Unit> debug(f: () -> Unit) {
    if (BuildConfig.DEBUG) f()
}