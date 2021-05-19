package com.daykm.p5executioner.skills

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daykm.p5executioner.arch.MonoViewModelFactory
import com.daykm.p5executioner.database.Skill
import com.daykm.p5executioner.skills.databinding.FragmentSkillsBinding
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class SkillsFragment : DaggerFragment() {

    @Inject
    lateinit var skillsAdapter: SkillsAdapter
    @Inject
    lateinit var viewModelFactory: MonoViewModelFactory<SkillsViewModel>
    @Inject
    lateinit var viewModel: SkillsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentSkillsBinding.inflate(inflater, container, false)

        binding.skillsRecycler.apply {
            layoutManager = LinearLayoutManager(binding.root.context, RecyclerView.VERTICAL, false)
            adapter = skillsAdapter.adapter
        }

        viewModel = activity?.run {
            Timber.i("Getting PersonaViewModel")
            ViewModelProviders.of(this, viewModelFactory).get(SkillsViewModel::class.java)
        } ?: throw Exception("Invalid activity $activity")

        val skillsObserver = Observer<List<Skill>> { liveData ->
            liveData?.let {
                skillsAdapter.setData(it)
            }
        }

        viewModel.skills.observe(this, skillsObserver)

        return binding.root
    }
}
