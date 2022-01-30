package com.platinumg17.rigoranthusemortisreborn.magica.common.datagen;

import com.platinumg17.rigoranthusemortisreborn.blocks.DecorativeOrStorageBlocks;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.BlockRegistry;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.MagicItemsRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class RigItemTagProvider extends ItemTagsProvider {

    public RigItemTagProvider(DataGenerator dataGen, BlockTagsProvider blockTag, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGen, blockTag, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {

        this.tag(Tags.Items.FENCES).add(BlockRegistry.AZULOREAL_FENCE.asItem());
        this.tag(Tags.Items.FENCES_WOODEN).add(BlockRegistry.AZULOREAL_FENCE.asItem());
        this.tag(Tags.Items.FENCE_GATES).add(BlockRegistry.AZULOREAL_FENCE_GATE.asItem());
        this.tag(Tags.Items.FENCE_GATES_WOODEN).add(BlockRegistry.AZULOREAL_FENCE_GATE.asItem());

        this.tag(Tags.Items.FENCES).add(BlockRegistry.JESSIC_FENCE.asItem());
        this.tag(Tags.Items.FENCES_WOODEN).add(BlockRegistry.JESSIC_FENCE.asItem());
        this.tag(Tags.Items.FENCE_GATES).add(BlockRegistry.JESSIC_FENCE_GATE.asItem());
        this.tag(Tags.Items.FENCE_GATES_WOODEN).add(BlockRegistry.JESSIC_FENCE_GATE.asItem());

        this.tag(ItemTags.createOptional(new ResourceLocation("forge", "gems/dominion")))
                .add(MagicItemsRegistry.dominionGem);

        this.tag(ItemTags.createOptional(new ResourceLocation("forge", "logs/verdurous"))).add(
                BlockRegistry.AZULOREAL_LOG.asItem(), BlockRegistry.JESSIC_LOG.asItem(),
                BlockRegistry.AZULOREAL_WOOD.asItem(), BlockRegistry.JESSIC_WOOD.asItem(),
                BlockRegistry.STRIPPED_AZULOREAL_LOG.asItem(), BlockRegistry.STRIPPED_JESSIC_LOG.asItem(),
                BlockRegistry.STRIPPED_AZULOREAL_WOOD.asItem(), BlockRegistry.STRIPPED_JESSIC_WOOD.asItem()
        );
        this.tag(ItemTags.LOGS).add(
                BlockRegistry.AZULOREAL_LOG.asItem(), BlockRegistry.JESSIC_LOG.asItem(),
                BlockRegistry.AZULOREAL_WOOD.asItem(), BlockRegistry.JESSIC_WOOD.asItem(),
                BlockRegistry.STRIPPED_AZULOREAL_LOG.asItem(), BlockRegistry.STRIPPED_JESSIC_LOG.asItem(),
                BlockRegistry.STRIPPED_AZULOREAL_WOOD.asItem(), BlockRegistry.STRIPPED_JESSIC_WOOD.asItem()
        );
        this.tag(ItemTags.LOGS_THAT_BURN).add(
                BlockRegistry.AZULOREAL_LOG.asItem(), BlockRegistry.JESSIC_LOG.asItem(),
                BlockRegistry.AZULOREAL_WOOD.asItem(), BlockRegistry.JESSIC_WOOD.asItem(),
                BlockRegistry.STRIPPED_AZULOREAL_LOG.asItem(), BlockRegistry.STRIPPED_JESSIC_LOG.asItem(),
                BlockRegistry.STRIPPED_AZULOREAL_WOOD.asItem(), BlockRegistry.STRIPPED_JESSIC_WOOD.asItem()
        );
        this.tag(ItemTags.createOptional(new ResourceLocation("forge", "planks/azuloreal")))
                .add(DecorativeOrStorageBlocks.AZULOREAL_PLANKS.get().asItem());

        this.tag(Tags.Items.STORAGE_BLOCKS).add(BlockRegistry.DOMINION_GEM_BLOCK.asItem());
        this.tag(ItemTags.createOptional(new ResourceLocation("forge", "storage_blocks/dominion")))
                .add(BlockRegistry.DOMINION_GEM_BLOCK.asItem());
        this.tag(Tags.Items.GEMS).add(MagicItemsRegistry.dominionGem);
        this.tag(ItemTags.BEACON_PAYMENT_ITEMS).add(MagicItemsRegistry.dominionGem);
        this.tag(ItemTags.SAPLINGS).add(BlockRegistry.AZULOREAL_SAPLING.asItem(), BlockRegistry.JESSIC_SAPLING.asItem());
        this.tag(ItemTags.PLANKS).add(DecorativeOrStorageBlocks.AZULOREAL_PLANKS.get().asItem());
        this.tag(ItemTags.FENCES).add(BlockRegistry.AZULOREAL_FENCE.asItem());
        this.tag(ItemTags.WOODEN_FENCES).add(BlockRegistry.AZULOREAL_FENCE.asItem());
        this.tag(ItemTags.BEACON_PAYMENT_ITEMS).add(MagicItemsRegistry.dominionGem);
        this.tag(ItemTags.BUTTONS).add(BlockRegistry.AZULOREAL_BUTTON.asItem());
        this.tag(ItemTags.WOODEN_BUTTONS).add(BlockRegistry.AZULOREAL_BUTTON.asItem());
        this.tag(ItemTags.DOORS).add(BlockRegistry.AZULOREAL_DOOR.asItem());
        this.tag(ItemTags.WOODEN_DOORS).add(BlockRegistry.AZULOREAL_DOOR.asItem());
        this.tag(ItemTags.SLABS).add(BlockRegistry.AZULOREAL_SLAB.asItem());
        this.tag(ItemTags.WOODEN_SLABS).add(BlockRegistry.AZULOREAL_SLAB.asItem());
        this.tag(ItemTags.STAIRS).add(BlockRegistry.AZULOREAL_STAIRS.asItem());
        this.tag(ItemTags.WOODEN_STAIRS).add(BlockRegistry.AZULOREAL_STAIRS.asItem());
        this.tag(ItemTags.TRAPDOORS).add(BlockRegistry.AZULOREAL_TRAPDOOR.asItem());
        this.tag(ItemTags.WOODEN_TRAPDOORS).add(BlockRegistry.AZULOREAL_TRAPDOOR.asItem());
        this.tag(ItemTags.WOODEN_PRESSURE_PLATES).add(BlockRegistry.AZULOREAL_PRESSURE_PLATE.asItem());
        this.tag(ItemTags.PLANKS).add(DecorativeOrStorageBlocks.JESSIC_PLANKS.get().asItem());
        this.tag(ItemTags.FENCES).add(BlockRegistry.JESSIC_FENCE.asItem());
        this.tag(ItemTags.WOODEN_FENCES).add(BlockRegistry.JESSIC_FENCE.asItem());
        this.tag(ItemTags.BUTTONS).add(BlockRegistry.JESSIC_BUTTON.asItem());
        this.tag(ItemTags.WOODEN_BUTTONS).add(BlockRegistry.JESSIC_BUTTON.asItem());
        this.tag(ItemTags.DOORS).add(BlockRegistry.JESSIC_DOOR.asItem());
        this.tag(ItemTags.WOODEN_DOORS).add(BlockRegistry.JESSIC_DOOR.asItem());
        this.tag(ItemTags.SLABS).add(BlockRegistry.JESSIC_SLAB.asItem());
        this.tag(ItemTags.WOODEN_SLABS).add(BlockRegistry.JESSIC_SLAB.asItem());
        this.tag(ItemTags.STAIRS).add(BlockRegistry.JESSIC_STAIRS.asItem());
        this.tag(ItemTags.WOODEN_STAIRS).add(BlockRegistry.JESSIC_STAIRS.asItem());
        this.tag(ItemTags.TRAPDOORS).add(BlockRegistry.JESSIC_TRAPDOOR.asItem());
        this.tag(ItemTags.WOODEN_TRAPDOORS).add(BlockRegistry.JESSIC_TRAPDOOR.asItem());
        this.tag(ItemTags.WOODEN_PRESSURE_PLATES).add(BlockRegistry.JESSIC_PRESSURE_PLATE.asItem());
    }
}