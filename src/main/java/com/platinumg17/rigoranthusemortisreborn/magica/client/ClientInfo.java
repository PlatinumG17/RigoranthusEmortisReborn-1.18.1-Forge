package com.platinumg17.rigoranthusemortisreborn.magica.client;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class ClientInfo {
    private ClientInfo(){};

    public static CompoundTag persistentData = new CompoundTag();
    public static int ticksInGame = 0;
    public static List<BlockPos> herb_visionPositions = new ArrayList<>();
}