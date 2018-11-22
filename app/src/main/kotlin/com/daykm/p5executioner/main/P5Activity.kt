package com.daykm.p5executioner.main

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.daykm.p5executioner.R
import com.daykm.p5executioner.databinding.ActivityMainBinding
import com.daykm.p5executioner.fusion.FusionFragment
import com.daykm.p5executioner.info.InfoFragment
import com.daykm.p5executioner.personas.PersonaListFragment
import com.daykm.p5executioner.skills.P5SkillsFragment
import com.daykm.p5executioner.view.FragmentItemPagerAdapter
import com.daykm.p5executioner.view.fragmentAdapterItem
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber

class P5Activity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initBottomNav(binding)
        initPager(binding)
    }

    private fun initPager(binding: ActivityMainBinding) {
        val adapter = FragmentItemPagerAdapter(supportFragmentManager)

        adapter.items = listOf(
                fragmentAdapterItem {
                    FusionFragment()
                },
                fragmentAdapterItem {
                    PersonaListFragment()
                },
                fragmentAdapterItem {
                    P5SkillsFragment()
                },
                fragmentAdapterItem {
                    InfoFragment()
                }
        )

        binding.viewPager.adapter = adapter
    }

    private fun initBottomNav(binding: ActivityMainBinding) {
        binding.bottomNav.apply {
            setOnNavigationItemReselectedListener { item ->
                Timber.i("Menu item '%s' reselected", item.title)
            }
            setOnNavigationItemSelectedListener { item ->
                Timber.i("Menu item '%s' selected", item.title)

                val index = when (item.itemId) {
                    R.id.nav_fusion -> 0
                    R.id.nav_by_persona -> 1
                    R.id.nav_skills -> 2
                    R.id.nav_settings -> 3
                    else -> 0
                }
                binding.viewPager.currentItem = index
                true
            }
        }
    }
}


