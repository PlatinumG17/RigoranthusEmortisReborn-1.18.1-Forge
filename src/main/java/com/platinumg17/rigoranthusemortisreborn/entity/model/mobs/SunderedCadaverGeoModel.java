package com.platinumg17.rigoranthusemortisreborn.entity.model.mobs;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.entity.mobs.SunderedCadaverEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

import static com.platinumg17.rigoranthusemortisreborn.canis.common.lib.Resources.*;

public class SunderedCadaverGeoModel extends AnimatedGeoModel<SunderedCadaverEntity> {

    @Override
    public ResourceLocation getModelLocation(SunderedCadaverEntity cadaver) {
        return CADAVER_MODEL;
    }

    @Override
    public ResourceLocation getTextureLocation(SunderedCadaverEntity cadaver) {
        return CADAVER_TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SunderedCadaverEntity animatable) {
        return CADAVER_ANIMATION;
    }

    @Override
    public void setLivingAnimations(SunderedCadaverEntity entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        if (customPredicate != null) {
            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 240F));
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 240F));
        }
    }
}