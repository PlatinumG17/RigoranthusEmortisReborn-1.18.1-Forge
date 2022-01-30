package com.platinumg17.rigoranthusemortisreborn.canis.common.entity.ai;

import com.platinumg17.rigoranthusemortisreborn.canis.common.block.tileentity.CanisBedTileEntity;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.WorldUtil;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.core.BlockPos;

import java.util.EnumSet;

public class CanisMoveToBlockGoal extends Goal {

    protected final CanisEntity canis;

    public CanisMoveToBlockGoal(CanisEntity canisIn) {
        this.canis = canisIn;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return this.canis.getTargetBlock() != null && !this.canis.isOrderedToSit();
    }

    @Override
    public boolean canContinueToUse() {
        return this.canis.isPathFinding() && !this.canis.getTargetBlock().closerThan(this.canis.position, 0.5);
    }

    @Override
    public void stop() {
        BlockPos target = this.canis.getTargetBlock();
        CanisBedTileEntity canisBedTileEntity = WorldUtil.getTileEntity(canis.level, target, CanisBedTileEntity.class);

        if (canisBedTileEntity != null) {
            // Double check the bed still has no owner
            if (canisBedTileEntity.getOwnerUUID() == null) {
                canisBedTileEntity.setOwner(this.canis);
                this.canis.setBedPos(this.canis.level.dimension(), target);
            }
        }
        this.canis.setTargetBlock(null);
        this.canis.setOrderedToSit(true);
        this.canis.level.broadcastEntityEvent(this.canis, EmortisConstants.EntityState.CANIS_HEARTS);
    }

    @Override
    public void start() {
        BlockPos target = this.canis.getTargetBlock();
        this.canis.getNavigation().moveTo((target.getX()) + 0.5D, target.getY() + 1, (target.getZ()) + 0.5D, 1.0D);
    }
}

/*
public class MoveToSpecificBlockGoal extends Goal {

    protected final Mob mob;

    public MoveToSpecificBlockGoal(Mob mobIn) {
        this.mob = mobIn;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return this.mob.getTargetBlock() != null && !this.mob.immobile() && !this.mob.isDeadOrDying();
    }

    @Override
    public boolean canContinueToUse() {
        return this.mob.isPathFinding() && !this.mob.getTargetBlock().closerThan(this.mob.position, 0.5);
    }

    @Override
    public void stop() {
        BlockPos target = this.mob.getTargetBlock();
        CofferTileEntity cofferTileEntity = WorldUtil.getTileEntity(mob, target, CofferTileEntity.class);

        if (cofferTileEntity != null) {
            // Double check the bed still has no owner
            if (cofferTileEntity.getOwnerUUID() == null) {
                cofferTileEntity.setOwner(this.mob);
                this.mob.setCofferPos(this.mob.level.dimension(), target);
            }
        }
        this.mob.setTargetBlock(null);
        this.mob.setImmobile(true); // idk make it stop somewhere specific or something
        this.mob.level. // figure the rest out idk
    }

    @Override
    public void start() {
        BlockPos target = this.mob.getTargetBlock();
        this.mob.getNavigation().moveTo((target.getX()) + 0.5D, target.getY() + 1, (target.getZ()) + 0.5D, 1.0D);
    }
}
 */