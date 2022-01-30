package com.platinumg17.rigoranthusemortisreborn;

import com.platinumg17.rigoranthusemortisreborn.addon.AddonManager;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.FoodHandler;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.InteractionHandler;
import com.platinumg17.rigoranthusemortisreborn.blocks.DecorativeOrStorageBlocks;
import com.platinumg17.rigoranthusemortisreborn.blocks.tileentity.smeltery.RigoranthusTileEntities;
import com.platinumg17.rigoranthusemortisreborn.canis.*;
import com.platinumg17.rigoranthusemortisreborn.canis.client.ClientSetup;
import com.platinumg17.rigoranthusemortisreborn.canis.client.data.REBlockstateProvider;
import com.platinumg17.rigoranthusemortisreborn.canis.client.data.REItemModelProvider;
import com.platinumg17.rigoranthusemortisreborn.canis.client.event.ClientEventHandler;
import com.platinumg17.rigoranthusemortisreborn.canis.common.Capabilities;
import com.platinumg17.rigoranthusemortisreborn.canis.common.SpecializedEntityTypes;
import com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.CanisEventHandler;
import com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.CanisPacketHandler;
import com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data.REBlockTagsProvider;
import com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data.REItemTagsProvider;
import com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data.RERecipeProvider;
import com.platinumg17.rigoranthusemortisreborn.canis.common.commands.CanisReviveCommand;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.HelmetInteractionHandler;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.MeatFoodHandler;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.accouterments.CanisAccouterments;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.accouterments.CanisAccoutrementTypes;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.CanisBedMaterials;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.canis.common.skill.ChungusPupperSkill;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.CanisRecipeSerializers;
import com.platinumg17.rigoranthusemortisreborn.config.Config;
import com.platinumg17.rigoranthusemortisreborn.core.events.advancements.REAdvancementProvider;
import com.platinumg17.rigoranthusemortisreborn.core.events.other.VanillaCompatRigoranthus;
import com.platinumg17.rigoranthusemortisreborn.core.init.ItemInit;
import com.platinumg17.rigoranthusemortisreborn.core.init.Registration;
import com.platinumg17.rigoranthusemortisreborn.core.init.network.REPacketHandler;
import com.platinumg17.rigoranthusemortisreborn.core.init.network.messages.Messages;
import com.platinumg17.rigoranthusemortisreborn.core.registry.RigoranthusSoundRegistry;
import com.platinumg17.rigoranthusemortisreborn.core.registry.fluid.FluidRegistry;
import com.platinumg17.rigoranthusemortisreborn.core.registry.fluid.particles.EmortisParticleTypes;
import com.platinumg17.rigoranthusemortisreborn.magica.IProxy;
import com.platinumg17.rigoranthusemortisreborn.magica.TextureEvent;
import com.platinumg17.rigoranthusemortisreborn.magica.client.ClientHandler;
import com.platinumg17.rigoranthusemortisreborn.magica.common.network.Networking;
import com.platinumg17.rigoranthusemortisreborn.magica.common.potions.ModPotions;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.APIRegistry;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.ClientProxy;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.ModSetup;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.ServerProxy;
import com.platinumg17.rigoranthusemortisreborn.world.WorldEvent;
import com.platinumg17.rigoranthusemortisreborn.world.gen.EmortisBiomeGen;
import com.platinumg17.rigoranthusemortisreborn.world.trees.RigoranthusWoodTypes;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.platinumg17.rigoranthusemortisreborn.magica.common.datagen.DungeonLootGenerator.GLM;

@Mod(EmortisConstants.MOD_ID)
@Mod.EventBusSubscriber(modid = EmortisConstants.MOD_ID, bus = Bus.MOD)
public class RigoranthusEmortisReborn {
    public static IProxy proxy = DistExecutor.runForDist(()-> () -> new ClientProxy(), () -> ()-> new ServerProxy());
	public static final Logger LOGGER = LogManager.getLogger(EmortisConstants.MOD_ID);
	public static final String MOD_ID = "rigoranthusemortisreborn";
    public static final RegistryHelper REGISTRY_HELPER = RegistryHelper.create(MOD_ID, helper -> {});
    public static final SimpleChannel HANDLER = NetworkRegistry.ChannelBuilder.named(EmortisConstants.CHANNEL_NAME)
            .clientAcceptedVersions(EmortisConstants.PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(EmortisConstants.PROTOCOL_VERSION::equals)
            .networkProtocolVersion(EmortisConstants.PROTOCOL_VERSION::toString)
            .simpleChannel();
    public static boolean caelusLoaded = false;
    public static CreativeModeTab RIGORANTHUS_EMORTIS_GROUP = new CreativeModeTab(EmortisConstants.MOD_ID) {
        @Override public ItemStack makeIcon() {
            return new ItemStack(ItemInit.PACT_OF_SERVITUDE.get());
        }
    };

	public RigoranthusEmortisReborn() {
        caelusLoaded = ModList.get().isLoaded("caelus");
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
//        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.CONFIG_SERVER_SPEC);
//        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.CONFIG_SERVER_SPEC);
//        Config.loadConfig(Config.CONFIG_SERVER_SPEC, FMLPaths.CONFIGDIR.get().resolve("rigoranthusemortisreborn/RigoranthusEmortisReborn-common.toml"));
        Config.init(modEventBus);
        REGISTRY_HELPER.register(modEventBus);
        modEventBus.addListener(this::gatherData);                  	modEventBus.addListener(this::enqueueIMC);
		modEventBus.addListener(this::setup);                           modEventBus.addListener(this::processIMC);
        modEventBus.register(Registration.class);                       modEventBus.addListener(CanisRegistries::newRegistry);
        modEventBus.addListener(Capabilities::registerCaps);            modEventBus.addListener(SpecializedEntityTypes::addEntityAttributes);

        APIRegistry.registerSpells();
        RigoranthusTileEntities.register(modEventBus);
        Registration.init();                                            FluidRegistry.register(modEventBus);
        ItemInit.ITEMS.register(modEventBus);                           RigoranthusSoundRegistry.SOUND_EVENTS.register(modEventBus);
        EmortisParticleTypes.PARTICLES.register(modEventBus);           CanisRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        CanisBlocks.BLOCKS.register(modEventBus);                       CanisItems.ITEMS.register(modEventBus);
        CanisTileEntityTypes.TILE_ENTITIES.register(modEventBus);       SpecializedEntityTypes.ENTITIES.register(modEventBus);
        CanisSerializers.SERIALIZERS.register(modEventBus);             CanisContainerTypes.CONTAINERS.register(modEventBus);
        CanisSkills.SKILLS.register(modEventBus);                       CanisAttributes.ATTRIBUTES.register(modEventBus);
        CanisAccouterments.ACCOUTERMENTS.register(modEventBus);         CanisAccoutrementTypes.ACCOUTREMENT_TYPE.register(modEventBus);
        CanisBedMaterials.CASINGS.register(modEventBus);                CanisBedMaterials.BEDDINGS.register(modEventBus);

        Messages.registerMessages("rigoranthusemortisreborn_network");
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        forgeEventBus.addListener(this::onServerStarting);
        forgeEventBus.addListener(this::registerCommands);
        forgeEventBus.register(new CanisEventHandler());

//        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> Mod.EventBusSubscriber.Bus.FORGE.bus().get().register(PathClientEventHandler.class));
//        Mod.EventBusSubscriber.Bus.FORGE.bus().get().register(PathFMLEventHandler.class);
        ModSetup.initGeckolib();
        //  Client Events  //
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            modEventBus.addListener(this::doClientStuff);   //            modEventBus.addListener(CanisBlocks::registerBlockColors);
            modEventBus.addListener(CanisItems::registerItemColors);
            modEventBus.addListener(ClientSetup::setupTileEntityRenderers);
//            modEventBus.addListener(ClientHandler::registerRenderers);
//            modEventBus.addListener(ClientSetup::setupEntityRenderers);
            modEventBus.addListener(ClientEventHandler::onModelBakeEvent);
            forgeEventBus.register(new ClientEventHandler());
        });
        GLM.register(FMLJavaModLoadingContext.get().getModEventBus());
        AddonManager.init();
    }
    @SubscribeEvent public static void registerItems(RegistryEvent.Register<Item> event) {Registration.registerItems(event);}

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Networking.registerMessages();
            BlockEntityRenderers.register(RigoranthusTileEntities.RE_SIGN_ENTITIES.get(), SignRenderer::new);
            Sheets.addWoodType(RigoranthusWoodTypes.AZULOREAL);    Sheets.addWoodType(RigoranthusWoodTypes.JESSIC);
            VanillaCompatRigoranthus.registerCompostables();       VanillaCompatRigoranthus.registerFlammables();
            VanillaCompatRigoranthus.registerDispenserBehaviors(); REPacketHandler.setupChannel();
            ModPotions.addRecipes();                               CanisReviveCommand.registerSerializers();
            CanisPacketHandler.init();                             InteractionHandler.registerHandler(new HelmetInteractionHandler());
            FoodHandler.registerHandler(new MeatFoodHandler());    FoodHandler.registerDynPredicate(ChungusPupperSkill.INNER_DYN_PRED);
            Config.initSkillConfig();                              CanisEntity.initEntityDataAccessors();
            WorldEvent.registerFeatures();                         EmortisBiomeGen.addBiomeTypes();
//            EmortisSurfaceBuilder.Configured.registerConfiguredSurfaceBuilders();
            if (Config.verdurousWoodlandsSpawnWeight.get() > 0) {
//                BiomeProviders.register(new WoodlandBiomeProvider(new ResourceLocation(MOD_ID, "biome_provider"), 2));
                BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(EmortisBiomeGen.verdurousWoodlandsKey, Config.verdurousWoodlandsSpawnWeight.get()));}
            if (Config.verdurousFieldsSpawnWeight.get() > 0) {
                BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(EmortisBiomeGen.verdurousFieldsKey, Config.verdurousFieldsSpawnWeight.get()));}
        });
    }
    @SubscribeEvent
    public void onServerStarting(final ServerStartingEvent event) { LOGGER.info("Ah baby! Da mod man make a message thing!"); LOGGER.info("Wait... If I change the changelog, do I have to put that in the Changelog?"); }
    public void registerCommands(final RegisterCommandsEvent event) { CanisReviveCommand.register(event.getDispatcher()); }

    @OnlyIn(Dist.CLIENT)
    public void doClientStuff(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ClientSetup.setupScreenManagers(event);
            ClientSetup.setupCollarRenderers(event);
            makeBow(ItemInit.BONE_BOW.get());
            WoodType.register(RigoranthusWoodTypes.AZULOREAL);
            WoodType.register(RigoranthusWoodTypes.JESSIC);
            ItemBlockRenderTypes.setRenderLayer(DecorativeOrStorageBlocks.JESSIC_LEAF_CARPET.get(), RenderType.cutoutMipped());
            ItemBlockRenderTypes.setRenderLayer(DecorativeOrStorageBlocks.JESSIC_POST.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(DecorativeOrStorageBlocks.STRIPPED_JESSIC_POST.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(DecorativeOrStorageBlocks.JESSIC_HEDGE.get(), RenderType.cutoutMipped());
            ItemBlockRenderTypes.setRenderLayer(DecorativeOrStorageBlocks.AZULOREAL_LEAF_CARPET.get(), RenderType.cutoutMipped());
            ItemBlockRenderTypes.setRenderLayer(DecorativeOrStorageBlocks.AZULOREAL_POST.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(DecorativeOrStorageBlocks.STRIPPED_AZULOREAL_POST.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(DecorativeOrStorageBlocks.AZULOREAL_HEDGE.get(), RenderType.cutoutMipped());

//            ItemBlockRenderTypes.setRenderLayer(DecorativeOrStorageBlocks.JESSIC_DOOR.get(), RenderType.cutout());
//            ItemBlockRenderTypes.setRenderLayer(DecorativeOrStorageBlocks.JESSIC_TRAPDOOR.get(), RenderType.cutout());
//            ItemBlockRenderTypes.setRenderLayer(DecorativeOrStorageBlocks.JESSIC_STAIRS.get(), RenderType.cutout());
//            ItemBlockRenderTypes.setRenderLayer(DecorativeOrStorageBlocks.JESSIC_SLAB.get(), RenderType.cutout());
//            ItemBlockRenderTypes.setRenderLayer(DecorativeOrStorageBlocks.AZULOREAL_DOOR.get(), RenderType.cutout());
//            ItemBlockRenderTypes.setRenderLayer(DecorativeOrStorageBlocks.AZULOREAL_TRAPDOOR.get(), RenderType.cutout());
//            ItemBlockRenderTypes.setRenderLayer(DecorativeOrStorageBlocks.AZULOREAL_STAIRS.get(), RenderType.cutout());
//            ItemBlockRenderTypes.setRenderLayer(DecorativeOrStorageBlocks.AZULOREAL_SLAB.get(), RenderType.cutout());
//            ItemBlockRenderTypes.setRenderLayer(Registration.LUMISHROOM.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Registration.MASTERFUL_SMELTERY.get(), RenderType.cutout());

            ItemBlockRenderTypes.setRenderLayer(FluidRegistry.CADAVEROUS_ICHOR_FLUID.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FluidRegistry.CADAVEROUS_ICHOR_BLOCK.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FluidRegistry.CADAVEROUS_ICHOR_FLOWING.get(), RenderType.translucent());
        });
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientHandler::registerRenderers);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientHandler::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(TextureEvent::textEvent);
    }
    private void makeBow(Item item) {
        ItemProperties.register(item, new ResourceLocation("pull"), (bow, world, entity, arg4) -> {if (entity == null) {return 0.0F;} else {return entity.getUseItem() != bow ? 0.0F : (float) (bow.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F;}});
        ItemProperties.register(item, new ResourceLocation("pulling"), (bow, world, entity, arg4) -> entity != null && entity.isUsingItem() && entity.getUseItem() == bow ? 1.0F : 0.0F);
    }
    private void enqueueIMC(final InterModEnqueueEvent event) {
        ModSetup.sendIntercoms();
    }

    protected void processIMC(final InterModProcessEvent event) {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        AddonManager.init();
    }

    private void gatherData(final GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        if (event.includeClient()) {
            REBlockstateProvider blockstates = new REBlockstateProvider(gen, event.getExistingFileHelper());
            gen.addProvider(blockstates);
            gen.addProvider(new REItemModelProvider(gen, blockstates.getExistingHelper()));
        }
        if (event.includeServer()) {
            gen.addProvider(new REAdvancementProvider(gen));
            REBlockTagsProvider blockTagProvider = new REBlockTagsProvider(gen, event.getExistingFileHelper());
            gen.addProvider(blockTagProvider);
            gen.addProvider(new REItemTagsProvider(gen, blockTagProvider, event.getExistingFileHelper()));
            gen.addProvider(new RERecipeProvider(gen));
        }
    }
    public static ResourceLocation rl(String path) {
        return new ResourceLocation(EmortisConstants.MOD_ID, path);
    }
}