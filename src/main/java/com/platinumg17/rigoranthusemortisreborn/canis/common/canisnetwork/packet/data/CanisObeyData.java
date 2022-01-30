package com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data;

public class CanisObeyData extends CanisData {
    public final boolean obeyOthers;

    public CanisObeyData(int entityId, boolean obeyOthers) {
        super(entityId);
        this.obeyOthers = obeyOthers;
    }
}