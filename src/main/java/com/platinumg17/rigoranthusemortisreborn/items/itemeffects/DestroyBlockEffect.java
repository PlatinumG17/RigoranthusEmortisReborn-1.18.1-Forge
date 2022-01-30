package com.platinumg17.rigoranthusemortisreborn.items.itemeffects;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public interface DestroyBlockEffect {
    void onDestroyBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entity);

    DestroyBlockEffect DOUBLE_FARM = (stack, worldIn, state, pos, entity) -> {
        if((state.getBlock() instanceof CropBlock && ((CropBlock) state.getBlock()).isMaxAge(state)))
            CropBlock.dropResources(state, worldIn, pos);
    };

    static DestroyBlockEffect extraHarvests(boolean melonOverload, float percentage, int maxBonusItems, Supplier<Item> itemDropped, Supplier<Block> harvestedBlock) {
        return (stack, worldIn, state, pos, entity) -> {
            if(state == harvestedBlock.get().defaultBlockState() && !worldIn.isClientSide) {
                int harvestCounter = 0;
                for(float i = entity.getRandom().nextFloat(); i <= percentage && harvestCounter < maxBonusItems; i = entity.getRandom().nextFloat()) {
                    ItemStack harvestItemStack = new ItemStack(itemDropped.get(), 1);
                    ItemEntity item = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), harvestItemStack);
                    worldIn.addFreshEntity(item);

                    harvestCounter++;
                }
                if(melonOverload && harvestCounter >= 9 && entity instanceof Player) {
                    Component message = new TranslatableComponent(stack.getDescriptionId() + ".message");
                    entity.sendMessage(message.copy().withStyle(ChatFormatting.GREEN, ChatFormatting.BOLD), Util.NIL_UUID);

                    entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1800, 3));
                    entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1800, 3));
                    entity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 1800, 3));

                    //CriteriaTriggersRigoranthus.KILL_A_LANGUID_DWELLER.trigger((ServerPlayer) entity);
                }
            }
        };
    }
}