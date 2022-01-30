package com.platinumg17.rigoranthusemortisreborn.canis.common.entity.ai;

import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.EnumMode;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;

public class SentinelModeGoal extends NearestAttackableTargetGoal<Monster> {

    private final CanisEntity canis;
    private LivingEntity owner;

    public SentinelModeGoal(CanisEntity canisIn, boolean checkSight) {
        super(canisIn, Monster.class, 0, checkSight, false, null);
        this.canis = canisIn;
    }

    @Override
    public boolean canUse() {
        LivingEntity owner = this.canis.getOwner();
        if (owner == null) { return false; }
        if (!this.canis.isMode(EnumMode.GUARD)) { return false; }
        this.owner = owner;
        if (super.canUse()) {
            this.owner = owner;
            return true;
        }
        return false;
    }
    @Override protected double getFollowDistance() {return 6D;}

    @Override
    protected void findTarget() {
        this.target = this.canis.level.getNearestEntity(this.targetType, this.targetConditions, this.canis, this.owner.getX(), this.owner.getEyeY(), this.owner.getZ(), this.getTargetSearchArea(this.getFollowDistance()));
    }
}