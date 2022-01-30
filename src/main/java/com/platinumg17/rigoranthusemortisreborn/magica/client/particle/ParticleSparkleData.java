package com.platinumg17.rigoranthusemortisreborn.magica.client.particle;

import com.platinumg17.rigoranthusemortisreborn.core.registry.fluid.particles.EmortisParticleTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;

import java.util.Random;

public class ParticleSparkleData implements ParticleProvider<ColoredDynamicTypeData> {
    private final SpriteSet spriteSet;
    public static final String NAME = "sparkle";

    public static final Random random = new Random();
    public ParticleSparkleData(SpriteSet sprite) {
        this.spriteSet = sprite;
    }

    @Override
    public Particle createParticle(ColoredDynamicTypeData data, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        return new ParticleSparkle(worldIn, x,y,z,xSpeed, ySpeed, zSpeed, data.color.getRed(), data.color.getGreen(), data.color.getBlue(),
                data.scale,
                data.age,  this.spriteSet);
    }

    public static ParticleOptions createData(ParticleColor color) {
        return new ColoredDynamicTypeData(EmortisParticleTypes.SPARKLE_TYPE, color,.25f,  36);
    }

    public static ParticleOptions createData(ParticleColor color, float scale, int age) {
        return new ColoredDynamicTypeData(EmortisParticleTypes.SPARKLE_TYPE, color, scale, age);
    }
}