package com.platinumg17.rigoranthusemortisreborn.items.tooltypes;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class REToolTypes {

    private final Set<Material> harvestMaterials = new HashSet<>();
    private final Set<Enchantment> enchantments = new HashSet<>();

//    public REToolTypes(Material... materials) {
//        this(materials);
//    }
    public REToolTypes(Material... materials) {harvestMaterials.addAll(Arrays.asList(materials));}
    public REToolTypes() {}

    public REToolTypes(REToolTypes... classCombo) {
        for(REToolTypes cls : classCombo) {
            harvestMaterials.addAll(cls.harvestMaterials);
            enchantments.addAll(cls.enchantments);
        }
    }

    public boolean canHarvest(BlockState state) {
        if(harvestMaterials.contains(state.getMaterial()))
            return true;

        return false;
    }

    //TODO Tool types WILL be created before Custom Mod enchantments are created + registered. Consider using suppliers instead
    public REToolTypes addEnchantments(Enchantment... enchantments) {
        this.enchantments.addAll(Arrays.asList(enchantments));
        return this;
    }

    public REToolTypes addEnchantments(List<Enchantment> enchantments) {
        this.enchantments.addAll(enchantments);
        return this;
    }

    public REToolTypes addEnchantments(EnchantmentCategory... enchantmentTypes) {
        for(EnchantmentCategory type : enchantmentTypes) {
            ForgeRegistries.ENCHANTMENTS.forEach(enchantment ->
            {if(enchantment.category == type) addEnchantments(enchantment);});
        }
        return this;
    }

    public Set<Material> getHarvestMaterials() {return harvestMaterials;}
    public Set<Enchantment> getEnchantments() {return enchantments;}  //TODO Consider --> functions that check if a material/enchant is valid, rather than make the whole lists accessible.
}
