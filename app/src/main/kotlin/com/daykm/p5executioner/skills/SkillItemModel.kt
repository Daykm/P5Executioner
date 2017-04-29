package com.daykm.p5executioner.skills

import android.content.Context
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.daykm.p5executioner.R
import com.daykm.p5executioner.R.string
import com.daykm.p5executioner.proto.Skill
import com.daykm.p5executioner.proto.Skill.Element.PASSIVE

class SkillItemModel(val skill: Skill, val ctx: Context) : EpoxyModelWithHolder<SkillItemHolder>() {

    val skillCost = skillCost()

    override fun getDefaultLayout(): Int {
        return R.layout.skill_list_item
    }

    override fun createNewHolder(): SkillItemHolder {
        return SkillItemHolder()
    }

    override fun bind(holder: SkillItemHolder) {
        holder.binding.let {
            it.skillCost.text = skillCost
            it.skillDescription.text = skill.effect
            it.skillElement.text = skill.element.name
            it.skillName.text = skill.name
        }
    }

    fun skillCost(): String {
        if (skill.element != PASSIVE) {
            return when {
                skill.cost < 100 -> ctx.getString(R.string.skill_cost_hp, skill.cost)
                else -> ctx.getString(R.string.skill_cost_sp, skill.cost / 100)
            }
        } else {
            return ctx.getString(string.skill_no_cost)
        }

    }
}