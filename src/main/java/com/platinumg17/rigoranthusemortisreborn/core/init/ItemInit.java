package com.platinumg17.rigoranthusemortisreborn.core.init;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.blocks.DecorativeOrStorageBlocks;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.config.Config;
import com.platinumg17.rigoranthusemortisreborn.core.registry.RigoranthusSoundRegistry;
import com.platinumg17.rigoranthusemortisreborn.core.registry.effects.weapons.OnHitEffect;
import com.platinumg17.rigoranthusemortisreborn.core.registry.fluid.FluidRegistry;
import com.platinumg17.rigoranthusemortisreborn.entity.item.GhastlyScepterItem;
import com.platinumg17.rigoranthusemortisreborn.items.RigoranthusItemTier;
import com.platinumg17.rigoranthusemortisreborn.items.armor.armorsets.DwellerThoraxArmor;
import com.platinumg17.rigoranthusemortisreborn.items.itemeffects.ItemRightClickEffect;
import com.platinumg17.rigoranthusemortisreborn.items.pacts.PactOfServitudeItem;
import com.platinumg17.rigoranthusemortisreborn.items.specialized.RazorToothItem;
import com.platinumg17.rigoranthusemortisreborn.items.tooltypes.ToolRegistry;
import com.platinumg17.rigoranthusemortisreborn.items.weapons.REWeaponItem;
import com.platinumg17.rigoranthusemortisreborn.items.weapons.type.projectiles.BiliBombItem;
import com.platinumg17.rigoranthusemortisreborn.items.weapons.type.projectiles.basetype.BouncingProjectileWeaponItem;
import com.platinumg17.rigoranthusemortisreborn.items.weapons.type.projectiles.basetype.ConsumableProjectileWeaponItem;
import com.platinumg17.rigoranthusemortisreborn.items.weapons.type.projectiles.basetype.ReturningProjectileWeaponItem;
import com.platinumg17.rigoranthusemortisreborn.items.weapons.type.strung.BoneBow;
import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EmortisConstants.MOD_ID);
	public static final ItemSubRegistryHelper HELPER = RigoranthusEmortisReborn.REGISTRY_HELPER.getItemSubHelper();

	public static final Item.Properties MusicDiscProp = new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.RARE).tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP);
	public static final Item.Properties IngotProp = new Item.Properties().fireResistant().rarity(Rarity.EPIC).tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP);
	public static final Item.Properties PactProp = new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP).stacksTo(16);
	public static final Item.Properties DefaultProp = new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP);


					///_______________________  S I G N S  _______________________///

	public static final RegistryObject<Item> AZULOREAL_SIGN_ITEM = ITEMS.register("azuloreal_sign",
			() -> new SignItem(new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP).stacksTo(16),
					Registration.AZULOREAL_STANDING.get(), Registration.AZULOREAL_WALL.get()));

	public static final RegistryObject<Item> JESSIC_SIGN_ITEM = ITEMS.register("jessic_sign",
			() -> new SignItem(new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP).stacksTo(16),
					Registration.JESSIC_STANDING.get(), Registration.JESSIC_WALL.get()));


					///_______________________  P A C T S  _______________________///

	public static final RegistryObject<Item> PACT_OF_SERVITUDE = 		ITEMS.register("pact_of_servitude", () -> new PactOfServitudeItem(PactProp));
	public static final RegistryObject<Item> PACT_OF_MYRMIDON = 		ITEMS.register("pact_of_myrmidon", () -> new Item(PactProp));
	public static final RegistryObject<Item> ROGUE_PACT_OF_MYRMIDON = 	ITEMS.register("rogue_pact_of_myrmidon", () -> new Item(PactProp));
	public static final RegistryObject<Item> PACT_OF_PURTURBATION = 	ITEMS.register("pact_of_purturbation", () -> new Item(PactProp));



				///_______________________  I N G R E D I E N T S  _______________________///

	public static final RegistryObject<Item> BUCKET_OF_CADAVEROUS_ICHOR = ITEMS.register("bucket_of_cadaverous_ichor",
			() -> new BucketItem(() -> FluidRegistry.CADAVEROUS_ICHOR_FLUID.get(), DefaultProp.craftRemainder(Items.BUCKET).stacksTo(1)));

	public static final RegistryObject<Item> BONE_FRAGMENT = ITEMS.register("bone_fragment", () -> new Item(DefaultProp));

	public static final RegistryObject<Item> RAZOR_TOOTH = ITEMS.register("razor_tooth", () -> new RazorToothItem(DefaultProp));

	public static final RegistryObject<Item> IRON_SLIME_BALL = ITEMS.register("iron_slime_ball", () -> new Item(DefaultProp));

	public static final RegistryObject<Item> ICE_SHARD = ITEMS.register("ice_shard", () -> new Item(DefaultProp));

	public static final RegistryObject<Item> GHAST_IRON_INGOT = ITEMS.register("ghast_iron_ingot", () -> new Item(DefaultProp.rarity(Rarity.UNCOMMON)));

	public static final RegistryObject<Item> BLIGHT_ICHOR = ITEMS.register("blight_ichor", () -> new Item(DefaultProp.rarity(Rarity.RARE)));

	public static final RegistryObject<Item> EMORTIC_WEAPON_GRIP = ITEMS.register("emortic_weapon_grip", () -> new Item(DefaultProp));


			///_______________________  P R O J E C T I L E S  _______________________///

	public static final RegistryObject<Item> BONE_BOW = ITEMS.register("bone_bow",
			() -> new BoneBow(DefaultProp.stacksTo(1).durability(Config.bone_bow_durability.get())));

	public static final RegistryObject<Item> RAZORTOOTH_KUNAI = ITEMS.register("razortooth_kunai",
			() -> new ConsumableProjectileWeaponItem(DefaultProp, Config.razortooth_kunai_velocity.get().floatValue(), Config.razortooth_kunai_inaccuracy.get().floatValue(), Config.razortooth_kunai_damage.get()));

	public static final RegistryObject<Item> THROWING_KNIFE = ITEMS.register("throwing_knife",
			() -> new ConsumableProjectileWeaponItem(DefaultProp, Config.throwing_knife_velocity.get().floatValue(), Config.throwing_knife_inaccuracy.get().floatValue(), Config.throwing_knife_damage.get()));

	public static final RegistryObject<Item> RAZORTOOTH_FRISBEE = ITEMS.register("razortooth_frisbee",
			() -> new ReturningProjectileWeaponItem(MusicDiscProp.durability(Config.razortooth_frisbee_durability.get()), Config.razortooth_frisbee_velocity.get().floatValue(), Config.razortooth_frisbee_inaccuracy.get().floatValue(), Config.razortooth_frisbee_damage.get(), Config.razortooth_frisbee_duration.get()));

	public static final RegistryObject<Item> RICOCHET_ROUND = ITEMS.register("ricochet_round",
			() -> new BouncingProjectileWeaponItem(DefaultProp, Config.ricochet_round_velocity.get().floatValue(), Config.ricochet_round_inaccuracy.get().floatValue(),Config.ricochet_round_damage.get(), Config.ricochet_round_duration.get()));

	public static final RegistryObject<Item> BILI_BOMB = ITEMS.register("bili_bomb", () -> new BiliBombItem(PactProp.rarity(Rarity.UNCOMMON)));



			  ///_______________________  S W O R D S  _______________________///

//	public static final RegistryObject<Item> RISSAI = ITEMS.register("rissai",
//			() -> new REWeaponItem(new REWeaponItem.Builder(RigoranthusItemTier.PRISMARIC, 3, -2.4F).efficiency(1.0F).set(ToolRegistry.SWORD_TOOL).add(
//					OnHitEffect.SWEEP), new Item.Properties().defaultDurability(100).tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP)));

	public static final RegistryObject<Item> MORRAI = ITEMS.register("morrai",
			() -> new REWeaponItem(new REWeaponItem.Builder(RigoranthusItemTier.RESILE_TIER, Config.morrai_damage.get(), Config.morrai_speed.get().floatValue()).efficiency(3.0F).set(ToolRegistry.SWORD_TOOL).add(
					OnHitEffect.SWEEP).add(OnHitEffect.onCrit(OnHitEffect.DISARM_ENEMY)), new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP).defaultDurability(Config.morrai_durability.get())));

	public static final RegistryObject<Item> ANDURIL = ITEMS.register("anduril",
			() -> new REWeaponItem(new REWeaponItem.Builder(RigoranthusItemTier.GHAST_IRON, Config.anduril_damage.get(), Config.anduril_speed.get().floatValue()).efficiency(4.0F).set(ToolRegistry.SWORD_TOOL).add(
					OnHitEffect.SWEEP).add(OnHitEffect.onCrit(OnHitEffect.setOnFire(Config.anduril_fire_duration.get()))), MusicDiscProp.defaultDurability(Config.anduril_durability.get())));

//	public static final RegistryObject<Item> LUCK_OF_HEPHAESTUS = ITEMS.register("luck_of_hephaestus",
//			() -> new REWeaponItem(new REWeaponItem.Builder(RigoranthusItemTier.GHAST_IRON, 4, -2.4F).efficiency(15.0F).set(ToolRegistry.SWORD_TOOL).add(
//					OnHitEffect.SWEEP).add(OnHitEffect.onCrit(OnHitEffect.enemyPotionEffect(() -> new MobEffectInstance(MobEffects.WITHER, 100, 1)))), DefaultProp.rarity(Rarity.UNCOMMON)));



					///_______________________  S C Y T H E S  _______________________///

//	public static final RegistryObject<Item> SICKLE = ITEMS.register("sickle",
//			() -> new REWeaponItem(new REWeaponItem.Builder(ItemTier.IRON, 2, -2.2F).efficiency(1.5F).
//					disableShield().set(ToolRegistry.SICKLE_TOOL), new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP)));

//	public static final RegistryObject<Item> SICKLYTH = ITEMS.register("sicklyth",
//			() -> new REWeaponItem(new REWeaponItem.Builder(RigoranthusItemTier.BLIGHT_TIER, 6, -2.2F).efficiency(4.0F).disableShield().set(ToolRegistry.SICKLE_TOOL).add(
//					OnHitEffect.PSYCHO_BLIGHT), new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP).rarity(Rarity.UNCOMMON)));



				  ///_______________________  H A M M E R S  _______________________///

//	public static final RegistryObject<Item> CAROM_HAMMER = ITEMS.register("carom_hammer",
//			() -> new REWeaponItem(new REWeaponItem.Builder(RigoranthusItemTier.RESILE_TIER, 5, -3.2F).efficiency(2.0F).set(ToolRegistry.HAMMER_TOOL).set(
//			BunnyHopEffect.EFFECT_07).add(BunnyHopEffect.EFFECT_07), new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP)));
//
//	public static final RegistryObject<Item> VERBERATE = ITEMS.register("verberate",
//			() -> new REWeaponItem(new REWeaponItem.Builder(RigoranthusItemTier.RESILE_TIER, 7, -3.2F).efficiency(2.0F).set(ToolRegistry.HAMMER_TOOL).set(
//					BunnyHopEffect.EFFECT_04).add(BunnyHopEffect.EFFECT_04), new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP)));

//	public static final RegistryObject<Item> PITFALL = ITEMS.register("pitfall",
//			() -> new REWeaponItem(new REWeaponItem.Builder(RigoranthusItemTier.BLIGHT_TIER, 7, -3.2F).efficiency(7.0F).set(ToolRegistry.HAMMER_TOOL).add(
//					OnHitEffect.enemyPotionEffect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1))), new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP).rarity(Rarity.RARE)));
//
//	public static final RegistryObject<Item> SCARLET_LYHO = ITEMS.register("scarlet_lyho",
//			() -> new REWeaponItem(new REWeaponItem.Builder(RigoranthusItemTier.GHAST_IRON, 8, -3.2F).efficiency(12.0F).set(ToolRegistry.HAMMER_TOOL).add(
//					OnHitEffect.setOnFire(25)), new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP).rarity(Rarity.UNCOMMON)));



				///_______________________  C L A W S  _______________________///

//	public static final RegistryObject<Item> CLAWS_DRAWN = ITEMS.register("claws_drawn",
//			() -> new REWeaponItem(new REWeaponItem.Builder(ItemTier.DIAMOND, 5, -1.5F).efficiency(10.0F).set(ToolRegistry.CLAWS_TOOL).set(
//					ItemRightClickEffect.switchTo(() -> ItemInit.CLAWS_SHEATHED.get())), new Item.Properties().defaultDurability(200).tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP).rarity(Rarity.RARE)));
//
//	public static final RegistryObject<Item> CLAWS_SHEATHED = ITEMS.register("claws_sheathed",
//			() -> new REWeaponItem(new REWeaponItem.Builder(ItemTier.DIAMOND, -1, -1.0F).efficiency(10.0F).set(
//					ItemRightClickEffect.switchTo(() -> ItemInit.CLAWS_DRAWN.get())), new Item.Properties().defaultDurability(200).rarity(Rarity.RARE)));

//	public static final RegistryObject<Item> STEOCLASTS_DRAWN = ITEMS.register("steoclasts_drawn",
//			() -> new REWeaponItem(new REWeaponItem.Builder(RigoranthusItemTier.PROSAIC, 6, -1.5F).efficiency(10.0F).set(ToolRegistry.CLAWS_TOOL).set(
//					ItemRightClickEffect.switchTo(() -> ItemInit.STEOCLASTS_SHEATHED.get())).add(OnHitEffect.targetSpecificAdditionalDamage(4, () ->
//					EntityType.SKELETON)), new Item.Properties().defaultDurability(250).tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP)));
//
//	public static final RegistryObject<Item> STEOCLASTS_SHEATHED = ITEMS.register("steoclasts_sheathed",
//			() -> new REWeaponItem(new REWeaponItem.Builder(RigoranthusItemTier.PROSAIC, -1, -1.0F).efficiency(10.0F).set(
//					ItemRightClickEffect.switchTo(() -> ItemInit.STEOCLASTS_DRAWN.get())), new Item.Properties().defaultDurability(250)));


				     ///_______________________  M A G I C  _______________________///

	public static final RegistryObject<Item> PSYGLYPHIC_SCRIPT = ITEMS.register("psyglyphic_script", () -> new Item(DefaultProp));

	public static final RegistryObject<Item> CRY_OF_DESPERATION = ITEMS.register("cry_of_desperation",
			() -> new REWeaponItem(new REWeaponItem.Builder(RigoranthusItemTier.GHAST_IRON, Config.cry_of_desperation_damage.get(), Config.cry_of_desperation_speed.get().floatValue())
					.efficiency(4.0F).set(ToolRegistry.MISC_TOOL).set(ItemRightClickEffect.summonFireball()), MusicDiscProp.defaultDurability(Config.cry_of_desperation_durability.get())));

//	public static final RegistryObject<Item> GREENHORN_WAND = ITEMS.register("greenhorn_wand",
//			() -> new REWeaponItem(new REWeaponItem.Builder(RigoranthusItemTier.SPLINTERED, 1, -1.0F).efficiency(1.0F).set(ToolRegistry.MISC_TOOL).set(
//					MagicAttackRightClickEffect.NOVICE_MAGIC), new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP)));

//	public static final RegistryObject<Item> TYRO_WAND = ITEMS.register("tyro_wand",
//			() -> new REWeaponItem(new REWeaponItem.Builder(ItemTier.IRON, -1, -1.0F).efficiency(1.0F).set(ToolRegistry.MISC_TOOL).set(
//					MagicAttackRightClickEffect.TYRO_MAGIC), new Item.Properties().defaultDurability(512).tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP)));

//	public static final RegistryObject<Item> PROHIBATE_WAND = ITEMS.register("prohibate_wand",
//			() -> new REWeaponItem(new REWeaponItem.Builder(RigoranthusItemTier.PRISMARIC, 0, -1.0F).efficiency(1.0F).set(ToolRegistry.MISC_TOOL).set(
//					MagicAttackRightClickEffect.PROHIBATE_MAGIC), new Item.Properties().defaultDurability(324).tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP)));
//
//	public static final RegistryObject<Item> SPECTIVE_WAND = ITEMS.register("spective_wand",
//			() -> new REWeaponItem(new REWeaponItem.Builder(RigoranthusItemTier.LUSTERIC, -1, -1.0F).efficiency(1.0F).set(ToolRegistry.MISC_TOOL).set(
//					MagicAttackRightClickEffect.SPECTIVE_MAGIC), new Item.Properties().defaultDurability(450).tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP)));
//
//	public static final RegistryObject<Item> CIRCEAN_SCEPTER = ITEMS.register("circean_scepter",
//			() -> new REWeaponItem(new REWeaponItem.Builder(RigoranthusItemTier.ESOTERIC_TIER, 0, -1.0F).efficiency(1.0F).set(ToolRegistry.MISC_TOOL).set(
//					MagicAttackRightClickEffect.CIRCEAN_MAGIC), new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP).rarity(Rarity.EPIC)));
//
//	public static final RegistryObject<Item> BLACKTHORN_STAFF = ITEMS.register("blackthorn_staff",
//			() -> new REWeaponItem(new REWeaponItem.Builder(RigoranthusItemTier.CIRCEAN, -1, -1.0F).efficiency(1.0F).set(ToolRegistry.MISC_TOOL).set(
//					MagicAttackRightClickEffect.APOGEAN_MAGIC), new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP).rarity(Rarity.EPIC)));
//
//	public static final RegistryObject<Item> PAGAN_ASH_WAND = ITEMS.register("pagan_ash_wand",
//			() -> new REWeaponItem(new REWeaponItem.Builder(RigoranthusItemTier.BLIGHT_TIER, 0, -1.0F).efficiency(1.0F).set(ToolRegistry.MISC_TOOL).add(
//					OnHitEffect.PSYCHO_BLIGHT).set(MagicAttackRightClickEffect.PSYCHO_BLIGHT_MAGIC), new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP).rarity(Rarity.UNCOMMON)));

	public static final RegistryObject<Item> GHASTLY_SCEPTER = ITEMS.register("ghastly_scepter",
			() -> new GhastlyScepterItem(new REWeaponItem.Builder(RigoranthusItemTier.GHAST_IRON, Config.ghastly_scepter_damage.get(), Config.ghastly_scepter_speed.get().floatValue()).efficiency(3.0F).set(ToolRegistry.MISC_TOOL).set(
					ItemRightClickEffect.shootFireball()), MusicDiscProp.durability(Config.ghastly_scepter_durability.get())));


 /*
 .add(OnHitEffect.LIFE_SATURATION)

	public static final RegistryObject<Item> DESOLATOR_MACE = ITEMS.register("desolator_mace",
			() -> new REWeaponItem(new REWeaponItem.Builder(ItemTier.IRON, 1, -2.8F).efficiency(2.0F).set(ToolRegistry.MISC_TOOL).add(
				OnHitEffect.armorBypassingDamageMod(4)), new Item.Properties().defaultDurability(2048).tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP).rarity(Rarity.UNCOMMON)));

	public static final RegistryObject<Item> FAN = ITEMS.register("fan",
			() -> new REWeaponItem(new REWeaponItem.Builder(ItemTier.IRON, 1, -1.0F).efficiency(1.5F).set(ToolRegistry.MISC_TOOL).set(
				ItemRightClickEffect.extinguishFire(1)).add(OnHitEffect.enemyKnockback(1.5F)), new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP)));

	public static final RegistryObject<Item> STEOCLASTS_SHEATHED = ITEMS.register("steoclasts_sheathed",
			() -> new REWeaponItem(new REWeaponItem.Builder(RigoranthusItemTier.ESOTERIC_TIER, 2, -1.0F).efficiency(1.5F).set(ToolRegistry.MISC_TOOL).set(
				ItemRightClickEffect.extinguishFire(3)).add(OnHitEffect.BREATH_LEVITATION_AOE).add(OnHitEffect.enemyKnockback(2.0F)), new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP).rarity(Rarity.RARE)));

	public static final RegistryObject<Item> ELECTRO_AXE_CHARGED = ITEMS.register("electro_axe_charged",
			() -> new REWeaponItem(new REWeaponItem.Builder(ItemTier.IRON, 8, -3.0F).efficiency(6.0F).disableShield().set(ToolRegistry.AXE_TOOL).set(ItemRightClickEffect.switchTo(() -> ELECTRO_AXE_SPENT)).add(
				OnHitEffect.DROP_FOE_ITEM).add(InventoryTickEffect.DROP_WHEN_IN_WATER).add(OnHitEffect.playSound(() -> RigoranthusSoundRegistry.EVENT_ELECTRO_SHOCK.get(), 0.6F, 1.0F)), new Item.Properties().defaultDurability(800).tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP)));

	public static final RegistryObject<Item> ELECTRO_AXE_SPENT = ITEMS.register("electro_axe_spent",
			() -> new REWeaponItem(new REWeaponItem.Builder(ItemTier.IRON, 8, -3.0F).efficiency(6.0F).disableShield().set(ToolRegistry.AXE_TOOL).set(
				ItemRightClickEffect.switchTo(() -> ItemInit.ELECTRO_AXE_CHARGED.get())), new Item.Properties().defaultDurability(800)));

	.add(OnHitEffect.targetSpecificAdditionalDamage(6, () -> ModEntityTypes.ENTITY))
	.add(OnHitEffect.setOnFire(5))
	.set(ItemRightClickEffect.switchTo(() -> Items.ITEM))
	.add(OnHitEffect.ICE_SHARD)
 	.add(OnHitEffect.RANDOM_DAMAGE)
	.add(InventoryTickEffect.BREATH_SLOW_FALLING)
 	.setEating(FinishUseItemEffect.foodEffect(2, 0.3F)
  */


	///_______________________  D W E L L E R  S T U F F  _______________________///

	public static final RegistryObject<Item> DWELLER_THORAX = ITEMS.register("dweller_thorax", () -> new DwellerThoraxArmor(MusicDiscProp));

	public static final RegistryObject<Item> FORGOTTEN_RECORD = ITEMS.register("forgotten_record",
			() -> new RecordItem(1, RigoranthusSoundRegistry.FORGOTTEN_RECORD, MusicDiscProp));

	public static final RegistryObject<Item> MUSIC_DISK_KICKSTART = ITEMS.register("music_disk_kickstart",
			() -> new RecordItem(2, RigoranthusSoundRegistry.LEVEL_UP, MusicDiscProp));

	public static final RegistryObject<Item> MUSIC_DISK_NEON_LIGHTS = ITEMS.register("music_disk_neon_lights",
			() -> new RecordItem(3, RigoranthusSoundRegistry.NEON_LIGHTS, MusicDiscProp));



					///_______________________  M I S C E L L A N E O U S  _______________________///

	public static final RegistryObject<Item> JESSIC_BOAT = HELPER.createBoatItem("jessic", DecorativeOrStorageBlocks.JESSIC_PLANKS);
	public static final RegistryObject<Item> AZULOREAL_BOAT = HELPER.createBoatItem("azuloreal", DecorativeOrStorageBlocks.AZULOREAL_PLANKS);

//	public static RegistryObject<Item> createBoatItem(String wood, Block block) {
//		String type = "rigoranthusemortisreborn:" + wood;
//		RegistryObject<Item> boat = ITEMS.register(wood + "_boat", ()  -> new BlueprintBoatItem(type, createSimpleItemProperty(1, CreativeModeTab.TAB_TRANSPORTATION)));
//		REBoatRegistry.registerBoat(type, boat, block);
//		return boat;
//	}

	public static void register(IEventBus modEventBus) {
		ITEMS.register(modEventBus);
	}
}