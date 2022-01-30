package com.platinumg17.rigoranthusemortisreborn.magica.common.items;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.item.ICasterTool;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.AbstractSpellPart;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.Spell;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.SpellCaster;
import com.platinumg17.rigoranthusemortisreborn.magica.common.lib.LibItemNames;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

//public class SpellParchment extends ModItem implements ICasterTool {
//    public SpellParchment() {
//        super(LibItemNames.SPELL_PARCHMENT);
//    }
//
//    public static void setSpell(ItemStack stack, String spellRecipe){
//        stack.getOrCreateTag().putString("spell", spellRecipe);
//    }
//
//    @Deprecated
//    public static List<AbstractSpellPart> getSpellRecipe(ItemStack stack){
//        return getSpell(stack).recipe;
//    }
//
//    public static @Nonnull
//    Spell getSpell(ItemStack stack){
//        return SpellCaster.deserialize(stack).getSpell();
//    }
//
//    @Override
//    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
//        getInformation(stack, worldIn, tooltip2, flagIn);
//        super.appendHoverText(stack, worldIn, tooltip2, flagIn);
//    }
//}
//    public SpellParchment() {
//        super(LibItemNames.SPELL_PARCHMENT);
//    }
//
//    @Override
//    public void inventoryTick(ItemStack stack, Level p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
//        if(!stack.hasTag())
//            stack.setTag(new CompoundTag());
//    }
//
//    public static void setSpell(ItemStack stack, String spellRecipe){
//        stack.getOrCreateTag().putString("spell", spellRecipe);
//    }
//
//    @Deprecated
//    public static List<AbstractSpellPart> getSpellRecipe(ItemStack stack){
//        if(!stack.hasTag())
//            return null;
//        return SpellRecipeUtil.getSpellsFromTagString(stack.getOrCreateTag().getString("spell"));
//    }
//
//    @Override
//    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag p_77624_4_) {
//        if(!stack.hasTag() || stack.getOrCreateTag().getString("spell").equals(""))
//            return;
//        List<AbstractSpellPart> spellsFromTagString = SpellRecipeUtil.getSpellsFromTagString(stack.getOrCreateTag().getString("spell"));
//        Spell spell = new Spell(spellsFromTagString);
//        tooltip.add(new TextComponent(spell.getDisplayString()));
//    }
//
//    @Override
//    public boolean onScribe(Level world, BlockPos pos, Player player, InteractionHand handIn, ItemStack thisStack) {
//        if(!(player.getItemInHand(handIn).getItem() instanceof SpellBook))
//            return false;
//        if(SpellBook.getMode(player.getItemInHand(handIn).getOrCreateTag()) == 0){
//            PortUtil.sendMessage(player, new TranslatableComponent("rigoranthusemortisreborn.spell_parchment.no_spell"));
//            return false;
//        }
//        SpellParchment.setSpell(thisStack, SpellBook.getRecipeString(player.getItemInHand(handIn).getOrCreateTag(), SpellBook.getMode(player.getItemInHand(handIn).getOrCreateTag())));
//        PortUtil.sendMessage(player,new TranslatableComponent("rigoranthusemortisreborn.spell_parchment.inscribed"));
//        return false;
//    }
//}