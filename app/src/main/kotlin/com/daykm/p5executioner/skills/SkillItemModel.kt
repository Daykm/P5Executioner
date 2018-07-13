package com.daykm.p5executioner.skills

import android.content.Context
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.daykm.p5executioner.R
import com.daykm.p5executioner.R.string
import com.daykm.p5executioner.proto.Skill
import com.daykm.p5executioner.proto.Skill.Element.PASSIVE

class SkillItemModel(
        private val skill: Skill,
        private val ctx: Context
) : EpoxyModelWithHolder<SkillItemHolder>() {

    private val skillCost = skillCost()

    override fun getDefaultLayout(): Int = R.layout.skill_list_item

    override fun createNewHolder(): SkillItemHolder = SkillItemHolder()

    override fun bind(holder: SkillItemHolder) = holder.let {
        it.cost.text = skillCost
        it.effect.text = skill.effect
        it.element.text = skill.element.name
        it.name.text = skill.name
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
            } else ctx.getString(string.skill_no_cost)
}
