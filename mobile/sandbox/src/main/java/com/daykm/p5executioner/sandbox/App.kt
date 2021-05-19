package com.daykm.p5executioner.sandbox

import com.daykm.p5executioner.sandbox.di.DaggerAppComponent
import dagger.android.support.DaggerApplication
import timber.log.Timber

class App : DaggerApplication() {

    @Suppress("HasPlatformType")
    override fun applicationInjector() = DaggerAppComponent.builder()
            .ctx(this)
            .create(this)

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}