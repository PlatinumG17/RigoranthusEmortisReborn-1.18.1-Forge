package com.platinumg17.rigoranthusemortisreborn.magica.common.entity.familiar;

import com.platinumg17.rigoranthusemortisreborn.magica.common.entity.familiar.FamiliarEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class FamiliarBaseGoal extends Goal {
    public FamiliarEntity entity;

    public FamiliarBaseGoal(FamiliarEntity entity){
        this.entity = entity;
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse();
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public boolean canUse() {
        return false;
    }
}