package com.platinumg17.rigoranthusemortisreborn.magica.setup;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.blocks.DecorativeOrStorageBlocks;
import com.platinumg17.rigoranthusemortisreborn.blocks.custom.*;
import com.platinumg17.rigoranthusemortisreborn.blocks.custom.skull.HangingSkullBlock;
import com.platinumg17.rigoranthusemortisreborn.blocks.custom.skull.RESkullBlock;
import com.platinumg17.rigoranthusemortisreborn.blocks.tileentity.HangingSkullTile;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.config.Config;
import com.platinumg17.rigoranthusemortisreborn.magica.client.renderer.tile.*;
import com.platinumg17.rigoranthusemortisreborn.magica.common.block.LightBlock;
import com.platinumg17.rigoranthusemortisreborn.magica.common.block.*;
import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.*;
import com.platinumg17.rigoranthusemortisreborn.magica.common.items.AnimBlockItem;
import com.platinumg17.rigoranthusemortisreborn.magica.common.items.RendererBlockItem;
import com.platinumg17.rigoranthusemortisreborn.magica.common.lib.LibBlockNames;
import com.platinumg17.rigoranthusemortisreborn.magica.common.lib.LibItemNames;
import com.platinumg17.rigoranthusemortisreborn.world.plants.SpectabilisBushBlock;
import com.platinumg17.rigoranthusemortisreborn.world.plants.VerdurousLeavesBlock;
import com.platinumg17.rigoranthusemortisreborn.world.trees.*;
import net.minecraft.core.NonNullList;
import com.teamabnormals.blueprint.core.util.item.filling.TargetedItemCategoryFiller;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

@ObjectHolder(EmortisConstants.MOD_ID)
public class BlockRegistry {
    public static Block.Properties LOG_PROP = BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F).sound(SoundType.WOOD);
    public static Block.Properties SAP_PROP = BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS);
    public static Block.Properties FLOWER_POT_PROP = BlockBehaviour.Properties.of(Material.DECORATION).strength(0.0F).noOcclusion();

    public static Block.Properties PLANKS = BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD);
    public static Block.Properties PRESSURE_PLATE = BlockBehaviour.Properties.of(Material.WOOD).noCollission().strength(0.5F).sound(SoundType.WOOD);
    public static Block.Properties DOOR = BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).noOcclusion().strength(3.0F).sound(SoundType.WOOD);
    public static Block.Properties BUTTON = BlockBehaviour.Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(SoundType.WOOD);
    public static Block.Properties LAMP_PROP = BlockBehaviour.Properties.of(Material.DECORATION).strength(0.0F).noOcclusion();
    public static Block.Properties NETHERITE_PROP = BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).lightLevel((value) -> { return 5; }).strength(25f, 30f).requiresCorrectToolForDrops().sound(SoundType.STONE);
    public static Block.Properties ABYSSAL_PROP = BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).strength(60f, 1800f).requiresCorrectToolForDrops().sound(SoundType.STONE);
    public static Block.Properties OXY_PROP = BlockBehaviour.Properties.of(Material.ICE_SOLID, MaterialColor.COLOR_BLUE).strength(12f, 20f).friction(0.5f).requiresCorrectToolForDrops().sound(SoundType.GLASS);
    public static Block.Properties FORTIFIED_PROP = BlockBehaviour.Properties.of(Material.METAL, MaterialColor.STONE).strength(8f, 10f).requiresCorrectToolForDrops().sound(SoundType.STONE);
    public static Block.Properties SOUL_PROP = BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(5.0F, 12.0F).sound(SoundType.SOUL_SAND);
    public static Block.Properties AMALGAM_PROP = BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).strength(10f, 15f).requiresCorrectToolForDrops().sound(SoundType.STONE);
    public static Block.Properties HIMALAYAN_PROP = BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_PINK).strength(7f, 10f).requiresCorrectToolForDrops().sound(SoundType.NETHER_ORE);
    public static Block.Properties BRAIN_PROP = BlockBehaviour.Properties.of(Material.SPONGE, MaterialColor.COLOR_PINK).strength(0.2f, 0.2f).noOcclusion().sound(SoundType.SLIME_BLOCK);
    public static Block.Properties FRAG_COBBLE_PROP =  BlockBehaviour.Properties.of(Material.SAND, MaterialColor.STONE).strength(0.8f, 0.8f).sound(SoundType.GRAVEL);
    public static Block.Properties FRAG_NETHER_PROP =  BlockBehaviour.Properties.of(Material.SAND, MaterialColor.NETHER).strength(0.6f, 0.6f).sound(SoundType.NETHERRACK);
    public static Block.Properties ESOTERIC_PROP = BlockBehaviour.Properties.of(Material.SAND, MaterialColor.STONE).strength(1.0f, 6.0f).sound(SoundType.GRAVEL);
    public static Block.Properties PINK_SALT_PROP =  BlockBehaviour.Properties.of(Material.SAND, MaterialColor.COLOR_PINK).strength(0.6f, 0.6f).sound(SoundType.GRAVEL);
    public static Block.Properties SANDESITE_PROP =  BlockBehaviour.Properties.of(Material.SAND, MaterialColor.CLAY).strength(0.6f, 0.6f).sound(SoundType.GRAVEL);


    //____________________________ W O O D   T Y P E S ____________________________//
    @ObjectHolder(LibBlockNames.AZULOREAL_LOG) public static StrippableLog AZULOREAL_LOG;
    @ObjectHolder(LibBlockNames.AZULOREAL_LEAVES) public static VerdurousLeavesBlock AZULOREAL_LEAVES;
    @ObjectHolder(LibBlockNames.AZULOREAL_SAPLING) public static SaplingBlock AZULOREAL_SAPLING;
    @ObjectHolder(LibBlockNames.AZULOREAL_WOOD) public static StrippableLog AZULOREAL_WOOD;
    @ObjectHolder(LibBlockNames.STRIPPED_AZULOREAL_LOG) public static RotatedPillarBlock STRIPPED_AZULOREAL_LOG;
    @ObjectHolder(LibBlockNames.STRIPPED_AZULOREAL_WOOD) public static RotatedPillarBlock STRIPPED_AZULOREAL_WOOD;

//    @ObjectHolder(LibBlockNames.AZULOREAL_PLANKS) public static REPlank AZULOREAL_PLANKS;
    @ObjectHolder(LibBlockNames.AZULOREAL_SLAB) public static SlabBlock AZULOREAL_SLAB;
    @ObjectHolder(LibBlockNames.AZULOREAL_STAIRS) public static StairBlock AZULOREAL_STAIRS;
    @ObjectHolder(LibBlockNames.AZULOREAL_PRESSURE_PLATE) public static PressurePlateBlock AZULOREAL_PRESSURE_PLATE;
    @ObjectHolder(LibBlockNames.AZULOREAL_FENCE) public static FenceBlock AZULOREAL_FENCE;
    @ObjectHolder(LibBlockNames.AZULOREAL_FENCE_GATE) public static FenceGateBlock AZULOREAL_FENCE_GATE;
    @ObjectHolder(LibBlockNames.AZULOREAL_BUTTON) public static WoodButtonBlock AZULOREAL_BUTTON;
    @ObjectHolder(LibBlockNames.AZULOREAL_DOOR) public static DoorBlock AZULOREAL_DOOR;
    @ObjectHolder(LibBlockNames.AZULOREAL_TRAPDOOR) public static TrapDoorBlock AZULOREAL_TRAPDOOR;
//    @ObjectHolder(LibBlockNames.AZULOREAL_STANDING_SIGN) public static AzulorealStandingSignBlock AZULOREAL_STANDING_SIGN;
//    @ObjectHolder(LibBlockNames.AZULOREAL_WALL_SIGN) public static AzulorealWallSignBlock AZULOREAL_WALL_SIGN;

    @ObjectHolder(LibBlockNames.JESSIC_LOG) public static StrippableLog JESSIC_LOG;
    @ObjectHolder(LibBlockNames.JESSIC_LEAVES) public static VerdurousLeavesBlock JESSIC_LEAVES;
    @ObjectHolder(LibBlockNames.JESSIC_SAPLING) public static SaplingBlock JESSIC_SAPLING;
    @ObjectHolder(LibBlockNames.JESSIC_WOOD) public static StrippableLog JESSIC_WOOD;
    @ObjectHolder(LibBlockNames.STRIPPED_JESSIC_LOG) public static RotatedPillarBlock STRIPPED_JESSIC_LOG;
    @ObjectHolder(LibBlockNames.STRIPPED_JESSIC_WOOD) public static RotatedPillarBlock STRIPPED_JESSIC_WOOD;

//    @ObjectHolder(LibBlockNames.JESSIC_PLANKS) public static REPlank JESSIC_PLANKS;
    @ObjectHolder(LibBlockNames.JESSIC_SLAB) public static SlabBlock JESSIC_SLAB;
    @ObjectHolder(LibBlockNames.JESSIC_STAIRS) public static StairBlock JESSIC_STAIRS;
    @ObjectHolder(LibBlockNames.JESSIC_PRESSURE_PLATE) public static PressurePlateBlock JESSIC_PRESSURE_PLATE;
    @ObjectHolder(LibBlockNames.JESSIC_FENCE) public static FenceBlock JESSIC_FENCE;
    @ObjectHolder(LibBlockNames.JESSIC_FENCE_GATE) public static FenceGateBlock JESSIC_FENCE_GATE;
    @ObjectHolder(LibBlockNames.JESSIC_BUTTON) public static WoodButtonBlock JESSIC_BUTTON;
    @ObjectHolder(LibBlockNames.JESSIC_DOOR) public static DoorBlock JESSIC_DOOR;
    @ObjectHolder(LibBlockNames.JESSIC_TRAPDOOR) public static TrapDoorBlock JESSIC_TRAPDOOR;
//    @ObjectHolder(LibBlockNames.JESSIC_STANDING_SIGN) public static JessicStandingSignBlock JESSIC_STANDING_SIGN;
//    @ObjectHolder(LibBlockNames.JESSIC_WALL_SIGN) public static JessicWallSignBlock JESSIC_WALL_SIGN;


    //____________________________ F L O W E R S ____________________________//
    @ObjectHolder(LibBlockNames.AZULOREAL_ORCHID) public static FlowerBlock AZULOREAL_ORCHID;
    @ObjectHolder(LibBlockNames.IRIDESCENT_SPROUTS) public static FlowerBlock IRIDESCENT_SPROUTS;
    @ObjectHolder(LibBlockNames.SPECTABILIS_BUSH) public static SpectabilisBushBlock SPECTABILIS_BUSH;
    @ObjectHolder(LibBlockNames.LISIANTHUS) public static TallFlowerBlock LISIANTHUS;

    @ObjectHolder(LibBlockNames.POTTED_AZULOREAL_ORCHID) public static FlowerPotBlock POTTED_AZULOREAL_ORCHID;
    @ObjectHolder(LibBlockNames.POTTED_IRIDESCENT_SPROUTS) public static FlowerPotBlock POTTED_IRIDESCENT_SPROUTS;
    @ObjectHolder(LibBlockNames.POTTED_AZULOREAL_SAPLING) public static FlowerPotBlock POTTED_AZULOREAL_SAPLING;
    @ObjectHolder(LibBlockNames.POTTED_JESSIC_SAPLING) public static FlowerPotBlock POTTED_JESSIC_SAPLING;


    //____________________________ B L O C K S ____________________________//
    @ObjectHolder(LibBlockNames.AZULOREAL_LAMP) public static RedstoneLampBlock AZULOREAL_LAMP;
    @ObjectHolder(LibBlockNames.JESSIC_LAMP) public static RedstoneLampBlock JESSIC_LAMP;

    @ObjectHolder(LibBlockNames.APOGEAN_NETHERITE_BLOCK) public static ModBlock APOGEAN_NETHERITE_BLOCK;
    @ObjectHolder(LibBlockNames.AQUEOUS_NETHERITE_BLOCK) public static ModBlock AQUEOUS_NETHERITE_BLOCK;
    @ObjectHolder(LibBlockNames.ATROPHYING_NETHERITE_BLOCK) public static ModBlock ATROPHYING_NETHERITE_BLOCK;
    @ObjectHolder(LibBlockNames.OPULENT_NETHERITE_BLOCK) public static ModBlock OPULENT_NETHERITE_BLOCK;
    @ObjectHolder(LibBlockNames.PERNICIOUS_NETHERITE_BLOCK) public static ModBlock PERNICIOUS_NETHERITE_BLOCK;
    @ObjectHolder(LibBlockNames.PHANTASMAL_NETHERITE_BLOCK) public static ModBlock PHANTASMAL_NETHERITE_BLOCK;
    @ObjectHolder(LibBlockNames.INCORPOREAL_NETHERITE_BLOCK) public static ModBlock INCORPOREAL_NETHERITE_BLOCK;
    @ObjectHolder(LibBlockNames.INFERNAL_NETHERITE_BLOCK) public static ModBlock INFERNAL_NETHERITE_BLOCK;
    @ObjectHolder(LibBlockNames.REMEX_NETHERITE_BLOCK) public static ModBlock REMEX_NETHERITE_BLOCK;

    @ObjectHolder(LibBlockNames.CADAVER_SKULL) public static RESkullBlock CADAVER_SKULL;
    @ObjectHolder(LibBlockNames.DWELLER_BRAIN) public static BrainBlock DWELLER_BRAIN;
    @ObjectHolder(LibBlockNames.OPULENT_MAGMA) public static OpulentMagmaBlock OPULENT_MAGMA;
    @ObjectHolder(LibBlockNames.FRAGMENTED_COBBLESTONE) public static GravelBlock FRAGMENTED_COBBLESTONE;
    @ObjectHolder(LibBlockNames.FRAGMENTED_NETHERRACK) public static GravelBlock FRAGMENTED_NETHERRACK;
    @ObjectHolder(LibBlockNames.BLOCK_OF_ESOTERICUM) public static GravelBlock BLOCK_OF_ESOTERICUM;
    @ObjectHolder(LibBlockNames.PINK_SALT) public static GravelBlock PINK_SALT;
    @ObjectHolder(LibBlockNames.SANDESITE) public static GravelBlock SANDESITE;
    @ObjectHolder(LibBlockNames.FORTIFIED_SANDESITE) public static ModBlock FORTIFIED_SANDESITE;
    @ObjectHolder(LibBlockNames.ABYSSALITE) public static ModBlock ABYSSALITE;
    @ObjectHolder(LibBlockNames.OXYLITE) public static ModBlock OXYLITE;
    @ObjectHolder(LibBlockNames.SOUL_COAL_BLOCK) public static ModBlock SOUL_COAL_BLOCK;
    @ObjectHolder(LibBlockNames.GOLD_AMALGAM) public static ModBlock GOLD_AMALGAM;
    @ObjectHolder(LibBlockNames.HIMALAYAN_SALT) public static ModBlock HIMALAYAN_SALT;



    //____________________________ T I L E   E N T I T Y   B L O C K S ____________________________//
    @ObjectHolder(LibBlockNames.HANGING_CADAVER_SKULL) public static HangingSkullBlock hangingCadaverSkull;
    @ObjectHolder(LibBlockNames.HANGING_CADAVER_SKULL) public static BlockEntityType<HangingSkullTile> hangingCadaverSkullTile;

    @ObjectHolder(LibBlockNames.DECAYING_BLOCK) public static DecayingBlock DECAYING_BLOCK;
    @ObjectHolder(LibBlockNames.DECAYING_BLOCK) public static BlockEntityType<DecayingBlockTile> DECAYING_TILE;
    @ObjectHolder(LibBlockNames.LIGHT_BLOCK) public static LightBlock LIGHT_BLOCK;
    @ObjectHolder(LibBlockNames.LIGHT_BLOCK) public static BlockEntityType<LightTile> LIGHT_TILE;
    @ObjectHolder(LibBlockNames.PSYGLYPHIC_AMALGAMATOR) public static BlockEntityType<PsyglyphicAmalgamatorTile> PSYGLYPHIC_AMALG_TILE;
    @ObjectHolder(LibBlockNames.PSYGLYPHIC_AMALGAMATOR) public static PsyglyphicAmalgamatorBlock PSYGLYPHIC_AMALG_BLOCK;
    @ObjectHolder(LibBlockNames.EMORTIC_CRAFTING_PRESS) public static EmorticCraftingPressBlock EMORTIC_CRAFTING_PRESS_BLOCK;
    @ObjectHolder(LibBlockNames.EMORTIC_CRAFTING_PRESS) public static BlockEntityType<EmorticCraftingPressTile> EMORTIC_CRAFTING_PRESS_TILE;
    @ObjectHolder(LibBlockNames.SPLINTERED_PEDESTAL) public static BlockEntityType<SplinteredPedestalTile> SPLINTERED_PEDESTAL_TILE;
    @ObjectHolder(LibBlockNames.SPLINTERED_PEDESTAL) public static SplinteredPedestal SPLINTERED_PEDESTAL;
    @ObjectHolder(LibBlockNames.DOMINION_JAR) public static DominionJar DOMINION_JAR;
    @ObjectHolder(LibBlockNames.DOMINION_JAR) public static BlockEntityType<DominionJarTile> DOMINION_JAR_TILE;
    @ObjectHolder(LibBlockNames.TABLE_BLOCK) public static TableBlock TABLE_BLOCK;
    @ObjectHolder(LibBlockNames.TABLE_BLOCK) public static BlockEntityType<TableTile> TABLE_TILE;
    @ObjectHolder(LibBlockNames.PORTAL) public static PortalBlock PORTAL_BLOCK;
    @ObjectHolder(LibBlockNames.PORTAL) public static BlockEntityType<PortalTile> PORTAL_TILE_TYPE;
    @ObjectHolder(LibBlockNames.INTANGIBLE_AIR) public static IntangibleAirBlock INTANGIBLE_AIR;
    @ObjectHolder(LibBlockNames.INTANGIBLE_AIR) public static  BlockEntityType<IntangibleAirTile> INTANGIBLE_AIR_TYPE;
    @ObjectHolder(LibBlockNames.DOMINION_BERRY_BUSH) public static DominionBerryBush DOMINION_BERRY_BUSH;
    @ObjectHolder(LibBlockNames.CREATIVE_DOMINION_JAR) public static CreativeDominionJar CREATIVE_DOMINION_JAR;
    @ObjectHolder(LibBlockNames.CREATIVE_DOMINION_JAR) public static BlockEntityType<CreativeDominionJarTile> CREATIVE_DOMINION_JAR_TILE;
    @ObjectHolder(LibBlockNames.RITUAL_VESSEL) public static RitualVesselBlock RITUAL_BLOCK;
    @ObjectHolder(LibBlockNames.RITUAL_VESSEL) public static BlockEntityType<RitualTile> RITUAL_TILE;
    @ObjectHolder(LibBlockNames.DOMINION_GEM_BLOCK) public static ModBlock DOMINION_GEM_BLOCK;
    @ObjectHolder(LibBlockNames.ICHOR_JAR) public static IchorJar ICHOR_JAR;
    @ObjectHolder(LibBlockNames.ICHOR_JAR) public static BlockEntityType<IchorJarTile> ICHOR_JAR_TILE;
    @ObjectHolder(LibBlockNames.CREATIVE_ICHOR_JAR) public static CreativeIchorJar CREATIVE_ICHOR_JAR;
    @ObjectHolder(LibBlockNames.CREATIVE_ICHOR_JAR) public static BlockEntityType<CreativeIchorJarTile> CREATIVE_ICHOR_JAR_TILE;
    @ObjectHolder(LibBlockNames.RELAY_DEPOSIT) public static RelayDepositBlock RELAY_DEPOSIT;
    @ObjectHolder(LibBlockNames.RELAY_DEPOSIT) public static BlockEntityType<RelayDepositTile> RELAY_DEPOSIT_TILE;
    @ObjectHolder(LibBlockNames.EMORTIC_RELAY) public static BlockEntityType<EmorticRelayTile> EMORTIC_RELAY_TILE;
    @ObjectHolder(LibBlockNames.EMORTIC_RELAY) public static EmorticRelay EMORTIC_RELAY;
    @ObjectHolder(LibBlockNames.RELAY_SPLITTER) public static RelaySplitterBlock RELAY_SPLITTER;
    @ObjectHolder(LibBlockNames.RELAY_SPLITTER) public static BlockEntityType<RelaySplitterTile> RELAY_SPLITTER_TILE;
    @ObjectHolder(LibBlockNames.ICHOR_EXTRACTOR) public static IchorExtractorBlock ICHOR_EXTRACTOR_BLOCK;
    @ObjectHolder(LibBlockNames.ICHOR_EXTRACTOR) public static BlockEntityType<IchorExtractorTile> ICHOR_EXTRACTOR_TILE;
    @ObjectHolder(LibBlockNames.EMORTIC_CORTEX) public static EmorticCortex EMORTIC_CORTEX_BLOCK;
    @ObjectHolder(LibBlockNames.EMORTIC_CORTEX) public static BlockEntityType<EmorticCortexTile> EMORTIC_CORTEX_TILE;
    @ObjectHolder(LibBlockNames.DOMINION_CRYSTALLIZER) public static DominionCrystallizerBlock DOMINION_CRYSTALLIZER_BLOCK;
    @ObjectHolder(LibBlockNames.DOMINION_CRYSTALLIZER) public static BlockEntityType<DominionCrystallizerTile> DOMINION_CRYSTALLIZER_TILE;
    @ObjectHolder(LibBlockNames.PSYGLYPHIC_CIPHER) public static CipherBlock PSYGLYPHIC_CIPHER;
    @ObjectHolder(LibBlockNames.PSYGLYPHIC_CIPHER) public static BlockEntityType<PsyglyphicCipherTile> PSYGLYPHIC_TILE;

    @ObjectHolder(LibBlockNames.STATE_PROVIDER) public static BlockStateProviderType stateProviderType;
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            IForgeRegistry<Block> registry = blockRegistryEvent.getRegistry();
            registry.register(new RedstoneLampBlock(LAMP_PROP).setRegistryName(LibBlockNames.AZULOREAL_LAMP));
//            registry.register(new REPlank(PLANKS).setRegistryName(LibBlockNames.AZULOREAL_PLANKS));
            registry.register(new SlabBlock(woodProp).setRegistryName(LibBlockNames.AZULOREAL_SLAB));
            registry.register(new StairBlock(()-> DecorativeOrStorageBlocks.AZULOREAL_PLANKS.get().defaultBlockState(),woodProp).setRegistryName(LibBlockNames.AZULOREAL_STAIRS));
            registry.register(new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, woodProp).setRegistryName(LibBlockNames.AZULOREAL_PRESSURE_PLATE));
            registry.register(new FenceBlock(woodProp).setRegistryName(LibBlockNames.AZULOREAL_FENCE));
            registry.register(new FenceGateBlock(woodProp).setRegistryName(LibBlockNames.AZULOREAL_FENCE_GATE));
            registry.register(new WoodButtonBlock(Block.Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(SoundType.WOOD)).setRegistryName(LibBlockNames.AZULOREAL_BUTTON));
            registry.register(new DoorBlock(woodProp).setRegistryName(LibBlockNames.AZULOREAL_DOOR));
            registry.register(new TrapDoorBlock(woodProp).setRegistryName(LibBlockNames.AZULOREAL_TRAPDOOR));
//            registry.register(new AzulorealStandingSignBlock(PLANKS, RigoranthusWoodTypes.AZULOREAL).setRegistryName(LibBlockNames.AZULOREAL_STANDING_SIGN));
//            registry.register(new AzulorealWallSignBlock(PLANKS, RigoranthusWoodTypes.AZULOREAL).setRegistryName(LibBlockNames.AZULOREAL_WALL_SIGN));
            registry.register(new StrippableLog(LOG_PROP, LibBlockNames.AZULOREAL_LOG, () -> BlockRegistry.STRIPPED_AZULOREAL_LOG));
            registry.register(new RotatedPillarBlock(LOG_PROP).setRegistryName(LibBlockNames.STRIPPED_AZULOREAL_LOG));
            registry.register(createAzulorealLeavesBlock().setRegistryName(LibBlockNames.AZULOREAL_LEAVES));
            registry.register(new StrippableLog(LOG_PROP, LibBlockNames.AZULOREAL_WOOD, () -> BlockRegistry.STRIPPED_AZULOREAL_WOOD));
            registry.register(new RotatedPillarBlock(LOG_PROP).setRegistryName(LibBlockNames.STRIPPED_AZULOREAL_WOOD));
            registry.register(new SaplingBlock(new AzulorealTree(), SAP_PROP).setRegistryName(LibBlockNames.AZULOREAL_SAPLING));
            registry.register(new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, () -> AZULOREAL_SAPLING, FLOWER_POT_PROP).setRegistryName(LibBlockNames.POTTED_AZULOREAL_SAPLING));

            registry.register(new RedstoneLampBlock(LAMP_PROP).setRegistryName(LibBlockNames.JESSIC_LAMP));
//            registry.register(new REPlank(PLANKS).setRegistryName(LibBlockNames.JESSIC_PLANKS));
            registry.register(new SlabBlock(woodProp).setRegistryName(LibBlockNames.JESSIC_SLAB));
            registry.register(new StairBlock(()-> DecorativeOrStorageBlocks.JESSIC_PLANKS.get().defaultBlockState(),woodProp).setRegistryName(LibBlockNames.JESSIC_STAIRS));
            registry.register(new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, woodProp).setRegistryName(LibBlockNames.JESSIC_PRESSURE_PLATE));
            registry.register(new FenceBlock(woodProp).setRegistryName(LibBlockNames.JESSIC_FENCE));
            registry.register(new FenceGateBlock(woodProp).setRegistryName(LibBlockNames.JESSIC_FENCE_GATE));
            registry.register(new WoodButtonBlock(Block.Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(SoundType.WOOD)).setRegistryName(LibBlockNames.JESSIC_BUTTON));
            registry.register(new DoorBlock(woodProp).setRegistryName(LibBlockNames.JESSIC_DOOR));
            registry.register(new TrapDoorBlock(woodProp).setRegistryName(LibBlockNames.JESSIC_TRAPDOOR));
//            registry.register(new JessicStandingSignBlock(PLANKS, RigoranthusWoodTypes.JESSIC).setRegistryName(LibBlockNames.JESSIC_STANDING_SIGN));
//            registry.register(new JessicWallSignBlock(PLANKS, RigoranthusWoodTypes.JESSIC).setRegistryName(LibBlockNames.JESSIC_WALL_SIGN));
            registry.register(new StrippableLog(LOG_PROP, LibBlockNames.JESSIC_LOG, () -> BlockRegistry.STRIPPED_JESSIC_LOG));
            registry.register(new RotatedPillarBlock(LOG_PROP).setRegistryName(LibBlockNames.STRIPPED_JESSIC_LOG));
            registry.register(createJessicLeavesBlock().setRegistryName(LibBlockNames.JESSIC_LEAVES));
            registry.register(new StrippableLog(LOG_PROP, LibBlockNames.JESSIC_WOOD, () -> BlockRegistry.STRIPPED_JESSIC_WOOD));
            registry.register(new RotatedPillarBlock(LOG_PROP).setRegistryName(LibBlockNames.STRIPPED_JESSIC_WOOD));
            registry.register(new SaplingBlock(new JessicTree(), SAP_PROP).setRegistryName(LibBlockNames.JESSIC_SAPLING));
            registry.register(new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, () -> JESSIC_SAPLING, FLOWER_POT_PROP).setRegistryName(LibBlockNames.POTTED_JESSIC_SAPLING));

            registry.register(createAzulorealOrchidBlock().setRegistryName(LibBlockNames.AZULOREAL_ORCHID));
            registry.register(createIridescentSproutBlock().setRegistryName(LibBlockNames.IRIDESCENT_SPROUTS));
            registry.register(new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, () -> AZULOREAL_ORCHID, FLOWER_POT_PROP).setRegistryName(LibBlockNames.POTTED_AZULOREAL_ORCHID));
            registry.register(new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, () -> IRIDESCENT_SPROUTS, FLOWER_POT_PROP).setRegistryName(LibBlockNames.POTTED_IRIDESCENT_SPROUTS));
            registry.register(new TallFlowerBlock(SAP_PROP).setRegistryName(LibBlockNames.LISIANTHUS));

            registry.register(new DominionBerryBush(BlockBehaviour.Properties.of(Material.PLANT).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH)));
            registry.register(new SpectabilisBushBlock(BlockBehaviour.Properties.of(Material.PLANT).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH)));

            registry.register(new HangingSkullBlock(LibBlockNames.HANGING_CADAVER_SKULL));
            registry.register(new ModBlock(ModBlock.defaultProperties().noOcclusion().lightLevel((s) -> 6), LibBlockNames.DOMINION_GEM_BLOCK));
            registry.register(new SplinteredPedestal());
            registry.register(new PsyglyphicAmalgamatorBlock());
            registry.register(new EmorticCortex());
            registry.register(new EmorticCraftingPressBlock());
            registry.register(new RitualVesselBlock(LibBlockNames.RITUAL_VESSEL));
            registry.register(new CipherBlock());
            registry.register(new IchorJar());
            registry.register(new CreativeIchorJar());
            registry.register(new DominionJar());
            registry.register(new CreativeDominionJar());
            registry.register(new EmorticRelay());
            registry.register(new RelayDepositBlock());
            registry.register(new RelaySplitterBlock());
            registry.register(new DominionCrystallizerBlock());
            registry.register(new IchorExtractorBlock());
            registry.register(new LightBlock());
            registry.register(new TableBlock());
            registry.register(new DecayingBlock());
            registry.register(new IntangibleAirBlock());
            registry.register(new PortalBlock());

            registry.register(new GravelBlock(FRAG_COBBLE_PROP).setRegistryName(LibBlockNames.FRAGMENTED_COBBLESTONE));
            registry.register(new GravelBlock(FRAG_NETHER_PROP).setRegistryName(LibBlockNames.FRAGMENTED_NETHERRACK));
            registry.register(new GravelBlock(ESOTERIC_PROP).setRegistryName(LibBlockNames.BLOCK_OF_ESOTERICUM));
            registry.register(new GravelBlock(PINK_SALT_PROP).setRegistryName(LibBlockNames.PINK_SALT));
            registry.register(new GravelBlock(SANDESITE_PROP).setRegistryName(LibBlockNames.SANDESITE));

            registry.register(new BrainBlock(BRAIN_PROP, LibBlockNames.DWELLER_BRAIN));
            registry.register(new RESkullBlock(LibBlockNames.CADAVER_SKULL));
            registry.register(new OpulentMagmaBlock(LibBlockNames.OPULENT_MAGMA));
            registry.register(new ModBlock(FORTIFIED_PROP, LibBlockNames.FORTIFIED_SANDESITE));
            registry.register(new ModBlock(ABYSSAL_PROP, LibBlockNames.ABYSSALITE));
            registry.register(new ModBlock(OXY_PROP, LibBlockNames.OXYLITE));
            registry.register(new ModBlock(SOUL_PROP, LibBlockNames.SOUL_COAL_BLOCK));
            registry.register(new ModBlock(AMALGAM_PROP, LibBlockNames.GOLD_AMALGAM));
            registry.register(new ModBlock(HIMALAYAN_PROP, LibBlockNames.HIMALAYAN_SALT));

            registry.register(new ModBlock(NETHERITE_PROP, LibBlockNames.APOGEAN_NETHERITE_BLOCK));
            registry.register(new ModBlock(NETHERITE_PROP, LibBlockNames.AQUEOUS_NETHERITE_BLOCK));
            registry.register(new ModBlock(NETHERITE_PROP, LibBlockNames.ATROPHYING_NETHERITE_BLOCK));
            registry.register(new ModBlock(NETHERITE_PROP, LibBlockNames.OPULENT_NETHERITE_BLOCK));
            registry.register(new ModBlock(NETHERITE_PROP, LibBlockNames.PERNICIOUS_NETHERITE_BLOCK));
            registry.register(new ModBlock(NETHERITE_PROP, LibBlockNames.PHANTASMAL_NETHERITE_BLOCK));
            registry.register(new ModBlock(NETHERITE_PROP, LibBlockNames.INCORPOREAL_NETHERITE_BLOCK));
            registry.register(new ModBlock(NETHERITE_PROP, LibBlockNames.INFERNAL_NETHERITE_BLOCK));
            registry.register(new ModBlock(NETHERITE_PROP, LibBlockNames.REMEX_NETHERITE_BLOCK));
        }
        public static FlowerBlock createAzulorealOrchidBlock() {
            return new FlowerBlock(MobEffects.HEAL, 8, Block.Properties.copy(Blocks.AZURE_BLUET)) {
                @Override public MobEffect getSuspiciousStewEffect() { return MobEffects.HEAL; }
                @Override public int getEffectDuration() { return this.getSuspiciousStewEffect().isInstantenous() ? 8 : 8 * 20; }
            };
        }
        public static FlowerBlock createIridescentSproutBlock() {
            return new FlowerBlock(MobEffects.NIGHT_VISION, 10, Block.Properties.copy(Blocks.NETHER_SPROUTS)) {
                @Override public MobEffect getSuspiciousStewEffect() { return MobEffects.NIGHT_VISION; }
                @Override public int getEffectDuration() { return this.getSuspiciousStewEffect().isInstantenous() ? 10 : 10 * 20; }
            };
        }
        static Block.Properties woodProp = Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD);
        public static VerdurousLeavesBlock createAzulorealLeavesBlock() {
            return new VerdurousLeavesBlock(Block.Properties.of(Material.LEAVES, MaterialColor.SNOW)
                    .noOcclusion().lightLevel((p_235455_0_) -> 10).strength(0.2F).randomTicks().sound(SoundType.GRASS)
                    .isValidSpawn(BlockRegistry::allowsSpawnOnLeaves).isSuffocating(BlockRegistry::isntSolid).isViewBlocking(BlockRegistry::isntSolid));
        }
        public static VerdurousLeavesBlock createJessicLeavesBlock() {
            return new VerdurousLeavesBlock(Block.Properties.of(Material.LEAVES, MaterialColor.COLOR_PURPLE)
                    .noOcclusion().lightLevel((p_235455_0_) -> 10).strength(0.2F).randomTicks().sound(SoundType.GRASS)
                    .isValidSpawn(BlockRegistry::allowsSpawnOnLeaves).isSuffocating(BlockRegistry::isntSolid).isViewBlocking(BlockRegistry::isntSolid));
        }

        @SubscribeEvent
        public static void onTileEntityRegistry(final RegistryEvent.Register<BlockEntityType<?>> event){

            event.getRegistry().register(BlockEntityType.Builder.of(HangingSkullTile::new, BlockRegistry.hangingCadaverSkull).build(null).setRegistryName(LibBlockNames.HANGING_CADAVER_SKULL));

            event.getRegistry().register(BlockEntityType.Builder.of(DecayingBlockTile::new, BlockRegistry.DECAYING_BLOCK).build(null).setRegistryName(LibBlockNames.DECAYING_BLOCK));
            event.getRegistry().register(BlockEntityType.Builder.of(DominionJarTile::new, BlockRegistry.DOMINION_JAR).build(null).setRegistryName(LibBlockNames.DOMINION_JAR));
            event.getRegistry().register(BlockEntityType.Builder.of(LightTile::new, BlockRegistry.LIGHT_BLOCK).build(null).setRegistryName(LibBlockNames.LIGHT_BLOCK));
            event.getRegistry().register(BlockEntityType.Builder.of(EmorticCraftingPressTile::new, BlockRegistry.EMORTIC_CRAFTING_PRESS_BLOCK).build(null).setRegistryName(LibBlockNames.EMORTIC_CRAFTING_PRESS));
            event.getRegistry().register(BlockEntityType.Builder.of(PsyglyphicAmalgamatorTile::new, BlockRegistry.PSYGLYPHIC_AMALG_BLOCK).build(null).setRegistryName(LibBlockNames.PSYGLYPHIC_AMALGAMATOR));
            event.getRegistry().register(BlockEntityType.Builder.of(SplinteredPedestalTile::new, BlockRegistry.SPLINTERED_PEDESTAL).build(null).setRegistryName(LibBlockNames.SPLINTERED_PEDESTAL));
            event.getRegistry().register(BlockEntityType.Builder.of(TableTile::new, BlockRegistry.TABLE_BLOCK).build(null).setRegistryName(LibBlockNames.TABLE_BLOCK));
            event.getRegistry().register(BlockEntityType.Builder.of(PortalTile::new, BlockRegistry.PORTAL_BLOCK).build(null).setRegistryName(LibBlockNames.PORTAL));
            event.getRegistry().register(BlockEntityType.Builder.of(IntangibleAirTile::new, BlockRegistry.INTANGIBLE_AIR).build(null).setRegistryName(LibBlockNames.INTANGIBLE_AIR));
            event.getRegistry().register(BlockEntityType.Builder.of(CreativeDominionJarTile::new, BlockRegistry.CREATIVE_DOMINION_JAR).build(null).setRegistryName(LibBlockNames.CREATIVE_DOMINION_JAR));
            event.getRegistry().register(BlockEntityType.Builder.of(RitualTile::new, BlockRegistry.RITUAL_BLOCK).build(null).setRegistryName(LibBlockNames.RITUAL_VESSEL));
            event.getRegistry().register(BlockEntityType.Builder.of(IchorJarTile::new, BlockRegistry.ICHOR_JAR).build(null).setRegistryName(LibBlockNames.ICHOR_JAR));
            event.getRegistry().register(BlockEntityType.Builder.of(CreativeIchorJarTile::new, BlockRegistry.CREATIVE_ICHOR_JAR).build(null).setRegistryName(LibBlockNames.CREATIVE_ICHOR_JAR));
            event.getRegistry().register(BlockEntityType.Builder.of(RelayDepositTile::new, BlockRegistry.RELAY_DEPOSIT).build(null).setRegistryName(LibBlockNames.RELAY_DEPOSIT));
            event.getRegistry().register(BlockEntityType.Builder.of(EmorticRelayTile::new, BlockRegistry.EMORTIC_RELAY).build(null).setRegistryName(LibBlockNames.EMORTIC_RELAY));
            event.getRegistry().register(BlockEntityType.Builder.of(IchorExtractorTile::new, BlockRegistry.ICHOR_EXTRACTOR_BLOCK).build(null).setRegistryName(LibBlockNames.ICHOR_EXTRACTOR));
            event.getRegistry().register(BlockEntityType.Builder.of(RelaySplitterTile::new, BlockRegistry.RELAY_SPLITTER).build(null).setRegistryName(LibBlockNames.RELAY_SPLITTER));
            event.getRegistry().register(BlockEntityType.Builder.of(EmorticCortexTile::new, BlockRegistry.EMORTIC_CORTEX_BLOCK).build(null).setRegistryName(LibBlockNames.EMORTIC_CORTEX));
            event.getRegistry().register(BlockEntityType.Builder.of(DominionCrystallizerTile::new, BlockRegistry.DOMINION_CRYSTALLIZER_BLOCK).build(null).setRegistryName(LibBlockNames.DOMINION_CRYSTALLIZER));
            event.getRegistry().register(BlockEntityType.Builder.of(PsyglyphicCipherTile::new, BlockRegistry.PSYGLYPHIC_CIPHER).build(null).setRegistryName(LibBlockNames.PSYGLYPHIC_CIPHER));
        }
        @SubscribeEvent
        public static void onMagicItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
            IForgeRegistry<Item> registry = itemRegistryEvent.getRegistry();
            Item dominionBerry = new ItemNameBlockItem(BlockRegistry.DOMINION_BERRY_BUSH, MagicItemsRegistry.defaultItemProperties().food(MagicItemsRegistry.DOMINION_BERRY_FOOD)).setRegistryName(LibItemNames.DOMINION_BERRY);
            Item bilisBerry = new ItemNameBlockItem(BlockRegistry.SPECTABILIS_BUSH, MagicItemsRegistry.defaultItemProperties().food(MagicItemsRegistry.BILIS_BERRY_FOOD)).setRegistryName(LibItemNames.BILIS_BERRY);

            ComposterBlock.COMPOSTABLES.putIfAbsent(dominionBerry, 0.3f);
            ComposterBlock.COMPOSTABLES.putIfAbsent(bilisBerry, 0.3f);
            registry.register(dominionBerry);
            registry.register(bilisBerry);

            registry.register(getDefaultBlockItem(BlockRegistry.DOMINION_GEM_BLOCK, LibBlockNames.DOMINION_GEM_BLOCK));
            registry.register(new BlockItem(BlockRegistry.DOMINION_JAR, MagicItemsRegistry.defaultItemProperties()).setRegistryName(LibBlockNames.DOMINION_JAR));
            registry.register(new BlockItem(BlockRegistry.CREATIVE_DOMINION_JAR, MagicItemsRegistry.defaultItemProperties()).setRegistryName(LibBlockNames.CREATIVE_DOMINION_JAR));
            registry.register(new BlockItem(BlockRegistry.ICHOR_JAR, MagicItemsRegistry.defaultItemProperties()).setRegistryName(LibBlockNames.ICHOR_JAR));
            registry.register(new BlockItem(BlockRegistry.CREATIVE_ICHOR_JAR, MagicItemsRegistry.defaultItemProperties()).setRegistryName(LibBlockNames.CREATIVE_ICHOR_JAR));
            registry.register(new BlockItem(BlockRegistry.DECAYING_BLOCK, MagicItemsRegistry.defaultItemProperties()).setRegistryName(LibBlockNames.DECAYING_BLOCK));
            registry.register(new BlockItem(BlockRegistry.LIGHT_BLOCK, new Item.Properties()).setRegistryName(LibBlockNames.LIGHT_BLOCK));
            registry.register(new BlockItem(BlockRegistry.PORTAL_BLOCK, new Item.Properties()).setRegistryName(LibBlockNames.PORTAL));

            registry.register(new RendererBlockItem(BlockRegistry.RITUAL_BLOCK, MagicItemsRegistry.defaultItemProperties()) {
                @Override
                public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() { return RitualVesselRenderer::getISTER; }
            }.setRegistryName(LibBlockNames.RITUAL_VESSEL));

            registry.register(new RendererBlockItem(BlockRegistry.TABLE_BLOCK, MagicItemsRegistry.defaultItemProperties()) {
                @Override
                public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() { return TableRenderer::getISTER; }
            }.setRegistryName(LibBlockNames.TABLE_BLOCK));
//            registry.register(new FluidBlockItem(BlockRegistry.RE_LILLY_PAD, MagicItemsRegistry.defaultItemProperties().fireResistant()).setRegistryName(LibBlockNames.RE_LILLY_PAD));

            Item hangingCadaverSkull = new RendererBlockItem(BlockRegistry.hangingCadaverSkull, new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP).fireResistant()) {
                @Override
                public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() { return HangingSkullRenderer::getISTER; }
                @Override public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List< Component > tooltip, TooltipFlag flagIn) {
                    if (Screen.hasShiftDown()) {
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".hanging_cadaver_skull"));
                    } else {
                        tooltip.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".hold_shift").setStyle(Style.EMPTY)); }
                    super.appendHoverText(stack, worldIn, tooltip, flagIn);
                }
            }.setRegistryName(LibBlockNames.HANGING_CADAVER_SKULL);

            Item relayDeposit = new RendererBlockItem(BlockRegistry.RELAY_DEPOSIT, new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP).fireResistant()) {
                @Override
                public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() { return RelayDepositRenderer::getISTER; }
                @Override public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List< Component > tooltip, TooltipFlag flagIn) {
                    if (Screen.hasShiftDown()) {
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".relay_deposit"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".relay_deposit2"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".relay_deposit3"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".relay_deposit4"));
                    } else {
                        tooltip.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".hold_shift").setStyle(Style.EMPTY)); }
                    super.appendHoverText(stack, worldIn, tooltip, flagIn);
                }
            }.setRegistryName(LibBlockNames.RELAY_DEPOSIT);

            Item relaySplitter = new RendererBlockItem(BlockRegistry.RELAY_SPLITTER, new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP).fireResistant()) {
                @Override
                public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() { return RelaySplitterRenderer::getISTER; }
                @Override public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List< Component > tooltip, TooltipFlag flagIn) {
                    if (Screen.hasShiftDown()) {
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".relay_splitter"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".relay_splitter2"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".relay_splitter3"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".relay_splitter4"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".relay_splitter5"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".relay_splitter6"));
                    } else {
                        tooltip.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".hold_shift").setStyle(Style.EMPTY)); }
                    super.appendHoverText(stack, worldIn, tooltip, flagIn);
                }
            }.setRegistryName(LibBlockNames.RELAY_SPLITTER);

            Item emorticRelay = new RendererBlockItem(BlockRegistry.EMORTIC_RELAY, new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP).fireResistant()) {
                @Override
                public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() { return EmorticRelayRenderer::getISTER; }
                @Override public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List< Component > tooltip, TooltipFlag flagIn) {
                    if (Screen.hasShiftDown()) {
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".emortic_relay"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".emortic_relay2"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".emortic_relay3"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".emortic_relay4"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".emortic_relay5"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".emortic_relay6"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".emortic_relay7"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".emortic_relay8"));
                    } else {
                        tooltip.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".hold_shift").setStyle(Style.EMPTY)); }
                    super.appendHoverText(stack, worldIn, tooltip, flagIn);
                }
            }.setRegistryName(LibBlockNames.EMORTIC_RELAY);

            Item emorticCortex = new RendererBlockItem(BlockRegistry.EMORTIC_CORTEX_BLOCK, new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP).fireResistant()) {
                @Override
                public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() { return EmorticCortexRenderer::getISTER; }
                @Override public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List< Component > tooltip, TooltipFlag flagIn) {
                if (Screen.hasShiftDown()) {
                    tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".emortic_cortex"));
                    tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".emortic_cortex2"));
                    tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".emortic_cortex3"));
                } else {
                    tooltip.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".hold_shift").setStyle(Style.EMPTY)); }
                    super.appendHoverText(stack, worldIn, tooltip, flagIn);
                }
            }.setRegistryName(LibBlockNames.EMORTIC_CORTEX);

            Item ichorExtractor = new RendererBlockItem(BlockRegistry.ICHOR_EXTRACTOR_BLOCK, new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP).fireResistant()) {
                @Override
                public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() { return IchorExtractorRenderer::getISTER; }
                @Override public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List< Component > tooltip, TooltipFlag flagIn) {
                    if (Screen.hasShiftDown()) {
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".ichor_extractor"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".ichor_extractor2"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".ichor_extractor3"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".ichor_extractor4"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".ichor_extractor5"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".ichor_extractor6"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".ichor_extractor7"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".ichor_extractor8"));
                    } else {
                        tooltip.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".hold_shift").setStyle(Style.EMPTY)); }
                    super.appendHoverText(stack, worldIn, tooltip, flagIn);
                }
            }.setRegistryName(LibBlockNames.ICHOR_EXTRACTOR);

            Item cipherBlock = new RendererBlockItem(BlockRegistry.PSYGLYPHIC_CIPHER, new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP).fireResistant()) {
                @Override
                public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() {
                    return CipherRenderer::getISTER;
                }
                @Override public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List< Component > tooltip, TooltipFlag flagIn) {
                    if (Screen.hasShiftDown()) {
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".psyglyphic_cipher"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".psyglyphic_cipher2"));
                    } else {
                        tooltip.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".hold_shift").setStyle(Style.EMPTY)); }
                    super.appendHoverText(stack, worldIn, tooltip, flagIn);
                }
            }.setRegistryName(LibBlockNames.PSYGLYPHIC_CIPHER);

            Item ichorCrystallizer = new RendererBlockItem(BlockRegistry.DOMINION_CRYSTALLIZER_BLOCK, new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP).fireResistant()) {
                @Override
                public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() { return DominionCrystallizerRenderer::getISTER; }
                @Override public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List< Component > tooltip, TooltipFlag flagIn) {
                    if (Screen.hasShiftDown()) {
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".dominion_crystallizer"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".dominion_crystallizer2"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".dominion_crystallizer3"));
                    } else {
                        tooltip.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".hold_shift").setStyle(Style.EMPTY)); }
                    super.appendHoverText(stack, worldIn, tooltip, flagIn);
                }
            }.setRegistryName(LibBlockNames.DOMINION_CRYSTALLIZER);

            Item craftingPress = new RendererBlockItem(BlockRegistry.EMORTIC_CRAFTING_PRESS_BLOCK, new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP).fireResistant()) {
                @Override
                public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() { return EmorticCraftingPressRenderer::getISTER; }
                @Override public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List< Component > tooltip, TooltipFlag flagIn) {
                    if (Screen.hasShiftDown()) {
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".crafting_press"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".crafting_press2"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".crafting_press3"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".crafting_press4"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".crafting_press5"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".crafting_press6"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".crafting_press7"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".crafting_press8"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".crafting_press9"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".crafting_press10"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".crafting_press11"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".crafting_press12"));
                        tooltip.add(new TranslatableComponent("tooltip.block." + EmortisConstants.MOD_ID + ".crafting_press13"));
                    } else {
                        tooltip.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".hold_shift").setStyle(Style.EMPTY)); }
                    super.appendHoverText(stack, worldIn, tooltip, flagIn);
                }
            }.setRegistryName(LibBlockNames.EMORTIC_CRAFTING_PRESS);

            registry.register(hangingCadaverSkull);
            registry.register(emorticRelay);
            registry.register(relaySplitter);
            registry.register(relayDeposit);
            registry.register(cipherBlock);
            registry.register(ichorExtractor);
            registry.register(ichorCrystallizer);
            registry.register(emorticCortex);
            registry.register(registerTooltipBlockItem(LibBlockNames.PSYGLYPHIC_AMALGAMATOR, BlockRegistry.PSYGLYPHIC_AMALG_BLOCK, LibBlockNames.PSYGLYPHIC_AMALGAMATOR, "psyglyphic_amalgamator2"));
            registry.register(registerTooltipBlockItem(LibBlockNames.SPLINTERED_PEDESTAL, BlockRegistry.SPLINTERED_PEDESTAL, LibBlockNames.SPLINTERED_PEDESTAL,"splintered_pedestal2"));
            registry.register(craftingPress);

            registry.register(registerSingleTooltipBlockItem(LibBlockNames.BLOCK_OF_ESOTERICUM, BlockRegistry.BLOCK_OF_ESOTERICUM, LibBlockNames.BLOCK_OF_ESOTERICUM));
            registry.register(registerSingleTooltipBlockItem(LibBlockNames.SANDESITE, BlockRegistry.SANDESITE, LibBlockNames.SANDESITE));
            registry.register(registerSingleTooltipBlockItem(LibBlockNames.FORTIFIED_SANDESITE, BlockRegistry.FORTIFIED_SANDESITE, LibBlockNames.FORTIFIED_SANDESITE));
            registry.register(registerSingleTooltipBlockItem(LibBlockNames.PINK_SALT, BlockRegistry.PINK_SALT, LibBlockNames.PINK_SALT));
            registry.register(registerSingleTooltipBlockItem(LibBlockNames.HIMALAYAN_SALT, BlockRegistry.HIMALAYAN_SALT, LibBlockNames.HIMALAYAN_SALT));
            registry.register(registerSingleTooltipBlockItem(LibBlockNames.DWELLER_BRAIN, BlockRegistry.DWELLER_BRAIN, LibBlockNames.DWELLER_BRAIN));
            registry.register(registerSingleTooltipBlockItem(LibBlockNames.CADAVER_SKULL, BlockRegistry.CADAVER_SKULL, LibBlockNames.CADAVER_SKULL));

            Item fragmentedCobblestone = new BlockItem(BlockRegistry.FRAGMENTED_COBBLESTONE, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.COBBLESTONE);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
                    if (Screen.hasShiftDown()) {
                        tooltip.add(new TranslatableComponent("tooltip.block.rigoranthusemortisreborn.fragmented_cobblestone"));
                        tooltip.add(new TranslatableComponent("tooltip.block.rigoranthusemortisreborn.fragmented_cobblestone2"));
                    } else { tooltip.add(new TranslatableComponent("tooltip.rigoranthusemortisreborn.hold_shift").setStyle(Style.EMPTY)); }
                    super.appendHoverText(stack, worldIn, tooltip, flagIn);
                }
            }.setRegistryName(LibBlockNames.FRAGMENTED_COBBLESTONE);

            Item fragmentedNetherrack = new BlockItem(BlockRegistry.FRAGMENTED_NETHERRACK, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.NETHERRACK);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
                    if (Screen.hasShiftDown()) {
                        tooltip.add(new TranslatableComponent("tooltip.block.rigoranthusemortisreborn.fragmented_netherrack"));
                        tooltip.add(new TranslatableComponent("tooltip.block.rigoranthusemortisreborn.fragmented_netherrack2"));
                    } else { tooltip.add(new TranslatableComponent("tooltip.rigoranthusemortisreborn.hold_shift").setStyle(Style.EMPTY)); }
                    super.appendHoverText(stack, worldIn, tooltip, flagIn);
                }
            }.setRegistryName(LibBlockNames.FRAGMENTED_NETHERRACK);

            Item abyssalite = new BlockItem(BlockRegistry.ABYSSALITE, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.OBSIDIAN);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
                    if (Screen.hasShiftDown()) { tooltip.add(new TranslatableComponent("tooltip.block.rigoranthusemortisreborn.abyssalite")); }
                    else { tooltip.add(new TranslatableComponent("tooltip.rigoranthusemortisreborn.hold_shift").setStyle(Style.EMPTY)); }
                    super.appendHoverText(stack, worldIn, tooltip, flagIn);
                }
            }.setRegistryName(LibBlockNames.ABYSSALITE);

            Item oxylite = new BlockItem(BlockRegistry.OXYLITE, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.OBSIDIAN);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
                    if (Screen.hasShiftDown()) { tooltip.add(new TranslatableComponent("tooltip.block.rigoranthusemortisreborn.oxylite")); }
                    else { tooltip.add(new TranslatableComponent("tooltip.rigoranthusemortisreborn.hold_shift").setStyle(Style.EMPTY)); }
                    super.appendHoverText(stack, worldIn, tooltip, flagIn);
                }
            }.setRegistryName(LibBlockNames.OXYLITE);

            Item soulCoal = new BlockItem(BlockRegistry.SOUL_COAL_BLOCK, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.COAL_BLOCK);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
                    if (Screen.hasShiftDown()) { tooltip.add(new TranslatableComponent("tooltip.block.rigoranthusemortisreborn.soul_coal_block")); }
                    else { tooltip.add(new TranslatableComponent("tooltip.rigoranthusemortisreborn.hold_shift").setStyle(Style.EMPTY)); }
                    super.appendHoverText(stack, worldIn, tooltip, flagIn);
                }
            }.setRegistryName(LibBlockNames.SOUL_COAL_BLOCK);

            Item opulentMagma = new BlockItem(BlockRegistry.OPULENT_MAGMA, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.MAGMA_BLOCK);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
                    if (Screen.hasShiftDown()) { tooltip.add(new TranslatableComponent("tooltip.block.rigoranthusemortisreborn.opulent_magma")); }
                    else { tooltip.add(new TranslatableComponent("tooltip.rigoranthusemortisreborn.hold_shift").setStyle(Style.EMPTY)); }
                    super.appendHoverText(stack, worldIn, tooltip, flagIn);
                }
            }.setRegistryName(LibBlockNames.OPULENT_MAGMA);

            Item goldAmalgam = new BlockItem(BlockRegistry.GOLD_AMALGAM, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.GILDED_BLACKSTONE);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
                    if (Screen.hasShiftDown()) { tooltip.add(new TranslatableComponent("tooltip.block.rigoranthusemortisreborn.gold_amalgam")); }
                    else { tooltip.add(new TranslatableComponent("tooltip.rigoranthusemortisreborn.hold_shift").setStyle(Style.EMPTY)); }
                    super.appendHoverText(stack, worldIn, tooltip, flagIn);
                }
            }.setRegistryName(LibBlockNames.GOLD_AMALGAM);

            Item azulorealOrchid = new BlockItem(BlockRegistry.AZULOREAL_ORCHID, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.LILY_OF_THE_VALLEY);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.AZULOREAL_ORCHID);
            Item iridescentSprouts = new BlockItem(BlockRegistry.IRIDESCENT_SPROUTS, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.WARPED_ROOTS);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.IRIDESCENT_SPROUTS);
            Item lisianthus = new BlockItem(BlockRegistry.LISIANTHUS, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.PEONY);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.LISIANTHUS);

            Item jessicLeaf = new BlockItem(BlockRegistry.JESSIC_LEAVES, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.FLOWERING_AZALEA_LEAVES);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.JESSIC_LEAVES);
            Item jessicSapling = new BlockItem(BlockRegistry.JESSIC_SAPLING, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.DARK_OAK_SAPLING);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.JESSIC_SAPLING);
            Item jessicLog = new BlockItem(BlockRegistry.JESSIC_LOG, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.WARPED_STEM);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.JESSIC_LOG);
            Item jessicWood = new BlockItem(BlockRegistry.JESSIC_WOOD, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.WARPED_HYPHAE);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.JESSIC_WOOD);
            Item strippedJessicLog = new BlockItem(BlockRegistry.STRIPPED_JESSIC_LOG, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.STRIPPED_WARPED_STEM);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.STRIPPED_JESSIC_LOG);
            Item strippedJessicWood = new BlockItem(BlockRegistry.STRIPPED_JESSIC_WOOD, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.STRIPPED_WARPED_HYPHAE);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.STRIPPED_JESSIC_WOOD);
            Item jessicSlab = new BlockItem(BlockRegistry.JESSIC_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.WARPED_SLAB);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.JESSIC_SLAB);
            Item jessicStair = new BlockItem(BlockRegistry.JESSIC_STAIRS, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.WARPED_STAIRS);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.JESSIC_STAIRS);
            Item jessicFence = new BlockItem(BlockRegistry.JESSIC_FENCE, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.WARPED_FENCE);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.JESSIC_FENCE);
            Item jessicFenceGate = new BlockItem(BlockRegistry.JESSIC_FENCE_GATE, new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.WARPED_FENCE_GATE);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.JESSIC_FENCE_GATE);
            Item jessicDoor = new BlockItem(BlockRegistry.JESSIC_DOOR, new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.WARPED_DOOR);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.JESSIC_DOOR);
            Item jessicTrapdoor = new BlockItem(BlockRegistry.JESSIC_TRAPDOOR, new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.WARPED_TRAPDOOR);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.JESSIC_TRAPDOOR);
            Item jessicPressurePlate = new BlockItem(BlockRegistry.JESSIC_PRESSURE_PLATE, new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.WARPED_PRESSURE_PLATE);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.JESSIC_PRESSURE_PLATE);
            Item jessicButton = new BlockItem(BlockRegistry.JESSIC_BUTTON, new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.WARPED_BUTTON);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.JESSIC_BUTTON);
            Item jessicLamp = new BlockItem(BlockRegistry.JESSIC_LAMP, new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.REDSTONE_LAMP);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.JESSIC_LAMP);


            Item azulorealLeaf = new BlockItem(BlockRegistry.AZULOREAL_LEAVES, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.FLOWERING_AZALEA_LEAVES);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.AZULOREAL_LEAVES);
            Item azulorealSapling = new BlockItem(BlockRegistry.AZULOREAL_SAPLING, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.DARK_OAK_SAPLING);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.AZULOREAL_SAPLING);
            Item azulorealLog = new BlockItem(BlockRegistry.AZULOREAL_LOG, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.WARPED_STEM);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.AZULOREAL_LOG);
            Item azulorealWood = new BlockItem(BlockRegistry.AZULOREAL_WOOD, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.WARPED_HYPHAE);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.AZULOREAL_WOOD);
            Item strippedAzulorealLog = new BlockItem(BlockRegistry.STRIPPED_AZULOREAL_LOG, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.STRIPPED_WARPED_STEM);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.STRIPPED_AZULOREAL_LOG);
            Item strippedAzulorealWood = new BlockItem(BlockRegistry.STRIPPED_AZULOREAL_WOOD, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.STRIPPED_WARPED_HYPHAE);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.STRIPPED_AZULOREAL_WOOD);
            Item azulorealSlab = new BlockItem(BlockRegistry.AZULOREAL_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.WARPED_SLAB);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.AZULOREAL_SLAB);
            Item azulorealStair = new BlockItem(BlockRegistry.AZULOREAL_STAIRS, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.WARPED_STAIRS);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.AZULOREAL_STAIRS);
            Item azulorealFence = new BlockItem(BlockRegistry.AZULOREAL_FENCE, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.WARPED_FENCE);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.AZULOREAL_FENCE);
            Item azulorealFenceGate = new BlockItem(BlockRegistry.AZULOREAL_FENCE_GATE, new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.WARPED_FENCE_GATE);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.AZULOREAL_FENCE_GATE);
            Item azulorealDoor = new BlockItem(BlockRegistry.AZULOREAL_DOOR, new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.WARPED_DOOR);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.AZULOREAL_DOOR);
            Item azulorealTrapdoor = new BlockItem(BlockRegistry.AZULOREAL_TRAPDOOR, new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.WARPED_TRAPDOOR);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.AZULOREAL_TRAPDOOR);
            Item azulorealPressurePlate = new BlockItem(BlockRegistry.AZULOREAL_PRESSURE_PLATE, new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.WARPED_PRESSURE_PLATE);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.AZULOREAL_PRESSURE_PLATE);
            Item azulorealButton = new BlockItem(BlockRegistry.AZULOREAL_BUTTON, new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.WARPED_BUTTON);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.AZULOREAL_BUTTON);
            Item azulorealLamp = new BlockItem(BlockRegistry.AZULOREAL_LAMP, new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)) {
                private final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.REDSTONE_LAMP);
                public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) { FILLER.fillItem(this.asItem(), group, items); }
            }.setRegistryName(LibBlockNames.AZULOREAL_LAMP);

            registry.register(fragmentedCobblestone);
            registry.register(fragmentedNetherrack);
            registry.register(abyssalite);
            registry.register(oxylite);
            registry.register(soulCoal);
            registry.register(opulentMagma);
            registry.register(goldAmalgam);

            registry.register(azulorealOrchid);
            registry.register(iridescentSprouts);
            registry.register(lisianthus);

            registry.register(azulorealSapling);
            registry.register(azulorealLeaf);
            registry.register(azulorealLog);
            registry.register(azulorealWood);
            registry.register(strippedAzulorealLog);
            registry.register(strippedAzulorealWood);
            registry.register(azulorealSlab);
            registry.register(azulorealStair);
            registry.register(azulorealFence);
            registry.register(azulorealFenceGate);
            registry.register(azulorealDoor);
            registry.register(azulorealTrapdoor);
            registry.register(azulorealPressurePlate);
            registry.register(azulorealButton);
            registry.register(azulorealLamp);

            registry.register(jessicSapling);
            registry.register(jessicLeaf);
            registry.register(jessicLog);
            registry.register(jessicWood);
            registry.register(strippedJessicLog);
            registry.register(strippedJessicWood);
            registry.register(jessicSlab);
            registry.register(jessicStair);
            registry.register(jessicFence);
            registry.register(jessicFenceGate);
            registry.register(jessicDoor);
            registry.register(jessicTrapdoor);
            registry.register(jessicPressurePlate);
            registry.register(jessicButton);
            registry.register(jessicLamp);

////            registry.register(getDefaultBlockItem(BlockRegistry.JESSIC_PLANKS, LibBlockNames.JESSIC_PLANKS));
////            registry.register(getDefaultBlockItem(BlockRegistry.AZULOREAL_PLANKS, LibBlockNames.AZULOREAL_PLANKS));

            registry.register(getDefaultBlockItem(BlockRegistry.APOGEAN_NETHERITE_BLOCK, LibBlockNames.APOGEAN_NETHERITE_BLOCK));
            registry.register(getDefaultBlockItem(BlockRegistry.AQUEOUS_NETHERITE_BLOCK, LibBlockNames.AQUEOUS_NETHERITE_BLOCK));
            registry.register(getDefaultBlockItem(BlockRegistry.ATROPHYING_NETHERITE_BLOCK, LibBlockNames.ATROPHYING_NETHERITE_BLOCK));
            registry.register(getDefaultBlockItem(BlockRegistry.OPULENT_NETHERITE_BLOCK, LibBlockNames.OPULENT_NETHERITE_BLOCK));
            registry.register(getDefaultBlockItem(BlockRegistry.PERNICIOUS_NETHERITE_BLOCK, LibBlockNames.PERNICIOUS_NETHERITE_BLOCK));
            registry.register(getDefaultBlockItem(BlockRegistry.PHANTASMAL_NETHERITE_BLOCK, LibBlockNames.PHANTASMAL_NETHERITE_BLOCK));
            registry.register(getDefaultBlockItem(BlockRegistry.INCORPOREAL_NETHERITE_BLOCK, LibBlockNames.INCORPOREAL_NETHERITE_BLOCK));
            registry.register(getDefaultBlockItem(BlockRegistry.INFERNAL_NETHERITE_BLOCK, LibBlockNames.INFERNAL_NETHERITE_BLOCK));
            registry.register(getDefaultBlockItem(BlockRegistry.REMEX_NETHERITE_BLOCK, LibBlockNames.REMEX_NETHERITE_BLOCK));
        }

        public static Item getDefaultBlockItem(Block block, String registry) { return new BlockItem(block, MagicItemsRegistry.defaultItemProperties()).setRegistryName(registry); }

        private static Item registerTooltipBlockItem(String name, Block block, String tooltipKey, String tooltipKey2) {
            return new BlockItem(block, new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP)) {
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
                    if (Screen.hasShiftDown()) {
                        tooltip.add(new TranslatableComponent("tooltip.block.rigoranthusemortisreborn." + tooltipKey));
                        tooltip.add(new TranslatableComponent("tooltip.block.rigoranthusemortisreborn." + tooltipKey2)); }
                    else { tooltip.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".hold_shift").setStyle(Style.EMPTY)); }
                    super.appendHoverText(stack, worldIn, tooltip, flagIn);
                }
            }.setRegistryName(name);
        }
        private static Item registerSingleTooltipBlockItem(String name, Block block, String tooltipKey) {
            return new BlockItem(block, new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP)) {
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
                    if (Screen.hasShiftDown()) { tooltip.add(new TranslatableComponent("tooltip.block.rigoranthusemortisreborn." + tooltipKey)); }
                    else { tooltip.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".hold_shift").setStyle(Style.EMPTY)); }
                    super.appendHoverText(stack, worldIn, tooltip, flagIn);
                }
            }.setRegistryName(name);
        }
        private static Item registerSoulCoalBlockItem(String name, Block block, String tooltipKey) {
            return new BlockItem(block, new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP)) {
                @Override
                public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
                    int coalBurnTime = Config.soulCoalBurnTime.get();
                    return coalBurnTime * 11;
                }
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
                    if (Screen.hasShiftDown()) { tooltip.add(new TranslatableComponent("tooltip.block.rigoranthusemortisreborn." + tooltipKey)); }
                    else { tooltip.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".hold_shift").setStyle(Style.EMPTY)); }
                    super.appendHoverText(stack, worldIn, tooltip, flagIn);
                }
            }.setRegistryName(name);
        }
        private static Item registerTooltipAnimBlockItem(String name, Block block, String tooltipKey) {
            return new AnimBlockItem(block, new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP)) {
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
                    if (Screen.hasShiftDown()) { tooltip.add(new TranslatableComponent(tooltipKey)); }
                    else { tooltip.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".hold_shift").setStyle(Style.EMPTY)); }
                    super.appendHoverText(stack, worldIn, tooltip, flagIn);
                }
            }.setRegistryName(name);
        }
        @SubscribeEvent
        public static void registerBlockProvider(final RegistryEvent.Register<BlockStateProviderType<?>> e) {
            e.getRegistry().register(new BlockStateProviderType<>(SupplierBlockStateProvider.CODEC).setRegistryName(EmortisConstants.MOD_ID, LibBlockNames.STATE_PROVIDER));
        }
    }
    private static Boolean allowsSpawnOnLeaves(BlockState state, BlockGetter reader, BlockPos pos, EntityType<?> entity) { return entity == EntityType.OCELOT || entity == EntityType.PARROT; }
    private static boolean isntSolid(BlockState state, BlockGetter reader, BlockPos pos) {
        return false;
    }
}