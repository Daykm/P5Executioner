package com.daykm.p5executioner.fusion

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daykm.p5executioner.database.Dao
import com.daykm.p5executioner.fusion.databinding.FragmentFusionBinding
import dagger.android.support.DaggerFragment
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FusionFragment : DaggerFragment() {

    @Inject
    lateinit var fusionAdapter: P5FusionAdapter
    @Inject
    lateinit var activityPool: RecyclerView.RecycledViewPool
    @Inject
    lateinit var dao: Dao

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentFusionBinding.inflate(inflater, container, false)
        binding.fusionRecycler.apply {
            layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
            adapter = fusionAdapter.adapter
            recycledViewPool = activityPool
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Single.fromCallable(dao::personas)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy {
                    fusionAdapter.setPickerData(it)
                }
    }
}