package com.platinumg17.rigoranthusemortisreborn.canis.common.skill;

import com.platinumg17.rigoranthusemortisreborn.api.apicanis.entity.AbstractCanisEntity;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.Skill;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.SkillInstance;
import com.platinumg17.rigoranthusemortisreborn.canis.CanisSkills;
import com.platinumg17.rigoranthusemortisreborn.canis.common.inventory.WaywardTravellerItemHandler;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.CanisTags;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.InventoryUtil;
import com.platinumg17.rigoranthusemortisreborn.config.Config;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;

import java.util.List;
import java.util.function.Predicate;

public class WaywardTravellerSkill extends SkillInstance {

//    @CapabilityInject(WaywardTravellerItemHandler.class)
    public static Capability<WaywardTravellerItemHandler> WAYWARD_TRAVELLER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});;
    private WaywardTravellerItemHandler waywardTravellerHandler;
    private LazyOptional<?> lazyWaywardTravellerHandler;

    public static Predicate<ItemEntity> SHOULD_PICKUP_ENTITY_ITEM = (entity) -> {
        return entity.isAlive() && !entity.hasPickUpDelay() && !CanisTags.WAYWARD_TRAVELLER_BLACKLIST.contains(entity.getItem().getItem());// && !EntityAIFetch.BONE_PREDICATE.test(entity.getItem());
    };

    public WaywardTravellerSkill(Skill SkillIn, int levelIn) {
        super(SkillIn, levelIn);
        WaywardTravellerItemHandler handler = new WaywardTravellerItemHandler();
        this.waywardTravellerHandler = handler;
        this.lazyWaywardTravellerHandler = LazyOptional.of(() -> handler);
    }
    public WaywardTravellerItemHandler inventory() {return this.waywardTravellerHandler;}

    @Override
    public void tick(AbstractCanisEntity canisIn) {
        if (canisIn.isAlive() && !canisIn.level.isClientSide && this.level() >= 5) {
            List<ItemEntity> list = canisIn.level.getEntitiesOfClass(ItemEntity.class, canisIn.getBoundingBox().inflate(2.5D, 1D, 2.5D), SHOULD_PICKUP_ENTITY_ITEM);
            if (!list.isEmpty()) {
                for (ItemEntity entityItem : list) {
                    ItemStack remaining = InventoryUtil.addItem(this.waywardTravellerHandler, entityItem.getItem());
                    if (!remaining.isEmpty()) {
                        entityItem.setItem(remaining);
                    } else {
                        entityItem.discard();
                        canisIn.playSound(SoundEvents.ITEM_PICKUP, 0.25F, ((canisIn.level.random.nextFloat() - canisIn.level.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                    }
                }
            }
        }
    }

    @Override
    public void set(AbstractCanisEntity canis, int preLevel) {
        // No need to drop anything if canis didn't have wayward traveller skill
        if (preLevel > 0 && this.level == 0) {this.dropInventory(canis);}
    }

    @Override
    public void dropInventory(AbstractCanisEntity canisIn) {
        //TODO either drop inventory or save to respawn data, currently does both
        // No need to drop anything if canis didn't have wayward traveller
        for (int i = 0; i < this.waywardTravellerHandler.getSlots(); ++i) {
            Containers.dropItemStack(canisIn.level, canisIn.getX(), canisIn.getY(), canisIn.getZ(), this.waywardTravellerHandler.getStackInSlot(i));
            this.waywardTravellerHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    @Override
    public void writeToNBT(AbstractCanisEntity canisIn, CompoundTag compound) {
        super.writeToNBT(canisIn, compound);
        compound.merge(this.waywardTravellerHandler.serializeNBT());
    }

    @Override
    public void readFromNBT(AbstractCanisEntity canisIn, CompoundTag compound) {
        super.readFromNBT(canisIn, compound);
        this.waywardTravellerHandler.deserializeNBT(compound);
    }

    @Override
    public <T> LazyOptional<T> getCapability(AbstractCanisEntity canisIn, Capability<T> cap, Direction side) {
        if (cap == WaywardTravellerSkill.WAYWARD_TRAVELLER_CAPABILITY) {
            return (LazyOptional<T>) this.lazyWaywardTravellerHandler;
        }
        return null;
    }
    @Override public void invalidateCapabilities(AbstractCanisEntity canisIn) {this.lazyWaywardTravellerHandler.invalidate();}
    @Override public boolean hasRenderer() {return Config.ClientConfig.RENDER_CHEST.get();}

    public static boolean hasInventory(AbstractCanisEntity canisIn) {
        return canisIn.isAlive() && canisIn.getSkill(CanisSkills.WAYWARD_TRAVELLER).isPresent();
    }
}