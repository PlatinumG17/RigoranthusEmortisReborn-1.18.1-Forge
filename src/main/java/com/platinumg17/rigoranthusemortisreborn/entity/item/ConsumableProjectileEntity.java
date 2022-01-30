package com.platinumg17.rigoranthusemortisreborn.entity.item;

import com.platinumg17.rigoranthusemortisreborn.core.init.ItemInit;
import com.platinumg17.rigoranthusemortisreborn.items.weapons.type.projectiles.basetype.IProjectileDamaging;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;

public class ConsumableProjectileEntity extends ThrowableItemProjectile {
    public ConsumableProjectileEntity(EntityType<? extends ConsumableProjectileEntity> type, Level worldIn) {super(type, worldIn);}

    public ConsumableProjectileEntity(EntityType<? extends ConsumableProjectileEntity> type, double x, double y, double z, Level worldIn) {super(type, x, y, z, worldIn);}

    public ConsumableProjectileEntity(EntityType<? extends ConsumableProjectileEntity> type, LivingEntity livingEntityIn, Level worldIn) {super(type, livingEntityIn, worldIn);}

    private static boolean isNonCreativePlayer(Entity entity) {return entity instanceof Player && !((Player) entity).isCreative();}

    @Override
    protected void onHit(HitResult result) {
        int damage = IProjectileDamaging.getDamageFromItem(getItemFromItemStack().getItem());

        if(result.getType() == HitResult.Type.ENTITY) {
            Entity entity = ((EntityHitResult) result).getEntity();
                entity.hurt(DamageSource.thrown(this, getOwner()), damage);
        }
        if(isNonCreativePlayer(getOwner())) {
            if(random.nextFloat() < 0.99F) {
                ItemEntity itemEntity = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), this.getItemFromItemStack());
                level.addFreshEntity(itemEntity);
            }
            else {this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ITEM_BREAK, SoundSource.NEUTRAL, 0.8F, 1.5F);}
        }
        this.remove(RemovalReason.DISCARDED);
    }
    @Override
    public Packet<?> getAddEntityPacket() {return NetworkHooks.getEntitySpawningPacket(this);}

    @Override
    protected Item getDefaultItem() {
        return ItemInit.RAZORTOOTH_KUNAI.get();
    }

    public ItemStack getItemFromItemStack() {
        ItemStack itemstack = this.getItemRaw();
        return itemstack.isEmpty() ? new ItemStack(this.getDefaultItem()) : itemstack;
    }
}