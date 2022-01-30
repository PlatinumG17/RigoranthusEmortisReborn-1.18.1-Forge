package com.platinumg17.rigoranthusemortisreborn.items.tooltypes;

import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ToolRegistry {

    ///_______________________  B A S E   T O O L S  _______________________///
    public static final REToolTypes SICKLE_TOOL = new REToolTypes(Material.WEB, Material.LEAVES, Material.PLANT, Material.REPLACEABLE_PLANT).addEnchantments(EnchantmentCategory.WEAPON);
    public static final REToolTypes CLAWS_TOOL = new REToolTypes(Material.REPLACEABLE_PLANT, Material.PLANT, Material.WEB).addEnchantments(EnchantmentCategory.WEAPON);
    public static final REToolTypes PICKAXE_TOOL = new REToolTypes(Material.METAL, Material.HEAVY_METAL, Material.STONE).addEnchantments(EnchantmentCategory.DIGGER);
    public static final REToolTypes HAMMER_TOOL = new REToolTypes(PICKAXE_TOOL).addEnchantments(EnchantmentCategory.WEAPON);
    public static final REToolTypes AXE_TOOL = new REToolTypes(Material.WOOD, Material.PLANT, Material.REPLACEABLE_PLANT).addEnchantments(EnchantmentCategory.DIGGER, EnchantmentCategory.WEAPON);
    public static final REToolTypes SHOVEL_TOOL = new REToolTypes(Material.TOP_SNOW, Material.SNOW, Material.CLAY, Material.GRASS, Material.DIRT, Material.SAND).addEnchantments(EnchantmentCategory.DIGGER);
    public static final REToolTypes SWORD_TOOL = new REToolTypes(Material.WEB).addEnchantments(EnchantmentCategory.WEAPON);
    public static final REToolTypes MISC_TOOL = new REToolTypes().addEnchantments(EnchantmentCategory.WEAPON);

    ///_______________________  M U L T I   T O O L S  _______________________///
    public static final REToolTypes AXE_PICK_TOOL = new REToolTypes(PICKAXE_TOOL, AXE_TOOL);
    public static final REToolTypes AXE_HAMMER_TOOL = new REToolTypes(AXE_TOOL, HAMMER_TOOL);
    public static final REToolTypes SHOVEL_PICK_TOOL = new REToolTypes(PICKAXE_TOOL, SHOVEL_TOOL);
    public static final REToolTypes SHOVEL_AXE_TOOL = new REToolTypes(AXE_TOOL, SHOVEL_TOOL);
    public static final REToolTypes MULTI_TOOL = new REToolTypes(PICKAXE_TOOL, AXE_TOOL, SHOVEL_TOOL);

    ///_______________________  U N U S E D  _______________________///
    public static final REToolTypes GAUNTLET_TOOL = new REToolTypes(Material.GLASS, Material.ICE, Material.ICE_SOLID).addEnchantments(EnchantmentCategory.WEAPON).addEnchantments(Enchantments.SILK_TOUCH);
}
