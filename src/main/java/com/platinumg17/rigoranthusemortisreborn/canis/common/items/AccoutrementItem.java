package com.platinumg17.rigoranthusemortisreborn.canis.common.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.entity.AbstractCanisEntity;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.interfaces.ICanisItem;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.Accoutrement;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.AccoutrementInstance;

import java.util.function.Supplier;

public class AccoutrementItem extends Item implements ICanisItem {

    public Supplier<? extends Accoutrement> type;

    public AccoutrementItem(Supplier<? extends Accoutrement> type, Properties properties) {
        super(properties);
        this.type = type;
    }

    @Override
    public InteractionResult processInteract(AbstractCanisEntity canisIn, Level worldIn, Player playerIn, InteractionHand handIn) {
        if (canisIn.canInteract(playerIn) && canisIn.addAccoutrement(this.createInstance(canisIn, playerIn.getItemInHand(handIn), playerIn))) {
            canisIn.consumeItemFromStack(playerIn, playerIn.getItemInHand(handIn));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public AccoutrementInstance createInstance(AbstractCanisEntity canisIn, ItemStack stack, Player playerIn) {
        return this.type.get().getDefault();
    }
}
