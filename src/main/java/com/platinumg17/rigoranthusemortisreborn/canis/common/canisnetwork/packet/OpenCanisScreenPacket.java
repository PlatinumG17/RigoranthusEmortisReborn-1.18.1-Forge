package com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet;

import com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.IPacket;
import com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data.OpenCanisScreenData;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import com.platinumg17.rigoranthusemortisreborn.canis.common.inventory.screens.CanisScreens;
import com.platinumg17.rigoranthusemortisreborn.canis.common.skill.WaywardTravellerSkill;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class OpenCanisScreenPacket implements IPacket<OpenCanisScreenData> {

    @Override
    public OpenCanisScreenData decode(FriendlyByteBuf buf) {
        return new OpenCanisScreenData();
    }

    @Override
    public void encode(OpenCanisScreenData data, FriendlyByteBuf buf) {
    }

    @Override
    public void handle(OpenCanisScreenData data, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (ctx.get().getDirection().getReceptionSide() == LogicalSide.SERVER) {
                ServerPlayer player = ctx.get().getSender();
                List<CanisEntity> cani = player.level.getEntitiesOfClass(CanisEntity.class, player.getBoundingBox().inflate(12D, 12D, 12D),
                        (canis) -> canis.canInteract(player) && WaywardTravellerSkill.hasInventory(canis)
                );
                if (!cani.isEmpty()) {
                    CanisScreens.openCanisInventoriesScreen(player, cani);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}