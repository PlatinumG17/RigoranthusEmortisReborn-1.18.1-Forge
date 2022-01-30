package com.platinumg17.rigoranthusemortisreborn.config.itemconfigs;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.RegistryObject;

public class ItemConfig {

    private final String itemName;

    public final ForgeConfigSpec.IntValue durability;

    public ItemConfig(ForgeConfigSpec.Builder builder, RegistryObject<?> item, String maxDamageDescription) {
        this(builder, item.getId().getPath(), maxDamageDescription);
    }

    public ItemConfig(ForgeConfigSpec.Builder builder, String itemName, String maxDamageDescription) {
        this.itemName = itemName;
        builder.push(itemName);
        durability = builder
                .worldRestart()
                .comment(maxDamageDescription, "Setting this to 0 will make this item unbreakable")
                .translation(EmortisConstants.MOD_ID + ".config.server.items.durability")
                .defineInRange("durability", 400, 0, Short.MAX_VALUE);
        addConfigs(builder);
        builder.pop();
    }

    public void bake() { }

    public void addConfigs(ForgeConfigSpec.Builder builder) { }

    protected String translate(String value) {
        return String.format("%s.config.server.%s.%s", EmortisConstants.MOD_ID, itemName, value);
    }
}