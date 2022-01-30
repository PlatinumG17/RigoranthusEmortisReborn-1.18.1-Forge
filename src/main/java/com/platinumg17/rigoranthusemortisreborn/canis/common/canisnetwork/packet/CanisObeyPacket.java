package com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet;

import com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data.CanisObeyData;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CanisObeyPacket extends CanisPacket<CanisObeyData> {

    @Override
    public void encode(CanisObeyData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeBoolean(data.obeyOthers);
    }

    @Override
    public CanisObeyData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        boolean obeyOthers = buf.readBoolean();
        return new CanisObeyData(entityId, obeyOthers);
    }

    @Override
    public void handleCanis(CanisEntity canisIn, CanisObeyData data, Supplier<NetworkEvent.Context> ctx) {
        if (!canisIn.canInteract(ctx.get().getSender())) {
            return;
        }
        canisIn.setWillObeyOthers(data.obeyOthers);
    }
}