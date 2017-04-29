package com.daykm.p5executioner.main

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.widget.RecyclerView.RecycledViewPool
import com.daykm.p5executioner.App
import com.daykm.p5executioner.R
import com.daykm.p5executioner.fusion.PersonaFusionAdapter
import com.daykm.p5executioner.personas.PersonaListAdapter
import com.daykm.p5executioner.skills.SkillsAdapter
import com.daykm.p5executioner.util.PlaceholderController
import com.daykm.p5executioner.util.RecyclerPagerController
import com.daykm.p5executioner.util.RecyclerPagerView
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import timber.log.Timber
import javax.inject.Scope

class P5Activity : Activity() {

    lateinit var pager: RecyclerPagerController
    lateinit var recycler: RecyclerPagerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_p_five)

        recycler = findViewById(R.id.recycler_pager) as RecyclerPagerView
        
        val component = App.INSTANCE.component.persona()

        pager = RecyclerPagerController(
                arrayOf(component.fusion(),
                        component.list(),
                        component.skills(),
                        PlaceholderController())
        ).apply {
            (findViewById(R.id.recycler_pager) as RecyclerPagerView).adapter = adapter
            requestModelBuild()
        }

        val nav = findViewById(R.id.bottom_nav) as BottomNavigationView

        nav.setOnNavigationItemReselectedListener {
            Timber.i("Menu item '%s' reselected", it.title)
        }

        nav.setOnNavigationItemSelectedListener {
            Timber.i("Menu item '%s' selected", it.title)
            when (it.itemId) {
                R.id.nav_fusion -> recycler.smoothScrollToPosition(0)
                R.id.nav_by_persona -> recycler.smoothScrollToPosition(1)
                R.id.nav_skills -> recycler.smoothScrollToPosition(2)
                R.id.nav_settings -> recycler.smoothScrollToPosition(3)
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
    fun skills(): SkillsAdapter
}