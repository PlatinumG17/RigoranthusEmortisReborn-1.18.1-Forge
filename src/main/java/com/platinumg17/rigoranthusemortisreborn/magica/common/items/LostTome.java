package com.platinumg17.rigoranthusemortisreborn.magica.common.items;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.item.ICasterTool;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.Spell;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.interfaces.ISpellCaster;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.DominionUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class LostTome extends ModItem implements ICasterTool {
    public LostTome(Properties properties) {
        super(properties);
    }

    public LostTome(Properties properties, String registryName) {
        super(properties, registryName);
    }

    public LostTome(String registryName) {
        super(registryName);
    }

    @Override
    public boolean onScribe(Level world, BlockPos pos, Player player, InteractionHand handIn, ItemStack stack) {
        return player.isCreative() && ICasterTool.super.onScribe(world, pos, player, handIn, stack);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        ISpellCaster caster = getSpellCaster(stack);
        Spell spell = caster.getSpell();
        spell.setCost(Math.min(spell.getCastingCost()/2, DominionUtil.getMaxDominion(playerIn))); // Let even a new player cast 1 charge of a tome
        return caster.castSpell(worldIn, playerIn, handIn, new TranslatableComponent(""), spell);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
        if(worldIn == null)
            return;
        ISpellCaster caster = getSpellCaster(stack);

        Spell spell = caster.getSpell();
        tooltip2.add(new TextComponent(spell.getDisplayString()));
        if(!caster.getFlavorText().isEmpty())
            tooltip2.add(new TextComponent(caster.getFlavorText()).withStyle(Style.EMPTY.withItalic(true).withColor(ChatFormatting.BLUE)));

        tooltip2.add(new TranslatableComponent("tooltip.rigoranthusemortisreborn.lost_tome"));
        super.appendHoverText(stack, worldIn, tooltip2, flagIn);
    }
}