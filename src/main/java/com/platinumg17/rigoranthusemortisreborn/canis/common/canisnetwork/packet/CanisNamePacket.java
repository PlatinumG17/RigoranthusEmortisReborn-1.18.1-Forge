package com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet;

import com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data.CanisNameData;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CanisNamePacket extends CanisPacket<CanisNameData> {

    @Override
    public void encode(CanisNameData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeUtf(data.name, 64);
    }

    @Override
    public CanisNameData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        String name = buf.readUtf(64);
        return new CanisNameData(entityId, name);
    }

    @Override
    public void handleCanis(CanisEntity canisIn, CanisNameData data, Supplier<NetworkEvent.Context> ctx) {
        if (!canisIn.canInteract(ctx.get().getSender())) {
            return;
        }
        if (data.name.isEmpty()) {
            canisIn.setCustomName(null);
        }
        else {
            canisIn.setCustomName(new TextComponent(data.name));
        }
    }
}