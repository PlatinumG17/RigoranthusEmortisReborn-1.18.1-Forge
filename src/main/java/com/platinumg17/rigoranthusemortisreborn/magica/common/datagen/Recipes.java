package com.platinumg17.rigoranthusemortisreborn.magica.common.datagen;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.blocks.BlockInit;
import com.platinumg17.rigoranthusemortisreborn.blocks.DecorativeOrStorageBlocks;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.CanisTags;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.core.init.ItemInit;
import com.platinumg17.rigoranthusemortisreborn.core.init.Registration;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.BlockRegistry;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.MagicItemsRegistry;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider {
    public Recipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    public static Tag.Named<Item> DOMINION_GEM_TAG = ItemTags.bind("forge:gems/mana");
    public static Tag.Named<Item> DOMINION_GEM_BLOCK_TAG = ItemTags.bind("forge:storage_blocks/mana");
    public static Tag.Named<Block> DECORATIVE_RE =  BlockTags.createOptional(RigoranthusEmortisReborn.rl("re_decorative"));
    public static Tag.Named<Block> MAGIC_SAPLINGS =  BlockTags.createOptional(RigoranthusEmortisReborn.rl("magic_saplings"));
    public static Tag.Named<Block> MAGIC_PLANTS =  BlockTags.createOptional(RigoranthusEmortisReborn.rl("magic_plants"));
    public static Tag.Named<Item> MAGIC_FOOD = ItemTags.bind("rigoranthusemortisreborn:magic_food");

    public static Tag.Named<Block> AZULOREAL_LOGS_TAG =  BlockTags.createOptional(RigoranthusEmortisReborn.rl("azuloreal_logs"));
    public static Tag.Named<Block> JESSIC_LOGS_TAG =  BlockTags.createOptional(RigoranthusEmortisReborn.rl("jessic_logs"));

    public static Ingredient DOMINION_GEM = Ingredient.of(MagicItemsRegistry.dominionGem);
    public static Ingredient DOMINION_GEM_BLOCK = Ingredient.of(BlockRegistry.DOMINION_GEM_BLOCK);

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        {
            //makeArmor("novice", consumer, MagicItemsRegistry.DOMINION_FIBER);
            makeArmor("apprentice", consumer, ItemInit.GHAST_IRON_INGOT.get());
            makeArmor("emortic", consumer, MagicItemsRegistry.DWELLER_FLESH);

            SimpleCookingRecipeBuilder.smelting(Ingredient.of(Registration.RECONDITE_ORE.get()), MagicItemsRegistry.dominionGem,0.5f, 200)
                    .unlockedBy("has_ore", InventoryChangeTrigger.TriggerInstance.hasItems(Registration.RECONDITE_ORE.get())).save(consumer);

            ShapedRecipeBuilder.shaped(MagicItemsRegistry.unadornedRing).unlockedBy("has_journal",InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                    .pattern("xyx")
                    .pattern("x x")
                    .pattern("xxx").define('x', Tags.Items.NUGGETS_IRON).define('y', Tags.Items.INGOTS_IRON).save(consumer);

            ShapedRecipeBuilder.shaped(MagicItemsRegistry.unadornedAmulet).unlockedBy("has_journal",InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                    .pattern("xyx")
                    .pattern("l l")
                    .pattern(" l ").define('x', Tags.Items.NUGGETS_IRON).define('y', Tags.Items.INGOTS_IRON).define('l', Tags.Items.LEATHER).save(consumer);
            
            ShapedRecipeBuilder.shaped(BlockRegistry.DOMINION_JAR).unlockedBy("has_journal",InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                    .pattern("xyx")
                    .pattern("x x")
                    .pattern("xxx").define('x', Tags.Items.GLASS).define('y', BlockRegistry.OPULENT_MAGMA).save(consumer);

            ShapedRecipeBuilder.shaped(BlockRegistry.EMORTIC_CRAFTING_PRESS_BLOCK).unlockedBy("has_journal",InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                    .pattern("sgs")
                    .pattern("xyx")
                    .pattern("aba").define('x', Registration.POWDERED_ESOTERICUM.get()).define('y', Items.PISTON).define('g', Tags.Items.GLASS_PANES).define('s', Items.OBSIDIAN)
                    .define('a', Tags.Items.GEMS_DIAMOND).define('b', Items.ENCHANTING_TABLE).save(consumer);

            ShapedRecipeBuilder.shaped(BlockRegistry.SPLINTERED_PEDESTAL).unlockedBy("has_journal",InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                    .pattern("xgx")
                    .pattern(" m ")
                    .pattern("bmb").define('b', Items.BLACKSTONE).define('x', Tags.Items.INGOTS_GOLD).define('m', Items.MOSSY_STONE_BRICKS)
                    .define('g', Tags.Items.GLASS_PANES).save(consumer);

            ShapedRecipeBuilder.shaped(BlockRegistry.PSYGLYPHIC_AMALG_BLOCK).unlockedBy("has_journal",InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                    .pattern("ydy")
                    .pattern("gpg")
                    .pattern("beb").define('g', Tags.Items.INGOTS_GOLD).define('b', Items.BLACKSTONE).define('p', Tags.Items.GLASS_PANES).define('d', MagicItemsRegistry.dominionGem)
                    .define('e', Registration.POWDERED_ESOTERICUM.get()).define('y', Tags.Items.GEMS_DIAMOND).save(consumer);

            ShapedRecipeBuilder.shaped(BlockRegistry.TABLE_BLOCK).unlockedBy("has_journal",InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                    .pattern("xxx")
                    .pattern("yzy")
                    .pattern("y y").define('x',Ingredient.of(BlockRegistry.AZULOREAL_SLAB))
                    .define('y', Items.STICK)
                    .define('z', Ingredient.of(CanisTags.AZULOREAL_LOGS)).save(consumer);

            ShapedRecipeBuilder.shaped(BlockRegistry.EMORTIC_CORTEX_BLOCK).unlockedBy("has_journal",InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                    .pattern("sys")
                    .pattern("yby")
                    .pattern("ses").define('s',  Tags.Items.STONE).define('y', Tags.Items.GLASS).define('b', BlockRegistry.DWELLER_BRAIN.asItem())
                    .define('e', Registration.POWDERED_ESOTERICUM.get()).save(consumer);

            shapelessBuilder(BlockRegistry.DOMINION_GEM_BLOCK,1).requires(DOMINION_GEM, 9).save(consumer);
            shapelessBuilder(MagicItemsRegistry.dominionGem, 9).requires(BlockRegistry.DOMINION_GEM_BLOCK,1).save(consumer, RigoranthusEmortisReborn.rl("dominion_gem_from_block"));

            ShapedRecipeBuilder.shaped(Items.ARROW, 2)
                    .unlockedBy("has_journal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                    .pattern("x")
                    .pattern("y")
                    .pattern("z")
                    .define('x', ItemInit.RAZORTOOTH_KUNAI.get())
                    .define('y', Items.STICK)
                    .define('z', Items.FEATHER)
                    .save(consumer, RigoranthusEmortisReborn.rl("razortooth_to_arrow"));

            shapelessBuilder(BlockRegistry.RITUAL_BLOCK)
                    .requires(BlockRegistry.SPLINTERED_PEDESTAL)
                    .requires(Recipes.DOMINION_GEM_BLOCK_TAG)
                    .requires(Ingredient.of(Tags.Items.INGOTS_GOLD), 3)
                    .save(consumer);

//            shapelessBuilder(getRitualItem(RitualLib.RESTORATION))
//                    .requires(BlockRegistry.ICHOR_JAR)
//                    .requires(Items.GOLDEN_APPLE)
//                    .requires(RigoranthusEmortisRebornAPI.getInstance().getGlyphItem(EffectHeal.INSTANCE), 1)
//                    .save(consumer);

            shapelessBuilder(MagicItemsRegistry.GREATER_EXPERIENCE_GEM)
                    .requires(MagicItemsRegistry.EXPERIENCE_GEM, 4)
                    .save(consumer);
            shapelessBuilder(MagicItemsRegistry.EXPERIENCE_GEM, 4)
                    .requires(MagicItemsRegistry.GREATER_EXPERIENCE_GEM)
                    .save(consumer);

            STONECUTTER_COUNTER = 1;

            ShapedRecipeBuilder.shaped(BlockRegistry.EMORTIC_RELAY).unlockedBy("has_journal",InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                    .pattern("gbg")
                    .pattern("gMg")
                    .pattern("gbg")
                    .define('g', Tags.Items.INGOTS_GOLD)
                    .define('M', DOMINION_GEM_BLOCK)
                    .define('b', Items.BLACKSTONE)
                    .save(consumer);

//            shapelessBuilder(getRitualItem(RitualLib.BINDING))
//                    .requires(BlockRegistry.ICHOR_JAR)
//                    .requires(MagicItemsRegistry.BLANK_PARCHMENT)
//                    .requires(Items.ENDER_PEARL, 1)
//                    .requires(DOMINION_GEM, 3)
//                    .save(consumer);
        }
    }

//    public Item getRitualItem(String id){
//        return RigoranthusEmortisRebornAPI.getInstance().getRitualItemMap().get(id);
//    }

    public static ShapedRecipeBuilder makeWood(ItemLike logs, ItemLike wood, int count){
        return ShapedRecipeBuilder.shaped(wood, count).unlockedBy("has_journal",InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .pattern("xx ")
                .pattern("xx ").define('x', logs);
    }
    private static void shapedWoodenTrapdoor(Consumer<FinishedRecipe> recipeConsumer, ItemLike trapdoor, ItemLike input) {
        ShapedRecipeBuilder.shaped(trapdoor, 2).define('#', input).pattern("###").pattern("###").group("wooden_trapdoor")
                .unlockedBy("has_journal",InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE)).save(recipeConsumer);
    }
    public static void shapedWoodenStairs(Consumer<FinishedRecipe> recipeConsumer, ItemLike stairs, ItemLike input) {
        ShapedRecipeBuilder.shaped(stairs, 4)
                .define('#', input)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###").unlockedBy("has_journal",InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(recipeConsumer);
    }
    private static void shapelessWoodenButton(Consumer<FinishedRecipe> recipeConsumer, ItemLike button, ItemLike input) {
        ShapelessRecipeBuilder.shapeless(button).requires(input)
                .unlockedBy("has_journal",InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(recipeConsumer);
    }
    private static void strippedLogToWood(Consumer<FinishedRecipe> recipeConsumer, ItemLike stripped, ItemLike output) {
        ShapedRecipeBuilder.shaped(output, 3).define('#', stripped).pattern("##").pattern("##").group("bark")
                .unlockedBy("has_journal",InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(recipeConsumer);
    }
//    private static void shapedWoodenDoor(Consumer<IFinishedRecipe> recipeConsumer, ItemLike door, ItemLike input) {
//        ShapedRecipeBuilder.shaped(door, 3).define('#', input).pattern("##").pattern("##").pattern("##").group("wooden_door")
//                .unlockedBy("has_journal",InventoryChangeTrigger.Instance.hasItems(Items.CRAFTING_TABLE))
//                .save(recipeConsumer);
//    }
//    private static void shapedWoodenFence(Consumer<IFinishedRecipe> recipeConsumer, ItemLike fence, ItemLike input) {
//        ShapedRecipeBuilder.shaped(fence, 3).define('#', Items.STICK).define('W', input).pattern("W#W").pattern("W#W").group("wooden_fence")
//                .unlockedBy("has_journal",InventoryChangeTrigger.Instance.hasItems(Items.CRAFTING_TABLE))
//                .save(recipeConsumer);
//    }
//    private static void shapedWoodenFenceGate(Consumer<IFinishedRecipe> recipeConsumer, ItemLike fenceGate, ItemLike input) {
//        ShapedRecipeBuilder.shaped(fenceGate).define('#', Items.STICK).define('W', input).pattern("#W#").pattern("#W#").group("wooden_fence_gate")
//                .unlockedBy("has_journal",InventoryChangeTrigger.Instance.hasItems(Items.CRAFTING_TABLE))
//                .save(recipeConsumer);
//    }
//    private static void shapedWoodenPressurePlate(Consumer<IFinishedRecipe> recipeConsumer, ItemLike pressurePlate, ItemLike input) {
//        ShapedRecipeBuilder.shaped(pressurePlate).define('#', input).pattern("##").group("wooden_pressure_plate")
//                .unlockedBy("has_journal",InventoryChangeTrigger.Instance.hasItems(Items.CRAFTING_TABLE))
//                .save(recipeConsumer);
//    }
//    private static void shapedWoodenSlab(Consumer<IFinishedRecipe> recipeConsumer, ItemLike slab, ItemLike input) {
//        ShapedRecipeBuilder.shaped(slab, 6).define('#', input).pattern("###").group("wooden_slab")
//                .unlockedBy("has_journal",InventoryChangeTrigger.Instance.hasItems(Items.CRAFTING_TABLE))
//                .save(recipeConsumer);
//    }

    public ShapelessRecipeBuilder shapelessBuilder(ItemLike result){
        return shapelessBuilder(result, 1);
    }

    public ShapelessRecipeBuilder shapelessBuilder(ItemLike result, int resultCount){
        return ShapelessRecipeBuilder.shapeless(result, resultCount).unlockedBy("has_journal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE));
    }

    private static int STONECUTTER_COUNTER = 0;
    public static void makeStonecutter(Consumer<FinishedRecipe> consumer, ItemLike input, ItemLike output, String reg){
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), output).unlockedBy("has_journal",InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE)).save(consumer, RigoranthusEmortisReborn.rl(reg + "_"+STONECUTTER_COUNTER));
        STONECUTTER_COUNTER++;
    }

    public static void makeArmor(String prefix, Consumer<FinishedRecipe> consumer, Item material){
        ShapedRecipeBuilder.shaped(ForgeRegistries.ITEMS.getValue(RigoranthusEmortisReborn.rl(prefix + "_boots")))
                .pattern("   ")
                .pattern("x x")
                .pattern("x x").define('x', material).group(EmortisConstants.MOD_ID)
                .unlockedBy("has_journal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ForgeRegistries.ITEMS.getValue(RigoranthusEmortisReborn.rl(prefix + "_leggings")))
                .pattern("xxx")
                .pattern("x x")
                .pattern("x x").define('x', material).group(EmortisConstants.MOD_ID)
                .unlockedBy("has_journal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ForgeRegistries.ITEMS.getValue(RigoranthusEmortisReborn.rl(prefix + "_hood")))
                .pattern("xxx")
                .pattern("x x")
                .pattern("   ").define('x', material).group(EmortisConstants.MOD_ID)
                .unlockedBy("has_journal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ForgeRegistries.ITEMS.getValue(RigoranthusEmortisReborn.rl(prefix + "_robes")))
                .pattern("x x")
                .pattern("xxx")
                .pattern("xxx").define('x', material).group(EmortisConstants.MOD_ID)
                .unlockedBy("has_journal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer);
    }
}