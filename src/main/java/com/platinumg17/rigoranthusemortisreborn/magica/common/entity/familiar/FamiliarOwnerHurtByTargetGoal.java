package com.platinumg17.rigoranthusemortisreborn.magica.common.entity.familiar;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.entity.familiar.IFamiliar;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class FamiliarOwnerHurtByTargetGoal extends TargetGoal {

    IFamiliar familiar;
    private LivingEntity ownerLastHurtBy;
    private int timestamp;

    public FamiliarOwnerHurtByTargetGoal(IFamiliar familiar){
        super((Mob) familiar.getThisEntity(), false);
        this.familiar = familiar;
    }

    @Override
    public boolean canUse() {
        Entity owner = this.familiar.getOwnerServerside();
        LivingEntity livingentity = owner instanceof LivingEntity ? (LivingEntity) owner : null;

        if (livingentity == null) {
            return false;
        } else {
            this.ownerLastHurtBy = livingentity.getLastHurtByMob();
            int i = livingentity.getLastHurtByMobTimestamp();
            return i != this.timestamp && this.canAttack(this.ownerLastHurtBy, TargetingConditions.DEFAULT) && this.familiar.wantsToAttack(this.ownerLastHurtBy, livingentity);
        }
    }

    public void start() {
        this.mob.setTarget(this.ownerLastHurtBy);
        LivingEntity livingentity = (LivingEntity) this.familiar.getOwnerServerside();
        if (livingentity != null) {
            this.timestamp = livingentity.getLastHurtByMobTimestamp();
        }

        super.start();
    }
}
