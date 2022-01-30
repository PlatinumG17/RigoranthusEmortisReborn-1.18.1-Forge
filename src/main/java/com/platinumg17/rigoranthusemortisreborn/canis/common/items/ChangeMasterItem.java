package com.platinumg17.rigoranthusemortisreborn.canis.common.items;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.entity.AbstractCanisEntity;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.interfaces.ICanisItem;

public class ChangeMasterItem extends Item implements ICanisItem {

    public ChangeMasterItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult processInteract(AbstractCanisEntity canisIn, Level worldIn, Player playerIn, InteractionHand handIn) {
        if (!canisIn.isOwnedBy(playerIn)) {

            if (!worldIn.isClientSide) {
                canisIn.tame(playerIn);
                canisIn.getNavigation().stop();
                canisIn.setTarget((LivingEntity) null);
                canisIn.setOrderedToSit(true);
                worldIn.broadcastEntityEvent(canisIn, EmortisConstants.EntityState.CANIS_HEARTS);

                //TODO playerIn.sendMessage(new TranslatableComponent(""));
            }

            return InteractionResult.SUCCESS;
        }
        //TODO playerIn.sendMessage(new TranslatableComponent(""));
        return InteractionResult.FAIL;
    }
}
