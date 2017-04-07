package com.daykm.p5executioner

import android.app.Application
import android.content.Context
import com.daykm.p5executioner.json.ArcanaAdapter
import com.daykm.p5executioner.json.Persona
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.Component
import dagger.Module
import dagger.Provides
import okio.Okio
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



    val moshi = Moshi.Builder().add(ArcanaAdapter()).build()
    val adapter: JsonAdapter<List<Persona>> =
        Moshi.Builder()
            .add(ArcanaAdapter())
            .build()
            .adapter(
                Types.newParameterizedType(List::class.java, Persona::class.java))

    val personas: List<Persona> = adapter.fromJson(
        Okio.buffer(Okio.source(assets.open("personae.json"))))

    for(persona in personas) {
      Timber.i("Name: %s, Level: %d, Arcana: %s", persona.name, persona.level, persona.arcana.jsonVal)
    }
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