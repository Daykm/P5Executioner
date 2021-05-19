package com.daykm.p5executioner.personas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daykm.p5executioner.arch.MonoViewModelFactory
import com.daykm.p5executioner.database.Persona
import com.daykm.p5executioner.personas.databinding.FragmentPersonaListBinding
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class PersonaListFragment : DaggerFragment() {

    @Inject
    lateinit var personaAdapter: PersonaEpoxyController
    @Inject
    lateinit var viewmodelFactory: MonoViewModelFactory<PersonaViewModel>

    lateinit var viewModel: PersonaViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = activity?.run {
            Timber.i("Getting PersonaViewModel")
            ViewModelProviders.of(this, viewmodelFactory).get(PersonaViewModel::class.java)
        } ?: throw Exception("Invalid activity $activity")

        val binding = FragmentPersonaListBinding.inflate(inflater, container, false)

        binding.personaRecycler.apply {
            layoutManager = LinearLayoutManager(binding.root.context, RecyclerView.VERTICAL, false)
            adapter = personaAdapter.adapter
        }

        val observer = Observer<List<Persona>> {
            personaAdapter.setData(it)
        }

        viewModel.personas.observe(this, observer)

        return binding.root
    }
}
