package com.platinumg17.rigoranthusemortisreborn.magica.common.block;

import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.IchorJarTile;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

public class IchorJar extends IchorBlock implements EntityBlock {
    public static final VoxelShape JAR_SHAPE = Stream.of(
            Block.box(6, 14, 10, 10, 15, 11),
            Block.box(7, 0, 1, 9, 2, 2),
            Block.box(7, 1, 2, 9, 2, 5),
            Block.box(5, 1, 4, 7, 2, 5),
            Block.box(9, 1, 4, 12, 2, 5),
            Block.box(1, 0, 7, 2, 2, 9),
            Block.box(2, 1, 7, 5, 2, 9),
            Block.box(4, 1, 9, 5, 2, 11),
            Block.box(4, 1, 4, 5, 2, 7),
            Block.box(7, 0, 14, 9, 2, 15),
            Block.box(7, 1, 11, 9, 2, 14),
            Block.box(9, 1, 11, 11, 2, 12),
            Block.box(4, 1, 11, 7, 2, 12),
            Block.box(14, 0, 7, 15, 2, 9),
            Block.box(11, 1, 7, 14, 2, 9),
            Block.box(11, 1, 5, 12, 2, 7),
            Block.box(11, 1, 9, 12, 2, 12),
            Block.box(14, 12, 5, 15, 13, 11),
            Block.box(5, 12, 14, 11, 13, 15),
            Block.box(1, 12, 5, 2, 13, 11),
            Block.box(5, 12, 1, 11, 13, 2),
            Block.box(13, 2, 7, 14, 7, 9),
            Block.box(7, 2, 13, 9, 7, 14),
            Block.box(7, 8, 13, 9, 13, 14),
            Block.box(13, 8, 7, 14, 13, 9),
            Block.box(3, 7, 2, 14, 8, 3),
            Block.box(13, 7, 3, 14, 8, 14),
            Block.box(2, 7, 13, 13, 8, 14),
            Block.box(2, 7, 2, 3, 8, 13),
            Block.box(2, 2, 7, 3, 7, 9),
            Block.box(7, 2, 2, 9, 7, 3),
            Block.box(7, 8, 2, 9, 13, 3),
            Block.box(2, 8, 7, 3, 13, 9),
            Block.box(12, 2, 4, 13, 14, 13),
            Block.box(4, 15, 4, 12, 17, 5),
            Block.box(4, 15, 11, 12, 17, 12),
            Block.box(3, 2, 12, 12, 14, 13),
            Block.box(5, 15, 5, 11, 18, 11),
            Block.box(11, 15, 5, 12, 17, 11),
            Block.box(4, 15, 5, 5, 17, 11),
            Block.box(3, 2, 3, 4, 14, 12),
            Block.box(4, 2, 3, 13, 14, 4),
            Block.box(10, 14, 5, 11, 15, 11),
            Block.box(5, 14, 5, 6, 15, 11),
            Block.box(6, 14, 5, 10, 15, 6)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return JAR_SHAPE;
    }
    public static final Property<Integer> fill = IntegerProperty.create("fill", 0, 11);
    public IchorJar() {
        super(defaultProperties().noOcclusion(),"ichor_jar");
    }
    public IchorJar(Properties properties, String registryName){
        super(properties, registryName);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new IchorJarTile(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_149645_1_) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) { builder.add(IchorJar.fill); }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level worldIn, BlockPos pos) {
        IchorJarTile tile = (IchorJarTile) worldIn.getBlockEntity(pos);
        if (tile == null || tile.getCurrentIchor() <= 0) return 0;
        int step = (tile.getMaxIchor() - 1) / 14;
        return (tile.getCurrentIchor() - 1) / step + 1;
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        return super.use(state,worldIn,pos,player,handIn,hit);
    }
//    @Override
//    public InteractionResultHolder use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
//        if(worldIn.isClientSide)
//            return InteractionResult.SUCCESS;
//
//        IchorJarTile tile = (IchorJarTile) worldIn.getBlockEntity(pos);
//        if(tile == null)
//            return InteractionResult.SUCCESS;
//        ItemStack stack = player.getItemInHand(handIn);
//        if(stack.getItem() == MagicItemsRegistry.BOTTLE_OF_ICHOR) {
//            if (tile.getCurrentIchor() == 0) {
//                tile.addIchor(100);
//                if(!player.isCreative()) {
//                    player.setItemInHand(handIn, new ItemStack(Items.GLASS_BOTTLE));
//                    stack.shrink(1);
//                }
//            }
//            else if(tile.getCurrentIchor() < tile.getMaxIchor()){
//                tile.addIchor(100);
//                if(!player.isCreative()) {
//                    player.setItemInHand(handIn, new ItemStack(Items.GLASS_BOTTLE));
//                    stack.shrink(1);
//                }
//            }
//            worldIn.sendBlockUpdated(pos, worldIn.getBlockState(pos), worldIn.getBlockState(pos), 3);
//        }
//        if(stack.getItem() == ItemInit.BUCKET_OF_CADAVEROUS_ICHOR.get()) {
//            if (tile.getCurrentIchor() == 0) {
//                player.level.playSound(null, player.blockPosition(), SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 1.0f, 1.0f);
//                tile.addIchor(1000);
//                if(!player.isCreative()) {
//                    player.setItemInHand(handIn, new ItemStack(Items.BUCKET));
//                    stack.shrink(1);
//                }
//            }
//            else if(tile.getCurrentIchor() < tile.getMaxIchor()){
//                tile.addIchor(1000);
//                player.level.playSound(null, player.blockPosition(), SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 1.0f, 1.0f);
//                if(!player.isCreative()) {
//                    player.setItemInHand(handIn, new ItemStack(Items.BUCKET));
//                    stack.shrink(1);
//                }
//            }
//            worldIn.sendBlockUpdated(pos, worldIn.getBlockState(pos), worldIn.getBlockState(pos), 3);
//        }
//        if(stack.getItem() == Items.GLASS_BOTTLE && tile.getCurrentIchor() >= 100){
//            ItemStack ichor = new ItemStack(MagicItemsRegistry.BOTTLE_OF_ICHOR);
//            player.setItemInHand(handIn, ichor);
//            player.getItemInHand(handIn).shrink(1);
//            tile.removeIchor(100);
//        }
//        if(stack.getItem() == Items.BUCKET && tile.getCurrentIchor() >= 1000){
//            ItemStack ichor = new ItemStack(ItemInit.BUCKET_OF_CADAVEROUS_ICHOR.get());
//            player.level.playSound(null, player.blockPosition(), SoundEvents.BUCKET_FILL, SoundSource.PLAYERS, 1.0f, 1.0f);
//            player.getItemInHand(handIn).shrink(1);
//            player.setItemInHand(handIn, ichor);
//            tile.removeIchor(1000);
//        }
//        return super.use(state,worldIn,pos,player,handIn,hit);
//    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if(stack.getTag() == null)
            return;
        int ichor = stack.getTag().getCompound("BlockEntityTag").getInt("ichor");
        tooltip.add( new TextComponent((ichor*100) / 10000 + "% full"));
    }
}
