package com.platinumg17.rigoranthusemortisreborn.items.specialized;

import com.platinumg17.rigoranthusemortisreborn.core.registry.RigoranthusDamageSources;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

public class RazorToothItem extends Item {

    public RazorToothItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(attacker instanceof Player) {
            if(!((Player) attacker).isCreative()) {
                ItemEntity razorTooth = new ItemEntity(attacker.level, attacker.getX(), attacker.getY(), attacker.getZ(), stack.copy());
                if(!attacker.level.isClientSide) {
                    razorTooth.getItem().setCount(1);
                    razorTooth.setPickUpDelay(40);
                    attacker.level.addFreshEntity(razorTooth);
                    stack.shrink(1);
                    Component message = new TranslatableComponent("tooltip.rigoranthusemortisreborn.tooth.swing");
                    attacker.sendMessage(message, Util.NIL_UUID);
                }
                attacker.setHealth(attacker.getHealth() - 1);
                return true;
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if(entityLiving instanceof Player) {
            if(!((Player) entityLiving).isCreative()) {
                ItemEntity razorTooth = new ItemEntity(entityLiving.level, entityLiving.getX(), entityLiving.getY(), entityLiving.getZ(), stack.copy());
                if(!entityLiving.level.isClientSide) {
                    razorTooth.getItem().setCount(1);
                    razorTooth.setPickUpDelay(40);
                    entityLiving.level.addFreshEntity(razorTooth);
                    stack.shrink(1);
                    Component message = new TranslatableComponent("tooltip.rigoranthusemortisreborn.tooth.use");
                    entityLiving.sendMessage(message, Util.NIL_UUID);
                }
                entityLiving.hurt(RigoranthusDamageSources.RAZOR_TOOTH, 1);
            }
        }
        return super.mineBlock(stack, worldIn, state, pos, entityLiving);
    }
}