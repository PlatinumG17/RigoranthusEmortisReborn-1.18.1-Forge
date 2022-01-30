package com.platinumg17.rigoranthusemortisreborn.api.apimagic.dominion;

import com.platinumg17.rigoranthusemortisreborn.magica.common.block.ITickable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

import static com.platinumg17.rigoranthusemortisreborn.api.apimagic.MagicFluidTags.DOMINION_TAG;
import static com.platinumg17.rigoranthusemortisreborn.api.apimagic.MagicFluidTags.MAX_DOMINION_TAG;

public abstract class AbstractDominionTile extends BlockEntity implements IDominionTile, ITickable {
    private int dominion = 0;
    private int maxDominion = 0;
    public AbstractDominionTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    @Override
    public void load(CompoundTag tag) {
        dominion = tag.getInt(DOMINION_TAG);
        maxDominion = tag.getInt(MAX_DOMINION_TAG);
        super.load(tag);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt(DOMINION_TAG, getCurrentDominion());
        tag.putInt(MAX_DOMINION_TAG, getMaxDominion());
    }

    @Override
    public int setDominion(int dominion) {
        this.dominion = dominion;
        if(this.dominion > this.getMaxDominion())
            this.dominion = this.getMaxDominion();
        if(this.dominion < 0)
            this.dominion = 0;
        update();
        return this.dominion;
    }

    @Override
    public int addDominion(int dominionToAdd) {
        return this.setDominion(this.getCurrentDominion() + dominionToAdd);
    }

    @Override
    public int getCurrentDominion() {
        return this.dominion;
    }

    @Override
    public int removeDominion(int dominionToRemove) {
        this.setDominion(this.getCurrentDominion() - dominionToRemove);
        update();
        return this.getCurrentDominion();
    }

    @Override
    public void setMaxDominion(int max) {
        this.maxDominion = max;
        update();
    }

    public boolean update(){
        if(this.worldPosition != null && this.level != null){
            level.sendBlockUpdated(this.worldPosition, level.getBlockState(worldPosition),  level.getBlockState(worldPosition), 2);
            return true;
        }
        return false;
    }

    @Override
    public int getMaxDominion() {
        return maxDominion;
    }

    public boolean canAcceptDominion(){ return this.getCurrentDominion() < this.getMaxDominion(); }

    public int dominionCanAccept(IDominionTile tile){return tile.getMaxDominion() - tile.getCurrentDominion();}

    public int transferDominion(IDominionTile from, IDominionTile to){
        int transferRate = getTransferRate(from, to);
        from.removeDominion(transferRate);
        to.addDominion(transferRate);
        return transferRate;
    }

    public int getTransferRate(IDominionTile from, IDominionTile to){
        return Math.min(Math.min(from.getTransferRate(), from.getCurrentDominion()), to.getMaxDominion() - to.getCurrentDominion());
    }

    public int transferDominion(IDominionTile from, IDominionTile to, int fromTransferRate){
        int transferRate = getTransferRate(from, to, fromTransferRate);
        if(transferRate == 0)
            return 0;
        from.removeDominion(transferRate);
        to.addDominion(transferRate);
        return transferRate;
    }

    public int getTransferRate(IDominionTile from, IDominionTile to, int fromTransferRate){
        return Math.min(Math.min(fromTransferRate, from.getCurrentDominion()), to.getMaxDominion() - to.getCurrentDominion());
    }

    @Override
    @Nullable
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag);
        return tag;
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getTag() == null ? new CompoundTag() : pkt.getTag());
    }
}
