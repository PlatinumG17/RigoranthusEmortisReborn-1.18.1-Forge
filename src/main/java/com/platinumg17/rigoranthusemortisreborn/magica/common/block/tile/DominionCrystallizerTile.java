package com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.dominion.AbstractDominionTile;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.DominionUtil;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.IchorUtil;
import com.platinumg17.rigoranthusemortisreborn.magica.common.network.Networking;
import com.platinumg17.rigoranthusemortisreborn.magica.common.network.PacketOneShotAnimation;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.BlockRegistry;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.MagicItemsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
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

public class DominionCrystallizerTile extends AbstractDominionTile implements Container, IAnimatable, IAnimationListener {
    private final LazyOptional<IItemHandler> itemHandler = LazyOptional.of(() -> new InvWrapper(this));
    private final AnimationFactory animationFactory = new AnimationFactory(this);
    public String engageController = "engageController";
    public String engageAnim = "press";
    public ItemStack stack = ItemStack.EMPTY;
    public ItemEntity entity;
    public boolean draining = false;
    public int timeAnimating = 0;
    public int timeWaiting = 0;

    public DominionCrystallizerTile(BlockPos pos, BlockState state) {
        super(BlockRegistry.DOMINION_CRYSTALLIZER_TILE, pos, state);
    }

    @Override
    public int getTransferRate() {
        return 0;
    }

    @Override
    public void tick() {
        if(level.isClientSide)
            return;

        if (draining) {
            timeAnimating++;
            if(timeAnimating >= 100) {
                this.draining = false;
                update();
            }
            return;
        }
        if (this.stack.isEmpty() && this.level.getGameTime() % 100 == 0) {
            if(isDrainingIchorPossible()) {
                if(attemptDrainIchor())
                    this.addDominion(100);
                update();
            } else if (isDrainingDominionPossible()) {
                if(attemptDrainDominion())
                    this.addDominion(200);
                update();
            }
        }
        else if (this.level.getGameTime() % 100 == 0) {
            if (draining) {
                draining = false;
            }
        }
        if (this.getCurrentDominion() >= 1000 && (stack == null || stack.isEmpty())) {
//            Item foundItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(Config.CRYSTALLIZER_ITEM.get()));
//            if (foundItem == null) {
//                System.out.println("NULL ICHOR CRYSTALLIZER ITEM.");
//                foundItem = MagicItemsRegistry.dominionGem;
//            }
            this.stack = new ItemStack(MagicItemsRegistry.dominionGem);
            this.setDominion(0);
        }
    }
    @Override
    public boolean update() {
        if(this.worldPosition != null && this.level != null){
            BlockState state = level.getBlockState(worldPosition);
            level.sendBlockUpdated(worldPosition, state, state, 2);
            return true;
        }
        return false;
    }

    public boolean isDrainingDominionPossible() {
        if(draining)
            return false;
        return DominionUtil.hasDominionNearby(worldPosition.below(), level, 0, 200);
    }

    public boolean isDrainingIchorPossible() {
        if(draining)
            return false;
        return IchorUtil.hasIchorNearby(worldPosition.below(), level, 0, 200);
    }

    public boolean attemptDrainDominion() {
        BlockPos jar = DominionUtil.takeDominionNearby(worldPosition.below(), level, 0, 200);
        if (jar != null) {
            if (!draining) {
                draining = true;
                Networking.sendToNearby(level, worldPosition, new PacketOneShotAnimation(worldPosition, NeedleAnimations.NEEDLE_ENGAGE.ordinal()));
            }
            update();
            return true;
        }
        return false;
    }

    public boolean attemptDrainIchor() {
        BlockPos jar = IchorUtil.takeIchorNearby(worldPosition.below(), level, 0, 200);
        if (jar != null) {
            if (!draining) {
                draining = true;
                Networking.sendToNearby(level, worldPosition, new PacketOneShotAnimation(worldPosition, NeedleAnimations.NEEDLE_ENGAGE.ordinal()));
            }
            update();
            return true;
        }
        return false;
    }

    @Override public int getMaxDominion() { return 1000; }
    @Override public int getContainerSize() { return 1; }
    @Override public boolean isEmpty() { return this.stack == null || this.stack.isEmpty(); }
    @Override public ItemStack getItem(int index) { return stack; }
    @Override public void setItem(int index, ItemStack stack) { this.stack = stack; }
    @Override public boolean stillValid(Player player) { return true; }
    @Override public void clearContent() { this.stack = ItemStack.EMPTY; }

    @Override
    public ItemStack removeItem(int index, int count) {
        ItemStack copy = stack.copy();
        stack.shrink(count);
        return copy;
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        ItemStack stack = this.stack.copy();
        this.stack = ItemStack.EMPTY;
        return stack;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, final @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) { return itemHandler.cast(); }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        itemHandler.invalidate();
        super.invalidateCaps();
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, engageController, 1, this::engagePredicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.animationFactory;
    }
    private <E extends BlockEntity & IAnimatable > PlayState engagePredicate(AnimationEvent<E> event) { return PlayState.CONTINUE; }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if(stack != null) {
            CompoundTag reagentTag = new CompoundTag();
            stack.save(reagentTag);
            tag.put("itemStack", reagentTag);
        }
        tag.putInt("counter", this.timeAnimating);
        tag.putInt("waiting", this.timeWaiting);
        tag.putBoolean("draining", draining);
    }

    @Override
    public void load(CompoundTag compound) {
        stack = ItemStack.of((CompoundTag)compound.get("itemStack"));
        timeAnimating = compound.getInt("counter");
        timeWaiting = compound.getInt("waiting");
        draining = compound.getBoolean("draining");
        super.load(compound);
    }

    @Override
    public void startAnimation(int arg) {
        AnimationData engageData = this.animationFactory.getOrCreateAnimationData(this.hashCode());
        AnimationController engageCont = engageData.getAnimationControllers().get(engageController);
        try{
            if (arg == NeedleAnimations.NEEDLE_ENGAGE.ordinal()) {
                engageCont.markNeedsReload();
                engageCont.setAnimation(new AnimationBuilder().addAnimation(engageAnim, false));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public void onAnimationSync(int id, int state) {
//        final AnimationController<?> controllerE = GeckoLibUtil.getControllerForID(this.animationFactory, id, engageController);
//        final AnimationController<?> controllerR = GeckoLibUtil.getControllerForID(this.animationFactory, id, retractController);
//        if (draining) {
//            controllerE.markNeedsReload();
//            controllerE.setAnimation(new AnimationBuilder().addAnimation(engageAnim, false));
//        } else {
//            controllerR.markNeedsReload();
//            controllerR.setAnimation(new AnimationBuilder().addAnimation(retractAnim, false));
//        }
//    }

    public enum NeedleAnimations{ NEEDLE_ENGAGE, NEEDLE_RETRACT }

    @Override
    @Nullable
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        if(stack != null) {
            CompoundTag reagentTag = new CompoundTag();
            stack.save(reagentTag);
            tag.put("itemStack", reagentTag);
        }
        tag.putInt("counter", this.timeAnimating);
        tag.putInt("waiting", this.timeWaiting);
        tag.putBoolean("draining", this.draining);
        this.saveAdditional(tag);
        return tag;
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getTag() == null ? new CompoundTag() : pkt.getTag());
    }
}