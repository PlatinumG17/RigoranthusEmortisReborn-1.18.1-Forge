package com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data;

public class CanisNameData extends CanisData {
    public final String name;

    public CanisNameData(int entityId, String name) {
        super(entityId);
        this.name = name;
    }
}
