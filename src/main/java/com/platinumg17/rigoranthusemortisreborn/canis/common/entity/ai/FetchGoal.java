package com.platinumg17.rigoranthusemortisreborn.canis.common.entity.ai;

import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.EnumMode;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.interfaces.IThrowableItem;

import java.util.function.Predicate;

public class FetchGoal extends MoveToClosestItemGoal {

    public static Predicate<ItemStack> BONE_PREDICATE = (item) -> item.getItem() instanceof IThrowableItem;

    public FetchGoal(CanisEntity canisIn, double speedIn, float range) {
        super(canisIn, speedIn, range, 2, BONE_PREDICATE);
    }

    @Override
    public boolean canUse() {
        if (this.canis.isInSittingPose()) {
            return false;
        } else if (this.canis.hasBone()) {
            return false;
        }
        return this.canis.getMode() == EnumMode.DOCILE && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if (this.canis.isInSittingPose()) {
            return false;
        } else if (this.canis.hasBone()) {
            return false;
        }
        return this.canis.getMode() == EnumMode.DOCILE && super.canContinueToUse();
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void stop() {
        // Dog doesn't have bone and is close enough to target
        if (!this.canis.hasBone() && this.canis.distanceTo(this.target) < this.minDist * this.minDist) {
            if (this.target.isAlive() && !this.target.hasPickUpDelay()) {
                this.canis.setBoneVariant(this.target.getItem());
                this.target.discard();
            }
        }
        super.stop();
    }
}