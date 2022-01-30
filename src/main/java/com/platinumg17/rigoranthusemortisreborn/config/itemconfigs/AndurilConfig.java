package com.platinumg17.rigoranthusemortisreborn.config.itemconfigs;


import com.platinumg17.rigoranthusemortisreborn.core.init.ItemInit;
import net.minecraftforge.common.ForgeConfigSpec;

public class AndurilConfig extends ItemConfig {

    public ForgeConfigSpec.IntValue fireDuration;

    public AndurilConfig(ForgeConfigSpec.Builder builder) {
        super(builder, ItemInit.ANDURIL.getId().getPath(), "Affects how much this item can be used before breaking");
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        fireDuration = builder
                .comment("Duration (equal to total fire damage) for which entities are set on fire each time a player gets a Crit")
                .translation(translate("fire_duration"))
                .defineInRange("fire_duration", 3, 0, Integer.MAX_VALUE);
    }
}
