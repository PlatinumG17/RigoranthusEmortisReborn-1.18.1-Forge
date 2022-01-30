package com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet;

import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.EnumShadesColor;
import com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data.ShadesColorData;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ShadesColorPacket extends CanisPacket<ShadesColorData> {

    @Override
    public void encode(ShadesColorData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeInt(data.shadesColor.getIndex());
    }

    @Override
    public ShadesColorData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        int shadesColorIndex = buf.readInt();
        return new ShadesColorData(entityId, EnumShadesColor.byIndex(shadesColorIndex));
    }

    @Override
    public void handleCanis(CanisEntity canisIn, ShadesColorData data, Supplier<NetworkEvent.Context> ctx) {
        if (!canisIn.canInteract(ctx.get().getSender())) {
            return;
        }
        canisIn.setShadesColor(data.shadesColor);
    }
}