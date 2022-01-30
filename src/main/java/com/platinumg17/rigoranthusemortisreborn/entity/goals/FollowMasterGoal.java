package com.platinumg17.rigoranthusemortisreborn.entity.goals;

import com.platinumg17.rigoranthusemortisreborn.canis.common.util.EntityUtil;
import com.platinumg17.rigoranthusemortisreborn.entity.mobs.SummonedCadaver;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

import java.util.EnumSet;

public class FollowMasterGoal extends Goal {

    private final SummonedCadaver cadaver;
    private final PathNavigation navigator;
    private final Level world;
    private final double followSpeed;
    private final float stopDist; // If closer than stopDist stop moving towards owner
    private final float startDist; // If further than startDist moving towards owner

    private LivingEntity owner;
    private int timeToRecalcPath;
    private float oldWaterCost;

    public FollowMasterGoal(SummonedCadaver cadaverIn, double speedIn, float minDistIn, float maxDistIn) {
        this.cadaver = cadaverIn;
        this.world = cadaverIn.level;
        this.followSpeed = speedIn;
        this.navigator = cadaverIn.getNavigation();
        this.startDist = minDistIn;
        this.stopDist = maxDistIn;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity owner = this.cadaver.getOwner();
        if (owner == null) {
            return false;
        } else if (owner.isSpectator()) {
            return false;
        } else {
            this.owner = owner;
            return true;
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (this.navigator.isDone()) {
            return false;
        } else {
            return this.cadaver.distanceToSqr(this.owner) > this.stopDist * this.stopDist;
        }
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.cadaver.getPathfindingMalus(BlockPathTypes.WATER);
        this.cadaver.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

    @Override
    public void stop() {
        this.owner = null;
        this.navigator.stop();
        this.cadaver.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
    }

    @Override
    public void tick() {
        this.cadaver.getLookControl().setLookAt(this.owner, 10.0F, this.cadaver.getMaxHeadXRot());
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = 10;
            if (!this.cadaver.isLeashed() && !this.cadaver.isPassenger()) { // Is not leashed and is not a passenger
                if (this.cadaver.distanceToSqr(this.owner) >= 144.0D) { // Further than 12 blocks away teleport
                    EntityUtil.tryToTeleportNearEntity(this.cadaver, this.navigator, this.owner, 4);
                } else {
                    this.navigator.moveTo(this.owner, this.followSpeed);
                }
            }
        }
    }

    public float getMinStartDistanceSq() {
//        return 5F;
        return this.startDist * this.startDist;
    }
}