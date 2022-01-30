package com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet;

import com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data.CanisModeData;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import net.minecraft.network.FriendlyByteBuf;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.EnumMode;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CanisModePacket extends CanisPacket<CanisModeData> {

    @Override
    public void encode(CanisModeData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeInt(data.mode.getIndex());
    }

    @Override
    public CanisModeData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        int modeIndex = buf.readInt();
        return new CanisModeData(entityId, EnumMode.byIndex(modeIndex));
    }

    @Override
    public void handleCanis(CanisEntity canisIn, CanisModeData data, Supplier<NetworkEvent.Context> ctx) {
        if (!canisIn.canInteract(ctx.get().getSender())) {
            return;
        }
        canisIn.setMode(data.mode);
    }
}