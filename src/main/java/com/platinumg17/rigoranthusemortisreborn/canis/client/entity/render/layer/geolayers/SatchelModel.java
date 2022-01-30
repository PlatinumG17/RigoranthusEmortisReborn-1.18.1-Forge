package com.platinumg17.rigoranthusemortisreborn.canis.client.entity.render.layer.geolayers;

import com.platinumg17.rigoranthusemortisreborn.canis.CanisSkills;
import com.platinumg17.rigoranthusemortisreborn.canis.client.entity.model.CanisModel;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;

import static com.platinumg17.rigoranthusemortisreborn.canis.common.lib.Resources.*;

/*
* A Thanks to JoeFoxe for allowing me to use his sexy Satchel Texture. Texture and model were edited to suit the needs of the pupper
* */
public class SatchelModel extends AnimatedGeoModel<CanisEntity> {
    public GeoModelProvider<CanisEntity> modelProvider;

    public SatchelModel(GeoModelProvider<CanisEntity> geoModelProvider) {
        this.modelProvider = geoModelProvider;
    }

    @Override
    public ResourceLocation getModelLocation(CanisEntity canis) {
        return SATCHEL_MODEL;
    }

    @Override
    public ResourceLocation getTextureLocation(CanisEntity canis) {
        return SATCHEL_TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(CanisEntity canis) {
        return TAME_CANIS_ANIMATION;
    }

    @Override
    public void setLivingAnimations(CanisEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone body = this.getAnimationProcessor().getBone("satchel");
        IBone canis = ((CanisModel) modelProvider).getBone("canis");
//        IBone parentBody = modelProvider.getModel(modelProvider.getModelLocation(entity)).getBone("whole_body").get();
//        body.setPivotX(parentBody.getPivotX());
//        body.setPivotY(parentBody.getPivotY());
//        body.setPivotZ(parentBody.getPivotZ());
//        body.setRotationX(parentBody.getRotationX() + 5);
//        body.setRotationY(parentBody.getRotationY());
//        body.setRotationZ(parentBody.getRotationZ());
//        body.setPositionY(canis.getPositionY() / 16f);
//        body.setPositionZ(canis.getPositionZ() * -1.2f);
        body.setHidden(!(entity.getCanisLevel(CanisSkills.WAYWARD_TRAVELLER.get()) >= 1) || entity.isInSittingPose() || entity.isBegging() || entity.isLying());
    }
}