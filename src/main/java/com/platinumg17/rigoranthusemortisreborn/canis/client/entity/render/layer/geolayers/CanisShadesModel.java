package com.platinumg17.rigoranthusemortisreborn.canis.client.entity.render.layer.geolayers;

import com.platinumg17.rigoranthusemortisreborn.canis.client.entity.model.CanisModel;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.accouterments.CanisAccouterments;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.Resources;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;

import static com.platinumg17.rigoranthusemortisreborn.canis.common.lib.Resources.*;

public class CanisShadesModel extends AnimatedGeoModel<CanisEntity> {
    public GeoModelProvider<CanisEntity> modelProvider;

    public CanisShadesModel(GeoModelProvider<CanisEntity> geoModelProvider) {
        this.modelProvider = geoModelProvider;
    }

    @Override public ResourceLocation getModelLocation(CanisEntity canis) { return SHADES_MODEL; }
    @Override public ResourceLocation getAnimationFileLocation(CanisEntity canis) { return TAME_CANIS_ANIMATION; }

    @Override public ResourceLocation getTextureLocation(CanisEntity canis) {
        return getColor(canis);
    }

    public ResourceLocation getColor(CanisEntity e) {
        String color = e.getShadesColor().getSaveName().toLowerCase();
        if(color.isEmpty())
            return SHADES_BLACK;
        return Resources.getShadesColor(color);
    }

    @Override
    public void setLivingAnimations(CanisEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("shades");
        IBone canis = ((CanisModel) modelProvider).getBone("canis");
        IBone parentHead = modelProvider.getModel(modelProvider.getModelLocation(entity)).getBone("head").get();
        head.setPivotX(parentHead.getPivotX());
        head.setPivotY(parentHead.getPivotY());
        head.setPivotZ(parentHead.getPivotZ());
        head.setRotationX(parentHead.getRotationX());
        head.setRotationY(parentHead.getRotationY());
        head.setRotationZ(parentHead.getRotationZ());
//        float scale = 11f;
//        head.setPositionY(canis.getPositionY() / 16f);
//        head.setPositionZ(canis.getPositionZ() * -1.2f);
        head.setHidden(!entity.getAccoutrement(CanisAccouterments.SUNGLASSES.get()).isPresent() || entity.isInSittingPose() || entity.isBegging() || entity.isLying());
    }
}
