package com.platinumg17.rigoranthusemortisreborn.util;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

public class MagicHitTargetUtil {
    public enum Type {
        GREEN(() -> (ParticleOptions) ParticleTypes.DUST, false, false),
        CRIT(() -> ParticleTypes.ENCHANTED_HIT, false, false),
        ENCHANT(() -> ParticleTypes.ENCHANT, false, true),
        RED(() -> (ParticleOptions) ParticleTypes.DUST, false, false),
        INK(() -> ParticleTypes.SQUID_INK, true, false),
        CIRCEAN(() -> (ParticleOptions) ParticleTypes.DUST, true, false),
        APOGEE(() -> ParticleTypes.END_ROD, true, false);

        private final Supplier<ParticleOptions> particle;
        private final boolean explosiveFinish;
        private final boolean extraParticles;

        Type(Supplier<ParticleOptions> particle, boolean explosiveFinish, boolean extraParticles) {
            this.particle = particle;
            this.explosiveFinish = explosiveFinish;
            this.extraParticles = extraParticles;
        }

        public int toInt() {return ordinal();}

        public static Type fromInt(int i) {
            if(i >= 0 && i < values().length)
                return values()[i];
            else return ENCHANT;
        }
    }

    public static void particleEffect(Type type, ClientLevel world, Vec3 pos, Vec3 lookVector, int length, boolean collides) {
        particleEffect(type.particle.get(), type.explosiveFinish, type.extraParticles, world, pos, lookVector, length, collides);
    }

    public static void particleEffect(ParticleOptions particle, boolean explosiveFinish, boolean extraParticles, ClientLevel world, Vec3 pos, Vec3 lookVector, int length, boolean collides) {
        for(int step = 0; step <= length; step++) {
            pathParticles(particle, extraParticles, world, pos.add(lookVector.scale(step / 2D)), step);

            if(collides && step == length) {
                // uses the vector to a prior position before it was inside a block/entity so that the flash particle is not obscured and particles can fly out
                collisionEffect(particle, explosiveFinish, world, pos.add(lookVector.scale((step - 1) / 2D)));
            }
        }
    }

    private static void pathParticles(ParticleOptions particle, boolean extraParticles, ClientLevel world, Vec3 vecPos, int i) {
        // starts particle trail along vector path after a few runs, its away from the players vision so not to obscure everything
        if(i >= 5) {
            float offsetX = (world.random.nextFloat() - .5F) / 4;
            float offsetY = (world.random.nextFloat() - .5F) / 4;
            float offsetZ = (world.random.nextFloat() - .5F) / 4;
            world.addParticle(particle, true, vecPos.x + offsetX, vecPos.y + offsetY, vecPos.z + offsetZ, 0.0D, 0.0D, 0.0D);
        //  increases particle visibility
            if(extraParticles) {
                for(float a = 0; a < 4; a++) {
                    float extraOffsetX = (world.random.nextFloat() - .5F) / 5;
                    float extraOffsetY = (world.random.nextFloat() - .5F) / 5;
                    float extraOffsetZ = (world.random.nextFloat() - .5F) / 5;
                    world.addParticle(particle, true, vecPos.x + extraOffsetX, vecPos.y + extraOffsetY, vecPos.z + extraOffsetZ, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    private static void collisionEffect(ParticleOptions particle, boolean explosiveFinish, ClientLevel world, Vec3 vecPos) {
        int particles = 25 + world.random.nextInt(10);
        //   if  explosiveFinish = true : adds a sound + flash
        if(explosiveFinish) {
            world.playLocalSound(vecPos.x, vecPos.y, vecPos.z, SoundEvents.SHULKER_BULLET_HIT, SoundSource.BLOCKS, 1.2F, 0.6F, false);
            world.addParticle(ParticleTypes.FLASH, vecPos.x, vecPos.y, vecPos.z, 0.0D, 0.0D, 0.0D);
            for(int a = 0; a < particles; a++)
                world.addParticle(particle, true, vecPos.x, vecPos.y, vecPos.z, world.random.nextGaussian() * 0.12D, world.random.nextGaussian() * 0.12D, world.random.nextGaussian() * 0.12D);
        }
        //   if  explosiveFinish = false : adds a small particle effect
        else {
            for(int a = 0; a < particles; a++)
                world.addParticle(ParticleTypes.CRIT, true, vecPos.x, vecPos.y, vecPos.z, world.random.nextGaussian(), world.random.nextGaussian(), world.random.nextGaussian());
        }
    }
}
