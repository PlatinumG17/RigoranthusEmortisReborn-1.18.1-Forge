package com.platinumg17.rigoranthusemortisreborn.api.apicanis.interfaces;

import javax.annotation.Nullable;

import com.platinumg17.rigoranthusemortisreborn.api.apicanis.entity.AbstractCanisEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public interface ICanisFoodHandler extends ICanisFoodPredicate {
    /**
     * Checks if the canis can eat the food item
     * used by some food items to apply potion effects
     * @param canisIn The canis eating the item
     * @param stackIn The stack that is being eaten, DO NOT alter the start in this method
     * @param entityIn The entity who fed the canis, usually the player. Can be null probably meaning the canis ate on its own
     * @return If the canis can eat the stack, {@link #consume} is called to eat the stack
     */
    public boolean canConsume(AbstractCanisEntity canisIn, ItemStack stackIn, @Nullable Entity entityIn);

    /**
     * Actually eat the stack,
     * @param canisIn The canis eating the item
     * @param stackIn The stack that is being eaten
     * @param entityIn The entity who fed the canis, usually the player. Can be null probably meaning the canis ate on its own
     * @return
     */
    public InteractionResult consume(AbstractCanisEntity canisIn, ItemStack stackIn, @Nullable Entity entityIn);
}