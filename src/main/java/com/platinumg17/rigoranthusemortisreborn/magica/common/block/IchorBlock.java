package com.platinumg17.rigoranthusemortisreborn.magica.common.block;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.dominion.AbstractIchorTile;
import com.platinumg17.rigoranthusemortisreborn.core.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

public abstract class IchorBlock extends ModBlock {
    public IchorBlock(String registryName) {
        super(registryName);
    }

    public IchorBlock(Properties properties, String registry) {
        super(properties, registry);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if(!worldIn.isClientSide && handIn == InteractionHand.MAIN_HAND){
            if(worldIn.getBlockEntity(pos) instanceof AbstractIchorTile){
                AbstractIchorTile tile = (AbstractIchorTile) worldIn.getBlockEntity(pos);
                if(player.getItemInHand(handIn).getItem() == ItemInit.BUCKET_OF_CADAVEROUS_ICHOR.get()){
                    if(tile.getMaxIchor() - tile.getCurrentIchor() >= 1000){
                        tile.addIchor(1000);
                        player.level.playSound(null, player.blockPosition(), SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 1.0f, 1.0f);
                        if(!player.isCreative())
                            player.setItemInHand(handIn, new ItemStack(Items.BUCKET));
                    }
                    return super.use(state, worldIn, pos, player, handIn, hit);
                } else if (player.getItemInHand(handIn).getItem() instanceof BucketItem && ((BucketItem)player.getItemInHand(handIn).getItem()).getFluid() == Fluids.EMPTY) {
                    if(tile.getCurrentIchor() >= 1000){
                        if(player.getItemInHand(handIn).getCount() == 1) {
                            player.setItemInHand(handIn, new ItemStack((ItemInit.BUCKET_OF_CADAVEROUS_ICHOR.get()).asItem()));
                            player.level.playSound(null, player.blockPosition(), SoundEvents.BUCKET_FILL, SoundSource.PLAYERS, 1.0f, 1.0f);
                            tile.removeIchor(1000);
                        }
                        else if(player.addItem(new ItemStack((ItemInit.BUCKET_OF_CADAVEROUS_ICHOR.get()).asItem()))) {
                            player.level.playSound(null, player.blockPosition(), SoundEvents.BUCKET_FILL, SoundSource.PLAYERS, 1.0f, 1.0f);
                            player.getItemInHand(handIn).shrink(1);
                            tile.removeIchor(1000);
                        }
                    }else if(tile.getCurrentIchor() >= 1000 && player.getItemInHand(handIn).getCount() == 1){
                        player.level.playSound(null, player.blockPosition(), SoundEvents.BUCKET_FILL, SoundSource.PLAYERS, 1.0f, 1.0f);
                        tile.removeIchor(1000);
                        player.setItemInHand(player.getUsedItemHand(), new ItemStack((ItemInit.BUCKET_OF_CADAVEROUS_ICHOR.get()).asItem()));
                    }
                }
            }
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }
}