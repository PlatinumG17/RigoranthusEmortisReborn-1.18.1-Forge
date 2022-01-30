package com.platinumg17.rigoranthusemortisreborn.magica.setup;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.items.ingots.*;
import com.platinumg17.rigoranthusemortisreborn.magica.common.entity.ModEntities;
import com.platinumg17.rigoranthusemortisreborn.magica.common.items.*;
import com.platinumg17.rigoranthusemortisreborn.magica.common.items.curios.AbstractDominionCurio;
import com.platinumg17.rigoranthusemortisreborn.magica.common.items.curios.ConservationRing;
import com.platinumg17.rigoranthusemortisreborn.magica.common.items.curios.SummonersStrength;
import com.platinumg17.rigoranthusemortisreborn.magica.common.lib.LibItemNames;
import com.platinumg17.rigoranthusemortisreborn.magica.common.potions.ModPotions;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.HashSet;
import java.util.Set;

@ObjectHolder(EmortisConstants.MOD_ID)
public class MagicItemsRegistry {

    @ObjectHolder(LibItemNames.APOGEAN_NETHERITE_INGOT) public static ApogeanIngotItem ApogeanIngotItem;
    @ObjectHolder(LibItemNames.AQUEOUS_NETHERITE_INGOT) public static AqueousIngotItem AqueousIngotItem;
    @ObjectHolder(LibItemNames.ATROPHYING_NETHERITE_INGOT) public static AtrophyingIngotItem AtrophyingIngotItem;
    @ObjectHolder(LibItemNames.INCORPOREAL_NETHERITE_INGOT) public static IncorporealIngotItem IncorporealIngotItem;
    @ObjectHolder(LibItemNames.INFERNAL_NETHERITE_INGOT) public static InfernalIngotItem InfernalIngotItem;
    @ObjectHolder(LibItemNames.OPULENT_NETHERITE_INGOT) public static OpulentIngotItem OpulentIngotItem;
    @ObjectHolder(LibItemNames.PERNICIOUS_NETHERITE_INGOT) public static PerniciousIngotItem PerniciousIngotItem;
    @ObjectHolder(LibItemNames.PHANTASMAL_NETHERITE_INGOT) public static PhantasmalIngotItem PhantasmalIngotItem;
    @ObjectHolder(LibItemNames.REMEX_NETHERITE_INGOT) public static RemexIngotItem RemexIngotItem;

    @ObjectHolder(LibItemNames.FIRE_SHOT) public static ModItem FIRE_SHOT;
    @ObjectHolder(LibItemNames.BOTTLE_OF_ICHOR) public static BottleItem BOTTLE_OF_ICHOR;
    @ObjectHolder(LibItemNames.DWELLER_FLESH) public static ModItem DWELLER_FLESH;
    @ObjectHolder(LibItemNames.CREATIVE_SPELL_BOOK) public static SpellBook creativeSpellBook;
    @ObjectHolder(LibItemNames.BUCKET_OF_DOMINION) public static ModItem bucketOfDominion;
    @ObjectHolder(LibItemNames.UNADORNED_RING) public  static ModItem unadornedRing;
    @ObjectHolder(LibItemNames.RING_OF_LESSER_CONSERVATION) public static ConservationRing ringOfLesserConservation;
    @ObjectHolder(LibItemNames.RING_OF_GREATER_CONSERVATION) public static ConservationRing ringOfGreaterConservation;
    @ObjectHolder(LibItemNames.AMULET_OF_DOMINION_BOOST) public static AbstractDominionCurio amuletOfDominionBoost;
    @ObjectHolder(LibItemNames.AMULET_OF_DOMINION_REGEN) public static AbstractDominionCurio amuletOfDominionRegen;
    @ObjectHolder(LibItemNames.UNADORNED_AMULET) public static ModItem unadornedAmulet;
    @ObjectHolder(LibItemNames.DOMINION_WAND) public static DominionWand dominionWand;
    @ObjectHolder(LibItemNames.DOMINION_GEM) public static ModItem dominionGem;
    @ObjectHolder(LibItemNames.ADONIS) public static Adonis ADONIS;
    @ObjectHolder(LibItemNames.BONE_ARROW) public static BoneArrow BONE_ARROW;

    @ObjectHolder(LibItemNames.EXP_GEM) public static ExperienceGem EXPERIENCE_GEM;
    @ObjectHolder(LibItemNames.GREATER_EXP_GEM) public static ExperienceGem GREATER_EXPERIENCE_GEM;
    @ObjectHolder(LibItemNames.LUSTERIC_SHIELD) public static LustericShield LUSTERIC_SHIELD;
    @ObjectHolder(LibItemNames.SUMMONERS_STRENGTH) public static SummonersStrength SUMMONERS_STRENGTH;

    public static FoodProperties DOMINION_BERRY_FOOD = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.1F).effect(() -> new MobEffectInstance(ModPotions.DOMINION_REGEN_EFFECT, 100), 1.0f).alwaysEat().build();
    public static FoodProperties BILIS_BERRY_FOOD = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.5F).build();
    public static FoodProperties ICHOR_FOOD = (new FoodProperties.Builder()).nutrition(5).saturationMod(0.5F).meat().build();

    @Mod.EventBusSubscriber(modid = EmortisConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistrationHandler {
        public static final Set<Item> ITEMS = new HashSet<>();

        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event) {
            Item[] items = {

                    new ApogeanIngotItem(),
                    new AqueousIngotItem(),
                    new AtrophyingIngotItem(),
                    new IncorporealIngotItem(),
                    new InfernalIngotItem(),
                    new OpulentIngotItem(),
                    new PerniciousIngotItem(),
                    new PhantasmalIngotItem(),
                    new RemexIngotItem(),

                    new BottleItem(defaultItemProperties().stacksTo(16).food(ICHOR_FOOD)).setRegistryName(LibItemNames.BOTTLE_OF_ICHOR),
                    new ModItem(defaultItemProperties().stacksTo(1), LibItemNames.BUCKET_OF_DOMINION),
                    new DominionWand(),
                    new ModItem(LibItemNames.DWELLER_FLESH),
                    new ModItem(LibItemNames.UNADORNED_AMULET).withTooltip(new TranslatableComponent("rigoranthusemortisreborn.tooltip.unadorned")),
                    new ModItem(LibItemNames.UNADORNED_RING).withTooltip(new TranslatableComponent("rigoranthusemortisreborn.tooltip.unadorned")),
                    new ConservationRing(LibItemNames.RING_OF_LESSER_CONSERVATION) { @Override public int getDominionDiscount() { return 10; }},
                    new ConservationRing(LibItemNames.RING_OF_GREATER_CONSERVATION) { @Override public int getDominionDiscount() { return 20; }},
                    new AbstractDominionCurio(LibItemNames.AMULET_OF_DOMINION_BOOST){ @Override public int getMaxDominionBoost(ItemStack stack) { return 50; }},
                    new AbstractDominionCurio(LibItemNames.AMULET_OF_DOMINION_REGEN){ @Override public int getDominionRegenBonus(ItemStack stack) { return 3; }},
                    new SummonersStrength(defaultItemProperties().stacksTo(1)),
                    new ModItem(LibItemNames.DOMINION_GEM).withTooltip(new TranslatableComponent("tooltip.dominion_gem")),
                    new LustericShield(),
                    new Adonis(),
                    new BoneArrow(),
                    new ModItem(LibItemNames.FIRE_SHOT),
                    new SpawnEggItem(ModEntities.FERAL_CANIS, 0x999999, 0xffffff, defaultItemProperties()).setRegistryName(LibItemNames.CANIS_CHORDATA_SPAWN_EGG),
                    new SpawnEggItem(ModEntities.SUNDERED_CADAVER, -6684673, -39322, defaultItemProperties()).setRegistryName(LibItemNames.SUNDERED_CADAVER_SPAWN_EGG),
                    new SpawnEggItem(ModEntities.NECRAW_FASCII, 0x27640c, 0xffd966, defaultItemProperties()).setRegistryName(LibItemNames.NECRAW_FASCII_SPAWN_EGG),
                    new SpawnEggItem(ModEntities.LANGUID_DWELLER, 0x968d81, 0x491919, defaultItemProperties()).setRegistryName(LibItemNames.LANGUID_DWELLER_SPAWN_EGG),
                    new ExperienceGem(defaultItemProperties(), LibItemNames.EXP_GEM) { @Override public int getValue() { return 3; }},
                    new ExperienceGem(defaultItemProperties(), LibItemNames.GREATER_EXP_GEM) { @Override public int getValue() { return 12; }}
            };

            final IForgeRegistry<Item> registry = event.getRegistry();

            for (final Item item : items) {
                registry.register(item);
                ITEMS.add(item);
            }
        }
    }
    public static Item.Properties defaultItemProperties() {
        return new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP);
    }
}


















//            for(Glyph glyph : RigoranthusEmortisRebornAPI.getInstance().getGlyphMap().values()){
//                registry.register(glyph);
//                ITEMS.add(glyph);
//            }
//            for(RitualOffering ritualParchment : RigoranthusEmortisRebornAPI.getInstance().getRitualItemMap().values()){
//                registry.register(ritualParchment);
//                ITEMS.add(ritualParchment);
//            }
//            for(FamiliarScript script : RigoranthusEmortisRebornAPI.getInstance().getFamiliarScriptMap().values()){
//                registry.register(script);
//                ITEMS.add(script);
//            }

//    @ObjectHolder(LibItemNames.POWDERED_ESOTERICUM) public static Esotericum POWDERED_ESOTERICUM;
//    @ObjectHolder(LibItemNames.BLASTING_AUGMENT) public static ItemAugmentBlasting BLASTING_AUGMENT;
//    @ObjectHolder(LibItemNames.SMOKING_AUGMENT) public static ItemAugmentSmoking SMOKING_AUGMENT;
//    @ObjectHolder(LibItemNames.SPEED_AUGMENT) public static ItemAugmentSpeed SPEED_AUGMENT;
//    @ObjectHolder(LibItemNames.FUEL_AUGMENT) public static ItemAugmentFuel FUEL_AUGMENT;
//    @ObjectHolder(LibItemNames.ITEM_COPY) public static ItemSmelteryCopy ITEM_COPY;
//    @ObjectHolder(LibItemNames.BLANK_GLYPH) public static  Item blankGlyph;
//    @ObjectHolder(LibItemNames.PROSAIC_BELT) public static ModItem prosaicBelt;

//    @ObjectHolder(LibItemNames.WAND) public static Wand WAND;
//    @ObjectHolder(LibItemNames.AMPLIFY_ARROW) public static SpellArrow AMPLIFY_ARROW;
//    @ObjectHolder(LibItemNames.SPLIT_ARROW) public static SpellArrow SPLIT_ARROW;
//    @ObjectHolder(LibItemNames.PIERCE_ARROW) public static SpellArrow PIERCE_ARROW;
//    @ObjectHolder(LibItemNames.SPELL_PARCHMENT) public static SpellParchment spellParchment;

//                    new ModItem(LibItemNames.BLANK_GLYPH),
//                    new ModItem(LibItemNames.PROSAIC_BELT).withTooltip(new TranslatableComponent("rigoranthusemortisreborn.tooltip.unadorned")),
//                    new NoviceArmor(EquipmentSlot.FEET).setRegistryName("novice_boots"),
//                    new NoviceArmor(EquipmentSlot.LEGS).setRegistryName("novice_leggings"),
//                    new NoviceArmor(EquipmentSlot.CHEST).setRegistryName("novice_robes"),
//                    new NoviceArmor(EquipmentSlot.HEAD).setRegistryName("novice_hood"),
//                    new ApprenticeArmor(EquipmentSlot.FEET).setRegistryName("apprentice_boots"),
//                    new ApprenticeArmor(EquipmentSlot.LEGS).setRegistryName("apprentice_leggings"),
//                    new ApprenticeArmor(EquipmentSlot.CHEST).setRegistryName("apprentice_robes"),
//                    new ApprenticeArmor(EquipmentSlot.HEAD).setRegistryName("apprentice_hood"),
//                    new MasterArmor(EquipmentSlot.FEET).setRegistryName("emortic_boots"),
//                    new MasterArmor(EquipmentSlot.LEGS).setRegistryName("emortic_leggings"),
//                    new MasterArmor(EquipmentSlot.CHEST).setRegistryName("emortic_robes"),
//                    new MasterArmor(EquipmentSlot.HEAD).setRegistryName("emortic_hood"),
//                    new SpellBook(ISpellTier.Tier.ONE).setRegistryName(LibItemNames.NOVICE_SPELL_BOOK), // TODO --> Nix the whole Tier thing [probably]
//                    new SpellBook(ISpellTier.Tier.TWO).setRegistryName(LibItemNames.APPRENTICE_SPELL_BOOK),
//                    new SpellParchment(),
//                    new Wand(),
//                    new FormSpellArrow(LibItemNames.PIERCE_ARROW, AugmentPierce.INSTANCE, 2),
//                    new FormSpellArrow(LibItemNames.SPLIT_ARROW, AugmentSplit.INSTANCE, 2),
//                    new SpellArrow(LibItemNames.AMPLIFY_ARROW, AugmentAmplify.INSTANCE, 2),
//                    new ItemAugmentBlasting(new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP).stacksTo(16)).setRegistryName(LibItemNames.BLASTING_AUGMENT),
//                    new ItemAugmentSmoking(new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP).stacksTo(16)).setRegistryName(LibItemNames.SMOKING_AUGMENT),
//                    new ItemAugmentSpeed(new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP).stacksTo(16)).setRegistryName(LibItemNames.SPEED_AUGMENT),
//                    new ItemAugmentFuel(new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP).stacksTo(16)).setRegistryName(LibItemNames.FUEL_AUGMENT),
//                    new ItemSmelteryCopy(new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP).stacksTo(16)).setRegistryName(LibItemNames.ITEM_COPY),