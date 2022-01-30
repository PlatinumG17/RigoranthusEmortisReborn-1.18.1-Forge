package com.platinumg17.rigoranthusemortisreborn.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.platinumg17.rigoranthusemortisreborn.entity.DelphicBloomEntity;
import com.platinumg17.rigoranthusemortisreborn.entity.model.DelphicBloomModel;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class DelphicBloomRenderer extends GeoEntityRenderer<DelphicBloomEntity> {
    public DelphicBloomRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DelphicBloomModel());
    }

    @Override
    public RenderType getRenderType(DelphicBloomEntity animatable, float partialTicks, PoseStack stack, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}