package com.daykm.p5executioner.personas

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daykm.p5executioner.database.Persona
import com.daykm.p5executioner.personas.databinding.FragmentPersonaListBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class PersonaListFragment : DaggerFragment() {

    @Inject
    lateinit var personaAdapter: PersonaEpoxyController
    @Inject
    lateinit var viewmodelFactory: ViewModelProvider.Factory

    lateinit var viewModel: PersonaViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentPersonaListBinding.inflate(inflater, container, false)

        binding.personaRecycler.apply {
            layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
            adapter = personaAdapter.adapter
        }

        viewModel = ViewModelProviders.of(this, viewmodelFactory).get(PersonaViewModel::class.java)

        val observer = Observer<List<Persona>> {
            personaAdapter.setData(it)
        }

        viewModel.personas.observe(this, observer)

        return binding.root
    }

}
