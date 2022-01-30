package com.platinumg17.rigoranthusemortisreborn.magica.common.block;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.psyglyphic_amalgamator.IPsyglyphicRecipe;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.DominionUtil;
import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.PsyglyphicAmalgamatorTile;
import com.platinumg17.rigoranthusemortisreborn.magica.common.util.PortUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class PsyglyphicAmalgamatorBlock extends ModBlock implements EntityBlock {

    public PsyglyphicAmalgamatorBlock() {
        super(ModBlock.defaultProperties().noOcclusion(),"psyglyphic_amalgamator");
    }

    @Override public void attack(BlockState state, Level worldIn, BlockPos pos, Player player) { super.attack(state, worldIn, pos, player); }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (world.isClientSide || handIn != InteractionHand.MAIN_HAND)
            return InteractionResult.SUCCESS;
        PsyglyphicAmalgamatorTile tile = (PsyglyphicAmalgamatorTile) world.getBlockEntity(pos);
        if (tile.isCrafting)
            return InteractionResult.SUCCESS;
        if (!(world.getBlockState(pos.below()).getBlock() instanceof EmorticCortex)) {
            PortUtil.sendMessage(player, new TranslatableComponent("alert.core"));
            return InteractionResult.SUCCESS;
        }
        if (tile.catalystItem == null || tile.catalystItem.isEmpty()) {
            IPsyglyphicRecipe recipe = tile.getRecipe(player.getMainHandItem(), player);
            if (recipe == null) {
                PortUtil.sendMessage(player, new TranslatableComponent("rigoranthusemortisreborn.amalgamator.norecipe"));
            } else if (recipe.consumesDominion() && !DominionUtil.hasDominionNearby(tile.getBlockPos(), tile.getLevel(), 10, recipe.dominionCost())) {
                PortUtil.sendMessage(player, new TranslatableComponent("rigoranthusemortisreborn.amalgamator.nodominion"));
            } else {
                if(tile.attemptCraft(player.getMainHandItem(), player)) {
                    tile.catalystItem = player.inventory.removeItem(player.inventory.selected, 1);
                }
            }
        } else {
            ItemEntity item = new ItemEntity(world, player.getX(), player.getY(), player.getZ(), tile.catalystItem);
            world.addFreshEntity(item);
            tile.catalystItem = ItemStack.EMPTY;
            if (tile.attemptCraft(player.getMainHandItem(), player)) {
                tile.catalystItem = player.inventory.removeItem(player.inventory.selected, 1);
            }
        }
        world.sendBlockUpdated(pos, state, state, 2);
        return InteractionResult.SUCCESS;
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) { return new PsyglyphicAmalgamatorTile(pos, state); }
    public static final VoxelShape SHAPE = Stream.of(
            Block.box(14, 1.449, 2, 15, 5.7, 14),
            Block.box(13.25, 0.75, 13.25, 16, 7.75, 16),
            Block.box(0, 0.75, 13.25, 2.75, 7.75, 16),
            Block.box(13.25, 0.75, 0, 16, 7.75, 2.75),
            Block.box(0, 0.75, 0, 2.75, 7.75, 2.75),
            Block.box(2, 1.45, 14, 14, 5.7, 15),
            Block.box(2, 1.45, 1, 14, 5.7, 2),
            Block.box(1, 1.45, 2, 2, 5.7, 14),
            Block.box(4, 4.7, 4, 12, 5.7, 12)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    @Override public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) { return SHAPE; }
    @Override public RenderShape getRenderShape(BlockState p_149645_1_) {return RenderShape.MODEL;}

    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        super.playerWillDestroy(worldIn, pos, state, player);
        if (worldIn.getBlockEntity(pos) instanceof PsyglyphicAmalgamatorTile && ((PsyglyphicAmalgamatorTile) worldIn.getBlockEntity(pos)).catalystItem != null) {
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), ((PsyglyphicAmalgamatorTile) worldIn.getBlockEntity(pos)).catalystItem));
        }
    }
}