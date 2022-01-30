package com.platinumg17.rigoranthusemortisreborn.core.registry.effects.weapons;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class BunnyHopEffect implements RightClickBlockEffect, OnHitEffect {
    public static final BunnyHopEffect EFFECT_02 = new BunnyHopEffect(0.2);
    public static final BunnyHopEffect EFFECT_04 = new BunnyHopEffect(0.4);
    public static final BunnyHopEffect EFFECT_05 = new BunnyHopEffect(0.5);
    public static final BunnyHopEffect EFFECT_06 = new BunnyHopEffect(0.6);
    public static final BunnyHopEffect EFFECT_07 = new BunnyHopEffect(0.7);

    private final double hoppingMotion;

    public BunnyHopEffect(double hoppingMotion) {this.hoppingMotion = hoppingMotion;}

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity player) {hitEntity(stack, target, player, getHoppingMotion(stack));}
    private double getHoppingMotion(ItemStack stack){return hoppingMotion;}
    private static double addEfficiencyModifier(double hoppingMotion, ItemStack stack) {
        return hoppingMotion * ((EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, stack)*0.15)+1);
    }

    @Override
    public InteractionResult onClick(UseOnContext context) {
        return onItemUse(context.getPlayer(), context.getLevel(), context.getClickedPos(), context.getItemInHand(), context.getClickedFace(), getHoppingMotion(context.getItemInHand()));
    }

    private static void hitEntity(ItemStack stack, LivingEntity target, LivingEntity player, double hoppingMotion) {
        hoppingMotion = addEfficiencyModifier(hoppingMotion, stack);
        if (player.fallDistance > 0.0F && !player.isOnGround() && !player.onClimbable() && !player.isInWater() && !player.isPassenger()) {
            double knockbackModifier = 1D - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
            double targetMotionY = Math.max(target.getDeltaMovement().y, knockbackModifier * Math.min(hoppingMotion* 2, Math.abs(player.getDeltaMovement().y) + target.getDeltaMovement().y + hoppingMotion));
            target.setDeltaMovement(target.getDeltaMovement().x, targetMotionY, target.getDeltaMovement().z);
            player.setDeltaMovement(player.getDeltaMovement().x, 0, player.getDeltaMovement().z);
            player.fallDistance = 0;
        }
    }

    private static InteractionResult onItemUse(Player player, Level worldIn, BlockPos pos, ItemStack stack, Direction facing, double hoppingMotion) {
        hoppingMotion = addEfficiencyModifier(hoppingMotion, stack);
        if (worldIn.getBlockState(pos).getBlock() != Blocks.AIR) {
            double playerMotionX;
            double playerMotionY;
            double playerMotionZ;
            double velocity = Math.max(player.getDeltaMovement().y, Math.min(hoppingMotion * 2, Math.abs(player.getDeltaMovement().y) + hoppingMotion));
            final float HORIZONTAL_Y = 6f;
            switch (facing.getAxis())
            {
                case X:
                    velocity += Math.abs(player.getDeltaMovement().x) / 2;
                    playerMotionX = velocity * facing.getNormal().getX();
                    playerMotionY = velocity / HORIZONTAL_Y;
                    player.setDeltaMovement(playerMotionX, playerMotionY, player.getDeltaMovement().z);
                    break;
                case Y:
                    playerMotionY = velocity * facing.getNormal().getY();
                    player.setDeltaMovement(player.getDeltaMovement().x, playerMotionY, player.getDeltaMovement().z);
                    break;
                case Z:
                    velocity += Math.abs(player.getDeltaMovement().z) / 2;
                    playerMotionY = velocity / HORIZONTAL_Y;
                    playerMotionZ = velocity * facing.getNormal().getZ();
                    player.setDeltaMovement(player.getDeltaMovement().x, playerMotionY, playerMotionZ);
                    break;
            }
            player.fallDistance = 0;
            stack.hurtAndBreak(1, player, playerEntity -> playerEntity.broadcastBreakEvent(InteractionHand.MAIN_HAND));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}