package com.platinumg17.rigoranthusemortisreborn.magica.client.renderer.tile;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.RitualTile;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RitualVesselModel extends AnimatedGeoModel<RitualTile> {
    public static final ResourceLocation model = RigoranthusEmortisReborn.rl("geo/ritual_vessel.geo.json");
    public static final ResourceLocation texture = RigoranthusEmortisReborn.rl("textures/blocks/ritual_vessel.png");
    public static final ResourceLocation anim = RigoranthusEmortisReborn.rl("animations/ritual_vessel.json");

    @Override
    public ResourceLocation getModelLocation(RitualTile extractorTile) {
        return model;
    }

    @Override
    public ResourceLocation getTextureLocation(RitualTile extractorTile) {
        return texture;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(RitualTile extractorTile) {
        return anim;
    }
}