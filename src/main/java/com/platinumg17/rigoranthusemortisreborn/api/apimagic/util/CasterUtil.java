package com.platinumg17.rigoranthusemortisreborn.api.apimagic.util;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.item.ICasterTool;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.SpellCaster;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.interfaces.ISpellCaster;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CasterUtil {

    public static ISpellCaster getCaster(ItemStack stack){
        Item item = stack.getItem();
        if(item instanceof ICasterTool){
            return ((ICasterTool) item).getSpellCaster(stack);
        }
        return new SpellCaster(stack);
    }
}