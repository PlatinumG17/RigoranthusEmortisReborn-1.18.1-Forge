package com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet;

import com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.IPacket;
import com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data.CanisInventoryPageData;
import com.platinumg17.rigoranthusemortisreborn.canis.common.inventory.container.CanisInventoriesContainer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CanisInventoryPagePacket implements IPacket<CanisInventoryPageData> {

    @Override
    public CanisInventoryPageData decode(FriendlyByteBuf buf) {
        return new CanisInventoryPageData(buf.readInt());
    }

    @Override
    public void encode(CanisInventoryPageData data, FriendlyByteBuf buf) {
        buf.writeInt(data.page);
    }

    @Override
    public void handle(CanisInventoryPageData data, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (ctx.get().getDirection().getReceptionSide() == LogicalSide.SERVER) {
                ServerPlayer player = ctx.get().getSender();
                AbstractContainerMenu container = player.containerMenu;
                if (container instanceof CanisInventoriesContainer) {
                    CanisInventoriesContainer inventories = (CanisInventoriesContainer) container;
                    int page = Mth.clamp(data.page, 0, Math.max(0, inventories.getTotalNumColumns() - 9));

                    inventories.setPage(page);
                    inventories.setData(0, page);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
