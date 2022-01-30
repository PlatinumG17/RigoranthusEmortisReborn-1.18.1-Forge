package com.platinumg17.rigoranthusemortisreborn.magica.client.renderer.item;

import com.platinumg17.rigoranthusemortisreborn.magica.common.items.AnimBlockItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FixedGeoBlockItemModel<T extends AnimBlockItem & IAnimatable> extends AnimatedGeoModel<T> {
    AnimatedGeoModel model;
    public FixedGeoBlockItemModel(AnimatedGeoModel model){
        this.model = model;
    }

    @Override
    public ResourceLocation getModelLocation(AnimBlockItem animBlockItem) {
        return model.getModelLocation(null);
    }

    @Override
    public ResourceLocation getTextureLocation(AnimBlockItem animBlockItem) {
        return model.getTextureLocation(null);
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AnimBlockItem animBlockItem) {
        return model.getAnimationFileLocation(null);
    }
}
