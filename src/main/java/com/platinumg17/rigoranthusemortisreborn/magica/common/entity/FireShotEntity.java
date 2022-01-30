package com.platinumg17.rigoranthusemortisreborn.magica.common.entity;

import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.GlowParticleData;
import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.ParticleColor;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.MagicItemsRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class FireShotEntity extends LargeFireball {
    private int age;
    int maxAge = 60;

    public FireShotEntity(Level world, LivingEntity entity, double xD, double yD, double zD, int n) {
        super(world, entity, xD, yD, zD, n);
    }

    public FireShotEntity(EntityType<? extends LargeFireball> fireShot, Level world) {
        super(fireShot, world);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        if (!this.level.isClientSide) {
            Entity entity = pResult.getEntity();
            Entity entity1 = this.getOwner();
            if (entity != entity1) {
                super.onHitEntity(pResult);
//                entity.hurt(DamageSource.fireball(this, entity1), 6.0F);
            }
        }
    }

    @Override
    protected @NotNull ItemStack getItemRaw() {
        return MagicItemsRegistry.FIRE_SHOT.getStack();
    }

    @Override
    public @NotNull ItemStack getItem() {
        ItemStack itemstack = MagicItemsRegistry.FIRE_SHOT.getStack();
        return itemstack.isEmpty() ? new ItemStack(MagicItemsRegistry.FIRE_SHOT.asItem()) : itemstack;
    }

    public void tick() {
        this.age++;
        if(level.isClientSide && this.age > 1) {
            double deltaX = getX() - xOld;
            double deltaY = getY() - yOld;
            double deltaZ = getZ() - zOld;
            double dist = Math.ceil(Math.sqrt(deltaX*deltaX+deltaY*deltaY+deltaZ*deltaZ) * 20);
            int counter = 0;
            for (double i = 0; i < dist; i ++){
                double coeff = i/dist;
                counter += level.random.nextInt(2);
                if (counter % (Minecraft.getInstance().options.particles.getId() == 0 ? 1 : 2 * Minecraft.getInstance().options.particles.getId()) == 0) {
                    level.addParticle(GlowParticleData.createData(new ParticleColor(255, 0, 0)),
                            (float) (xo + deltaX * coeff), (float) (yo + deltaY * coeff), (float) (zo + deltaZ * coeff), 0.0125f * (random.nextFloat() - 0.5f), 0.0125f * (random.nextFloat() - 0.5f), 0.0125f * (random.nextFloat() - 0.5f));
                }
            }
        }
        super.tick();
        if(age > maxAge) {
            this.discard();
        }
    }

    @Override
    public void baseTick() {
        super.baseTick();
    }
//    @Override
//    public Packet<?> getAddEntityPacket() {
//        return NetworkHooks.getEntitySpawningPacket(this);
//    }
//
//    public FireShotEntity(PlayMessages.SpawnEntity packet, Level world){
//        super(EntityType.FIREBALL, world);
//    }
//
//    @Override
//    public EntityType<?> getType() {
//        return EntityType.FIREBALL;
//    }
}