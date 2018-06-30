package com.daykm.p5executioner.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.daykm.p5executioner.App
import com.daykm.p5executioner.R
import org.jetbrains.anko.setContentView
import timber.log.Timber

class P5Activity : AppCompatActivity() {

    val component: P5Component by lazy { App.INSTANCE.component.persona(P5Module(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        P5Layout().apply {
            setContentView(this@P5Activity)
            pager.adapter = component.adapter()
            nav.apply {
                setOnNavigationItemReselectedListener {
                    Timber.i("Menu item '%s' reselected", it.title)
                }
                setOnNavigationItemSelectedListener {
                    Timber.i("Menu item '%s' selected", it.title)
                    pager.currentItem = when (it.itemId) {
                        R.id.nav_fusion -> 0
                        R.id.nav_by_persona -> 1
                        R.id.nav_skills -> 2
                        R.id.nav_settings -> 3
                        else -> 0
                    }
                    true
                }
            }
        }
    }
}


