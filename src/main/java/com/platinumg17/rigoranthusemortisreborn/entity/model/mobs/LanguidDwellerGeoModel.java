package com.platinumg17.rigoranthusemortisreborn.entity.model.mobs;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.entity.mobs.LanguidDwellerEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

import static com.platinumg17.rigoranthusemortisreborn.canis.common.lib.Resources.*;

public class LanguidDwellerGeoModel extends AnimatedGeoModel<LanguidDwellerEntity> {

    @Override
    public ResourceLocation getModelLocation(LanguidDwellerEntity dweller) {
        return DWELLER_MODEL;
    }

    @Override
    public ResourceLocation getTextureLocation(LanguidDwellerEntity dweller) {
        return DWELLER_TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(LanguidDwellerEntity animatable) {
        return DWELLER_ANIMATION;
    }

    @Override
    public void setLivingAnimations(LanguidDwellerEntity entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone whole_head = this.getAnimationProcessor().getBone("whole_head");
        if (customPredicate != null) {
            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            whole_head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
            whole_head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
        }
    }
}