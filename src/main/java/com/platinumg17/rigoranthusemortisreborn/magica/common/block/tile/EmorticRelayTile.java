package com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.client.ITooltipProvider;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.dominion.AbstractDominionTile;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.item.IWandable;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.BlockUtil;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.NBTUtil;
import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.ParticleUtil;
import com.platinumg17.rigoranthusemortisreborn.magica.common.items.DominionWand;
import com.platinumg17.rigoranthusemortisreborn.magica.common.util.PortUtil;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class EmorticRelayTile extends AbstractDominionTile implements ITooltipProvider, IWandable, IAnimatable {

    public EmorticRelayTile(BlockPos pos, BlockState state) {
        super(BlockRegistry.EMORTIC_RELAY_TILE, pos, state);
    }
    public EmorticRelayTile(BlockEntityType<?> type, BlockPos pos, BlockState state){
        super(type, pos, state);
    }
    public BlockPos getToPos() {
        return toPos;
    }
    public void setToPos(BlockPos toPos) {
        this.toPos = toPos;
    }
    public BlockPos getFromPos() {
        return fromPos;
    }
    public void setFromPos(BlockPos fromPos) {
        this.fromPos = fromPos;
    }

    private BlockPos toPos;
    private BlockPos fromPos;

    public boolean setTakeFrom(BlockPos pos){
        if(BlockUtil.distanceFrom(pos, this.worldPosition) > getMaxDistance()){
            return false;
        }
        this.fromPos = pos;
        update();
        return true;
    }

    public boolean setSendTo(BlockPos pos ){
        if(BlockUtil.distanceFrom(pos, this.worldPosition) > getMaxDistance()){
            return false;
        }
        this.toPos = pos;
        update();
        return true;
    }

    public int getMaxDistance(){
        return 30;
    }

    public void clearPos(){
        this.toPos = null;
        this.fromPos = null;
        update();
    }

    @Override
    public int getTransferRate() {
        return 1000;
    }

    @Override
    public int getMaxDominion() {
        return 1000;
    }

    public boolean closeEnough(BlockPos pos){
        return BlockUtil.distanceFrom(pos, this.worldPosition) <= getMaxDistance();
    }

    @Override
    public void onFinishedConnectionFirst(@Nullable BlockPos storedPos, @Nullable LivingEntity storedEntity, Player playerEntity) {
        if(storedPos == null || level.isClientSide)
            return;
        // Let relays take from us, no action needed.
        if(this.setSendTo(storedPos.immutable())) {
            PortUtil.sendMessage(playerEntity, new TranslatableComponent("rigoranthusemortisreborn.connections.send", DominionWand.getPosString(storedPos)));
            ParticleUtil.beam(storedPos, worldPosition, level);
        }else{
            PortUtil.sendMessage(playerEntity, new TranslatableComponent("rigoranthusemortisreborn.connections.fail"));
        }
    }

    @Override
    public void onFinishedConnectionLast(@Nullable BlockPos storedPos, @Nullable LivingEntity storedEntity, Player playerEntity) {
        if(storedPos == null)
            return;
        if(level.getBlockEntity(storedPos) instanceof EmorticRelayTile)
            return;
        if(this.setTakeFrom(storedPos.immutable())) {
            PortUtil.sendMessage(playerEntity, new TranslatableComponent("rigoranthusemortisreborn.connections.take", DominionWand.getPosString(storedPos)));
        }else{
            PortUtil.sendMessage(playerEntity, new TranslatableComponent("rigoranthusemortisreborn.connections.fail"));
        }
    }

    @Override
    public void onWanded(Player playerEntity) {
        this.clearPos();
        PortUtil.sendMessage(playerEntity,new TranslatableComponent("rigoranthusemortisreborn.connections.cleared"));
    }

    @Override
    public void tick() {
        if(level.isClientSide) {
            return;
        }
        if(level.getGameTime() % 20 != 0)
            return;

        if(fromPos != null) {
            // Block has been removed
            if (!(level.getBlockEntity(fromPos) instanceof AbstractDominionTile)) {
                fromPos = null;
                update();
                return;
            }
            else if (level.getBlockEntity(fromPos) instanceof AbstractDominionTile) {
                // Transfer dominion fromPos to this
                AbstractDominionTile fromTile = (AbstractDominionTile) level.getBlockEntity(fromPos);
                if(transferDominion(fromTile, this) > 0) {
                    update();
                    ParticleUtil.spawnFollowProjectile(level, fromPos, worldPosition);
                }
            }
        }
        if(toPos == null)
            return;
        if(!(level.getBlockEntity(toPos) instanceof AbstractDominionTile)){
            toPos = null;
            update();
            return;
        }
        AbstractDominionTile toTile = (AbstractDominionTile) this.level.getBlockEntity(toPos);
        if(transferDominion(this, toTile) > 0){
            ParticleUtil.spawnFollowProjectile(level, worldPosition, toPos);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        if(NBTUtil.hasBlockPos(tag, "to")){
            this.toPos = NBTUtil.getBlockPos(tag, "to");
        }else{
            toPos = null;
        }
        if(NBTUtil.hasBlockPos(tag, "from")){
            this.fromPos = NBTUtil.getBlockPos(tag, "from");
        }else{
            fromPos = null;
        }
        super.load(tag);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if(toPos != null) {
            NBTUtil.storeBlockPos(tag, "to", toPos);
        }else{
            NBTUtil.removeBlockPos(tag, "to");
        }
        if(fromPos != null) {
            NBTUtil.storeBlockPos(tag, "from", fromPos);
        }else{
            NBTUtil.removeBlockPos(tag, "from");
        }
    }

    @Override
    public void getTooltip(List<Component> tooltip) {
        if(toPos == null){
            tooltip.add(new TranslatableComponent("rigoranthusemortisreborn.relay.no_to"));
        }else{
            tooltip.add(new TranslatableComponent("rigoranthusemortisreborn.relay.one_to", 1));
        }
        if(fromPos == null){
            tooltip.add(new TranslatableComponent("rigoranthusemortisreborn.relay.no_from"));
        }else{
            tooltip.add(new TranslatableComponent("rigoranthusemortisreborn.relay.one_from", 1));
        }
    }
    AnimationFactory factory = new AnimationFactory(this);

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "rotate_controller", 0, this::idlePredicate));
        data.addAnimationController(new AnimationController(this, "float_controller", 0, this::floatPredicate));
    }

    private <P extends IAnimatable> PlayState idlePredicate(AnimationEvent<P> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("floating", true));
        return PlayState.CONTINUE;
    }

    private <P extends IAnimatable> PlayState floatPredicate(AnimationEvent<P> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("rotation", true));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}