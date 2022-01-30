package com.platinumg17.rigoranthusemortisreborn.canis.client.entity.model;

import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

import static com.platinumg17.rigoranthusemortisreborn.canis.common.lib.Resources.*;

public class CanisModel extends AnimatedGeoModel<CanisEntity> {

    @Override
    public void setLivingAnimations(CanisEntity entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        if(entity.partyCanis)
            return;
        IBone head = this.getAnimationProcessor().getBone("head");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX(extraData.headPitch * 0.017453292F);
        head.setRotationY(extraData.netHeadYaw * 0.017453292F);
    }

    @Override
    public ResourceLocation getModelLocation(CanisEntity canis) {
        if (canis.isInSittingPose())
            return SITTING_MODEL;
        else if (canis.isLying())
            return LYING_MODEL;
        else if (canis.isBegging())
            return BEGGING_MODEL;
        else return TAME_CANIS_MODEL;
//        return canis.isOrderedToSit() ? SITTING_MODEL : STANDING_MODEL;
    }

    @Override
    public ResourceLocation getTextureLocation(CanisEntity canis) {
        return canis.isTame() ? TAME_CANIS_TEXTURE : FERAL_CANIS_TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(CanisEntity canis) {
        return TAME_CANIS_ANIMATION;
    }
}

//    @Override
//    public void prepareMobModel(T canis, float limbSwing, float limbSwingAmount, float partialTickTime) {
//        this.tail.zRot = canis.getWagAngle(limbSwing, limbSwingAmount, partialTickTime);
//
//        this.whole_head.zRot = canis.getInterestedAngle(partialTickTime) + canis.getShakeAngle(partialTickTime, 0.0F);
//        this.mane.zRot = canis.getShakeAngle(partialTickTime, -0.08F);
//        this.body.zRot = canis.getShakeAngle(partialTickTime, -0.16F);
//        this.tail.zRot = canis.getShakeAngle(partialTickTime, -0.2F);
//    }
//
//    @Override
//    public void setupAnim(T canisIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
//        this.legBackRight.xRot = Mth.cos(limbSwing * 0.3332F) * 1.1F * limbSwingAmount;
//        this.legBackLeft.xRot = Mth.cos(limbSwing * 0.3332F + (float)Math.PI) * 1.1F * limbSwingAmount;
//        this.legFrontRight.xRot = Mth.cos(limbSwing * 0.3332F + (float)Math.PI) * 1.1F * limbSwingAmount;
//        this.legFrontLeft.xRot = Mth.cos(limbSwing * 0.3332F) * 1.1F * limbSwingAmount;
//
//        this.whole_head.xRot = headPitch * ((float)Math.PI / 200F);
//        this.whole_head.yRot = netHeadYaw * (canisIn.isInSittingPose() && canisIn.isLying() ? 0.005F : (float)Math.PI / 200F);
//        this.tail.xRot = ageInTicks;
//    }