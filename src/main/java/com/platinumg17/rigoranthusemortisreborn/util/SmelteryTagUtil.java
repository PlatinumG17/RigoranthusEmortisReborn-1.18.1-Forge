package com.platinumg17.rigoranthusemortisreborn.util;

import net.minecraft.world.item.Item;
import net.minecraft.tags.Tag;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;

public class SmelteryTagUtil {
    public static Item getOreDict(String oreDic) {
        ResourceLocation tag = new ResourceLocation("forge", oreDic);
        Tag<Item> t = ItemTags.getAllTags().getTag(tag);
        if (t == null) {
            return null;
        }
        Collection<Item> tagCollection = t.getValues();
        for (Item item : tagCollection) {
            return item;
        }
        return null;
    }
}