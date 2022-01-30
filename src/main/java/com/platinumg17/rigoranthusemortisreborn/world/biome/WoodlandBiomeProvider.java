package com.platinumg17.rigoranthusemortisreborn.world.biome;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.SurfaceRules;
import terrablender.api.BiomeProvider;

import terrablender.worldgen.TBClimate;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static terrablender.api.ParameterUtils.*;

//public class WoodlandBiomeProvider extends BiomeProvider {
//    public WoodlandBiomeProvider(ResourceLocation name, int overworldWeight) {
//        super(name, overworldWeight);
//    }
//
//    @Override
//    public void addOverworldBiomes(Registry<Biome> registry, Consumer<Pair<TBClimate.ParameterPoint, ResourceKey<Biome>>> mapper) {
//        this.addModifiedVanillaOverworldBiomes(mapper, builder -> {
//            // Simple example:
//            // Replace Vanilla biomes
////            builder.replaceBiome(Biomes.FOREST, VerdurousBiomes.VERDUROUS_WOODLANDS);
////            builder.replaceBiome(Biomes.PLAINS, VerdurousBiomes.VERDUROUS_FIELDS);
//            // More complex example:
//            // Replace specific parameter points for the biome gen to occur at
//            List<Climate.ParameterPoint> fieldsPoints = new ParameterPointListBuilder()
//                    .temperature(Temperature.WARM, Temperature.COOL, Temperature.NEUTRAL)
//                    .humidity(Humidity.ARID, Humidity.DRY, Humidity.NEUTRAL, Humidity.WET, Humidity.HUMID)
//                    .continentalness(Continentalness.span(Continentalness.INLAND, Continentalness.FAR_INLAND), Continentalness.span(Continentalness.MID_INLAND, Continentalness.FAR_INLAND))
//                    .erosion(Erosion.EROSION_0, Erosion.EROSION_1)
//                    .depth(Depth.SURFACE, Depth.FLOOR)
//                    .weirdness(Weirdness.VALLEY, Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.MID_SLICE_NORMAL_DESCENDING)
//                    .buildVanilla();
//
//            fieldsPoints.forEach(point -> builder.replaceBiome(point, VerdurousBiomes.VERDUROUS_FIELDS));
//
//            List<Climate.ParameterPoint> woodlandPoints = new ParameterPointListBuilder()
//                    .temperature(Temperature.ICY, Temperature.COOL, Temperature.NEUTRAL)
//                    .humidity(Humidity.ARID, Humidity.DRY, Humidity.NEUTRAL, Humidity.WET, Humidity.HUMID)
//                    .continentalness(Continentalness.span(Continentalness.COAST, Continentalness.NEAR_INLAND), Continentalness.span(Continentalness.NEAR_INLAND, Continentalness.INLAND))
//                    .erosion(Erosion.EROSION_0, Erosion.EROSION_6)
//                    .depth(Depth.SURFACE, Depth.FLOOR)
//                    .weirdness(Weirdness.MID_SLICE_VARIANT_ASCENDING, Weirdness.PEAK_VARIANT, Weirdness.HIGH_SLICE_VARIANT_DESCENDING)
//                    .buildVanilla();
//
//            woodlandPoints.forEach(point -> builder.replaceBiome(point, VerdurousBiomes.VERDUROUS_WOODLANDS));
//        });
//    }
//
//    @Override
//    public Optional<SurfaceRules.RuleSource> getOverworldSurfaceRules() {
//        return Optional.of(VerdurousSurfaceRuleData.makeRules());
//    }
//}