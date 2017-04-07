package com.daykm.p5executioner

import android.app.Application
import android.content.Context
import dagger.Component
import dagger.Module
import dagger.Provides
import timber.log.Timber
import timber.log.Timber.DebugTree

class App : Application() {

  companion object {
    lateinit var instance: App
      private set
  }

  lateinit var component : AppComponent

  override fun onCreate() {
    super.onCreate()
    instance = this

    if (BuildConfig.DEBUG) {
      Timber.plant(DebugTree())
    }

    component = DaggerAppComponent.builder().appModule(AppModule(this)).build()



  }
}


@Module class AppModule(val app : App) {

  @Provides fun ctx() : Context {
    return app
  }

}

@Component(modules = arrayOf(AppModule::class)) interface AppComponent {
  fun persona() : PersonaComponent
}