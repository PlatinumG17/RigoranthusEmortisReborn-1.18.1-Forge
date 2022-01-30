package com.platinumg17.rigoranthusemortisreborn.canis.client.entity.render.layer.geolayers;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.canis.client.entity.model.CanisModel;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.accouterments.CanisAccouterments;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;

import static com.platinumg17.rigoranthusemortisreborn.canis.common.lib.Resources.*;

public class CanisArmorModel extends AnimatedGeoModel<CanisEntity> {

    public GeoModelProvider<CanisEntity> modelProvider;

    public CanisArmorModel(GeoModelProvider<CanisEntity> geoModelProvider) {
        this.modelProvider = geoModelProvider;
    }

    @Override
    public ResourceLocation getModelLocation(CanisEntity canis) {
        return RigoranthusEmortisReborn.rl("geo/canis/canis_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(CanisEntity canis) {
        if (canis.getAccoutrement(CanisAccouterments.IRON_HELMET.get()).isPresent())
            return IRON_ARMOR;
        else if (canis.getAccoutrement(CanisAccouterments.CHAINMAIL_HELMET.get()).isPresent())
            return CHAINMAIL_ARMOR;
        else if (canis.getAccoutrement(CanisAccouterments.GOLDEN_HELMET.get()).isPresent())
            return GOLD_ARMOR;
        else if (canis.getAccoutrement(CanisAccouterments.DIAMOND_HELMET.get()).isPresent())
            return DIAMOND_ARMOR;
        else if (canis.getAccoutrement(CanisAccouterments.NETHERITE_HELMET.get()).isPresent())
            return NETHERITE_ARMOR;
        else if (canis.getAccoutrement(CanisAccouterments.LEATHER_HELMET.get()).isPresent())
            return LEATHER_ARMOR;
        else if (canis.getAccoutrement(CanisAccouterments.TURTLE_HELMET.get()).isPresent())
            return TURTLE_ARMOR;
        else return IRON_ARMOR;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(CanisEntity canis) {
        return TAME_CANIS_ANIMATION;
    }

    @Override
    public void setLivingAnimations(CanisEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        IBone head = this.getAnimationProcessor().getBone("armor");
        IBone helmet = this.getAnimationProcessor().getBone("helmet");
        IBone chestplate = this.getAnimationProcessor().getBone("chestplate");
        IBone canis = ((CanisModel) modelProvider).getBone("canis");
        IBone parentHead = modelProvider.getModel(modelProvider.getModelLocation(entity)).getBone("head").get();
        IBone parentChest = modelProvider.getModel(modelProvider.getModelLocation(entity)).getBone("body").get();
        chestplate.setPivotX(parentChest.getPivotX());
        chestplate.setPivotY(parentChest.getPivotY());
        chestplate.setPivotZ(parentChest.getPivotZ());
        chestplate.setRotationX(parentChest.getRotationX());
        chestplate.setRotationY(parentChest.getRotationY());
        chestplate.setRotationZ(parentChest.getRotationZ());

        helmet.setPivotX(parentHead.getPivotX());
        helmet.setPivotY(parentHead.getPivotY());
        helmet.setPivotZ(parentHead.getPivotZ());
        helmet.setRotationX(parentHead.getRotationX());
        helmet.setRotationY(parentHead.getRotationY());
        helmet.setRotationZ(parentHead.getRotationZ());
//        head.setPositionY(canis.getPositionY() / 16f);
//        head.setPositionZ(canis.getPositionZ() * -1.2f);
//        float scale = 11f;
//        head.setPositionY(canis.getPositionY() / 16f);
//        head.setPositionZ(canis.getPositionZ() * -1.2f);
        head.setHidden(
                !entity.getAccoutrement(CanisAccouterments.CHAINMAIL_HELMET.get()).isPresent() ||
                !entity.getAccoutrement(CanisAccouterments.LEATHER_HELMET.get()).isPresent() ||
                !entity.getAccoutrement(CanisAccouterments.IRON_HELMET.get()).isPresent() ||
                !entity.getAccoutrement(CanisAccouterments.GOLDEN_HELMET.get()).isPresent() ||
                !entity.getAccoutrement(CanisAccouterments.DIAMOND_HELMET.get()).isPresent() ||
                !entity.getAccoutrement(CanisAccouterments.NETHERITE_HELMET.get()).isPresent() ||
                !entity.getAccoutrement(CanisAccouterments.TURTLE_HELMET.get()).isPresent() ||
                 entity.isInSittingPose() || entity.isBegging() || entity.isLying());
    }
}