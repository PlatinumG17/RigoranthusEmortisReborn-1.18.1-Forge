package com.platinumg17.rigoranthusemortisreborn.canis.common.entity.serializers;

import net.minecraft.network.FriendlyByteBuf;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.CanisLevel;
import net.minecraft.network.syncher.EntityDataSerializer;

public class CanisLevelSerializer implements EntityDataSerializer<CanisLevel> {

    @Override
    public void write(FriendlyByteBuf buf, CanisLevel value) {
        buf.writeInt(value.getLevel(CanisLevel.Type.CHORDATA));
//        buf.writeInt(value.getLevel(CanisLevel.Type.KYPHOS));
//        buf.writeInt(value.getLevel(CanisLevel.Type.CAVALIER));
        buf.writeInt(value.getLevel(CanisLevel.Type.HOMINI));
    }

    @Override
    public CanisLevel read(FriendlyByteBuf buf) {
        return new CanisLevel(buf.readInt(), buf.readInt());
    }

    @Override
    public CanisLevel copy(CanisLevel value) {
        return value.copy();
    }
}
