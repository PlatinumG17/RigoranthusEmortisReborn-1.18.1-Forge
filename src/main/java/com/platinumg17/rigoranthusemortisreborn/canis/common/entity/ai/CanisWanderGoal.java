package com.platinumg17.rigoranthusemortisreborn.canis.common.entity.ai;

import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.EnumMode;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Random;

public class CanisWanderGoal extends Goal {

    protected final CanisEntity canis;
    protected final double speed;
    protected int executionChance;

    public CanisWanderGoal(CanisEntity canisIn, double speedIn) {
        this.canis = canisIn;
        this.speed = speedIn;
        this.executionChance = 60;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!this.canis.isTame() || this.canis.isVehicle()) {
            return false;
        }
        if (!this.canis.isMode(EnumMode.WANDERING)) {
            return false;
        }
        Optional<BlockPos> bowlPos = this.canis.getBowlPos();

        if (!bowlPos.isPresent()) {
            return false;
        }
        return bowlPos.get().distSqr(this.canis.blockPosition()) < 400.0D;
    }

    @Override
    public void tick() {
        if (this.canis.getNoActionTime() >= 100) {
            return;
        } else if (this.canis.getRandom().nextInt(this.executionChance) != 0) {
            return;
        } if (this.canis.isPathFinding()) {
            return;
        }

        Vec3 pos = this.getPosition();
        this.canis.getNavigation().moveTo(pos.x, pos.y, pos.z, this.speed);
    }

    @Nullable
    protected Vec3 getPosition() {
        PathNavigation pathNavigate = this.canis.getNavigation();
        Random random = this.canis.getRandom();

        int xzRange = 5;
        int yRange = 3;

        float bestWeight = Float.MIN_VALUE;
        Optional<BlockPos> bowlPos = this.canis.getBowlPos();
        BlockPos bestPos = bowlPos.get();

        for (int attempt = 0; attempt < 5; ++attempt) {
            int l = random.nextInt(2 * xzRange + 1) - xzRange;
            int i1 = random.nextInt(2 * yRange + 1) - yRange;
            int j1 = random.nextInt(2 * xzRange + 1) - xzRange;

            BlockPos testPos = bowlPos.get().offset(l, i1, j1);

            if (pathNavigate.isStableDestination(testPos)) {
                float weight = this.canis.getWalkTargetValue(testPos);

                if (weight > bestWeight) {
                    bestWeight = weight;
                    bestPos = testPos;
                }
            }
        }
        return new Vec3(bestPos.getX(), bestPos.getY(), bestPos.getZ());
    }
}