package com.platinumg17.rigoranthusemortisreborn.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.api.RigoranthusEmortisRebornAPI;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.Skill;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.AbstractSpellPart;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.IRegistryDelegate;
import org.apache.commons.lang3.tuple.Pair;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber
public class Config {

    public static ClientConfig CLIENT;
    public static ServerConf SERVER;
    public static SkillConfig SKILL;
    public static ForgeConfigSpec CONFIG_SERVER_SPEC;
    private static ForgeConfigSpec CONFIG_SKILL_SPEC;
    private static ForgeConfigSpec CONFIG_CLIENT_SPEC;

    //_____________  C L I E N T     C O N F I G  _____________//

//    public static final boolean ALWAYS_SHOW_CANIS_NAME     = true;
    public static final boolean WHISTLE_SOUNDS             = false;
//    public static final boolean HOMINI_PARTICLES           = true;
//    public static final boolean RENDER_CHEST               = true;
//    public static final boolean SHOW_SMELTERY_ERRORS       = false;
//    public static final boolean enableAllCanisBedRecipes   = false;
//    public static final boolean enableJeiPlugin            = true;
//    public static final boolean enableJeiCatalysts         = true;
//    public static final boolean enableJeiClickArea         = true;
//    public static final float   DEFAULT_MAX_HUNGER         = 120F;
//
//    public static final int cache_capacity                 = 10;
//    public static final int smelteryXPDropValue            = 10;
//    public static final int smelteryXPDropValue2           = 100000;
//    public static final int masterfulSmelterySpeed         = 160;

    //_____________  S E R V E R     C O N F I G  _____________//
    public static ForgeConfigSpec.BooleanValue DISABLE_TAMING;
    public static ForgeConfigSpec.IntValue DEFAULT_MAX_HUNGER;
    public static ForgeConfigSpec.BooleanValue DISABLE_HUNGER;                      public static ForgeConfigSpec.BooleanValue STARTING_ITEMS;
    public static ForgeConfigSpec.BooleanValue CANIS_GENDER;                        public static ForgeConfigSpec.BooleanValue CANIS_PUPS_GET_PARENT_LEVELS;

    public static ForgeConfigSpec.BooleanValue enableBoneWeapons;                   public static ForgeConfigSpec.BooleanValue enableUnfiredBricks;
    public static ForgeConfigSpec.BooleanValue enableSoulCoal;                      public static ForgeConfigSpec.IntValue soulCoalBurnTime;
    public static ForgeConfigSpec.BooleanValue enableNetheriteAdditions;            public static ForgeConfigSpec.BooleanValue enableArmorSetBonuses;

    public static ForgeConfigSpec.IntValue morrai_durability;
    public static ForgeConfigSpec.IntValue morrai_damage;                           public static ForgeConfigSpec.DoubleValue morrai_speed;
    public static ForgeConfigSpec.IntValue anduril_durability;                      public static ForgeConfigSpec.IntValue anduril_fire_duration;
    public static ForgeConfigSpec.IntValue anduril_damage;                          public static ForgeConfigSpec.DoubleValue anduril_speed;
    public static ForgeConfigSpec.DoubleValue razortooth_kunai_velocity;            public static ForgeConfigSpec.DoubleValue razortooth_kunai_inaccuracy;
    public static ForgeConfigSpec.IntValue razortooth_kunai_damage;                 public static ForgeConfigSpec.DoubleValue throwing_knife_velocity;
    public static ForgeConfigSpec.IntValue throwing_knife_damage;                   public static ForgeConfigSpec.DoubleValue throwing_knife_inaccuracy;
    public static ForgeConfigSpec.DoubleValue ghastly_scepter_speed;                public static ForgeConfigSpec.IntValue ghastly_scepter_damage;
    public static ForgeConfigSpec.IntValue ghastly_scepter_durability;              public static ForgeConfigSpec.IntValue razortooth_frisbee_duration;
    public static ForgeConfigSpec.DoubleValue razortooth_frisbee_velocity;          public static ForgeConfigSpec.DoubleValue razortooth_frisbee_inaccuracy;
    public static ForgeConfigSpec.IntValue razortooth_frisbee_damage;               public static ForgeConfigSpec.IntValue razortooth_frisbee_durability;
    public static ForgeConfigSpec.IntValue ricochet_round_duration;                 public static ForgeConfigSpec.DoubleValue ricochet_round_inaccuracy;
    public static ForgeConfigSpec.DoubleValue ricochet_round_velocity;              public static ForgeConfigSpec.IntValue ricochet_round_damage;
    public static ForgeConfigSpec.IntValue cry_of_desperation_durability;           public static ForgeConfigSpec.BooleanValue enable_hellfire_rain;
    public static ForgeConfigSpec.DoubleValue cry_of_desperation_speed;             public static ForgeConfigSpec.IntValue cry_of_desperation_damage;

    public static ForgeConfigSpec.DoubleValue bone_spear_speed;                     public static ForgeConfigSpec.DoubleValue apogean_axe_speed;
    public static ForgeConfigSpec.IntValue bone_spear_damage;                       public static ForgeConfigSpec.IntValue apogean_axe_damage;
    public static ForgeConfigSpec.IntValue bone_tier_durability;                    public static ForgeConfigSpec.DoubleValue apogean_sword_speed;
    public static ForgeConfigSpec.DoubleValue bone_tier_speed;                      public static ForgeConfigSpec.IntValue apogean_sword_damage;
    public static ForgeConfigSpec.DoubleValue bone_tier_damage;                     public static ForgeConfigSpec.IntValue apogean_tier_durability;
    public static ForgeConfigSpec.IntValue bone_tier_enchantability;                public static ForgeConfigSpec.DoubleValue apogean_tier_speed;
    public static ForgeConfigSpec.IntValue bone_bow_projectile_range;               public static ForgeConfigSpec.DoubleValue apogean_tier_damage;
    public static ForgeConfigSpec.IntValue bone_bow_durability;                     public static ForgeConfigSpec.IntValue apogean_tier_enchantability;

    public static ForgeConfigSpec.DoubleValue aqueous_axe_speed;                    public static ForgeConfigSpec.DoubleValue atrophying_axe_speed;
    public static ForgeConfigSpec.IntValue aqueous_axe_damage;                      public static ForgeConfigSpec.IntValue atrophying_axe_damage;
    public static ForgeConfigSpec.DoubleValue aqueous_sword_speed;                  public static ForgeConfigSpec.DoubleValue atrophying_sword_speed;
    public static ForgeConfigSpec.IntValue aqueous_sword_damage;                    public static ForgeConfigSpec.IntValue atrophying_sword_damage;
    public static ForgeConfigSpec.IntValue aqueous_tier_durability;                 public static ForgeConfigSpec.IntValue atrophying_tier_durability;
    public static ForgeConfigSpec.DoubleValue aqueous_tier_speed;                   public static ForgeConfigSpec.DoubleValue atrophying_tier_speed;
    public static ForgeConfigSpec.DoubleValue aqueous_tier_damage;                  public static ForgeConfigSpec.DoubleValue atrophying_tier_damage;
    public static ForgeConfigSpec.IntValue aqueous_tier_enchantability;             public static ForgeConfigSpec.IntValue atrophying_tier_enchantability;

    public static ForgeConfigSpec.DoubleValue incorporeal_axe_speed;                public static ForgeConfigSpec.DoubleValue infernal_axe_speed;
    public static ForgeConfigSpec.IntValue incorporeal_axe_damage;                  public static ForgeConfigSpec.IntValue infernal_axe_damage;
    public static ForgeConfigSpec.DoubleValue incorporeal_sword_speed;              public static ForgeConfigSpec.IntValue infernal_sword_damage;
    public static ForgeConfigSpec.IntValue incorporeal_sword_damage;                public static ForgeConfigSpec.DoubleValue infernal_sword_speed;
    public static ForgeConfigSpec.IntValue incorporeal_tier_durability;             public static ForgeConfigSpec.IntValue infernal_tier_durability;
    public static ForgeConfigSpec.DoubleValue incorporeal_tier_speed;               public static ForgeConfigSpec.DoubleValue infernal_tier_speed;
    public static ForgeConfigSpec.DoubleValue incorporeal_tier_damage;              public static ForgeConfigSpec.DoubleValue infernal_tier_damage;
    public static ForgeConfigSpec.IntValue incorporeal_tier_enchantability;         public static ForgeConfigSpec.IntValue infernal_tier_enchantability;

    public static ForgeConfigSpec.DoubleValue opulent_axe_speed;                    public static ForgeConfigSpec.DoubleValue pernicious_axe_speed;
    public static ForgeConfigSpec.IntValue opulent_axe_damage;                      public static ForgeConfigSpec.IntValue pernicious_axe_damage;
    public static ForgeConfigSpec.DoubleValue opulent_sword_speed;                  public static ForgeConfigSpec.DoubleValue pernicious_sword_speed;
    public static ForgeConfigSpec.IntValue opulent_sword_damage;                    public static ForgeConfigSpec.IntValue pernicious_sword_damage;
    public static ForgeConfigSpec.IntValue opulent_tier_durability;                 public static ForgeConfigSpec.IntValue pernicious_tier_durability;
    public static ForgeConfigSpec.DoubleValue opulent_tier_speed;                   public static ForgeConfigSpec.DoubleValue pernicious_tier_speed;
    public static ForgeConfigSpec.DoubleValue opulent_tier_damage;                  public static ForgeConfigSpec.DoubleValue pernicious_tier_damage;
    public static ForgeConfigSpec.IntValue opulent_tier_enchantability;             public static ForgeConfigSpec.IntValue pernicious_tier_enchantability;

    public static ForgeConfigSpec.DoubleValue phantasmal_sword_speed;               public static ForgeConfigSpec.DoubleValue remex_sword_speed;
    public static ForgeConfigSpec.IntValue phantasmal_sword_damage;                 public static ForgeConfigSpec.IntValue remex_sword_damage;
    public static ForgeConfigSpec.DoubleValue phantasmal_axe_speed;                 public static ForgeConfigSpec.DoubleValue remex_axe_speed;
    public static ForgeConfigSpec.IntValue phantasmal_axe_damage;                   public static ForgeConfigSpec.IntValue remex_axe_damage;
    public static ForgeConfigSpec.IntValue phantasmal_tier_durability;              public static ForgeConfigSpec.IntValue remex_tier_durability;
    public static ForgeConfigSpec.DoubleValue phantasmal_tier_speed;                public static ForgeConfigSpec.DoubleValue remex_tier_speed;
    public static ForgeConfigSpec.DoubleValue phantasmal_tier_damage;               public static ForgeConfigSpec.DoubleValue remex_tier_damage;
    public static ForgeConfigSpec.IntValue phantasmal_tier_enchantability;          public static ForgeConfigSpec.IntValue remex_tier_enchantability;

    public static ForgeConfigSpec.IntValue apogean_boots_damage_reduction;          public static ForgeConfigSpec.IntValue aqueous_boots_damage_reduction;
    public static ForgeConfigSpec.IntValue apogean_leggings_damage_reduction;       public static ForgeConfigSpec.IntValue aqueous_leggings_damage_reduction;
    public static ForgeConfigSpec.IntValue apogean_chestplate_damage_reduction;     public static ForgeConfigSpec.IntValue aqueous_chestplate_damage_reduction;
    public static ForgeConfigSpec.IntValue apogean_helmet_damage_reduction;         public static ForgeConfigSpec.IntValue aqueous_helmet_damage_reduction;
    public static ForgeConfigSpec.IntValue apogean_durability_multiplier;           public static ForgeConfigSpec.IntValue aqueous_durability_multiplier;
    public static ForgeConfigSpec.IntValue apogean_armor_enchantability;            public static ForgeConfigSpec.IntValue aqueous_armor_enchantability;
    public static ForgeConfigSpec.DoubleValue apogean_toughness;                    public static ForgeConfigSpec.DoubleValue aqueous_toughness;
    public static ForgeConfigSpec.DoubleValue apogean_knockback_resistance;         public static ForgeConfigSpec.DoubleValue aqueous_knockback_resistance;
    public static ForgeConfigSpec.BooleanValue enableApogeanArmorClimmbingEffect;

    public static ForgeConfigSpec.IntValue atrophying_boots_damage_reduction;       public static ForgeConfigSpec.IntValue incorporeal_boots_damage_reduction;
    public static ForgeConfigSpec.IntValue atrophying_leggings_damage_reduction;    public static ForgeConfigSpec.IntValue incorporeal_leggings_damage_reduction;
    public static ForgeConfigSpec.IntValue atrophying_chestplate_damage_reduction;  public static ForgeConfigSpec.IntValue incorporeal_chestplate_damage_reduction;
    public static ForgeConfigSpec.IntValue atrophying_helmet_damage_reduction;      public static ForgeConfigSpec.IntValue incorporeal_helmet_damage_reduction;
    public static ForgeConfigSpec.IntValue atrophying_durability_multiplier;        public static ForgeConfigSpec.IntValue incorporeal_durability_multiplier;
    public static ForgeConfigSpec.IntValue atrophying_armor_enchantability;         public static ForgeConfigSpec.IntValue incorporeal_armor_enchantability;
    public static ForgeConfigSpec.DoubleValue atrophying_toughness;                 public static ForgeConfigSpec.DoubleValue incorporeal_toughness;
    public static ForgeConfigSpec.DoubleValue atrophying_knockback_resistance;      public static ForgeConfigSpec.DoubleValue incorporeal_knockback_resistance;

    public static ForgeConfigSpec.IntValue infernal_boots_damage_reduction;         public static ForgeConfigSpec.IntValue opulent_boots_damage_reduction;
    public static ForgeConfigSpec.IntValue infernal_leggings_damage_reduction;      public static ForgeConfigSpec.IntValue opulent_leggings_damage_reduction;
    public static ForgeConfigSpec.IntValue infernal_chestplate_damage_reduction;    public static ForgeConfigSpec.IntValue opulent_chestplate_damage_reduction;
    public static ForgeConfigSpec.IntValue infernal_helmet_damage_reduction;        public static ForgeConfigSpec.IntValue opulent_helmet_damage_reduction;
    public static ForgeConfigSpec.IntValue infernal_durability_multiplier;          public static ForgeConfigSpec.IntValue opulent_durability_multiplier;
    public static ForgeConfigSpec.IntValue infernal_armor_enchantability;           public static ForgeConfigSpec.IntValue opulent_armor_enchantability;
    public static ForgeConfigSpec.DoubleValue infernal_toughness;                   public static ForgeConfigSpec.DoubleValue opulent_toughness;
    public static ForgeConfigSpec.DoubleValue infernal_knockback_resistance;        public static ForgeConfigSpec.DoubleValue opulent_knockback_resistance;

    public static ForgeConfigSpec.IntValue pernicious_boots_damage_reduction;       public static ForgeConfigSpec.IntValue phantasmal_boots_damage_reduction;
    public static ForgeConfigSpec.IntValue pernicious_leggings_damage_reduction;    public static ForgeConfigSpec.IntValue phantasmal_leggings_damage_reduction;
    public static ForgeConfigSpec.IntValue pernicious_chestplate_damage_reduction;  public static ForgeConfigSpec.IntValue phantasmal_chestplate_damage_reduction;
    public static ForgeConfigSpec.IntValue pernicious_helmet_damage_reduction;      public static ForgeConfigSpec.IntValue phantasmal_helmet_damage_reduction;
    public static ForgeConfigSpec.IntValue pernicious_durability_multiplier;        public static ForgeConfigSpec.IntValue phantasmal_durability_multiplier;
    public static ForgeConfigSpec.IntValue pernicious_armor_enchantability;         public static ForgeConfigSpec.IntValue phantasmal_armor_enchantability;
    public static ForgeConfigSpec.DoubleValue pernicious_toughness;                 public static ForgeConfigSpec.DoubleValue phantasmal_toughness;
    public static ForgeConfigSpec.DoubleValue pernicious_knockback_resistance;      public static ForgeConfigSpec.DoubleValue phantasmal_knockback_resistance;

    public static ForgeConfigSpec.IntValue remex_boots_damage_reduction;
    public static ForgeConfigSpec.IntValue remex_leggings_damage_reduction;         public static ForgeConfigSpec.IntValue remex_chestplate_damage_reduction;
    public static ForgeConfigSpec.IntValue remex_helmet_damage_reduction;           public static ForgeConfigSpec.IntValue dweller_thorax_damage_reduction;
    public static ForgeConfigSpec.IntValue remex_durability_multiplier;             public static ForgeConfigSpec.IntValue dweller_thorax_durability_multiplier;
    public static ForgeConfigSpec.IntValue remex_armor_enchantability;              public static ForgeConfigSpec.IntValue dweller_thorax_enchantability;
    public static ForgeConfigSpec.DoubleValue remex_toughness;                      public static ForgeConfigSpec.DoubleValue dweller_thorax_toughness;
    public static ForgeConfigSpec.DoubleValue remex_knockback_resistance;           public static ForgeConfigSpec.DoubleValue dweller_thorax_knockback_resistance;

    public static ForgeConfigSpec.BooleanValue enableHammersAndVanillaOreFragments; public static ForgeConfigSpec.BooleanValue enableModdedOreFragments;
    public static ForgeConfigSpec.IntValue stone_hammer_durability;                 public static ForgeConfigSpec.IntValue iron_hammer_durability;
    public static ForgeConfigSpec.IntValue gold_hammer_durability;                  public static ForgeConfigSpec.IntValue diamond_hammer_durability;
    public static ForgeConfigSpec.IntValue abyssalite_hammer_durability;            public static ForgeConfigSpec.IntValue maxVeinSize;
    public static ForgeConfigSpec.IntValue minOreHeight;                            public static ForgeConfigSpec.IntValue maxOreHeight;
    public static ForgeConfigSpec.IntValue veinsPerChunk;

    public static ForgeConfigSpec.BooleanValue enableTreeGeneration;
    public static ForgeConfigSpec.IntValue verdurousWoodlandsSpawnWeight;           public static ForgeConfigSpec.IntValue verdurousFieldsSpawnWeight;
    public static ForgeConfigSpec.IntValue jessicSpawnWeight;                       public static ForgeConfigSpec.IntValue azulorealSpawnWeight;
    public static ForgeConfigSpec.IntValue loomingJessicSpawnWeight;                public static ForgeConfigSpec.IntValue loomingAzulorealSpawnWeight;
    public static ForgeConfigSpec.IntValue megaJessicSpawnWeight;                   public static ForgeConfigSpec.IntValue megaAzulorealSpawnWeight;

    public static ForgeConfigSpec.BooleanValue allowCadaverNetherSpawns;            public static ForgeConfigSpec.BooleanValue allowNecrawNetherSpawns;
    public static ForgeConfigSpec.DoubleValue sunderedCadaverMovementSpeed;         public static ForgeConfigSpec.DoubleValue necrawFasciiMovementSpeed;
    public static ForgeConfigSpec.DoubleValue sunderedCadaverAttackDamage;          public static ForgeConfigSpec.DoubleValue necrawFasciiAttackDamage;
    public static ForgeConfigSpec.DoubleValue sunderedCadaverKnockbackResistance;   public static ForgeConfigSpec.DoubleValue necrawFasciiKnockbackResistance;
    public static ForgeConfigSpec.DoubleValue sunderedCadaverAttackKnockback;       public static ForgeConfigSpec.DoubleValue necrawFasciiAttackKnockback;
    public static ForgeConfigSpec.DoubleValue sunderedCadaverArmorValue;            public static ForgeConfigSpec.DoubleValue necrawFasciiArmorValue;
    public static ForgeConfigSpec.DoubleValue sunderedCadaverMaxHealth;             public static ForgeConfigSpec.DoubleValue necrawFasciiMaxHealth;
    public static ForgeConfigSpec.IntValue sunderedCadaverSpawnWeight;              public static ForgeConfigSpec.IntValue necrawFasciiSpawnWeight;
    public static ForgeConfigSpec.IntValue sunderedCadaverMinGroupSize;             public static ForgeConfigSpec.IntValue necrawFasciiMinGroupSize;
    public static ForgeConfigSpec.IntValue sunderedCadaverMaxGroupSize;             public static ForgeConfigSpec.IntValue necrawFasciiMaxGroupSize;

    public static ForgeConfigSpec.DoubleValue languidDwellerMovementSpeed;          public static ForgeConfigSpec.DoubleValue feralCanisChordataMovementSpeed;
    public static ForgeConfigSpec.DoubleValue languidDwellerAttackDamage;           public static ForgeConfigSpec.DoubleValue feralCanisChordataAttackDamage;
    public static ForgeConfigSpec.DoubleValue languidDwellerKnockbackResistance;    public static ForgeConfigSpec.DoubleValue feralCanisChordataKnockbackResistance;
    public static ForgeConfigSpec.DoubleValue languidDwellerAttackKnockback;        public static ForgeConfigSpec.DoubleValue feralCanisChordataAttackKnockback;
    public static ForgeConfigSpec.DoubleValue languidDwellerArmorValue;             public static ForgeConfigSpec.DoubleValue feralCanisChordataArmorValue;
    public static ForgeConfigSpec.DoubleValue languidDwellerMaxHealth;              public static ForgeConfigSpec.DoubleValue feralCanisChordataMaxHealth;
    public static ForgeConfigSpec.IntValue languidDwellerSpawnWeight;               public static ForgeConfigSpec.IntValue feralCanisChordataSpawnWeight;
    public static ForgeConfigSpec.IntValue languidDwellerMinGroupSize;              public static ForgeConfigSpec.IntValue feralCanisChordataMinGroupSize;
    public static ForgeConfigSpec.IntValue languidDwellerMaxGroupSize;              public static ForgeConfigSpec.IntValue feralCanisChordataMaxGroupSize;
    public static ForgeConfigSpec.IntValue languidDwellerMaxSpawnHeight;

    public static ForgeConfigSpec.BooleanValue SPAWN_ORE;                           public static ForgeConfigSpec.BooleanValue SPAWN_BERRIES;
    public static ForgeConfigSpec.IntValue DOMINION_BOOST_BONUS;
    public static ForgeConfigSpec.IntValue DOMINION_REGEN_ENCHANT_BONUS;            public static ForgeConfigSpec.IntValue DOMINION_REGEN_POTION;
    public static ForgeConfigSpec.IntValue REGEN_INTERVAL;                          //public static ForgeConfigSpec.IntValue SUMMON_FAMILIAR_DOMINION_COST;
    public static ForgeConfigSpec.IntValue INIT_MAX_DOMINION;                       public static ForgeConfigSpec.IntValue INIT_DOMINION_REGEN;

    public static ForgeConfigSpec.ConfigValue<List<? extends String>> DIMENSION_BLACKLIST;
//    public static ForgeConfigSpec.ConfigValue<? extends String> CRYSTALLIZER_ITEM;

    public static Map<String, Integer> addonSpellCosts = new HashMap<>();
    public static void putAddonSpellCost(String tag, int cost){
        addonSpellCosts.put(tag,cost);
    }
    public static boolean isSpellEnabled(String tag){
        AbstractSpellPart spellPart = RigoranthusEmortisRebornAPI.getInstance().getSpellpartMap().get(tag);
        return spellPart.ENABLED == null || spellPart.ENABLED.get();
    }
    public static int getAddonSpellCost(String tag){
        return addonSpellCosts.getOrDefault(tag,
            RigoranthusEmortisRebornAPI.getInstance().getSpellpartMap().containsKey(tag) ?
            RigoranthusEmortisRebornAPI.getInstance().getSpellpartMap().get(tag).getDefaultDominionCost() : 0);
    }
    private static Map<String, ForgeConfigSpec.IntValue> spellCost = new HashMap<>();
    /**
     * Returns the dominion cost specified in the Rigoranthus Emortis config, or falls back to the addon spell cost map. If not there, falls back to the method cost.
     */
    @Deprecated
    public static int getSpellCost(String tag){return spellCost.containsKey(tag + "_cost") ? spellCost.get(tag+"_cost").get() : getAddonSpellCost(tag);}



    public static void init(IEventBus modEventBus) {
        Pair<ServerConf, ForgeConfigSpec> commonPair = new ForgeConfigSpec.Builder().configure(ServerConf::new);
        CONFIG_SERVER_SPEC = commonPair.getRight();
        SERVER = commonPair.getLeft();
        Pair<ClientConfig, ForgeConfigSpec> clientPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        CONFIG_CLIENT_SPEC = clientPair.getRight();
        CLIENT = clientPair.getLeft();

        RigoranthusEmortisReborn.LOGGER.debug("Register configs");

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CONFIG_SERVER_SPEC, "rigoranthusemortisreborn/RigoranthusEmortisReborn-Server.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CONFIG_CLIENT_SPEC, "rigoranthusemortisreborn/RigoranthusEmortisReborn-Client.toml");
    }

    public static void initSkillConfig() {
        Pair<SkillConfig, ForgeConfigSpec> skillPair = new ForgeConfigSpec.Builder().configure(SkillConfig::new);
        CONFIG_SKILL_SPEC = skillPair.getRight();
        SKILL = skillPair.getLeft();

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, CONFIG_SKILL_SPEC, "rigoranthusemortisreborn/rigoranthusemortisreborn-skills.toml");
    }


    public static class ClientConfig {

        public static ForgeConfigSpec.BooleanValue ALWAYS_SHOW_CANIS_NAME;
        public static ForgeConfigSpec.BooleanValue HOMINI_PARTICLES;
        public static ForgeConfigSpec.BooleanValue RENDER_CHEST;
        public static ForgeConfigSpec.BooleanValue SHOW_SMELTERY_ERRORS;
        public static ForgeConfigSpec.IntValue cache_capacity;
        public static ForgeConfigSpec.IntValue smelteryXPDropValue;
        public static ForgeConfigSpec.IntValue smelteryXPDropValue2;
        public static ForgeConfigSpec.IntValue masterfulSmelterySpeed;
        public static ForgeConfigSpec.BooleanValue enableJeiPlugin;
        public static ForgeConfigSpec.BooleanValue enableJeiCatalysts;
        public static ForgeConfigSpec.BooleanValue enableJeiClickArea;
        public static ForgeConfigSpec.BooleanValue enableAllCanisBedRecipes;

        public ClientConfig(ForgeConfigSpec.Builder builder) {
            builder.push("General");
            builder.pop();

            builder.push("Canis Rendering");
            ALWAYS_SHOW_CANIS_NAME = builder
                    .comment(" Whether or not to always render name above canis")
                    .translation("rigoranthusemortisreborn.config.client.render_name")
                    .define("render_name", true);
            HOMINI_PARTICLES = builder
                    .comment(" Enables the particle effect on Homini Level 30 cani.")
                    .translation("rigoranthusemortisreborn.config.client.enable_homini_particles")
                    .define("enable_homini_particles", true);
            RENDER_CHEST = builder
                    .comment(" When enabled, Cani with points in Wayward Traveller will have chests on their side.")
                    .translation("rigoranthusemortisreborn.config.client.render_chest")
                    .define("render_chest", true);
            builder.pop();

            builder.push("JEI Settings");
            enableJeiPlugin = builder
                    .comment(" Enable or disable the JeiPlugin for the mod.")
                    .translation("rigoranthusemortisreborn.config.client.enable_jei")
                    .define("enable_jei", true);
            enableJeiCatalysts = builder
                    .comment(" Enable or disable the Catalysts in Jei for the mod.")
                    .translation("rigoranthusemortisreborn.config.client.enable_jei_catalysts")
                    .define("enable_jei_catalysts", true);
            enableJeiClickArea = builder
                    .comment(" Enable or disable the Clickable Area inside the Masterful Smeltery's GUI.")
                    .translation("rigoranthusemortisreborn.config.client.enable_jei_click_area")
                    .define("enable_jei_click_area", true);
            builder.pop();

            builder.push("Masterful Smeltery Settings");
            cache_capacity = builder
                    .comment(" The capacity of the recipe cache, higher values use more memory.\n Default: 10")
                    .translation("rigoranthusemortisreborn.config.client.recipe_cache")
                    .defineInRange("recipe_cache", 10, 1, 100);
            masterfulSmelterySpeed = builder
                    .comment(" Number of ticks per 'Smelting Operation.'\n Vanilla Furnace = 200 ticks.\n 1 Second = 20 Ticks\n Default: 160")
                    .translation("rigoranthusemortisreborn.config.client.masterful_smeltery_speed")
                    .defineInRange("masterful_smeltery_speed", 160, 2, 72000);
            smelteryXPDropValue = builder
                    .comment(" Value indicating when the Masterful Smeltery should 'overload' and auto-eject the stored xp. \n Default: 10, Recipes")
                    .translation("rigoranthusemortisreborn.config.client.xp_value")
                    .defineInRange("xp_value", 10, 1, 500);
            smelteryXPDropValue2 = builder
                    .comment(" Value indicating when the smeltery should 'overload' and auto-eject the stored xp. \n Default: 100000, Single recipe uses")
                    .translation("rigoranthusemortisreborn.config.client.xp_value_two")
                    .defineInRange("xp_value_two", 100000, 1, 1000000);
            builder.pop();

            builder.push("Miscellaneous");
            SHOW_SMELTERY_ERRORS = builder
                    .comment(" Debugging Tool that prints Smeltery Settings errors in chat.")
                    .translation("rigoranthusemortisreborn.config.client.display_smeltery_errors")
                    .define("display_smeltery_errors", false);
            enableAllCanisBedRecipes = builder
                    .comment(" Show or Hide all of the Auto-Generated Canis Bed Recipes in JEI.")
                    .translation("rigoranthusemortisreborn.config.client.show_all_recipes")
                    .define("show_all_recipes", false);
            builder.pop();
        }
    }
    public static class ServerConf {
        public Map<String, ForgeConfigSpec.BooleanValue> DISABLED_SKILLS;

        public ServerConf(ForgeConfigSpec.Builder builder) {
//        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

            //_____________  S E R V E R     C O N F I G   _____________//

            builder.push("General Settings");
            builder.pop();

            builder.push("->   WORLD-GEN / BIOMES  +  ORES / RESOURCES   <-");
            builder.pop();

            builder.push("Ore Generation, Ore Fragments, and Crushing Hammers");
            SPAWN_ORE = builder.comment(" Spawn Recondite Ore in the world").define("oreGen", true);
            setupOreConfig(builder);
            builder.pop();

            builder.push("Biome Config");
            SPAWN_BERRIES = builder.comment(" Spawn Dominion & Spectabilis Berry Bushes in the world").define("genBerries", true);
            setupBiomeConfig(builder);
            builder.pop();

            builder.push("->   MOBS   <-");
            builder.pop();

            builder.push("Mobs [Spawn Weights & Attributes]");
            DIMENSION_BLACKLIST = builder.comment(" Dimensions where hostile mobs will not spawn [In addition to the ones they already don't spawn in]. Ex: [\"minecraft:overworld\", \"undergarden:undergarden\"]. . Run /forge dimensions for a list.").translation("rigoranthusemortisreborn.config.dimension_blacklist").defineList("dimensionBlacklist", new ArrayList<>(),(o) -> true);
            setupCadaverConfig(builder);
            setupNecrawConfig(builder);
            setupFeralCanisConfig(builder);
            setupDwellerConfig(builder);
            builder.pop();

            builder.push("Tame Canis Settings");
            DISABLE_TAMING = builder
                    .comment(" Disable the ability of players to tame a feral Canis")
                    .translation("rigoranthusemortisreborn.config.canis.disable_taming")
                    .define("disable_taming", false);
            DEFAULT_MAX_HUNGER = builder
                    .comment(" Disable hunger for the canis")
                    .translation("rigoranthusemortisreborn.config.canis.disable_hunger")
                    .defineInRange("default_max_hunger", 120, 10, 5000);
            DISABLE_HUNGER = builder
                    .comment(" Disable hunger for the canis")
                    .translation("rigoranthusemortisreborn.config.canis.disable_hunger")
                    .define("disable_hunger", false);
            STARTING_ITEMS = builder
                    .comment(" When enabled you will spawn with a guide & Canis Summoning Charm.")
                    .translation("rigoranthusemortisreborn.config.enable_starting_items")
                    .define("enable_starting_items", false);
            CANIS_GENDER = builder
                    .comment(" When enabled, cani will be randomly assigned genders and will only mate with the opposite gender.")
                    .translation("rigoranthusemortisreborn.config.enable_gender")
                    .define("enable_gender", true);
            CANIS_PUPS_GET_PARENT_LEVELS = builder
                    .comment(" [Cani are sterile and cannot reproduce. Idk why but im just going to leave this config anyway]\n When enabled, cani pups get some levels from their parents. When disabled, Cani pups start at 0 points.")
                    .translation("rigoranthusemortisreborn.config.enable_canis_pups_get_parent_levels")
                    .define("canis_pups_get_parent_levels", false);
            builder.pop();

            builder.push("->   ARMOR  +  WEAPONS   <-");
            builder.pop();

            builder.push("General Armor Settings");
            enableArmorSetBonuses = builder
                    .comment(" Enable or Disable the Full Armor Set Bonuses.")
                    .translation("rigoranthusemortisreborn.config.server.armor_bonuses_enabled")
                    .define("armor_bonuses_enabled", true);
            enableBoneWeapons = builder
                    .comment(" Enable or Disable Bone Weapons. [ Spear / Arrows ]")
                    .translation("rigoranthusemortisreborn.config.server.bone_weapons_enabled")
                    .define("bone_weapons_enabled", true);
            enableNetheriteAdditions = builder
                    .comment(" Enable or Disable the custom Netherite Armors / Weapons.")
                    .translation("rigoranthusemortisreborn.config.server.netherite_additions_enabled")
                    .define("netherite_additions_enabled", true);
            builder.pop();

            builder.push("APOGEAN Armor Settings");
            setupApogeanConfig(builder);
            builder.pop();
            builder.push("AQUEOUS Armor Settings");
            setupAqueousConfig(builder);
            builder.pop();
            builder.push("ATROPHYING Armor Settings");
            setupAtrophyingConfig(builder);
            builder.pop();
            builder.push("INCORPOREAL Armor Settings");
            setupIncorporealConfig(builder);
            builder.pop();
            builder.push("INFERNAL Armor Settings");
            setupInfernalConfig(builder);
            builder.pop();
            builder.push("OPULENT Armor Settings");
            setupOpulentConfig(builder);
            builder.pop();
            builder.push("PERNICIOUS Armor Settings");
            setupPerniciousConfig(builder);
            builder.pop();
            builder.push("PHANTASMAL Armor Settings");
            setupPhantasmalConfig(builder);
            builder.pop();
            builder.push("REMEX Armor Settings");
            setupRemexConfig(builder);
            builder.pop();
            builder.push("DWELLER Armor Settings");
            setupDwellerArmorConfig(builder);
            builder.pop();

            builder.push("Weapons [other than netherite]");
            setupWeaponConfig(builder);
            builder.pop();

            builder.push("->   MISCELLANEOUS SETTINGS   <-");
            builder.pop();

            builder.push("Misc & Resources");
            enableTreeGeneration = builder
                    .comment(" Enable or Disable Natural Tree & Flower Generation in Verdurous Woodlands.")
                    .translation("rigoranthusemortisreborn.config.server.tree_generation_enabled")
                    .define("tree_generation_enabled", true);
            enableSoulCoal = builder
                    .comment(" Enable or disable Soul Coal")
                    .translation("rigoranthusemortisreborn.config.server.soul_coal_enabled")
                    .define("soul_coal_enabled", true);
            soulCoalBurnTime = builder
                    .comment(" Set the Burn Time of Soul Coal in Ticks.\n [20 ticks = 1 second]\n Default: 3500 ticks [2.9 minutes] \n [Regular coal = 1600 ticks]")
                    .translation("rigoranthusemortisreborn.config.server.soul_coal_burn_time")
                    .defineInRange("soul_coal_burn_time", 3500, 20, 10000000);
            enableUnfiredBricks = builder
                    .comment(" Enable or disable Unfired Bricks and Mud Globs.\n (Unfired Bricks add an extra step to crafting bricks that makes it more realistic, mud is useless without this Mod's Datapack)")
                    .translation("rigoranthusemortisreborn.config.server.bricks_enabled")
                    .define("datapack_stuff.bricks_enabled", true);
            builder.pop();

            builder.comment("Magica").push("magica");
//        CRYSTALLIZER_ITEM = builder.comment(" Crystallizer output item. Do not use a wrong ID!").define("crystallizer_output", "rigoranthusemortisreborn:dominion_gem");
            builder.pop();

            builder.comment("Dominion").push("dominion");
            INIT_DOMINION_REGEN = builder.comment(" Base dominion regen in seconds").defineInRange("baseRegen", 5, 0, Integer.MAX_VALUE);
            INIT_MAX_DOMINION = builder.comment(" Base max dominion").defineInRange("baseMax", 100, 0, Integer.MAX_VALUE);
            REGEN_INTERVAL = builder.comment(" How often the Max & Regen Rate of Dominion will be calculated, in ticks.\n NOTE: The default base dominion regen is the lowest recommended rate.")
                    .defineInRange("updateInterval", 5, 1, 20);
            DOMINION_BOOST_BONUS = builder.comment(" Dominion Boost value per level").defineInRange("dominionBoost", 25, 0, Integer.MAX_VALUE);
            DOMINION_REGEN_ENCHANT_BONUS = builder.comment(" [Enchantment] Dominion regen per second per level").defineInRange("dominionRegenEnchantment", 2, 0, Integer.MAX_VALUE);
            DOMINION_REGEN_POTION = builder.comment(" Regen bonus per potion level").defineInRange("potionRegen", 10, 0, Integer.MAX_VALUE);
            builder.pop();

            CONFIG_SERVER_SPEC = builder.build();
            FMLPaths.getOrCreateGameRelativePath(FMLPaths.CONFIGDIR.get().resolve(EmortisConstants.MOD_ID), EmortisConstants.MOD_ID);
//        RegistryHelper.generateConfig(EmortisConstants.MOD_ID, new ArrayList<>(RigoranthusEmortisRebornAPI.getInstance().getSpellpartMap().values()));
        }
    }

    public static class SkillConfig {
        public Map<IRegistryDelegate<Skill>, ForgeConfigSpec.BooleanValue> DISABLED_SKILLS;

        public SkillConfig(ForgeConfigSpec.Builder builder) {
            builder.comment("Here you can disable skills.").push("Skills");

            DISABLED_SKILLS = new HashMap<>();

            RigoranthusEmortisRebornAPI.SKILLS.forEach((loc) ->
                    DISABLED_SKILLS.put(loc.delegate, builder.define(loc.getRegistryName().toString(), true))
            );
            builder.pop();
        }
        public boolean getFlag(Skill skill) {
            ForgeConfigSpec.BooleanValue booleanValue = this.DISABLED_SKILLS.get(skill.delegate);
            if (booleanValue == null) {
                return true;
            }
            return booleanValue.get();
        }
    }

    public static boolean isStarterEnabled(AbstractSpellPart e){
        return e.STARTER_SPELL != null && e.STARTER_SPELL.get();
    }

    private static void setupCadaverConfig(ForgeConfigSpec.Builder builder) {
        allowCadaverNetherSpawns = builder.comment(" Are Cadavers allowed to spawn in the Nether?").translation("rigoranthusemortisreborn.config.server.cadaver.nether").define("cadaver.nether", true);
        sunderedCadaverSpawnWeight = builder.comment(" How often this mob Spawns.\n Higher Number = Spawn More Often\n Set to 0 to disable spawns.\n Default: 45").translation("rigoranthusemortisreborn.config.server.cadaver.spawn_weight").defineInRange("cadaver.spawn_weight", 45, 0, 1000);
        sunderedCadaverMinGroupSize = builder.comment(" Minimum Number of mobs that will Spawn Together in a Group.\n Set to 0 to disable spawns.\n Default: 1").translation("rigoranthusemortisreborn.config.server.cadaver.min_group_size").defineInRange("cadaver.min_group_size", 1, 0, 100);
        sunderedCadaverMaxGroupSize = builder.comment(" Maximum Number of mobs that will Spawn Together in a Group.\n Set to 0 to disable spawns.\n Default: 5").translation("rigoranthusemortisreborn.config.server.cadaver.max_group_size").defineInRange("cadaver.max_group_size", 5, 0, 100);
        sunderedCadaverMaxHealth = builder.comment(" How much Health this mob has.\n Default: 30.0").translation("rigoranthusemortisreborn.config.server.cadaver.max_health").defineInRange("cadaver.max_health", 30.0, 2.0, 10000);
        sunderedCadaverArmorValue = builder.comment(" How Resistant to Attacks from Players this mob is.\n Default: 3.5").translation("rigoranthusemortisreborn.config.server.cadaver.armor").defineInRange("cadaver.armor", 3.5, 0.0, 10000);
        sunderedCadaverAttackDamage = builder.comment(" How much Damage does this mobs Attacks do.\n Default: 3.0").translation("rigoranthusemortisreborn.config.server.cadaver.attack").defineInRange("cadaver.attack", 3.0, 0.0, 10000);
        sunderedCadaverMovementSpeed = builder.comment(" How Fast this mob is.\n Default: 0.3").translation("rigoranthusemortisreborn.config.server.cadaver.movement_speed").defineInRange("cadaver.movement_speed", 0.3, 0.0, 10);
        sunderedCadaverAttackKnockback = builder.comment(" How far does each Attack push players back.\n Default: 0.5").translation("rigoranthusemortisreborn.config.server.cadaver.knockback").defineInRange("cadaver.knockback", 0.5, 0.0, 100);
        sunderedCadaverKnockbackResistance = builder.comment(" How well does this mob stay in one place while players attack it.\n Default: 0.0").translation("rigoranthusemortisreborn.config.server.cadaver.knockback_resistance").defineInRange("cadaver.knockback_resistance", 0.0, 0.0, 100);
    }
    private static void setupNecrawConfig(ForgeConfigSpec.Builder builder) {
        allowNecrawNetherSpawns = builder.comment(" Are Necraw allowed to spawn in the Nether?").translation("rigoranthusemortisreborn.config.server.necraw.nether").define("necraw.nether", true);
        necrawFasciiSpawnWeight = builder.comment(" How often this mob Spawns.\n Higher Number = Spawn More Often\n Set to 0 to disable spawns.\n Default: 30").translation("rigoranthusemortisreborn.config.server.necraw.spawn_weight").defineInRange("necraw.spawn_weight", 30, 0, 10000);
        necrawFasciiMinGroupSize = builder.comment(" Minimum Number of mobs that will Spawn Together in a Group.\n Set to 0 to disable spawns.\n Default: 1").translation("rigoranthusemortisreborn.config.server.necraw.min_group_size").defineInRange("necraw.min_group_size", 1, 0, 100);
        necrawFasciiMaxGroupSize = builder.comment(" Maximum Number of mobs that will Spawn Together in a Group.\n Set to 0 to disable spawns.\n Default: 3").translation("rigoranthusemortisreborn.config.server.necraw.max_group_size").defineInRange("necraw.max_group_size", 3, 0, 100);
        necrawFasciiMaxHealth = builder.comment(" How much Health this mob has.\n Default: 45.0").translation("rigoranthusemortisreborn.config.server.necraw.max_health").defineInRange("necraw.max_health", 45.0, 2.0, 10000);
        necrawFasciiArmorValue = builder.comment(" How Resistant to Attacks from Players this mob is.\n Default: 5.0").translation("rigoranthusemortisreborn.config.server.necraw.armor").defineInRange("necraw.armor", 5.0, 0.0, 10000);
        necrawFasciiAttackDamage = builder.comment(" How much Damage does this mobs Attacks do.\n Default: 5.0").translation("rigoranthusemortisreborn.config.server.necraw.attack").defineInRange("necraw.attack", 5.0, 0.0, 10000);
        necrawFasciiMovementSpeed = builder.comment(" How Fast this mob is.\n Default: 0.21").translation("rigoranthusemortisreborn.config.server.necraw.movement_speed").defineInRange("necraw.movement_speed", 0.21, 0.0, 10);
        necrawFasciiAttackKnockback = builder.comment(" How far does each Attack push players back.\n Default: 1.0").translation("rigoranthusemortisreborn.config.server.necraw.knockback").defineInRange("necraw.knockback", 1.0, 0.0, 100);
        necrawFasciiKnockbackResistance = builder.comment(" How well does this mob stay in one place while players attack it.\n Default: 0.2").translation("rigoranthusemortisreborn.config.server.necraw.knockback_resistance").defineInRange("necraw.knockback_resistance", 0.2, 0.0, 100);
    }
    private static void setupDwellerConfig(ForgeConfigSpec.Builder builder) {
        languidDwellerSpawnWeight = builder.comment(" How often this mob Spawns.\n Higher Number = Spawn More Often\n Set to 0 to disable spawns.\n Default: 10").translation("rigoranthusemortisreborn.config.server.dweller.spawn_weight").defineInRange("dweller.spawn_weight", 10, 0, 10000);
        languidDwellerMinGroupSize = builder.comment(" Minimum Number of mobs that will Spawn Together in a Group.\n Set to 0 to disable spawns.\n Default: 1").translation("rigoranthusemortisreborn.config.server.dweller.min_group_size").defineInRange("dweller.min_group_size", 1, 0, 100);
        languidDwellerMaxGroupSize = builder.comment(" Maximum Number of mobs that will Spawn Together in a Group.\n Set to 0 to disable spawns.\n Default: 1").translation("rigoranthusemortisreborn.config.server.dweller.max_group_size").defineInRange("dweller.max_group_size", 1, 0, 100);
        languidDwellerMaxSpawnHeight = builder.comment(" How often this mob Spawns.\n Higher Number = Spawn More Often\n Set to 0 to disable spawns.\n Default: 90").translation("rigoranthusemortisreborn.config.server.dweller.max_spawn_height").defineInRange("dweller.max_spawn_height", 90, 0, 10000);
        languidDwellerMaxHealth = builder.comment(" How much Health this mob has.\n Default: 100.0").translation("rigoranthusemortisreborn.config.server.dweller.max_health").defineInRange("dweller.max_health", 100.0, 2.0, 10000);
        languidDwellerArmorValue = builder.comment(" How Resistant to Attacks from Players this mob is.\n Default: 12.0").translation("rigoranthusemortisreborn.config.server.dweller.armor").defineInRange("dweller.armor", 12.0, 0.0, 10000);
        languidDwellerAttackDamage = builder.comment(" How much Damage does this mobs Attacks do.\n Default: 7.0").translation("rigoranthusemortisreborn.config.server.dweller.attack").defineInRange("dweller.attack", 7.0, 0.0, 10000);
        languidDwellerMovementSpeed = builder.comment(" How Fast this mob is.\n Default: 0.27").translation("rigoranthusemortisreborn.config.server.dweller.movement_speed").defineInRange("dweller.movement_speed", 0.27, 0.0, 10);
        languidDwellerAttackKnockback = builder.comment(" How far does each Attack push players back.\n Default: 1.2").translation("rigoranthusemortisreborn.config.server.dweller.knockback").defineInRange("dweller.knockback", 1.2, 0.0, 100);
        languidDwellerKnockbackResistance = builder.comment(" How well does this mob stay in one place while players attack it.\n Default: 2.0").translation("rigoranthusemortisreborn.config.server.dweller.knockback_resistance").defineInRange("dweller.knockback_resistance", 2.0, 0.0, 100);
    }
    private static void setupFeralCanisConfig(ForgeConfigSpec.Builder builder) {
        feralCanisChordataSpawnWeight = builder.comment(" How often this mob Spawns.\n Higher Number = Spawn More Often\n Set to 0 to disable spawns.\n Default: 50").translation("rigoranthusemortisreborn.config.server.canis.spawn_weight").defineInRange("canis.spawn_weight", 50, 0, 10000);
        feralCanisChordataMinGroupSize = builder.comment(" Minimum Number of mobs that will Spawn Together in a Group.\n Set to 0 to disable spawns.\n Default: 1").translation("rigoranthusemortisreborn.config.server.canis.min_group_size").defineInRange("canis.min_group_size", 1, 0, 100);
        feralCanisChordataMaxGroupSize = builder.comment(" Maximum Number of mobs that will Spawn Together in a Group.\n Set to 0 to disable spawns.\n Default: 5").translation("rigoranthusemortisreborn.config.server.canis.max_group_size").defineInRange("canis.max_group_size", 5, 0, 100);
        feralCanisChordataMaxHealth = builder.comment(" How much Health this mob has.\n Default: 100.0").translation("rigoranthusemortisreborn.config.server.canis.max_health").defineInRange("canis.max_health", 100.0, 2.0, 10000);
        feralCanisChordataArmorValue = builder.comment(" How Resistant to Attacks from Players this mob is.\n Default: 6.0").translation("rigoranthusemortisreborn.config.server.canis.armor").defineInRange("canis.armor", 6.0, 0.0, 10000);
        feralCanisChordataAttackDamage = builder.comment(" How much Damage does this mobs Attacks do.\n Default: 4.0").translation("rigoranthusemortisreborn.config.server.canis.attack").defineInRange("canis.attack", 4.0, 0.0, 10000);
        feralCanisChordataMovementSpeed = builder.comment(" How Fast this mob is.\n Default: 0.29").translation("rigoranthusemortisreborn.config.server.canis.movement_speed").defineInRange("canis.movement_speed", 0.29, 0.0, 10);
        feralCanisChordataAttackKnockback = builder.comment(" How far does each Attack push players back.\n Default: 1.25").translation("rigoranthusemortisreborn.config.server.canis.knockback").defineInRange("canis.knockback", 1.25, 0.0, 100);
        feralCanisChordataKnockbackResistance = builder.comment(" How well does this mob stay in one place while players attack it.\n Default: 0.4").translation("rigoranthusemortisreborn.config.server.canis.knockback_resistance").defineInRange("canis.knockback_resistance", 0.4, 0.0, 100);
    }
    private static void setupBiomeConfig(ForgeConfigSpec.Builder builder) {
        verdurousWoodlandsSpawnWeight = builder.comment(" How likely Verdurous Woodlands Biome is to Spawn.\n [Set to 0 to Disable Biome Generation]").translation("rigoranthusemortisreborn.config.server.biome.verdurous_woodlands_spawn_weight").defineInRange("biome.verdurous_woodlands_spawn_weight", 10, 0, 1000);
        verdurousFieldsSpawnWeight = builder.comment(" How likely Verdurous Fields Biome is to Spawn.\n [Set to 0 to Disable Biome Generation]").translation("rigoranthusemortisreborn.config.server.biome.verdurous_fields_spawn_weight").defineInRange("biome.verdurous_fields_spawn_weight", 8, 0, 1000);
        jessicSpawnWeight = builder.comment(" Spawn Weight of Jessic Trees in Verdurous Woodlands Biome.\n [Set to 0 to Disable Generation]").translation("rigoranthusemortisreborn.config.server.jessic.jessic_spawn_weight").defineInRange("jessic.jessic_spawn_weight", 10, 0, 1000);
        loomingJessicSpawnWeight = builder.comment(" Spawn Weight of Looming Jessic Trees in Verdurous Woodlands Biome.\n [Set to 0 to Disable Generation]").translation("rigoranthusemortisreborn.config.server.jessic.looming_jessic_spawn_weight").defineInRange("jessic.looming_jessic_spawn_weight", 8, 0, 1000);
        megaJessicSpawnWeight = builder.comment(" Spawn Weight of Mega Jessic Trees in Verdurous Woodlands Biome.\n [Set to 0 to Disable Generation]").translation("rigoranthusemortisreborn.config.server.jessic.mega_jessic_spawn_weight").defineInRange("jessic.mega_jessic_spawn_weight", 5, 0, 1000);
        azulorealSpawnWeight = builder.comment(" Spawn Weight of Azuloreal Trees in Verdurous Woodlands Biome.\n [Set to 0 to Disable Generation]").translation("rigoranthusemortisreborn.config.server.azuloreal.azuloreal_spawn_weight").defineInRange("azuloreal.azuloreal_spawn_weight", 10, 0, 1000);
        loomingAzulorealSpawnWeight = builder.comment(" Spawn Weight of Looming Azuloreal Trees in Verdurous Woodlands Biome.\n [Set to 0 to Disable Generation]").translation("rigoranthusemortisreborn.config.server.azuloreal.looming_azuloreal_spawn_weight").defineInRange("azuloreal.looming_azuloreal_spawn_weight", 8, 0, 1000);
        megaAzulorealSpawnWeight = builder.comment(" Spawn Weight of Mega Azuloreal Trees in Verdurous Woodlands Biome.\n [Set to 0 to Disable Generation]").translation("rigoranthusemortisreborn.config.server.azuloreal.mega_azuloreal_spawn_weight").defineInRange("azuloreal.mega_azuloreal_spawn_weight", 5, 0, 1000);
    }
    private static void setupOreConfig(ForgeConfigSpec.Builder builder) {
        veinsPerChunk = builder.comment(" The Number of Ore Veins that can spawn per chunk.\n Default: 3").translation("rigoranthusemortisreborn.config.server.ore.veins_per_chunk").defineInRange("ore.veins_per_chunk", 3, 0, 100);
        maxVeinSize = builder.comment(" The Maximum Vein Size of Ores from this mod.\n Default: 3").translation("rigoranthusemortisreborn.config.server.ore.max_vein_size").defineInRange("ore.max_vein_size", 3, 0, 50);
        minOreHeight = builder.comment(" The Minimum Height at which Ores from this mod can Generate.\n Default: 1").translation("rigoranthusemortisreborn.config.server.ore.min_ore_height").defineInRange("ore.min_ore_height", 1, 0, 254);
        maxOreHeight = builder.comment(" The Maximum Height at which Ores from this mod can Generate.\n Default: 14").translation("rigoranthusemortisreborn.config.server.ore.max_ore_height").defineInRange("ore.max_ore_height", 14, 0, 255);
        enableModdedOreFragments = builder.comment(" Enable or disable Modded Ore Fragments.\n (You still need this mod's datapack if you wish to have Ores drop fragments when mined)\n (Datapack Only works with ores from the mods 'AllTheOres' and 'Mystical World')").translation("rigoranthusemortisreborn.config.server.ores.datapack_stuff.modded_ore_fragments").define("ores.datapack_stuff.modded_ore_fragments", true);
        enableHammersAndVanillaOreFragments = builder.comment(" Enable or disable Vanilla Ore Fragments and Ore Crushing Hammers.\n (You still need this mod's datapack if you wish to have Ores drop fragments when mined)").translation("rigoranthusemortisreborn.config.server.ores.hammers_and_vanilla_ore_fragments").define("ores.hammers_and_vanilla_ore_fragments", true);
        stone_hammer_durability = builder.comment(" The Durability of Stone Hammers.\n Default: 25").translation("rigoranthusemortisreborn.config.server.stone_hammers.durability").defineInRange("stone_hammers.durability", 25, 1, 10000);
        iron_hammer_durability = builder.comment(" The Durability of Iron Hammers.\n Default: 100").translation("rigoranthusemortisreborn.config.server.iron_hammers.durability").defineInRange("iron_hammers.durability", 100, 1, 10000);
        gold_hammer_durability = builder.comment(" The Durability of Gold Hammers.\n Default: 80").translation("rigoranthusemortisreborn.config.server.gold_hammers.durability").defineInRange("gold_hammers.durability", 80, 1, 10000);
        diamond_hammer_durability = builder.comment(" The Durability of Diamond Hammers.\n Default: 250").translation("rigoranthusemortisreborn.config.server.diamond_hammers.durability").defineInRange("diamond_hammers.durability", 250, 1, 10000);
        abyssalite_hammer_durability = builder.comment(" The Durability of Abyssalite Hammers.\n Default: 350").translation("rigoranthusemortisreborn.config.server.abyssalite_hammers.durability").defineInRange("abyssalite_hammers.durability", 350, 1, 10000);
    }

    private static void setupWeaponConfig(ForgeConfigSpec.Builder builder) {
        anduril_speed = builder.comment(" Default: -1.8").translation("rigoranthusemortisreborn.config.server.anduril.speed").defineInRange("anduril.speed", -1.8, -1000.0, 1000);
        anduril_damage = builder.comment(" Default: 8").translation("rigoranthusemortisreborn.config.server.anduril.damage").defineInRange("anduril.damage", 8, 0, 1000);
        anduril_durability = builder.comment(" Default: 400").translation("rigoranthusemortisreborn.config.server.anduril.durability").defineInRange("anduril.durability", 400, 0, 100000);
        anduril_fire_duration = builder.comment(" When hitting with a Crit, how long are enemies set on fire?\n Default: 3 seconds").translation("rigoranthusemortisreborn.config.server.anduril.fire_duration").defineInRange("anduril.fire_duration", 3, 0, 100000);

        morrai_speed = builder.comment(" Default: -2.2").translation("rigoranthusemortisreborn.config.server.morrai.speed").defineInRange("morrai.speed", -2.2, -1000.0, 1000);
        morrai_damage = builder.comment(" Default: 6").translation("rigoranthusemortisreborn.config.server.morrai.damage").defineInRange("morrai.damage", 6, 0, 1000);
        morrai_durability = builder.comment(" Default: 300").translation("rigoranthusemortisreborn.config.server.morrai.durability").defineInRange("morrai.durability", 300, 0, 100000);

        razortooth_kunai_damage = builder.comment(" Default: 3").translation("rigoranthusemortisreborn.config.server.kunai.damage").defineInRange("kunai.damage", 3, 0, 1000);
        razortooth_kunai_velocity = builder.comment(" How fast the weapon travels when thrown\n Default: 1.5").translation("rigoranthusemortisreborn.config.server.kunai.velocity").defineInRange("kunai.velocity", 1.5, 0.0, 1000.0);
        razortooth_kunai_inaccuracy = builder.comment(" Higher number = Less accurate\n Default: 2.4").translation("rigoranthusemortisreborn.config.server.kunai.inaccuracy").defineInRange("kunai.inaccuracy", 2.4, 0, 100000);

        throwing_knife_damage = builder.comment(" Default: 5").translation("rigoranthusemortisreborn.config.server.throwing_knife.damage").defineInRange("throwing_knife.damage", 5, 0, 1000);
        throwing_knife_velocity = builder.comment(" How fast the weapon travels when thrown\n Default: 1.2").translation("rigoranthusemortisreborn.config.server.throwing_knife.velocity").defineInRange("throwing_knife.velocity", 1.2, 0.0, 1000.0);
        throwing_knife_inaccuracy = builder.comment(" Higher number = Less accurate\n Default: 2.8").translation("rigoranthusemortisreborn.config.server.throwing_knife.inaccuracy").defineInRange("throwing_knife.inaccuracy", 2.8, 0, 100000);

        razortooth_frisbee_damage = builder.comment(" Default: 5").translation("rigoranthusemortisreborn.config.server.razortooth_frisbee.damage").defineInRange("razortooth_frisbee.damage", 5, 0, 1000);
        razortooth_frisbee_velocity = builder.comment(" How fast the weapon travels when thrown\n Default: 1.4").translation("rigoranthusemortisreborn.config.server.razortooth_frisbee.velocity").defineInRange("razortooth_frisbee.velocity", 1.4, 0.0, 1000.0);
        razortooth_frisbee_inaccuracy = builder.comment(" Higher number = Less accurate\n Default: 1.0").translation("rigoranthusemortisreborn.config.server.razortooth_frisbee.inaccuracy").defineInRange("razortooth_frisbee.inaccuracy", 1.0, 0, 100000);
        razortooth_frisbee_duration = builder.comment(" How long, in ticks, it will take for the weapon to either disappear, or return to the player once it has been thrown\n Default: 70").translation("rigoranthusemortisreborn.config.server.razortooth_frisbee.duration").defineInRange("razortooth_frisbee.duration", 70, 0, 100000);
        razortooth_frisbee_durability = builder.comment(" Default: 250").translation("rigoranthusemortisreborn.config.server.razortooth_frisbee.durability").defineInRange("razortooth_frisbee.durability", 250, 0, 100000);

        ricochet_round_damage = builder.comment(" Default: 4").translation("rigoranthusemortisreborn.config.server.ricochet_round.damage").defineInRange("ricochet_round.damage", 4, 0, 1000);
        ricochet_round_velocity = builder.comment(" How fast the weapon travels when thrown\n Default: 1.5").translation("rigoranthusemortisreborn.config.server.ricochet_round.velocity").defineInRange("ricochet_round.velocity", 1.5, 0.0, 1000.0);
        ricochet_round_inaccuracy = builder.comment(" Higher number = Less accurate\n Default: 1.0").translation("rigoranthusemortisreborn.config.server.ricochet_round.inaccuracy").defineInRange("ricochet_round.inaccuracy", 1.0, 0, 100000);
        ricochet_round_duration = builder.comment(" How long, in ticks, it will take for the weapon to either disappear, or return to the player once it has been thrown.\n Default: 30").translation("rigoranthusemortisreborn.config.server.ricochet_round.duration").defineInRange("ricochet_round.duration", 30, 0, 100000);

        ghastly_scepter_speed = builder.comment(" Default: -1.5").translation("rigoranthusemortisreborn.config.server.ghastly_scepter.speed").defineInRange("ghastly_scepter.speed", -1.5, -1000.0, 1000);
        ghastly_scepter_damage = builder.comment(" Default: 8").translation("rigoranthusemortisreborn.config.server.ghastly_scepter.damage").defineInRange("ghastly_scepter.damage", 8, 0, 1000);
        ghastly_scepter_durability = builder.comment(" Default: 500").translation("rigoranthusemortisreborn.config.server.ghastly_scepter.durability").defineInRange("ghastly_scepter.durability", 500, 0, 100000);

        cry_of_desperation_speed = builder.comment(" Default: -2.5").translation("rigoranthusemortisreborn.config.server.cry_of_desperation.speed").defineInRange("cry_of_desperation.speed", -2.5, -1000.0, 1000);
        cry_of_desperation_damage = builder.comment(" Default: 8").translation("rigoranthusemortisreborn.config.server.cry_of_desperation.damage").defineInRange("cry_of_desperation.damage", 8, 0, 1000);
        cry_of_desperation_durability = builder.comment(" Default: 50").translation("rigoranthusemortisreborn.config.server.cry_of_desperation.durability").defineInRange("cry_of_desperation.durability", 50, 0, 100000);
        enable_hellfire_rain = builder.comment(" Enable or Disable the ability for this weapon to call forth a rain of hellfire.\n Default: true").translation("rigoranthusemortisreborn.config.server.cry_of_desperation.enable_hellfire").define("cry_of_desperation.enable_hellfire", true);

        bone_spear_speed = builder.comment(" Default: -1.0").translation("rigoranthusemortisreborn.config.server.spear.speed").defineInRange("spear.speed", -1.0, -1000.0, 1000);
        bone_spear_damage = builder.comment(" Default: 4.0").translation("rigoranthusemortisreborn.config.server.spear.damage").defineInRange("spear.damage", 4, 0, 1000);
        bone_tier_durability = builder.comment(" Default: 230").translation("rigoranthusemortisreborn.config.server.bone_tier.durability").defineInRange("bone_tier.durability", 230, 0, 100000);
        bone_tier_speed = builder.comment(" Default: 9.0").translation("rigoranthusemortisreborn.config.server.bone_tier.speed").defineInRange("bone_tier.speed", 9.0, 0.0, 1000);
        bone_tier_damage = builder.comment(" Default: 2.0").translation("rigoranthusemortisreborn.config.server.bone_tier.damage").defineInRange("bone_tier.damage", 2.0, 0.0, 1000);
        bone_tier_enchantability = builder.comment(" Default: 20").translation("rigoranthusemortisreborn.config.server.bone_tier.enchantability").defineInRange("bone_tier.enchantability", 20, 0, 100);
        bone_bow_projectile_range = builder.comment(" Set Default Projectile Range of the Bone Bow.\n Default: 15").translation("rigoranthusemortisreborn.config.server.range.bone_bow").defineInRange("range.bone_bow", 15, 1, 1000);
        bone_bow_durability = builder.comment(" Set Durability of the Bone Bow.\n Vanilla Bow Default: 384\n Bone Bow Default: 500").translation("rigoranthusemortisreborn.config.server.durability.bone_bow").defineInRange("durability.bone_bow", 500, 1, 100000);
    }
    private static void setupApogeanConfig(ForgeConfigSpec.Builder builder) {
        enableApogeanArmorClimmbingEffect = builder.comment(" Enable or Disable the ability to Climb on Walls\n when a full set of Apogean Armor is Equipped.\n Default: True").translation("rigoranthusemortisreborn.config.server.apogean_set_bonus.climbing").define("apogean_set_bonus.climbing", true);
        apogean_sword_speed = builder.comment(" Default:80\n Default Netherite: 9.0").translation("rigoranthusemortisreborn.config.server.apogean_sword.speed").defineInRange("apogean_sword.speed", 80.0, -1000.0, 1000);
        apogean_sword_damage = builder.comment(" Default:50\n Default Netherite: 4.0").translation("rigoranthusemortisreborn.config.server.apogean_sword.damage").defineInRange("apogean_sword.damage", 50, 0, 1000);
        apogean_axe_speed = builder.comment(" Default:80\n Default Netherite: 9.0").translation("rigoranthusemortisreborn.config.server.apogean_axe.speed").defineInRange("apogean_axe.speed", 80.0, -1000.0, 1000);
        apogean_axe_damage = builder.comment(" Default:50\n Default Netherite: 4.0").translation("rigoranthusemortisreborn.config.server.apogean_axe.damage").defineInRange("apogean_axe.damage", 50, 0, 1000);
        apogean_tier_durability = builder.comment(" Default:8000\n Default Netherite: 2031").translation("rigoranthusemortisreborn.config.server.apogean_tier.durability").defineInRange("apogean_tier.durability", 8000, 0, 100000);
        apogean_tier_speed = builder.comment(" Default:80\n Default Netherite: 9.0").translation("rigoranthusemortisreborn.config.server.apogean_tier.speed").defineInRange("apogean_tier.speed", 80.0, -1000.0, 1000);
        apogean_tier_damage = builder.comment(" Default:50\n Default Netherite: 4.0").translation("rigoranthusemortisreborn.config.server.apogean_tier.damage").defineInRange("apogean_tier.damage", 50.0, 0.0, 1000);
        apogean_tier_enchantability = builder.comment(" Default:75\n Default Netherite: 15").translation("rigoranthusemortisreborn.config.server.apogean_tier.enchantability").defineInRange("apogean_tier.enchantability", 75, 0, 100);

        apogean_boots_damage_reduction = builder.comment(" Boots Damage Reduction Value.\n Mod Default: 12\n Default Netherite: 3")
                .translation("rigoranthusemortisreborn.config.server.per_slot_apogean.apogean_boots_damage_reduction").defineInRange("per_slot_apogean.apogean_boots_damage_reduction", 12, 0, 1000);
        apogean_leggings_damage_reduction = builder.comment(" Leggings Damage Reduction Value.\n Mod Default: 24\n Default Netherite: 6")
                .translation("rigoranthusemortisreborn.config.server.per_slot_apogean.apogean_leggings_damage_reduction").defineInRange("per_slot_apogean.apogean_leggings_damage_reduction", 24, 0, 1000);
        apogean_chestplate_damage_reduction = builder.comment(" Chestplate Damage Reduction Value.\n Mod Default: 32\n Default Netherite: 8")
                .translation("rigoranthusemortisreborn.config.server.per_slot_apogean.apogean_chestplate_damage_reduction").defineInRange("per_slot_apogean.apogean_chestplate_damage_reduction", 32, 0, 1000);
        apogean_helmet_damage_reduction = builder.comment(" Helmet Damage Reduction Value.\n Mod Default: 12\n Default Netherite: 3")
                .translation("rigoranthusemortisreborn.config.server.per_slot_apogean.apogean_helmet_damage_reduction").defineInRange("per_slot_apogean.apogean_helmet_damage_reduction", 12, 0, 1000);
        apogean_durability_multiplier = builder.comment(" Mod Default: 120\n Default Netherite: 37")
                .translation("rigoranthusemortisreborn.config.server.general_values_apogean.durability_multiplier").defineInRange("general_values_apogean.durability_multiplier", 120, 0, 1000);
        apogean_armor_enchantability = builder.comment(" Mod Default: 75\n Default Netherite: 15")
                .translation("rigoranthusemortisreborn.config.server.general_values_apogean.enchantability").defineInRange("general_values_apogean.enchantability", 75, 0, 100);
        apogean_toughness = builder.comment(" Mod Default: 3\n Default Netherite: 3")
                .translation("rigoranthusemortisreborn.config.server.general_values_apogean.toughness").defineInRange("general_values_apogean.toughness", 3.0, 0.0, 10.0);
        apogean_knockback_resistance = builder.comment(" Mod Default: 0.1\n Default Netherite: 0.1")
                .translation("rigoranthusemortisreborn.config.server.general_values_apogean.knockback_resistance").defineInRange("general_values_apogean.knockback_resistance", 0.1, 0.0, 1.0);
    }
    private static void setupAqueousConfig(ForgeConfigSpec.Builder builder) {
        aqueous_sword_speed = builder.comment(" Default: 16\n Default Netherite: 9.0").translation("rigoranthusemortisreborn.config.server.aqueous_sword.speed").defineInRange("aqueous_sword.speed", 16.0, -1000.0, 1000);
        aqueous_sword_damage = builder.comment(" Default: 40\n Default Netherite: 4.0").translation("rigoranthusemortisreborn.config.server.aqueous_sword.damage").defineInRange("aqueous_sword.damage", 40, 0, 1000);
        aqueous_axe_speed = builder.comment(" Default: 16\n Default Netherite: 9.0").translation("rigoranthusemortisreborn.config.server.aqueous_axe.speed").defineInRange("aqueous_axe.speed", 16.0, -1000.0, 1000);
        aqueous_axe_damage = builder.comment(" Default: 40\n Default Netherite: 4.0").translation("rigoranthusemortisreborn.config.server.aqueous_axe.damage").defineInRange("aqueous_axe.damage", 40, 0, 1000);
        aqueous_tier_durability = builder.comment(" Default: 4000\n Default Netherite: 2031").translation("rigoranthusemortisreborn.config.server.aqueous_tier.durability").defineInRange("aqueous_tier.durability", 4000, 0, 100000);
        aqueous_tier_speed = builder.comment(" Default: 16\n Default Netherite: 9.0").translation("rigoranthusemortisreborn.config.server.aqueous_tier.speed").defineInRange("aqueous_tier.speed", 16.0, -1000.0, 1000);
        aqueous_tier_damage = builder.comment(" Default: 40\n Default Netherite: 4.0").translation("rigoranthusemortisreborn.config.server.aqueous_tier.damage").defineInRange("aqueous_tier.damage", 40.0, 0.0, 1000);
        aqueous_tier_enchantability = builder.comment(" Default: 25\n Default Netherite: 15").translation("rigoranthusemortisreborn.config.server.aqueous_tier.enchantability").defineInRange("aqueous_tier.enchantability", 25, 0, 100);

        aqueous_boots_damage_reduction = builder.comment(" Boots Damage Reduction Value.\n Mod Default: 12\n Default Netherite: 3")
                .translation("rigoranthusemortisreborn.config.server.per_slot_aqueous.aqueous_boots_damage_reduction").defineInRange("per_slot_aqueous.aqueous_boots_damage_reduction", 12, 0, 1000);
        aqueous_leggings_damage_reduction = builder.comment(" Leggings Damage Reduction Value.\n Mod Default: 24\n Default Netherite: 6")
                .translation("rigoranthusemortisreborn.config.server.per_slot_aqueous.aqueous_leggings_damage_reduction").defineInRange("per_slot_aqueous.aqueous_leggings_damage_reduction", 24, 0, 1000);
        aqueous_chestplate_damage_reduction = builder.comment(" Chestplate Damage Reduction Value.\n Mod Default: 32\n Default Netherite: 8")
                .translation("rigoranthusemortisreborn.config.server.per_slot_aqueous.aqueous_chestplate_damage_reduction").defineInRange("per_slot_aqueous.aqueous_chestplate_damage_reduction", 32, 0, 1000);
        aqueous_helmet_damage_reduction = builder.comment(" Helmet Damage Reduction Value.\n Mod Default: 12\n Default Netherite: 3")
                .translation("rigoranthusemortisreborn.config.server.per_slot_aqueous.aqueous_helmet_damage_reduction").defineInRange("per_slot_aqueous.aqueous_helmet_damage_reduction", 12, 0, 1000);
        aqueous_durability_multiplier = builder.comment(" Mod Default: 90\n Default Netherite: 37")
                .translation("rigoranthusemortisreborn.config.server.general_values_aqueous.durability_multiplier").defineInRange("general_values_aqueous.durability_multiplier", 90, 0, 1000);
        aqueous_armor_enchantability = builder.comment(" Mod Default: 25\n Default Netherite: 15")
                .translation("rigoranthusemortisreborn.config.server.general_values_aqueous.enchantability").defineInRange("general_values_aqueous.enchantability", 25, 0, 100);
        aqueous_toughness = builder.comment(" Mod Default: 3\n Default Netherite: 3")
                .translation("rigoranthusemortisreborn.config.server.general_values_aqueous.toughness").defineInRange("general_values_aqueous.toughness", 3.0, 0.0, 10.0);
        aqueous_knockback_resistance = builder.comment(" Mod Default: 0.1\n Default Netherite: 0.1")
                .translation("rigoranthusemortisreborn.config.server.general_values_aqueous.knockback_resistance").defineInRange("general_values_aqueous.knockback_resistance", 0.1, 0.0, 1.0);
    }
    private static void setupAtrophyingConfig(ForgeConfigSpec.Builder builder) {
        atrophying_sword_speed = builder.comment(" Default: 14\n Default Netherite: 9.0").translation("rigoranthusemortisreborn.config.server.atrophying_sword.speed").defineInRange("atrophying_sword.speed", 14.0, -1000.0, 1000);
        atrophying_sword_damage = builder.comment(" Default: 35\n Default Netherite: 4.0").translation("rigoranthusemortisreborn.config.server.atrophying_sword.damage").defineInRange("atrophying_sword.damage", 35, 0, 1000);
        atrophying_axe_speed = builder.comment(" Default: 14\n Default Netherite: 9.0").translation("rigoranthusemortisreborn.config.server.atrophying_axe.speed").defineInRange("atrophying_axe.speed", 14.0, -1000.0, 1000);
        atrophying_axe_damage = builder.comment(" Default: 35\n Default Netherite: 4.0").translation("rigoranthusemortisreborn.config.server.atrophying_axe.damage").defineInRange("atrophying_axe.damage", 35, 0, 1000);
        atrophying_tier_durability = builder.comment(" Default: 3000\n Default Netherite: 2031").translation("rigoranthusemortisreborn.config.server.atrophying_tier.durability").defineInRange("atrophying_tier.durability", 3000, 0, 100000);
        atrophying_tier_speed = builder.comment(" Default: 14\n Default Netherite: 9.0").translation("rigoranthusemortisreborn.config.server.atrophying_tier.speed").defineInRange("atrophying_tier.speed", 14.0, -1000.0, 1000);
        atrophying_tier_damage = builder.comment(" Default: 35\n Default Netherite: 4.0").translation("rigoranthusemortisreborn.config.server.atrophying_tier.damage").defineInRange("atrophying_tier.damage", 35.0, 0.0, 1000);
        atrophying_tier_enchantability = builder.comment(" Default: 20\n Default Netherite: 15").translation("rigoranthusemortisreborn.config.server.atrophying_tier.enchantability").defineInRange("atrophying_tier.enchantability", 20, 0, 100);

        atrophying_boots_damage_reduction = builder.comment(" Boots Damage Reduction Value.\n Mod Default: 12\n Default Netherite: 3")
                .translation("rigoranthusemortisreborn.config.server.per_slot_atrophying.atrophying_boots_damage_reduction").defineInRange("per_slot_atrophying.atrophying_boots_damage_reduction", 12, 0, 1000);
        atrophying_leggings_damage_reduction = builder.comment(" Leggings Damage Reduction Value.\n Mod Default: 24\n Default Netherite: 6")
                .translation("rigoranthusemortisreborn.config.server.per_slot_atrophying.atrophying_leggings_damage_reduction").defineInRange("per_slot_atrophying.atrophying_leggings_damage_reduction", 24, 0, 1000);
        atrophying_chestplate_damage_reduction = builder.comment(" Chestplate Damage Reduction Value.\n Mod Default: 32\n Default Netherite: 8")
                .translation("rigoranthusemortisreborn.config.server.per_slot_atrophying.atrophying_chestplate_damage_reduction").defineInRange("per_slot_atrophying.atrophying_chestplate_damage_reduction", 32, 0, 1000);
        atrophying_helmet_damage_reduction = builder.comment(" Helmet Damage Reduction Value.\n Mod Default: 12\n Default Netherite: 3")
                .translation("rigoranthusemortisreborn.config.server.per_slot_atrophying.atrophying_helmet_damage_reduction").defineInRange("per_slot_atrophying.atrophying_helmet_damage_reduction", 12, 0, 1000);
        atrophying_durability_multiplier = builder.comment(" Mod Default: 50\n Default Netherite: 37")
                .translation("rigoranthusemortisreborn.config.server.general_values_atrophying.durability_multiplier").defineInRange("general_values_atrophying.durability_multiplier", 50, 0, 1000);
        atrophying_armor_enchantability = builder.comment(" Mod Default: 20\n Default Netherite: 15")
                .translation("rigoranthusemortisreborn.config.server.general_values_atrophying.enchantability").defineInRange("general_values_atrophying.enchantability", 20, 0, 100);
        atrophying_toughness = builder.comment(" Mod Default: 3\n Default Netherite: 3")
                .translation("rigoranthusemortisreborn.config.server.general_values_atrophying.toughness").defineInRange("general_values_atrophying.toughness", 3.0, 0.0, 10.0);
        atrophying_knockback_resistance = builder.comment(" Mod Default: 0.1\n Default Netherite: 0.1")
                .translation("rigoranthusemortisreborn.config.server.general_values_atrophying.knockback_resistance").defineInRange("general_values_atrophying.knockback_resistance", 0.1, 0.0, 1.0);
    }
    private static void setupIncorporealConfig(ForgeConfigSpec.Builder builder) {
        incorporeal_sword_speed = builder.comment(" Default: 12\n Default Netherite: 9.0").translation("rigoranthusemortisreborn.config.server.incorporeal_sword.speed").defineInRange("incorporeal_sword.speed", 12.0, -1000.0, 1000);
        incorporeal_sword_damage = builder.comment(" Default: 25\n Default Netherite: 4.0").translation("rigoranthusemortisreborn.config.server.incorporeal_sword.damage").defineInRange("incorporeal_sword.damage", 25, 0, 1000);
        incorporeal_axe_speed = builder.comment(" Default: 12\n Default Netherite: 9.0").translation("rigoranthusemortisreborn.config.server.incorporeal_axe.speed").defineInRange("incorporeal_axe.speed", 12.0, -1000.0, 1000);
        incorporeal_axe_damage = builder.comment(" Default: 25\n Default Netherite: 4.0").translation("rigoranthusemortisreborn.config.server.incorporeal_axe.damage").defineInRange("incorporeal_axe.damage", 25, 0, 1000);
        incorporeal_tier_durability = builder.comment(" Default: 2000\n Default Netherite: 2031").translation("rigoranthusemortisreborn.config.server.incorporeal_tier.durability").defineInRange("incorporeal_tier.durability", 2000, 0, 100000);
        incorporeal_tier_speed = builder.comment(" Default: 12\n Default Netherite: 9.0").translation("rigoranthusemortisreborn.config.server.incorporeal_tier.speed").defineInRange("incorporeal_tier.speed", 12.0, -1000.0, 1000);
        incorporeal_tier_damage = builder.comment(" Default: 25\n Default Netherite: 4.0").translation("rigoranthusemortisreborn.config.server.incorporeal_tier.damage").defineInRange("incorporeal_tier.damage", 25.0, 0.0, 1000);
        incorporeal_tier_enchantability = builder.comment(" Default: 40\n Default Netherite: 15").translation("rigoranthusemortisreborn.config.server.incorporeal_tier.enchantability").defineInRange("incorporeal_tier.enchantability", 40, 0, 100);

        incorporeal_boots_damage_reduction = builder.comment(" Boots Damage Reduction Value.\n Mod Default: 9\n Default Netherite: 3")
                .translation("rigoranthusemortisreborn.config.server.per_slot_incorporeal.incorporeal_boots_damage_reduction").defineInRange("per_slot_incorporeal.incorporeal_boots_damage_reduction", 9, 0, 1000);
        incorporeal_leggings_damage_reduction = builder.comment(" Leggings Damage Reduction Value.\n Mod Default: 18\n Default Netherite: 6")
                .translation("rigoranthusemortisreborn.config.server.per_slot_incorporeal.incorporeal_leggings_damage_reduction").defineInRange("per_slot_incorporeal.incorporeal_leggings_damage_reduction", 18, 0, 1000);
        incorporeal_chestplate_damage_reduction = builder.comment(" Chestplate Damage Reduction Value.\n Mod Default: 24\n Default Netherite: 8")
                .translation("rigoranthusemortisreborn.config.server.per_slot_incorporeal.incorporeal_chestplate_damage_reduction").defineInRange("per_slot_incorporeal.incorporeal_chestplate_damage_reduction", 24, 0, 1000);
        incorporeal_helmet_damage_reduction = builder.comment(" Helmet Damage Reduction Value.\n Mod Default: 9\n Default Netherite: 3")
                .translation("rigoranthusemortisreborn.config.server.per_slot_incorporeal.incorporeal_helmet_damage_reduction").defineInRange("per_slot_incorporeal.incorporeal_helmet_damage_reduction", 9, 0, 1000);
        incorporeal_durability_multiplier = builder.comment(" Mod Default: 50\n Default Netherite: 37")
                .translation("rigoranthusemortisreborn.config.server.general_values_incorporeal.durability_multiplier").defineInRange("general_values_incorporeal.durability_multiplier", 50, 0, 1000);
        incorporeal_armor_enchantability = builder.comment(" Mod Default: 40\n Default Netherite: 15")
                .translation("rigoranthusemortisreborn.config.server.general_values_incorporeal.enchantability").defineInRange("general_values_incorporeal.enchantability", 40, 0, 100);
        incorporeal_toughness = builder.comment(" Mod Default: 3\n Default Netherite: 3")
                .translation("rigoranthusemortisreborn.config.server.general_values_incorporeal.toughness").defineInRange("general_values_incorporeal.toughness", 3.0, 0.0, 10.0);
        incorporeal_knockback_resistance = builder.comment(" Mod Default: 0.1\n Default Netherite: 0.1")
                .translation("rigoranthusemortisreborn.config.server.general_values_incorporeal.knockback_resistance").defineInRange("general_values_incorporeal.knockback_resistance", 0.1, 0.0, 1.0);
    }
    private static void setupInfernalConfig(ForgeConfigSpec.Builder builder) {
        infernal_sword_speed = builder.comment(" Default: 6\n Default Netherite: 9.0").translation("rigoranthusemortisreborn.config.server.infernal_sword.speed").defineInRange("infernal_sword.speed", 6.0, -1000.0, 1000);
        infernal_sword_damage = builder.comment(" Default: 40\n Default Netherite: 4.0").translation("rigoranthusemortisreborn.config.server.infernal_sword.damage").defineInRange("infernal_sword.damage", 40, 0, 1000);
        infernal_axe_speed = builder.comment(" Default: 6\n Default Netherite: 9.0").translation("rigoranthusemortisreborn.config.server.infernal_axe.speed").defineInRange("infernal_axe.speed", 6.0, -1000.0, 1000);
        infernal_axe_damage = builder.comment(" Default: 40\n Default Netherite: 4.0").translation("rigoranthusemortisreborn.config.server.infernal_axe.damage").defineInRange("infernal_axe.damage", 40, 0, 1000);
        infernal_tier_durability = builder.comment(" Default: 4000\n Default Netherite: 2031").translation("rigoranthusemortisreborn.config.server.infernal_tier.durability").defineInRange("infernal_tier.durability", 4000, 0, 100000);
        infernal_tier_speed = builder.comment(" Default: 6\n Default Netherite: 9.0").translation("rigoranthusemortisreborn.config.server.infernal_tier.speed").defineInRange("infernal_tier.speed", 6.0, -1000.0, 1000);
        infernal_tier_damage = builder.comment(" Default: 40\n Default Netherite: 4.0").translation("rigoranthusemortisreborn.config.server.infernal_tier.damage").defineInRange("infernal_tier.damage", 40.0, 0.0, 1000);
        infernal_tier_enchantability = builder.comment(" Default: 25\n Default Netherite: 15").translation("rigoranthusemortisreborn.config.server.infernal_tier.enchantability").defineInRange("infernal_tier.enchantability", 25, 0, 100);

        infernal_boots_damage_reduction = builder.comment(" Boots Damage Reduction Value.\n Mod Default: 12\n Default Netherite: 3")
                .translation("rigoranthusemortisreborn.config.server.per_slot_infernal.infernal_boots_damage_reduction").defineInRange("per_slot_infernal.infernal_boots_damage_reduction", 12, 0, 1000);
        infernal_leggings_damage_reduction = builder.comment(" Leggings Damage Reduction Value.\n Mod Default: 24\n Default Netherite: 6")
                .translation("rigoranthusemortisreborn.config.server.per_slot_infernal.infernal_leggings_damage_reduction").defineInRange("per_slot_infernal.infernal_leggings_damage_reduction", 24, 0, 1000);
        infernal_chestplate_damage_reduction = builder.comment(" Chestplate Damage Reduction Value.\n Mod Default: 32\n Default Netherite: 8")
                .translation("rigoranthusemortisreborn.config.server.per_slot_infernal.infernal_chestplate_damage_reduction").defineInRange("per_slot_infernal.infernal_chestplate_damage_reduction", 32, 0, 1000);
        infernal_helmet_damage_reduction = builder.comment(" Helmet Damage Reduction Value.\n Mod Default: 12\n Default Netherite: 3")
                .translation("rigoranthusemortisreborn.config.server.per_slot_infernal.infernal_helmet_damage_reduction").defineInRange("per_slot_infernal.infernal_helmet_damage_reduction", 12, 0, 1000);
        infernal_durability_multiplier = builder.comment(" Mod Default: 90\n Default Netherite: 37")
                .translation("rigoranthusemortisreborn.config.server.general_values_infernal.durability_multiplier").defineInRange("general_values_infernal.durability_multiplier", 90, 0, 1000);
        infernal_armor_enchantability = builder.comment(" Mod Default: 25\n Default Netherite: 15")
                .translation("rigoranthusemortisreborn.config.server.general_values_infernal.enchantability").defineInRange("general_values_infernal.enchantability", 25, 0, 100);
        infernal_toughness = builder.comment(" Mod Default: 3\n Default Netherite: 3")
                .translation("rigoranthusemortisreborn.config.server.general_values_infernal.toughness").defineInRange("general_values_infernal.toughness", 3.0, 0.0, 10.0);
        infernal_knockback_resistance = builder.comment(" Mod Default: 0.1\n Default Netherite: 0.1")
                .translation("rigoranthusemortisreborn.config.server.general_values_infernal.knockback_resistance").defineInRange("general_values_infernal.knockback_resistance", 0.1, 0.0, 1.0);
    }
    private static void setupOpulentConfig(ForgeConfigSpec.Builder builder) {
        opulent_sword_speed = builder.comment(" Default: 14\n Default Netherite: 9.0").translation("rigoranthusemortisreborn.config.server.opulent_sword.speed").defineInRange("opulent_sword.speed", 14.0, -1000.0, 1000);
        opulent_sword_damage = builder.comment(" Default: 20\n Default Netherite: 4.0").translation("rigoranthusemortisreborn.config.server.opulent_sword.damage").defineInRange("opulent_sword.damage", 20, 0, 1000);
        opulent_axe_speed = builder.comment(" Default: 14\n Default Netherite: 9.0").translation("rigoranthusemortisreborn.config.server.opulent_axe.speed").defineInRange("opulent_axe.speed", 14.0, -1000.0, 1000);
        opulent_axe_damage = builder.comment(" Default: 20\n Default Netherite: 4.0").translation("rigoranthusemortisreborn.config.server.opulent_axe.damage").defineInRange("opulent_axe.damage", 20, 0, 1000);
        opulent_tier_durability = builder.comment(" Default: 2500\n Default Netherite: 2031").translation("rigoranthusemortisreborn.config.server.opulent_tier.durability").defineInRange("opulent_tier.durability", 2500, 0, 100000);
        opulent_tier_speed = builder.comment(" Default: 14\n Default Netherite: 9.0").translation("rigoranthusemortisreborn.config.server.opulent_tier.speed").defineInRange("opulent_tier.speed", 14.0, -1000.0, 1000);
        opulent_tier_damage = builder.comment(" Default: 20\n Default Netherite: 4.0").translation("rigoranthusemortisreborn.config.server.opulent_tier.damage").defineInRange("opulent_tier.damage", 20.0, 0.0, 1000);
        opulent_tier_enchantability = builder.comment(" Default: 70\n Default Netherite: 15").translation("rigoranthusemortisreborn.config.server.opulent_tier.enchantability").defineInRange("opulent_tier.enchantability", 70, 0, 100);

        opulent_boots_damage_reduction = builder.comment(" Boots Damage Reduction Value.\n Mod Default: 10\n Default Netherite: 3")
                .translation("rigoranthusemortisreborn.config.server.per_slot_opulent.opulent_boots_damage_reduction").defineInRange("per_slot_opulent.opulent_boots_damage_reduction", 10, 0, 1000);
        opulent_leggings_damage_reduction = builder.comment(" Leggings Damage Reduction Value.\n Mod Default: 20\n Default Netherite: 6")
                .translation("rigoranthusemortisreborn.config.server.per_slot_opulent.opulent_leggings_damage_reduction").defineInRange("per_slot_opulent.opulent_leggings_damage_reduction", 20, 0, 1000);
        opulent_chestplate_damage_reduction = builder.comment(" Chestplate Damage Reduction Value.\n Mod Default: 28\n Default Netherite: 8")
                .translation("rigoranthusemortisreborn.config.server.per_slot_opulent.opulent_chestplate_damage_reduction").defineInRange("per_slot_opulent.opulent_chestplate_damage_reduction", 28, 0, 1000);
        opulent_helmet_damage_reduction = builder.comment(" Helmet Damage Reduction Value.\n Mod Default: 10\n Default Netherite: 3")
                .translation("rigoranthusemortisreborn.config.server.per_slot_opulent.opulent_helmet_damage_reduction").defineInRange("per_slot_opulent.opulent_helmet_damage_reduction", 10, 0, 1000);
        opulent_durability_multiplier = builder.comment(" Mod Default: 45\n Default Netherite: 37")
                .translation("rigoranthusemortisreborn.config.server.general_values_opulent.durability_multiplier").defineInRange("general_values_opulent.durability_multiplier", 45, 0, 1000);
        opulent_armor_enchantability = builder.comment(" Mod Default: 70\n Default Netherite: 15")
                .translation("rigoranthusemortisreborn.config.server.general_values_opulent.enchantability").defineInRange("general_values_opulent.enchantability", 70, 0, 100);
        opulent_toughness = builder.comment(" Mod Default: 3\n Default Netherite: 3")
                .translation("rigoranthusemortisreborn.config.server.general_values_opulent.toughness").defineInRange("general_values_opulent.toughness", 3.0, 0.0, 10.0);
        opulent_knockback_resistance = builder.comment(" Mod Default: 0.1\n Default Netherite: 0.1")
                .translation("rigoranthusemortisreborn.config.server.general_values_opulent.knockback_resistance").defineInRange("general_values_opulent.knockback_resistance", 0.1, 0.0, 1.0);
    }
    private static void setupPerniciousConfig(ForgeConfigSpec.Builder builder) {
        pernicious_sword_speed = builder.comment(" Default: 6\n Default Netherite: 9.0").translation("rigoranthusemortisreborn.config.server.pernicious_sword.speed").defineInRange("pernicious_sword.speed", 6.0, -1000.0, 1000);
        pernicious_sword_damage = builder.comment(" Default: 40\n Default Netherite: 4.0").translation("rigoranthusemortisreborn.config.server.pernicious_sword.damage").defineInRange("pernicious_sword.damage", 40, 0, 1000);
        pernicious_axe_speed = builder.comment(" Default: 6\n Default Netherite: 9.0").translation("rigoranthusemortisreborn.config.server.pernicious_axe.speed").defineInRange("pernicious_axe.speed", 6.0, -1000.0, 1000);
        pernicious_axe_damage = builder.comment(" Default: 40\n Default Netherite: 4.0").translation("rigoranthusemortisreborn.config.server.pernicious_axe.damage").defineInRange("pernicious_axe.damage", 40, 0, 1000);
        pernicious_tier_durability = builder.comment(" Default: 4000\n Default Netherite: 2031").translation("rigoranthusemortisreborn.config.server.pernicious_tier.durability").defineInRange("pernicious_tier.durability", 4000, 0, 100000);
        pernicious_tier_speed = builder.comment(" Default: 6\n Default Netherite: 9.0").translation("rigoranthusemortisreborn.config.server.pernicious_tier.speed").defineInRange("pernicious_tier.speed", 6.0, -1000.0, 1000);
        pernicious_tier_damage = builder.comment(" Default: 40\n Default Netherite: 4.0").translation("rigoranthusemortisreborn.config.server.pernicious_tier.damage").defineInRange("pernicious_tier.damage", 40.0, 0.0, 1000);
        pernicious_tier_enchantability = builder.comment(" Default: 25\n Default Netherite: 15").translation("rigoranthusemortisreborn.config.server.pernicious_tier.enchantability").defineInRange("pernicious_tier.enchantability", 25, 0, 100);

        pernicious_boots_damage_reduction = builder.comment(" Boots Damage Reduction Value.\n Mod Default: 12\n Default Netherite: 3")
                .translation("rigoranthusemortisreborn.config.server.per_slot_pernicious.pernicious_boots_damage_reduction").defineInRange("per_slot_pernicious.pernicious_boots_damage_reduction", 12, 0, 1000);
        pernicious_leggings_damage_reduction = builder.comment(" Leggings Damage Reduction Value.\n Mod Default: 24\n Default Netherite: 6")
                .translation("rigoranthusemortisreborn.config.server.per_slot_pernicious.pernicious_leggings_damage_reduction").defineInRange("per_slot_pernicious.pernicious_leggings_damage_reduction", 24, 0, 1000);
        pernicious_chestplate_damage_reduction = builder.comment(" Chestplate Damage Reduction Value.\n Mod Default: 32\n Default Netherite: 8")
                .translation("rigoranthusemortisreborn.config.server.per_slot_pernicious.pernicious_chestplate_damage_reduction").defineInRange("per_slot_pernicious.pernicious_chestplate_damage_reduction", 32, 0, 1000);
        pernicious_helmet_damage_reduction = builder.comment(" Helmet Damage Reduction Value.\n Mod Default: 12\n Default Netherite: 3")
                .translation("rigoranthusemortisreborn.config.server.per_slot_pernicious.pernicious_helmet_damage_reduction").defineInRange("per_slot_pernicious.pernicious_helmet_damage_reduction", 12, 0, 1000);
        pernicious_durability_multiplier = builder.comment(" Mod Default: 90\n Default Netherite: 37")
                .translation("rigoranthusemortisreborn.config.server.general_values_pernicious.durability_multiplier").defineInRange("general_values_pernicious.durability_multiplier", 90, 0, 1000);
        pernicious_armor_enchantability = builder.comment(" Mod Default: 25\n Default Netherite: 15")
                .translation("rigoranthusemortisreborn.config.server.general_values_pernicious.enchantability").defineInRange("general_values_pernicious.enchantability", 25, 0, 100);
        pernicious_toughness = builder.comment(" Mod Default: 3\n Default Netherite: 3")
                .translation("rigoranthusemortisreborn.config.server.general_values_pernicious.toughness").defineInRange("general_values_pernicious.toughness", 3.0, 0.0, 10.0);
        pernicious_knockback_resistance = builder.comment(" Mod Default: 0.1\n Default Netherite: 0.1")
                .translation("rigoranthusemortisreborn.config.server.general_values_pernicious.knockback_resistance").defineInRange("general_values_pernicious.knockback_resistance", 0.1, 0.0, 1.0);
    }
    private static void setupPhantasmalConfig(ForgeConfigSpec.Builder builder) {
        phantasmal_sword_speed = builder.comment(" Default: 10\n Default Netherite: 9.0").translation("rigoranthusemortisreborn.config.server.phantasmal_sword.speed").defineInRange("phantasmal_sword.speed", 10.0, -1000.0, 1000);
        phantasmal_sword_damage = builder.comment(" Default: 18\n Default Netherite: 4.0").translation("rigoranthusemortisreborn.config.server.phantasmal_sword.damage").defineInRange("phantasmal_sword.damage", 18, 0, 1000);
        phantasmal_axe_speed = builder.comment(" Default: 10\n Default Netherite: 9.0").translation("rigoranthusemortisreborn.config.server.phantasmal_axe.speed").defineInRange("phantasmal_axe.speed", 10.0, -1000.0, 1000);
        phantasmal_axe_damage = builder.comment(" Default: 18\n Default Netherite: 4.0").translation("rigoranthusemortisreborn.config.server.phantasmal_axe.damage").defineInRange("phantasmal_axe.damage", 18, 0, 1000);
        phantasmal_tier_durability = builder.comment(" Default: 2000\n Default Netherite: 2031").translation("rigoranthusemortisreborn.config.server.phantasmal_tier.durability").defineInRange("phantasmal_tier.durability", 2000, 0, 100000);
        phantasmal_tier_speed = builder.comment(" Default: 10\n Default Netherite: 9.0").translation("rigoranthusemortisreborn.config.server.phantasmal_tier.speed").defineInRange("phantasmal_tier.speed", 10.0, -1000.0, 1000);
        phantasmal_tier_damage = builder.comment(" Default: 18\n Default Netherite: 4.0").translation("rigoranthusemortisreborn.config.server.phantasmal_tier.damage").defineInRange("phantasmal_tier.damage", 18.0, 0.0, 1000);
        phantasmal_tier_enchantability = builder.comment(" Default: 60\n Default Netherite: 15").translation("rigoranthusemortisreborn.config.server.phantasmal_tier.enchantability").defineInRange("phantasmal_tier.enchantability", 60, 0, 100);

        phantasmal_boots_damage_reduction = builder.comment(" Boots Damage Reduction Value.\n Mod Default: 3\n Default Netherite: 3")
                .translation("rigoranthusemortisreborn.config.server.per_slot_phantasmal.phantasmal_boots_damage_reduction").defineInRange("per_slot_phantasmal.phantasmal_boots_damage_reduction", 3, 0, 1000);
        phantasmal_leggings_damage_reduction = builder.comment(" Leggings Damage Reduction Value.\n Mod Default: 6\n Default Netherite: 6")
                .translation("rigoranthusemortisreborn.config.server.per_slot_phantasmal.phantasmal_leggings_damage_reduction").defineInRange("per_slot_phantasmal.phantasmal_leggings_damage_reduction", 6, 0, 1000);
        phantasmal_chestplate_damage_reduction = builder.comment(" Chestplate Damage Reduction Value.\n Mod Default: 8\n Default Netherite: 8")
                .translation("rigoranthusemortisreborn.config.server.per_slot_phantasmal.phantasmal_chestplate_damage_reduction").defineInRange("per_slot_phantasmal.phantasmal_chestplate_damage_reduction", 8, 0, 1000);
        phantasmal_helmet_damage_reduction = builder.comment(" Helmet Damage Reduction Value.\n Mod Default: 3\n Default Netherite: 3")
                .translation("rigoranthusemortisreborn.config.server.per_slot_phantasmal.phantasmal_helmet_damage_reduction").defineInRange("per_slot_phantasmal.phantasmal_helmet_damage_reduction", 3, 0, 1000);
        phantasmal_durability_multiplier = builder.comment(" Mod Default: 37\n Default Netherite: 37")
                .translation("rigoranthusemortisreborn.config.server.general_values_phantasmal.durability_multiplier").defineInRange("general_values_phantasmal.durability_multiplier", 37, 0, 1000);
        phantasmal_armor_enchantability = builder.comment(" Mod Default: 60\n Default Netherite: 15")
                .translation("rigoranthusemortisreborn.config.server.general_values_phantasmal.enchantability").defineInRange("general_values_phantasmal.enchantability", 60, 0, 100);
        phantasmal_toughness = builder.comment(" Mod Default: 3\n Default Netherite: 3")
                .translation("rigoranthusemortisreborn.config.server.general_values_phantasmal.toughness").defineInRange("general_values_phantasmal.toughness", 3.0, 0.0, 10.0);
        phantasmal_knockback_resistance = builder.comment(" Mod Default: 0.1\n Default Netherite: 0.1")
                .translation("rigoranthusemortisreborn.config.server.general_values_phantasmal.knockback_resistance").defineInRange("general_values_phantasmal.knockback_resistance", 0.1, 0.0, 1.0);
    }
    private static void setupRemexConfig(ForgeConfigSpec.Builder builder) {
        remex_sword_speed = builder.comment(" Default: 120\n Default Netherite: 9.0").translation("rigoranthusemortisreborn.config.server.remex_sword.speed").defineInRange("remex_sword.speed", 120.0, -1000.0, 1000);
        remex_sword_damage = builder.comment(" Default: 25\n Default Netherite: 4.0").translation("rigoranthusemortisreborn.config.server.remex_sword.damage").defineInRange("remex_sword.damage", 25, 0, 1000);
        remex_axe_speed = builder.comment(" Default: 120\n Default Netherite: 9.0").translation("rigoranthusemortisreborn.config.server.remex_axe.speed").defineInRange("remex_axe.speed", 120.0, -1000.0, 1000);
        remex_axe_damage = builder.comment(" Default: 25\n Default Netherite: 4.0").translation("rigoranthusemortisreborn.config.server.remex_axe.damage").defineInRange("remex_axe.damage", 25, 0, 1000);
        remex_tier_durability = builder.comment(" Default: 3500\n Default Netherite: 2031").translation("rigoranthusemortisreborn.config.server.remex_tier.durability").defineInRange("remex_tier.durability", 3500, 0, 100000);
        remex_tier_speed = builder.comment(" Default: 120\n Default Netherite: 9.0").translation("rigoranthusemortisreborn.config.server.remex_tier.speed").defineInRange("remex_tier.speed", 120.0, -1000.0, 1000);
        remex_tier_damage = builder.comment(" Default: 25\n Default Netherite: 4.0").translation("rigoranthusemortisreborn.config.server.remex_tier.damage").defineInRange("remex_tier.damage", 25.0, 0.0, 1000);
        remex_tier_enchantability = builder.comment(" Default: 40\n Default Netherite: 15").translation("rigoranthusemortisreborn.config.server.remex_tier.enchantability").defineInRange("remex_tier.enchantability", 40, 0, 100);

        remex_boots_damage_reduction = builder.comment(" Boots Damage Reduction Value.\n Mod Default: 6\n Default Netherite: 3")
                .translation("rigoranthusemortisreborn.config.server.per_slot_remex.remex_boots_damage_reduction").defineInRange("per_slot_remex.remex_boots_damage_reduction", 6, 0, 1000);
        remex_leggings_damage_reduction = builder.comment(" Leggings Damage Reduction Value.\n Mod Default: 12\n Default Netherite: 6")
                .translation("rigoranthusemortisreborn.config.server.per_slot_remex.remex_leggings_damage_reduction").defineInRange("per_slot_remex.remex_leggings_damage_reduction", 12, 0, 1000);
        remex_chestplate_damage_reduction = builder.comment(" Chestplate Damage Reduction Value.\n Mod Default: 16\n Default Netherite: 8")
                .translation("rigoranthusemortisreborn.config.server.per_slot_remex.remex_chestplate_damage_reduction").defineInRange("per_slot_remex.remex_chestplate_damage_reduction", 16, 0, 1000);
        remex_helmet_damage_reduction = builder.comment(" Helmet Damage Reduction Value.\n Mod Default: 6\n Default Netherite: 3")
                .translation("rigoranthusemortisreborn.config.server.per_slot_remex.remex_helmet_damage_reduction").defineInRange("per_slot_remex.remex_helmet_damage_reduction", 6, 0, 1000);
        remex_durability_multiplier = builder.comment(" Mod Default: 40\n Default Netherite: 37")
                .translation("rigoranthusemortisreborn.config.server.general_values_remex.durability_multiplier").defineInRange("general_values_remex.durability_multiplier", 40, 0, 1000);
        remex_armor_enchantability = builder.comment(" Mod Default: 40\n Default Netherite: 15")
                .translation("rigoranthusemortisreborn.config.server.general_values_remex.enchantability").defineInRange("general_values_remex.enchantability", 40, 0, 100);
        remex_toughness = builder.comment(" Mod Default: 3\n Default Netherite: 3")
                .translation("rigoranthusemortisreborn.config.server.general_values_remex.toughness").defineInRange("general_values_remex.toughness", 0.0, 0.0, 10.0);
        remex_knockback_resistance = builder.comment(" Mod Default: 0.0\n Default Netherite: 0.1")
                .translation("rigoranthusemortisreborn.config.server.general_values_remex.knockback_resistance").defineInRange("general_values_remex.knockback_resistance", 0.0, 0.0, 1.0);
    }
    private static void setupDwellerArmorConfig(ForgeConfigSpec.Builder builder) {
        dweller_thorax_damage_reduction = builder.comment(" Chestplate Damage Reduction Value.\n Default: 7").translation("rigoranthusemortisreborn.config.server.per_slot_thorax.dweller_thorax_damage_reduction").defineInRange("per_slot_thorax.dweller_thorax_damage_reduction", 7, 0, 1000);
        dweller_thorax_durability_multiplier = builder.comment(" Default: 25").translation("rigoranthusemortisreborn.config.server.general_values_thorax.durability_multiplier").defineInRange("general_values_thorax.durability_multiplier", 25, 0, 1000);
        dweller_thorax_enchantability = builder.comment(" Default: 100").translation("rigoranthusemortisreborn.config.server.general_values_thorax.enchantability").defineInRange("general_values_thorax.enchantability", 100, 0, 100);
        dweller_thorax_toughness = builder.comment(" Default: 2").translation("rigoranthusemortisreborn.config.server.general_values_thorax.toughness").defineInRange("general_values_thorax.toughness", 2.0, 0.0, 10.0);
        dweller_thorax_knockback_resistance = builder.comment(" Default: 0.1").translation("rigoranthusemortisreborn.config.server.general_values_thorax.knockback_resistance").defineInRange("general_values_thorax.knockback_resistance", 0.1, 0.0, 10.0);
    }

//    public static void loadConfig(ForgeConfigSpec spec, Path path) {
//        RigoranthusEmortisReborn.LOGGER.debug("Loading config file {}", path);
//        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
//                .sync()
//                .autosave()
//                .writingMode(WritingMode.REPLACE)
//                .build();
//        RigoranthusEmortisReborn.LOGGER.debug("Built TOML config for {}", path.toString());
//        configData.load();
//        RigoranthusEmortisReborn.LOGGER.debug("Loaded TOML config file {}", path.toString());
//        spec.setConfig(configData);
//    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading configEvent) {}
    @SubscribeEvent public static void onReload(final ModConfigEvent.Reloading configEvent) { Config.refreshServer(); }

    public static void refreshServer() {
        RigoranthusEmortisReborn.LOGGER.debug("Refreshing Server Config");
        Config.DISABLE_HUNGER.get();                          Config.CANIS_PUPS_GET_PARENT_LEVELS.get();            Config.STARTING_ITEMS.get();                          Config.CANIS_GENDER.get();
        Config.enableUnfiredBricks.get();                     Config.enableNetheriteAdditions.get();                Config.enableApogeanArmorClimmbingEffect.get();       Config.enableArmorSetBonuses.get();
        Config.enableTreeGeneration.get();                    Config.enableBoneWeapons.get();                       Config.enableModdedOreFragments.get();                Config.enableHammersAndVanillaOreFragments.get();
        Config.soulCoalBurnTime.get();                        Config.enableSoulCoal.get();                          Config.bone_spear_speed.get();                        Config.apogean_axe_speed.get();
        Config.bone_spear_damage.get();                       Config.apogean_axe_damage.get();                      Config.bone_tier_durability.get();                    Config.apogean_sword_speed.get();
        Config.bone_tier_speed.get();                         Config.apogean_sword_damage.get();                    Config.bone_tier_damage.get();                        Config.apogean_tier_durability.get();
        Config.bone_tier_enchantability.get();                Config.apogean_tier_speed.get();                      Config.bone_bow_projectile_range.get();               Config.apogean_tier_damage.get();
        Config.bone_bow_durability.get();                     Config.apogean_tier_enchantability.get();             Config.aqueous_axe_speed.get();                       Config.atrophying_axe_speed.get();
        Config.aqueous_axe_damage.get();                      Config.atrophying_axe_damage.get();                   Config.aqueous_sword_speed.get();                     Config.atrophying_sword_speed.get();
        Config.aqueous_sword_damage.get();                    Config.atrophying_sword_damage.get();                 Config.aqueous_tier_durability.get();                 Config.atrophying_tier_durability.get();
        Config.aqueous_tier_speed.get();                      Config.atrophying_tier_speed.get();                   Config.aqueous_tier_damage.get();                     Config.atrophying_tier_damage.get();
        Config.aqueous_tier_enchantability.get();             Config.atrophying_tier_enchantability.get();          Config.incorporeal_axe_speed.get();                   Config.infernal_axe_speed.get();
        Config.incorporeal_axe_damage.get();                  Config.infernal_axe_damage.get();                     Config.incorporeal_sword_speed.get();                 Config.infernal_sword_damage.get();
        Config.incorporeal_sword_damage.get();                Config.infernal_sword_speed.get();                    Config.incorporeal_tier_durability.get();             Config.infernal_tier_durability.get();
        Config.incorporeal_tier_speed.get();                  Config.infernal_tier_speed.get();                     Config.incorporeal_tier_damage.get();                 Config.infernal_tier_damage.get();
        Config.incorporeal_tier_enchantability.get();         Config.infernal_tier_enchantability.get();            Config.opulent_axe_speed.get();                       Config.pernicious_axe_speed.get();
        Config.opulent_axe_damage.get();                      Config.pernicious_axe_damage.get();                   Config.opulent_sword_speed.get();                     Config.pernicious_sword_speed.get();
        Config.opulent_sword_damage.get();                    Config.pernicious_sword_damage.get();                 Config.opulent_tier_durability.get();                 Config.pernicious_tier_durability.get();
        Config.opulent_tier_speed.get();                      Config.pernicious_tier_speed.get();                   Config.opulent_tier_damage.get();                     Config.pernicious_tier_damage.get();
        Config.opulent_tier_enchantability.get();             Config.pernicious_tier_enchantability.get();          Config.phantasmal_sword_speed.get();                  Config.remex_sword_speed.get();
        Config.phantasmal_sword_damage.get();                 Config.remex_sword_damage.get();                      Config.phantasmal_axe_speed.get();                    Config.remex_axe_speed.get();
        Config.phantasmal_axe_damage.get();                   Config.remex_axe_damage.get();                        Config.phantasmal_tier_durability.get();              Config.remex_tier_durability.get();
        Config.phantasmal_tier_speed.get();                   Config.remex_tier_speed.get();                        Config.phantasmal_tier_damage.get();                  Config.remex_tier_damage.get();
        Config.phantasmal_tier_enchantability.get();          Config.remex_tier_enchantability.get();               Config.apogean_boots_damage_reduction.get();          Config.aqueous_boots_damage_reduction.get();
        Config.apogean_leggings_damage_reduction.get();       Config.aqueous_leggings_damage_reduction.get();       Config.apogean_chestplate_damage_reduction.get();     Config.aqueous_chestplate_damage_reduction.get();
        Config.apogean_helmet_damage_reduction.get();         Config.aqueous_helmet_damage_reduction.get();         Config.apogean_durability_multiplier.get();           Config.aqueous_durability_multiplier.get();
        Config.apogean_armor_enchantability.get();            Config.aqueous_armor_enchantability.get();            Config.apogean_toughness.get();                       Config.aqueous_toughness.get();
        Config.apogean_knockback_resistance.get();            Config.aqueous_knockback_resistance.get();            Config.atrophying_boots_damage_reduction.get();       Config.incorporeal_boots_damage_reduction.get();
        Config.atrophying_leggings_damage_reduction.get();    Config.incorporeal_leggings_damage_reduction.get();   Config.atrophying_chestplate_damage_reduction.get();  Config.incorporeal_chestplate_damage_reduction.get();
        Config.atrophying_helmet_damage_reduction.get();      Config.incorporeal_helmet_damage_reduction.get();     Config.atrophying_durability_multiplier.get();        Config.incorporeal_durability_multiplier.get();
        Config.atrophying_armor_enchantability.get();         Config.incorporeal_armor_enchantability.get();        Config.atrophying_toughness.get();                    Config.incorporeal_toughness.get();
        Config.atrophying_knockback_resistance.get();         Config.incorporeal_knockback_resistance.get();        Config.infernal_boots_damage_reduction.get();         Config.opulent_boots_damage_reduction.get();
        Config.infernal_leggings_damage_reduction.get();      Config.opulent_leggings_damage_reduction.get();       Config.infernal_chestplate_damage_reduction.get();    Config.opulent_chestplate_damage_reduction.get();
        Config.infernal_helmet_damage_reduction.get();        Config.opulent_helmet_damage_reduction.get();         Config.infernal_durability_multiplier.get();          Config.opulent_durability_multiplier.get();
        Config.infernal_armor_enchantability.get();           Config.opulent_armor_enchantability.get();            Config.infernal_toughness.get();                      Config.opulent_toughness.get();
        Config.infernal_knockback_resistance.get();           Config.opulent_knockback_resistance.get();            Config.pernicious_boots_damage_reduction.get();       Config.phantasmal_boots_damage_reduction.get();
        Config.pernicious_leggings_damage_reduction.get();    Config.phantasmal_leggings_damage_reduction.get();    Config.pernicious_chestplate_damage_reduction.get();  Config.phantasmal_chestplate_damage_reduction.get();
        Config.pernicious_helmet_damage_reduction.get();      Config.phantasmal_helmet_damage_reduction.get();      Config.pernicious_durability_multiplier.get();        Config.phantasmal_durability_multiplier.get();
        Config.pernicious_armor_enchantability.get();         Config.phantasmal_armor_enchantability.get();         Config.pernicious_toughness.get();                    Config.phantasmal_toughness.get();
        Config.pernicious_knockback_resistance.get();         Config.phantasmal_knockback_resistance.get();         Config.remex_boots_damage_reduction.get();            Config.remex_knockback_resistance.get();
        Config.remex_leggings_damage_reduction.get();         Config.remex_chestplate_damage_reduction.get();       Config.remex_helmet_damage_reduction.get();           Config.dweller_thorax_damage_reduction.get();
        Config.remex_durability_multiplier.get();             Config.dweller_thorax_durability_multiplier.get();    Config.remex_armor_enchantability.get();              Config.dweller_thorax_enchantability.get();
        Config.remex_toughness.get();                         Config.dweller_thorax_toughness.get();                Config.stone_hammer_durability.get();                 Config.iron_hammer_durability.get();
        Config.gold_hammer_durability.get();                  Config.diamond_hammer_durability.get();               Config.abyssalite_hammer_durability.get();            Config.maxVeinSize.get();
        Config.minOreHeight.get();                            Config.maxOreHeight.get();                            Config.verdurousWoodlandsSpawnWeight.get();           Config.verdurousFieldsSpawnWeight.get();
        Config.jessicSpawnWeight.get();                       Config.azulorealSpawnWeight.get();                    Config.loomingJessicSpawnWeight.get();                Config.loomingAzulorealSpawnWeight.get();
        Config.megaJessicSpawnWeight.get();                   Config.megaAzulorealSpawnWeight.get();                Config.allowCadaverNetherSpawns.get();                Config.allowNecrawNetherSpawns.get();
        Config.languidDwellerMaxSpawnHeight.get();            Config.languidDwellerMovementSpeed.get();             Config.feralCanisChordataMovementSpeed.get();
        Config.languidDwellerAttackDamage.get();              Config.feralCanisChordataAttackDamage.get();          Config.languidDwellerKnockbackResistance.get();       Config.feralCanisChordataKnockbackResistance.get();
        Config.languidDwellerAttackKnockback.get();           Config.feralCanisChordataAttackKnockback.get();       Config.languidDwellerArmorValue.get();                Config.feralCanisChordataArmorValue.get();
        Config.languidDwellerMaxHealth.get();                 Config.feralCanisChordataMaxHealth.get();             Config.languidDwellerSpawnWeight.get();               Config.feralCanisChordataSpawnWeight.get();
        Config.languidDwellerMinGroupSize.get();              Config.feralCanisChordataMinGroupSize.get();          Config.languidDwellerMaxGroupSize.get();              Config.feralCanisChordataMaxGroupSize.get();
        Config.sunderedCadaverMovementSpeed.get();            Config.necrawFasciiMovementSpeed.get();               Config.sunderedCadaverAttackDamage.get();             Config.necrawFasciiAttackDamage.get();
        Config.sunderedCadaverKnockbackResistance.get();      Config.necrawFasciiKnockbackResistance.get();         Config.sunderedCadaverAttackKnockback.get();          Config.necrawFasciiAttackKnockback.get();
        Config.sunderedCadaverArmorValue.get();               Config.necrawFasciiArmorValue.get();                  Config.sunderedCadaverMaxHealth.get();                Config.necrawFasciiMaxHealth.get();
        Config.sunderedCadaverSpawnWeight.get();              Config.necrawFasciiSpawnWeight.get();                 Config.sunderedCadaverMinGroupSize.get();             Config.necrawFasciiMinGroupSize.get();
        Config.sunderedCadaverMaxGroupSize.get();             Config.necrawFasciiMaxGroupSize.get();                Config.dweller_thorax_knockback_resistance.get();     Config.SPAWN_ORE.get();
        Config.SPAWN_BERRIES.get();                           Config.REGEN_INTERVAL.get();                          Config.INIT_MAX_DOMINION.get();                       Config.INIT_DOMINION_REGEN.get();
        Config.DOMINION_BOOST_BONUS.get();                    Config.DOMINION_REGEN_POTION.get();                   Config.DOMINION_REGEN_ENCHANT_BONUS.get();            Config.morrai_durability.get();
        Config.morrai_damage.get();                           Config.morrai_speed.get();                            Config.anduril_durability.get();                      Config.anduril_fire_duration.get();
        Config.anduril_damage.get();                          Config.anduril_speed.get();                           Config.razortooth_kunai_velocity.get();               Config.razortooth_kunai_inaccuracy.get();
        Config.razortooth_kunai_damage.get();                 Config.throwing_knife_velocity.get();                 Config.throwing_knife_damage.get();                   Config.throwing_knife_inaccuracy.get();
        Config.ghastly_scepter_speed.get();                   Config.ghastly_scepter_damage.get();                  Config.ghastly_scepter_durability.get();              Config.razortooth_frisbee_duration.get();
        Config.razortooth_frisbee_velocity.get();             Config.razortooth_frisbee_inaccuracy.get();           Config.razortooth_frisbee_damage.get();               Config.razortooth_frisbee_durability.get();
        Config.ricochet_round_duration.get();                 Config.ricochet_round_inaccuracy.get();               Config.ricochet_round_velocity.get();                 Config.ricochet_round_damage.get();
        Config.cry_of_desperation_durability.get();           Config.enable_hellfire_rain.get();                    Config.cry_of_desperation_speed.get();                Config.cry_of_desperation_damage.get();
        //Config.SUMMON_FAMILIAR_DOMINION_COST.get();  Config.TIER_MAX_BONUS.get();  Config.GLYPH_REGEN_BONUS.get();  Config.GLYPH_MAX_BONUS.get();
    }

//    @SubscribeEvent
//    public static void onWorldLoad(final WorldEvent.Load event) {
//        Config.loadConfig(Config.CONFIG_SERVER_SPEC, FMLPaths.CONFIGDIR.get().resolve("rigoranthusemortisreborn/RigoranthusEmortisReborn-Server.toml"));
//        Config.loadConfig(Config.CONFIG_CLIENT_SPEC, FMLPaths.CONFIGDIR.get().resolve("rigoranthusemortisreborn/RigoranthusEmortisReborn-Client.toml"));
//        Config.loadConfig(Config.CONFIG_SKILL_SPEC, FMLPaths.CONFIGDIR.get().resolve("rigoranthusemortisreborn-skills.toml"));
//    }

//    @SubscribeEvent
//    public static void player(final TickEvent.PlayerTickEvent event) {
//        if (!Config.GIVEN_COAL.get()) {
//            Player player = getPlayer(event.player.level);
//            if (player != null) {
//                Config.GIVEN_COAL.set(true);
//                player.level.addFreshEntity(new ItemEntity(player.level, player.position.x, player.position.y, player.position.z, new ItemStack(Registration.SOUL_COAL)));
//            }
//        }
//    }
////
//    @Nullable
//    public static Player getPlayer(LevelAccessor world) {
//        if (world == null) {return null;}
//        if (world.getPlayerByUUID(UUID.fromString("89f4f7f8-8ed5-479d-b04e-f7f843f14963")) != null) {
//            return world.getPlayerByUUID(UUID.fromString("89f4f7f8-8ed5-479d-b04e-f7f843f14963"));}
//        if (world.getPlayerByUUID(UUID.fromString("2b27a3a3-e2d6-468a-92e2-70f6f15b6e41")) != null) {
//            return world.getPlayerByUUID(UUID.fromString("2b27a3a3-e2d6-468a-92e2-70f6f15b6e41"));
//        }
//        return null;
//    }
}

//_____________  C L I E N T     C O N F I G  _____________//
        /*CLIENT_BUILDER.push("Masterful Smeltery Settings");
        setupSmelteryConfig(CLIENT_BUILDER);
        CLIENT_BUILDER.pop();*/