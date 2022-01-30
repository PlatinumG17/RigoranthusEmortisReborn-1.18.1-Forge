package com.platinumg17.rigoranthusemortisreborn.entity.item;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GhastlyScepterModel extends AnimatedGeoModel<GhastlyScepterItem> {
    @Override
    public ResourceLocation getModelLocation(GhastlyScepterItem object) {
        return RigoranthusEmortisReborn.rl("geo/ghastly_scepter.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(GhastlyScepterItem object) {
        return RigoranthusEmortisReborn.rl("textures/items/ghastly_scepter.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(GhastlyScepterItem animatable) {
        return RigoranthusEmortisReborn.rl("animations/ghastly_scepter.animation.json");
    }
}