package com.platinumg17.rigoranthusemortisreborn.magica.common.network;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.util.thread.EffectiveSide;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class Networking {
    public static SimpleChannel INSTANCE;

    private static int ID = 0;
    public static int nextID(){return ID++;}
    public static void registerMessages(){
        System.out.println("Registering Magic packets!");
        INSTANCE = NetworkRegistry.newSimpleChannel(RigoranthusEmortisReborn.rl("magica_network"), () -> "1.0", s->true, s->true);

//        INSTANCE.registerMessage(nextID(),
//                PacketOpenSpellBook.class,
//                PacketOpenSpellBook::toBytes,
//                PacketOpenSpellBook::new,
//                PacketOpenSpellBook::handle);
//        INSTANCE.registerMessage(nextID(),
//                PacketUpdateSpellbook.class,
//                PacketUpdateSpellbook::toBytes,
//                PacketUpdateSpellbook::new,
//                PacketUpdateSpellbook::handle);
        INSTANCE.registerMessage(nextID(),
                PacketUpdateBookGUI.class,
                PacketUpdateBookGUI::toBytes,
                PacketUpdateBookGUI::new,
                PacketUpdateBookGUI::handle);
        INSTANCE.registerMessage(nextID(),
                PacketUpdateDominion.class,
                PacketUpdateDominion::toBytes,
                PacketUpdateDominion::new,
                PacketUpdateDominion::handle);
        INSTANCE.registerMessage(nextID(),
                PacketSetBookMode.class,
                PacketSetBookMode::toBytes,
                PacketSetBookMode::new,
                PacketSetBookMode::handle);
        INSTANCE.registerMessage(nextID(),
                PacketREEffect.class,
                PacketREEffect::encode,
                PacketREEffect::decode,
                PacketREEffect.Handler::handle);
        INSTANCE.registerMessage(nextID(),
                PacketReflexSummon.class,
                PacketReflexSummon::toBytes,
                PacketReflexSummon::new,
                PacketReflexSummon::handle);
        INSTANCE.registerMessage(nextID(),
                PacketWarpPosition.class,
                PacketWarpPosition::encode,
                PacketWarpPosition::decode,
                PacketWarpPosition.Handler::handle);
        INSTANCE.registerMessage(nextID(),
                PacketUpdateSpellColors.class,
                PacketUpdateSpellColors::toBytes,
                PacketUpdateSpellColors::new,
                PacketUpdateSpellColors::handle);
        INSTANCE.registerMessage(nextID(),
                PacketOneShotAnimation.class,
                PacketOneShotAnimation::encode,
                PacketOneShotAnimation::decode,
                PacketOneShotAnimation.Handler::handle);
        INSTANCE.registerMessage(nextID(),
                PacketAnimEntity.class,
                PacketAnimEntity::encode,
                PacketAnimEntity::decode,
                PacketAnimEntity.Handler::handle);
        INSTANCE.registerMessage(nextID(),
                PacketGetPersistentData.class,
                PacketGetPersistentData::toBytes,
                PacketGetPersistentData::new,
                PacketGetPersistentData::handle);
        INSTANCE.registerMessage(nextID(),
                PacketNoSpamChatMessage.class,
                PacketNoSpamChatMessage::toBytes,
                PacketNoSpamChatMessage::new,
                PacketNoSpamChatMessage::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        INSTANCE.registerMessage(nextID(),
                PacketUpdateFlight.class,
                PacketUpdateFlight::toBytes,
                PacketUpdateFlight::new,
                PacketUpdateFlight::handle);
        INSTANCE.registerMessage(nextID(),
                PacketClientDelayEffect.class,
                PacketClientDelayEffect::toBytes,
                PacketClientDelayEffect::new,
                PacketClientDelayEffect::handle);
//        INSTANCE.registerMessage(nextID(),
//                PacketTimedEvent.class,
//                PacketTimedEvent::toBytes,
//                PacketTimedEvent::new,
//                PacketTimedEvent::handle);
        INSTANCE.registerMessage(nextID(),
                PacketSummonFamiliar.class,
                PacketSummonFamiliar::toBytes,
                PacketSummonFamiliar::new,
                PacketSummonFamiliar::handle);
        INSTANCE.registerMessage(nextID(),
                PacketSetCommandMode.class,
                PacketSetCommandMode::toBytes,
                PacketSetCommandMode::new,
                PacketSetCommandMode::handle);
        INSTANCE.registerMessage(nextID(),
                PacketSyncPlayerCap.class,
                PacketSyncPlayerCap::toBytes,
                PacketSyncPlayerCap::new,
                PacketSyncPlayerCap::handle);
        INSTANCE.registerMessage(nextID(),
                PacketTogglePathing.class,
                PacketTogglePathing::toBytes,
                PacketTogglePathing::new,
                PacketTogglePathing::handle);

        INSTANCE.registerMessage(nextID(),
                PacketHotkeyPressed.class,
                PacketHotkeyPressed::toBytes,
                PacketHotkeyPressed::new,
                PacketHotkeyPressed::handle);

        INSTANCE.registerMessage(nextID(),
                PacketIncorporealDoubleJump.class,
                PacketIncorporealDoubleJump::encode,
                PacketIncorporealDoubleJump::new,
                PacketIncorporealDoubleJump::handle);
        INSTANCE.registerMessage(nextID(),
                PacketApogeanDoubleJump.class,
                PacketApogeanDoubleJump::encode,
                PacketApogeanDoubleJump::new,
                PacketApogeanDoubleJump::handle);
    }
    public static void sendToNearby(Level world, BlockPos pos, Object toSend){
        if (world instanceof ServerLevel) {
            ServerLevel ws = (ServerLevel) world;
            ws.getChunkSource().chunkMap.getPlayers(new ChunkPos(pos), false).stream()
                    .filter(p -> p.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) < 64 * 64)
                    .forEach(p -> INSTANCE.send(PacketDistributor.PLAYER.with(() -> p), toSend));
        }
    }

    public static void sendToNearby(Level world, Entity e, Object toSend) {
        sendToNearby(world, e.blockPosition(), toSend);
    }

    public static void sendToPlayer(Object msg, Player player) {
        if (EffectiveSide.get() == LogicalSide.SERVER) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), msg);
        }
    }
}