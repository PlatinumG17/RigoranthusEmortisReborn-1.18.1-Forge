package com.platinumg17.rigoranthusemortisreborn.canis.client.data;

import com.platinumg17.rigoranthusemortisreborn.canis.CanisBlocks;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class REBlockstateProvider extends BlockStateProvider {

    public REBlockstateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, EmortisConstants.MOD_ID, exFileHelper);
    }

    public ExistingFileHelper getExistingHelper() {
        return this.models().existingFileHelper;
    }

    @Override
    public String getName() {
        return "RigoranthusEmortisReborn Blockstates/Block Models";
    }

    @Override
    protected void registerStatesAndModels() {
//        canisBath(CanisBlocks.CANIS_BATH);
        canisBed(CanisBlocks.CANIS_BED);
        createFromShape(CanisBlocks.FOOD_BOWL, new AABB(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D));
    }

    // Applies texture to all faces and for the input face culls that direction
    private static BiFunction<String, Direction, BiConsumer<Direction, ModelBuilder<BlockModelBuilder>.ElementBuilder.FaceBuilder>> cullFaceFactory = (texture, input) -> (d, b) -> b.texture(texture).cullface(d == input ? d : null);

    protected void createFromShape(Supplier<? extends Block> blockIn, AABB bb) {
        BlockModelBuilder model = this.models()
                .getBuilder(name(blockIn))
                .parent(this.models().getExistingFile(mcLoc(ModelProvider.BLOCK_FOLDER + "/block")))
                .texture("particle", extend(blockTexture(blockIn), "_bottom"))
                .texture("bottom", extend(blockTexture(blockIn), "_bottom"))
                .texture("top", extend(blockTexture(blockIn), "_top"))
                .texture("side", extend(blockTexture(blockIn), "_side"));

        model.element()
                .from((float) bb.minX, (float) bb.minY, (float) bb.minZ)
                .to((float) bb.maxX, (float) bb.maxY, (float) bb.maxZ)
                .allFaces((d, f) -> f.cullface(d == Direction.DOWN ? d : null).texture(d.getAxis().isHorizontal() ? "#side" : d == Direction.DOWN ? "#bottom" : "#top"));

        this.simpleBlock(blockIn.get(), model);
    }

    protected void canisBed(Supplier<? extends Block> blockIn) {
        BlockModelBuilder model = this.models()
                .getBuilder(name(blockIn))
                .parent(this.models().getExistingFile(mcLoc(ModelProvider.BLOCK_FOLDER + "/block")))
                .texture("particle", blockTexture(Blocks.OAK_PLANKS.delegate))
                .texture("bedding", blockTexture(Blocks.WHITE_WOOL.delegate))
                .texture("casing", blockTexture(Blocks.OAK_PLANKS.delegate))
                .ao(false);

        model.element()
                .from(1.6F, 3.2F, 1.6F)
                .to(14.4F, 6.4F, 14.4F)
                .face(Direction.UP).texture("#bedding").end()
                .face(Direction.NORTH).texture("#bedding");

        model.element() //base
                .from(0, 0, 0)
                .to(16, 3.2F, 16)
                .allFaces(cullFaceFactory.apply("#casing", Direction.DOWN));

        model.element()
                .from(11.2F, 3.2F, 0)
                .to(16, 9.6F, 1.6F)
                .allFaces(cullFaceFactory.apply("#casing", Direction.NORTH));

        model.element()
                .from(0, 3.2F, 0)
                .to(4.8F, 9.6F, 1.6F)
                .allFaces(cullFaceFactory.apply("#casing", Direction.NORTH));

        model.element()
                .from(14.4F, 3.2F, 0)
                .to(16, 9.6F, 16)
                .allFaces(cullFaceFactory.apply("#casing", Direction.EAST));

        model.element()
                .from(0, 3.2F, 14.4F)
                .to(16, 9.6F, 16)
                .allFaces(cullFaceFactory.apply("#casing", Direction.SOUTH));

        model.element()
                .from(0, 3.2F, 0)
                .to(1.6F, 9.6F, 16)
                .allFaces(cullFaceFactory.apply("#casing", Direction.WEST));

        this.simpleBlock(blockIn.get(), model);
    }

    private String name(Supplier<? extends IForgeRegistryEntry<?>> block) {
        return block.get().getRegistryName().getPath();
    }

    private ResourceLocation blockTexture(Supplier<? extends Block> block) {
        ResourceLocation base = block.get().getRegistryName();
        return prextend(base, ModelProvider.BLOCK_FOLDER + "/");
    }

    public ModelFile cross(Supplier<? extends Block> block) {
        return this.models().cross(name(block), blockTexture(block));
    }

    protected void makeSimple(Supplier<? extends Block> blockIn) {
        this.simpleBlock(blockIn.get());
    }

    private ResourceLocation prextend(ResourceLocation rl, String prefix) {
        return new ResourceLocation(rl.getNamespace(), prefix + rl.getPath());
    }

    private ResourceLocation extend(ResourceLocation rl, String suffix) {
        return new ResourceLocation(rl.getNamespace(), rl.getPath() + suffix);
    }
}
/*
     private void createSweetBerryBush() {
      this.createSimpleFlatItemModel(Items.SWEET_BERRIES);
      this.blockStateOutput.accept(FinishedVariantBlockState.multiVariant(Blocks.SWEET_BERRY_BUSH).with(BlockStateVariantBuilder.property(BlockStateProperties.AGE_3).generate((p_239910_1_) -> {
         return BlockModelDefinition.variant().with(BlockModelFields.MODEL, this.createSuffixedVariant(Blocks.SWEET_BERRY_BUSH, "_stage" + p_239910_1_, StockModelShapes.CROSS, ModelTextures::cross));
      })));
   }*/