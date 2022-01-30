package com.platinumg17.rigoranthusemortisreborn.core.events.other;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.teamabnormals.blueprint.common.advancement.EmptyTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = EmortisConstants.MOD_ID)
public class CriteriaTriggersRigoranthus {

    public static final EmptyTrigger KILL_A_LANGUID_DWELLER = CriteriaTriggers.register(new EmptyTrigger(prefix("kill_a_languid_dweller")));
    public static final EmptyTrigger OBTAIN_A_RECORD = CriteriaTriggers.register(new EmptyTrigger(prefix("obtain_a_record")));
    public static final EmptyTrigger LISTEN_TO_RECORD = CriteriaTriggers.register(new EmptyTrigger(prefix("listen_to_a_record")));

    private static ResourceLocation prefix(String name) {
        return RigoranthusEmortisReborn.rl(name);
    }
}