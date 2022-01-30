package com.platinumg17.rigoranthusemortisreborn.entity.render.mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.platinumg17.rigoranthusemortisreborn.entity.mobs.FeralCanisEntity;
import com.platinumg17.rigoranthusemortisreborn.entity.model.mobs.canis.FeralCanisGeoModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class FeralCanisRenderer extends GeoEntityRenderer<FeralCanisEntity> {
    public FeralCanisRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FeralCanisGeoModel());
        this.shadowRadius = 1.0F;
    }

    @Override
    public RenderType getRenderType(FeralCanisEntity animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }
}










//    private static final ResourceLocation TEXTURE_CANIS = RigoranthusEmortisReborn.rl("textures/entity/chordata.png");
//    private static final RenderType[] GLOW = new RenderType[2];
//
//    protected final List<GeoLayerRenderer<T>> layerRenderers = Lists.newArrayList();
//    private final AnimatedGeoModel<T> modelProvider;

//    public FeralCanisRenderer(EntityRenderDispatcher renderManager) {
//        super(renderManager, new FeralCanisGeoModel());
//        this.shadowRadius = 0.7F;
//        this.addLayer(new AbstractEyesLayer<CanisChordataEntity, CanisChordataModel>(this) {
//            @Override
//            public RenderType renderType() {return GLOW[0];}
//            @Override
//            public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, CanisChordataEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
//                VertexConsumer ivertexbuilder = vertexConsumers.getBuffer(GLOW[entity.getCanisEvolutionLevel()]);
//                this.getParentModel().renderToBuffer(matrices, ivertexbuilder, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
//            }
//        });
//    }

//    FeralCanisEntity feralCanis;
//    MultiBufferSource buffer;
//    ResourceLocation text;

//    @Override
//    protected void applyRotations(FeralCanisEntity entityLiving, PoseStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
//        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
//    }
//    @Override
//    public void renderEarly(FeralCanisEntity animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
//        this.feralCanis = animatable;
//        this.buffer = renderTypeBuffer;
//        this.text = this.getTextureLocation(animatable);
//        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
//    }

//    @Override
//    public void render(FeralCanisEntity entity, float entityYaw, float p_225623_3_, PoseStack matrixStack, MultiBufferSource iRenderTypeBuffer, int p_225623_6_) {
//        super.render(entity, entityYaw, p_225623_3_, matrixStack, iRenderTypeBuffer, p_225623_6_);
//    }
//    @Override
//    public void renderRecursively(GeoBone bone, PoseStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
//        if(bone.getName().equals("item")){
////            System.out.println(bone);
//            stack.pushPose();
//            RenderUtils.moveToPivot(bone, stack);
//            stack.translate(0, -0.10, 0);
//            stack.scale(0.75f, 0.75f, 0.75f);
////            ItemStack itemstack = feralCanis.getHeldStack();
////            Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemTransforms.TransformType.GROUND, packedLightIn, OverlayTexture.NO_OVERLAY, stack, this.buffer);
//            stack.popPose();
//            bufferIn = buffer.getBuffer(RenderType.entityCutoutNoCull(text));
//        }
//        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//    }



//    static {
//        GLOW[0] = RenderType.eyes(new ResourceLocation(RigoranthusEmortisReborn.MOD_ID, ("textures/entity/canis/canis_chordata_e.png")));
//    }
//    @Override
//    public void render(FeralCanisEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn,
//                       int packedLightIn) {
//        stack.pushPose();
//    EntityModelData entityModelData = new EntityModelData();
//    entityModelData.isCanis = entity.isCanis();
