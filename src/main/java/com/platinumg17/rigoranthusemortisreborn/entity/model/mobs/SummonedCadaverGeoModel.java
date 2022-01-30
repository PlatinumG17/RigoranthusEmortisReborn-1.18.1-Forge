package com.platinumg17.rigoranthusemortisreborn.entity.model.mobs;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.entity.mobs.SummonedCadaver;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

public class SummonedCadaverGeoModel extends AnimatedGeoModel<SummonedCadaver> {

    private static final ResourceLocation CADAVER_TEXTURE = RigoranthusEmortisReborn.rl("textures/entity/summoned_cadaver.png");
    public static final ResourceLocation CADAVER_MODEL = RigoranthusEmortisReborn.rl("geo/sundered_cadaver.geo.json");
    public static final ResourceLocation ANIMATIONS = RigoranthusEmortisReborn.rl("animations/sundered_cadaver.json");

    @Override
    public ResourceLocation getModelLocation(SummonedCadaver cadaver) {
        return CADAVER_MODEL;
    }

    @Override
    public ResourceLocation getTextureLocation(SummonedCadaver cadaver) {
        return CADAVER_TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SummonedCadaver animatable) {
        return ANIMATIONS;
    }

    @Override
    public void setLivingAnimations(SummonedCadaver entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        if (customPredicate != null) {
            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 240F));
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 240F));
        }
    }
}