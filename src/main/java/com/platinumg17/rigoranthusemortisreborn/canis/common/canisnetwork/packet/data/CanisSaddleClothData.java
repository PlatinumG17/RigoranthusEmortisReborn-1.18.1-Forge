package com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data;

public class CanisSaddleClothData extends CanisData {
    public final boolean displayCloth;

    public CanisSaddleClothData(int entityId, boolean displayCloth) {
        super(entityId);
        this.displayCloth = displayCloth;
    }
}