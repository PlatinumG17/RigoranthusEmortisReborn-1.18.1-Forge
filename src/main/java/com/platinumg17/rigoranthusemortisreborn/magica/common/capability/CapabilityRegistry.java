package com.platinumg17.rigoranthusemortisreborn.magica.common.capability;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.dominion.IDominionCap;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.magica.common.network.Networking;
import com.platinumg17.rigoranthusemortisreborn.magica.common.network.PacketSyncPlayerCap;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class CapabilityRegistry {

    public static final Capability<IDominionCap> DOMINION_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
    public static final Capability<IPlayerCap> PLAYER_DATA_CAP = CapabilityManager.get(new CapabilityToken<>(){});

    public static final Direction DEFAULT_FACING = null;

    /**
     * Get the {@link IDominionCap} from the specified entity.
     *
     * @param entity The entity
     * @return A lazy optional containing the IDominion, if any
     */
    public static LazyOptional<IDominionCap> getDominion(final LivingEntity entity){
        return entity.getCapability(DOMINION_CAPABILITY);
    }

    /**
     * Get the {@link IPlayerCap} from the specified entity.
     *
     * @param entity The entity
     * @return A lazy optional containing the IDominion, if any
     */
    public static LazyOptional<IPlayerCap> getPlayerDataCap(final LivingEntity entity){
        return entity.getCapability(PLAYER_DATA_CAP);
    }
    /**
     * Event handler for the {@link IDominionCap} capability.
     */
    @SuppressWarnings("unused")
    @Mod.EventBusSubscriber(modid = EmortisConstants.MOD_ID)
    public static class EventHandler {

        /**
         * Attach the {@link IDominionCap} capability to all living entities.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void attachCapabilities(final AttachCapabilitiesEvent<Entity> event) {
            if(event.getObject() instanceof Player) {
                DominionCapAttacher.attach(event);
                REPlayerCapAttacher.attach(event);
            }
        }

        @SubscribeEvent
        public static void registerCapabilities(final RegisterCapabilitiesEvent event) {
            event.register(IDominionCap.class);
            event.register(IPlayerCap.class);
        }
        /**
         * Copy the player's dominion when they respawn after dying or returning from the end.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void playerClone(PlayerEvent.Clone event) {
            Player oldPlayer = event.getOriginal();
            oldPlayer.revive();
            getDominion(oldPlayer).ifPresent(oldMaxDominion -> getDominion(event.getPlayer()).ifPresent(newMaxDominion -> {
                newMaxDominion.setMaxDominion(oldMaxDominion.getMaxDominion());
                newMaxDominion.setDominion(oldMaxDominion.getCurrentDominion());
                newMaxDominion.setBookTier(oldMaxDominion.getBookTier());
                newMaxDominion.setGlyphBonus(oldMaxDominion.getGlyphBonus());
            }));

            getPlayerDataCap(oldPlayer).ifPresent(oldPlayerCap ->{
                IPlayerCap playerDataCap = getPlayerDataCap(event.getPlayer()).orElse(new REPlayerDataCap());
                CompoundTag tag = oldPlayerCap.serializeNBT();
                playerDataCap.deserializeNBT(tag);
                syncPlayerCap(event.getPlayer());
            });
            event.getOriginal().invalidateCaps();
        }


        @SubscribeEvent
        public static void onPlayerLoginEvent(PlayerEvent.PlayerLoggedInEvent event) {
            if(event.getPlayer() instanceof ServerPlayer){
                syncPlayerCap(event.getPlayer());
            }
        }

        @SubscribeEvent
        public static void respawnEvent(PlayerEvent.PlayerRespawnEvent event) {
            if(event.getPlayer() instanceof ServerPlayer) {
                syncPlayerCap(event.getPlayer());
            }
        }


        @SubscribeEvent
        public static void onPlayerStartTrackingEvent(PlayerEvent.StartTracking event) {
            if (event.getTarget() instanceof Player && event.getPlayer() instanceof ServerPlayer) {
                syncPlayerCap(event.getPlayer());
            }
        }

        @SubscribeEvent
        public static void onPlayerDimChangedEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
            if (event.getPlayer() instanceof ServerPlayer) {
                syncPlayerCap(event.getPlayer());
            }
        }

        public static void syncPlayerCap(Player player){
            IPlayerCap cap = CapabilityRegistry.getPlayerDataCap(player).orElse(new REPlayerDataCap());
            CompoundTag tag = cap.serializeNBT();
            Networking.sendToPlayer(new PacketSyncPlayerCap(tag), player);
        }
    }
}