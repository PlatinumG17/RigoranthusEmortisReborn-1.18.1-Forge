package com.platinumg17.rigoranthusemortisreborn.canis.common.items;

import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.DataKey;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.EnumMode;
import com.platinumg17.rigoranthusemortisreborn.canis.CanisSkills;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import com.platinumg17.rigoranthusemortisreborn.canis.common.skill.SeizingSnarlSkill;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.EntityUtil;
import com.platinumg17.rigoranthusemortisreborn.config.Config;
import com.platinumg17.rigoranthusemortisreborn.core.registry.RigoranthusSoundRegistry;
import com.platinumg17.rigoranthusemortisreborn.magica.common.potions.ModPotions;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ProPercivalalb, edited by PlatinumG17
 */
public class CommandWritItem extends Item {
    public static final String BOOK_MODE_TAG = "mode";
    public static DataKey<List<BlockPos>> POS = DataKey.make();

    public CommandWritItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.isShiftKeyDown()) {
            if (!world.isClientSide) {
                if (!stack.hasTag()) {
                    stack.setTag(new CompoundTag());
//                    stack.getTag().putByte(BOOK_MODE_TAG, (byte)0);
                    stack.getTag().putInt(BOOK_MODE_TAG, 0);
                }
                int mode = stack.getTag().getInt(BOOK_MODE_TAG);
                stack.getTag().putInt(BOOK_MODE_TAG, (mode + 1) % 6);
            }

            return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, stack);
        }
        else {
            int mode = 0;
//            stack.getOrCreateTag().putInt(CommandWritItem.BOOK_MODE_TAG, 0);

            if (stack.hasTag() && stack.getTag().contains(BOOK_MODE_TAG, Tag.TAG_ANY_NUMERIC)) {
                mode = stack.getTag().getByte(BOOK_MODE_TAG);
            }

            List<CanisEntity> caniList = world.getEntitiesOfClass(CanisEntity.class, player.getBoundingBox().inflate(100D, 50D, 100D), canis -> canis.isOwnedBy(player));
            boolean successful = false;

            if (mode == 0) { // Stand
                if (!world.isClientSide) {
                    for (CanisEntity canis : caniList) {
                        canis.setOrderedToSit(false);
                        canis.getNavigation().stop();
                        canis.setTarget(null);
                        if (canis.getMode() == EnumMode.WANDERING) {
                            canis.setMode(EnumMode.DOCILE);
                        }
                        successful = true;
                    }
                    if (Config.WHISTLE_SOUNDS)
                        world.playSound(null, player.blockPosition(), RigoranthusSoundRegistry.WHISTLE_LONG.get(), SoundSource.PLAYERS, 0.6F + world.random.nextFloat() * 0.1F, 0.8F + world.random.nextFloat() * 0.2F);
                    if (successful) {
                        player.sendMessage(new TranslatableComponent("mastersbehest.come"), Util.NIL_UUID);
                    }
                }

                return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, stack);
            }
            else if (mode == 1) { // All Follow
                if (!world.isClientSide) {
                    for (CanisEntity canis : caniList) {
                        if (!canis.isInSittingPose() && canis.getMode() != EnumMode.WANDERING) {
                            if (canis.distanceToSqr(canis.getOwner()) > 9) { // Only tp if less than 3 blocks away
                                EntityUtil.tryToTeleportNearEntity(canis, canis.getNavigation(), canis.getOwner(), 2);
                            }
                            successful = true;
                        }
                    }

                    if (Config.WHISTLE_SOUNDS)
                        world.playSound(null, player.blockPosition(), RigoranthusSoundRegistry.WHISTLE_LONG.get(), SoundSource.PLAYERS, 0.6F + world.random.nextFloat() * 0.1F, 0.8F + world.random.nextFloat() * 0.2F);

                    if (successful) {
                        player.sendMessage(new TranslatableComponent("mastersbehest.heel"), Util.NIL_UUID);
                    }
                }

                return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, stack);
            }
            else if (mode == 2) { // Stay
                if (!world.isClientSide) {
                    for (CanisEntity canis : caniList) {
                        canis.setOrderedToSit(true);
                        canis.getNavigation().stop();
                        canis.setTarget(null);
                        if (canis.getMode() == EnumMode.WANDERING) {
                            canis.setMode(EnumMode.DOCILE);
                        }
                        successful = true;
                    }

                    if (Config.WHISTLE_SOUNDS)
                        world.playSound(null, player.blockPosition(), RigoranthusSoundRegistry.WHISTLE_SHORT.get(), SoundSource.PLAYERS, 0.6F + world.random.nextFloat() * 0.1F, 0.8F + world.random.nextFloat() * 0.2F);

                    if (successful) {
                        player.sendMessage(new TranslatableComponent("mastersbehest.stay"), Util.NIL_UUID);
                    }
                }
                return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
            }
            else if (mode == 3) { // Ok
                if (!world.isClientSide) {
                    for (CanisEntity canis : caniList) {
                        if (canis.getMaxHealth() / 2 >= canis.getHealth()) {
                            canis.setOrderedToSit(true);
                            canis.getNavigation().stop();
                            canis.setTarget(null);
                        }
                        else {
                            canis.setOrderedToSit(false);
                            canis.getNavigation().stop();
                            canis.setTarget(null);
                        }
                        successful = true;
                    }
                    if (Config.WHISTLE_SOUNDS)
                        world.playSound(null, player.blockPosition(), RigoranthusSoundRegistry.WHISTLE_LONG.get(), SoundSource.PLAYERS, 0.6F + world.random.nextFloat() * 0.1F, 0.4F + world.random.nextFloat() * 0.2F);
                    if (successful) {
                        player.sendMessage(new TranslatableComponent("mastersbehest.ok"), Util.NIL_UUID);
                    }
                    return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
                }
            }
            else if (mode == 4) {
                if (!world.isClientSide) {
                    if (Config.WHISTLE_SOUNDS)
                        world.playSound(null, player.blockPosition(), RigoranthusSoundRegistry.WHISTLE_SHORT.get(), SoundSource.PLAYERS, 0.6F + world.random.nextFloat() * 0.1F, 0.8F + world.random.nextFloat() * 0.2F);
                }

                return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
//            } else if (mode == 5) {
//                if (!world.isClientSide) {
//                    if (ConfigHandler.WHISTLE_SOUNDS)
//                        world.playSound((Player)null, player.blockPosition(), SoundEvents.ARROW_SHOOT, SoundSource.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
//                    this.addPosToStack(player.getItemInHand(hand), player.blockPosition());
//                    CanisBeamEntity canisBeam = new CanisBeamEntity(world, player);
//                    canisBeam.shootFromRotation(player, player.xRot, player.yRot, 0.0F, 2.0F, 1.0F);
//                    world.addFreshEntity(canisBeam);
//                }
//                return new InteractionResultHolder<>(InteractionResult.CONSUME, player.getItemInHand(hand));
            } else if (mode == 5) {
                if (!world.isClientSide) {
                    List<CanisEntity> snarlingCani = caniList.stream().filter(canis -> canis.getCanisLevel(CanisSkills.SNARL) > 0).collect(Collectors.toList());
                    if (snarlingCani.isEmpty()) {
                        player.displayClientMessage(new TranslatableComponent("skill.rigoranthusemortisreborn.snarl.level"), true);
                    } else {
                        List<CanisEntity> cooldownCani = snarlingCani.stream().filter(canis -> canis.getDataOrDefault(SeizingSnarlSkill.COOLDOWN, canis.tickCount) <= canis.tickCount).collect(Collectors.toList());
                        if (cooldownCani.isEmpty()) {
                            player.displayClientMessage(new TranslatableComponent("skill.rigoranthusemortisreborn.snarl.cooldown"), true);
                        } else {
                            boolean anyHits = false;

                            for (CanisEntity canis : caniList) {
                                int level = canis.getCanisLevel(CanisSkills.SNARL);
                                int snarlCooldown = canis.tickCount;

                                byte damage = (byte)(level > 4 ? level * 2 : level);
                                /*
                                 * If level = 1, set duration to  20 ticks (1 second); level = 2, set duration to 24 ticks (1.2 seconds)
                                 * If level = 3, set duration to 36 ticks (1.8 seconds); If level = 4, set duration to 48 ticks (2.4 seconds)
                                 * If level = max (5), set duration to 70 ticks (3.5 seconds);
                                 */
                                byte effectDuration = (byte)(level > 4 ? level * 14 : level * (level == 1 ? 20 : 12));
                                byte knockback = (byte)level;

                                boolean hit = false;
                                List<LivingEntity> list = canis.level.<LivingEntity>getEntitiesOfClass(LivingEntity.class, canis.getBoundingBox().inflate(level * 4, 4D, level * 4));
                                for (LivingEntity mob : list) {
                                    if (mob instanceof Mob) {
                                        hit = true;
                                        mob.hurt(DamageSource.GENERIC, damage);
                                        mob.addEffect(new MobEffectInstance(ModPotions.SNARE_EFFECT, effectDuration, 127, false, false));
                                        mob.addEffect(new MobEffectInstance(MobEffects.GLOWING, effectDuration, 1, false, false));
                                        mob.push(Mth.sin(mob.yRot * (float) Math.PI / 180.0F) * knockback * 0.5F, 0.1D, -Mth.cos(mob.yRot * (float) Math.PI / 180.0F) * knockback * 0.5F);
                                    }
                                }
                                if (hit) {
                                    canis.playSound(RigoranthusSoundRegistry.CANIS_AMBIENT.get(), 1.0F, 1.0F);
                                    snarlCooldown += level >= 5 ? 60 : 100;
                                    anyHits = true;
                                } else {
                                    canis.playSound(RigoranthusSoundRegistry.CANIS_HUFF.get(), 1F, 1.2F);
                                    snarlCooldown += level >= 5 ? 30 : 50;
                                }
                                canis.setData(SeizingSnarlSkill.COOLDOWN, snarlCooldown);
                            }
                            if (!anyHits) {
                                player.displayClientMessage(new TranslatableComponent("skill.rigoranthusemortisreborn.snarl.miss"), true);
                            }
                        }
                    }
                }
                return new InteractionResultHolder<>(InteractionResult.SUCCESS, player.getItemInHand(hand));
            }
        }
        return new InteractionResultHolder<>(InteractionResult.FAIL, player.getItemInHand(hand));
    }

    public static String getModeName(CompoundTag tag, int mode){
    switch (mode) {
        case 0:
            return new TranslatableComponent("item.rigoranthusemortisreborn.whistle.0").getString();
        case 1:
            return new TranslatableComponent("item.rigoranthusemortisreborn.whistle.1").getString();
        case 2:
            return new TranslatableComponent("item.rigoranthusemortisreborn.whistle.2").getString();
        case 3:
            return new TranslatableComponent("item.rigoranthusemortisreborn.whistle.3").getString();
        case 4:
            return new TranslatableComponent("item.rigoranthusemortisreborn.whistle.4").getString();
        case 5:
            return new TranslatableComponent("item.rigoranthusemortisreborn.whistle.5").getString();
        default:
            throw new IllegalStateException("Unable to get next Command of " + mode);
    }
//        if(mode == 0)
//            return new TranslatableComponent("item.rigoranthusemortisreborn.whistle.0").getString();
//        return tag.getString( "item.rigoranthusemortisreborn.whistle." + mode);
    }

    public static String getModeName(CompoundTag tag){
        return getModeName(tag, getMode(tag));
    }

    public static void setMode(CompoundTag tag, int mode) {
        tag.putByte(CommandWritItem.BOOK_MODE_TAG, (byte) mode);
    }

    public static int getMode(CompoundTag tag){
        return tag.getByte(CommandWritItem.BOOK_MODE_TAG);
    }

//    @Override
//    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
//        if(!stack.hasTag())
//            stack.setTag(new CompoundTag());
//
//        if(!worldIn.isClientSide && worldIn.getGameTime() % 5 == 0 && !stack.hasTag()) {
//            CompoundTag tag = new CompoundTag();
//            tag.putByte(CommandWritItem.BOOK_MODE_TAG, (byte) 0);
//            stack.setTag(tag);
//        }
//        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
//    }
//    public void addPosToStack(ItemStack stackIn, BlockPos posIn) {
//        CompoundTag tag = stackIn.getOrCreateTag();
//        ListNBT list = tag.getList("patrolPos", Tag.TAG_COMPOUND);
//        CompoundTag pos = new CompoundTag();
//        NBTUtilities.putBlockPos(pos, posIn);
//        list.add(pos);
//        tag.put("patrolPos", list);
//    }
//
//    public List<BlockPos> getPos(ItemStack stackIn) {
//        if (stackIn.hasTag() && stackIn.getTag().contains("patrolPos", Tag.TAG_LIST)) {
//            ListNBT list = stackIn.getTag().getList("patrolPos", Tag.TAG_COMPOUND);
//            List<BlockPos> pos = new ArrayList<>(list.size());
//            for (int i = 0; i < list.size(); i++) {
//                pos.add(NBTUtilities.getBlockPos(list.getCompound(i)));
//            }
//            return pos;
//        }
//        return Collections.emptyList();
//    }
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void appendHoverText(final ItemStack stack, @Nullable final Level world, final List<Component> tooltip, final TooltipFlag flag) {
//        super.appendHoverText(stack, world, tooltip, flag);
//        if(stack.hasTag()) {
//            tooltip.add(new TextComponent(CommandWritItem.getModeName(stack.getTag())));
//            tooltip.add(new TranslatableComponent("rigoranthusemortisreborn.whistle.select", KeyBinding.createNameSupplier(REKeyBindings.OPEN_COMMAND_SELECTION.getKeyBinding().getName()).get().getString()));
////            tooltip.add(new TranslatableComponent("rigoranthusemortisreborn.whistle.inventory", KeyBinding.createNameSupplier(REKeyBindings.OPEN_CANIS_INV.getKeyBinding().getName()).get().getString()));
//        }
//    }
    @Override
    public String getDescriptionId(ItemStack stack) {
        byte mode = 0;

        if (stack.hasTag() && stack.getTag().contains(BOOK_MODE_TAG, Tag.TAG_ANY_NUMERIC)) {
            mode = stack.getTag().getByte(BOOK_MODE_TAG);
        }
        return this.getDescriptionId() + "." + mode;
    }
    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.UNCOMMON;
    }
}