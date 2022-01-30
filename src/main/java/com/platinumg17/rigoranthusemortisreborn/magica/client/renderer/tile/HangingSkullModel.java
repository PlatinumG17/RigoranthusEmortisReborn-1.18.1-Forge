package com.platinumg17.rigoranthusemortisreborn.magica.client.renderer.tile;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.blocks.tileentity.HangingSkullTile;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HangingSkullModel extends AnimatedGeoModel<HangingSkullTile> {
    @Override
    public ResourceLocation getModelLocation(HangingSkullTile skullTile) {
        return RigoranthusEmortisReborn.rl("geo/hanging_cadaver_skull.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(HangingSkullTile skullTile) {
        return RigoranthusEmortisReborn.rl("textures/blocks/cadaver_skull.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(HangingSkullTile skullTile) {
        return RigoranthusEmortisReborn.rl("animations/cadaver_skull.json");
    }
}