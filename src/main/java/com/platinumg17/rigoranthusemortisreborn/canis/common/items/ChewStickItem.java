package com.platinumg17.rigoranthusemortisreborn.canis.common.items;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.InteractionResultHolder;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.entity.AbstractCanisEntity;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.interfaces.ICanisFoodHandler;

public class ChewStickItem extends Item implements ICanisFoodHandler {

    public ChewStickItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFood(ItemStack stackIn) {
        return stackIn.getItem() == this;
    }

    @Override
    public boolean canConsume(AbstractCanisEntity canisIn, ItemStack stackIn, Entity entityIn) {
        return true;
    }

    @Override
    public InteractionResult consume(AbstractCanisEntity canisIn, ItemStack stackIn, Entity entityIn) {
        if (!canisIn.level.isClientSide) {
            canisIn.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100, 1, false, true));
            canisIn.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 6, false, true));
            canisIn.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 2, false, true));
            canisIn.consumeItemFromStack(entityIn, stackIn);
        }
        return InteractionResult.SUCCESS;
    }
}
