package com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data;

import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.EnumShadesColor;

public class ShadesColorData extends CanisData {
    public EnumShadesColor shadesColor;

    public ShadesColorData(int entityId, EnumShadesColor shadesColorIn) {
        super(entityId);
        this.shadesColor = shadesColorIn;
    }
}