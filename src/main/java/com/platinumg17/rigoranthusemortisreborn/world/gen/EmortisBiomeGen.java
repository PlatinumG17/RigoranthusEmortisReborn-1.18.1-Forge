package com.platinumg17.rigoranthusemortisreborn.world.gen;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.config.Config;
import com.platinumg17.rigoranthusemortisreborn.core.init.Registration;
import com.platinumg17.rigoranthusemortisreborn.magica.common.entity.ModEntities;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.BlockRegistry;
import com.platinumg17.rigoranthusemortisreborn.world.WorldEvent;
import com.platinumg17.rigoranthusemortisreborn.world.gen.feature.BiomeMusic;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.BiConsumer;

import static com.platinumg17.rigoranthusemortisreborn.world.gen.feature.FeatureLib.*;
import static net.minecraftforge.common.BiomeDictionary.Type.*;

@Mod.EventBusSubscriber(modid = EmortisConstants.MOD_ID)
public class EmortisBiomeGen {

    private static final int NORMAL_WATER_COLOR = 4159204;
    private static final int NORMAL_WATER_FOG_COLOR = 329011;
    private static final int OVERWORLD_FOG_COLOR = 12638463;
    @Nullable
    private static final Music NORMAL_MUSIC = null;

    protected static int calculateSkyColor(float color) {
        float $$1 = color / 3.0F;
        $$1 = Mth.clamp($$1, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - $$1 * 0.05F, 0.5F + $$1 * 0.1F, 1.0F);
    }

    private static Biome biome(Biome.Precipitation precipitation, Biome.BiomeCategory category, float temperature, float downfall, MobSpawnSettings.Builder spawnBuilder, BiomeGenerationSettings.Builder biomeBuilder, @Nullable Music music) {
        return biome(precipitation, category, temperature, downfall, 4159204, 329011, spawnBuilder, biomeBuilder, music);
    }

    private static Biome biome(Biome.Precipitation precipitation, Biome.BiomeCategory category, float temperature, float downfall, int waterColor, int waterFogColor, MobSpawnSettings.Builder spawnBuilder, BiomeGenerationSettings.Builder biomeBuilder, @Nullable Music music) {
        return (new Biome.BiomeBuilder()).precipitation(precipitation).biomeCategory(category).temperature(temperature).downfall(downfall).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(waterColor).waterFogColor(waterFogColor).fogColor(12638463).skyColor(calculateSkyColor(temperature)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(music).build()).mobSpawnSettings(spawnBuilder.build()).generationSettings(biomeBuilder.build()).build();
    }

    //___________________  W O O D L A N D S  ____________________//

    private static Biome makeVerdurousWoodlandsBiome() {
        MobSpawnSettings.Builder mobspawninfo$builder = new MobSpawnSettings.Builder();
//        mobspawninfo$builder.setPlayerCanSpawn();
        EmortisBiomeGen.withVerdurousSpawns(mobspawninfo$builder);
        EmortisBiomeGen.withWoodlandsSpawns(mobspawninfo$builder);

        BiomeGenerationSettings.Builder biomegenerationsettings$builder = new BiomeGenerationSettings.Builder();

//        BiomeDefaultFeatures.addDefaultOverworldLandStructures(biomegenerationsettings$builder);
//        biomegenerationsettings$builder.addStructureStart(StructureFeatures.VILLAGE_TAIGA);
//        biomegenerationsettings$builder.addStructureStart(StructureFeatures.JUNGLE_TEMPLE);
//        biomegenerationsettings$builder.addStructureStart(StructureFeatures.RUINED_PORTAL_JUNGLE);

        EmortisBiomeGen.withWoodlandsFeatures(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addSurfaceFreezing(biomegenerationsettings$builder);
        Music music = Musics.createGameMusic(BiomeMusic.calmRight);
        return (new Biome.BiomeBuilder())
                .precipitation(Biome.Precipitation.RAIN).biomeCategory(Biome.BiomeCategory.FOREST).temperature(0.95F).downfall(0.25F)
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(4445678)
                        .waterFogColor(5613789)
                        .fogColor(12638463)
                        .skyColor(calculateSkyColor(0.5F))
                        .grassColorOverride(5627304)
                        .foliageColorOverride(8442041).skyColor(11726569)
                        .ambientMoodSound(BiomeMusic.CALM_RIGHT)
                        .backgroundMusic(BiomeMusic.UN_DIA_DE_ABRIL_MUSIC)
                        .build())
                .mobSpawnSettings(mobspawninfo$builder.build()).generationSettings(biomegenerationsettings$builder.build()).build();
    }

    //___________________  F I E L D S  ____________________//

    private static Biome makeVerdurousFieldsBiome() {
        MobSpawnSettings.Builder mobspawninfo$builder = new MobSpawnSettings.Builder();
//        mobspawninfo$builder.setPlayerCanSpawn();
        EmortisBiomeGen.withVerdurousSpawns(mobspawninfo$builder);
        EmortisBiomeGen.withFieldsSpawns(mobspawninfo$builder);

        BiomeGenerationSettings.Builder biomegenerationsettings$builder = new BiomeGenerationSettings.Builder();

//        BiomeDefaultFeatures.addDefaultOverworldLandStructures(biomegenerationsettings$builder);
//        biomegenerationsettings$builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, StructureFeatures.VILLAGE_PLAINS);
//        biomegenerationsettings$builder.addStructureStart(StructureFeatures.PILLAGER_OUTPOST);
//        biomegenerationsettings$builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, StructureFeature.MINESHAFT);

        EmortisBiomeGen.withFieldsFeatures(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addSurfaceFreezing(biomegenerationsettings$builder);
        Music music = Musics.createGameMusic(BiomeMusic.diaDeAbril);
        return (new Biome.BiomeBuilder())
                .precipitation(Biome.Precipitation.RAIN).biomeCategory(Biome.BiomeCategory.PLAINS).temperature(0.95F).downfall(0.25F)
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(4445678)
                        .waterFogColor(5613789)
                        .fogColor(12638463)
                        .skyColor(calculateSkyColor(0.5F))
                        .grassColorOverride(5627304)
                        .foliageColorOverride(8442041).skyColor(11726569)
                        .ambientMoodSound(BiomeMusic.CALM_RIGHT)
                        .backgroundMusic(BiomeMusic.UN_DIA_DE_ABRIL_MUSIC)
                        .build())
                .mobSpawnSettings(mobspawninfo$builder.build()).generationSettings(biomegenerationsettings$builder.build()).build();
    }

    private static int getSkyColorWithTemperatureModifier(float temperature) {
        float lvt_1_1_ = temperature / 3.0F;
        lvt_1_1_ = Mth.clamp(lvt_1_1_, -0.75F, 0.75F);
        return Mth.hsvToRgb(0.2460909F - lvt_1_1_ * 0.05F, 0.5F + lvt_1_1_ * 0.1F, 1.0F);
    }
    public static void addBiomeTypes() {
        BiomeDictionary.addTypes(verdurousFieldsKey, PLAINS, LUSH, OVERWORLD, SPARSE, MAGICAL);
        BiomeDictionary.addTypes(verdurousWoodlandsKey, FOREST, LUSH, OVERWORLD, DENSE, MAGICAL);
    }
    public static Biome verdurousWoodlands =
            makeVerdurousWoodlandsBiome(/*0.4F, 0.425F*/).setRegistryName(EmortisConstants.MOD_ID, "verdurous_woodlands");

    public static Biome verdurousFields =
            makeVerdurousFieldsBiome(/*0.125F, 0.05F*/).setRegistryName(EmortisConstants.MOD_ID, "verdurous_fields");

    public static ResourceKey<Biome> verdurousWoodlandsKey = BiomeRegistry.key(verdurousWoodlands);
    public static ResourceKey<Biome> verdurousFieldsKey = BiomeRegistry.key(verdurousFields);

    @ObjectHolder(EmortisConstants.MOD_ID)
    @Mod.EventBusSubscriber(modid = EmortisConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class BiomeRegistry {
        @SubscribeEvent
        public static void biomeRegistry(final RegistryEvent.Register<Biome> biomeRegistryEvent) {
            biomeRegistryEvent.getRegistry().registerAll(verdurousWoodlands, verdurousFields);
            BiomeDictionary.addTypes(verdurousWoodlandsKey, FOREST);
            BiomeDictionary.addTypes(verdurousFieldsKey, PLAINS);
        }
//        @SubscribeEvent
//        public static void featureRegistry(final RegistryEvent.Register<Feature<?>> registryEvent) {
//            registryEvent.getRegistry().register(WorldEvent.LIGHTS.setRegistryName(FeatureLib.LIGHTS));
//        }
        private static ResourceKey<Biome> key(Biome b) {
            return ResourceKey.create(Registry.BIOME_REGISTRY, Objects.requireNonNull(b.getRegistryName()));
        }
    }
    public static void registerStructures(BiConsumer<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>> p_194758_) {

    }
    @SubscribeEvent
    public static void biomeLoad(BiomeLoadingEvent e) {
        if (e.getCategory() == Biome.BiomeCategory.NETHER || e.getCategory() == Biome.BiomeCategory.THEEND)
            return;

        if (Config.SPAWN_ORE.get()) {
            EmortisOreGen.generateOres(e);
        }
//            e.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES,
//                    Objects.requireNonNull(BuiltinRegistries.PLACED_FEATURE.get(Registration.RECONDITE_ORE.get().getRegistryName()))).build();
        if ((e.getCategory().equals(Biome.BiomeCategory.FOREST) || e.getCategory().equals(Biome.BiomeCategory.TAIGA) || e.getName().equals(verdurousWoodlands.getRegistryName()) || e.getName().equals(verdurousWoodlands.getRegistryName())  && Config.SPAWN_BERRIES.get())) {
            e.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                    Objects.requireNonNull(BuiltinRegistries.PLACED_FEATURE.get(RigoranthusEmortisReborn.rl("placed_dominion_bush")))).build();
            e.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                    Objects.requireNonNull(BuiltinRegistries.PLACED_FEATURE.get(RigoranthusEmortisReborn.rl("placed_spectabilis_bush")))).build();
        }
        if(e.getName().equals(verdurousWoodlands.getRegistryName())){
            addVerdurousWoodlandsFeatures(e);
        }
        if(e.getName().equals(verdurousFields.getRegistryName())){
            addVerdurousFieldsFeatures(e);
        }
    }

    public static void addVerdurousFieldsFeatures(BiomeLoadingEvent e){
        e.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                Objects.requireNonNull(BuiltinRegistries.PLACED_FEATURE.get(RigoranthusEmortisReborn.rl("placed_dominion_bush")))).build();
        e.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                Objects.requireNonNull(BuiltinRegistries.PLACED_FEATURE.get(RigoranthusEmortisReborn.rl("placed_spectabilis_bush")))).build();

        e.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                Objects.requireNonNull(BuiltinRegistries.PLACED_FEATURE.get(RigoranthusEmortisReborn.rl("placed_azuloreal_orchid")))).build();
        e.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                Objects.requireNonNull(BuiltinRegistries.PLACED_FEATURE.get(RigoranthusEmortisReborn.rl("placed_iridescent_sprouts")))).build();
        e.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                Objects.requireNonNull(BuiltinRegistries.PLACED_FEATURE.get(RigoranthusEmortisReborn.rl("placed_lisianthus")))).build();
        e.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                Objects.requireNonNull(BuiltinRegistries.PLACED_FEATURE.get(new ResourceLocation("rigoranthusemortisreborn:placed_random_verdurous")))).build();
//        e.getGeneration().addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS,
//                Objects.requireNonNull(BuiltinRegistries.PLACED_FEATURE.get(RANDOM_LIGHTS_LOC))).build();
//        e.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
//                Objects.requireNonNull(BuiltinRegistries.PLACED_FEATURE.get(new ResourceLocation("rigoranthusemortisreborn:placed_jessic_tree")))).build();
//        e.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
//                Objects.requireNonNull(BuiltinRegistries.PLACED_FEATURE.get(new ResourceLocation("rigoranthusemortisreborn:placed_azuloreal_tree")))).build();
//        e.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
//                Objects.requireNonNull(BuiltinRegistries.PLACED_FEATURE.get(new ResourceLocation("rigoranthusemortisreborn:placed_looming_jessic_tree")))).build();
//        e.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
//                Objects.requireNonNull(BuiltinRegistries.PLACED_FEATURE.get(new ResourceLocation("rigoranthusemortisreborn:placed_looming_azuloreal_tree")))).build();
//        e.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
//                Objects.requireNonNull(BuiltinRegistries.PLACED_FEATURE.get(new ResourceLocation("rigoranthusemortisreborn:placed_mega_jessic_tree")))).build();
//        e.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
//                Objects.requireNonNull(BuiltinRegistries.PLACED_FEATURE.get(new ResourceLocation("rigoranthusemortisreborn:placed_mega_azuloreal_tree")))).build();
//            if (Config.SPAWN_ORE.get()) {
//                e.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES,
//                        Objects.requireNonNull(BuiltinRegistries.PLACED_FEATURE.get(Registration.RECONDITE_ORE.get().getRegistryName()))).build();
//            }
    }

    public static void addVerdurousWoodlandsFeatures(BiomeLoadingEvent e){

        e.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                Objects.requireNonNull(BuiltinRegistries.PLACED_FEATURE.get(RigoranthusEmortisReborn.rl("placed_dominion_bush")))).build();
        e.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                Objects.requireNonNull(BuiltinRegistries.PLACED_FEATURE.get(RigoranthusEmortisReborn.rl("placed_spectabilis_bush")))).build();

        e.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                Objects.requireNonNull(BuiltinRegistries.PLACED_FEATURE.get(RigoranthusEmortisReborn.rl("placed_azuloreal_orchid")))).build();
        e.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                Objects.requireNonNull(BuiltinRegistries.PLACED_FEATURE.get(RigoranthusEmortisReborn.rl("placed_iridescent_sprouts")))).build();
        e.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                Objects.requireNonNull(BuiltinRegistries.PLACED_FEATURE.get(RigoranthusEmortisReborn.rl("placed_lisianthus")))).build();

//        e.getGeneration().addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS,
//                Objects.requireNonNull(BuiltinRegistries.PLACED_FEATURE.get(RANDOM_LIGHTS_LOC))).build();
        e.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                Objects.requireNonNull(BuiltinRegistries.PLACED_FEATURE.get(new ResourceLocation("rigoranthusemortisreborn:placed_random_verdurous")))).build();

//        new ResourceLocation("rigoranthusemortisreborn:placed_random_verdurous")
//        if (Config.SPAWN_ORE.get()) {
//            e.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES,
//                    Objects.requireNonNull(BuiltinRegistries.PLACED_FEATURE.get(Registration.RECONDITE_ORE.get().getRegistryName()))).build();
//        }
    }

    public static void withVerdurousSpawns(MobSpawnSettings.Builder spawns) {
        BiomeDefaultFeatures.commonSpawns(spawns);
        spawns
            .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.FERAL_CANIS, 100, 1, 4))
            .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntities.SUNDERED_CADAVER, 100, 1, 6))
            .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntities.NECRAW_FASCII, 80, 1, 2))
            .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIFIED_PIGLIN, 3, 2, 4));
    }
    public static void withWoodlandsSpawns(MobSpawnSettings.Builder spawns) {
        spawns
            .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PARROT, 40, 1, 3))
            .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 10, 2, 3))
            .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 8, 1, 2))
            .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PANDA, 80, 1, 2));
    }
    public static void withFieldsSpawns(MobSpawnSettings.Builder spawns) {
        BiomeDefaultFeatures.farmAnimals(spawns);
        BiomeDefaultFeatures.plainsSpawns(spawns);
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 10, 2, 3));
    }

    public static void withVerdurousFeatures(BiomeGenerationSettings.Builder feature) {
        BiomeDefaultFeatures.addDefaultCarversAndLakes(feature);

        BiomeDefaultFeatures.addLushCavesVegetationFeatures(feature);
        BiomeDefaultFeatures.addLushCavesSpecialOres(feature);
        BiomeDefaultFeatures.addDefaultCrystalFormations(feature);
        BiomeDefaultFeatures.addDripstone(feature);
        BiomeDefaultFeatures.addJungleVines(feature);

        BiomeDefaultFeatures.addDefaultMonsterRoom(feature);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(feature);
        BiomeDefaultFeatures.addDefaultOres(feature);
        BiomeDefaultFeatures.addDefaultSoftDisks(feature);
        BiomeDefaultFeatures.addDefaultMushrooms(feature);
        BiomeDefaultFeatures.addDefaultExtraVegetation(feature);
        BiomeDefaultFeatures.addDefaultSprings(feature);
    }
    public static void withWoodlandsFeatures(BiomeGenerationSettings.Builder feature) {
        BiomeDefaultFeatures.addMossyStoneBlock(feature);
        BiomeDefaultFeatures.addBambooVegetation(feature);
//                                                         TODO  -->  CONSIDER ADDING CUSTOM TREE GEN HERE
        BiomeDefaultFeatures.addWarmFlowers(feature);
        BiomeDefaultFeatures.addJungleGrass(feature);
        withVerdurousFeatures(feature);
        BiomeDefaultFeatures.addJungleMelons(feature);
        BiomeDefaultFeatures.addSurfaceFreezing(feature);
    }
    public static void withFieldsFeatures(BiomeGenerationSettings.Builder feature) {
        BiomeDefaultFeatures.addPlainGrass(feature);
        BiomeDefaultFeatures.addPlainVegetation(feature);
//                                                         TODO  -->  CONSIDER ADDING CUSTOM TREE GEN HERE
        withVerdurousFeatures(feature);
        BiomeDefaultFeatures.addSurfaceFreezing(feature);
    }
}