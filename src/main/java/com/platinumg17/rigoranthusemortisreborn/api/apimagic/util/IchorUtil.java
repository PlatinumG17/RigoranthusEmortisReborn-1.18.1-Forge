package com.platinumg17.rigoranthusemortisreborn.api.apimagic.util;

import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.IchorJarTile;
import com.platinumg17.rigoranthusemortisreborn.magica.common.entity.EntityFollowProjectile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IchorUtil {

    /**
     * Searches for nearby ichor jars that have enough ichor.
     * Returns the position where the ichor was taken, or null if none were found.
     */
    @Nullable
    public static BlockPos takeIchorNearby(BlockPos pos, Level world, int range, int ichor){
        Optional<BlockPos> loc = BlockPos.findClosestMatch(pos, range, range, (b) -> world.getBlockEntity(b) instanceof IchorJarTile && ((IchorJarTile) world.getBlockEntity(b)).getCurrentIchor() >= ichor);
        if(!loc.isPresent())
            return null;
        IchorJarTile tile = (IchorJarTile) world.getBlockEntity(loc.get());
        tile.removeIchor(ichor);
        return loc.get();
    }

    public static @Nullable BlockPos takeIchorNearbyWithParticles(BlockPos pos, Level world, int range, int ichor){
        BlockPos result = takeIchorNearby(pos,world,range,ichor);
        if(result != null){
            EntityFollowProjectile aoeProjectile = new EntityFollowProjectile(world, result, pos);
            world.addFreshEntity(aoeProjectile);
        }
        return result;
    }

    /**
     * Searches for nearby ichor jars that have enough ichor.
     * Returns the position where the ichor was taken, or null if none were found.
     */
    public static boolean hasIchorNearby(BlockPos pos, Level world, int range, int ichor){
        Optional<BlockPos> loc = BlockPos.findClosestMatch(pos, range, range, (b) -> world.getBlockEntity(b) instanceof IchorJarTile && ((IchorJarTile) world.getBlockEntity(b)).getCurrentIchor() >= ichor);
        return loc.isPresent();
    }

    @Nullable
    public static BlockPos canGiveIchorClosest(BlockPos pos, Level world, int range){
        Optional<BlockPos> loc = BlockPos.findClosestMatch(pos, range, range, (b) ->  world.getBlockEntity(b) instanceof IchorJarTile && ((IchorJarTile) world.getBlockEntity(b)).canAcceptIchor());
        return loc.orElse(null);
    }

    public static List<BlockPos> canGiveIchorAny(BlockPos pos, Level world, int range){
        List<BlockPos> posList = new ArrayList<>();
        BlockPos.withinManhattanStream(pos, range, range, range).forEach(b ->{
            if(world.getBlockEntity(b) instanceof IchorJarTile && ((IchorJarTile) world.getBlockEntity(b)).canAcceptIchor())
                posList.add(b.immutable());
        });
        return posList;
    }
}