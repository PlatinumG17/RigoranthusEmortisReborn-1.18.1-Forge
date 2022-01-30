package com.platinumg17.rigoranthusemortisreborn.canis.client;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.blocks.tileentity.gui.MasterfulSmelteryScreen;
import com.platinumg17.rigoranthusemortisreborn.canis.CanisContainerTypes;
import com.platinumg17.rigoranthusemortisreborn.canis.CanisTileEntityTypes;
import com.platinumg17.rigoranthusemortisreborn.canis.client.entity.render.CanisBeamRenderer;
import com.platinumg17.rigoranthusemortisreborn.canis.client.entity.render.CanisRenderer;
import com.platinumg17.rigoranthusemortisreborn.canis.client.entity.render.layer.CanisScreenOverlays;
import com.platinumg17.rigoranthusemortisreborn.canis.client.screen.CanisInventoriesScreen;
import com.platinumg17.rigoranthusemortisreborn.canis.client.screen.FoodBowlScreen;
import com.platinumg17.rigoranthusemortisreborn.canis.client.screen.TreatBagScreen;
import com.platinumg17.rigoranthusemortisreborn.canis.client.screen.WaywardTravellerScreen;
import com.platinumg17.rigoranthusemortisreborn.canis.client.tileentity.renderer.CanisBedRenderer;
import com.platinumg17.rigoranthusemortisreborn.canis.common.SpecializedEntityTypes;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.core.init.Registration;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = EmortisConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    public static final ModelLayerLocation CANIS = new ModelLayerLocation(RigoranthusEmortisReborn.rl("canis"), "main");
    public static final ModelLayerLocation CANIS_ARMOR = new ModelLayerLocation(RigoranthusEmortisReborn.rl( "canis"), "armor");
    public static final ModelLayerLocation CANIS_BACKPACK = new ModelLayerLocation(RigoranthusEmortisReborn.rl( "canis_satchel"), "main");
    public static final ModelLayerLocation CANIS_SAVIOR_BOX = new ModelLayerLocation(RigoranthusEmortisReborn.rl( "canis_savior_box"), "main");
    public static final ModelLayerLocation CANIS_BEAM = new ModelLayerLocation(RigoranthusEmortisReborn.rl( "canis"), "main");

    public static void setupScreenManagers(final FMLClientSetupEvent event) {
        MenuScreens.register(CanisContainerTypes.FOOD_BOWL.get(), FoodBowlScreen::new);
        MenuScreens.register(CanisContainerTypes.WAYWARD_TRAVELLER.get(), WaywardTravellerScreen::new);
        MenuScreens.register(CanisContainerTypes.TREAT_BAG.get(), TreatBagScreen::new);
        MenuScreens.register(CanisContainerTypes.CANIS_INVENTORIES.get(), CanisInventoriesScreen::new);
        MenuScreens.register(Registration.MASTERFUL_SMELTERY_CONTAINER.get(), MasterfulSmelteryScreen::new);
    }

    public static void setupEntityRenderers(final EntityRenderersEvent.RegisterLayerDefinitions event) {
//        event.registerLayerDefinition(CANIS, CanisModel::createBodyLayer);
//        event.registerLayerDefinition(CANIS_ARMOR, CanisModel::createArmorLayer);
//        event.registerLayerDefinition(CANIS_BACKPACK, CanisBackpackModel::createSatchelLayer);
//        event.registerLayerDefinition(CANIS_SAVIOR_BOX, CanisSaviorModel::createSaviorBoxLayer);
        // TODO: RenderingRegistry.registerEntityRenderingHandler(SpecializedEntityTypes.CANIS_BEAM.get(), manager -> new CanisBeamRenderer<>(manager, event.getMinecraftSupplier().get().getItemRenderer()));
    }

    public static void setupTileEntityRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(SpecializedEntityTypes.CANIS.get(), CanisRenderer::new);
        event.registerEntityRenderer(SpecializedEntityTypes.CANIS_BEAM.get(), CanisBeamRenderer::new);
        event.registerBlockEntityRenderer(CanisTileEntityTypes.CANIS_BED.get(), CanisBedRenderer::new);
    }

    public static void setupCollarRenderers(final FMLClientSetupEvent event) {
//        CollarRenderManager.registerLayer(DefaultAccoutrementRenderer::new);
//        CollarRenderManager.registerLayer(ArmorAccoutrementRenderer::new);
//        CollarRenderManager.registerLayer(WaywardTravellerRenderer::new);
//        CollarRenderManager.registerLayer(SaviorCanisRenderer::new);

        OverlayRegistry.registerOverlayTop(new TranslatableComponent("canisgui.food_level").toString(), CanisScreenOverlays.FOOD_LEVEL_ELEMENT);
        OverlayRegistry.registerOverlayTop(new TranslatableComponent("canisgui.air_level").toString(), CanisScreenOverlays.AIR_LEVEL_ELEMENT);
    }

//    public static void addClientReloadListeners(final RegisterClientReloadListenersEvent event) {
//        event.registerReloadListener(CanisTextureManager.INSTANCE);
//    }
}