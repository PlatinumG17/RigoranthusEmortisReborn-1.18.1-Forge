package com.platinumg17.rigoranthusemortisreborn.items.specialized.smeltery;

import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;

import java.util.List;

public class ItemAugment extends Item {
    public ItemAugment(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".augment_right_click").setStyle(Style.EMPTY.applyFormat((ChatFormatting.GRAY))));
    }
}