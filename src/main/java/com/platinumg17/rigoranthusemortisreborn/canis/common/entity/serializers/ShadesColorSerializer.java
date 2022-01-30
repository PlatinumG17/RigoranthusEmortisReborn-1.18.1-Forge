package com.platinumg17.rigoranthusemortisreborn.canis.common.entity.serializers;

import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.EnumShadesColor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;

public class ShadesColorSerializer implements EntityDataSerializer<EnumShadesColor> {
    @Override
    public void write(FriendlyByteBuf buf, EnumShadesColor value) {
        buf.writeByte(value.getIndex());
    }

    @Override
    public EnumShadesColor read(FriendlyByteBuf buf) {
        return EnumShadesColor.byIndex(buf.readByte());
    }

    @Override
    public EnumShadesColor copy(EnumShadesColor value) {
        return value;
    }
}