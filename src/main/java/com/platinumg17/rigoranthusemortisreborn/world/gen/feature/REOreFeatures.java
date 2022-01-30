package com.platinumg17.rigoranthusemortisreborn.world.gen.feature;

import com.platinumg17.rigoranthusemortisreborn.config.Config;
import com.platinumg17.rigoranthusemortisreborn.core.init.Registration;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class REOreFeatures {
    public static final RuleTest NATURAL_STONE = new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);
    public static final RuleTest STONE_ORE_REPLACEABLES = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
    public static final RuleTest DEEPSLATE_ORE_REPLACEABLES = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
    public static final RuleTest NETHERRACK = new BlockMatchTest(Blocks.NETHERRACK);
    public static final RuleTest NETHER_ORE_REPLACEABLES = new TagMatchTest(BlockTags.BASE_STONE_NETHER);
    public static final List<OreConfiguration.TargetBlockState> ORE_RECONDITE_TARGET_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, Registration.RECONDITE_ORE.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, Registration.RECONDITE_ORE.get().defaultBlockState()));

//    public static final List<OreConfiguration.TargetBlockState> OVERWORLD_RECONDITE_ORES = List.of(
//            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, Registration.RECONDITE_ORE.get().defaultBlockState()),
//            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, Registration.RECONDITE_ORE.get().defaultBlockState()));
//
//    public static final ConfiguredFeature<?, ?> COBALT_ORE = FeatureUtils.register("recondite_ore",
//            Feature.ORE.configured(new OreConfiguration(OVERWORLD_RECONDITE_ORES, Config.maxVeinSize.get(), Config.veinsPerChunk.get())));

    public static final ConfiguredFeature<?, ?> ORE_RECONDITE_SMALL = FeatureUtils.register("ore_recondite_small", Feature.ORE.configured(new OreConfiguration(ORE_RECONDITE_TARGET_LIST, 4, 0.5F)));
    public static final ConfiguredFeature<?, ?> ORE_RECONDITE_LARGE = FeatureUtils.register("ore_recondite_large", Feature.ORE.configured(new OreConfiguration(ORE_RECONDITE_TARGET_LIST, 12, 0.7F)));
    public static final ConfiguredFeature<?, ?> ORE_RECONDITE_BURIED = FeatureUtils.register("ore_recondite_buried", Feature.ORE.configured(new OreConfiguration(ORE_RECONDITE_TARGET_LIST, 8, 1.0F)));
}
