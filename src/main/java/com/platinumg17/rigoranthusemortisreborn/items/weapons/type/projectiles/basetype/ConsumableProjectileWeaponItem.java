package com.platinumg17.rigoranthusemortisreborn.items.weapons.type.projectiles.basetype;

import com.platinumg17.rigoranthusemortisreborn.canis.common.SpecializedEntityTypes;
import com.platinumg17.rigoranthusemortisreborn.entity.item.ConsumableProjectileEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ConsumableProjectileWeaponItem extends Item implements IProjectileDamaging {
    public final float velocity;
    public final float accuracy;
    public final int damage;

    public ConsumableProjectileWeaponItem(Item.Properties properties, float velocity, float accuracy, int damage) {
        super(properties);
        this.velocity = velocity;
        this.accuracy = accuracy;
        this.damage = damage;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack item = playerIn.getItemInHand(handIn);
        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 0.8F, 1.5F);
        if(!worldIn.isClientSide) {
            ConsumableProjectileEntity projectileEntity = new ConsumableProjectileEntity(SpecializedEntityTypes.CONSUMABLE_PROJECTILE.get(), playerIn, worldIn);
            projectileEntity.setItem(item);
            projectileEntity.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, velocity, accuracy);
            worldIn.addFreshEntity(projectileEntity);
        }
        if(!playerIn.isCreative()) {item.shrink(1);}

        playerIn.getCooldowns().addCooldown(this, 8);
        playerIn.awardStat(Stats.ITEM_USED.get(this));
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, item);
    }
    @Override
    public int getProjectileDamage() {return damage;}
}
