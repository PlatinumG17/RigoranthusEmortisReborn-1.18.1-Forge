package com.platinumg17.rigoranthusemortisreborn.api.apimagic.entity;

import com.mojang.authlib.GameProfile;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.MenuProvider;
import net.minecraft.server.MinecraftServer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.util.FakePlayer;

import java.lang.ref.WeakReference;
import java.util.OptionalInt;
import java.util.UUID;

// https://github.com/Creators-of-Create/Create/blob/mc1.15/dev/src/main/java/com/simibubi/create/content/contraptions/components/deployer/DeployerFakePlayer.java#L57
public class REFakePlayer extends FakePlayer {

    private static final Connection NETWORK_MANAGER = new Connection(PacketFlow.CLIENTBOUND);
    public static final GameProfile PROFILE =
        new GameProfile(UUID.fromString("7400926d-1007-4e53-880f-b43e67f2bf29"),
       "Rigoranthus_Emortis_Reborn"
    );

    private REFakePlayer(ServerLevel world) {
        super(world, PROFILE);
        connection = new FakePlayNetHandler(world.getServer(), this);
    }
    private static WeakReference<REFakePlayer> FAKE_PLAYER = null;
    public static REFakePlayer getPlayer(ServerLevel world) {return new REFakePlayer(world);}
    @Override public OptionalInt openMenu(MenuProvider container) {
        return OptionalInt.empty();
    }

    @Override
    public Component getDisplayName() {
        return new TextComponent("RE_Fake_Player");
    }
    private static class FakePlayNetHandler extends ServerGamePacketListenerImpl {
        public FakePlayNetHandler(MinecraftServer server, ServerPlayer playerIn) {
            super(server, NETWORK_MANAGER, playerIn);
        }
        @Override
        public void send(Packet<?> packetIn) {}
        @Override
        public void send(Packet<?> packetIn, GenericFutureListener<? extends Future<? super Void>> futureListeners) {}
    }
}