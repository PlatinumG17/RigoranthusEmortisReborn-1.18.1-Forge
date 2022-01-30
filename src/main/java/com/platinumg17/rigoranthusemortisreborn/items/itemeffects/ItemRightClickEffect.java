package com.platinumg17.rigoranthusemortisreborn.items.itemeffects;

import com.platinumg17.rigoranthusemortisreborn.config.Config;
import com.platinumg17.rigoranthusemortisreborn.core.registry.RigoranthusSoundRegistry;
import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.ParticleUtil;
import com.platinumg17.rigoranthusemortisreborn.magica.common.entity.EntityFollowProjectile;
import com.platinumg17.rigoranthusemortisreborn.magica.common.entity.FireShotEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;

import java.util.List;
import java.util.function.Supplier;

public interface ItemRightClickEffect {

    ItemRightClickEffect ACTIVE_HAND = (world, player, hand) -> {
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    };

    static ItemRightClickEffect switchTo(Supplier<Item> otherItem) {
        return (world, player, hand) -> {
            ItemStack itemStackIn = player.getItemInHand(hand);
            if(player.isShiftKeyDown()) {
                ItemStack newItem = new ItemStack(otherItem.get(), itemStackIn.getCount());
                newItem.setTag(itemStackIn.getTag());

                return InteractionResultHolder.success(newItem);
            }
            return InteractionResultHolder.pass(itemStackIn);
        };
    }

    static ItemRightClickEffect shootFireball() {
        return (world, player, hand) -> {
            ItemStack itemStackIn = player.getItemInHand(hand);
            world.playSound(null, player.getX(), player.getY(), player.getZ(), RigoranthusSoundRegistry.FIREBALL.get(), SoundSource.PLAYERS, 1.2F, 1.0F);
            Vec3 vector3d = player.getViewVector(1.0F);

            if(!world.isClientSide) {

                FireShotEntity fireballentity = new FireShotEntity(world, player, player.xxa, player.yya, player.zza, 1);
                fireballentity.setPos(player.getX() + vector3d.x * 4.0D, player.getY(0.5D) + 0.5D, fireballentity.getZ() + vector3d.z * 4.0D);
                fireballentity.shootFromRotation(player, player.xRot, player.yRot, 0.0F, 2.5F, 1.0F);
                ParticleUtil.spawnPoof((ServerLevel) world, player.blockPosition().offset(player.getLookAngle().x + 0.6F, player.getLookAngle().y + 0.7F, player.getLookAngle().z + 0.6F));
                world.addFreshEntity(fireballentity);
                player.swing(hand, true);
                player.getCooldowns().addCooldown(itemStackIn.getItem(), 50);
                itemStackIn.hurtAndBreak(2, player, playerEntity -> playerEntity.broadcastBreakEvent(InteractionHand.MAIN_HAND));
//                EntityFollowProjectile aoeProjectile = new EntityFollowProjectile(world, player.blockPosition(), fireballentity.blockPosition(), 255, 0, 0);
//                world.addFreshEntity(aoeProjectile);
            }
            return InteractionResultHolder.pass(itemStackIn);
        };
    }

    static ItemRightClickEffect summonFireball() {
        return (world, player, hand) -> {
            ItemStack itemStackIn = player.getItemInHand(hand);
            world.playSound(null, player.getX(), player.getY(), player.getZ(), RigoranthusSoundRegistry.DESPERATE_CRIES.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1.0F, 0.8F);

            if(Config.enable_hellfire_rain.get()) {
                AABB axisalignedbb = player.getBoundingBox().inflate(32.0D, 32.0D, 32.0D);
                List<Mob> list = player.level.getEntitiesOfClass(Mob.class, axisalignedbb);
//            list.remove(player);
                if (!list.isEmpty() && !world.isClientSide) {
                    for (Mob livingentity : list) {
                        LargeFireball fireball = new LargeFireball(world, player, 0, -8.0, 0, 1);
                        fireball.setPos(livingentity.getX() + (player.getRandom().nextInt(6) - 3), livingentity.getY() + 40, livingentity.getZ() + (player.getRandom().nextInt(6) - 3));
                        player.swing(hand, true);
                        player.getCooldowns().addCooldown(itemStackIn.getItem(), 600);
                        itemStackIn.hurtAndBreak(2, player, playerEntity -> playerEntity.broadcastBreakEvent(InteractionHand.MAIN_HAND));
                        world.addFreshEntity(fireball);
                    }
                } else if (!world.isClientSide) {
                    LargeFireball fireball = new LargeFireball(world, player, 0, -8.0, 0, 1);
                    fireball.setPos(player.getX() + (player.getRandom().nextInt(20) - 10), player.getY() + 40, player.getZ() + (player.getRandom().nextInt(20) - 10));
                    player.swing(hand, true);
                    player.getCooldowns().addCooldown(itemStackIn.getItem(), 600);
                    itemStackIn.hurtAndBreak(2, player, playerEntity -> playerEntity.broadcastBreakEvent(InteractionHand.MAIN_HAND));
                    world.addFreshEntity(fireball);
                }
            }
            return InteractionResultHolder.pass(itemStackIn);
        };
    }

    static ItemRightClickEffect extinguishFire(int mod) {
        return (world, player, hand) -> {
            ItemStack itemStackIn = player.getItemInHand(hand);

            if(!world.isClientSide) {
                world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_DRAGON_FLAP, SoundSource.PLAYERS, 1.0F, 1.4F);

                for(BlockPos blockPos : BlockPos.betweenClosed(player.blockPosition().offset(2 * mod, mod, 2 * mod), player.blockPosition().offset(-2 * mod, -1 * mod, -2 * mod))) {
                    BlockState blockState = world.getBlockState(blockPos);
                    if(blockState.getBlock() == Blocks.FIRE) {
                        world.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
                        world.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 1.0F);
                    }
                }

                AABB axisalignedbb = player.getBoundingBox().inflate(2 * mod, mod, 2 * mod);
                List<Mob> list = player.level.getEntitiesOfClass(Mob.class, axisalignedbb);
                for(Mob livingentity : list) {
                    if(livingentity.getRemainingFireTicks() > 0) {
                        livingentity.clearFire();
                        world.playSound(null, livingentity.getX(), livingentity.getY(), livingentity.getZ(), SoundEvents.FIRE_EXTINGUISH, SoundSource.NEUTRAL, 0.5F, 1.0F);
                    }
                }
                player.swing(hand, true);
                player.getCooldowns().addCooldown(itemStackIn.getItem(), 15);
                itemStackIn.hurtAndBreak(1, player, playerEntity -> playerEntity.broadcastBreakEvent(InteractionHand.MAIN_HAND));
            }
            return InteractionResultHolder.pass(itemStackIn);
        };
    }

    static ItemRightClickEffect absorbFluid(Supplier<Block> fluidBlock, Supplier<Item> otherItem) {
        return (world, player, hand) -> {
            Vec3 eyePos = player.getEyePosition(1.0F);
            Vec3 lookVec = player.getLookAngle();
            BlockState state;
            ItemStack itemStack = player.getItemInHand(hand);
    //  Raytrace from players current eye position to their maximum reach distance
            for(int step = 0; step < player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue() * 10; step++) {
                Vec3 vecPos = eyePos.add(lookVec.scale(step / 10D));
                BlockPos blockPos = new BlockPos(vecPos);
                state = world.getBlockState(blockPos);
    //  May cause error if fed non-fluid "fluidBlock" parameter
                if(state.getBlock() == fluidBlock.get() && state.getFluidState().isSource()) {
                    world.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
                    ItemStack newItem = new ItemStack(otherItem.get(), itemStack.getCount());
                    newItem.setTag(itemStack.getTag()); //  It is important that the item it is switching to has the same durability
                    world.playSound(null, blockPos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1F, 2F);
                    player.getCooldowns().addCooldown(otherItem.get(), 5);
                    return InteractionResultHolder.success(newItem);
                }
            }
            return InteractionResultHolder.fail(itemStack);
        };
    }
    InteractionResultHolder<ItemStack> onRightClick(Level world, Player player, InteractionHand hand);
}