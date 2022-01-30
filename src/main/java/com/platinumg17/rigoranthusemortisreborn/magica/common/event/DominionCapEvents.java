package com.platinumg17.rigoranthusemortisreborn.magica.common.event;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
//import com.platinumg17.rigoranthusemortisreborn.config.Config;
//import com.platinumg17.rigoranthusemortisreborn.magica.common.capability.DominionCapability;
//import com.platinumg17.rigoranthusemortisreborn.magica.common.network.Networking;
//import com.platinumg17.rigoranthusemortisreborn.magica.common.network.PacketUpdateDominion;
//import com.platinumg17.rigoranthusemortisreborn.api.apimagic.dominion.IDominion;
//import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.DominionUtil;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraftforge.event.TickEvent;
//import net.minecraftforge.event.entity.player.PlayerEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//import net.minecraftforge.network.PacketDistributor;
//
//@Mod.EventBusSubscriber(modid = EmortisConstants.MOD_ID)
//public class DominionCapEvents {
//    public static double MEAN_TPS = 20.0;
//
//    @SubscribeEvent
//    public static void playerOnTick(TickEvent.PlayerTickEvent e) {
//        if(e.player.getCommandSenderWorld().isClientSide || e.player.getCommandSenderWorld().getGameTime() % Config.REGEN_INTERVAL.get() != 0)
//            return;
//
//        IDominion dominion = DominionCapability.getDominion(e.player).orElse(null);
//        if(dominion == null)
//            return;
//
//        if (dominion.getCurrentDominion() != dominion.getMaxDominion()) {
//            double regenPerSecond = DominionUtil.getDominionRegen(e.player) / Math.max(1, ((int)MEAN_TPS / Config.REGEN_INTERVAL.get()));
//            dominion.addDominion(regenPerSecond);
//            Networking.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) e.player), new PacketUpdateDominion(dominion.getCurrentDominion(), dominion.getMaxDominion(), dominion.getGlyphBonus(), dominion.getBookTier()));
//        }
//        int max = DominionUtil.getMaxDominion(e.player);
//        if(dominion.getMaxDominion() != max) {
//            dominion.setMaxDominion(max);
//            Networking.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) e.player), new PacketUpdateDominion(dominion.getCurrentDominion(), dominion.getMaxDominion(), dominion.getGlyphBonus(), dominion.getBookTier()));
//        }
//    }
//
//    @SubscribeEvent
//    public static void playerRespawn(PlayerEvent.PlayerRespawnEvent e) {
//        syncPlayerEvent(e.getPlayer());
//    }
//
//    @SubscribeEvent
//    public static void playerClone(PlayerEvent.Clone e) {
//        if(e.getOriginal().level.isClientSide)
//            return;
//
//        DominionCapability.getDominion((LivingEntity) e.getEntity()).ifPresent(newDominion -> DominionCapability.getDominion(e.getOriginal()).ifPresent(origDominion -> {
//            newDominion.setMaxDominion(origDominion.getMaxDominion());
//            newDominion.setGlyphBonus(origDominion.getGlyphBonus());
//            newDominion.setBookTier(origDominion.getBookTier());
//            Networking.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer)  e.getEntity()), new PacketUpdateDominion(newDominion.getCurrentDominion(), newDominion.getMaxDominion(), newDominion.getGlyphBonus(), newDominion.getBookTier()));
//        }));
//    }
//
//    @SubscribeEvent
//    public static void playerLoggedIn(PlayerEvent.StartTracking e) {
//        syncPlayerEvent(e.getPlayer());
//    }
//
//    @SubscribeEvent
//    public static void playerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent e) {
//        syncPlayerEvent(e.getPlayer());
//    }
//
//    public static void syncPlayerEvent(Player playerEntity){
//        if (playerEntity instanceof ServerPlayer) {
//            DominionCapability.getDominion(playerEntity).ifPresent(dominion -> {
//                dominion.setMaxDominion(DominionUtil.getMaxDominion(playerEntity));
//                dominion.setGlyphBonus(dominion.getGlyphBonus());
//                dominion.setBookTier(dominion.getBookTier());
//                Networking.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) playerEntity), new PacketUpdateDominion(dominion.getCurrentDominion(), dominion.getMaxDominion(), dominion.getGlyphBonus(), dominion.getBookTier()));
//            });
//        }
//    }
//
//    @SubscribeEvent
//    public static void onTick(TickEvent.PlayerTickEvent e) {
//        if(e.player.level.isClientSide)
//            return;
//        if(e.player.level.getGameTime() % 600 == 0 && e.player.getServer() != null) {
//
//            double meanTickTime = mean(e.player.getServer().tickTimes) * 1.0E-6D;
//            double meanTPS = Math.min(1000.0 / meanTickTime, 20);
//            DominionCapEvents.MEAN_TPS = Math.max(1, meanTPS);
//        }
//    }
//    private static long mean(long[] values)
//    {
//        long sum = 0L;
//        for (long v : values)
//            sum += v;
//        return sum / values.length;
//    }
//}