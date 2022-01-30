package com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet;

import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.EnumClothColor;
import com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data.ClothColorData;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClothColorPacket extends CanisPacket<ClothColorData> {

    @Override
    public void encode(ClothColorData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeInt(data.clothColor.getIndex());
    }

    @Override
    public ClothColorData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        int clothColorIndex = buf.readInt();
        return new ClothColorData(entityId, EnumClothColor.byIndex(clothColorIndex));
    }

    @Override
    public void handleCanis(CanisEntity canisIn, ClothColorData data, Supplier<NetworkEvent.Context> ctx) {
        if (!canisIn.canInteract(ctx.get().getSender())) {
            return;
        }
        canisIn.setClothColor(data.clothColor);
    }
}