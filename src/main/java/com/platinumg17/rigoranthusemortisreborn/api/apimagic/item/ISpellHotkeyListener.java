package com.platinumg17.rigoranthusemortisreborn.api.apimagic.item;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.interfaces.ISpellCaster;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.CasterUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ISpellHotkeyListener {

    default void onNextKeyPressed(ItemStack stack, ServerPlayer player){
        ISpellCaster iSpellCaster = CasterUtil.getCaster(stack);
        iSpellCaster.setNextSlot();
    }

    default void onPreviousKeyPressed(ItemStack stack, ServerPlayer player){
        ISpellCaster iSpellCaster = CasterUtil.getCaster(stack);
        iSpellCaster.setPreviousSlot();
    }

    @OnlyIn(Dist.CLIENT)
    default void onOpenBookMenuKeyPressed(ItemStack stack, Player player){}

    @OnlyIn(Dist.CLIENT)
    default void onRadialKeyPressed(ItemStack stack, Player player){}

}