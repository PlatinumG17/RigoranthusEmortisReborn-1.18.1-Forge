package com.platinumg17.rigoranthusemortisreborn.magica.common.crafting;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import static com.platinumg17.rigoranthusemortisreborn.magica.setup.InjectionUtil.Null;

public class ModCrafting {

    @ObjectHolder(EmortisConstants.MOD_ID)
    @Mod.EventBusSubscriber(modid = EmortisConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Recipes {
        public static RecipeSerializer<BookUpgradeRecipe> BOOK_UPGRADE_RECIPE = Null();
        public static RecipeSerializer<DyeRecipe> DYE_RECIPE = Null();

        @SubscribeEvent
        public static void register(final RegistryEvent.Register<RecipeSerializer<?>> event) {
            BOOK_UPGRADE_RECIPE = new BookUpgradeRecipe.Serializer();
            DYE_RECIPE = new DyeRecipe.Serializer();
            event.getRegistry().registerAll(
                    BOOK_UPGRADE_RECIPE.setRegistryName(RigoranthusEmortisReborn.rl("book_upgrade")),
                    DYE_RECIPE.setRegistryName(RigoranthusEmortisReborn.rl("dye"))
            );
        }
    }
}