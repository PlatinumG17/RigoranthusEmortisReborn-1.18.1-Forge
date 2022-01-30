package com.platinumg17.rigoranthusemortisreborn.items.ingots;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

import static com.platinumg17.rigoranthusemortisreborn.core.init.ItemInit.IngotProp;

public class AqueousIngotItem extends Item {
    public AqueousIngotItem() {
        super(IngotProp);
        setRegistryName("aqueous_netherite_ingot");
    }

    public static Component newTip(String tip) {
        return new TranslatableComponent("tooltip.rigoranthusemortisreborn" + tip).setStyle(Style.EMPTY);
    }

    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tip, flagIn);
        if (Screen.hasShiftDown()) {
            tip.add(newTip(".aqueous_ingot"));
            tip.add(newTip(".aqueous_ingot2"));
            tip.add(newTip(".aqueous_ingot3"));
            tip.add(newTip(".aqueous_ingot4"));
            tip.add(newTip(".aqueous_ingot5"));
            tip.add(newTip(".aqueous_ingot6"));
            tip.add(newTip(".aqueous_ingot7"));
            tip.add(newTip(".aqueous_ingot8"));
            tip.add(newTip(".aqueous_ingot9"));
            tip.add(newTip(".aqueous_ingot10"));
            tip.add(newTip(".aqueous_ingot11"));
            tip.add(newTip(".aqueous_ingot12"));
            tip.add(newTip(".aqueous_ingot13"));
        } else {
            tip.add(newTip(".hold_shift"));
        }
    }
}