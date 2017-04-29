package com.daykm.p5executioner.skills

import android.view.View
import com.airbnb.epoxy.EpoxyHolder
import com.daykm.p5executioner.databinding.SkillListItemBinding

class SkillItemHolder : EpoxyHolder() {
  lateinit var binding: SkillListItemBinding
  override fun bindView(itemView: View?) {
    binding = SkillListItemBinding.bind(itemView)
  }
}
