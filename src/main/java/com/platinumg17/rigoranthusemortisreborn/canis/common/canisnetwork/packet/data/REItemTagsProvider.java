package com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data;

import com.platinumg17.rigoranthusemortisreborn.canis.CanisItems;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.CanisTags;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.core.init.ItemInit;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.BlockRegistry;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.MagicItemsRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Arrays;
import java.util.function.Supplier;

public class REItemTagsProvider extends ItemTagsProvider {

    public REItemTagsProvider(DataGenerator generatorIn, BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper) {
        super(generatorIn, blockTagsProvider, EmortisConstants.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "RigoranthusEmortisReborn Item Tags";
    }

    @Override
    public void addTags() {
        createBlockTag(CanisTags.JESSIC_LOGS, BlockRegistry.JESSIC_LOG.asItem(), BlockRegistry.JESSIC_WOOD.asItem(), BlockRegistry.STRIPPED_JESSIC_LOG.asItem(), BlockRegistry.STRIPPED_JESSIC_WOOD.asItem());
        createBlockTag(CanisTags.AZULOREAL_LOGS, BlockRegistry.AZULOREAL_LOG.asItem(), BlockRegistry.AZULOREAL_WOOD.asItem(), BlockRegistry.STRIPPED_AZULOREAL_LOG.asItem(), BlockRegistry.STRIPPED_AZULOREAL_WOOD.asItem());
        createBlockTag(CanisTags.JESSIC, BlockRegistry.JESSIC_LOG.asItem(), BlockRegistry.JESSIC_WOOD.asItem(), BlockRegistry.STRIPPED_JESSIC_LOG.asItem(), BlockRegistry.STRIPPED_JESSIC_WOOD.asItem());
        createBlockTag(CanisTags.AZULOREAL, BlockRegistry.AZULOREAL_LOG.asItem(), BlockRegistry.AZULOREAL_WOOD.asItem(), BlockRegistry.STRIPPED_AZULOREAL_LOG.asItem(), BlockRegistry.STRIPPED_AZULOREAL_WOOD.asItem());

        tag(ItemTags.ARROWS).add(MagicItemsRegistry.BONE_ARROW);
        tag(ItemTags.SMALL_FLOWERS).add(BlockRegistry.AZULOREAL_ORCHID.asItem()).add(BlockRegistry.IRIDESCENT_SPROUTS.asItem());
        tag(ItemTags.TALL_FLOWERS).add(BlockRegistry.LISIANTHUS.asItem());
        tag(ItemTags.LOGS)
                .add(BlockRegistry.AZULOREAL_LOG.asItem())
                .add(BlockRegistry.AZULOREAL_WOOD.asItem())
                .add(BlockRegistry.STRIPPED_AZULOREAL_LOG.asItem())
                .add(BlockRegistry.STRIPPED_AZULOREAL_WOOD.asItem())
                .add(BlockRegistry.JESSIC_LOG.asItem())
                .add(BlockRegistry.JESSIC_WOOD.asItem())
                .add(BlockRegistry.STRIPPED_JESSIC_LOG.asItem())
                .add(BlockRegistry.STRIPPED_JESSIC_WOOD.asItem());
        tag(ItemTags.SOUL_FIRE_BASE_BLOCKS).addTags(CanisTags.AZULOREAL_LOGS);
        tag(ItemTags.LOGS_THAT_BURN).addTags(CanisTags.AZULOREAL_LOGS, CanisTags.JESSIC_LOGS);
        tag(ItemTags.SAPLINGS).add(BlockRegistry.AZULOREAL_SAPLING.asItem()).add(BlockRegistry.JESSIC_SAPLING.asItem());

        createTag(CanisTags.SNACK_ITEMS_TAMED, MagicItemsRegistry.BOTTLE_OF_ICHOR.delegate,/*CanisItems.BREEDING_BONE,*/ CanisItems.THROW_STICK, CanisItems.THROW_BONE, Items.BONE.delegate);
        appendToTag(CanisTags.TREATS);
        createTag(CanisTags.SNACK_ITEMS_UNTAMED, CanisItems.TRAINING_TREAT, Items.BONE.delegate);
        createTag(CanisTags.BREEDING_ITEMS, MagicItemsRegistry.BOTTLE_OF_ICHOR.delegate);//CanisItems.BREEDING_BONE);
        createTag(CanisTags.BONES, ItemInit.BONE_FRAGMENT, CanisItems.BIG_BONE, CanisItems.TINY_BONE, Items.BONE.delegate);//CanisItems.BREEDING_BONE);
        createTag(CanisTags.WAYWARD_TRAVELLER_BLACKLIST, CanisItems.THROW_BONE, CanisItems.THROW_BONE_WET, CanisItems.THROW_STICK, CanisItems.THROW_STICK_WET);
        createTag(CanisTags.TREATS, CanisItems.TRAINING_TREAT, CanisItems.REGULAR_TREAT, CanisItems.MASTER_TREAT, CanisItems.HOMINI_TREAT);
    }

    @SafeVarargs
    private final void createTag(Tag.Named<Item> tag, Supplier<? extends ItemLike>... items) {
        tag(tag).add(Arrays.stream(items).map(Supplier::get).map(ItemLike::asItem).toArray(Item[]::new));
    }

    private void createBlockTag(Tag.Named<Item> tag, Item... items) {
        tag(tag).add(Arrays.stream(items).toArray(Item[]::new));
    }

    @SafeVarargs
    private final void appendToTag(Tag.Named<Item> tag, Tag.Named<Item>... toAppend) {
        tag(tag).addTags(toAppend);
    }
}