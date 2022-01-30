package com.platinumg17.rigoranthusemortisreborn.magica.common.block;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.item.IScribeable;
import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.TableTile;
import com.platinumg17.rigoranthusemortisreborn.magica.common.lib.LibBlockNames;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;

public class TableBlock extends TickableModBlock {
    public static final EnumProperty<BedPart> PART = BlockStateProperties.BED_PART;
    protected static final VoxelShape BASE = box(0.0D, 0D, 0.0D, 16.0D, 16, 16.0D);
    protected static final VoxelShape LEG_NORTH_WEST = box(0.0D, 0.0D, 0.0D, 3.0D, 3.0D, 3.0D);
    protected static final VoxelShape LEG_SOUTH_WEST = box(0.0D, 0.0D, 16.0D, 3.0D, 3.0D, 16.0D);
    protected static final VoxelShape LEG_NORTH_EAST = box(16.0D, 0.0D, 0.0D, 16.0D, 3.0D, 3.0D);
    protected static final VoxelShape LEG_SOUTH_EAST = box(16.0D, 0.0D, 16.0D, 16.0D, 3.0D, 16.0D);
    protected static final VoxelShape NORTH_SHAPE = Shapes.or(BASE, LEG_NORTH_WEST, LEG_NORTH_EAST);
    protected static final VoxelShape SOUTH_SHAPE = Shapes.or(BASE, LEG_SOUTH_WEST, LEG_SOUTH_EAST);
    protected static final VoxelShape WEST_SHAPE = Shapes.or(BASE, LEG_NORTH_WEST, LEG_SOUTH_WEST);
    protected static final VoxelShape EAST_SHAPE = Shapes.or(BASE, LEG_NORTH_EAST, LEG_SOUTH_EAST);

    public TableBlock() {
        super(Block.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(2.0f, 3.0f).noOcclusion(), LibBlockNames.TABLE_BLOCK);
        this.registerDefaultState(this.stateDefinition.any().setValue(PART, BedPart.FOOT));
        MinecraftForge.EVENT_BUS.register(this);
    }
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        if(handIn != InteractionHand.MAIN_HAND || !(world.getBlockEntity(pos) instanceof TableTile))
            return InteractionResult.PASS;
        TableTile tile = (TableTile) world.getBlockEntity(pos);
        if(state.getValue(TableBlock.PART) != BedPart.HEAD) {
            BlockEntity tileEntity = world.getBlockEntity(pos.relative(TableBlock.getConnectedDirection(state)));
            tile = tileEntity instanceof TableTile ? (TableTile) tileEntity : null;
            if(tile == null)
                return InteractionResult.PASS;
        }
        if(!player.isShiftKeyDown()) {
            if (tile.stack != null && player.getItemInHand(handIn).isEmpty()) {
                ItemEntity item = new ItemEntity(world, player.getX(), player.getY(), player.getZ(), tile.stack);
                world.addFreshEntity(item);
                tile.stack = null;
            } else if (!player.inventory.getSelected().isEmpty()) {
                if(tile.stack != null){
                    ItemEntity item = new ItemEntity(world, player.getX(), player.getY(), player.getZ(), tile.stack);
                    world.addFreshEntity(item);
                }
                tile.stack = player.inventory.removeItem(player.inventory.selected, 1);
            }
            BlockState updateState = world.getBlockState(tile.getBlockPos());
            world.sendBlockUpdated(tile.getBlockPos(), updateState, updateState, 2);
        }
        if(player.isShiftKeyDown()){
            ItemStack stack = tile.stack;
            if(stack == null || stack.isEmpty())
                return InteractionResult.SUCCESS;
            if(stack.getItem() instanceof IScribeable){
                ((IScribeable) stack.getItem()).onScribe(world,pos,player,handIn, stack);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        super.playerWillDestroy(worldIn, pos, state, player);
        if(worldIn.getBlockEntity(pos) instanceof TableTile && ((TableTile) worldIn.getBlockEntity(pos)).stack != null){
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), ((TableTile) worldIn.getBlockEntity(pos)).stack));
        }
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        if (!world.isClientSide) {
            BlockPos blockpos = pos.relative(state.getValue(FACING));
            world.setBlock(blockpos, state.setValue(PART, BedPart.HEAD), 3);
            world.blockUpdated(pos, Blocks.AIR);
            state.updateNeighbourShapes(world, pos, 3);
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction direction = ctx.getHorizontalDirection();
        BlockPos blockpos = ctx.getClickedPos();
        BlockPos blockpos1 = blockpos.relative(direction);
        return ctx.getLevel().getBlockState(blockpos1).canBeReplaced(ctx) ? this.defaultBlockState().setValue(FACING, direction) : null;
    }

    public static Direction getFacingFromEntity(BlockPos clickedBlock, LivingEntity entity) {
        Vec3 vec = entity.position;
        Direction direction = Direction.getNearest((float) (vec.x - clickedBlock.getX()), (float) (vec.y - clickedBlock.getY()), (float) (vec.z - clickedBlock.getZ()));
        if(direction == Direction.UP || direction == Direction.DOWN)
            direction = Direction.NORTH;
        return direction;
    }
    // If the user breaks the other side of the table, this side needs to drop its item
    public BlockState tearDown(BlockState state, Direction direction, BlockState state2, LevelAccessor world, BlockPos pos, BlockPos pos2){
        if(!world.isClientSide()) {
            BlockEntity entity = world.getBlockEntity(pos);
            if (entity instanceof TableTile && ((TableTile) entity).stack != null) {
                world.addFreshEntity(new ItemEntity((Level) world, pos.getX(), pos.getY(), pos.getZ(), ((TableTile) entity).stack));
            }
        }
        return Blocks.AIR.defaultBlockState();
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState state2, LevelAccessor world, BlockPos pos, BlockPos pos2) {
        if (direction == getNeighbourDirection(state.getValue(PART), state.getValue(FACING))) {
            return state2.is(this) && state2.getValue(PART) != state.getValue(PART) ? state : tearDown(state, direction, state2, world, pos, pos2);
        } else {
            return super.updateShape(state, direction, state2, world, pos, pos2);
        }
    }
    private static Direction getNeighbourDirection(BedPart p_208070_0_, Direction p_208070_1_) {
        return p_208070_0_ == BedPart.FOOT ? p_208070_1_ : p_208070_1_.getOpposite();
    }


    @SubscribeEvent
    public void rightClick(PlayerInteractEvent.RightClickBlock event) {
        if(!(event.getWorld().getBlockEntity(event.getPos()) instanceof TableTile))
            return;
        Level world = event.getWorld();
        BlockPos pos = event.getPos();
        if(world.getBlockState(pos).getBlock() instanceof TableBlock){
            BlockRegistry.TABLE_BLOCK.use(world.getBlockState(pos), world, pos, event.getPlayer(), event.getHand(), null);
            event.setCanceled(true);
        }
    }

    public static Direction getConnectedDirection(BlockState p_226862_0_) {
        Direction direction = p_226862_0_.getValue(FACING);
        return p_226862_0_.getValue(PART) == BedPart.HEAD ? direction.getOpposite() : direction;
    }
    public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext ctx) {
        Direction direction = getConnectedDirection(state).getOpposite();
        switch(direction) {
            case NORTH:
                return NORTH_SHAPE;
            case SOUTH:
                return SOUTH_SHAPE;
            case WEST:
                return WEST_SHAPE;
            default:
                return EAST_SHAPE;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PART);
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TableTile(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}