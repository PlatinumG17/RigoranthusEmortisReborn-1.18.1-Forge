package com.platinumg17.rigoranthusemortisreborn.magica.client.renderer.entity;

//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.RenderType;
//import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
//import net.minecraft.resources.ResourceLocation;
//import software.bernie.geckolib3.model.AnimatedGeoModel;
//import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
//
//import javax.annotation.Nullable;
//
//public class GenericRenderer extends GeoEntityRenderer {
//    public GenericRenderer(BlockEntityRendererProvider.Context renderManager, AnimatedGeoModel model){
//        super(renderManager, model);
//    }
//
//    @Override
//    public RenderType getRenderType(Object animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
//        return RenderType.entityCutoutNoCull(textureLocation);
//    }
//}