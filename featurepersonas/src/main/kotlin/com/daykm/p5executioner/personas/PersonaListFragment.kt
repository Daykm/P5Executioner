package com.daykm.p5executioner.personas

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daykm.p5executioner.personas.databinding.FragmentPersonaListBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class PersonaListFragment : DaggerFragment() {

    @Inject
    lateinit var personaAdapter: P5ListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentPersonaListBinding.inflate(inflater, container, false)

        binding.personaRecycler.apply {
            layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
            adapter = personaAdapter.adapter
        }

        return binding.root
    }
}
