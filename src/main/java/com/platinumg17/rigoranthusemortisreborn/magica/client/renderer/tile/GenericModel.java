package com.platinumg17.rigoranthusemortisreborn.magica.client.renderer.tile;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GenericModel<T extends IAnimatable> extends AnimatedGeoModel<T> {
    public String path;

    public ResourceLocation modelLocation;
    public ResourceLocation textLoc;
    public ResourceLocation animationLoc;
    public String textPathRoot = "blocks";
    public GenericModel(String name){
        this.modelLocation = RigoranthusEmortisReborn.rl("geo/" + name + ".geo.json");
        this.textLoc = RigoranthusEmortisReborn.rl("textures/" + textPathRoot + "/" + name + ".png");
        this.animationLoc = RigoranthusEmortisReborn.rl("animations/" + name + "_animations.json");
    }

    public GenericModel(String name, String textPath){
        this(name);
        this.textPathRoot = textPath;
        this.textLoc = RigoranthusEmortisReborn.rl("textures/" + textPathRoot + "/" + name + ".png");
    }

    @Override
    public ResourceLocation getModelLocation(T iAnimatable) {
        return modelLocation;
    }

    @Override
    public ResourceLocation getTextureLocation(T iAnimatable) {
        return textLoc;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(T iAnimatable) {
        return animationLoc;
    }
}