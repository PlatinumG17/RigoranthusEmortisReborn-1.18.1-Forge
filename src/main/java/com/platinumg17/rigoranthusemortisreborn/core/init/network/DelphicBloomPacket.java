package com.platinumg17.rigoranthusemortisreborn.core.init.network;

import com.platinumg17.rigoranthusemortisreborn.core.registry.effects.weapons.IPlayerToClientPacket;
import com.platinumg17.rigoranthusemortisreborn.entity.DelphicBloomEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.FriendlyByteBuf;

public class DelphicBloomPacket implements IPlayerToClientPacket {
    private final int entityID;
    private final DelphicBloomEntity.Animation animation;

    public static DelphicBloomPacket createPacket(DelphicBloomEntity entity, DelphicBloomEntity.Animation animation) {
        return new DelphicBloomPacket(entity.getId(), animation);
    }

    private DelphicBloomPacket(int entityID, DelphicBloomEntity.Animation animation) {
        this.entityID = entityID;
        this.animation = animation;
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(entityID);
        buffer.writeInt(animation.ordinal());
    }

    public static DelphicBloomPacket decode(FriendlyByteBuf buffer) {
        int entityID = buffer.readInt(); //readInt spits out the values you gave to the FriendlyByteBuf in encode in that order
        DelphicBloomEntity.Animation animation = DelphicBloomEntity.Animation.values()[buffer.readInt()];

        return new DelphicBloomPacket(entityID, animation);
    }

    @Override
    public void execute() {
        Entity entity = Minecraft.getInstance().level.getEntity(entityID);
        if(entity instanceof DelphicBloomEntity) {
            ((DelphicBloomEntity) entity).setAnimationFromPacket(animation);
        }
    }
}
