package com.platinumg17.rigoranthusemortisreborn.magica.client.renderer.tile;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.TableTile;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TableModel extends AnimatedGeoModel<TableTile> {
    @Override
    public ResourceLocation getModelLocation(TableTile extractorTile) {
        return RigoranthusEmortisReborn.rl("geo/table.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(TableTile extractorTile) {
        return RigoranthusEmortisReborn.rl("textures/blocks/table.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(TableTile extractorTile) {
        return RigoranthusEmortisReborn.rl("animations/dominion_splitter_animation.json");
    }
}
