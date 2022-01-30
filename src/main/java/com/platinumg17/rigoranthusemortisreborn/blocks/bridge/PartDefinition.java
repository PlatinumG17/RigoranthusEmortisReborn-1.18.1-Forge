package com.platinumg17.rigoranthusemortisreborn.blocks.bridge;

import net.minecraft.util.StringRepresentable;

public enum PartDefinition implements StringRepresentable {
    END("end"),
    MIDDLE("middle"),
    END2("end2");

    private final String name;

    private PartDefinition(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getSerializedName() {
        return this.name;
    }
}