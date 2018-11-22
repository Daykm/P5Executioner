package com.daykm.p5executioner.skills

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daykm.p5executioner.database.Dao
import com.daykm.p5executioner.skills.databinding.FragmentSkillsBinding
import dagger.android.support.DaggerFragment
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class P5SkillsFragment : DaggerFragment() {

    @Inject
    lateinit var skillsAdapter: P5SkillsAdapter

    @Inject
    lateinit var dao: Dao

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentSkillsBinding.inflate(inflater, container, false)

        binding.skillsRecycler.apply {
            layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
            adapter = skillsAdapter.adapter
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Single.fromCallable(dao::skills)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy {
                    skillsAdapter.setData(it)
                }
    }
}
