package com.platinumg17.rigoranthusemortisreborn.canis.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.interfaces.IThrowableItem;
import com.platinumg17.rigoranthusemortisreborn.canis.client.entity.model.CanisModel;
import com.platinumg17.rigoranthusemortisreborn.canis.client.entity.render.layer.geolayers.CanisArmorModel;
import com.platinumg17.rigoranthusemortisreborn.canis.client.entity.render.layer.geolayers.CanisShadesModel;
import com.platinumg17.rigoranthusemortisreborn.canis.client.entity.render.layer.geolayers.SaddleModel;
import com.platinumg17.rigoranthusemortisreborn.canis.client.entity.render.layer.geolayers.SatchelModel;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.accouterments.CanisAccouterments;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.Resources;
import com.platinumg17.rigoranthusemortisreborn.config.Config;
import com.platinumg17.rigoranthusemortisreborn.magica.client.renderer.entity.ModelLayerRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.util.RenderUtils;

import javax.annotation.Nullable;

import static com.platinumg17.rigoranthusemortisreborn.canis.common.lib.Resources.COLLAR_WHITE;
import static com.platinumg17.rigoranthusemortisreborn.canis.common.lib.Resources.TAME_CANIS_TEXTURE;

public class CanisRenderer extends GeoEntityRenderer<CanisEntity> {

    public CanisRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CanisModel());
        this.addLayer(new ModelLayerRenderer(this, new CanisArmorModel(this.getGeoModelProvider())));
//        this.addLayer(new TransparentModelLayerRenderer(this, new CanisShadesModel(this.getGeoModelProvider())));
        this.addLayer(new ModelLayerRenderer(this, new CanisShadesModel(this.getGeoModelProvider())));
        this.addLayer(new ModelLayerRenderer(this, new SatchelModel(this.getGeoModelProvider())));
        this.addLayer(new ModelLayerRenderer(this, new SaddleModel(this.getGeoModelProvider())));
        this.shadowRadius = 0.7F;
    }
    CanisEntity canis;
    MultiBufferSource buffer;
    ResourceLocation text;

    private float r;
    private float g;
    private float b;

    public void setColor(float red, float green, float blue) {
        this.r = red;
        this.g = green;
        this.b = blue;
    }

    @Override
    protected void applyRotations(CanisEntity entityLiving, PoseStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    public void renderEarly(CanisEntity animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        this.canis = animatable;
        this.buffer = renderTypeBuffer;
        this.text = this.getTextureLocation(animatable);
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public void render(CanisEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        if (canis.isCanisWet()) {
            float f = canis.getShadingWhileWet(partialTicks);
            this.setColor(f, f, f);
        }
        if (this.shouldShowName(entityIn) && !entityIn.canBeControlledByRider() && !entityIn.isVehicle()) {
            double d0 = this.entityRenderDispatcher.distanceToSqr(entityIn);
            if (d0 <= 64 * 64) {
                String tip = entityIn.getMode().getTip();
                String label = String.format(Config.CANIS_GENDER.get() ? "%s[%d]%s" : "%s[%d]",(new TranslatableComponent(tip)).getString(), Mth.ceil(entityIn.getCanisHunger()), (new TranslatableComponent(entityIn.getGender().getUnlocalisedTip())).getString());
                RenderUtil.renderLabelWithScale(entityIn, this, entityRenderDispatcher, label, matrixStackIn, bufferIn, packedLightIn, 0.01F, 0.12F);
                if (d0 <= 5 * 5 && this.entityRenderDispatcher.camera.getEntity().isShiftKeyDown()) {
                    RenderUtil.renderLabelWithScale(entityIn, this, entityRenderDispatcher, entityIn.getOwnersName().orElseGet(() -> this.getNameUnknown(entityIn)), matrixStackIn, bufferIn, packedLightIn, 0.01F, -0.25F);
                }
            }
        }
    }

    @Override
    public void renderRecursively(GeoBone bone, PoseStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {

        if (canis.hasBone()) {
            if (bone.getName().equals("jaw")) {
                stack.pushPose();
                RenderUtils.moveToPivot(bone, stack);
                stack.translate(-0.1F, 0.05F, -0.22F);
                stack.mulPose(Vector3f.YP.rotationDegrees(45.0F));
                stack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
                IThrowableItem throwableItem = canis.getThrowableItem();
                Minecraft.getInstance().getItemInHandRenderer().renderItem(canis, throwableItem != null ? throwableItem.getRenderStack(canis.getBoneVariant()) : canis.getBoneVariant(), ItemTransforms.TransformType.GROUND, false, stack, this.buffer, packedLightIn);
                stack.popPose();
                bufferIn = buffer.getBuffer(RenderType.entityTranslucent(text));
            }
        }
        if (bone.getName().equals("tail")) {//(1.55F - (this.getMaxHealth() - (this.getHealth())) * 0.02F) * (float)Math.PI
            stack.pushPose();
            bone.setRotationX((1.55F - (canis.getMaxHealth() - (canis.getHealth())) * 0.02F) * (float)Math.PI);
            bone.setPositionZ(Mth.cos(canis.moveDist * 0.6662F) * 1.4F);
//            RenderUtils.moveToPivot(bone, stack);
            stack.popPose();
            bufferIn = buffer.getBuffer(RenderType.entityCutoutNoCull(text));
        }
        if (bone.getName().equals("shades")) {//(1.55F - (this.getMaxHealth() - (this.getHealth())) * 0.02F) * (float)Math.PI
            stack.pushPose();
            RenderUtils.moveToPivot(bone, stack);
            stack.popPose();
            bufferIn = buffer.getBuffer(RenderType.entityCutoutNoCull(text));
        }
        if (bone.getName().equals("armor")) {
            stack.pushPose();
            RenderUtils.moveToPivot(bone, stack);
            stack.popPose();
            bufferIn = buffer.getBuffer(RenderType.entityCutoutNoCull(text));
        }
        if (bone.getName().equals("satchel")) {
            stack.pushPose();
            RenderUtils.moveToPivot(bone, stack);
            stack.popPose();
            bufferIn = buffer.getBuffer(RenderType.entityCutoutNoCull(text));
        }
        if (bone.getName().equals("saddle")) {
//            if (bone.getName().equals("mane_rotation3")) {
            stack.pushPose();
            RenderUtils.moveToPivot(bone, stack);
            stack.popPose();
            bufferIn = buffer.getBuffer(RenderType.entityCutoutNoCull(text));
//            }
        }
//        if (bone.getName().equals("saddle")) {
//            if (bone.getName().equals("mane_rotation3")) {
//                stack.pushPose();
//                RenderUtils.moveToPivot(bone, stack);
//                stack.popPose();
//                bufferIn = buffer.getBuffer(RenderType.entityCutoutNoCull(text));
//            }
//        }
        if (canis.isCanisWet()) {
//            float f = canis.getShadingWhileWet(120);
            this.setColor(red = this.r * red, green = this.g * green, blue = this.b * blue);
        }
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    public ResourceLocation getColor(CanisEntity e) {
        String color = e.getEntityData().get(CanisEntity.COLOR).toLowerCase();
        if(color.isEmpty())
            return COLLAR_WHITE;
        return Resources.getCanisCollar(color);
    }

    @Override
    public ResourceLocation getTextureLocation(CanisEntity entity) {
        return entity.getAccoutrement(CanisAccouterments.DYEABLE_COLLAR.get()).isPresent() ? getColor(entity) : TAME_CANIS_TEXTURE;
    }

    private Component getNameUnknown(CanisEntity canisIn) {
        return new TranslatableComponent(canisIn.getOwnerUUID() != null ? "entity.rigoranthusemortisreborn.canis.unknown_owner" : "entity.rigoranthusemortisreborn.canis.untamed");
    }

    @Override
    public RenderType getRenderType(CanisEntity animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(textureLocation);
    }
}