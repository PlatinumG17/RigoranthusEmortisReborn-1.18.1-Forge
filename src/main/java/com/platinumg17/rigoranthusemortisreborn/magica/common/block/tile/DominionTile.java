package com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.dominion.AbstractDominionTile;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.dominion.ExtractorEventQueue;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.dominion.IDominionTile;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.BlockUtil;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.DominionUtil;
import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.ParticleUtil;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraftforge.eventbus.api.Event;
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

public class DominionTile extends AbstractDominionTile implements IAnimatable {

    int progress;
    public boolean isDisabled = false;
    public boolean registered = false;

    public DominionTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    @Override
    public int getTransferRate() {
        return 1000;
    }

    @Override
    public int getMaxDominion() {
        return 1000;
    }

    @Override
    public void tick() {
        if(level.isClientSide)
            return;
        if(level.getGameTime() % 120 == 0 && usesEventQueue()){
            ExtractorEventQueue.addPosition(level, this.worldPosition);
            registered = true;
        }

        if(level.getGameTime() % 100 == 0 && getCurrentDominion() > 0){
            BlockPos jarPos = DominionUtil.canGiveDominionClosest(worldPosition, level, 5);
            if(jarPos != null){
                transferDominion(this, (IDominionTile) level.getBlockEntity(jarPos));
                ParticleUtil.spawnFollowProjectile(level, this.worldPosition, jarPos);
            }
        }
    }

    public List<SplinteredPedestalTile> getSurroundingPedestals(){
        List<SplinteredPedestalTile> inventories = new ArrayList<>();
        for(BlockPos p : BlockPos.betweenClosed(getBlockPos().below().east().north(), getBlockPos().above().west().south())){
            if(level.getBlockEntity(p) instanceof SplinteredPedestalTile){
                inventories.add((SplinteredPedestalTile) level.getBlockEntity(p));
            }
        }
        return inventories;
    }

    public void getDominionEvent(BlockPos dominionPos, int total){
        this.addDominion(total);
        ParticleUtil.spawnFollowProjectile(level, dominionPos, this.worldPosition);
    }

    public boolean eventInRange(BlockPos dominionPos, @Nullable Event event){
        return BlockUtil.distanceFrom(this.worldPosition, dominionPos) <= 15;
    }

    public boolean usesEventQueue(){
        return false;
    }

    public void doRandomAction(){}

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "rotate_controller", 0, this::idlePredicate));
    }
    AnimationFactory factory = new AnimationFactory(this);
    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        progress = tag.getInt("progress");
        isDisabled = tag.getBoolean("disabled");
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("progress", progress);
        tag.putBoolean("disabled", isDisabled);
    }

    private <E extends BlockEntity & IAnimatable > PlayState idlePredicate(AnimationEvent<E> event) {
        if(this.isDisabled)
            return PlayState.STOP;
        event.getController().setAnimation(new AnimationBuilder().addAnimation("rotation", true));
        return PlayState.CONTINUE;
    }
}