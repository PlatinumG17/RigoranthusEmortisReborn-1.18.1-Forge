package com.platinumg17.rigoranthusemortisreborn.api.apimagic.dominion;

/**
 * Interface for a generic tile that holds ichor.
 */
public interface IIchorTile {
    int getTransferRate();
    boolean canAcceptIchor();
    int getCurrentIchor();
    int getMaxIchor();
    void setMaxIchor(int max);
    int setIchor(final int ichor);
    int addIchor(final int ichorToAdd);
    int removeIchor(final int ichorToRemove);
}
