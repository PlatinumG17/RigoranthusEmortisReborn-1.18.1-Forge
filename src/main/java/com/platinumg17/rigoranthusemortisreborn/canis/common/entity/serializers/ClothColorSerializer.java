package com.platinumg17.rigoranthusemortisreborn.canis.common.entity.serializers;

import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.EnumClothColor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;

public class ClothColorSerializer implements EntityDataSerializer<EnumClothColor> {
    @Override
    public void write(FriendlyByteBuf buf, EnumClothColor value) {
        buf.writeByte(value.getIndex());
    }

    @Override
    public EnumClothColor read(FriendlyByteBuf buf) {
        return EnumClothColor.byIndex(buf.readByte());
    }

    @Override
    public EnumClothColor copy(EnumClothColor value) {
        return value;
    }
}