package com.platinumg17.rigoranthusemortisreborn.entity.render.mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.platinumg17.rigoranthusemortisreborn.entity.mobs.SummonedCadaver;
import com.platinumg17.rigoranthusemortisreborn.entity.model.mobs.SummonedCadaverGeoModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class SummonedCadaverRenderer extends GeoEntityRenderer<SummonedCadaver> {

    public SummonedCadaverRenderer(EntityRendererProvider.Context manager) {
        super(manager, new SummonedCadaverGeoModel());
        this.shadowRadius = 0.5F;
    }
    @Override
    public RenderType getRenderType(SummonedCadaver animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }
}