package com.platinumg17.rigoranthusemortisreborn.core.registry.effects.weapons;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface InventoryTickEffect {
    void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected);

    InventoryTickEffect DROP_WHEN_IN_WATER = (stack, worldIn, entityIn, itemSlot, isSelected) -> {
        if(isSelected && entityIn.isInWater() && entityIn instanceof LivingEntity) {
            stack.hurtAndBreak(70, ((LivingEntity) entityIn), entity -> entity.broadcastBreakEvent(InteractionHand.MAIN_HAND));
            ItemEntity weapon = new ItemEntity(entityIn.level, entityIn.getX(), entityIn.getY(), entityIn.getZ(), stack.copy());
            weapon.getItem().setCount(1);
            weapon.setPickUpDelay(40);
            entityIn.level.addFreshEntity(weapon);
            stack.shrink(1);

            entityIn.hurt(DamageSource.LIGHTNING_BOLT, 5);
        }
    };
}