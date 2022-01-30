package com.platinumg17.rigoranthusemortisreborn.blocks.bridge;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.ToIntFunction;
import java.util.stream.Stream;

public class BridgeBase extends Block implements SimpleWaterloggedBlock {
    private static final EnumProperty<PartDefinition> PART = EnumProperty.create("part", PartDefinition.class);
    public static final BooleanProperty WATERLOGGED;
    public static final DirectionProperty FACING;
    public static final BooleanProperty LIT;
    private static final VoxelShape X_AXIS_AABB;
    private static final VoxelShape Z_AXIS_AABB;
    public static ToIntFunction<BlockState> lightLevel;

    public static ToIntFunction<BlockState> getLightLevel(int lightValue) {
        return (state) -> {
            if (BlockStateProperties.LIT == LIT) {
                return (Boolean)state.getValue(BlockStateProperties.LIT) ? lightValue : 0;
            } else {
                return (Boolean)state.getValue(BlockStateProperties.LIT) ? lightValue : 15;
            }
        };
    }

    public BridgeBase(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false).setValue(PART, PartDefinition.MIDDLE).setValue(WATERLOGGED, false));
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor world, BlockPos pos, BlockPos newPos) {
        return this.getStateforBridge(state, world, pos, (Direction)state.getValue(FACING));
    }

    private boolean BridgeDirection(LevelAccessor world, BlockPos source, Direction direction, Direction facing) {
        BlockState state = world.getBlockState(source.relative(direction));
        if (state.getBlock() == this) {
            Direction bridgedir = (Direction)state.getValue(FACING);
            return bridgedir.equals(facing);
        } else {
            return false;
        }
    }

    private BlockState getStateforBridge(BlockState state, LevelAccessor world, BlockPos pos, Direction dir) {
        boolean front = this.BridgeDirection(world, pos, dir.getClockWise(), dir);
        boolean back = this.BridgeDirection(world, pos, dir.getOpposite(), dir);
        if (front && back) {
            return state.setValue(PART, PartDefinition.MIDDLE);
        } else if (front) {
            return state.setValue(PART, PartDefinition.END);
        } else {
            return back ? state.setValue(PART, PartDefinition.END2) : state.setValue(PART, PartDefinition.MIDDLE);
        }
    }

    public VoxelShape getShape(BlockState state, BlockGetter blockReader, BlockPos pos, CollisionContext selectionContext) {
        PartDefinition part = (PartDefinition)state.getValue(PART);
        switch((Direction)state.getValue(FACING)) {
            case WEST:
                if (part == PartDefinition.MIDDLE) {return X_AXIS_AABB;}
                return X_AXIS_AABB;
            case EAST:
                if (part == PartDefinition.MIDDLE) {return X_AXIS_AABB;}
                return X_AXIS_AABB;
            case NORTH:
                if (part == PartDefinition.MIDDLE) {return Z_AXIS_AABB;}
                return Z_AXIS_AABB;
            case SOUTH:
                if (part == PartDefinition.MIDDLE) {return Z_AXIS_AABB;}
                return Z_AXIS_AABB;
            default:
                return null;
        }
    }

    public static ResourceLocation location(String name) {
        return RigoranthusEmortisReborn.rl(name);
    }

//    public ToolType getHarvestTool(BlockState state) {
//        return this.material != Material.STONE && this.material != Material.METAL ? ToolType.PICKAXE : ToolType.AXE;
//    }

    public void onBroken(Level worldIn, BlockPos pos) {
        worldIn.destroyBlockProgress(1029, pos, 0);
    }

    public int getHarvestLevel(BlockState state) {
        return 1;
    }

    public boolean hasTileEntity(BlockState state, BlockGetter worldIn, BlockPos pos, NodeEvaluator type) {
        return false;
    }

    public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        super.playerWillDestroy(world, pos, state, player);
        Boolean i = (Boolean)state.getValue(LIT);
        if (i) {
            dropTorch(world, pos);
        }
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public static void dropTorch(Level world, BlockPos pos) {
        popResource(world, pos, new ItemStack(Items.TORCH, 1));
    }

    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        ItemStack itemstack = player.getItemInHand(handIn);
        Item item = itemstack.getItem();
        Boolean i = (Boolean)state.getValue(LIT);
        if (i) {
            state = (BlockState)state.cycle(LIT);
            worldIn.setBlock(pos, state, 2);
            dropTorch(worldIn, pos);
        }

        if (!i && item != Items.LANTERN) {
            return InteractionResult.PASS;
        } else {
            if (item == Items.TORCH && !i) {
                state = (BlockState)state.cycle(LIT);
                worldIn.setBlock(pos, state, 2);
                if (!player.abilities.instabuild) {
                    itemstack.shrink(1);
                }
            }
            if ((Boolean)state.getValue(WATERLOGGED)) {
                worldIn.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
            }
            return InteractionResult.sidedSuccess(worldIn.isClientSide);
        }
    }

    public int getLightValue(BlockState state, BlockGetter world, BlockPos pos) {
        Boolean i = (Boolean)state.getValue(LIT);
        return i ? 14 : 0;
    }

    public FluidState getFluidState(BlockState state) {
        return (Boolean)state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public float lightValue(BlockState state, BlockGetter reader, BlockPos pos) {
        return 1.0F;
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return (BlockState)state.setValue(FACING, rot.rotate((Direction)state.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, WATERLOGGED, PART, LIT});
    }

    static {
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        FACING = HorizontalDirectionalBlock.FACING;
        LIT = BlockStateProperties.LIT;
        X_AXIS_AABB = (VoxelShape) Stream.of(Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)).reduce((v1, v2) -> {
            return Shapes.join(v1, v2, BooleanOp.OR);
        }).get();
        Z_AXIS_AABB = (VoxelShape)Stream.of(Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)).reduce((v1, v2) -> {
            return Shapes.join(v1, v2, BooleanOp.OR);
        }).get();
        lightLevel = (BlockState) -> {
            return 5;
        };
    }
}