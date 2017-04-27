package com.daykm.p5executioner

import android.app.Application
import android.content.Context
import com.squareup.leakcanary.LeakCanary
import dagger.Component
import dagger.Module
import dagger.Provides
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

    if (BuildConfig.DEBUG) {
      Timber.plant(DebugTree())
    }
    component = DaggerAppComponent.builder().appModule(AppModule(this)).build()

    if (!LeakCanary.isInAnalyzerProcess(this)) {
      LeakCanary.install(this)
    }
  }
}

@Module class AppModule(val app: App) {

  @Provides fun ctx(): Context {
    return app
  }

}

@Singleton @Component(modules = arrayOf(AppModule::class)) interface AppComponent {
  fun persona(): PersonaComponent
}
