package com.platinumg17.rigoranthusemortisreborn.api.apimagic.item;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.client.renderer.IDisplayDominion;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.Spell;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.SpellCaster;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.interfaces.ISpellCaster;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.CasterUtil;
import com.platinumg17.rigoranthusemortisreborn.magica.common.items.SpellBook;
import com.platinumg17.rigoranthusemortisreborn.magica.common.util.PortUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * An interface for caster items that provides default behavior for scribing, displaying dominion level, and tooltips
 */
public interface ICasterTool extends IScribeable, IDisplayDominion, ISpellHotkeyListener {
    @Override
    default boolean onScribe(Level world, BlockPos pos, Player player, InteractionHand handIn, ItemStack stack) {
        ItemStack heldStack = player.getItemInHand(handIn);
        ISpellCaster thisCaster = CasterUtil.getCaster(stack);
        if(!((heldStack.getItem() instanceof SpellBook)))// || (heldStack.getItem() instanceof SpellParchment)) )
            return false;
        boolean success;

        Spell spell = new Spell();
        if(heldStack.getItem() instanceof ICasterTool){
            ISpellCaster heldCaster = CasterUtil.getCaster(heldStack);
            spell = heldCaster.getSpell();
            thisCaster.setColor(heldCaster.getColor());
            thisCaster.setFlavorText(heldCaster.getFlavorText());
        }
        if(isScribedSpellValid(thisCaster, player, handIn, stack, spell)){
            success = setSpell(thisCaster, player, handIn, stack, spell);
            if(success){
                sendSetMessage(player);
                return true;
            }
        }else{
            sendInvalidMessage(player);
        }
        return false;
    }

    default void sendSetMessage(Player player){
        PortUtil.sendMessageNoSpam(player, new TranslatableComponent("rigoranthusemortisreborn.set_spell"));
    }

    default void sendInvalidMessage(Player player){
        PortUtil.sendMessageNoSpam(player, new TranslatableComponent("rigoranthusemortisreborn.invalid_spell"));
    }

    default @Nonnull ISpellCaster getSpellCaster(ItemStack stack){
        return new SpellCaster(stack);
    }

    default boolean setSpell(ISpellCaster caster, Player player, InteractionHand hand, ItemStack stack, Spell spell){
        caster.setSpell(spell);
        return true;
    }

    default boolean isScribedSpellValid(ISpellCaster caster, Player player, InteractionHand hand, ItemStack stack, Spell spell){
        return spell.isValid();
    }

    @Override
    default boolean shouldDisplay(ItemStack stack) {
        return true;
    }

    default void getInformation(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
//        if(worldIn == null)
//            return;
//        ISpellCaster caster = getSpellCaster(stack);

//        if(caster.getSpell().isEmpty()){
//            tooltip2.add(new TranslatableComponent("rigoranthusemortisreborn.tooltip.can_inscribe"));
//            return;
//        }
        tooltip2.add(new TranslatableComponent("tooltip.rigoranthusemortisreborn.adonis").withStyle(Style.EMPTY.withItalic(true).withColor(ChatFormatting.BLUE)));
//        Spell spell = caster.getSpell();
//        tooltip2.add(new TextComponent(spell.getDisplayString()));
//        if(!caster.getFlavorText().isEmpty())
//            tooltip2.add(new TextComponent(caster.getFlavorText()).withStyle(Style.EMPTY.withItalic(true).withColor(ChatFormatting.BLUE)));
    }
}