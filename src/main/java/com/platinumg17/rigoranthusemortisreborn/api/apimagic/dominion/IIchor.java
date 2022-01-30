package com.platinumg17.rigoranthusemortisreborn.api.apimagic.dominion;

public interface IIchor {
    double getCurrentIchor();
    int getMaxIchor();
    void setMaxIchor(int max);
    double setIchor(final double ichor);
    double addIchor(final double ichorToAdd);
    double removeIchor(final double ichorToRemove);
}