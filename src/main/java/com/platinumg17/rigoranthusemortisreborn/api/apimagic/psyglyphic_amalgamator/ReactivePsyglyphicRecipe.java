package com.platinumg17.rigoranthusemortisreborn.api.apimagic.psyglyphic_amalgamator;

//import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.SpellCaster;
//import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
//import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.PsyglyphicAmalgamatorTile;
//import com.platinumg17.rigoranthusemortisreborn.magica.common.enchantment.EnchantmentRegistry;
//import com.platinumg17.rigoranthusemortisreborn.magica.common.items.SpellParchment;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.resources.ResourceLocation;
//
//import javax.annotation.Nonnull;
//import javax.annotation.Nullable;
//import java.util.List;
//
//public class ReactivePsyglyphicRecipe extends PsyglyphicEnchantingRecipe {
//    public ReactivePsyglyphicRecipe(ItemStack[] pedestalItems, int dominionCost) {
//        super(pedestalItems,  EnchantmentRegistry.REFLEX_SUMMON_ENCHANTMENT, 1, dominionCost);
//    }
//
//    @Override
//    public boolean isMatch(List<ItemStack> pedestalItems, ItemStack reagent, PsyglyphicAmalgamatorTile psyglyphicAmalgamatorTile, @Nullable Player player) {
//        ItemStack parchment = getParchment(pedestalItems);
//        return super.isMatch(pedestalItems, reagent, psyglyphicAmalgamatorTile, player) && !parchment.isEmpty() && !SpellParchment.getSpell(parchment).isEmpty();
//    }
//
//    public static @Nonnull ItemStack getParchment(List<ItemStack> pedestalItems){
//        for(ItemStack stack : pedestalItems){
//            if(stack.getItem() instanceof SpellParchment){
//                return stack;
//            }
//        }
//        return ItemStack.EMPTY;
//    }
//
//    @Override
//    public ItemStack getResult(List<ItemStack> pedestalItems, ItemStack reagent, PsyglyphicAmalgamatorTile psyglyphicAmalgamatorTile) {
//        ItemStack stack = super.getResult(pedestalItems, reagent, psyglyphicAmalgamatorTile);
//        CompoundTag tag = stack.getOrCreateTag();
//        ItemStack parchment = getParchment(pedestalItems);
//        tag.putString("spell", SpellParchment.getSpell(parchment).serialize());
//        tag.putString("spell_color", SpellCaster.deserialize(parchment).getColor().serialize());
//        stack.setTag(tag);
//        return stack;
//    }
//
//    @Override
//    public ResourceLocation getId() {
//        return RigoranthusEmortisReborn.rl("reactive");
//    }
//}