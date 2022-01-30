package com.platinumg17.rigoranthusemortisreborn.magica.common.datagen;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.magica.common.lib.LibBlockNames;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.BlockRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStatesDatagen extends BlockStateProvider {

    public BlockStatesDatagen(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        registerNormalCube(BlockRegistry.DOMINION_GEM_BLOCK, LibBlockNames.DOMINION_GEM_BLOCK);
        registerNormalCube(BlockRegistry.JESSIC_LOG, LibBlockNames.JESSIC_LOG);
        registerNormalCube(BlockRegistry.JESSIC_WOOD, LibBlockNames.JESSIC_WOOD);
        registerNormalCube(BlockRegistry.JESSIC_LEAVES, LibBlockNames.JESSIC_LEAVES);
        registerNormalCube(BlockRegistry.AZULOREAL_LOG, LibBlockNames.AZULOREAL_LOG);
        registerNormalCube(BlockRegistry.AZULOREAL_WOOD, LibBlockNames.AZULOREAL_WOOD);
        registerNormalCube(BlockRegistry.AZULOREAL_LEAVES, LibBlockNames.AZULOREAL_LEAVES);
        registerNormalCube(BlockRegistry.FRAGMENTED_COBBLESTONE, LibBlockNames.FRAGMENTED_COBBLESTONE);
    }

    public void registerNormalCube(Block block, String registry){
        buildNormalCube(registry);
        simpleBlock(block, getUncheckedModel(registry));
    }

    public static ModelFile getUncheckedModel(String registry){
        return new ModelFile.UncheckedModelFile("rigoranthusemortisreborn:block/" + registry);
    }

    public void buildNormalCube(String registryName){
        this.models().getBuilder(registryName).parent(new ModelFile.UncheckedModelFile("block/cube_all")).texture("all",getBlockLoc(registryName));
    }

    public ResourceLocation getBlockLoc(String registryName){
        return RigoranthusEmortisReborn.rl("blocks" + "/" +registryName);
    }
}