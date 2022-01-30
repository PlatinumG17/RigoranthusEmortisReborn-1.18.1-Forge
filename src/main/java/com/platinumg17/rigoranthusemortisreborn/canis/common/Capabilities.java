package com.platinumg17.rigoranthusemortisreborn.canis.common;

import com.platinumg17.rigoranthusemortisreborn.canis.common.inventory.WaywardTravellerItemHandler;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class Capabilities {
    public static void registerCaps(final RegisterCapabilitiesEvent event) {
        event.register(WaywardTravellerItemHandler.class);
    }
}