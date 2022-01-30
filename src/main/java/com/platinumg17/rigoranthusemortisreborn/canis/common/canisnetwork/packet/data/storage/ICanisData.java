package com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data.storage;

import java.util.UUID;

public interface ICanisData {
    UUID getCanisId();
    UUID getOwnerId();
    String getCanisName();
    String getOwnerName();
}