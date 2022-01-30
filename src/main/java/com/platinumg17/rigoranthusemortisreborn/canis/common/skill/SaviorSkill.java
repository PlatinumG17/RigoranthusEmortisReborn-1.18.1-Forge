package com.platinumg17.rigoranthusemortisreborn.canis.common.skill;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.util.Mth;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.entity.AbstractCanisEntity;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.Skill;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.SkillInstance;

public class SaviorSkill extends SkillInstance {
    public SaviorSkill(Skill SkillIn, int levelIn) {
        super(SkillIn, levelIn);
    }
    @Override
    public void livingTick(AbstractCanisEntity canisIn) {
        if (canisIn.level.isClientSide) {
            return;
        }
        if (this.level() > 0) {
            LivingEntity owner = canisIn.getOwner();
            //TODO add particles and check how far away canis is
            if (owner != null && owner.getHealth() <= 6) {
                int healCost = this.healCost(this.level());
                if (canisIn.getCanisHunger() >= healCost) {
                    owner.heal(Mth.floor(this.level() * 1.5D));
                    canisIn.setCanisHunger(canisIn.getCanisHunger() - healCost);
                }
            }
        }
    }
    public int healCost(int level) {
        byte cost = 100;
        if (level >= 5) {cost = 80;}
        return cost;
    }
    @Override public boolean hasRenderer() {return true;}
}