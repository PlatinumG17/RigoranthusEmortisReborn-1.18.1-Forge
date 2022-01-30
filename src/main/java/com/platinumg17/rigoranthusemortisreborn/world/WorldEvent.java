package com.platinumg17.rigoranthusemortisreborn.world;

import com.google.common.collect.ImmutableList;
import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.config.Config;
import com.platinumg17.rigoranthusemortisreborn.core.init.Registration;
import com.platinumg17.rigoranthusemortisreborn.magica.common.block.DominionBerryBush;
import com.platinumg17.rigoranthusemortisreborn.magica.common.lib.LibBlockNames;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.BlockRegistry;
import com.platinumg17.rigoranthusemortisreborn.world.gen.feature.REOreFeatures;
import com.platinumg17.rigoranthusemortisreborn.world.trees.SupplierBlockStateProvider;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaPineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AlterGroundDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = EmortisConstants.MOD_ID)
public class WorldEvent {
    public static ConfiguredFeature<TreeConfiguration, ?> AZULOREAL_TREE = Feature.TREE.configured((
                    new TreeConfiguration.TreeConfigurationBuilder(
                    new SupplierBlockStateProvider(LibBlockNames.AZULOREAL_LOG),
                    new FancyTrunkPlacer(3, 11, 0),
                    new SupplierBlockStateProvider(LibBlockNames.AZULOREAL_LEAVES),
                    new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                    new TwoLayersFeatureSize(0, 0, 0))).ignoreVines().build());//.heightmap(Heightmap.Types.MOTION_BLOCKING).build());
    public static ConfiguredFeature<TreeConfiguration, ?> LOOMING_AZULOREAL_TREE = Feature.TREE.configured((
                    new TreeConfiguration.TreeConfigurationBuilder(
                    new SupplierBlockStateProvider(LibBlockNames.AZULOREAL_LOG),
                    new ForkingTrunkPlacer(5, 2, 2),
                    new SupplierBlockStateProvider(LibBlockNames.AZULOREAL_LEAVES),
                    new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                    new TwoLayersFeatureSize(1, 0, 2))).ignoreVines().build());//.heightmap(Heightmap.Types.MOTION_BLOCKING).build());
    public static ConfiguredFeature<TreeConfiguration, ?> MEGA_AZULOREAL_TREE = Feature.TREE.configured((
                    new TreeConfiguration.TreeConfigurationBuilder(
                    new SupplierBlockStateProvider(LibBlockNames.AZULOREAL_LOG),
                    new GiantTrunkPlacer(12, 2, 14),
                    new SupplierBlockStateProvider(LibBlockNames.AZULOREAL_LEAVES),
                    new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), ConstantInt.of(13)),
                    new TwoLayersFeatureSize(1, 1, 2))).decorators(ImmutableList.of(
                    new AlterGroundDecorator(new SupplierBlockStateProvider(LibBlockNames.FRAGMENTED_COBBLESTONE)))).build());
    public static ConfiguredFeature<TreeConfiguration, ?> JESSIC_TREE = Feature.TREE.configured((
                    new TreeConfiguration.TreeConfigurationBuilder(
                            new SupplierBlockStateProvider(LibBlockNames.JESSIC_LOG),
                            new FancyTrunkPlacer(3, 11, 0),
                            new SupplierBlockStateProvider(LibBlockNames.JESSIC_LEAVES),
                            new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),

                            new TwoLayersFeatureSize(0, 0, 0))).ignoreVines().build());//.heightmap(Heightmap.Types.MOTION_BLOCKING).build());
    public static ConfiguredFeature<TreeConfiguration, ?> LOOMING_JESSIC_TREE = Feature.TREE.configured((
                    new TreeConfiguration.TreeConfigurationBuilder(
                            new SupplierBlockStateProvider(LibBlockNames.JESSIC_LOG),
                            new ForkingTrunkPlacer(5, 2, 2),
                            new SupplierBlockStateProvider(LibBlockNames.JESSIC_LEAVES),
                            new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                            new TwoLayersFeatureSize(1, 0, 2))).ignoreVines().build());//.heightmap(Heightmap.Types.MOTION_BLOCKING).build());
    public static ConfiguredFeature<TreeConfiguration, ?> MEGA_JESSIC_TREE = Feature.TREE.configured((
                    new TreeConfiguration.TreeConfigurationBuilder(
                            new SupplierBlockStateProvider(LibBlockNames.JESSIC_LOG),
                            new GiantTrunkPlacer(12, 2, 14),
                            new SupplierBlockStateProvider(LibBlockNames.JESSIC_LEAVES),
                            new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), ConstantInt.of(13)),
                            new TwoLayersFeatureSize(1, 1, 2))).decorators(ImmutableList.of(
                    new AlterGroundDecorator(new SupplierBlockStateProvider(LibBlockNames.FRAGMENTED_COBBLESTONE)))).build());
//    public static final StructureProcessorList VERDUROUS_PLAINS =
//            new StructureProcessorList(ImmutableList.of(new RuleStructureProcessor(
//                    ImmutableList.of(new RuleEntry(new RandomBlockMatchRuleTest(Blocks.COBBLESTONE, 0.8F), AlwaysTrueRuleTest.INSTANCE, Blocks.MOSSY_COBBLESTONE.defaultBlockState()),
//                            new RuleEntry(new TagMatchRuleTest(BlockTags.DOORS), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.defaultBlockState()),
//                            new RuleEntry(new BlockMatchRuleTest(Blocks.TORCH), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.defaultBlockState()),
//                            new RuleEntry(new BlockMatchRuleTest(Blocks.WALL_TORCH), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.defaultBlockState()),
//                            new RuleEntry(new RandomBlockMatchRuleTest(Blocks.COBBLESTONE, 0.07F), AlwaysTrueRuleTest.INSTANCE, Blocks.COBWEB.defaultBlockState()),
//                            new RuleEntry(new RandomBlockMatchRuleTest(Blocks.MOSSY_COBBLESTONE, 0.07F), AlwaysTrueRuleTest.INSTANCE, Blocks.COBWEB.defaultBlockState()),
//                            new RuleEntry(new RandomBlockMatchRuleTest(Blocks.WHITE_TERRACOTTA, 0.07F), AlwaysTrueRuleTest.INSTANCE, Blocks.COBWEB.defaultBlockState()),
//                            new RuleEntry(new RandomBlockMatchRuleTest(Blocks.OAK_LOG, 0.05F), AlwaysTrueRuleTest.INSTANCE, Blocks.COBWEB.defaultBlockState()),
//                            new RuleEntry(new RandomBlockMatchRuleTest(Blocks.OAK_PLANKS, 0.1F), AlwaysTrueRuleTest.INSTANCE, Blocks.COBWEB.defaultBlockState()),
//                            new RuleEntry(new RandomBlockMatchRuleTest(Blocks.OAK_STAIRS, 0.1F), AlwaysTrueRuleTest.INSTANCE, Blocks.COBWEB.defaultBlockState()),
//                            new RuleEntry(new RandomBlockMatchRuleTest(Blocks.STRIPPED_OAK_LOG, 0.02F), AlwaysTrueRuleTest.INSTANCE, Blocks.COBWEB.defaultBlockState()),
//                            new RuleEntry(new BlockStateMatchRuleTest(Blocks.GLASS_PANE.defaultBlockState().setValue(PaneBlock.NORTH, Boolean.TRUE).setValue(PaneBlock.SOUTH, Boolean.TRUE)), AlwaysTrueRuleTest.INSTANCE, Blocks.BROWN_STAINED_GLASS_PANE.defaultBlockState().setValue(PaneBlock.NORTH, Boolean.TRUE).setValue(PaneBlock.SOUTH, Boolean.TRUE)),
//                            new RuleEntry(new BlockStateMatchRuleTest(Blocks.GLASS_PANE.defaultBlockState().setValue(PaneBlock.EAST, Boolean.TRUE).setValue(PaneBlock.WEST, Boolean.TRUE)), AlwaysTrueRuleTest.INSTANCE, Blocks.BROWN_STAINED_GLASS_PANE.defaultBlockState().setValue(PaneBlock.EAST, Boolean.TRUE).setValue(PaneBlock.WEST, Boolean.TRUE)),
//                            new RuleEntry(new RandomBlockMatchRuleTest(Blocks.WHEAT, 0.3F), AlwaysTrueRuleTest.INSTANCE, Blocks.CARROTS.defaultBlockState()),
//                            new RuleEntry(new RandomBlockMatchRuleTest(Blocks.WHEAT, 0.2F), AlwaysTrueRuleTest.INSTANCE, Blocks.POTATOES.defaultBlockState()),
//                            new RuleEntry(new RandomBlockMatchRuleTest(Blocks.WHEAT, 0.1F), AlwaysTrueRuleTest.INSTANCE, Blocks.BEETROOTS.defaultBlockState())))));
//    public static final ResourceLocation EXTRA_RECONDITE_ORE = RigoranthusEmortisReborn.rl("recondite_ore_extra");

//    public static final Feature<BlockStateConfiguration> LIGHTS = new SingleBlockFeature(BlockStateConfiguration.CODEC) {
//        public void onStatePlace(ISeedReader seed, ChunkGenerator chunkGenerator, Random rand, BlockPos pos, BlockStateConfiguration config) {
//            if(seed instanceof WorldGenRegion){
//                WorldGenRegion world = (WorldGenRegion) seed;
//                Random random = world.getRandom();
//                if(world.getBlockEntity(pos) instanceof LightTile){
//                    LightTile tile = (LightTile) world.getBlockEntity(pos);
//                    tile.red = Math.max(10, random.nextInt(255));
//                    tile.green = Math.max(10, random.nextInt(255));
//                    tile.blue = Math.max(10, random.nextInt(255));
//                }
//            }
//        }
//    };
    public static void registerFeatures() {
//        int maxVeinClusterSize = Config.maxVeinSize.get();
//        int minOreY = Config.minOreHeight.get();
//        int maxOreY = Config.maxOreHeight.get();
//        int chunkVeinDensity = Config.veinsPerChunk.get();
        ConfiguredFeature<?, ?> PATCH_DOMINION_BERRY_BUSH = FeatureUtils.register("rigoranthusemortisreborn:patch_dominion_berry", Feature.RANDOM_PATCH.configured(FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(BlockRegistry.DOMINION_BERRY_BUSH.defaultBlockState().setValue(DominionBerryBush.AGE, 2)))), List.of(Blocks.GRASS_BLOCK))));
        PlacedFeature DOMINION_BERRY_BUSH_PATCH_CONFIG = PlacementUtils.register("rigoranthusemortisreborn:placed_dominion_bush", PATCH_DOMINION_BERRY_BUSH.placed(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));

        ConfiguredFeature<?, ?> PATCH_SPECTABILIS_BERRY_BUSH = FeatureUtils.register("rigoranthusemortisreborn:patch_bilis_berry", Feature.RANDOM_PATCH.configured(FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(BlockRegistry.SPECTABILIS_BUSH.defaultBlockState().setValue(DominionBerryBush.AGE, 3)))), List.of(Blocks.GRASS_BLOCK))));
        PlacedFeature BILIS_BERRY_BUSH_PATCH_CONFIG = PlacementUtils.register("rigoranthusemortisreborn:placed_spectabilis_bush", PATCH_SPECTABILIS_BERRY_BUSH.placed(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));

        ConfiguredFeature<?, ?> PATCH_AZULOREAL_ORCHID = FeatureUtils.register("rigoranthusemortisreborn:patch_azuloreal_orchid", Feature.RANDOM_PATCH.configured(FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(BlockRegistry.AZULOREAL_ORCHID.defaultBlockState()))), List.of(Blocks.GRASS_BLOCK))));
        PlacedFeature AZULOREAL_ORCHID_PATCH_CONFIG = PlacementUtils.register("rigoranthusemortisreborn:placed_azuloreal_orchid", PATCH_AZULOREAL_ORCHID.placed(RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));

        ConfiguredFeature<?, ?> PATCH_IRIDESCENT_SPROUTS = FeatureUtils.register("rigoranthusemortisreborn:patch_iridescent_sprouts", Feature.RANDOM_PATCH.configured(FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(BlockRegistry.IRIDESCENT_SPROUTS.defaultBlockState()))), List.of(Blocks.GRASS_BLOCK))));
        PlacedFeature IRIDESCENT_SPROUTS_PATCH_CONFIG = PlacementUtils.register("rigoranthusemortisreborn:placed_iridescent_sprouts", PATCH_IRIDESCENT_SPROUTS.placed(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));

        ConfiguredFeature<?, ?> PATCH_LISIANTHUS = FeatureUtils.register("rigoranthusemortisreborn:patch_lisianthus", Feature.RANDOM_PATCH.configured(FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(BlockRegistry.LISIANTHUS.defaultBlockState()))), List.of(Blocks.GRASS_BLOCK))));
        PlacedFeature LISIANTHUS_PATCH_CONFIG = PlacementUtils.register("rigoranthusemortisreborn:placed_lisianthus", PATCH_LISIANTHUS.placed(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));

        ConfiguredFeature<?, ?> RECONDITE_ORE = FeatureUtils.register("rigoranthusemortisreborn:recondite_ore", Feature.ORE.configured(new OreConfiguration(REOreFeatures.ORE_RECONDITE_TARGET_LIST, 1, 1)));
        PlacedFeature RECONDITE_CONFIG = PlacementUtils.register("rigoranthusemortisreborn:placed_recondite", RECONDITE_ORE.placed(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()));

        float azulorealChance = Config.azulorealSpawnWeight.get().floatValue();
        float jessicChance = Config.jessicSpawnWeight.get().floatValue();
        float loomingAzulorealChance = Config.loomingAzulorealSpawnWeight.get().floatValue();
        float loomingJessicChance = Config.loomingJessicSpawnWeight.get().floatValue();
        float megaAzulorealChance = Config.megaAzulorealSpawnWeight.get().floatValue();
        float megaJessicChance = Config.megaJessicSpawnWeight.get().floatValue();

        PlacedFeature PLACED_AZULOREAL = PlacementUtils.register("rigoranthusemortisreborn:placed_azuloreal", AZULOREAL_TREE.filteredByBlockSurvival(BlockRegistry.AZULOREAL_SAPLING));
        PlacedFeature PLACED_LOOMING_AZULOREAL = PlacementUtils.register("rigoranthusemortisreborn:placed_looming_azuloreal", LOOMING_AZULOREAL_TREE.filteredByBlockSurvival(BlockRegistry.AZULOREAL_SAPLING));
        PlacedFeature PLACED_MEGA_AZULOREAL = PlacementUtils.register("rigoranthusemortisreborn:placed_mega_azuloreal", MEGA_AZULOREAL_TREE.filteredByBlockSurvival(BlockRegistry.AZULOREAL_SAPLING));
        PlacedFeature PLACED_JESSIC = PlacementUtils.register("rigoranthusemortisreborn:placed_jessic", JESSIC_TREE.filteredByBlockSurvival(BlockRegistry.JESSIC_SAPLING));
        PlacedFeature PLACED_LOOMING_JESSIC = PlacementUtils.register("rigoranthusemortisreborn:placed_looming_jessic", LOOMING_JESSIC_TREE.filteredByBlockSurvival(BlockRegistry.JESSIC_SAPLING));
        PlacedFeature PLACED_MEGA_JESSIC = PlacementUtils.register("rigoranthusemortisreborn:placed_mega_jessic", MEGA_JESSIC_TREE.filteredByBlockSurvival(BlockRegistry.JESSIC_SAPLING));

        ConfiguredFeature<RandomFeatureConfiguration, ?> CONFIGURED = FeatureUtils.register("rigoranthusemortisreborn:random_azuloreal", Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(PLACED_AZULOREAL, 0.25f),
                new WeightedPlacedFeature(PLACED_LOOMING_AZULOREAL, 0.25f),
                new WeightedPlacedFeature(PLACED_MEGA_AZULOREAL, 0.25f),
                new WeightedPlacedFeature(PLACED_JESSIC, 0.25f),
                new WeightedPlacedFeature(PLACED_LOOMING_JESSIC, 0.25f),
                new WeightedPlacedFeature(PLACED_MEGA_JESSIC, 0.25f)), PLACED_AZULOREAL)));
        PlacementUtils.register("rigoranthusemortisreborn:placed_random_verdurous",CONFIGURED.placed(VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(100))));

//        BlockClusterFeatureConfig DOMINION_BERRY_BUSH_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedPlacedFeature()).add(BlockRegistry.DOMINION_BERRY_BUSH.defaultBlockState().setValue(DominionBerryBush.AGE, 3), 80), SimpleBlockPlacer.INSTANCE)).tries(64).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).noProjection().build();
//        BlockClusterFeatureConfig BERRY_BUSH_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedPlacedFeature()).add(BlockRegistry.SPECTABILIS_BUSH.defaultBlockState().setValue(SpectabilisBushBlock.AGE, 3), 80), SimpleBlockPlacer.INSTANCE)).tries(64).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).noProjection().build();
//        BlockClusterFeatureConfig ORCHID_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedPlacedFeature()).add(BlockRegistry.AZULOREAL_ORCHID.defaultBlockState(), 80), SimpleBlockPlacer.INSTANCE)).tries(64).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).noProjection().build();
//        BlockClusterFeatureConfig IRIDESCENT_SPROUT_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedPlacedFeature()).add(BlockRegistry.IRIDESCENT_SPROUTS.defaultBlockState(), 70), SimpleBlockPlacer.INSTANCE)).tries(64).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).noProjection().build();
//        BlockClusterFeatureConfig LISIANTHUS_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedPlacedFeature()).add(BlockRegistry.LISIANTHUS.defaultBlockState(), 90), DoublePlantBlockPlacer.INSTANCE)).tries(64).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).noProjection().build();

//        ConfiguredFeature<?, ?> JESSIC = JESSIC_TREE
//                .decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(PlacementUtils.countExtra(1, 1, 1).type(PlacementModifierType.SURFACE_RELATIVE_THRESHOLD_FILTER).COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, jessicChance, 1)));
//        ConfiguredFeature<?, ?> LOOMING_JESSIC = LOOMING_JESSIC_TREE
//                .decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(PlacementUtils.countExtra(1, 1, 1).type(PlacementModifierType.SURFACE_RELATIVE_THRESHOLD_FILTER).COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, loomingJessicChance, 1)));
//        ConfiguredFeature<?, ?> MEGA_JESSIC = MEGA_JESSIC_TREE
//                .decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(PlacementUtils.countExtra(1, 1, 1).type(PlacementModifierType.SURFACE_RELATIVE_THRESHOLD_FILTER).COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, megaJessicChance, 1)));
//        ConfiguredFeature<?, ?> AZULOREAL = AZULOREAL_TREE
//                .decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(PlacementUtils.countExtra(1, 1, 1).type(PlacementModifierType.SURFACE_RELATIVE_THRESHOLD_FILTER).COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, azulorealChance, 1)));
//        ConfiguredFeature<?, ?> LOOMING_AZULOREAL = LOOMING_AZULOREAL_TREE
//                .decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(PlacementUtils.countExtra(1, 1, 1).type(PlacementModifierType.SURFACE_RELATIVE_THRESHOLD_FILTER).COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, loomingAzulorealChance, 1)));
//        ConfiguredFeature<?, ?> MEGA_AZULOREAL = MEGA_AZULOREAL_TREE
//                .decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(PlacementUtils.countExtra(1, 1, 1).type(PlacementModifierType.SURFACE_RELATIVE_THRESHOLD_FILTER).COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, megaAzulorealChance, 1)));

//        ConfiguredFeature<?, ?> JESSIC_COMMON = JESSIC_TREE.decorated(Features.Placements.HEIGHTMAP_SQUARE)
//                .decorated(PlacementUtils.countExtra(1, 1, 1).type(PlacementModifierType.SURFACE_RELATIVE_THRESHOLD_FILTER).COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(10, 0.01f, 1)));
//        ConfiguredFeature<?, ?> AZULOREAL_COMMON = AZULOREAL_TREE.decorated(Features.Placements.HEIGHTMAP_SQUARE)
//                .decorated(PlacementUtils.countExtra(1, 1, 1).type(PlacementModifierType.SURFACE_RELATIVE_THRESHOLD_FILTER).COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(10, 0.01f, 1)));
//        ConfiguredFeature<?, ?> LOOMING_JESSIC_COMMON = LOOMING_JESSIC_TREE.decorated(Features.Placements.HEIGHTMAP_SQUARE)
//                .decorated(PlacementUtils.countExtra(1, 1, 1).type(PlacementModifierType.SURFACE_RELATIVE_THRESHOLD_FILTER).COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(8, 0.01f, 1)));
//        ConfiguredFeature<?, ?> LOOMING_AZULOREAL_COMMON = LOOMING_AZULOREAL_TREE.decorated(Features.Placements.HEIGHTMAP_SQUARE)
//                .decorated(PlacementUtils.countExtra(1, 1, 1).type(PlacementModifierType.SURFACE_RELATIVE_THRESHOLD_FILTER).COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(8, 0.01f, 1)));
//        ConfiguredFeature<?, ?> MEGA_JESSIC_COMMON = MEGA_JESSIC_TREE.decorated(Features.Placements.HEIGHTMAP_SQUARE)
//                .decorated(PlacementUtils.countExtra(1, 1, 1).type(PlacementModifierType.SURFACE_RELATIVE_THRESHOLD_FILTER).COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(6, 0.01f, 1)));
//        ConfiguredFeature<?, ?> MEGA_AZULOREAL_COMMON = MEGA_AZULOREAL_TREE.decorated(Features.Placements.HEIGHTMAP_SQUARE)
//                .decorated(PlacementUtils.countExtra(1, 1, 1).type(PlacementModifierType.SURFACE_RELATIVE_THRESHOLD_FILTER).COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(6, 0.01f, 1)));

//        ConfiguredFeature<?, ?> RANDOM_LIGHTS = LIGHTS.configured(new BlockStateConfiguration(BlockRegistry.LIGHT_BLOCK.defaultBlockState()))
//                .decorated(Features.Placements.HEIGHTMAP_SQUARE)
//                .decorated(PlacementUtils.countExtra(1, 1, 1).type(PlacementModifierType.SURFACE_RELATIVE_THRESHOLD_FILTER).COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, 0.5f, 1)));

//        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, BlockRegistry.RECONDITE_ORE.getRegistryName(),
//                Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
//                        BlockRegistry.RECONDITE_ORE.defaultBlockState(), chunkVeinDensity)).decorated(
//                        PlacementUtils.countExtra(1, 1, 1..type(PlacementModifierType.SURFACE_RELATIVE_THRESHOLD_FILTER)RANGE.configured(new TopSolidRangeConfig(minOreY, maxOreY, maxVeinClusterSize))).squared().count(chunkVeinDensity));
//
//        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, EXTRA_RECONDITE_ORE,
//                Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
//                        BlockRegistry.RECONDITE_ORE.defaultBlockState(), chunkVeinDensity + 5)).decorated(
//                        PlacementUtils.countExtra(1, 1, 1..type(PlacementModifierType.SURFACE_RELATIVE_THRESHOLD_FILTER)RANGE.configured(new TopSolidRangeConfig(minOreY, maxOreY, maxVeinClusterSize))).squared().count(chunkVeinDensity + 5));

//        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, BlockRegistry.SPECTABILIS_BUSH.getRegistryName(), Feature.RANDOM_PATCH.configured(BERRY_BUSH_PATCH_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE));
//        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, BlockRegistry.DOMINION_BERRY_BUSH.getRegistryName(), Feature.RANDOM_PATCH.configured(DOMINION_BERRY_BUSH_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE));
//        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, BlockRegistry.LISIANTHUS.getRegistryName(), Feature.RANDOM_PATCH.configured(LISIANTHUS_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE));
//        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, BlockRegistry.IRIDESCENT_SPROUTS.getRegistryName(), Feature.RANDOM_PATCH.configured(IRIDESCENT_SPROUT_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(8));
//        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, BlockRegistry.AZULOREAL_ORCHID.getRegistryName(), Feature.RANDOM_PATCH.configured(ORCHID_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(8));
//
//        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, RANDOM_LIGHTS_LOC, RANDOM_LIGHTS);
//        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, AZULOREAL_TREE_LOC, AZULOREAL);
//        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, JESSIC_TREE_LOC, JESSIC);
//        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, LOOMING_AZULOREAL_TREE_LOC, LOOMING_AZULOREAL);
//        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, LOOMING_JESSIC_TREE_LOC, LOOMING_JESSIC);
//        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MEGA_AZULOREAL_TREE_LOC, MEGA_AZULOREAL);
//        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MEGA_JESSIC_TREE_LOC, MEGA_JESSIC);

//        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, VERDUROUS_AZULOREAL_TREES, Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(
//                AZULOREAL_COMMON.weighted(0.15f),
//                MEGA_AZULOREAL_COMMON.weighted(0.12f),
//                LOOMING_AZULOREAL_COMMON.weighted(0.12f)),
//                AZULOREAL_TREE.decorated(Features.Placements.HEIGHTMAP_SQUARE)
//                .decorated(PlacementUtils.countExtra(1, 1, 1).type(PlacementModifierType.SURFACE_RELATIVE_THRESHOLD_FILTER).COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, 0, 0))))));
//
//        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, VERDUROUS_JESSIC_TREES, Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(
//                JESSIC_COMMON.weighted(0.15f),
//                LOOMING_JESSIC_COMMON.weighted(0.12f),
//                MEGA_JESSIC_COMMON.weighted(0.12f)),
//                JESSIC_TREE.decorated(Features.Placements.HEIGHTMAP_SQUARE)
//                .decorated(PlacementUtils.countExtra(1, 1, 1).type(PlacementModifierType.SURFACE_RELATIVE_THRESHOLD_FILTER).COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, 0, 0))))));
//
//        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, VANILLA_VERDUROUS, Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(
//                FANCY_OAK.weighted(0.5f),
//                FANCY_OAK_BEES_0002.weighted(0.2f),
//                FANCY_OAK_BEES_005.weighted(0.2f),
//                DARK_OAK.weighted(0.1f)),
//                AZULOREAL_TREE.decorated(Features.Placements.HEIGHTMAP_SQUARE)
//                .decorated(PlacementUtils.countExtra(1, 1, 1).type(PlacementModifierType.SURFACE_RELATIVE_THRESHOLD_FILTER).COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, 0, 0))))));
    }
}