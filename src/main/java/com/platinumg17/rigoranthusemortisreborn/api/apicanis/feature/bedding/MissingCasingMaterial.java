package com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.bedding;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.ICasingMaterial;

public class MissingCasingMaterial extends ICasingMaterial {

    public static final ICasingMaterial NULL = new MissingCasingMaterial();
    private static final ResourceLocation MISSING_TEXTURE = new ResourceLocation("missingno");

    @Override
    public ResourceLocation getTexture() {
        return MissingCasingMaterial.MISSING_TEXTURE;
    }

    @Override
    public Component getTooltip() {
        return new TranslatableComponent("canisbed.casing.missing", this.getRegistryName());
    }

    @Override
    public Ingredient getIngredient() {
        return Ingredient.EMPTY;
    }
}
