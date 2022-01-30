package com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature;

import com.platinumg17.rigoranthusemortisreborn.api.apicanis.entity.AbstractCanisEntity;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author ProPercivalalb, edited by PlatinumG17
 */
public enum EnumMode {

    DOCILE(0, "docile"),
    WANDERING(1, "wandering"),
    AGGRESIVE(2, "aggressive"),
    BERSERKER(3, "berserker"),
    TACTICAL(4, "tactical"),
//    PATROL(5, "patrol"),
    GUARD(5, "guard");

    private int index;
    private String saveName;
    private String unlocalisedTip;
    private String unlocalisedName;
    private String unlocalisedInfo;

    public static final EnumMode[] VALUES = Arrays.stream(EnumMode.values()).sorted(Comparator.comparingInt(EnumMode::getIndex)).toArray(size -> {
        return new EnumMode[size];
    });

    private EnumMode(int index, String name) {
        this(index, name, "canis.mode." + name, "canis.mode." + name + ".indicator", "canis.mode." + name + ".description");
    }

    private EnumMode(int index, String mode, String unlocalisedName, String tip, String info) {
        this.index = index;
        this.saveName = mode;
        this.unlocalisedName = unlocalisedName;
        this.unlocalisedTip = tip;
        this.unlocalisedInfo = info;
    }

    public int getIndex() {
        return this.index;
    }

    public String getSaveName() {
        return this.saveName;
    }

    public String getTip() {
        return this.unlocalisedTip;
    }

    public String getUnlocalisedName() {
        return this.unlocalisedName;
    }

    public String getUnlocalisedInfo() {
        return this.unlocalisedInfo;
    }

    public void onModeSet(AbstractCanisEntity canis, EnumMode prev) {
        switch(prev) {
            default:
                canis.getNavigation().stop();
                canis.setTarget(null);
                canis.setLastHurtByMob(null);
                break;
        }
    }

    public EnumMode previousMode() {
        int i = this.getIndex() - 1;
        if (i < 0) {
            i = VALUES.length - 1;
        }
        return VALUES[i];
    }

    public EnumMode nextMode() {
        int i = this.getIndex() + 1;
        if (i >= VALUES.length) {
            i = 0;
        }
        return VALUES[i];
    }

    public static EnumMode byIndex(int i) {
        if (i < 0 || i >= VALUES.length) {
            i = EnumMode.DOCILE.getIndex();
        }
        return VALUES[i];
    }

    public static EnumMode bySaveName(String saveName) {
        for (EnumMode gender : EnumMode.values()) {
            if (gender.saveName.equals(saveName)) {
                return gender;
            }
        }
        return DOCILE;
    }
}