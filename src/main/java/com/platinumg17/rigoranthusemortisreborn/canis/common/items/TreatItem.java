package com.platinumg17.rigoranthusemortisreborn.canis.common.items;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.CanisLevel;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.entity.AbstractCanisEntity;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.interfaces.ICanisItem;

public class
TreatItem extends Item implements ICanisItem {

    private final int maxLevel;
    private final CanisLevel.Type type;

    public TreatItem(int maxLevel, CanisLevel.Type typeIn, Properties properties) {
        super(properties);
        this.maxLevel = maxLevel;
        this.type = typeIn;
    }

    @Override
    public InteractionResult processInteract(AbstractCanisEntity canisIn, Level worldIn, Player playerIn, InteractionHand handIn) {
        if (!canisIn.isTame() || !canisIn.canInteract(playerIn)) {
            return InteractionResult.FAIL;
        }
        CanisLevel canisLevel = canisIn.getCanisLevel();
        if (canisIn.getAge() < 0) {
            if (!worldIn.isClientSide) {
                worldIn.broadcastEntityEvent(canisIn, EmortisConstants.EntityState.CANIS_SMOKE);
                playerIn.sendMessage(new TranslatableComponent("treat."+this.type.getName()+".too_young"), canisIn.getUUID());
            }
            return InteractionResult.CONSUME;
        }
        else if (!canisLevel.canIncrease(this.type)) {
            if (!worldIn.isClientSide) {
                worldIn.broadcastEntityEvent(canisIn, EmortisConstants.EntityState.CANIS_SMOKE);
                playerIn.sendMessage(new TranslatableComponent("treat."+this.type.getName()+".low_level"), canisIn.getUUID());
            }
            return InteractionResult.CONSUME;
        }
        else if (canisLevel.getLevel(this.type) < this.maxLevel) {
            if (!playerIn.level.isClientSide) {
                if (!playerIn.abilities.instabuild) {
                    playerIn.getItemInHand(handIn).shrink(1);
                }
                canisIn.increaseLevel(this.type);
                canisIn.setHealth(canisIn.getMaxHealth());
                canisIn.setOrderedToSit(true);
                worldIn.broadcastEntityEvent(canisIn, EmortisConstants.EntityState.CANIS_HEARTS);
                playerIn.sendMessage(new TranslatableComponent("treat."+this.type.getName()+".level_up"), canisIn.getUUID());
            }
            return InteractionResult.SUCCESS;
        }
        else {
            if (!worldIn.isClientSide) {
                worldIn.broadcastEntityEvent(canisIn, EmortisConstants.EntityState.CANIS_SMOKE);
                playerIn.sendMessage(new TranslatableComponent("treat."+this.type.getName()+".max_level"), canisIn.getUUID());
            }
            return InteractionResult.CONSUME;
        }
    }
}