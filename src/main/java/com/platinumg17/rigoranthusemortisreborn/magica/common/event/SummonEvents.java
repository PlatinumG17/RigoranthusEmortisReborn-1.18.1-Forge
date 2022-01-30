package com.platinumg17.rigoranthusemortisreborn.magica.common.event;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.event.SummonEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EmortisConstants.MOD_ID)
public class SummonEvents {

    @SubscribeEvent
    public static void summonedEvent(SummonEvent event){ }

    @SubscribeEvent
    public static void summonDeathEvent(SummonEvent.Death event){ }
}