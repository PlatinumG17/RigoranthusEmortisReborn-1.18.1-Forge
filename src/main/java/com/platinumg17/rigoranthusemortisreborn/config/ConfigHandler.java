package com.platinumg17.rigoranthusemortisreborn.config;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.IRegistryDelegate;
import org.apache.commons.lang3.tuple.Pair;
import com.platinumg17.rigoranthusemortisreborn.api.RigoranthusEmortisRebornAPI;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.Skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//public class ConfigHandler {
//
//
//
////_____________  S K I L L S     C O N F I G   _____________//
//
//
//
//
//
//
//
//
//    public static void loadConfig(final ModConfigEvent.Loading event) {
//        ModConfig config = event.getConfig();
//
//        if (config.getSpec() == ConfigHandler.CONFIG_CLIENT_SPEC) {
//            ConfigHandler.refreshClient();
//        } else if (config.getSpec() == Config.CONFIG_SKILL_SPEC) {
//            Config.refreshSkills();
//        }
//    }
//
//    public static void reloadConfig(final ModConfigEvent.Reloading event) {
//        ModConfig config = event.getConfig();
//        if (config.getSpec() == ConfigHandler.CONFIG_CLIENT_SPEC) {
//            ConfigHandler.refreshClient();
//        } else if (config.getSpec() == Config.CONFIG_SKILL_SPEC) {
//            Config.refreshSkills();
//        }
//    }
//
//    public static void refreshClient() {
//        RigoranthusEmortisReborn.LOGGER.debug("Refresh Client Config");
//        CLIENT.HOMINI_PARTICLES.get();          CLIENT.RENDER_CHEST.get();
//        CLIENT.SHOW_SMELTERY_ERRORS.get();
//        CLIENT.cache_capacity.get();            CLIENT.enableJeiPlugin.get();
//        CLIENT.enableJeiCatalysts.get();        CLIENT.enableJeiClickArea.get();
//        CLIENT.smelteryXPDropValue.get();       CLIENT.smelteryXPDropValue2.get();
//        CLIENT.masterfulSmelterySpeed.get();    CLIENT.enableAllCanisBedRecipes.get();
//    }
//
//}