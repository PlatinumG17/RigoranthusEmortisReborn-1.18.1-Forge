package com.platinumg17.rigoranthusemortisreborn.magica.client.renderer.tile;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.DominionTile;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class IchorExtractorModel<T extends DominionTile> extends AnimatedGeoModel<DominionTile> {

    public ResourceLocation modelLocation;
    public ResourceLocation textLoc;
    public ResourceLocation animationLoc = RigoranthusEmortisReborn.rl("animations/ichor_extractor_animations.json");

    public IchorExtractorModel(String name){
        this.modelLocation = RigoranthusEmortisReborn.rl("geo/" + name + ".geo.json");
        this.textLoc = RigoranthusEmortisReborn.rl("textures/blocks/dominion_crystallizer.png");
    }

    @Override
    public ResourceLocation getModelLocation(DominionTile ichorExtractor) {
        return modelLocation;
    }

    @Override
    public ResourceLocation getTextureLocation(DominionTile ichorExtractor) {
        return textLoc;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(DominionTile ichorExtractor) {
        return animationLoc;
    }
}