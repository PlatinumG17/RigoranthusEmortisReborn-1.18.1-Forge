package com.platinumg17.rigoranthusemortisreborn.blocks.tileentity;

import com.platinumg17.rigoranthusemortisreborn.blocks.tileentity.smeltery.RigoranthusTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class RESignEntity extends SignBlockEntity {
    public RESignEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(pWorldPosition, pBlockState);
    }
    @Override
    public BlockEntityType<?> getType() {
        return RigoranthusTileEntities.RE_SIGN_ENTITIES.get();
    }
}