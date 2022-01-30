package com.platinumg17.rigoranthusemortisreborn.entity.item;

import com.platinumg17.rigoranthusemortisreborn.core.init.ItemInit;
import com.platinumg17.rigoranthusemortisreborn.core.registry.RigoranthusSoundRegistry;
import com.platinumg17.rigoranthusemortisreborn.items.weapons.type.projectiles.basetype.IProjectileDamaging;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class ReturningProjectileEntity extends ThrowableItemProjectile {
    private int bounce;
    public int maxTick = 0;
    private int inBlockTicks = 0;
    private boolean noBlockCollision;

    public ReturningProjectileEntity(EntityType<? extends ReturningProjectileEntity> type, Level worldIn) {super(type, worldIn);}

    public ReturningProjectileEntity(EntityType<? extends ReturningProjectileEntity> type, double x, double y, double z, Level worldIn) {super(type, x, y, z, worldIn);}

    public ReturningProjectileEntity(EntityType<? extends ReturningProjectileEntity> type, LivingEntity livingEntityIn, Level worldIn, int maxTick, boolean noBlockCollision) {
        super(type, livingEntityIn, worldIn);
        this.maxTick = maxTick;
        this.noBlockCollision = noBlockCollision;
    }

    @Override
    protected void onHit(HitResult result) {
        int damage = IProjectileDamaging.getDamageFromItem(getItemFromItemStack().getItem());
//TODO onImpact does not trigger if already used within a couple ticks, allowing it to pass through blocks or entities
        if(result.getType() == HitResult.Type.ENTITY) {
            this.setDeltaMovement(getDeltaMovement().scale(-1.05));
            if(!level.isClientSide) {
                ++bounce;
                Entity entity = ((EntityHitResult) result).getEntity();

                if(entity != getOwner())
                    entity.hurt(DamageSource.thrown(this, getOwner()), damage);
                else {
                    resetThrower();
                }
            }
        } else if(result.getType() == HitResult.Type.BLOCK && !noBlockCollision) {
            BlockHitResult blockResult = (BlockHitResult) result;
            Direction blockFace = blockResult.getDirection();
            BlockPos blockPos = blockResult.getBlockPos();
            if(Block.canSupportCenter(level, blockPos, blockFace)) {
                this.setDeltaMovement(getDeltaMovement().scale(-1.05));
                if(!level.isClientSide) {
                    ++bounce;
                    this.level.playSound(null, this.getX(), this.getY(), this.getZ(), RigoranthusSoundRegistry.RAZORTOOTH_FRISBEE_HIT.get(), SoundSource.NEUTRAL, 0.6F, 4.0F);
                }
            }
            if(Block.canSupportCenter(level, blockPos, blockFace) && blockResult.isInside()) {
                ++inBlockTicks;
            }
        }
        if(bounce > 10) {
            resetThrower();
        }
    }

    public void resetThrower() {
        if(getOwner() instanceof Player) {
            ((Player)getOwner()).getCooldowns().addCooldown(getItemRaw().getItem(), 5);
            this.remove(RemovalReason.DISCARDED); //TODO find a better set of conditions to remove entity(ticksExisted?)
        }
    }

    public void tick() {
        Vec3 pos = position;
        this.xOld = pos.x;
        this.yOld = pos.y;
        this.zOld = pos.z;

        if(this.isInWater())
            this.setDeltaMovement(getDeltaMovement().scale(1.2));
        else
            this.setDeltaMovement(getDeltaMovement().scale(1.005));

        if(this.tickCount >= maxTick || inBlockTicks >= 2) {
            resetThrower();
        }
        super.tick();
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        bounce = compound.getInt("bounce");
        maxTick = compound.getInt("maxTick");
        inBlockTicks = compound.getInt("inBlockTicks");;
        noBlockCollision = compound.getBoolean("noBlockCollision");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("bounce", bounce);
        compound.putInt("maxTick", maxTick);
        compound.putInt("inBlockTicks", inBlockTicks);
        compound.putBoolean("noBlockCollision", noBlockCollision);
    }
    @Override public Packet<?> getAddEntityPacket() {return NetworkHooks.getEntitySpawningPacket(this);}

    @Override protected Item getDefaultItem() {return ItemInit.RAZORTOOTH_FRISBEE.get();}

    public ItemStack getItemFromItemStack() {
        ItemStack itemstack = this.getItemRaw();
        return itemstack.isEmpty() ? new ItemStack(this.getDefaultItem()) : itemstack;
    }
}
