package com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data;

import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.EnumClothColor;

public class ClothColorData extends CanisData {
    public EnumClothColor clothColor;

    public ClothColorData(int entityId, EnumClothColor clothColorIn) {
        super(entityId);
        this.clothColor = clothColorIn;
    }
}