package com.platinumg17.rigoranthusemortisreborn.core.registry;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.Tags.IOptionalNamedTag;

public class RigoranthusTagRegistry {
    public static class Blocks {
        public static final Tags.IOptionalNamedTag<Block> AZULOREAL_FENCE = createForgeTag("wooden_fences/azuloreal");
        public static final Tags.IOptionalNamedTag<Block> AZULOREAL_FENCE_GATE = createForgeTag("fence_gates/azuloreal");
        public static final Tags.IOptionalNamedTag<Block> JESSIC_FENCE = createForgeTag("wooden_fences/jessic");
        public static final Tags.IOptionalNamedTag<Block> JESSIC_FENCE_GATE = createForgeTag("fence_gates/jessic");

        public static final Tags.IOptionalNamedTag<Block> APOGEAN = createForgeTag("storage_blocks/apogean");
        public static final Tags.IOptionalNamedTag<Block> AQUEOUS = createForgeTag("storage_blocks/aqueous");
        public static final Tags.IOptionalNamedTag<Block> ATROPHYING = createForgeTag("storage_blocks/atrophying");
        public static final Tags.IOptionalNamedTag<Block> PERNICIOUS = createForgeTag("storage_blocks/pernicious");
        public static final Tags.IOptionalNamedTag<Block> PHANTASMAL = createForgeTag("storage_blocks/phantasmal");
        public static final Tags.IOptionalNamedTag<Block> OPULENT = createForgeTag("storage_blocks/opulent");
        public static final Tags.IOptionalNamedTag<Block> INCORPOREAL = createForgeTag("storage_blocks/incorporeal");
        public static final Tags.IOptionalNamedTag<Block> INFERNAL = createForgeTag("storage_blocks/infernal");
        public static final Tags.IOptionalNamedTag<Block> REMEX = createForgeTag("storage_blocks/remex");
        public static final Tags.IOptionalNamedTag<Block> ESOTERICUM = createForgeTag("storage_blocks/esotericum");
        public static final Tags.IOptionalNamedTag<Block> RECONDITE_ORE = createForgeTag("ores/recondite");
        public static final Tags.IOptionalNamedTag<Block> STORAGE_BLOCKS = createForgeTag("storage_blocks");

        public static IOptionalNamedTag<Block> createTag(String name) {
            return BlockTags.createOptional(RigoranthusEmortisReborn.rl(name));
        }
        public static IOptionalNamedTag<Block> createForgeTag(String name) {
            return BlockTags.createOptional(new ResourceLocation("forge", name));
        }
    }

    public static class Items {
        public static final Tags.IOptionalNamedTag<Item> MUSIC_DISKS = createForgeTag("music_disks");
        public static final Tags.IOptionalNamedTag<Item> APOGEAN = createForgeTag("storage_blocks/apogean");
        public static final Tags.IOptionalNamedTag<Item> AQUEOUS = createForgeTag("storage_blocks/aqueous");
        public static final Tags.IOptionalNamedTag<Item> ATROPHYING = createForgeTag("storage_blocks/atrophying");
        public static final Tags.IOptionalNamedTag<Item> PERNICIOUS = createForgeTag("storage_blocks/pernicious");
        public static final Tags.IOptionalNamedTag<Item> PHANTASMAL = createForgeTag("storage_blocks/phantasmal");
        public static final Tags.IOptionalNamedTag<Item> OPULENT = createForgeTag("storage_blocks/opulent");
        public static final Tags.IOptionalNamedTag<Item> INCORPOREAL = createForgeTag("storage_blocks/incorporeal");
        public static final Tags.IOptionalNamedTag<Item> INFERNAL = createForgeTag("storage_blocks/infernal");
        public static final Tags.IOptionalNamedTag<Item> REMEX = createForgeTag("storage_blocks/remex");
        public static final Tags.IOptionalNamedTag<Item> STORAGE_BLOCKS = createForgeTag("storage_blocks");
        public static final Tags.IOptionalNamedTag<Item> ESOTERICUM = createForgeTag("storage_blocks/esotericum");

//        public static final Tags.IOptionalNamedTag<Item> CRUSHING_HAMMER = createTag("crushing_hammer");
        public static final Tags.IOptionalNamedTag<Item> NETHERITE_INGOTS = createForgeTag("ingots/netherite");
        public static final Tags.IOptionalNamedTag<Item> AZULOREAL_FENCE = createForgeTag("wooden_fences/azuloreal");
        public static final Tags.IOptionalNamedTag<Item> AZULOREAL_FENCE_GATE = createForgeTag("fence_gates/azuloreal");

//        public static final Tags.IOptionalNamedTag<Item> CRUSHING_HAMMERS = createTag("crushing_hammers");
        public static final Tags.IOptionalNamedTag<Item> RIGORANTHUS_INGOTS = createTag("rigoranthus_ingots");
        public static final Tags.IOptionalNamedTag<Item> APOGEAN_ARMOR_SET = createTag("armor/apogean_armor_set");
        public static final Tags.IOptionalNamedTag<Item> AQUEOUS_ARMOR_SET = createTag("armor/aqueous_armor_set");
        public static final Tags.IOptionalNamedTag<Item> ATROPHYING_ARMOR_SET = createTag("armor/atrophying_armor_set");
        public static final Tags.IOptionalNamedTag<Item> INCORPOREAL_ARMOR_SET = createTag("armor/incorporeal_armor_set");
        public static final Tags.IOptionalNamedTag<Item> INFERNAL_ARMOR_SET = createTag("armor/infernal_armor_set");
        public static final Tags.IOptionalNamedTag<Item> OPULENT_ARMOR_SET = createTag("armor/opulent_armor_set");
        public static final Tags.IOptionalNamedTag<Item> PERNICIOUS_ARMOR_SET = createTag("armor/pernicious_armor_set");
        public static final Tags.IOptionalNamedTag<Item> PHANTASMAL_ARMOR_SET = createTag("armor/phantasmal_armor_set");
        public static final Tags.IOptionalNamedTag<Item> REMEX_ARMOR_SET = createTag("armor/remex_armor_set");

        public static final Tags.IOptionalNamedTag<Item> JESSIC_FENCE = createForgeTag("wooden_fences/jessic");
        public static final Tags.IOptionalNamedTag<Item> JESSIC_FENCE_GATE = createForgeTag("fence_gates/jessic");
        public static final Tags.IOptionalNamedTag<Item> NETHERITE_ARMOR = createForgeTag("armor/netherite");
        public static final Tags.IOptionalNamedTag<Item> NETHERITE_TOOLS = createForgeTag("tools/netherite");
        public static final Tags.IOptionalNamedTag<Item> AXES = createForgeTag("axes");
        public static final Tags.IOptionalNamedTag<Item> BOOTS = createForgeTag("boots");
        public static final Tags.IOptionalNamedTag<Item> CHESTPLATES = createForgeTag("chestplates");
        public static final Tags.IOptionalNamedTag<Item> COAL = createForgeTag("coal");
        public static final Tags.IOptionalNamedTag<Item> HELMETS = createForgeTag("helmets");
        public static final Tags.IOptionalNamedTag<Item> INGOTS = createForgeTag("ingots");
        public static final Tags.IOptionalNamedTag<Item> APOGEAN_INGOT = createForgeTag("ingots/apogean");
        public static final Tags.IOptionalNamedTag<Item> AQUEOUS_INGOT = createForgeTag("ingots/aqueous");
        public static final Tags.IOptionalNamedTag<Item> ATROPHYING_INGOT = createForgeTag("ingots/atrophying");
        public static final Tags.IOptionalNamedTag<Item> PERNICIOUS_INGOT = createForgeTag("ingots/pernicious");
        public static final Tags.IOptionalNamedTag<Item> PHANTASMAL_INGOT = createForgeTag("ingots/phantasmal");
        public static final Tags.IOptionalNamedTag<Item> OPULENT_INGOT = createForgeTag("ingots/opulent");
        public static final Tags.IOptionalNamedTag<Item> INCORPOREAL_INGOT = createForgeTag("ingots/incorporeal");
        public static final Tags.IOptionalNamedTag<Item> INFERNAL_INGOT = createForgeTag("ingots/infernal");
        public static final Tags.IOptionalNamedTag<Item> REMEX_INGOT = createForgeTag("ingots/remex");
        public static final Tags.IOptionalNamedTag<Item> LEATHER = createForgeTag("leather");
        public static final Tags.IOptionalNamedTag<Item> LEGGINGS = createForgeTag("leggings");
        public static final Tags.IOptionalNamedTag<Item> MUD_BALL = createForgeTag("mud_ball");
        public static final Tags.IOptionalNamedTag<Item> SWORDS = createForgeTag("swords");
        public static final Tags.IOptionalNamedTag<Item> TOOLS = createForgeTag("tools");

        public static IOptionalNamedTag<Item> createTag(String name) {
            return ItemTags.createOptional(RigoranthusEmortisReborn.rl(name));
        }
        public static IOptionalNamedTag<Item> createForgeTag(String name) {
            return ItemTags.createOptional(new ResourceLocation("forge", name));
        }
    }
}