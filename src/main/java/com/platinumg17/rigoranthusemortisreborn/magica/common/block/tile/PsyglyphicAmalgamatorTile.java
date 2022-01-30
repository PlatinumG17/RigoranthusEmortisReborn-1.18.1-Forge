package com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile;

import com.platinumg17.rigoranthusemortisreborn.api.RigoranthusEmortisRebornAPI;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.psyglyphic_amalgamator.IPsyglyphicRecipe;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.DominionUtil;
import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.ParticleUtil;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class PsyglyphicAmalgamatorTile extends AnimatedTile implements Container {
    private final LazyOptional<IItemHandler> itemHandler = LazyOptional.of(() -> new InvWrapper(this));
    public ItemStack catalystItem = ItemStack.EMPTY;
    public ItemEntity entity;
    public long frames = 0;

    private int craftingLength = 100;
    public boolean isCrafting;

    public PsyglyphicAmalgamatorTile(BlockPos pos, BlockState state) {
        super(BlockRegistry.PSYGLYPHIC_AMALG_TILE, pos, state);
        counter = 1;
    }

    @Override
    public void tick() {
        if(level.isClientSide)
            return;
        if(isCrafting){
            if(this.getRecipe(catalystItem, null) == null)
                this.isCrafting = false;
            counter += 1;
        }
        if(counter > craftingLength) {
            counter = 1;
            if (this.isCrafting) {
                IPsyglyphicRecipe recipe = this.getRecipe(catalystItem, null);
                List<ItemStack> pedestalItems = getPedestalItems();
                if (recipe != null) {
                    pedestalItems.forEach(i -> i = null);
                    this.catalystItem = recipe.getResult(pedestalItems, this.catalystItem, this);
                    clearItems();
                    ParticleUtil.spawnPoof((ServerLevel) level, worldPosition);
                }
                this.isCrafting = false;
            }
            updateBlock();
        }
    }

    public void clearItems(){
        BlockPos.betweenClosedStream(this.getBlockPos().offset(5, -3, 5), this.getBlockPos().offset(-5, 3, -5)).forEach(blockPos -> {
            if (level.getBlockEntity(blockPos) instanceof SplinteredPedestalTile && ((SplinteredPedestalTile) level.getBlockEntity(blockPos)).stack != null) {
                SplinteredPedestalTile tile = ((SplinteredPedestalTile) level.getBlockEntity(blockPos));
                tile.stack = tile.stack == null ? ItemStack.EMPTY : tile.stack.getContainerItem();
                BlockState state = level.getBlockState(blockPos);
                level.sendBlockUpdated(blockPos, state, state, 3);
            }
        });
    }

    // Used for rendering on the client
    public List<BlockPos> pedestalList(){
        ArrayList<BlockPos> posList = new ArrayList<>();
        BlockPos.betweenClosedStream(this.getBlockPos().offset(5, -3, 5), this.getBlockPos().offset(-5, 3, -5)).forEach(blockPos -> {
            if(level.getBlockEntity(blockPos) instanceof SplinteredPedestalTile && ((SplinteredPedestalTile) level.getBlockEntity(blockPos)).stack != null &&  !((SplinteredPedestalTile) level.getBlockEntity(blockPos)).stack.isEmpty()) {
                posList.add(blockPos.immutable());
            }
        });
        return posList;
    }

    public List<ItemStack> getPedestalItems(){
        ArrayList<ItemStack> pedestalItems = new ArrayList<>();
        BlockPos.betweenClosedStream(this.getBlockPos().offset(5, -3, 5), this.getBlockPos().offset(-5, 3, -5)).forEach(blockPos -> {
            if(level.getBlockEntity(blockPos) instanceof SplinteredPedestalTile && ((SplinteredPedestalTile) level.getBlockEntity(blockPos)).stack != null && !((SplinteredPedestalTile) level.getBlockEntity(blockPos)).stack.isEmpty()) {
                pedestalItems.add(((SplinteredPedestalTile) level.getBlockEntity(blockPos)).stack);
            }
        });
        return pedestalItems;
    }

    public IPsyglyphicRecipe getRecipe(ItemStack stack, @Nullable Player playerEntity){
        List<ItemStack> pedestalItems = getPedestalItems();
        return RigoranthusEmortisRebornAPI.getInstance().getPsyglyphicAmalgamatorRecipes(level).stream().filter(r-> r.isMatch(pedestalItems, stack, this, playerEntity)).findFirst().orElse(null);
    }

    public boolean attemptCraft(ItemStack catalyst, @Nullable Player playerEntity){
        if(isCrafting)
            return false;
        if (!craftingPossible(catalyst, playerEntity)) {
            return false;
        }
        IPsyglyphicRecipe recipe = this.getRecipe(catalyst, playerEntity);
        DominionUtil.takeDominionNearbyWithParticles(worldPosition, level, 10, recipe.dominionCost());
        this.isCrafting = true;
        updateBlock();
        return true;
    }

    public boolean craftingPossible(ItemStack stack, Player playerEntity){
        if(isCrafting || stack.isEmpty())
            return false;
        IPsyglyphicRecipe recipe = this.getRecipe(stack, playerEntity);
        return recipe != null && (!recipe.consumesDominion() || (recipe.consumesDominion() && DominionUtil.hasDominionNearby(worldPosition, level, 10, recipe.dominionCost())));
    }

    public void updateBlock(){
        if(counter == 0)
            counter = 1;
        BlockState state = level.getBlockState(worldPosition);
        level.sendBlockUpdated(worldPosition, state, state, 2);
    }

    @Override
    public void load(CompoundTag compound) {
        catalystItem = ItemStack.of((CompoundTag)compound.get("itemStack"));
        isCrafting = compound.getBoolean("is_crafting");
        counter = compound.getInt("counter");
        super.load(compound);
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        if(catalystItem != null) {
            CompoundTag reagentTag = new CompoundTag();
            catalystItem.save(reagentTag);
            compound.put("itemStack", reagentTag);
        }
        compound.putBoolean("is_crafting", isCrafting);
        compound.putInt("counter", counter);
    }
    @Override
    @Nullable
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("counter", this.counter);
        tag.putBoolean("is_crafting", this.isCrafting);
        this.saveAdditional(tag);
        return tag;
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getTag() == null ? new CompoundTag() : pkt.getTag());
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return catalystItem.isEmpty();
    }

    @Override
    public ItemStack getItem(int index) {
        if(isCrafting)
            return ItemStack.EMPTY;
        return catalystItem;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        if(isCrafting || stack.isEmpty())
            return false;
        return catalystItem.isEmpty() && craftingPossible(stack,null);
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        if(isCrafting)
            return ItemStack.EMPTY;
        ItemStack stack = catalystItem.copy();
        catalystItem.shrink(count);
        updateBlock();
        return stack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        if(isCrafting)
            return ItemStack.EMPTY;
        return catalystItem;
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        if(isCrafting)
            return;
        this.catalystItem = stack;
        updateBlock();
        attemptCraft(this.catalystItem, null);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }

    @Override
    public void clearContent() {
        this.catalystItem = ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, final @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        itemHandler.invalidate();
        super.invalidateCaps();
    }
}