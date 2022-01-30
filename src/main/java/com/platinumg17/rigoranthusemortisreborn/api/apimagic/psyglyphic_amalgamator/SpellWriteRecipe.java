package com.platinumg17.rigoranthusemortisreborn.api.apimagic.psyglyphic_amalgamator;

//import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.PsyglyphicAmalgamatorTile;
//import com.platinumg17.rigoranthusemortisreborn.magica.common.enchantment.EnchantmentRegistry;
//import com.platinumg17.rigoranthusemortisreborn.magica.common.items.SpellParchment;
//import com.platinumg17.rigoranthusemortisreborn.magica.setup.MagicItemsRegistry;
//import net.minecraft.enchantment.EnchantmentHelper;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.crafting.Ingredient;
//import net.minecraft.nbt.CompoundTag;
//
//import javax.annotation.Nullable;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import static com.platinumg17.rigoranthusemortisreborn.api.apimagic.psyglyphic_amalgamator.ReactivePsyglyphicRecipe.getParchment;
//
//public class SpellWriteRecipe extends PsyglyphicAmalgamatorRecipe {
//
//    public SpellWriteRecipe(){
//        this.pedestalItems = Collections.singletonList(Ingredient.of(MagicItemsRegistry.spellParchment));
//    }
//
//    @Override
//    public boolean isMatch(List<ItemStack> pedestalItems, ItemStack reagent, PsyglyphicAmalgamatorTile psyglyphicAmalgamatorTile, @Nullable Player player) {
//        int level = EnchantmentHelper.getEnchantments(reagent).getOrDefault(EnchantmentRegistry.REFLEX_SUMMON_ENCHANTMENT, 0);
//        ItemStack parchment = getParchment(pedestalItems);
//        return !parchment.isEmpty() && !SpellParchment.getSpell(parchment).isEmpty() && level > 0 && super.isMatch(pedestalItems, reagent, psyglyphicAmalgamatorTile, player);
//    }
//
//    @Override
//    public boolean doesReagentMatch(ItemStack reag) {
//        return true;
//    }
//
//    @Override
//    public ItemStack getResult(List<ItemStack> pedestalItems, ItemStack reagent, PsyglyphicAmalgamatorTile psyglyphicAmalgamatorTile) {
//        CompoundTag tag = reagent.getOrCreateTag();
//        ItemStack parchment = getParchment(pedestalItems);
//        tag.putString("spell", parchment.getOrCreateTag().getString("spell"));
//        reagent.setTag(tag);
//        return reagent.copy();
//    }
//}