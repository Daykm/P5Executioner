package com.daykm.p5executioner

import android.support.v7.widget.RecyclerView.Adapter
import android.view.ViewGroup
import javax.inject.Inject

class PAdapter @Inject constructor(val repo: DataRepo) : Adapter<VH>() {

  override fun getItemCount(): Int {
    return repo.personae.size
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
    return VH(parent)
  }

  override fun onBindViewHolder(holder: VH?, position: Int) {
    holder?.bind(repo.personae[position])
  }

}
