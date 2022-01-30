package com.platinumg17.rigoranthusemortisreborn.blocks;

import com.mojang.datafixers.util.Pair;
import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.world.trees.REPlank;
import com.platinumg17.rigoranthusemortisreborn.world.trees.RigoranthusWoodTypes;
import com.teamabnormals.blueprint.common.block.*;
import com.teamabnormals.blueprint.common.block.chest.*;
import com.teamabnormals.blueprint.common.block.sign.*;
import com.teamabnormals.blueprint.common.block.wood.*;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.common.Mod;

import static com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn.MOD_ID;

@Mod.EventBusSubscriber(modid = EmortisConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DecorativeOrStorageBlocks {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final BlockSubRegistryHelper HELPER = RigoranthusEmortisReborn.REGISTRY_HELPER.getBlockSubHelper();

    //___________________ A Z U L O R E A L ___________________//

    public static final RegistryObject<Block> AZULOREAL_PLANKS = HELPER.createBlock("azuloreal_planks", ()-> new REPlank(Properties.PLANKS), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> AZULOREAL_BOOKSHELF = HELPER.createCompatFuelBlock("quark", "azuloreal_bookshelf", () -> new BookshelfBlock(Properties.BOOKSHELF), 300, CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> VERTICAL_AZULOREAL_PLANKS = HELPER.createCompatBlock("quark", "vertical_azuloreal_planks", () -> new Block(Properties.PLANKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> VERTICAL_AZULOREAL_SLAB = HELPER.createCompatFuelBlock("quark", "vertical_azuloreal_slab", () -> new VerticalSlabBlock(Properties.PLANKS), 150, CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> AZULOREAL_LADDER = HELPER.createCompatFuelBlock("quark", "azuloreal_ladder", () -> new BlueprintLadderBlock(Properties.LADDER), 300, CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> AZULOREAL_LEAF_CARPET = HELPER.createCompatBlock("quark", "azuloreal_leaf_carpet", () -> new LeafCarpetBlock(Properties.AZULOREAL_CARPET), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> STRIPPED_AZULOREAL_POST = HELPER.createCompatFuelBlock("quark", "stripped_azuloreal_post", () -> new WoodPostBlock(Properties.PLANKS), 300, CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> AZULOREAL_POST = HELPER.createCompatFuelBlock("quark", "azuloreal_post", () -> new WoodPostBlock(STRIPPED_AZULOREAL_POST, Properties.PLANKS), 300, CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> AZULOREAL_HEDGE = HELPER.createCompatFuelBlock("quark", "azuloreal_hedge", () -> new HedgeBlock(Properties.HEDGE), 300, CreativeModeTab.TAB_DECORATIONS);
    public static final Pair<RegistryObject<BlueprintChestBlock>, RegistryObject<BlueprintTrappedChestBlock>> AZULOREAL_CHESTS = HELPER.createCompatChestBlocks("quark", "azuloreal", MaterialColor.COLOR_CYAN);

    //___________________ J E S S I C ___________________//

    public static final RegistryObject<Block> JESSIC_PLANKS = HELPER.createBlock("jessic_planks", ()-> new REPlank(Properties.PLANKS), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> JESSIC_BOOKSHELF = HELPER.createCompatFuelBlock("quark", "jessic_bookshelf", () -> new BookshelfBlock(Properties.BOOKSHELF), 300, CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> VERTICAL_JESSIC_PLANKS = HELPER.createCompatBlock("quark", "vertical_jessic_planks", () -> new Block(Properties.PLANKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> VERTICAL_JESSIC_SLAB = HELPER.createCompatFuelBlock("quark", "vertical_jessic_slab", () -> new VerticalSlabBlock(Properties.PLANKS), 150, CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> JESSIC_LADDER = HELPER.createCompatFuelBlock("quark", "jessic_ladder", () -> new BlueprintLadderBlock(Properties.LADDER), 300, CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> JESSIC_LEAF_CARPET = HELPER.createCompatBlock("quark", "jessic_leaf_carpet", () -> new LeafCarpetBlock(Properties.JESSIC_CARPET), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> STRIPPED_JESSIC_POST = HELPER.createCompatFuelBlock("quark", "stripped_jessic_post", () -> new WoodPostBlock(Properties.PLANKS), 300, CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> JESSIC_POST = HELPER.createCompatFuelBlock("quark", "jessic_post", () -> new WoodPostBlock(STRIPPED_JESSIC_POST, Properties.PLANKS), 300, CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> JESSIC_HEDGE = HELPER.createCompatFuelBlock("quark", "jessic_hedge", () -> new HedgeBlock(Properties.HEDGE), 300, CreativeModeTab.TAB_DECORATIONS);
    public static final Pair<RegistryObject<BlueprintChestBlock>, RegistryObject<BlueprintTrappedChestBlock>> JESSIC_CHESTS = HELPER.createCompatChestBlocks("quark", "jessic", MaterialColor.TERRACOTTA_MAGENTA);

    public static class Properties {
        public static final BlockBehaviour.Properties JESSIC_CARPET = BlockBehaviour.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_PURPLE).strength(0.0F).sound(SoundType.GRASS).noOcclusion();
        public static final BlockBehaviour.Properties AZULOREAL_CARPET = BlockBehaviour.Properties.of(Material.CLOTH_DECORATION, MaterialColor.SNOW).strength(0.0F).sound(SoundType.GRASS).noOcclusion();
        public static final BlockBehaviour.Properties HEDGE = BlockBehaviour.Properties.copy(Blocks.OAK_FENCE);
        public static final BlockBehaviour.Properties PLANKS = BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD);
        public static final BlockBehaviour.Properties LADDER = BlockBehaviour.Properties.of(Material.DECORATION).noOcclusion().strength(0.4F).sound(SoundType.LADDER);
        public static final BlockBehaviour.Properties BOOKSHELF = BlockBehaviour.Properties.of(Material.WOOD).strength(1.5F).sound(SoundType.WOOD);

        public static boolean ocelotOrParrot(BlockState state, BlockGetter access, BlockPos pos, EntityType<?> entity) {return entity == EntityType.OCELOT || entity == EntityType.PARROT;}
        public static boolean always(BlockState state, BlockGetter reader, BlockPos pos, EntityType<?> entity) {return true;}
        public static boolean hasPostProcess(BlockState state, BlockGetter reader, BlockPos pos) {
            return true;
        }
        public static boolean never(BlockState state, BlockGetter reader, BlockPos pos) {
            return false;
        }
    }
}