package com.platinumg17.rigoranthusemortisreborn.world.gen.feature;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.magica.common.lib.LibSoundNames;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import java.util.Objects;

public class BiomeMusic {

    public static ResourceLocation unDiaDeAbril_LOACATION = new  ResourceLocation(EmortisConstants.MOD_ID, "un_dia_de_abril");
    public static ResourceLocation calmRight_LOACATION = new  ResourceLocation(EmortisConstants.MOD_ID, "calm_right");

    public static SoundEvent diaDeAbril = new SoundEvent(unDiaDeAbril_LOACATION).setRegistryName(LibSoundNames.UN_DIA_DE_ABRIL);
    public static SoundEvent calmRight = new SoundEvent(calmRight_LOACATION).setRegistryName(LibSoundNames.CALM_RIGHT);

    public static ResourceKey<SoundEvent> diaDeAbrilKey = BiomeMusicRegistry.key(diaDeAbril);
    public static ResourceKey<SoundEvent> calmRightKey = BiomeMusicRegistry.key(calmRight);

    @ObjectHolder(EmortisConstants.MOD_ID)
    @Mod.EventBusSubscriber(modid = EmortisConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class BiomeMusicRegistry {
        @SubscribeEvent
        public static void soundRegistry(final RegistryEvent.Register<SoundEvent> soundRegistryEvent) {
            soundRegistryEvent.getRegistry().registerAll(diaDeAbril, calmRight);
        }
        private static ResourceKey<SoundEvent> key(SoundEvent soundEvent) {
            return ResourceKey.create(Registry.SOUND_EVENT_REGISTRY, Objects.requireNonNull(soundEvent.getRegistryName()));
        }
    }

    public static final Music UN_DIA_DE_ABRIL_MUSIC = createGameMusic(diaDeAbril);
    public static final Music CALM_RIGHT_MUSIC = createGameMusic(calmRight);

    public static final AmbientMoodSettings CALM_RIGHT = new AmbientMoodSettings(calmRight, 6000, 8, 2.0D);
    public static final AmbientMoodSettings UN_DIA_DE_ABRIL = new AmbientMoodSettings(diaDeAbril, 6000, 8, 2.0D);

    public static Music createGameMusic(SoundEvent soundEvent) {
        return new Music(soundEvent, 12000, 24000, true);
    }
}