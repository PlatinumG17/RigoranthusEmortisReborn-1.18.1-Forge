package com.platinumg17.rigoranthusemortisreborn.world;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.config.Config;
import com.platinumg17.rigoranthusemortisreborn.magica.common.entity.ModEntities;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = EmortisConstants.MOD_ID)
public class EmortisMobSpawns {

    @SubscribeEvent
    public static void onBiomeLoad(BiomeLoadingEvent event) {
        if (event.getName() == null) return;
        if (event.getCategory() == Biome.BiomeCategory.THEEND || event.getCategory().equals(Biome.BiomeCategory.MUSHROOM) || event.getCategory().equals(Biome.BiomeCategory.NONE))
            return;
        ResourceLocation biome = event.getName();
        ResourceKey<Biome> key = ResourceKey.create(Registry.BIOME_REGISTRY, biome);

        List<Biome.BiomeCategory> categories = Arrays.asList(Biome.BiomeCategory.FOREST, Biome.BiomeCategory.EXTREME_HILLS, Biome.BiomeCategory.JUNGLE,
                Biome.BiomeCategory.PLAINS, Biome.BiomeCategory.SWAMP, Biome.BiomeCategory.SAVANNA, Biome.BiomeCategory.MESA, Biome.BiomeCategory.TAIGA);

        if (Config.sunderedCadaverSpawnWeight.get() > 0) {
            if (event.getCategory().equals(Biome.BiomeCategory.NETHER) && Config.allowCadaverNetherSpawns.get()) {
                event.getSpawns().addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntities.SUNDERED_CADAVER, Config.sunderedCadaverSpawnWeight.get(), 1, 6));
            }
            else if (!event.getCategory().equals(Biome.BiomeCategory.NETHER))
                event.getSpawns().addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntities.SUNDERED_CADAVER, Config.sunderedCadaverSpawnWeight.get(), 1, 6));
        }

        if (Config.necrawFasciiSpawnWeight.get() > 0) {
            if (event.getCategory().equals(Biome.BiomeCategory.NETHER) && Config.allowNecrawNetherSpawns.get()) {
                event.getSpawns().addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntities.NECRAW_FASCII, Config.necrawFasciiSpawnWeight.get(), 1, 2));
            }
            else if (!event.getCategory().equals(Biome.BiomeCategory.NETHER))
                event.getSpawns().addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntities.NECRAW_FASCII, Config.necrawFasciiSpawnWeight.get(), 1, 2));
        }

        if (Config.feralCanisChordataSpawnWeight.get() > 0)
            event.getSpawns().addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntities.FERAL_CANIS, Config.feralCanisChordataSpawnWeight.get(), 1, 1));

        if (categories.contains(event.getCategory())) {
            if (Config.languidDwellerSpawnWeight.get() > 0)
                event.getSpawns().addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntities.LANGUID_DWELLER, Config.languidDwellerSpawnWeight.get(), 1, 1));
        }
    }
}