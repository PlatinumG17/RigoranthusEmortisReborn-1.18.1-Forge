package com.platinumg17.rigoranthusemortisreborn.items.specialized;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class HangingItem extends Item {
    protected final EntityProvider provider;
    public HangingItem(EntityProvider provider, Properties properties) {
        super(properties);
        this.provider = provider;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        Direction facing = context.getClickedFace();
        Level worldIn = context.getLevel();
        BlockPos blockPos = context.getClickedPos().relative(facing);

        if (facing != Direction.DOWN && facing != Direction.UP
                && (context.getPlayer() == null || context.getPlayer().mayUseItemAt(blockPos, facing, stack))) {
            HangingEntity entityhanging = provider.createEntity(worldIn, blockPos, facing, stack);

            if (entityhanging != null && entityhanging.survives()) {
                if (!worldIn.isClientSide) {
                    entityhanging.playPlacementSound();
                    worldIn.addFreshEntity(entityhanging);
                }
                stack.shrink(1);
            }
            return InteractionResult.SUCCESS;
        }
        else {
            return InteractionResult.FAIL;
        }
    }

    public interface EntityProvider { HangingEntity createEntity(Level world, BlockPos pos, Direction facing, ItemStack stack);}
}