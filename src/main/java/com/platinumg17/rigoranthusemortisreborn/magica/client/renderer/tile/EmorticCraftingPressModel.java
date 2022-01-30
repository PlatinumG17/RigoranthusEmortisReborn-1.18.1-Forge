package com.platinumg17.rigoranthusemortisreborn.magica.client.renderer.tile;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.EmorticCraftingPressTile;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EmorticCraftingPressModel extends AnimatedGeoModel<EmorticCraftingPressTile> {
    @Override
    public ResourceLocation getModelLocation(EmorticCraftingPressTile pressTile) {
        return RigoranthusEmortisReborn.rl("geo/emortic_crafting_press.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EmorticCraftingPressTile pressTile) {
        return RigoranthusEmortisReborn.rl("textures/blocks/emortic_crafting_press.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EmorticCraftingPressTile pressTile) {
        return RigoranthusEmortisReborn.rl("animations/emortic_crafting_press_animations.json");
    }
}