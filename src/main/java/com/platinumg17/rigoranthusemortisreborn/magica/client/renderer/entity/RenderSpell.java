package com.platinumg17.rigoranthusemortisreborn.magica.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.GlowParticleData;
import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.ParticleUtil;
import com.platinumg17.rigoranthusemortisreborn.magica.common.entity.EntityProjectileSpell;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class RenderSpell extends EntityRenderer<EntityProjectileSpell> {
    private final ResourceLocation entityTexture;

    public RenderSpell(EntityRendererProvider.Context renderManagerIn, ResourceLocation entityTexture) {
        super(renderManagerIn);
        this.entityTexture = entityTexture;
    }

    @Override
    public void render(EntityProjectileSpell proj, float entityYaw, float partialTicks, PoseStack p_225623_4_, MultiBufferSource p_225623_5_, int p_225623_6_) {

        if(proj.age < 1 || true)
            return;
        double deltaX = proj.getX() - proj.xOld;
        double deltaY = proj.getY() - proj.yOld;
        double deltaZ = proj.getZ() - proj.zOld;
        deltaX = deltaX + proj.getDeltaMovement().x() * partialTicks%20;
        deltaY = deltaY + proj.getDeltaMovement().y() *partialTicks%20;
        deltaZ = deltaZ + proj.getDeltaMovement().z() *partialTicks%20;
        double dist = Math.ceil(Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) * 10);
        int counter = 0;

        for (double j = 0; j < dist; j++) {
            double coeff = j / dist;
            proj.level.addParticle(GlowParticleData.createData(proj.getParticleColor()), (float) (proj.xOld + deltaX * coeff),
                    (float) (proj.yOld + deltaY * coeff),
                    (float) (proj.zOld  + deltaZ * coeff),
                    0.0125f * ParticleUtil.inRange(-0.5, 0.5), 0.0125f * ParticleUtil.inRange(-0.5, 0.5), 0.0125f * ParticleUtil.inRange(-0.5, 0.5));
        }
    }

    @Override
    public ResourceLocation getTextureLocation(EntityProjectileSpell entity) {
        return this.entityTexture;
    }
}