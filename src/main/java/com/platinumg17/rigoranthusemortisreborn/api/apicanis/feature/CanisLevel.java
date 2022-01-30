package com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature;

public class CanisLevel {

    private int level;
    private int hominiLevel;

    public static enum Type {
        CHORDATA("chordata_treat"),
//        KYPHOS("kyphos_treat"),
//        CAVALIER("cavalier_treat"),
        HOMINI("homini_treat");

        String n;
        Type(String n) {
            this.n = n;
        }
        public String getName() {
            return this.n;
        }
    }

    public CanisLevel(int level, int hominiLevel) {
        this.level = level;
        this.hominiLevel = hominiLevel;
    }

    public int getLevel(Type type) {
        return type == Type.HOMINI ? this.hominiLevel : this.level;
    }

    public boolean canIncrease(Type type) {
        return type != Type.HOMINI || this.level >= 60;
    }

    @Deprecated
    public void setLevel(Type type, int level) {
        if (type == Type.HOMINI) {
            this.hominiLevel = level;
        } else {
            this.level = level;
        }
    }

    @Deprecated
    public void incrementLevel(Type type) {
        this.setLevel(type, this.getLevel(type) + 1);
    }

    public CanisLevel copy() {
        return new CanisLevel(this.level, this.hominiLevel);
    }

    /**
     * Combines parents levels together
     */
    public CanisLevel combine(CanisLevel levelIn) {
        int combinedLevel = this.getLevel(Type.CHORDATA) + levelIn.getLevel(Type.CHORDATA);
        combinedLevel /= 2;
        combinedLevel = Math.min(combinedLevel, 20);
        return new CanisLevel(combinedLevel, 0);
    }

    public final boolean isHominiCanis() {
        return this.hominiLevel >= 30;
    }
}
