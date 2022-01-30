package com.platinumg17.rigoranthusemortisreborn.magica.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.platinumg17.rigoranthusemortisreborn.magica.common.entity.EntityEminentialProjection;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.phys.Vec3;

public class EminentialProjectionRenderer extends LivingEntityRenderer<EntityEminentialProjection, PlayerModel<EntityEminentialProjection>> {

    public EminentialProjectionRenderer(EntityRendererProvider.Context entity) {
        this(entity, false);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityEminentialProjection entity) {
        return entity.getSkinTextureLocation();
    }

    public EminentialProjectionRenderer(EntityRendererProvider.Context manager, boolean p_i46103_2_) {
        super(manager, new PlayerModel(manager.bakeLayer(ModelLayers.PLAYER_SLIM), p_i46103_2_), 0.5F);
    }

    public void render(EntityEminentialProjection entity, float lightIn, float overlayIn, PoseStack matrix, MultiBufferSource renderTypeBuf, int scale) {
        this.setModelProperties(entity);
        super.render(entity, lightIn, overlayIn, matrix, renderTypeBuf, scale);
    }

    public Vec3 getRenderOffset(EntityEminentialProjection entity, float offset) {
        return entity.isCrouching() ? new Vec3(0.0D, -0.125D, 0.0D) : super.getRenderOffset(entity, offset);
    }

    private void setModelProperties(EntityEminentialProjection entity) {
        PlayerModel<EntityEminentialProjection> playermodel = this.getModel();
        if (entity.isSpectator()) {
            playermodel.setAllVisible(false);
            playermodel.head.visible = true;
            playermodel.hat.visible = true;
        } else {
            playermodel.setAllVisible(true);
            playermodel.crouching = entity.isCrouching();
            PlayerModel.ArmPose bipedmodel$armpose = getArmPose(entity, InteractionHand.MAIN_HAND);
            PlayerModel.ArmPose bipedmodel$armpose1 = getArmPose(entity, InteractionHand.OFF_HAND);
            if (bipedmodel$armpose.isTwoHanded()) {
                bipedmodel$armpose1 = entity.getOffhandItem().isEmpty() ? PlayerModel.ArmPose.EMPTY : PlayerModel.ArmPose.ITEM;
            }
            if (entity.getMainArm() == HumanoidArm.RIGHT) {
                playermodel.rightArmPose = bipedmodel$armpose;
                playermodel.leftArmPose = bipedmodel$armpose1;
            } else {
                playermodel.rightArmPose = bipedmodel$armpose1;
                playermodel.leftArmPose = bipedmodel$armpose;
            }
        }
    }

    private static PlayerModel.ArmPose getArmPose(EntityEminentialProjection entity, InteractionHand handIn) {
        ItemStack itemstack = entity.getItemInHand(handIn);
        if (itemstack.isEmpty()) {
            return PlayerModel.ArmPose.EMPTY;
        } else {
            if (entity.getUsedItemHand() == handIn && entity.getUseItemRemainingTicks() > 0) {
                UseAnim useaction = itemstack.getUseAnimation();
                if (useaction == UseAnim.BLOCK) {
                    return PlayerModel.ArmPose.BLOCK;
                }
                if (useaction == UseAnim.BOW) {
                    return PlayerModel.ArmPose.BOW_AND_ARROW;
                }
                if (useaction == UseAnim.SPEAR) {
                    return PlayerModel.ArmPose.THROW_SPEAR;
                }
                if (useaction == UseAnim.CROSSBOW && handIn == entity.getUsedItemHand()) {
                    return PlayerModel.ArmPose.CROSSBOW_CHARGE;
                }
            } else if (!entity.swinging && itemstack.getItem() == Items.CROSSBOW && CrossbowItem.isCharged(itemstack)) {
                return PlayerModel.ArmPose.CROSSBOW_HOLD;
            }
            return PlayerModel.ArmPose.ITEM;
        }
    }

    protected void scale(EntityEminentialProjection entity, PoseStack matrix, float num) {
        float f = 0.9375F;
        matrix.scale(0.9375F, 0.9375F, 0.9375F);
    }

    protected void renderNameTag(EntityEminentialProjection entity, Component name, PoseStack matrix, MultiBufferSource renderTypeBuf, int size) {
        double d0 = this.entityRenderDispatcher.distanceToSqr(entity);
        matrix.pushPose();

        super.renderNameTag(entity, name, matrix, renderTypeBuf, size);
        matrix.popPose();
    }

    public void renderRightHand(PoseStack matrix, MultiBufferSource renderType, int scale, EntityEminentialProjection entity) {
        this.renderHand(matrix, renderType, scale, entity, (this.model).rightArm, (this.model).rightSleeve);
    }

    public void renderLeftHand(PoseStack matrix, MultiBufferSource renderType, int scale, EntityEminentialProjection entity) {
        this.renderHand(matrix, renderType, scale, entity, (this.model).leftArm, (this.model).leftSleeve);
    }

    private void renderHand(PoseStack matrix, MultiBufferSource renderType, int scale, EntityEminentialProjection entity, ModelPart model1, ModelPart model2) {
        PlayerModel<EntityEminentialProjection> playermodel = this.getModel();
        this.setModelProperties(entity);
        playermodel.attackTime = 0.0F;
        playermodel.crouching = false;
        playermodel.swimAmount = 0.0F;
        playermodel.setupAnim(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        model1.xRot = 0.0F;
        model1.render(matrix, renderType.getBuffer(RenderType.entitySolid(entity.getSkinTextureLocation())), scale, OverlayTexture.NO_OVERLAY);
        model2.xRot = 0.0F;
        model2.render(matrix, renderType.getBuffer(RenderType.entityTranslucent(entity.getSkinTextureLocation())), scale, OverlayTexture.NO_OVERLAY);
    }

    protected void setupRotations(EntityEminentialProjection entity, PoseStack matrix, float x, float y, float z) {
        float f = entity.getSwimAmount(z);
        if (entity.isFallFlying()) {
            super.setupRotations(entity, matrix, x, y, z);
            float f1 = (float)entity.getFallFlyingTicks() + z;
            float f2 = Mth.clamp(f1 * f1 / 100.0F, 0.0F, 1.0F);
            if (!entity.isAutoSpinAttack()) {
                matrix.mulPose(Vector3f.XP.rotationDegrees(f2 * (-90.0F - entity.xRot)));
            }
            Vec3 vector3d = entity.getViewVector(z);
            Vec3 vector3d1 = entity.getDeltaMovement();
            double d0 = (float) vector3d1.horizontalDistanceSqr();
            double d1 =  (float) vector3d.horizontalDistanceSqr();
            if (d0 > 0.0D && d1 > 0.0D) {
                double d2 = (vector3d1.x * vector3d.x + vector3d1.z * vector3d.z) / Math.sqrt(d0 * d1);
                double d3 = vector3d1.x * vector3d.z - vector3d1.z * vector3d.x;
                matrix.mulPose(Vector3f.YP.rotation((float)(Math.signum(d3) * Math.acos(d2))));
            }
        } else if (f > 0.0F) {
            super.setupRotations(entity, matrix, x, y, z);
            float f3 = entity.isInWater() ? -90.0F - entity.xRot : -90.0F;
            float f4 = Mth.lerp(f, 0.0F, f3);
            matrix.mulPose(Vector3f.XP.rotationDegrees(f4));
            if (entity.isVisuallySwimming()) {
                matrix.translate(0.0D, -1.0D, 0.3F);
            }
        } else {
            super.setupRotations(entity, matrix, x, y, z);
        }
    }
}