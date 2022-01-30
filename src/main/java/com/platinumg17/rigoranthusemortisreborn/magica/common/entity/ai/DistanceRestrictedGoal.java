package com.platinumg17.rigoranthusemortisreborn.magica.common.entity.ai;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.BlockUtil;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.core.BlockPos;

import java.util.function.Supplier;

public abstract class DistanceRestrictedGoal extends Goal {
    public BlockPos positionFrom;
    public int maxDistance;

    public DistanceRestrictedGoal(Supplier<BlockPos> pos, int maxDistance){
        this.positionFrom = pos.get();
        this.maxDistance = maxDistance;
    }

    public boolean isInRange(BlockPos pos){
        return BlockUtil.distanceFrom(pos, positionFrom) <= maxDistance;
    }
}