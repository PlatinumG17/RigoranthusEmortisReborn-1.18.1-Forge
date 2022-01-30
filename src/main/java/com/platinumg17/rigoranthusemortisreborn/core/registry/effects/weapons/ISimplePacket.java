package com.platinumg17.rigoranthusemortisreborn.core.registry.effects.weapons;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public interface ISimplePacket {
    void encode(FriendlyByteBuf buffer);
    void consume(Supplier<NetworkEvent.Context> ctx);
}