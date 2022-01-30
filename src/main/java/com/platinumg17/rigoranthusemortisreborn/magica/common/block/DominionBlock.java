package com.platinumg17.rigoranthusemortisreborn.magica.common.block;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.dominion.AbstractDominionTile;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.MagicItemsRegistry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;

public abstract class DominionBlock extends TickableModBlock {
    public DominionBlock(String registryName) {
        super(registryName);
    }

    public DominionBlock(Properties properties, String registry) {
        super(properties, registry);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if(!worldIn.isClientSide && handIn == InteractionHand.MAIN_HAND){
            if(worldIn.getBlockEntity(pos) instanceof AbstractDominionTile){
                AbstractDominionTile tile = (AbstractDominionTile) worldIn.getBlockEntity(pos);
                if(player.getItemInHand(handIn).getItem() == MagicItemsRegistry.bucketOfDominion){
                    if(tile.getMaxDominion() - tile.getCurrentDominion() >= 1000){
                        tile.addDominion(1000);
                        player.level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 1.0f, 1.0f);
                        if(!player.isCreative())
                            player.setItemInHand(handIn, new ItemStack(Items.BUCKET));
                    }
                    return super.use(state, worldIn, pos, player, handIn, hit);
                }else if(player.getItemInHand(handIn).getItem() instanceof BucketItem && ((BucketItem)player.getItemInHand(handIn).getItem()).getFluid() == Fluids.EMPTY){
                    if(tile.getCurrentDominion() >= 1000) {
                        if(player.getItemInHand(handIn).getCount() == 1) {
                            player.setItemInHand(handIn, new ItemStack(MagicItemsRegistry.bucketOfDominion));
                            player.level.playSound(null, player.blockPosition(), SoundEvents.BUCKET_FILL, SoundSource.PLAYERS, 1.0f, 1.0f);
                            tile.removeDominion(1000);
                        }else if(player.addItem(new ItemStack(MagicItemsRegistry.bucketOfDominion))) {
                            player.level.playSound(null, player.blockPosition(), SoundEvents.BUCKET_FILL, SoundSource.PLAYERS, 1.0f, 1.0f);
                            player.getItemInHand(handIn).shrink(1);
                            tile.removeDominion(1000);
                        }
                    }else if(tile.getCurrentDominion() >= 1000 && player.getItemInHand(handIn).getCount() == 1){
                        player.level.playSound(null, player.blockPosition(), SoundEvents.BUCKET_FILL, SoundSource.PLAYERS, 1.0f, 1.0f);
                        tile.removeDominion(1000);
                        player.setItemInHand(player.getUsedItemHand(), new ItemStack(MagicItemsRegistry.bucketOfDominion));
                    }
                }
            }
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }
}