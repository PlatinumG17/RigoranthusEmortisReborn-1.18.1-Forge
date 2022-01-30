package com.platinumg17.rigoranthusemortisreborn.magica.setup;


import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.items.armor.models.DwellerThoraxModel;
import com.platinumg17.rigoranthusemortisreborn.magica.IProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientProxy implements IProxy {

    public static final ModelLayerLocation DWELLER_THORAX_ARMOR_LAYER = new ModelLayerLocation(RigoranthusEmortisReborn.rl("dweller_thorax"), "main");
    public static DwellerThoraxModel DWELLER_THORAX_ARMOR_MODEL = null;

    @Override
    public void init() { }

    @Override
    public Level getClientWorld() {
        return Minecraft.getInstance().level;
    }

    @Override
    public Player getPlayer(){return Minecraft.getInstance().player;}

    @Override
    public Minecraft getMinecraft(){return Minecraft.getInstance();}


//    @SubscribeEvent
//    public static void setup(EntityRenderersEvent.RegisterRenderers e){ }

    @SubscribeEvent
    public static void layerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
//        event.registerLayerDefinition(SatchelModel.LAYER_LOCATION, SatchelModel::createBodyLayer);
        event.registerLayerDefinition(ClientProxy.DWELLER_THORAX_ARMOR_LAYER, DwellerThoraxModel::createBodyLayer);

    }
}