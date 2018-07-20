package com.daykm.p5executioner.fusion

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daykm.p5executioner.databinding.FragmentFusionBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FusionFragment : DaggerFragment() {

    @Inject
    lateinit var fusionAdapter: P5FusionAdapter
    @Inject
    lateinit var activityPool: RecyclerView.RecycledViewPool

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentFusionBinding.inflate(inflater, container, false)
        binding.fusionRecycler.apply {
            layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
            adapter = fusionAdapter.adapter
            recycledViewPool = activityPool
        }
        return binding.root
    }
}