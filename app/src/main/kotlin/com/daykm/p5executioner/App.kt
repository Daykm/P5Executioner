package com.daykm.p5executioner

import android.app.Application
import android.content.Context
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

    if (BuildConfig.DEBUG) {
      Timber.plant(DebugTree())
    }

    //CalligraphyConfig.initDefault(CalligraphyConfig.get())

    component = DaggerAppComponent.builder().ctx(this).build()

    if (!LeakCanary.isInAnalyzerProcess(this)) {
      LeakCanary.install(this)
    }
    }
}

@Module abstract class AppModule

@Singleton @Component(modules = arrayOf(AppModule::class)) interface AppComponent {
  fun persona(): PersonaComponent

  @Component.Builder interface Builder {
    fun build(): AppComponent
    @BindsInstance fun ctx(ctx: Context): Builder
  }
}
