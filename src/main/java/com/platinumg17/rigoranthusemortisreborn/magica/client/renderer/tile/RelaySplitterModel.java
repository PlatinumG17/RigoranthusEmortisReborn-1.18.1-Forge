package com.platinumg17.rigoranthusemortisreborn.magica.client.renderer.tile;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.RelaySplitterTile;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RelaySplitterModel extends AnimatedGeoModel<RelaySplitterTile> {

    @Override
    public ResourceLocation getModelLocation(RelaySplitterTile relaySplitterTile) {
        return RigoranthusEmortisReborn.rl("geo/relays/relay_splitter.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(RelaySplitterTile relaySplitterTile) {
        return RigoranthusEmortisReborn.rl("textures/blocks/relay_splitter.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(RelaySplitterTile relaySplitterTile) {
        return RigoranthusEmortisReborn.rl("animations/relays/relay_splitter_animations.json");
    }
}