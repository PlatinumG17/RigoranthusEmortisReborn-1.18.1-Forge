package com.platinumg17.rigoranthusemortisreborn.entity.render.mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.platinumg17.rigoranthusemortisreborn.entity.mobs.NecrawFasciiEntity;
import com.platinumg17.rigoranthusemortisreborn.entity.model.mobs.NecrawFasciiGeoModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class NecrawFasciiRenderer extends GeoEntityRenderer<NecrawFasciiEntity> {

    public NecrawFasciiRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new NecrawFasciiGeoModel());
        this.shadowRadius = 0.7F;
    }

    @Override
    public RenderType getRenderType(NecrawFasciiEntity animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }
}