package com.platinumg17.rigoranthusemortisreborn.magica.common.entity.ai;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.BlockUtil;
import net.minecraft.world.entity.Mob;
import net.minecraft.core.BlockPos;

import java.util.function.Supplier;

public class ReturnHomeGoal extends DistanceRestrictedGoal {
    Mob entity;
    Supplier<Boolean> shouldGo;

    public ReturnHomeGoal(Mob entity, Supplier<BlockPos> pos, int maxDistance) {
        super(pos, maxDistance);
        this.entity = entity;
        this.shouldGo = () -> true;
    }

    public ReturnHomeGoal(Mob entity, Supplier<BlockPos> pos, int maxDistance, Supplier<Boolean> shouldGo) {
        super(pos, maxDistance);
        this.entity = entity;
        this.shouldGo = shouldGo;
    }

    @Override
    public void tick() {
        if(positionFrom != null && BlockUtil.distanceFrom(entity.blockPosition(), this.positionFrom) > 5){
            entity.getNavigation().moveTo(this.positionFrom.getX(), this.positionFrom.getY(), this.positionFrom.getZ(), 1.5);
        }
    }

    @Override
    public boolean canContinueToUse() {
        return positionFrom != null && BlockUtil.distanceFrom(entity.blockPosition(), this.positionFrom) > 5 && shouldGo.get();
    }

    @Override
    public boolean canUse() {
        return entity.level.random.nextFloat() < 0.02f && positionFrom != null && !this.isInRange(entity.blockPosition()) && shouldGo.get();
    }
}