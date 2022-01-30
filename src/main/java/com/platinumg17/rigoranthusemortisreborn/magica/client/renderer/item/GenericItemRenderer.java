package com.platinumg17.rigoranthusemortisreborn.magica.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.platinumg17.rigoranthusemortisreborn.magica.common.items.AnimBlockItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

import javax.annotation.Nullable;

public class GenericItemRenderer extends GeoItemRenderer<AnimBlockItem> {

    public boolean isTranslucent;
    public GenericItemRenderer(AnimatedGeoModel modelProvider) {
        super(new GenericItemModel(modelProvider));
    }

    public GenericItemRenderer withTranslucency(){
        this.isTranslucent = true;
        return this;
    }

    @Override
    public RenderType getRenderType(AnimBlockItem animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return this.isTranslucent ? RenderType.entityTranslucent(textureLocation) : super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}