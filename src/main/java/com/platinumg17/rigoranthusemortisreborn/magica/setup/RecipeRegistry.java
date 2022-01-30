package com.platinumg17.rigoranthusemortisreborn.magica.setup;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.psyglyphic_amalgamator.IPsyglyphicRecipe;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.psyglyphic_amalgamator.PsyglyphicAmalgamatorRecipe;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.psyglyphic_amalgamator.PsyglyphicEnchantingRecipe;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.recipe.CraftingPressRecipe;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.recipe.IIchoricRecipe;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class RecipeRegistry {

    public static final RecipeType<IIchoricRecipe> CRAFTING_PRESS_TYPE = new RigRecipeType();
    public static final RecipeType<IPsyglyphicRecipe> PSYGLYPHIC_TYPE = new RigRecipeType();
    public static final RecipeType<PsyglyphicEnchantingRecipe> ENCHANTMENT_TYPE = new RigRecipeType();

    public static final RecipeSerializer<CraftingPressRecipe> CRAFTING_PRESS_SERIALIZER = new CraftingPressRecipe.Serializer();
    public static final RecipeSerializer<PsyglyphicAmalgamatorRecipe> PSYGLYPHIC_SERIALIZER = new PsyglyphicAmalgamatorRecipe.Serializer();
    public static final RecipeSerializer<PsyglyphicEnchantingRecipe> ENCHANTMENT_SERIALIZER = new PsyglyphicEnchantingRecipe.Serializer();

    @SubscribeEvent
    public static void register(final RegistryEvent.Register<RecipeSerializer<?>> evt) {
        Registry.register(Registry.RECIPE_TYPE, RigoranthusEmortisReborn.rl("crafting_press_recipe"), CRAFTING_PRESS_TYPE);
        Registry.register(Registry.RECIPE_TYPE, RigoranthusEmortisReborn.rl("amalgamator_recipe"), PSYGLYPHIC_TYPE);
        Registry.register(Registry.RECIPE_TYPE, RigoranthusEmortisReborn.rl(PsyglyphicEnchantingRecipe.RECIPE_ID), ENCHANTMENT_TYPE);

        evt.getRegistry().register(CRAFTING_PRESS_SERIALIZER.setRegistryName(RigoranthusEmortisReborn.rl("crafting_press_recipe")));
        evt.getRegistry().register(PSYGLYPHIC_SERIALIZER.setRegistryName(RigoranthusEmortisReborn.rl("amalgamator_recipe")));
        evt.getRegistry().register(ENCHANTMENT_SERIALIZER.setRegistryName(RigoranthusEmortisReborn.rl(PsyglyphicEnchantingRecipe.RECIPE_ID)));
    }

    private static class RigRecipeType<T extends Recipe<?>> implements RecipeType<T> {
        @Override
        public String toString() {
            return Registry.RECIPE_TYPE.getKey(this).toString();
        }
    }
}