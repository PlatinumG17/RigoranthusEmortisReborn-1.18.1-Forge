package com.platinumg17.rigoranthusemortisreborn.items.armor;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.config.Config;
import com.platinumg17.rigoranthusemortisreborn.core.init.ItemInit;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.MagicItemsRegistry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public enum RigoranthusArmorMaterial implements ArmorMaterial {

    DWELLER("dweller", Config.dweller_thorax_durability_multiplier.get(), new int[]{0, 0, Config.dweller_thorax_damage_reduction.get(), 0}, Config.dweller_thorax_enchantability.get(), SoundEvents.CORAL_BLOCK_STEP, Config.dweller_thorax_toughness.get().floatValue(), Config.dweller_thorax_knockback_resistance.get().floatValue(), () -> {
        return Ingredient.of(MagicItemsRegistry.DWELLER_FLESH);}),

    APOGEAN_NETHERITE("apogean_netherite", Config.apogean_durability_multiplier.get(), new int[]{Config.apogean_boots_damage_reduction.get(), Config.apogean_leggings_damage_reduction.get(), Config.apogean_chestplate_damage_reduction.get(), Config.apogean_helmet_damage_reduction.get()}, Config.apogean_armor_enchantability.get(), SoundEvents.ARMOR_EQUIP_NETHERITE, Config.apogean_toughness.get().floatValue(), Config.apogean_knockback_resistance.get().floatValue(), () -> {
        return Ingredient.of(MagicItemsRegistry.ApogeanIngotItem);}),

    AQUEOUS_NETHERITE("aqueous_netherite", Config.aqueous_durability_multiplier.get(), new int[]{Config.aqueous_boots_damage_reduction.get(), Config.aqueous_leggings_damage_reduction.get(), Config.aqueous_chestplate_damage_reduction.get(), Config.aqueous_helmet_damage_reduction.get()}, Config.aqueous_armor_enchantability.get(), SoundEvents.ARMOR_EQUIP_NETHERITE, Config.aqueous_toughness.get().floatValue(), Config.aqueous_knockback_resistance.get().floatValue(), () -> {
        return Ingredient.of(MagicItemsRegistry.AqueousIngotItem);}),

    ATROPHYING_NETHERITE("atrophying_netherite", Config.atrophying_durability_multiplier.get(), new int[]{Config.atrophying_boots_damage_reduction.get(), Config.atrophying_leggings_damage_reduction.get(), Config.atrophying_chestplate_damage_reduction.get(), Config.atrophying_helmet_damage_reduction.get()}, Config.atrophying_armor_enchantability.get(), SoundEvents.ARMOR_EQUIP_NETHERITE, Config.atrophying_toughness.get().floatValue(), Config.atrophying_knockback_resistance.get().floatValue(), () -> {
        return Ingredient.of(MagicItemsRegistry.AtrophyingIngotItem);}),

    INCORPOREAL_NETHERITE("incorporeal_netherite", Config.incorporeal_durability_multiplier.get(), new int[]{Config.incorporeal_boots_damage_reduction.get(), Config.incorporeal_leggings_damage_reduction.get(), Config.incorporeal_chestplate_damage_reduction.get(), Config.incorporeal_helmet_damage_reduction.get()}, Config.incorporeal_armor_enchantability.get(), SoundEvents.ARMOR_EQUIP_NETHERITE, Config.incorporeal_toughness.get().floatValue(), Config.incorporeal_knockback_resistance.get().floatValue(), () -> {
        return Ingredient.of(MagicItemsRegistry.IncorporealIngotItem);}),

    INFERNAL_NETHERITE("infernal_netherite", Config.infernal_durability_multiplier.get(), new int[]{Config.infernal_boots_damage_reduction.get(), Config.infernal_leggings_damage_reduction.get(), Config.infernal_chestplate_damage_reduction.get(), Config.infernal_helmet_damage_reduction.get()}, Config.infernal_armor_enchantability.get(), SoundEvents.ARMOR_EQUIP_NETHERITE, Config.infernal_toughness.get().floatValue(), Config.infernal_knockback_resistance.get().floatValue(), () -> {
        return Ingredient.of(MagicItemsRegistry.InfernalIngotItem);}),

    OPULENT_NETHERITE("opulent_netherite", Config.opulent_durability_multiplier.get(), new int[]{Config.opulent_boots_damage_reduction.get(), Config.opulent_leggings_damage_reduction.get(), Config.opulent_chestplate_damage_reduction.get(), Config.opulent_helmet_damage_reduction.get()}, Config.opulent_armor_enchantability.get(), SoundEvents.ARMOR_EQUIP_NETHERITE, Config.opulent_toughness.get().floatValue(), Config.opulent_knockback_resistance.get().floatValue(), () -> {
        return Ingredient.of(MagicItemsRegistry.OpulentIngotItem);}),

    PERNICIOUS_NETHERITE("pernicious_netherite", Config.pernicious_durability_multiplier.get(), new int[]{Config.pernicious_boots_damage_reduction.get(), Config.pernicious_leggings_damage_reduction.get(), Config.pernicious_chestplate_damage_reduction.get(), Config.pernicious_helmet_damage_reduction.get()}, Config.pernicious_armor_enchantability.get(), SoundEvents.ARMOR_EQUIP_NETHERITE, Config.pernicious_toughness.get().floatValue(), Config.pernicious_knockback_resistance.get().floatValue(), () -> {
        return Ingredient.of(MagicItemsRegistry.PerniciousIngotItem);}),

    PHANTASMAL_NETHERITE("phantasmal_netherite", Config.phantasmal_durability_multiplier.get(), new int[]{Config.phantasmal_boots_damage_reduction.get(), Config.phantasmal_leggings_damage_reduction.get(), Config.phantasmal_chestplate_damage_reduction.get(), Config.phantasmal_helmet_damage_reduction.get()}, Config.phantasmal_armor_enchantability.get(), SoundEvents.ARMOR_EQUIP_NETHERITE, Config.phantasmal_toughness.get().floatValue(), Config.phantasmal_knockback_resistance.get().floatValue(), () -> {
        return Ingredient.of(MagicItemsRegistry.PhantasmalIngotItem);}),

    REMEX_NETHERITE("remex_netherite", Config.remex_durability_multiplier.get(), new int[]{Config.remex_boots_damage_reduction.get(), Config.remex_leggings_damage_reduction.get(), Config.remex_chestplate_damage_reduction.get(), Config.remex_helmet_damage_reduction.get()}, Config.remex_armor_enchantability.get(), SoundEvents.ARMOR_EQUIP_NETHERITE, Config.remex_toughness.get().floatValue(), Config.remex_knockback_resistance.get().floatValue(), () -> {
        return Ingredient.of(MagicItemsRegistry.RemexIngotItem);
    }),


    //______________  M A G I C   A R M O R  ______________//

    novice(EmortisConstants.MOD_ID + ":novice", 25,new int[]{1, 4, 5, 2},
            30, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0F, () -> {
        return Ingredient.of(Items.WHITE_WOOL);}),

    apprentice(EmortisConstants.MOD_ID + ":apprentice", 25, new int[]{2, 5, 6, 2},
            30, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0F, () -> {
        return Ingredient.of(Items.SCUTE);}),

    master(EmortisConstants.MOD_ID + ":master", 33, new int[]{3, 6, 8, 3},
            30, SoundEvents.ARMOR_EQUIP_LEATHER, 2.5f,  0F, () -> {
        return Ingredient.of(Items.SCUTE);});

    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
    private final String name;
    private final int durabilityMultiplier;
    private final int[] slotProtections;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    private RigoranthusArmorMaterial(String name, int durabilityMult, int[] protections, int enchantValue, SoundEvent equipSound, float toughness, float knockbackResist, Supplier<Ingredient> ingredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMult;
        this.slotProtections = protections;
        this.enchantmentValue = enchantValue;
        this.sound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResist;
        this.repairIngredient = new LazyLoadedValue<>(ingredient);
    }

    public int getDurabilityForSlot(EquipmentSlot slotType) {return HEALTH_PER_SLOT[slotType.getIndex()] * this.durabilityMultiplier;}
    public int getDefenseForSlot(EquipmentSlot slotType) {
        return this.slotProtections[slotType.getIndex()];
    }
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }
    public SoundEvent getEquipSound() {
        return this.sound;
    }
    @Override public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
    @OnlyIn(Dist.CLIENT) public String getName() {
        return EmortisConstants.MOD_ID + ":" + this.name;
    }
    public float getToughness() {
        return this.toughness;
    }
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
