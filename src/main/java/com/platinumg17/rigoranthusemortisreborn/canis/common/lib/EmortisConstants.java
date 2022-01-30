package com.platinumg17.rigoranthusemortisreborn.canis.common.lib;

import com.platinumg17.rigoranthusemortisreborn.canis.common.util.REUtil;
import net.minecraft.resources.ResourceLocation;

public class EmortisConstants {

    public static ResourceLocation resourceLoc(String name) { return new ResourceLocation(MOD_ID, name); }

    public static final String MOD_ID = "rigoranthusemortisreborn";
    public static final String MOD_NAME = "Rigoranthus Emortis Reborn";

    public static final String VANILLA_ID = "minecraft";
    public static final String VANILLA_NAME = "Minecraft";

    // Network
    public static final ResourceLocation CHANNEL_NAME = REUtil.getResource("channel");
    public static final String PROTOCOL_VERSION = Integer.toString(1);

    // Storage
    public static final String STORAGE_CANIS_RESPAWN = MOD_ID + "CanisGraveyard";
    public static final String STORAGE_CANIS_LOCATION = "canis_locations";

    public static class EntityState {
        public static final byte DEATH = 3;
        public static final byte CANIS_SMOKE = 6;
        public static final byte CANIS_HEARTS = 7;
        public static final byte CANIS_START_SHAKING = 8;
        public static final byte GUARDIAN_SOUND = 21;
        public static final byte TOTEM_OF_UNDYING = 35;
        public static final byte SLIDING_DOWN_HONEY = 53;
        public static final byte CANIS_INTERRUPT_SHAKING = 56;
    }
}