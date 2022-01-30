package com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data;

import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.EnumMode;

public class CanisModeData extends CanisData {
    public EnumMode mode;

    public CanisModeData(int entityId, EnumMode modeIn) {
        super(entityId);
        this.mode = modeIn;
    }
}