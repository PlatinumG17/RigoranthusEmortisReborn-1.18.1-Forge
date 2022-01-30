package com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author PlatinumG17
 */
public enum EnumClothColor {

    RED(        0, "red"),
    YELLOW(     1, "yellow"),
    ORANGE(     2, "orange"),
    PINK(       3, "pink"),
    MAGENTA(    4, "magenta"),
    PURPLE(     5, "purple"),
    BLUE(       6, "blue"),
    LIGHT_BLUE( 7, "light_blue"),
    CYAN(       8, "cyan"),
    GREEN(      9, "green"),
    LIME(       10,"lime"),
    BROWN(      11,"brown"),
    BLACK(      12,"black"),
    GRAY(       13,"gray"),
    LIGHT_GRAY( 14,"light_gray"),
    WHITE(      15,"white");

    private int index;
    private String saveName;
    private String unlocalisedName;

    public static final EnumClothColor[] VALUES = Arrays.stream(EnumClothColor.values()).sorted(Comparator.comparingInt(EnumClothColor::getIndex)).toArray(size -> {
        return new EnumClothColor[size];
    });

    private EnumClothColor(int index, String name) {
        this(index, name, "canis.color." + name);
    }

    private EnumClothColor(int index, String color, String unlocalisedName) {
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

    public EnumClothColor previousColor() {
        int i = this.getIndex() - 1;
        if (i < 0) {
            i = VALUES.length - 1;
        }
        return VALUES[i];
    }

    public EnumClothColor nextColor() {
        int i = this.getIndex() + 1;
        if (i >= VALUES.length) {
            i = 0;
        }
        return VALUES[i];
    }

    public static EnumClothColor byIndex(int i) {
        if (i < 0 || i >= VALUES.length) {
            i = EnumClothColor.RED.getIndex();
        }
        return VALUES[i];
    }

    public static EnumClothColor bySaveName(String saveName) {
        for (EnumClothColor clothColor : EnumClothColor.values()) {
            if (clothColor.saveName.equals(saveName)) {
                return clothColor;
            }
        }
        return RED;
    }
}