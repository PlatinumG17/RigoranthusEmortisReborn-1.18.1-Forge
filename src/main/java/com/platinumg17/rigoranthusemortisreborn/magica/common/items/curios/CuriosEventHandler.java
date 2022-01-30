package com.platinumg17.rigoranthusemortisreborn.magica.common.items.curios;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.item.RigoranthusEmortisRebornCurio;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.CuriosUtil;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EmortisConstants.MOD_ID)
public class CuriosEventHandler {

    @SubscribeEvent
    public static void playerOnTick(TickEvent.PlayerTickEvent event) {
        if(event.phase != TickEvent.Phase.END)
            return;
        CuriosUtil.getAllWornItems(event.player).ifPresent(e ->{
            for(int i = 0; i < e.getSlots(); i++){
                Item item = e.getStackInSlot(i).getItem();
                if(item instanceof RigoranthusEmortisRebornCurio)
                    ((RigoranthusEmortisRebornCurio) item).wearableTick(event.player);
            }
        });
    }
}