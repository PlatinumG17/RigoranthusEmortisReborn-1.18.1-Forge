package com.platinumg17.rigoranthusemortisreborn.items.specialized.smeltery;

import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;

public class ItemAugmentBlasting extends ItemAugment {

    public ItemAugmentBlasting(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".augment_blasting_pro").setStyle(Style.EMPTY.applyFormat((ChatFormatting.GREEN))));
        tooltip.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".augment_blasting_con").setStyle(Style.EMPTY.applyFormat(ChatFormatting.DARK_RED)));
    }
}