package com.platinumg17.rigoranthusemortisreborn.core.registry.effects.weapons;

import com.platinumg17.rigoranthusemortisreborn.items.itemeffects.ItemRightClickEffect;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class PropulsionEffect implements ItemRightClickEffect {
    public static final PropulsionEffect SELF_PROPEL = new PropulsionEffect(3);//, EnumAspect.PROPEL);

    private final double velocity;
//    private final EnumAspect aspect;

    public PropulsionEffect(double velocity) {         //, EnumAspect aspect) {
        this.velocity = velocity;
//        this.aspect = aspect;
    }

    @Override
    public InteractionResultHolder<ItemStack> onRightClick(Level world, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        propulsionAction(player, itemStack, getVelocityMod(), hand);
        return InteractionResultHolder.pass(itemStack);
    }

    private double getVelocityMod() {return velocity;}

    void propulsionAction(Player player, ItemStack stack, double velocity, InteractionHand hand) {
//        Title title = null;
//        if(player.level.isClientSide) {
//            title = ClientPlayerData.getTitle();
//        if(player instanceof ServerPlayer) {
//            title = PlayerSavedData.getData((ServerPlayer) player).getTitle();
            if(player.getCooldowns().getCooldownPercent(stack.getItem(), 1F) <= 0 && (player.isCreative()))
                propulsionActionSound(player.level, player);
//        }

        if(player.isCreative()) {
            Vec3 lookVec = player.getLookAngle().scale(velocity);
            if(player.isFallFlying()) {
                lookVec = lookVec.scale(velocity / 12D);
            }
            player.push(lookVec.x, lookVec.y * 0.4D, lookVec.z);

            player.swing(hand, true);
            player.getCooldowns().addCooldown(stack.getItem(), 100);
            stack.hurtAndBreak(4, player, playerEntity -> playerEntity.broadcastBreakEvent(InteractionHand.MAIN_HAND));
        }
    }

    void propulsionActionSound(Level world, Player player) {
        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.TRIDENT_RIPTIDE_2, SoundSource.PLAYERS, 1.75F, 1.6F);
    }
}