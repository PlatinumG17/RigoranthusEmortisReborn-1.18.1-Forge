package com.platinumg17.rigoranthusemortisreborn.items.specialized.smeltery;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.config.Config;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ItemSoulCoal extends Item {
    public ItemSoulCoal(Properties properties) {
        super(properties);
        setRegistryName(EmortisConstants.MOD_ID, "soul_coal");
    }
    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(new TranslatableComponent("tooltip." + EmortisConstants.MOD_ID + ".soul_coal").setStyle(Style.EMPTY));
        tooltip.add(new TranslatableComponent("tooltip." + EmortisConstants.MOD_ID + ".soul_coal2").setStyle(Style.EMPTY));
    }
    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return Config.soulCoalBurnTime.get();
    }
}