package com.platinumg17.rigoranthusemortisreborn.magica.client.renderer.item;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.magica.common.items.Adonis;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AdonisModel extends AnimatedGeoModel<Adonis> {

    @Override
    public ResourceLocation getModelLocation(Adonis wand) {
        return RigoranthusEmortisReborn.rl("geo/adonis.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Adonis wand) {
        return  RigoranthusEmortisReborn.rl("textures/items/adonis.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Adonis wand) {
        return RigoranthusEmortisReborn.rl("animations/wand_animation.json");
    }
}