package com.daykm.p5executioner.skills

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daykm.p5executioner.databinding.FragmentSkillsBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class P5SkillsFragment : DaggerFragment() {

    @Inject
    lateinit var skillsAdapter: P5SkillsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentSkillsBinding.inflate(inflater, container, false)

        binding.skillsRecycler.apply {
            layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
            adapter = skillsAdapter.adapter
        }
        return binding.root
    }
}
