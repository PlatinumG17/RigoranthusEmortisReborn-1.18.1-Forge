package com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author PlatinumG17
 */
public enum EnumShadesColor {

    BLACK(          0, "black"),
    BLK_TRANSPARENT(1, "black_transparent"),
    BLUE(           2, "blue"),
    CYAN(           3, "cyan"),
    GLASS(          4, "glass"),
    GOLDEN(         5, "golden"),
    GRAY(           6, "gray"),
    GREEN(          7, "green"),
    ORANGE(         8, "orange"),
    PURPLE(         9, "purple"),
    RED(            10,"red"),
    YELLOW(         11,"yellow");

    private int index;
    private String saveName;
    private String unlocalisedName;

    public static final EnumShadesColor[] VALUES = Arrays.stream(EnumShadesColor.values()).sorted(Comparator.comparingInt(EnumShadesColor::getIndex)).toArray(size -> {
        return new EnumShadesColor[size];
    });

    private EnumShadesColor(int index, String name) {
        this(index, name, "canis.color." + name);
    }

    private EnumShadesColor(int index, String color, String unlocalisedName) {
        this.index = index;
        this.saveName = color;
        this.unlocalisedName = unlocalisedName;
    }

    public int getIndex() {
        return this.index;
    }

    public String getSaveName() {
        return this.saveName;
    }

    public String getUnlocalisedName() {
        return this.unlocalisedName;
    }

    public EnumShadesColor previousColor() {
        int i = this.getIndex() - 1;
        if (i < 0) {
            i = VALUES.length - 1;
        }
        return VALUES[i];
    }

    public EnumShadesColor nextColor() {
        int i = this.getIndex() + 1;
        if (i >= VALUES.length) {
            i = 0;
        }
        return VALUES[i];
    }

    public static EnumShadesColor byIndex(int i) {
        if (i < 0 || i >= VALUES.length) {
            i = EnumShadesColor.BLACK.getIndex();
        }
        return VALUES[i];
    }

    public static EnumShadesColor bySaveName(String saveName) {
        for (EnumShadesColor shadesColor : EnumShadesColor.values()) {
            if (shadesColor.saveName.equals(saveName)) {
                return shadesColor;
            }
        }
        return BLACK;
    }
}