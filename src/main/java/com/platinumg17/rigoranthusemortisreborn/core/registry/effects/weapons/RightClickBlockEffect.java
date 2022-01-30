package com.platinumg17.rigoranthusemortisreborn.core.registry.effects.weapons;

import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public interface RightClickBlockEffect {

    InteractionResult onClick(UseOnContext context);

    static RightClickBlockEffect placeFluid(Supplier<Block> fluidBlock, Supplier<Item> otherItem) {
        return (context) -> {
            Level worldIn = context.getLevel();
            Player player = context.getPlayer();
            ItemStack itemStack = context.getItemInHand();
            Direction facing = context.getClickedFace();
            BlockPos pos = context.getClickedPos().relative(facing);

            BlockState state = worldIn.getBlockState(pos);
            if(state.getBlock() == Blocks.AIR || state.getBlock() == fluidBlock.get()) {
                if(!worldIn.isClientSide && player != null) {
                    worldIn.setBlockAndUpdate(pos, fluidBlock.get().defaultBlockState());
                    ItemStack newItem = new ItemStack(otherItem.get(), itemStack.getCount());
                    newItem.setTag(itemStack.getTag()); //It is important that the item it is switching to has the same durability
                    player.setItemInHand(context.getHand(), newItem);
                    worldIn.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1F, 2F);
                    player.getCooldowns().addCooldown(otherItem.get(), 5);
                }
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        };
    }

    static RightClickBlockEffect scoopBlock(Supplier<Block> validBlock) {
        return (context) -> {
            Level worldIn = context.getLevel();
            BlockPos pos = context.getClickedPos();
            Player player = context.getPlayer();
            Direction facing = context.getClickedFace();
            boolean inside = context.isInside();

            BlockState state = worldIn.getBlockState(pos);
            BlockHitResult blockRayTrace = new BlockHitResult(context.getClickLocation(), facing, pos, inside);
            Item lookedAtBlockItem = state.getCloneItemStack(blockRayTrace, worldIn, pos, player).getItem();

            if(player != null && state.getBlock() == validBlock.get().defaultBlockState().getBlock()) {
                if(!worldIn.isClientSide) {
                    if(!player.inventory.add(new ItemStack(lookedAtBlockItem))) {
                        player.drop(new ItemStack(lookedAtBlockItem), false);
                    }
                    context.getItemInHand().hurtAndBreak(1, player, (playerEntity) -> playerEntity.broadcastBreakEvent(InteractionHand.MAIN_HAND));
                    worldIn.setBlockAndUpdate(blockRayTrace.getBlockPos(), Blocks.AIR.defaultBlockState());
                }
                worldIn.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, 1F, 1F);

                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        };
    }
}