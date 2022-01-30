package com.platinumg17.rigoranthusemortisreborn.magica.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.platinumg17.rigoranthusemortisreborn.magica.common.entity.familiar.FamiliarCadaver;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

import static com.platinumg17.rigoranthusemortisreborn.canis.common.lib.Resources.*;

public class FamiliarCadaverRenderer extends GeoEntityRenderer<FamiliarCadaver> {

    public FamiliarCadaverRenderer(EntityRendererProvider.Context manager) {super(manager, new FamiliarCadaverModel());}

    @Override
    protected void applyRotations(FamiliarCadaver entityLiving, PoseStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
            super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    public void render (FamiliarCadaver entity,float entityYaw, float p_225623_3_, PoseStack matrixStack, MultiBufferSource iRenderTypeBuffer, int p_225623_6_) {
            super.render(entity, entityYaw, p_225623_3_, matrixStack, iRenderTypeBuffer, p_225623_6_);
    }

    public ResourceLocation getColor (FamiliarCadaver e) { return CADAVER_TEXTURE; }

    @Override
    public ResourceLocation getTextureLocation (FamiliarCadaver entity){
            return CADAVER_TEXTURE;
    }

    @Override
    public RenderType getRenderType (FamiliarCadaver animatable, float partialTicks, PoseStack
            stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation){
            return RenderType.entityCutoutNoCull(textureLocation);
    }

    public static class FamiliarCadaverModel extends AnimatedGeoModel<FamiliarCadaver> {

        @Override
        public void setLivingAnimations(FamiliarCadaver entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
            super.setLivingAnimations(entity, uniqueID, customPredicate);
            IBone head = this.getAnimationProcessor().getBone("head");
            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            head.setRotationX(extraData.headPitch * 0.017453292F);
            head.setRotationY(extraData.netHeadYaw * 0.017453292F);
        }
        @Override public ResourceLocation getModelLocation(FamiliarCadaver cadaver) { return CADAVER_MODEL; }
        @Override public ResourceLocation getTextureLocation(FamiliarCadaver cadaver) {
            return CADAVER_TEXTURE;
        }
        @Override public ResourceLocation getAnimationFileLocation(FamiliarCadaver cadaver) { return CADAVER_ANIMATION; }
    }
}