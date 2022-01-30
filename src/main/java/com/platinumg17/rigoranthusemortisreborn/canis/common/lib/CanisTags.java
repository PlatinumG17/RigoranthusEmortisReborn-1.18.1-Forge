package com.platinumg17.rigoranthusemortisreborn.canis.common.lib;

import com.platinumg17.rigoranthusemortisreborn.canis.common.util.REUtil;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.tags.ItemTags;

public class CanisTags {

    public static Tag.Named<Item> SNACK_ITEMS_TAMED = tag("snack_items_tamed");
    public static Tag.Named<Item> SNACK_ITEMS_UNTAMED = tag("snack_items_untamed");
    public static Tag.Named<Item> BREEDING_ITEMS = tag("breeding_items");
    public static Tag.Named<Item> WAYWARD_TRAVELLER_BLACKLIST = tag("wayward_traveller_blacklist");
    public static Tag.Named<Item> TREATS = tag("treats");
    public static Tag.Named<Item> BONES = tag("bones");

    public static Tag.Named<Item> JESSIC_LOGS = tag("jessic_logs");
    public static Tag.Named<Item> JESSIC = tag("jessic");
    public static Tag.Named<Item> AZULOREAL_LOGS = tag("azuloreal_logs");
    public static Tag.Named<Item> AZULOREAL = tag("azuloreal");

    private static Tag.Named<Item> tag(String name) {
        return ItemTags.bind(REUtil.getResourcePath(name));
    }
}
