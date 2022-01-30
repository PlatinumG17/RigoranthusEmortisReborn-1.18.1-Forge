package com.platinumg17.rigoranthusemortisreborn.api.apimagic.dominion;

import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.DominionTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.eventbus.api.Event;

import java.util.*;

public class ExtractorEventQueue {

    public static Map<String, Set<BlockPos>> posMap = new HashMap<>();

    public static void addPosition(Level world, BlockPos pos) {
        String key = world.dimension().getRegistryName().toString();
        if (!posMap.containsKey(key))
            posMap.put(key, new HashSet<>());
        posMap.get(key).add(pos);
    }

    public static void addDominionEvent(Level world, Class<? extends DominionTile> tileType, int amount, Event event, BlockPos sourcePos) {
        List<BlockPos> stalePos = new ArrayList<>();
        Set<BlockPos> worldList = posMap.getOrDefault(world.dimension().getRegistryName().toString(), new HashSet<>());
        for (BlockPos p : worldList) {
            BlockEntity entity = world.getBlockEntity(p);
            if (world.getBlockEntity(p) == null || !(entity instanceof DominionTile)) {
                stalePos.add(p);
                continue;
            }
            if (entity.getClass().equals(tileType) && ((DominionTile) entity).eventInRange(sourcePos, event) && ((DominionTile) entity).canAcceptDominion()) {
                ((DominionTile) entity).getDominionEvent(sourcePos, amount);
                break;
            }
        }
        for (BlockPos p : stalePos) {
            worldList.remove(p);
        }
    }
}