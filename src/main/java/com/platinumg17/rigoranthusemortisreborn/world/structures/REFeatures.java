package com.platinumg17.rigoranthusemortisreborn.world.structures;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.config.Config;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class REFeatures {

    public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, EmortisConstants.MOD_ID);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> CIPHER = REGISTRY.register("cipher", CipherFeature::new);

    public static PlacedFeature PLACED_CIPHER;

    public static void register() {
        ConfiguredFeature<?, ?> configuredFeature = Registry.register(
                BuiltinRegistries.CONFIGURED_FEATURE,
                new ResourceLocation(EmortisConstants.MOD_ID, "cipher"),
                CIPHER.get().configured(FeatureConfiguration.NONE)
        );

        PLACED_CIPHER = Registry.register(
                BuiltinRegistries.PLACED_FEATURE,
                new ResourceLocation(EmortisConstants.MOD_ID, "underground_cipher"),
                configuredFeature.placed(
                        RarityFilter.onAverageOnceEvery(1/*Config.ServerConf.cipherRarity.get()*/),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(
                                VerticalAnchor.aboveBottom(32),
                                VerticalAnchor.aboveBottom(96)
                        ),
                        EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 32),
                        RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                        BiomeFilter.biome()
                )
        );
    }
}