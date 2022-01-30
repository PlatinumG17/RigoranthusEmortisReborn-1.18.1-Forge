package com.platinumg17.rigoranthusemortisreborn.magica.client.renderer.tile;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.DominionCrystallizerTile;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DominionCrystallizerModel extends AnimatedGeoModel<DominionCrystallizerTile> {

    @Override
    public ResourceLocation getModelLocation(DominionCrystallizerTile dominionCrystallizer) {
        return RigoranthusEmortisReborn.rl("geo/dominion_crystallizer.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(DominionCrystallizerTile dominionCrystallizer) {
        return RigoranthusEmortisReborn.rl("textures/blocks/dominion_crystallizer.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(DominionCrystallizerTile dominionCrystallizer) {
        return RigoranthusEmortisReborn.rl("animations/dominion_crystallizer_animations.json");
    }
}