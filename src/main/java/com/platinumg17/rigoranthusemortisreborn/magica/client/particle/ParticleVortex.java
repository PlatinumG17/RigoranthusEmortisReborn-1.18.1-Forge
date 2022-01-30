package com.platinumg17.rigoranthusemortisreborn.magica.client.particle;

import com.platinumg17.rigoranthusemortisreborn.magica.client.ClientInfo;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SpriteSet;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class ParticleVortex extends ParticleGlow {
    int randOffset;
    double randMotionOff;
    public ParticleVortex(ClientLevel worldIn, double x, double y, double z, double vx, double vy, double vz, float r, float g, float b, float a, float scale, int lifetime, SpriteSet sprite) {
        super(worldIn, x, y, z, vx, vy, vz, r, g, b, a, scale, lifetime, sprite, false);
        this.quadSize = scale/15f;
        initScale = scale/2.0f;
        this.lifetime = (int) ParticleUtil.inRange(25, 90);
        this.hasPhysics = false;
        randOffset = (int) ParticleUtil.inRange(300, 1000);
        randMotionOff = ParticleUtil.inRange(0.1, 0.5);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.yd -= 0.04D * (double)this.gravity;
            this.move(this.xd, this.yd, this.zd);
            this.xd *= 0.98F;
            this.yd *= 0.98F;
            this.zd *= 0.98F;
            if (this.onGround) {
                this.xd *= 0.7F;
                this.zd *= 0.7F;
            }
        }
        float radius = 0.5f;
        float lifeCoeff = (float)this.age/(float)this.lifetime;
        float reduc = 15f;
        this.xd =   randMotionOff * sin(ClientInfo.ticksInGame + randOffset)/(reduc);
        this.yd = -0.005;
        this.zd =   randMotionOff * cos(ClientInfo.ticksInGame + randOffset)/(reduc);

        this.quadSize = initScale-initScale*lifeCoeff;
        this.oRoll = roll;
    }
}