package com.platinumg17.rigoranthusemortisreborn.canis.client.data;

import com.platinumg17.rigoranthusemortisreborn.canis.CanisBlocks;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.canis.CanisItems;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.REUtil;
import net.minecraft.world.level.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.ItemLike;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelBuilder.Perspective;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class REItemModelProvider extends ItemModelProvider {

    public REItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, EmortisConstants.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "RigoranthusEmortisReborn Item Models";
    }

    @Override
    protected void registerModels() {
        handheld(CanisItems.TINY_BONE);
        handheld(CanisItems.BIG_BONE);

        handheld(CanisItems.TRAINING_TREAT);
        handheld(CanisItems.REGULAR_TREAT);
        handheld(CanisItems.MASTER_TREAT);
        handheld(CanisItems.HOMINI_TREAT);
//        handheld(CanisItems.BREEDING_BONE);
        handheld(CanisItems.CHEW_STICK);
//        generated(CanisItems.CAPE);
        generated(CanisItems.COLLAR_SHEARS);
        generated(CanisItems.CREATIVE_COLLAR);
        generated(CanisItems.CANIS_SUMMONING_CHARM);
        generated(CanisItems.CADAVER_SUMMONING_CHARM);
        generated(CanisItems.MASTER_CHANGE);
        generated(CanisItems.SUNGLASSES);
        generated(CanisItems.THROW_BONE);
        generated(CanisItems.THROW_BONE_WET);
        generated(CanisItems.THROW_STICK);
        generated(CanisItems.THROW_STICK_WET);
        generated(CanisItems.TREAT_BAG);
        generated(CanisItems.WHISTLE);
        generated(CanisItems.WOOL_COLLAR);

        blockItem(CanisBlocks.CANIS_BED);
        blockItem(CanisBlocks.FOOD_BOWL);
    }

    private ResourceLocation itemTexture(Supplier<? extends ItemLike> item) {
        return modLoc(ModelProvider.ITEM_FOLDER + "/" + name(item));
    }

    private String name(Supplier<? extends ItemLike> item) {
        return item.get().asItem().getRegistryName().getPath();
    }

    private ItemModelBuilder blockItem(Supplier<? extends Block> block) {
        return blockItem(block, "");
    }

    private ItemModelBuilder radar(Supplier<? extends ItemLike> item) {
        return radar(item, itemTexture(item));
    }

    private ItemModelBuilder radar(Supplier<? extends ItemLike> item, ResourceLocation texture) {
        ItemModelBuilder builder = generated(item, texture);
        builder.transforms().transform(Perspective.THIRDPERSON_RIGHT).rotation(0, 0, 55F).translation(0, 4F, 0.5F).scale(0.85F);
        builder.transforms().transform(Perspective.THIRDPERSON_LEFT).rotation(0, 0, -55F).translation(0, 4F, 0.5F).scale(0.85F);
        builder.transforms().transform(Perspective.FIRSTPERSON_RIGHT).translation(-3.13F, 3.2F, 1.13F).scale(0.8F);
        builder.transforms().transform(Perspective.FIRSTPERSON_LEFT).translation(-3.13F, 3.2F, 1.13F).scale(0.8F);
        return builder;
    }

    private ItemModelBuilder generated(Supplier<? extends ItemLike> item) {
        return generated(item, itemTexture(item));
    }

    private ItemModelBuilder generated(Supplier<? extends ItemLike> item, ResourceLocation texture) {
        return getBuilder(name(item)).parent(new UncheckedModelFile(EmortisConstants.MOD_ID + ":models/item")).texture("layer0", texture);
    }

    private ItemModelBuilder handheld(Supplier<? extends ItemLike> item) {
        return handheld(item, itemTexture(item));
    }

    private ItemModelBuilder handheld(Supplier<? extends ItemLike> item, ResourceLocation texture) {
        return getBuilder(name(item)).parent(new UncheckedModelFile(REUtil.getResource(EmortisConstants.MOD_ID + ":models/item"))).texture("layer0", texture);
    }

    private ItemModelBuilder blockItem(Supplier<? extends Block> block, String suffix) {
        return withExistingParent(name(block), modLoc(EmortisConstants.MOD_ID + ":models/block/" + name(block) + suffix));
    }
}
/*
    private ItemModelBuilder generated(Supplier<? extends ItemLike> item) {
        return generated(item, itemTexture(item));
    }

    private ItemModelBuilder generated(Supplier<? extends ItemLike> item, ResourceLocation texture) {
        return getBuilder(name(item)).parent(new UncheckedModelFile(ModelProvider.ITEM_FOLDER + "/generated")).texture("layer0", texture);
    }

    private ItemModelBuilder handheld(Supplier<? extends ItemLike> item) {
        return handheld(item, itemTexture(item));
    }

    private ItemModelBuilder handheld(Supplier<? extends ItemLike> item, ResourceLocation texture) {
        return getBuilder(name(item)).parent(new UncheckedModelFile(ModelProvider.ITEM_FOLDER + "/handheld")).texture("layer0", texture);
    }

    private ItemModelBuilder blockItem(Supplier<? extends Block> block, String suffix) {
        return withExistingParent(name(block), modLoc(ModelProvider.BLOCK_FOLDER + "/" + name(block) + suffix));
    }
 */