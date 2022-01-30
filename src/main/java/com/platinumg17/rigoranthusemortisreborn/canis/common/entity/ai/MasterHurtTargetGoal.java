package com.platinumg17.rigoranthusemortisreborn.canis.common.entity.ai;

import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.EnumMode;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;

public class MasterHurtTargetGoal extends OwnerHurtTargetGoal {
    private CanisEntity canis;

    public MasterHurtTargetGoal(CanisEntity canisIn) {
        super(canisIn);
        this.canis = canisIn;
    }

    @Override
    public boolean canUse() {
        return this.canis.isMode(EnumMode.AGGRESIVE, EnumMode.BERSERKER, EnumMode.TACTICAL) && super.canUse();
    }
}