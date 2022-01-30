package com.platinumg17.rigoranthusemortisreborn.magica.common.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.platinumg17.rigoranthusemortisreborn.api.RigoranthusEmortisRebornAPI;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.psyglyphic_amalgamator.IPsyglyphicRecipe;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.psyglyphic_amalgamator.PsyglyphicAmalgamatorRecipe;
import com.platinumg17.rigoranthusemortisreborn.blocks.BlockInit;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.core.init.ItemInit;
import com.platinumg17.rigoranthusemortisreborn.core.init.Registration;
import com.platinumg17.rigoranthusemortisreborn.magica.common.enchantment.EnchantmentRegistry;
import com.platinumg17.rigoranthusemortisreborn.magica.common.spell.augment.AugmentAmplify;
import com.platinumg17.rigoranthusemortisreborn.magica.common.spell.augment.AugmentExtendTime;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.BlockRegistry;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.MagicItemsRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.Tags;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PsyglyphicRecipeProvider implements DataProvider {
    private final DataGenerator generator;
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private static final Logger LOGGER = LogManager.getLogger();
    public PsyglyphicRecipeProvider(DataGenerator generatorIn) {
        this.generator = generatorIn;
    }

    List<PsyglyphicAmalgamatorRecipe> recipes = new ArrayList<>();
    @Override
    public void run(HashCache cache) throws IOException {
        addEntries();
        Path output = this.generator.getOutputFolder();
        for(IPsyglyphicRecipe g : recipes){
            if(g instanceof PsyglyphicAmalgamatorRecipe){
                System.out.println(g);
                Path path = getRecipePath(output, ((PsyglyphicAmalgamatorRecipe) g).getId().getPath());
                DataProvider.save(GSON, cache, ((PsyglyphicAmalgamatorRecipe) g).asRecipe(), path);

                if(g.getResultItem().isEmpty())
                    continue;
                Path path1 = getAmalgamatorPath(output, (PsyglyphicAmalgamatorRecipe) g);
                try {
                    DataProvider.save(GSON, cache, ((PsyglyphicAmalgamatorRecipe)g).serialize(), path1);
                    System.out.println(g);
                } catch (IOException ioexception) {
                    LOGGER.error("Couldn't save amalgamator {}", path1, ioexception);
                }
            }
        }
    }
    private static Path getAmalgamatorPath(Path pathIn, PsyglyphicAmalgamatorRecipe e) {
        System.out.println(e.result.getItem().toString());
        return pathIn.resolve("data/rigoranthusemortisreborn/amalgamator/" + e.result.getItem().getRegistryName().toString().replace(EmortisConstants.MOD_ID + ":", "") + ".json");
    }

    public PsyglyphicRecipeBuilder builder(){
        return PsyglyphicRecipeBuilder.builder();
    }

    public void addEntries(){

        addRecipe(builder()
                .withResult(ItemInit.CRY_OF_DESPERATION.get())
                .withReagent(BlockRegistry.BLOCK_OF_ESOTERICUM.asItem())
                .withPedestalItem(2,  Ingredient.of(Tags.Items.STORAGE_BLOCKS_GOLD))
                .withPedestalItem(4,  Ingredient.of(Items.DARK_OAK_PLANKS))
                .withPedestalItem(2,  Recipes.DOMINION_GEM)
                .build());
        addRecipe(builder()
                .withResult(ItemInit.MORRAI.get())
                .withReagent(ItemInit.EMORTIC_WEAPON_GRIP.get())
                .withPedestalItem(2,  Ingredient.of(ItemInit.IRON_SLIME_BALL.get()))
                .withPedestalItem(1,  Ingredient.of(Items.LEAD))
                .withPedestalItem(1,  Recipes.DOMINION_GEM)
                .build());
        addRecipe(builder()
                .withResult(ItemInit.ANDURIL.get())
                .withReagent(ItemInit.EMORTIC_WEAPON_GRIP.get())
                .withPedestalItem(1,  Ingredient.of(Items.FIRE_CHARGE))
                .withPedestalItem(1,  Recipes.DOMINION_GEM)
                .withPedestalItem(2,  Ingredient.of(ItemInit.GHAST_IRON_INGOT.get()))
                .build());
        addRecipe(builder()
                .withResult(ItemInit.BLIGHT_ICHOR.get())
                .withReagent(Items.WITHER_SKELETON_SKULL)
                .withPedestalItem(2,  Ingredient.of(MagicItemsRegistry.BOTTLE_OF_ICHOR))
                .withPedestalItem(2,  Ingredient.of(Items.SPIDER_EYE))
                .withPedestalItem(2,  Ingredient.of(Items.NETHER_WART))
                .build());
        addRecipe(builder()
                .withResult(ItemInit.RAZORTOOTH_FRISBEE.get())
                .withReagent(ItemInit.EMORTIC_WEAPON_GRIP.get())
                .withPedestalItem(1,  Recipes.DOMINION_GEM)
                .withPedestalItem(7,  Ingredient.of(ItemInit.RAZORTOOTH_KUNAI.get()))
                .build());
        addRecipe(builder()
                .withResult(MagicItemsRegistry.ADONIS)
                .withReagent(Items.BOW)
                .withPedestalItem(2, Ingredient.of(ItemInit.EMORTIC_WEAPON_GRIP.get()))
                .withPedestalItem(1, Ingredient.of(ItemInit.PSYGLYPHIC_SCRIPT.get()))
                .withPedestalItem(4, Ingredient.of(Tags.Items.INGOTS_GOLD))
                .withPedestalItem(1,  Recipes.DOMINION_GEM)
                .build());
        addRecipe(builder()
                .withResult(MagicItemsRegistry.unadornedRing)
                .withReagent(Ingredient.of(Tags.Items.INGOTS_IRON))
                .withPedestalItem(4,  Ingredient.of(Tags.Items.NUGGETS_IRON))
                .build());
        addRecipe(builder()
                .withResult(BlockRegistry.DOMINION_BERRY_BUSH.asItem())
                .withReagent(Ingredient.of(Tags.Items.SEEDS))
                .withPedestalItem(2, Recipes.DOMINION_GEM)
                .withPedestalItem(2, Ingredient.of(Items.SWEET_BERRIES))
                .build());
        addRecipe(builder()
                .withResult(MagicItemsRegistry.ringOfLesserConservation)
                .withReagent(MagicItemsRegistry.unadornedRing)
                .withPedestalItem(2,  Ingredient.of(Tags.Items.GEMS_DIAMOND))
                .withPedestalItem(2,  Ingredient.of(Registration.POWDERED_ESOTERICUM.get()))
                .withPedestalItem(2,  Recipes.DOMINION_GEM)
                .withPedestalItem(2,  Ingredient.of(Tags.Items.ENDER_PEARLS))
                .build());
        addRecipe(builder()
                .withResult(MagicItemsRegistry.ringOfGreaterConservation)
                .withReagent(MagicItemsRegistry.ringOfLesserConservation)
                .withPedestalItem(2,  Ingredient.of(Tags.Items.GEMS_DIAMOND))
                .withPedestalItem(2,  Ingredient.of(Tags.Items.RODS_BLAZE))
                .withPedestalItem(2,  Ingredient.of(Registration.POWDERED_ESOTERICUM.get()))
                .withPedestalItem(2, Recipes.DOMINION_GEM)
                .build());

        addRecipe(builder()
                .withResult(MagicItemsRegistry.unadornedAmulet)
                .withReagent(Ingredient.of(Tags.Items.INGOTS_IRON))
                .withPedestalItem(3,  Ingredient.of(Tags.Items.NUGGETS_IRON))
                .withPedestalItem(3, Ingredient.of(Tags.Items.LEATHER))
                .build());
        addRecipe(builder()
                .withResult(MagicItemsRegistry.amuletOfDominionBoost)
                .withReagent(MagicItemsRegistry.unadornedAmulet)
                .withPedestalItem(2,Ingredient.of(Tags.Items.GEMS_DIAMOND))
                .withPedestalItem(2,  Ingredient.of(Registration.POWDERED_ESOTERICUM.get()))
                .withPedestalItem(3, Recipes.DOMINION_GEM)
                .build());
        addRecipe(builder()
                .withResult(MagicItemsRegistry.amuletOfDominionRegen)
                .withReagent(MagicItemsRegistry.unadornedAmulet)
                .withPedestalItem(2,Ingredient.of(Tags.Items.GEMS_DIAMOND))
                .withPedestalItem(2,Ingredient.of(Tags.Items.INGOTS_GOLD))
                .withPedestalItem(4, Recipes.DOMINION_GEM)
                .build());
        addRecipe(builder()
                .withResult(MagicItemsRegistry.dominionWand)
                .withReagent(Items.STICK)
                .withPedestalItem(2, Recipes.DOMINION_GEM)
                .withPedestalItem(Ingredient.of(Tags.Items.INGOTS_GOLD))
                .build());

/*
    TODO -->    Use 1 Powdered Esotericum to make 1 Blank Script

    TODO -->    Players find naturally generated Psyglyphic Blocks around the world.
                    Dwellers will ALWAYS spawn near a Psyglyphic Block
                    Right click with a blank script to obtain a psyglyphic script -> cipher turns to stone.

    TODO -->    Players place a Decipher Kit on one of the faces of the Psyglyphic Block ("Psyglyphic Cipher")
                   [Eval Kit == Metal Frame w/ Animated Scanning Feature (render fake face of Psyglyphic Block in window of frame) --> After Anim, Psyglyphic Script is Printed, and the Block changes to stone]
*/

        addRecipe(builder()
                .withPedestalItem(1,  Ingredient.of(Items.SUGAR))
                .withPedestalItem(1,  Ingredient.of(Items.IRON_PICKAXE))
                .withPedestalItem(2, Recipes.DOMINION_GEM_BLOCK)
                .buildPsyglyphicRecipe(Enchantments.BLOCK_EFFICIENCY, 1, 2000));
        addRecipe(builder()
                .withPedestalItem(1,  Ingredient.of(Tags.Items.STORAGE_BLOCKS_REDSTONE))
                .withPedestalItem(1,  Ingredient.of(Items.GOLDEN_PICKAXE))
                .withPedestalItem(2, Recipes.DOMINION_GEM_BLOCK)
                .buildPsyglyphicRecipe(Enchantments.BLOCK_EFFICIENCY, 2, 3500));
        addRecipe(builder()
                .withPedestalItem(2,  Ingredient.of(Tags.Items.STORAGE_BLOCKS_REDSTONE))
                .withPedestalItem(1,  Ingredient.of(Items.GOLDEN_PICKAXE))
                .withPedestalItem(3, Recipes.DOMINION_GEM_BLOCK)
                .withPedestalItem(2,  Ingredient.of(Tags.Items.OBSIDIAN))
                .buildPsyglyphicRecipe(Enchantments.BLOCK_EFFICIENCY, 3, 5000));
        addRecipe(builder()
                .withPedestalItem(2,  Ingredient.of(Tags.Items.STORAGE_BLOCKS_REDSTONE))
                .withPedestalItem(1,  Ingredient.of(Items.DIAMOND_PICKAXE))
                .withPedestalItem(1,  Ingredient.of(Items.IRON_SHOVEL))
                .withPedestalItem(2, Recipes.DOMINION_GEM_BLOCK)
                .withPedestalItem(2,  Ingredient.of(Tags.Items.OBSIDIAN))
                .buildPsyglyphicRecipe(Enchantments.BLOCK_EFFICIENCY, 4, 6500));
        addRecipe(builder()
                .withPedestalItem(2,  Ingredient.of(Tags.Items.STORAGE_BLOCKS_REDSTONE))
                .withPedestalItem(1,  Ingredient.of(Items.DIAMOND_PICKAXE))
                .withPedestalItem(1,  Ingredient.of(Items.DIAMOND_SHOVEL))
                .withPedestalItem(2, Recipes.DOMINION_GEM_BLOCK)
                .withPedestalItem(2,  Ingredient.of(Tags.Items.STORAGE_BLOCKS_LAPIS))
                .buildPsyglyphicRecipe(Enchantments.BLOCK_EFFICIENCY, 5, 8000));

        addRecipe(builder()
                .withPedestalItem(2, Ingredient.of(Tags.Items.STORAGE_BLOCKS_QUARTZ))
                .withPedestalItem(1, Recipes.DOMINION_GEM_BLOCK)
                .withPedestalItem(1, Ingredient.of(Tags.Items.STORAGE_BLOCKS_LAPIS))
                .buildPsyglyphicRecipe(Enchantments.SHARPNESS, 1, 2000));
        addRecipe(builder()
                .withPedestalItem(2, Ingredient.of(Tags.Items.STORAGE_BLOCKS_QUARTZ))
                .withPedestalItem(1, Recipes.DOMINION_GEM_BLOCK)
                .withPedestalItem(1, Ingredient.of(Tags.Items.STORAGE_BLOCKS_LAPIS))
                .buildPsyglyphicRecipe(Enchantments.SHARPNESS, 2, 3500));

        addRecipe(builder()
                .withPedestalItem(2, Ingredient.of(Items.BONE_BLOCK))
                .withPedestalItem(1, Recipes.DOMINION_GEM_BLOCK)
                .withPedestalItem(1, Ingredient.of(Tags.Items.STORAGE_BLOCKS_LAPIS))
                .buildPsyglyphicRecipe(Enchantments.SMITE, 1, 2000));
        addRecipe(builder()
                .withPedestalItem(3, Ingredient.of(Items.BONE_BLOCK))
                .withPedestalItem(1, Recipes.DOMINION_GEM_BLOCK)
                .withPedestalItem(1, Ingredient.of(Tags.Items.STORAGE_BLOCKS_LAPIS))
                .buildPsyglyphicRecipe(Enchantments.SMITE, 2, 3500));

        addRecipe(builder()
                .withPedestalItem(4, Ingredient.of(Items.BONE_BLOCK))
                .withPedestalItem(1, Recipes.DOMINION_GEM_BLOCK)
                .withPedestalItem(1, Ingredient.of(Tags.Items.STORAGE_BLOCKS_LAPIS))
                .buildPsyglyphicRecipe(Enchantments.SMITE, 3, 500));

        addRecipe(builder()
                .withPedestalItem(4, Ingredient.of(Items.BONE_BLOCK))
                .withPedestalItem(2, Recipes.DOMINION_GEM_BLOCK)
                .withPedestalItem(1, Ingredient.of(Tags.Items.STORAGE_BLOCKS_LAPIS))
                .buildPsyglyphicRecipe(Enchantments.SMITE, 4, 6500));

        addRecipe(builder()
                .withPedestalItem(4, Ingredient.of(Items.BONE_BLOCK))
                .withPedestalItem(2, Recipes.DOMINION_GEM_BLOCK)
                .withPedestalItem(2, Ingredient.of(Tags.Items.STORAGE_BLOCKS_LAPIS))
                .buildPsyglyphicRecipe(Enchantments.SMITE, 5, 8000));

        addRecipe(builder()
                .withPedestalItem(1, BlockRegistry.DOMINION_BERRY_BUSH)
                .withPedestalItem(4, Recipes.DOMINION_GEM_BLOCK)
                .buildPsyglyphicRecipe(EnchantmentRegistry.DOMINION_BOOST_ENCHANTMENT, 1, 2000));
        addRecipe(builder()
                .withPedestalItem(1, BlockRegistry.DOMINION_BERRY_BUSH)
                .withPedestalItem(1, Ingredient.of(RigoranthusEmortisRebornAPI.getInstance().getGlyphItem(AugmentAmplify.INSTANCE)))
                .withPedestalItem(4, Recipes.DOMINION_GEM_BLOCK)
                .withPedestalItem(2, Ingredient.of(Tags.Items.STORAGE_BLOCKS_LAPIS))
                .buildPsyglyphicRecipe(EnchantmentRegistry.DOMINION_BOOST_ENCHANTMENT, 2, 3500));

        addRecipe(builder()
                .withPedestalItem(1, BlockRegistry.DOMINION_BERRY_BUSH)
                .withPedestalItem(2, Ingredient.of(RigoranthusEmortisRebornAPI.getInstance().getGlyphItem(AugmentAmplify.INSTANCE)))
                .withPedestalItem(4, Recipes.DOMINION_GEM_BLOCK)
                .withPedestalItem(1, Ingredient.of(Tags.Items.STORAGE_BLOCKS_LAPIS))
                .buildPsyglyphicRecipe(EnchantmentRegistry.DOMINION_BOOST_ENCHANTMENT, 3, 5000));

        addRecipe(builder()
                .withPedestalItem(2, BlockRegistry.DOMINION_BERRY_BUSH)
                .withPedestalItem(2, Recipes.DOMINION_GEM_BLOCK)
                .withPedestalItem(2, Ingredient.of(Tags.Items.STORAGE_BLOCKS_LAPIS))
                .buildPsyglyphicRecipe(EnchantmentRegistry.DOMINION_REGEN_ENCHANTMENT, 1, 2000));
        addRecipe(builder()
                .withPedestalItem(2, BlockRegistry.DOMINION_BERRY_BUSH)
                .withPedestalItem(2, Ingredient.of(RigoranthusEmortisRebornAPI.getInstance().getGlyphItem(AugmentExtendTime.INSTANCE)))
                .withPedestalItem(2, Recipes.DOMINION_GEM_BLOCK)
                .withPedestalItem(2, Ingredient.of(Tags.Items.STORAGE_BLOCKS_LAPIS))
                .buildPsyglyphicRecipe(EnchantmentRegistry.DOMINION_REGEN_ENCHANTMENT, 2, 3500));

        addRecipe(builder()
                .withPedestalItem(2, BlockRegistry.DOMINION_BERRY_BUSH)
                .withPedestalItem(3, Ingredient.of(RigoranthusEmortisRebornAPI.getInstance().getGlyphItem(AugmentExtendTime.INSTANCE)))
                .withPedestalItem(2, Recipes.DOMINION_GEM_BLOCK)
                .withPedestalItem(1, Ingredient.of(Tags.Items.STORAGE_BLOCKS_LAPIS))
                .buildPsyglyphicRecipe(EnchantmentRegistry.DOMINION_REGEN_ENCHANTMENT, 3, 5000));

        addRecipe(builder()
                .withResult(MagicItemsRegistry.LUSTERIC_SHIELD)
                .withReagent(Items.SHIELD)
                .withPedestalItem(2, Ingredient.of(Tags.Items.STORAGE_BLOCKS_GOLD))
                .withPedestalItem(2, Recipes.DOMINION_GEM)
                .build());

        addRecipe(builder()
                .withResult(MagicItemsRegistry.SUMMONERS_STRENGTH)
                .withReagent(Recipes.DOMINION_GEM_BLOCK)
                .withPedestalItem(ItemInit.BONE_FRAGMENT.get())
                .withPedestalItem(Ingredient.of(Tags.Items.INGOTS_GOLD))
                .build());

        addRecipe(builder()
                .withResult(BlockRegistry.RELAY_DEPOSIT)
                .withReagent(BlockRegistry.EMORTIC_RELAY)
                .withPedestalItem(4,Ingredient.of(Items.HOPPER))
                .build());
    }

    public void addRecipe(PsyglyphicAmalgamatorRecipe recipe){
        recipes.add(recipe);
    }

    private static Path getRecipePath(Path pathIn, Item item) {
        return getRecipePath(pathIn, item.getRegistryName().getPath());
    }

    private static Path getRecipePath(Path pathIn, String str){
        return pathIn.resolve("data/rigoranthusemortisreborn/recipes/amalgamator/" + str + ".json");
    }
    @Override
    public String getName() {
        return new TranslatableComponent("block.rigoranthusemortisreborn.psyglyphic_amalgamator").getString();
    }
}