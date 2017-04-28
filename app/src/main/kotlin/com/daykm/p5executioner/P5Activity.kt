package com.daykm.p5executioner

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.RecyclerView.RecycledViewPool
import com.daykm.p5executioner.databinding.ActivityPFiveBinding
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import timber.log.Timber
import javax.inject.Scope

class P5Activity : Activity() {

  lateinit var activityBinding: ActivityPFiveBinding

  lateinit var pager: RecyclerPagerController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setTheme(R.style.AppTheme)
    activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_p_five)

    val component = App.INSTANCE.component.persona()

    pager = RecyclerPagerController(
        arrayOf(component.fusion(), component.list(), PlaceholderController(),
            PlaceholderController())).apply {
      activityBinding.recyclerPager.adapter = adapter
      requestModelBuild()
    }

    val nav = activityBinding.bottomNav

    nav.setOnNavigationItemReselectedListener {
      Timber.i("Menu item '%s' reselected", it.title)
    }

    nav.setOnNavigationItemSelectedListener {
      Timber.i("Menu item '%s' selected", it.title)
      when (it.itemId) {
        R.id.nav_fusion -> activityBinding.recyclerPager.smoothScrollToPosition(0)
        R.id.nav_by_persona -> activityBinding.recyclerPager.smoothScrollToPosition(1)
        R.id.nav_third -> activityBinding.recyclerPager.smoothScrollToPosition(2)
        R.id.nav_settings -> activityBinding.recyclerPager.smoothScrollToPosition(3)
      }
      true
    }
  }
}

@Scope annotation class ActivityScope
@Module class PersonaModule {
  @Provides fun pool(): RecycledViewPool {
    return RecycledViewPool()
  }
}

@ActivityScope @Subcomponent(modules = arrayOf(PersonaModule::class)) interface PersonaComponent {
  fun fusion(): PersonaFusionAdapter
  fun list(): PersonaListAdapter
}