package com.daykm.p5executioner.skills

import android.content.Context
import android.view.View
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.daykm.p5executioner.skills.databinding.SkillListItemBinding

class SkillItemModel(
        val skill: Skill,
        private val ctx: Context
) : EpoxyModelWithHolder<SkillItemHolder>() {

    val skillCost = skillCost()

    override fun getDefaultLayout(): Int = R.layout.skill_list_item

    override fun createNewHolder(): SkillItemHolder = SkillItemHolder()

    override fun bind(holder: SkillItemHolder) {
        holder.bindModel(this)
    }

    fun skillCost(): String =
            if (skill.element != PASSIVE) {
                when {
                    skill.cost < 100 -> {
                        val cost: Int = skill.cost
                        ctx.getString(R.string.skill_cost_hp, cost)
                    }
                    else -> {
                        val cost: Int = skill.cost / 100
                        ctx.getString(R.string.skill_cost_sp, cost)
                    }
                }
            } else ctx.getString(R.string.skill_no_cost)
}

class SkillItemHolder : EpoxyHolder() {

    private lateinit var binding: SkillListItemBinding

    override fun bindView(itemView: View) {
        binding = SkillListItemBinding.bind(itemView)
    }

    fun bindModel(model: SkillItemModel) {
        binding.skillCost.text = model.skillCost
        binding.skillDescription.text = model.skill.effect
        binding.skillElement.text = model.skill.element.name
        binding.skillName.text = model.skill.name
    }
}
