package com.daykm.p5executioner.fusion

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
import com.daykm.p5executioner.fusion.databinding.FragmentFusionBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FusionFragment : DaggerFragment() {

    @Inject
    lateinit var fusionAdapter: P5FusionAdapter
    @Inject
    lateinit var activityPool: RecyclerView.RecycledViewPool
    @Inject
    lateinit var viewModelFactory: MonoViewModelFactory<FusionViewModel>

    private lateinit var viewModel: FusionViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory).get(FusionViewModel::class.java)
        } ?: throw Exception("Invalid activity $activity")

        val binding = FragmentFusionBinding.inflate(inflater, container, false)
        binding.fusionRecycler.apply {
            layoutManager = LinearLayoutManager(binding.root.context, RecyclerView.VERTICAL, false)
            adapter = fusionAdapter.adapter
            setRecycledViewPool(activityPool)

        }

        val firstPickerObserver = Observer<List<Persona>> { liveData ->
            liveData?.let {
                fusionAdapter.setFirstPickerData(it)
            }
        }

        viewModel.firstPersonas.observe(this, firstPickerObserver)

        val secondPickerObserver = Observer<List<Persona>> { liveData ->
            liveData?.let {
                fusionAdapter.setSecondPickerData(it)
            }
        }

        viewModel.secondPersonas.observe(this, secondPickerObserver)

        return binding.root
    }
}