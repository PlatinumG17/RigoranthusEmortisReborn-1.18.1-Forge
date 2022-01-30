package com.platinumg17.rigoranthusemortisreborn.api.apimagic.dominion;

/**
 * Interface for a generic tile that holds dominion.
 */
public interface IDominionTile {
    int getTransferRate();
    boolean canAcceptDominion();
    int getCurrentDominion();
    int getMaxDominion();
    void setMaxDominion(int max);
    int setDominion(final int dominion);
    int addDominion(final int dominionToAdd);
    int removeDominion(final int dominionToRemove);
}