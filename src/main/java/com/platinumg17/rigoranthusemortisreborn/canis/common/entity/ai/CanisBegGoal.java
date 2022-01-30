package com.platinumg17.rigoranthusemortisreborn.canis.common.entity.ai;

import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.FoodHandler;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.CanisTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.EnumSet;

public class CanisBegGoal extends Goal {

    private final CanisEntity canis;
    private Player player;
    private final Level world;
    private final float minPlayerDistance;
    private int timeoutCounter;
    private final TargetingConditions playerPredicate;

    public CanisBegGoal(CanisEntity canis, float minDistance) {
        this.canis = canis;
        this.world = canis.level;
        this.minPlayerDistance = minDistance;
        this.playerPredicate = TargetingConditions.DEFAULT.range(minDistance); // TODO check
        this.setFlags(EnumSet.of(Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        this.player = this.world.getNearestPlayer(this.playerPredicate, this.canis);
        return this.player == null ? false : this.hasTemptationItemInHand(this.player);
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.player.isAlive()) {
            return false;
        } else if (this.canis.distanceToSqr(this.player) > this.minPlayerDistance * this.minPlayerDistance) {
            return false;
        } else {
            return this.timeoutCounter > 0 && this.hasTemptationItemInHand(this.player);
        }
    }

    @Override
    public void start() {
        this.canis.setBegging(true);
        this.timeoutCounter = 40 + this.canis.getRandom().nextInt(40);
    }

    @Override
    public void stop() {
        this.canis.setBegging(false);
        this.player = null;
    }

    @Override
    public void tick() {
        this.canis.getLookControl().setLookAt(this.player.getX(), this.player.getEyeY(), this.player.getZ(), 10.0F, this.canis.getMaxHeadXRot());
        --this.timeoutCounter;
    }

    private boolean hasTemptationItemInHand(Player player) {
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack itemstack = player.getItemInHand(hand);
            if ((this.canis.isTame() ? CanisTags.SNACK_ITEMS_TAMED : CanisTags.SNACK_ITEMS_UNTAMED).contains(itemstack.getItem())) {
                return true;
            }
            if (FoodHandler.isFood(itemstack).isPresent()) {
                return true;
            }
            if (this.canis.isFood(itemstack)) {
                return true;
            }
        }
        return false;
    }
}
