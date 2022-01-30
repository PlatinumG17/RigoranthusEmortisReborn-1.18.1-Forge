package com.platinumg17.rigoranthusemortisreborn.magica.common.block;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.recipe.IIchoricRecipe;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.DominionUtil;
import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.EmorticCraftingPressTile;
import com.platinumg17.rigoranthusemortisreborn.magica.common.util.PortUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class EmorticCraftingPressBlock extends ModBlock implements EntityBlock {
    public static final Property<Integer> stage = IntegerProperty.create("stage", 1, 31);

    public EmorticCraftingPressBlock() {
        super(ModBlock.defaultProperties().noOcclusion(),"emortic_crafting_press");
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult rayTraceResult) {
        if (world.isClientSide || handIn != InteractionHand.MAIN_HAND)
            return InteractionResult.SUCCESS;
        EmorticCraftingPressTile tile = (EmorticCraftingPressTile) world.getBlockEntity(pos);
        if (tile.isCrafting)
            return InteractionResult.PASS;

        if (tile.baseMaterial != null && !tile.baseMaterial.isEmpty() && player.getItemInHand(handIn).isEmpty()) {
            ItemEntity item = new ItemEntity(world, player.getX(), player.getY(), player.getZ(), tile.baseMaterial);
            world.addFreshEntity(item);
            tile.baseMaterial = ItemStack.EMPTY;
        }
        else if (!player.inventory.getSelected().isEmpty()) {

            if (tile.baseMaterial == null || tile.baseMaterial.isEmpty()) {
                if (!player.inventory.getSelected().isEmpty()) {
                    tile.baseMaterial = player.inventory.removeItem(player.inventory.selected, 1);
                }
            }
            else if(tile.baseMaterial != null && !tile.baseMaterial.isEmpty()) {
                if(tile.reagentItem != null && !tile.reagentItem.isEmpty()){
                    ItemEntity item = new ItemEntity(world, player.getX(), player.getY(), player.getZ(), tile.reagentItem);
                    world.addFreshEntity(item);
                }
                tile.reagentItem = player.inventory.removeItem(player.inventory.selected, 1);
                if(!tile.isCrafting && player.inventory.add(tile.reagentItem)) {
                    tile.reagentItem = ItemStack.EMPTY;
                }
                IIchoricRecipe recipe = tile.getRecipe(player.getMainHandItem(), tile.baseMaterial, player);
                if (recipe == null) {
                    PortUtil.sendMessage(player, new TranslatableComponent("rigoranthusemortisreborn.amalgamator.norecipe"));
                } else if (recipe.consumesDominion() && !DominionUtil.hasDominionNearby(tile.getBlockPos(), tile.getLevel(), 10, recipe.dominionCost())) {
                    PortUtil.sendMessage(player, new TranslatableComponent("rigoranthusemortisreborn.amalgamator.nodominion"));
                } else {
                    if (tile.attemptCraft(player.getMainHandItem(), tile.baseMaterial, player)) {
                        tile.reagentItem = player.inventory.removeItem(player.inventory.selected, 1);
                    }
                }
            }
            else {
                tile.reagentItem = ItemStack.EMPTY;
                if (tile.attemptCraft(player.getMainHandItem(), tile.baseMaterial, player)) {
                    tile.reagentItem = player.inventory.removeItem(player.inventory.selected, 1);
                }
            }
        }
        world.sendBlockUpdated(pos, state, state, 2);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        super.playerWillDestroy(worldIn, pos, state, player);
        if(!(worldIn.getBlockEntity(pos) instanceof EmorticCraftingPressTile) || worldIn.isClientSide)
            return;
        EmorticCraftingPressTile tile = ((EmorticCraftingPressTile) worldIn.getBlockEntity(pos));
        if(tile.baseMaterial != null && !tile.baseMaterial.isEmpty()){
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), tile.baseMaterial));
            if(tile.reagentItem != null && !tile.reagentItem.isEmpty()){
                worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), tile.reagentItem));
            }
        }
    }

    @Override public RenderShape getRenderShape(BlockState state) { return RenderShape.ENTITYBLOCK_ANIMATED; }
    @Override protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) { builder.add(stage); }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) { return new EmorticCraftingPressTile(pos, state); }
    public static final VoxelShape SHAPE = Stream.of(
            Block.box(3, 15.025, 3, 13, 16, 13),
            Block.box(3, 0, 3, 13, 1, 13),
            Block.box(13, 0, 11, 14, 2, 12),
            Block.box(13, 0, 4, 14, 2, 5),
            Block.box(11, 0, 13, 12, 2, 14),
            Block.box(4, 0, 13, 5, 2, 14),
            Block.box(11, 0, 2, 12, 2, 3),
            Block.box(4, 0, 2, 5, 2, 3),
            Block.box(2, 0, 11, 3, 2, 12),
            Block.box(2, 0, 4, 3, 2, 5),
            Block.box(2, 15, 11, 3, 16, 12),
            Block.box(4, 15, 13, 5, 16, 14),
            Block.box(2, 15, 4, 3, 16, 5),
            Block.box(4, 15, 2, 5, 16, 3),
            Block.box(13, 15, 4, 14, 16, 5),
            Block.box(13, 15, 11, 14, 16, 12),
            Block.box(14, 15, 11, 15, 16, 13),
            Block.box(14, 15, 3, 15, 16, 5),
            Block.box(1, 15, 3, 2, 16, 5),
            Block.box(1, 15, 11, 2, 16, 13),
            Block.box(11, 15, 2, 12, 16, 3),
            Block.box(11, 15, 1, 13, 16, 2),
            Block.box(3, 15, 1, 5, 16, 2),
            Block.box(11, 15, 14, 13, 16, 15),
            Block.box(3, 15, 14, 5, 16, 15),
            Block.box(11, 0, 14, 13, 1, 15),
            Block.box(3, 0, 14, 5, 1, 15),
            Block.box(14, 0, 11, 15, 1, 13),
            Block.box(1, 0, 11, 2, 1, 13),
            Block.box(1, 0, 3, 2, 1, 5),
            Block.box(3, 0, 1, 5, 1, 2),
            Block.box(11, 0, 1, 13, 1, 2),
            Block.box(14, 0, 3, 15, 1, 5),
            Block.box(11, 15, 13, 12, 16, 14),
            Block.box(1, 0, 1, 3, 3, 3),
            Block.box(1, 0, 13, 3, 3, 15),
            Block.box(13, 0, 13, 15, 3, 15),
            Block.box(13, 0, 1, 15, 3, 3),
            Block.box(13, 13, 1, 15, 16, 3),
            Block.box(1, 13, 1, 3, 16, 3),
            Block.box(1, 13, 13, 3, 16, 15),
            Block.box(13, 13, 13, 15, 16, 15)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    @Override public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) { return SHAPE; }
}