package com.platinumg17.rigoranthusemortisreborn.canis.common.entity.ai;

import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import net.minecraft.world.entity.LivingEntity;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.EnumMode;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

public class BerserkerModeGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {

    private final CanisEntity canis;

    public BerserkerModeGoal(CanisEntity canisIn, Class<T> targetClassIn, boolean checkSight) {
        super(canisIn, targetClassIn, checkSight, false);
        this.canis = canisIn;
    }

    @Override
    public boolean canUse() {
        return this.canis.isMode(EnumMode.BERSERKER) && super.canUse();
    }
}