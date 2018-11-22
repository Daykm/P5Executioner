package com.daykm.p5executioner.personas

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daykm.p5executioner.database.Dao
import com.daykm.p5executioner.personas.databinding.FragmentPersonaListBinding
import dagger.android.support.DaggerFragment
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PersonaListFragment : DaggerFragment() {

    @Inject
    lateinit var personaAdapter: PersonaEpoxyController
    @Inject
    lateinit var dao: Dao

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentPersonaListBinding.inflate(inflater, container, false)

        binding.personaRecycler.apply {
            layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
            adapter = personaAdapter.adapter
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        Single.fromCallable(dao::personas)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy {
                    personaAdapter.setData(it)
                }
    }
}
