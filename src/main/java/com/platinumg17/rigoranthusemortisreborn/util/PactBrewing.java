package com.platinumg17.rigoranthusemortisreborn.util;

import com.platinumg17.rigoranthusemortisreborn.core.init.ItemInit;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipe;

import javax.annotation.Nonnull;

public class PactBrewing extends BrewingRecipe {

    private final Ingredient input;
    private final Ingredient ingredient;
    private final ItemStack output;

    public PactBrewing(Ingredient input, Ingredient ingredient, ItemStack output) {
        super(input, ingredient, output);
        this.input = input;
        this.ingredient = ingredient;
        this.output = output;
    }

    @Override
    public boolean isInput(@Nonnull ItemStack stack) {
        if (stack == null) {
            return false;
        }
        return (stack.getItem()
                == ItemInit.PACT_OF_SERVITUDE.get()) || (stack.getItem()
                == ItemInit.PACT_OF_MYRMIDON.get()) || (stack.getItem()
                == ItemInit.ROGUE_PACT_OF_MYRMIDON.get()) || (stack.getItem()
                == ItemInit.PACT_OF_PURTURBATION.get());
    }
}
