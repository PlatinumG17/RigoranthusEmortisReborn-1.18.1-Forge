package com.platinumg17.rigoranthusemortisreborn.api.apicanis.interfaces;

import com.platinumg17.rigoranthusemortisreborn.api.apicanis.entity.AbstractCanisEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.level.Level;

public interface ICanisItem {
    /**
     * Implement on item class called when player interacts with a canis
     * @param canisIn The canis the item is being used on
     * @param worldIn The world the canis is in
     * @param playerIn The player who clicked
     * @param handIn The hand used
     * @return The result of the interaction
     */
    public InteractionResult processInteract(AbstractCanisEntity canisIn, Level worldIn, Player playerIn, InteractionHand handIn);

    @Deprecated
    default InteractionResult onInteractWithCanis(AbstractCanisEntity canisIn, Level worldIn, Player playerIn, InteractionHand handIn) {
        return processInteract(canisIn, worldIn, playerIn, handIn);
    }
}