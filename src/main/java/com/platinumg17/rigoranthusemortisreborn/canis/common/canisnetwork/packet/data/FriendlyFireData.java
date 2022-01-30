package com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data;

public class FriendlyFireData extends CanisData {

    public final boolean friendlyFire;

    public FriendlyFireData(int entityId, boolean friendlyFire) {
        super(entityId);
        this.friendlyFire = friendlyFire;
    }
}