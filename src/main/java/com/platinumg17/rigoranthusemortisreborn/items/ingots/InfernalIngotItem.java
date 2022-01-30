package com.platinumg17.rigoranthusemortisreborn.items.ingots;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

import static com.platinumg17.rigoranthusemortisreborn.core.init.ItemInit.IngotProp;

public class InfernalIngotItem extends Item {
    public InfernalIngotItem() {
        super(IngotProp);
        setRegistryName("infernal_netherite_ingot");
    }

    public static Component newTip(String tip) {
        return new TranslatableComponent("tooltip.rigoranthusemortisreborn" + tip).setStyle(Style.EMPTY);
    }

    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tip, flagIn);
        if (Screen.hasShiftDown()) {
            tip.add(newTip(".infernal_ingot"));
            tip.add(newTip(".infernal_ingot2"));
            tip.add(newTip(".infernal_ingot3"));
            tip.add(newTip(".infernal_ingot4"));
            tip.add(newTip(".infernal_ingot5"));
            tip.add(newTip(".infernal_ingot6"));
            tip.add(newTip(".infernal_ingot7"));
            tip.add(newTip(".infernal_ingot8"));
            tip.add(newTip(".infernal_ingot9"));
            tip.add(newTip(".infernal_ingot10"));
            tip.add(newTip(".infernal_ingot11"));
            tip.add(newTip(".infernal_ingot12"));
            tip.add(newTip(".infernal_ingot13"));
            tip.add(newTip(".infernal_ingot14"));
            tip.add(newTip(".infernal_ingot15"));
            tip.add(newTip(".infernal_ingot16"));
            tip.add(newTip(".infernal_ingot17"));
            tip.add(newTip(".infernal_ingot18"));
        } else {
            tip.add(newTip(".hold_shift"));
        }
    }
}