package com.daykm.p5executioner.fusion

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daykm.p5executioner.databinding.FragmentFusionBinding

class FusionFragment : Fragment() {

    override fun onAttach(context: Context?) {

        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentFusionBinding.inflate(inflater, container, false)
        binding.fusionRecycler.apply {
            layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
            // adapter = P5FusionAdapter().adapter
        }
        return binding.root
    }
}