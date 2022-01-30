package com.platinumg17.rigoranthusemortisreborn.entity.model;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.entity.DelphicBloomEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DelphicBloomModel extends AnimatedGeoModel<DelphicBloomEntity> {
    @Override
    public ResourceLocation getAnimationFileLocation(DelphicBloomEntity entity) {
        return RigoranthusEmortisReborn.rl("animations/delphic_bloom.json");
    }

    @Override
    public ResourceLocation getModelLocation(DelphicBloomEntity entity) {
        return RigoranthusEmortisReborn.rl("geo/delphic_bloom.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(DelphicBloomEntity entity) {
        return RigoranthusEmortisReborn.rl("textures/entity/delphic_bloom.png");
    }

    @Override
    public void setLivingAnimations(DelphicBloomEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
    }
}