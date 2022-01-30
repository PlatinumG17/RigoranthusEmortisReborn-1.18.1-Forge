package com.platinumg17.rigoranthusemortisreborn.items;

import com.platinumg17.rigoranthusemortisreborn.config.Config;
import com.platinumg17.rigoranthusemortisreborn.core.init.ItemInit;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.MagicItemsRegistry;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum RigoranthusItemTier implements Tier {
    APOGEAN(4, Config.apogean_tier_durability.get(), Config.apogean_tier_speed.get().floatValue(), Config.apogean_tier_damage.get().floatValue(), Config.apogean_tier_enchantability.get(),
            () -> Ingredient.of(MagicItemsRegistry.ApogeanIngotItem)),
    AQUEOUS(4, Config.aqueous_tier_durability.get(), Config.aqueous_tier_speed.get().floatValue(), Config.aqueous_tier_damage.get().floatValue(), Config.aqueous_tier_enchantability.get(),
            () -> Ingredient.of(MagicItemsRegistry.AqueousIngotItem)),
    ATROPHYING(4, Config.atrophying_tier_durability.get(), Config.atrophying_tier_speed.get().floatValue(), Config.atrophying_tier_damage.get().floatValue(), Config.atrophying_tier_enchantability.get(),
            () -> Ingredient.of(MagicItemsRegistry.AtrophyingIngotItem)),
    INCORPOREAL(4, Config.incorporeal_tier_durability.get(), Config.incorporeal_tier_speed.get().floatValue(), Config.incorporeal_tier_damage.get().floatValue(), Config.incorporeal_tier_enchantability.get(),
            () -> Ingredient.of(MagicItemsRegistry.IncorporealIngotItem)),
    INFERNAL(4, Config.infernal_tier_durability.get(), Config.infernal_tier_speed.get().floatValue(), Config.infernal_tier_damage.get().floatValue(), Config.infernal_tier_enchantability.get(),
            () -> Ingredient.of(MagicItemsRegistry.InfernalIngotItem)),
    OPULENT(4, Config.opulent_tier_durability.get(), Config.opulent_tier_speed.get().floatValue(), Config.opulent_tier_damage.get().floatValue(), Config.opulent_tier_enchantability.get(),
            () -> Ingredient.of(MagicItemsRegistry.OpulentIngotItem)),
    PERNICIOUS(4, Config.pernicious_tier_durability.get(), Config.pernicious_tier_speed.get().floatValue(), Config.pernicious_tier_damage.get().floatValue(), Config.pernicious_tier_enchantability.get(),
            () -> Ingredient.of(MagicItemsRegistry.PerniciousIngotItem)),
    PHANTASMAL(4, Config.phantasmal_tier_durability.get(), Config.phantasmal_tier_speed.get().floatValue(), Config.phantasmal_tier_damage.get().floatValue(), Config.phantasmal_tier_enchantability.get(),
            () -> Ingredient.of(MagicItemsRegistry.PhantasmalIngotItem)),
    REMEX(4, Config.remex_tier_durability.get(), Config.remex_tier_speed.get().floatValue(), Config.remex_tier_damage.get().floatValue(), Config.remex_tier_enchantability.get(),
            () -> Ingredient.of(MagicItemsRegistry.RemexIngotItem)),
    BONE(2, Config.bone_tier_durability.get(), Config.bone_tier_speed.get().floatValue(), Config.bone_tier_damage.get().floatValue(), Config.bone_tier_enchantability.get(),
            () -> Ingredient.of(ItemInit.BONE_FRAGMENT.get())),

    SPLINTERED(0, 59, 0.0F, -1.0F, 0,
            () -> Ingredient.EMPTY),

    FROSTBITTEN(0, 60, 11.0F, 1.0F, 25,
            () -> Ingredient.of(ItemInit.ICE_SHARD.get())),

    PRISMARIC(2, 300, 4.0F, 3.0F, 10,
            () -> Ingredient.of(Items.PRISMARINE_SHARD)),

    PROSAIC(0, 450, 2.5F, 0.0F, 10,
            () -> Ingredient.EMPTY),

    RESILE_TIER(1, 450, 2.0F, 2.0F, 8,
            () -> Ingredient.of(ItemInit.IRON_SLIME_BALL.get())),

    GHAST_IRON(3, 1024, 6.0F, 3.0F, 16, //
            () -> Ingredient.of(ItemInit.GHAST_IRON_INGOT.get())),

    BLIGHT_TIER(3, 1440, 4.0F, 4.0F, 15, //
            () -> Ingredient.of(ItemInit.BLIGHT_ICHOR.get())),

    LUSTERIC(4, 1640, 13.0F, 3.0F, 10, // 1640
            () -> Ingredient.EMPTY),

    ESOTERIC_TIER(4, 1820, 14.0F, 4.0F, 25,
            () -> Ingredient.EMPTY),

    CIRCEAN(5, 2024, 12.0F, 5.0F, 30,
            () -> Ingredient.EMPTY);

    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    RigoranthusItemTier(int level, int uses, float speed, float damage, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
    }
    @Override public int getUses() {
        return 0;
    }
    @Override public float getSpeed() {
        return 0;
    }
    @Override public float getAttackDamageBonus() {
        return 0;
    }
    @Override public int getLevel() {
        return 0;
    }
    @Override public int getEnchantmentValue() {
        return 0;
    }
    @Override public Ingredient getRepairIngredient() {
        return repairIngredient.get();
    }
}
