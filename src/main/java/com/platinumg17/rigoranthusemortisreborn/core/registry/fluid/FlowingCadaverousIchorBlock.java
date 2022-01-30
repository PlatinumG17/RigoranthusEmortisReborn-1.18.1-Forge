package com.platinumg17.rigoranthusemortisreborn.core.registry.fluid;

import com.platinumg17.rigoranthusemortisreborn.magica.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.EmptyFluid;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.function.Supplier;

public class FlowingCadaverousIchorBlock extends LiquidBlock {//implements IIchorFog {

//    protected final Vector3d fogColor;
//    protected final float fogDensity;

    public FlowingCadaverousIchorBlock(Supplier<? extends FlowingFluid> fluid, Properties properties) {
        super(fluid, properties);
    }

//    public FlowingCadaverousIchorBlock(Supplier<? extends FlowingFluid> fluid, Vector3d fogColor, float fogDensity, Properties properties) {
//        super(fluid, properties);
//        this.fogColor = fogColor;
//        this.fogDensity = fogDensity;
//    }
//
//    @Override
//    public Vector3d getFogColor(BlockState state, IWorldReader world, BlockPos pos, Entity entity, Vector3d originalColor, float partialTicks) {
//        return fogColor != null ? fogColor : super.getFogColor(state, world, pos, entity, originalColor, partialTicks);
//    }
//
//    @Override
//    public float getRigorFogDensity() {
//        return fogDensity;
//    }
//
//    @Override
//    public Vector3d getRigorFogColor(BlockState state, IWorldReader world, BlockPos pos, Entity entity, Vector3d originalColor, float partialTicks) {
//        return fogColor;
//    }


    public void onBlockAdded(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (this.reactWithNeighbors(worldIn, pos, state)) {
            worldIn.scheduleTick(pos, state.getFluidState().getType(), this.getFluid().getTickDelay(worldIn));
        }
    }

    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (this.reactWithNeighbors(worldIn, pos, state)) {
            worldIn.scheduleTick(pos, state.getFluidState().getType(), this.getFluid().getTickDelay(worldIn));
        }
    }

    private boolean reactWithNeighbors(Level world, BlockPos pos, BlockState state) {
        for (Direction dir : Direction.values()) {
            FluidState otherState = world.getFluidState(pos.relative(dir));
            Fluid otherFluid = otherState.getType();
            if (otherFluid instanceof FlowingFluid) {
                otherFluid = ((FlowingFluid) otherFluid).getSource();
            }
            if (otherFluid instanceof EmptyFluid || otherFluid.equals(this.getFluid())) {
                continue;
            }
            boolean isHot = otherFluid.getAttributes().getTemperature(world, pos.relative(dir)) > 600;
            if (isHot) {
                world.setBlock(pos, ForgeEventFactory.fireFluidPlaceBlockEvent(world, pos, pos, BlockRegistry.DOMINION_GEM_BLOCK.defaultBlockState()), 3);
            }
        }
        return true;
    }
}