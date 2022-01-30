package com.platinumg17.rigoranthusemortisreborn.magica.common.datagen;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.blocks.DecorativeOrStorageBlocks;
import com.platinumg17.rigoranthusemortisreborn.canis.CanisBlocks;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.core.init.Registration;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.BlockRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.nio.file.Path;

public class RigBlockTagProvider extends BlockTagsProvider {

    public static Tag.Named<Block> IGNORE_TILE =  BlockTags.createOptional(RigoranthusEmortisReborn.rl("ignore_tile"));

    public RigBlockTagProvider(DataGenerator generatorIn, ExistingFileHelper helper) {
        super(generatorIn, EmortisConstants.MOD_ID, helper);
    }

    @Override
    protected void addTags() {
        // super.addTags();
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(


                BlockRegistry.CREATIVE_DOMINION_JAR,
                BlockRegistry.CREATIVE_ICHOR_JAR,
                BlockRegistry.DOMINION_JAR,
                BlockRegistry.ICHOR_JAR,
                BlockRegistry.DOMINION_CRYSTALLIZER_BLOCK,
                BlockRegistry.ICHOR_EXTRACTOR_BLOCK,
                BlockRegistry.DOMINION_GEM_BLOCK,
                BlockRegistry.EMORTIC_CRAFTING_PRESS_BLOCK,
                BlockRegistry.EMORTIC_CORTEX_BLOCK,
                BlockRegistry.RITUAL_BLOCK,
                BlockRegistry.RELAY_DEPOSIT,
                BlockRegistry.RELAY_SPLITTER,
                BlockRegistry.EMORTIC_RELAY,
                BlockRegistry.hangingCadaverSkull,
                BlockRegistry.PSYGLYPHIC_CIPHER,
                BlockRegistry.PSYGLYPHIC_AMALG_BLOCK,
                BlockRegistry.SPLINTERED_PEDESTAL,

                BlockRegistry.TABLE_BLOCK,

                BlockRegistry.ABYSSALITE,
                BlockRegistry.CADAVER_SKULL,
                BlockRegistry.HIMALAYAN_SALT,
                BlockRegistry.FORTIFIED_SANDESITE,
                BlockRegistry.SOUL_COAL_BLOCK,
                BlockRegistry.GOLD_AMALGAM,
                BlockRegistry.OXYLITE,
                BlockRegistry.OPULENT_MAGMA,
                CanisBlocks.FOOD_BOWL.get(),
                BlockRegistry.APOGEAN_NETHERITE_BLOCK,
                BlockRegistry.AQUEOUS_NETHERITE_BLOCK,
                BlockRegistry.ATROPHYING_NETHERITE_BLOCK,
                BlockRegistry.OPULENT_NETHERITE_BLOCK,
                BlockRegistry.PERNICIOUS_NETHERITE_BLOCK,
                BlockRegistry.PHANTASMAL_NETHERITE_BLOCK,
                BlockRegistry.INCORPOREAL_NETHERITE_BLOCK,
                BlockRegistry.INFERNAL_NETHERITE_BLOCK,
                BlockRegistry.REMEX_NETHERITE_BLOCK,

                Registration.RECONDITE_ORE.get(),
                Registration.MASTERFUL_SMELTERY.get()
        );

        this.tag(BlockTags.MINEABLE_WITH_AXE).add(
                CanisBlocks.CANIS_BED.get(),
                BlockRegistry.AZULOREAL_LAMP, BlockRegistry.JESSIC_LAMP,

                BlockRegistry.AZULOREAL_LOG, BlockRegistry.JESSIC_LOG,
                BlockRegistry.AZULOREAL_WOOD, BlockRegistry.JESSIC_WOOD,

                BlockRegistry.STRIPPED_AZULOREAL_LOG, BlockRegistry.STRIPPED_JESSIC_LOG,
                BlockRegistry.STRIPPED_AZULOREAL_WOOD, BlockRegistry.STRIPPED_JESSIC_WOOD,

                BlockRegistry.AZULOREAL_FENCE, BlockRegistry.JESSIC_FENCE,
                BlockRegistry.AZULOREAL_FENCE_GATE, BlockRegistry.JESSIC_FENCE_GATE,
                DecorativeOrStorageBlocks.AZULOREAL_PLANKS.get(), DecorativeOrStorageBlocks.JESSIC_PLANKS.get(),
                    DecorativeOrStorageBlocks.VERTICAL_AZULOREAL_PLANKS.get(), DecorativeOrStorageBlocks.VERTICAL_JESSIC_PLANKS.get(),
                BlockRegistry.AZULOREAL_BUTTON, BlockRegistry.JESSIC_BUTTON,
                BlockRegistry.AZULOREAL_DOOR, BlockRegistry.JESSIC_DOOR,
                BlockRegistry.AZULOREAL_SLAB, BlockRegistry.JESSIC_SLAB,
                BlockRegistry.AZULOREAL_STAIRS, BlockRegistry.JESSIC_STAIRS,
                BlockRegistry.AZULOREAL_TRAPDOOR, BlockRegistry.JESSIC_TRAPDOOR,
                DecorativeOrStorageBlocks.AZULOREAL_LADDER.get(), DecorativeOrStorageBlocks.JESSIC_LADDER.get(),
                BlockRegistry.AZULOREAL_PRESSURE_PLATE, BlockRegistry.JESSIC_PRESSURE_PLATE

                );
        this.tag(BlockTags.MINEABLE_WITH_HOE).add(BlockRegistry.AZULOREAL_LEAVES, BlockRegistry.JESSIC_LEAVES);

        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(BlockRegistry.PINK_SALT, BlockRegistry.SANDESITE, BlockRegistry.FRAGMENTED_NETHERRACK, BlockRegistry.BLOCK_OF_ESOTERICUM, BlockRegistry.FRAGMENTED_COBBLESTONE);

        Tag.Named<Block> MAGIC_PLANTS = BlockTags.createOptional(RigoranthusEmortisReborn.rl("magic_plants"));

        this.tag(BlockTags.SAPLINGS).add(
                BlockRegistry.AZULOREAL_SAPLING,
                BlockRegistry.JESSIC_SAPLING
        );

        this.tag(MAGIC_PLANTS).add(BlockRegistry.DOMINION_BERRY_BUSH);

        this.tag(Tags.Blocks.FENCES).add(BlockRegistry.AZULOREAL_FENCE, BlockRegistry.JESSIC_FENCE);
        this.tag(Tags.Blocks.FENCES_WOODEN).add(BlockRegistry.AZULOREAL_FENCE, BlockRegistry.JESSIC_FENCE);

        this.tag(Tags.Blocks.FENCE_GATES).add(BlockRegistry.AZULOREAL_FENCE_GATE, BlockRegistry.JESSIC_FENCE_GATE);
        this.tag(Tags.Blocks.FENCE_GATES_WOODEN).add(BlockRegistry.AZULOREAL_FENCE_GATE, BlockRegistry.JESSIC_FENCE_GATE);

        this.tag(BlockTags.LOGS).add(
                BlockRegistry.AZULOREAL_LOG, BlockRegistry.JESSIC_LOG);
        this.tag(BlockTags.LOGS_THAT_BURN).add(
                BlockRegistry.AZULOREAL_LOG, BlockRegistry.JESSIC_LOG);
        this.tag(BlockTags.PLANKS).add(DecorativeOrStorageBlocks.AZULOREAL_PLANKS.get(), DecorativeOrStorageBlocks.JESSIC_PLANKS.get());
        this.tag(BlockTags.FENCE_GATES).add(BlockRegistry.AZULOREAL_FENCE_GATE, BlockRegistry.JESSIC_FENCE_GATE);
        this.tag(BlockTags.FENCES).add(BlockRegistry.AZULOREAL_FENCE, BlockRegistry.JESSIC_FENCE);
        this.tag(BlockTags.WOODEN_FENCES).add(BlockRegistry.AZULOREAL_FENCE, BlockRegistry.JESSIC_FENCE);

        this.tag(BlockTags.createOptional(new ResourceLocation("minecraft", "leaves/verdurous_leaves"))).add(BlockRegistry.AZULOREAL_LEAVES, BlockRegistry.JESSIC_LEAVES);
        this.tag(BlockTags.LEAVES).add(BlockRegistry.AZULOREAL_LEAVES, BlockRegistry.JESSIC_LEAVES);

        this.tag(BlockTags.CLIMBABLE).add(DecorativeOrStorageBlocks.JESSIC_LADDER.get(), DecorativeOrStorageBlocks.AZULOREAL_LADDER.get());

//        this.tag(BlockTags.UNSTABLE_BOTTOM_CENTER).add(BlockRegistry.LIGHT_BLOCK, BlockRegistry.hangingCadaverSkull, BlockRegistry.PSYGLYPHIC_CIPHER);

        this.tag(BlockTags.PREVENT_MOB_SPAWNING_INSIDE).add(BlockRegistry.OPULENT_MAGMA);
        this.tag(BlockTags.STRIDER_WARM_BLOCKS).add(BlockRegistry.OPULENT_MAGMA);

        this.tag(BlockTags.STONE_ORE_REPLACEABLES).add(BlockRegistry.FRAGMENTED_COBBLESTONE);
        this.tag(BlockTags.STONE_ORE_REPLACEABLES).add(BlockRegistry.FRAGMENTED_COBBLESTONE);
        this.tag(BlockTags.SMALL_DRIPLEAF_PLACEABLE).add(BlockRegistry.FRAGMENTED_COBBLESTONE);
        this.tag(BlockTags.RABBITS_SPAWNABLE_ON).add(BlockRegistry.FRAGMENTED_COBBLESTONE);
        this.tag(BlockTags.MOSS_REPLACEABLE).add(BlockRegistry.FRAGMENTED_COBBLESTONE);
        this.tag(BlockTags.MUSHROOM_GROW_BLOCK).add(BlockRegistry.FRAGMENTED_COBBLESTONE);
        this.tag(BlockTags.GOATS_SPAWNABLE_ON).add(BlockRegistry.FRAGMENTED_COBBLESTONE);
        this.tag(BlockTags.VALID_SPAWN).add(BlockRegistry.FRAGMENTED_COBBLESTONE);
        this.tag(BlockTags.WOLVES_SPAWNABLE_ON).add(BlockRegistry.FRAGMENTED_COBBLESTONE);
        this.tag(BlockTags.FOXES_SPAWNABLE_ON).add(BlockRegistry.FRAGMENTED_COBBLESTONE);
        this.tag(BlockTags.ENDERMAN_HOLDABLE).add(BlockRegistry.FRAGMENTED_COBBLESTONE);
        this.tag(BlockTags.BIG_DRIPLEAF_PLACEABLE).add(BlockRegistry.FRAGMENTED_COBBLESTONE);
        this.tag(BlockTags.AZALEA_ROOT_REPLACEABLE).add(BlockRegistry.FRAGMENTED_COBBLESTONE);
        this.tag(BlockTags.AZALEA_GROWS_ON).add(BlockRegistry.FRAGMENTED_COBBLESTONE);
        this.tag(BlockTags.BAMBOO_PLANTABLE_ON).add(BlockRegistry.FRAGMENTED_COBBLESTONE);
        this.tag(BlockTags.ANIMALS_SPAWNABLE_ON).add(BlockRegistry.FRAGMENTED_COBBLESTONE);

        this.tag(BlockTags.BUTTONS).add(BlockRegistry.AZULOREAL_BUTTON, BlockRegistry.JESSIC_BUTTON);

        this.tag(BlockTags.SLABS).add(BlockRegistry.AZULOREAL_SLAB, BlockRegistry.JESSIC_SLAB);
        this.tag(BlockTags.STAIRS).add(BlockRegistry.AZULOREAL_STAIRS, BlockRegistry.JESSIC_STAIRS);
        this.tag(BlockTags.TRAPDOORS).add(BlockRegistry.AZULOREAL_TRAPDOOR, BlockRegistry.JESSIC_TRAPDOOR);
        this.tag(BlockTags.WOODEN_BUTTONS).add(BlockRegistry.AZULOREAL_BUTTON, BlockRegistry.JESSIC_BUTTON);
        this.tag(BlockTags.WOODEN_DOORS).add(BlockRegistry.AZULOREAL_DOOR, BlockRegistry.JESSIC_DOOR);
        this.tag(BlockTags.DOORS).add(BlockRegistry.AZULOREAL_DOOR, BlockRegistry.JESSIC_DOOR);
        this.tag(BlockTags.WOODEN_SLABS).add(BlockRegistry.AZULOREAL_SLAB, BlockRegistry.JESSIC_SLAB);
        this.tag(BlockTags.WOODEN_STAIRS).add(BlockRegistry.AZULOREAL_STAIRS, BlockRegistry.JESSIC_STAIRS);
        this.tag(BlockTags.WOODEN_TRAPDOORS).add(BlockRegistry.AZULOREAL_TRAPDOOR, BlockRegistry.JESSIC_TRAPDOOR);

        this.tag(IGNORE_TILE).add(
                BlockRegistry.INTANGIBLE_AIR,
                BlockRegistry.LIGHT_BLOCK
        );
    }

    protected Path getPath(ResourceLocation resource) {
        return this.generator.getOutputFolder().resolve("data/" + resource.getNamespace() + "/tags/blocks/" + resource.getPath() + ".json");
    }

    public String getName() {
        return "RE tags";
    }
}