package com.platinumg17.rigoranthusemortisreborn.magica.client.particle;

import com.platinumg17.rigoranthusemortisreborn.core.registry.fluid.particles.EmortisParticleTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;

import java.util.Random;

public class ParticleLineData implements ParticleProvider<ColoredDynamicTypeData> {
    private final SpriteSet spriteSet;
    public static final String NAME = "line";

    public static Random random = new Random();
    public ParticleLineData(SpriteSet sprite) {
        this.spriteSet = sprite;
    }

    @Override
    public Particle createParticle(ColoredDynamicTypeData data, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        return new ParticleLine(worldIn, x,y,z,xSpeed, ySpeed, zSpeed, data.color.getRed(), data.color.getGreen(), data.color.getBlue(),
                data.scale,
                data.age,  this.spriteSet);

    }

    public static ParticleOptions createData(ParticleColor color) {
        return new ColoredDynamicTypeData(EmortisParticleTypes.LINE_TYPE, color, (float) ParticleUtil.inRange(0.05, 0.15), 40+random.nextInt(20));
    }

    public static ParticleOptions createData(ParticleColor color, float scale, int age) {
        return new ColoredDynamicTypeData(EmortisParticleTypes.LINE_TYPE, color, scale, age);
    }
}