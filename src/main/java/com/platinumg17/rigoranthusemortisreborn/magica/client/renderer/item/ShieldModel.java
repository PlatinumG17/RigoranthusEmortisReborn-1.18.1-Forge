package com.platinumg17.rigoranthusemortisreborn.magica.client.renderer.item;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.magica.common.items.LustericShield;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShieldModel extends AnimatedGeoModel<LustericShield> {

    @Override
    public ResourceLocation getModelLocation(LustericShield wand) {
        return RigoranthusEmortisReborn.rl("geo/shield.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(LustericShield wand) {
        return  RigoranthusEmortisReborn.rl("textures/items/lusteric_shield.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(LustericShield wand) {
        return RigoranthusEmortisReborn.rl("animations/shield.json");
    }
}