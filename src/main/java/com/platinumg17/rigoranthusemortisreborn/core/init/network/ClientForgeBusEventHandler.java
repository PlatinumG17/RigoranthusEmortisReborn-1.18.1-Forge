package com.platinumg17.rigoranthusemortisreborn.core.init.network;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.core.registry.fluid.FluidRegistry;
import com.platinumg17.rigoranthusemortisreborn.magica.common.potions.ModPotions;
import net.minecraft.client.Camera;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.FOVModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EmortisConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeBusEventHandler {

    @SubscribeEvent
    public static void getFogDensity(EntityViewRenderEvent.FogDensity event) {
        Camera info = event.getCamera();
        FluidState fluidState = info.getBlockAtCamera().getFluidState();//.level.getFluidState(info.blockPosition);
        if (fluidState.isEmpty())
            return;
        Fluid fluid = fluidState.getType();

        if (fluid.isSame(FluidRegistry.CADAVEROUS_ICHOR_FLUID.get())) {
            event.setDensity(10f);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void getFogColor(EntityViewRenderEvent.FogColors event) {
        Camera info = event.getCamera();
        FluidState fluidState = info.getBlockAtCamera().getFluidState();//.level.getFluidState(info.blockPosition);
        if (fluidState.isEmpty())
            return;
        Fluid fluid = fluidState.getType();

        if (fluid.isSame(FluidRegistry.CADAVEROUS_ICHOR_FLUID.get())) {
            event.setRed(48 / 256f);
            event.setGreen(4 / 256f);
            event.setBlue(4 / 256f);
        }
    }

    @SubscribeEvent
    public void onFOVUpdate(FOVModifierEvent event) {
        if (event.getEntity().hasEffect(ModPotions.NECROTIZING_FASCIITIS_EFFECT)) {
            event.setNewfov(1.0F);
        }
    }
}