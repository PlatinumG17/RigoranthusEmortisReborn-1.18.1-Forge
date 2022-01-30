package com.platinumg17.rigoranthusemortisreborn.core.init.network.messages;

import io.netty.buffer.ByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.BlockPos;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import com.platinumg17.rigoranthusemortisreborn.blocks.tileentity.smeltery.SmelteryTileEntityBase;

public class PacketShowSettingsButton {

	private int x;
	private int y;
	private int z;
	private int set;

	public PacketShowSettingsButton(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		set = buf.readInt();
	}

	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(set);
	}

	public PacketShowSettingsButton(BlockPos pos, int set) {
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
		this.set = set;
	}

	public void handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayer player = ctx.get().getSender();
			BlockPos pos = new BlockPos(x, y, z);
			SmelteryTileEntityBase te = (SmelteryTileEntityBase) player.level.getBlockEntity(pos);
			if (player.level.isLoaded(pos)) {
				te.show_inventory_settings = set;
				te.getLevel().markAndNotifyBlock(pos, player.level.getChunkAt(pos), te.getLevel().getBlockState(pos).getBlock().defaultBlockState(), te.getLevel().getBlockState(pos), 2, 3);
				te.setChanged();
			}
		});
		ctx.get().setPacketHandled(true);
	}
}