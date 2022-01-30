package com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile;

import com.platinumg17.rigoranthusemortisreborn.api.RigoranthusEmortisRebornAPI;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.client.ITooltipProvider;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.ritual.AbstractRitual;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.SpellContext;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.SpellStats;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.interfaces.ILightable;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.DominionUtil;
import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.GlowParticleData;
import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.ParticleColor;
import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.ParticleUtil;
import com.platinumg17.rigoranthusemortisreborn.magica.common.block.RitualVesselBlock;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RitualTile extends AnimatedTile implements ITooltipProvider, IAnimatable, ILightable, Container {
    private final LazyOptional<IItemHandler> itemHandler = LazyOptional.of(() -> new InvWrapper(this));
    public float frames;
    public ItemEntity entity;
    public ItemStack stack;

    public AbstractRitual ritual;
    AnimationFactory manager = new AnimationFactory(this);
    public boolean isDecorative;
    int red;
    int blue;
    int green;
    public boolean isOff;

    public RitualTile(BlockPos pos, BlockState state) {
        super(BlockRegistry.RITUAL_TILE, pos, state);
    }

    public void makeParticle(ParticleColor centerColor, ParticleColor outerColor, int intensity){
        Level world = getLevel();
        BlockPos pos = getBlockPos();
        Random rand = world.random;
        double xzOffset = 0.25;
        for(int i =0; i < intensity; i++){
            world.addParticle(
                    GlowParticleData.createData(centerColor),
                    pos.getX() +0.5 + ParticleUtil.inRange(-xzOffset/2, xzOffset/2)  , pos.getY() + 1 + ParticleUtil.inRange(-0.05, 0.2) , pos.getZ() +0.5 + ParticleUtil.inRange(-xzOffset/2, xzOffset/2),
                    0, ParticleUtil.inRange(0.0, 0.05f),0);
        }
        for(int i =0; i < intensity; i++){
            world.addParticle(
                    GlowParticleData.createData(outerColor),
                    pos.getX() +0.5 + ParticleUtil.inRange(-xzOffset, xzOffset)  , pos.getY() +1 + ParticleUtil.inRange(0, 0.7) , pos.getZ() +0.5 + ParticleUtil.inRange(-xzOffset, xzOffset),
                    0, ParticleUtil.inRange(0.0, 0.05f),0);
        }
    }

    @Override
    public void tick() {
        if(isDecorative && level.isClientSide){
            makeParticle(ParticleColor.makeRandomColor(red, green, blue, level.random), ParticleColor.makeRandomColor(red, green, blue, level.random), 50);
            return;
        }
        if(level.isClientSide && ritual != null){
            makeParticle(ritual.getCenterColor(), ritual.getOuterColor(), ritual.getParticleIntensity());
        }
        if(isOff)
            return;
        if(ritual != null){
            if(ritual.getContext().isDone){
                ritual.onEnd();
                ritual = null;
                getLevel().playSound(null, getBlockPos(), SoundEvents.FIRE_EXTINGUISH, SoundSource.NEUTRAL, 1.0f, 1.0f);
                getLevel().setBlock(getBlockPos(), getLevel().getBlockState(getBlockPos()).setValue(RitualVesselBlock.LIT, false), 3);
                return;
            }
            if(!ritual.isRunning() && !level.isClientSide){
                level.getEntitiesOfClass(ItemEntity.class, new AABB(getBlockPos()).inflate(1)).forEach(i ->{
                    if(ritual.canConsumeItem(i.getItem())){
                        ritual.onItemConsumed(i.getItem());
                        ParticleUtil.spawnPoof((ServerLevel) level, i.blockPosition());
                    }
                });
            }
            if(ritual.consumesDominion() && ritual.needsDominionNow()){
                int cost = ritual.getDominionCost();
                if(DominionUtil.takeDominionNearbyWithParticles(getBlockPos(), getLevel(), 6, cost) != null){
                    ritual.setNeedsDominion(false);
                }else{
                    return;
                }
            }
            ritual.tryTick();
        }
    }
    public boolean isRitualDone(){
        return ritual != null && ritual.getContext().isDone;
    }

    public boolean canRitualStart(){
        return ritual.canStart();
    }

    @Override
    @Nullable
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        setChanged();
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

    public void startRitual(){
        if(ritual == null || !ritual.canStart() || ritual.isRunning())
            return;
        getLevel().playSound(null, getBlockPos(), SoundEvents.ILLUSIONER_CAST_SPELL, SoundSource.NEUTRAL, 1.0f, 1.0f);
        ritual.onStart();
    }

    @Override
    public void load(CompoundTag tag) {
        stack = ItemStack.of((CompoundTag)tag.get("itemStack"));
        super.load(tag);
        String ritualID = tag.getString("ritualID");
        if(!ritualID.isEmpty()){
            ritual = RigoranthusEmortisRebornAPI.getInstance().getRitual(ritualID);
            if(ritual != null) {
                ritual.read(tag);
                ritual.tile = this;
            }
        }else{
            ritual = null;
        }
        this.red = tag.getInt("red");
        this.red = red > 0 ? red : 255;
        this.green = tag.getInt("green");
        green = this.green > 0 ? green : 125;
        this.blue = tag.getInt("blue");
        blue = this.blue > 0 ? blue : 255;
        isDecorative = tag.getBoolean("decorative");
        isOff = tag.getBoolean("off");
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if(stack != null) {
            CompoundTag item = new CompoundTag();
            stack.save(item);
            tag.put("itemStack", item);
        }
        if(ritual != null){
            tag.putString("ritualID", ritual.getID());
            ritual.write(tag);
        }else{
            tag.remove("ritualID");
        }
        tag.putInt("red", red);
        tag.putInt("green", green);
        tag.putInt("blue", blue);
        tag.putBoolean("decorative", isDecorative);
        tag.putBoolean("off", isOff);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return stack == null || stack.isEmpty();
    }

    @Override
    public ItemStack getItem(int slot) {
        return stack == null ? ItemStack.EMPTY : stack;
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        ItemStack toReturn = getItem(0).copy();
        stack.shrink(1);
        updateBlock();
        return toReturn;
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        ItemStack toReturn = getItem(0).copy();
        stack.shrink(1);
        updateBlock();
        return toReturn;
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack s) {
        return stack == null || stack.isEmpty();
    }

    @Override
    public void setItem(int index, ItemStack s) {
        if(stack == null || stack.isEmpty()) {
            stack = s;
            updateBlock();
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        this.stack = ItemStack.EMPTY;
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

    public void setRitual(String selectedRitual) {
        this.ritual = RigoranthusEmortisRebornAPI.getInstance().getRitual(selectedRitual);
        if(ritual != null){
            this.ritual.tile = this;
            Level world = getLevel();
            BlockState state = world.getBlockState(getBlockPos());
            world.setBlock(getBlockPos(), state.setValue(RitualVesselBlock.LIT, true), 3);
        }
        this.isDecorative = false;
        level.playSound(null, getBlockPos(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.NEUTRAL, 1.0f, 1.0f);
    }

    @Override
    public void getTooltip(List<Component> tooltips) {
        if(ritual != null){
            tooltips.add(new TranslatableComponent(ritual.getLangName()));
            if(isOff) {
                tooltips.add(new TranslatableComponent("rigoranthusemortisreborn.tooltip.turned_off"));
            }
            if(!ritual.isRunning()){
                if(!ritual.canStart()){
                    tooltips.add(new TranslatableComponent("rigoranthusemortisreborn.tooltip.conditions_unmet"));
                }else
                    tooltips.add(new TranslatableComponent("rigoranthusemortisreborn.tooltip.waiting"));
            }else{

                tooltips.add(new TranslatableComponent("rigoranthusemortisreborn.tooltip.running"));
            }
            if(ritual.getConsumedItems().size() != 0) {
                tooltips.add(new TranslatableComponent("rigoranthusemortisreborn.tooltip.consumed"));
                for (ItemStack i : ritual.getConsumedItems()) {
                    tooltips.add(i.getHoverName());
                }
            }
            if(ritual.needsDominionNow())
                tooltips.add(new TranslatableComponent("rigoranthusemortisreborn.ritual.need_dominion"));
        }
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "idle", 0, this::idlePredicate));
        animationData.addAnimationController(new AnimationController(this, "gripping", 0, this::gripPredicate));
    }
    private <P extends IAnimatable> PlayState gripPredicate(AnimationEvent<P> pAnimationEvent) {
        if(stack != null)
            pAnimationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("grip", false));
        return PlayState.CONTINUE;
    }
    private <P extends IAnimatable> PlayState idlePredicate(AnimationEvent<P> pAnimationEvent) {
        pAnimationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("gem_float", true));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return manager;
    }

    @Override
    public void onLight(HitResult rayTraceResult, Level world, LivingEntity shooter, SpellStats stats, SpellContext spellContext) {
        this.red = spellContext.colors.r;
        this.green = spellContext.colors.g;
        this.blue = spellContext.colors.b;
        this.isDecorative = true;
        BlockState state = world.getBlockState(getBlockPos());
        world.setBlock(getBlockPos(), state.setValue(RitualVesselBlock.LIT, true), 3);
    }
}