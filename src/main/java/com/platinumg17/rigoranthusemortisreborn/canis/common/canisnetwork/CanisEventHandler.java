package com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork;

import com.platinumg17.rigoranthusemortisreborn.canis.CanisItems;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import com.platinumg17.rigoranthusemortisreborn.canis.common.skill.PredatorSkill;
import com.platinumg17.rigoranthusemortisreborn.config.Config;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CanisEventHandler {

//    @SubscribeEvent
//    public void rightClickEntity(final PlayerInteractEvent.EntityInteract event) {
//
//        Level world = event.getWorld();
//        ItemStack stack = event.getItemStack();
//        Entity target = event.getTarget();
//
//        if (target.getType() == RigoranthusEntityTypes.FERAL_CANIS.get() && stack.getItem() == ItemInit.PACT_OF_SERVITUDE.get()) {
//            event.setCanceled(true);
//            FeralCanisEntity feralCanis = (FeralCanisEntity) target;
//            Player player = event.getPlayer();
//            BlockPos blockpos = event.getPos();
//
//            if (feralCanis.isAlive()) {     //&& feralCanis.isTame() && feralCanis.isOwnedBy(player)) {
//                if (!world.isClientSide) {
//                    if (!player.abilities.instabuild) {
//                        stack.shrink(1);
//                    }
//                    world.playSound(player, blockpos, SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0F,(world.random.nextFloat() - world.random.nextFloat()) * 0.4F + 1.0F); // was  0.2F + 1.0F
//                    if ((Math.random() <= 0.15)) {
//                        if (player.level.isClientSide) {
//                            feralCanis.level.addParticle(ParticleTypes.SOUL, feralCanis.getRandomX(1.0D), feralCanis.getRandomY() + 0.5D, feralCanis.getRandomZ(1.0D), 0.0D, 0.0D, 0.0D); //((ServerLevel) level).sendParticles(player, true, 5, 3, 3, IPacket <?> level) //    (ParticleTypes.SOUL, feralCanis.blockPosition(), 5, 3, 3, 3, 1);
//                            feralCanis.level.addParticle(ParticleTypes.SOUL, feralCanis.getRandomX(1.5D), feralCanis.getRandomY() + 0.8D, feralCanis.getRandomZ(1.5D), 0.0D, 0.0D, 0.0D);
//                        }
//                        world.playSound(null, blockpos, SoundEvents.WOLF_HOWL, SoundSource.NEUTRAL, 1f, 0.8f);
//                        feralCanis.setNoActionTime(60); // feralCanis.addEffect(new MobEffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 7));
//                        feralCanis.setSecondsOnFire(3);
//                        if (player.level.isClientSide) {
//                            player.displayClientMessage(new TextComponent("\u00A76The Pact was Successful. \u00A7cThe Beasts Impurities will now be Expelled."), (true));
//                        }
//                        CanisEntity canis = SpecializedEntityTypes.CANIS.get().create(world);
//                        canis.setTame(true);
//                        canis.setOwnerUUID(player.getUUID());  // TODO --> setOwnerUUID may not be needed if canis.tame(player) is already here
//                        canis.setHealth(canis.getMaxHealth());
//                        canis.setOrderedToSit(false);
////                        canis.setAge(1);
//                        canis.absMoveTo(feralCanis.getX(), feralCanis.getY(), feralCanis.getZ(), feralCanis.yRot, feralCanis.xRot);
//                        world.addFreshEntity(canis);
//                        feralCanis.remove();
//                    }
//                }
//                event.setCancellationResult(InteractionResult.SUCCESS);
//            } else {
//                event.setCancellationResult(InteractionResult.FAIL);
//            }
//        }
//    }

    @SubscribeEvent
    public void onEntitySpawn(final EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof AbstractSkeleton) {
            AbstractSkeleton skeleton = (AbstractSkeleton) entity;
            skeleton.goalSelector.addGoal(3, new AvoidEntityGoal<>(skeleton, CanisEntity.class, 6.0F, 1.0D, 1.2D)); // Same goal as in AbstractSkeletonEntity
        }
    }

    @SubscribeEvent
    public void playerLoggedIn(final PlayerLoggedInEvent event) {
        if (Config.STARTING_ITEMS.get()) {
            Player player = event.getPlayer();
            CompoundTag tag = player.getPersistentData();
            if (!tag.contains(Player.PERSISTED_NBT_TAG)) {
                tag.put(Player.PERSISTED_NBT_TAG, new CompoundTag());
            }
            CompoundTag persistTag = tag.getCompound(Player.PERSISTED_NBT_TAG);
            if (!persistTag.getBoolean("gotCanisStartingItems")) {
                persistTag.putBoolean("gotCanisStartingItems", true);
                player.inventory.add(new ItemStack(CanisItems.CANIS_SUMMONING_CHARM.get()));
                player.inventory.add(new ItemStack(CanisItems.CADAVER_SUMMONING_CHARM.get()));
                player.inventory.add(new ItemStack(CanisItems.WHISTLE.get()));
            }
        }
    }
    @SubscribeEvent
    public void onLootDrop(final LootingLevelEvent event) {
        PredatorSkill.onLootDrop(event);
    }
}