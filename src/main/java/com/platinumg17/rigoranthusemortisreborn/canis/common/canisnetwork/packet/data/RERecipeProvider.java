package com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data;

import com.google.gson.JsonObject;
import com.platinumg17.rigoranthusemortisreborn.canis.CanisBlocks;
import com.platinumg17.rigoranthusemortisreborn.canis.CanisItems;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.CanisRecipeSerializers;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.REUtil;
import net.minecraft.data.recipes.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.world.item.Items;

import java.nio.file.Path;
import java.util.function.Consumer;

public class RERecipeProvider extends RecipeProvider {

    public RERecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public String getName() {
        return "RigoranthusEmortisReborn Recipes";
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        //TODO
        ShapedRecipeBuilder.shaped(CanisItems.THROW_BONE.get()).pattern(" X ").pattern("XYX").pattern(" X ").define('X', Items.BONE).define('Y', Items.SLIME_BALL).unlockedBy("has_bone", has(Items.BONE)).save(consumer);
        ShapelessRecipeBuilder.shapeless(CanisItems.THROW_BONE.get()).requires(CanisItems.THROW_BONE_WET.get(), 1).unlockedBy("has_throw_bone", has(CanisItems.THROW_BONE.get())).save(consumer, REUtil.getResource("throw_bone_wet"));
        ShapedRecipeBuilder.shaped(CanisItems.THROW_STICK.get(), 1).pattern(" X ").pattern("XYX").pattern(" X ").define('X', Items.STICK).define('Y', Items.SLIME_BALL).unlockedBy("has_slime_ball", has(Items.SLIME_BALL)).save(consumer);
        ShapelessRecipeBuilder.shapeless(CanisItems.THROW_STICK.get(), 1).requires(CanisItems.THROW_STICK_WET.get(), 1).unlockedBy("has_throw_stick", has(CanisItems.THROW_STICK.get())).save(consumer, REUtil.getResource("throw_stick_wet"));
        ShapelessRecipeBuilder.shapeless(CanisItems.REGULAR_TREAT.get(), 5).requires(CanisItems.TRAINING_TREAT.get(), 5).requires(Items.GOLDEN_APPLE, 1).unlockedBy("has_golden_apple", has(Items.GOLDEN_APPLE)).save(consumer);
        ShapelessRecipeBuilder.shapeless(CanisItems.HOMINI_TREAT.get(), 1).requires(CanisItems.MASTER_TREAT.get(), 5).requires(Blocks.END_STONE, 1).unlockedBy("has_master_treat", has(CanisItems.MASTER_TREAT.get())).save(consumer);
        //ShapelessRecipeBuilder.shapeless(CanisItems.BREEDING_BONE.get(), 2).requires(CanisItems.MASTER_TREAT.get(), 1).requires(Items.COOKED_BEEF, 1).requires(Items.COOKED_PORKCHOP, 1).requires(Items.COOKED_CHICKEN, 1).requires(Items.COOKED_COD, 1).unlockedBy("has_cooked_porkchop", has(Items.COOKED_PORKCHOP)).save(consumer);
        ShapelessRecipeBuilder.shapeless(CanisItems.MASTER_TREAT.get(), 5).requires(CanisItems.REGULAR_TREAT.get(), 5).requires(Items.DIAMOND, 1).unlockedBy("has_master_treat", has(CanisItems.REGULAR_TREAT.get())).save(consumer);
        ShapedRecipeBuilder.shaped(CanisItems.TRAINING_TREAT.get(), 1).pattern("TUV").pattern("XXX").pattern("YYY").define('T', Items.STRING).define('U', Items.BONE).define('V', Items.GUNPOWDER).define('X', Items.SUGAR).define('Y', Items.WHEAT).unlockedBy("has_wheat", has(Items.WHEAT)).save(consumer);
        ShapedRecipeBuilder.shaped(CanisItems.COLLAR_SHEARS.get(), 1).pattern(" X ").pattern("XYX").pattern(" X ").define('X', Items.BONE).define('Y', Items.SHEARS).unlockedBy("has_shears", has(Items.SHEARS)).save(consumer);
        ShapedRecipeBuilder.shaped(CanisItems.WHISTLE.get(), 1).pattern("IRI").pattern("II ").define('I', Items.IRON_INGOT).define('R', Items.REDSTONE).unlockedBy("has_iron_ingot", has(Items.IRON_INGOT)).save(consumer);
        ShapedRecipeBuilder.shaped(CanisBlocks.FOOD_BOWL.get(), 1).pattern("XXX").pattern("XYX").pattern("XXX").define('X', Items.IRON_INGOT).define('Y', Items.BONE).unlockedBy("has_iron_ingot", has(Items.IRON_INGOT)).save(consumer);
        ShapedRecipeBuilder.shaped(CanisItems.CHEW_STICK.get(), 1).pattern("SW").pattern("WS").define('W', Items.WHEAT).define('S', Items.SUGAR).unlockedBy("has_sugar", has(Items.SUGAR)).save(consumer);
        ShapedRecipeBuilder.shaped(CanisItems.WOOL_COLLAR.get(), 1).pattern("SSS").pattern("S S").pattern("SSS").define('S', Items.STRING).unlockedBy("has_stick", has(Items.STRING)).save(consumer);
        ShapedRecipeBuilder.shaped(CanisItems.TREAT_BAG.get(), 1).pattern("LCL").pattern("LLL").define('L', Items.LEATHER).define('C', CanisItems.CHEW_STICK.get()).unlockedBy("has_leather", has(Items.LEATHER)).save(consumer);
//        ShapedRecipeBuilder.shaped(CanisItems.CAPE.get(), 1).pattern("S S").pattern("LWL").pattern("WLW").define('L', Items.LEATHER).define('S', Items.STRING).define('W', ItemTags.WOOL).unlockedBy("has_leather", has(Items.LEATHER)).save(consumer);
        ShapedRecipeBuilder.shaped(CanisItems.SUNGLASSES.get(), 1).pattern("S S").pattern("GSG").define('S', Items.STICK).define('G', Blocks.GLASS_PANE).unlockedBy("has_stick", has(Items.STICK)).save(consumer);
        ShapedRecipeBuilder.shaped(CanisItems.TINY_BONE.get(), 1).pattern("BI").pattern("IB").define('B', Items.BONE).define('I', Items.IRON_INGOT).unlockedBy("has_iron_ingot", has(Items.IRON_INGOT)).save(consumer);
        ShapedRecipeBuilder.shaped(CanisItems.BIG_BONE.get(), 1).pattern("BI").pattern("IB").pattern("BI").define('B', Items.BONE).define('I', Items.IRON_INGOT).unlockedBy("has_iron_ingot", has(Items.IRON_INGOT)).save(consumer);

        SpecialRecipeBuilder.special(CanisRecipeSerializers.CANIS_BED.get()).save(consumer, REUtil.getResourcePath("canis_bed"));
    }

    @Override
    protected void saveAdvancement(HashCache cache, JsonObject advancementJson, Path pathIn) {
        // - We dont replace any of the advancement things yet...
    }
}