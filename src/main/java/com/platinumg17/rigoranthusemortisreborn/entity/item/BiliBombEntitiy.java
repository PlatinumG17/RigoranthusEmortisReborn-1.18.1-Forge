package com.platinumg17.rigoranthusemortisreborn.entity.item;

import com.platinumg17.rigoranthusemortisreborn.core.init.ItemInit;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;

//@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
public class BiliBombEntitiy extends ThrowableItemProjectile/* implements IRendersAsItem*/ {
    private boolean shouldDestroy = true;

    public BiliBombEntitiy(EntityType<? extends BiliBombEntitiy> type, Level worldIn) {super(type, worldIn);}

    public BiliBombEntitiy(EntityType<? extends BiliBombEntitiy> type, double x, double y, double z, Level worldIn) {super(type, x, y, z, worldIn);}

    public BiliBombEntitiy(EntityType<? extends BiliBombEntitiy> type, LivingEntity livingEntityIn, Level worldIn, boolean shouldDestroy) {
        super(type, livingEntityIn, worldIn);
        this.shouldDestroy = shouldDestroy;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("shouldDestroy", shouldDestroy);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        shouldDestroy = compound.getBoolean("shouldDestroy");
    }

    @Override
    protected void onHit(HitResult result) {
        if(!this.level.isClientSide) {
            level.explode(null, result.getLocation().x, result.getLocation().y, result.getLocation().z, 3F, shouldDestroy ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE);
        }
        this.remove(Entity.RemovalReason.DISCARDED);
    }
    @Override
    public Packet<?> getAddEntityPacket() {return NetworkHooks.getEntitySpawningPacket(this);}

    @Override
    protected Item getDefaultItem() {return ItemInit.BILI_BOMB.get();}
}