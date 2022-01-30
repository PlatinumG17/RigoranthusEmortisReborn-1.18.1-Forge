package com.platinumg17.rigoranthusemortisreborn.core.init.network.messages;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class Messages {
    public static SimpleChannel INSTANCE;

    private static int ID = 0;
    private static int nextID() {
        return ID++;
    }

    public static void registerMessages(String channelName) {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(RigoranthusEmortisReborn.MOD_ID, channelName), () -> "1.0", s -> true, s -> true);

        // Server side
        INSTANCE.registerMessage(nextID(), PacketSettingsButton.class,
                PacketSettingsButton::toBytes,
                PacketSettingsButton::new,
                PacketSettingsButton::handle);

        INSTANCE.registerMessage(nextID(), PacketShowSettingsButton.class,
                PacketShowSettingsButton::toBytes,
                PacketShowSettingsButton::new,
                PacketShowSettingsButton::handle);

        // Client side
    }
}