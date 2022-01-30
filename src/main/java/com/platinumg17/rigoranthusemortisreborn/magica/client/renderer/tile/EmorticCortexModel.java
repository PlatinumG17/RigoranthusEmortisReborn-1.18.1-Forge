package com.platinumg17.rigoranthusemortisreborn.magica.client.renderer.tile;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.EmorticCortexTile;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EmorticCortexModel extends AnimatedGeoModel<EmorticCortexTile> {

    @Override
    public ResourceLocation getModelLocation(EmorticCortexTile emorticCortexTile) {
        return RigoranthusEmortisReborn.rl("geo/emortic_cortex.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EmorticCortexTile emorticCortexTile) {
        return RigoranthusEmortisReborn.rl("textures/blocks/emortic_cortex.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EmorticCortexTile emorticCortexTile) {
        return RigoranthusEmortisReborn.rl("animations/emortic_cortex.json");
    }
}