package com.platinumg17.rigoranthusemortisreborn.items.itemeffects;

import com.platinumg17.rigoranthusemortisreborn.core.init.network.REPacketHandler;
import com.platinumg17.rigoranthusemortisreborn.core.registry.RigoranthusSoundRegistry;
import com.platinumg17.rigoranthusemortisreborn.core.registry.effects.weapons.MagicEffectPacket;
import com.platinumg17.rigoranthusemortisreborn.util.MagicHitTargetUtil;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class MagicAttackRightClickEffect implements ItemRightClickEffect {
    private final int distance;
    private final int damage;
    private final Supplier<MobEffectInstance> effect;
    private final Supplier<SoundEvent> sound;
    private final float pitch;
    @Nullable
    private final MagicHitTargetUtil.Type type;

    private static final TargetingConditions visiblePredicate = TargetingConditions.DEFAULT;//.setLineOfSiteRequired(); TODO should something else be done with the predicate?

    public static final MagicAttackRightClickEffect NOVICE_MAGIC = new NoviceMagicEffect(10, 1, null, null, 1.0F, MagicHitTargetUtil.Type.GREEN);
    public static final MagicAttackRightClickEffect TYRO_MAGIC = new TyroMagicEffect(14, 2, null, null, 1.0F, MagicHitTargetUtil.Type.CRIT);
    public static final MagicAttackRightClickEffect PROHIBATE_MAGIC = new MagicAttackRightClickEffect(15, 3, null, null, 1.0F, MagicHitTargetUtil.Type.ENCHANT);
    public static final MagicAttackRightClickEffect SPECTIVE_MAGIC = new MagicAttackRightClickEffect(18, 4, null, null, 1.0F, MagicHitTargetUtil.Type.RED);
    public static final MagicAttackRightClickEffect CIRCEAN_MAGIC = new MagicAttackRightClickEffect(30, 8, null, null, 1.0F, MagicHitTargetUtil.Type.CIRCEAN);
    public static final MagicAttackRightClickEffect APOGEAN_MAGIC = new MagicAttackRightClickEffect(50, 9, null, null, 1.0F, MagicHitTargetUtil.Type.APOGEE);
    public static final MagicAttackRightClickEffect PSYCHO_BLIGHT_MAGIC = new MagicAttackRightClickEffect(20, 5, () -> new MobEffectInstance(
            MobEffects.WITHER, 100, 0), () -> RigoranthusSoundRegistry.ITEM_GRIMOIRE_USE.get(), 1.2F, MagicHitTargetUtil.Type.INK);

    protected MagicAttackRightClickEffect(int distance, int damage, Supplier<MobEffectInstance> effect, Supplier<SoundEvent> sound, float pitch, @Nullable MagicHitTargetUtil.Type type) {
        this.distance = distance;
        this.damage = damage;
        this.effect = effect;
        this.sound = sound;
        this.pitch = pitch;
        this.type = type;
    }

    @Override
    public InteractionResultHolder<ItemStack> onRightClick(Level world, Player player, InteractionHand hand) {
        ItemStack itemStackIn = player.getItemInHand(hand);

        if(player instanceof ServerPlayer)
            magicAttack(world, (ServerPlayer) player);

        if(player.isCreative())
            player.getCooldowns().addCooldown(itemStackIn.getItem(), 10);
        else
            player.getCooldowns().addCooldown(itemStackIn.getItem(), 50);

        player.swing(hand, true);
        itemStackIn.hurtAndBreak(6, player, playerEntity -> playerEntity.broadcastBreakEvent(InteractionHand.MAIN_HAND));
        player.awardStat(Stats.ITEM_USED.get(itemStackIn.getItem()));

        return InteractionResultHolder.success(itemStackIn);
    }

    private void magicAttack(Level world, ServerPlayer player) {
        if(sound != null && player.getRandom().nextFloat() < .1F) //optional sound effect adding
            world.playSound(null, player.getX(), player.getY(), player.getZ(), sound.get(), SoundSource.PLAYERS, 0.7F, pitch);
        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.6F);

        targetEffect(player);

        Vec3 eyePos = player.getEyePosition(1.0F);
        Vec3 lookVec = player.getLookAngle();
//  Uses the float i value to increase the distance away from where the player is looking and creating a sort of raytrace
        for(int step = 0; step < distance * 2; step++) {
            Vec3 vecPos = eyePos.add(lookVec.scale(step / 2D));

            boolean hitObstacle = checkCollisionInPath(world, player, vecPos);

            if(hitObstacle) {
                sendEffectPacket(world, eyePos, lookVec, step, true);
                return;
            }
        }
        sendEffectPacket(world, eyePos, lookVec, distance * 2, false);
    }

    protected void sendEffectPacket(Level world, Vec3 pos, Vec3 lookVec, int length, boolean collides) {
        if(type != null)
            REPacketHandler.sendToNear(new MagicEffectPacket(type, pos, lookVec, length, collides),
                    new PacketDistributor.TargetPoint(pos.x, pos.y, pos.z, 64, world.dimension()));
    }

    protected void targetEffect(ServerPlayer player) {}

    private boolean checkCollisionInPath(Level world, ServerPlayer player, Vec3 vecPos) {
        BlockPos blockPos = new BlockPos(vecPos);

        if(!world.getBlockState(blockPos).isPathfindable(world, blockPos, PathComputationType.LAND)) {
            return true;
        }

        AABB axisAlignedBB = new AABB(blockPos);
        // gets entities in a bounding box around each vector position in the for loop
        LivingEntity closestTarget = player.level.getNearestEntity(LivingEntity.class, visiblePredicate, player, vecPos.x, vecPos.y, vecPos.z, axisAlignedBB);
        if(closestTarget != null) {
//            int playerLevel = PlayerSavedData.getData(player).getAspectProgress().getLevel();
//
//            if(closestTarget instanceof MonsterEntity)
//                closestTarget.hurt(DamageSource.playerAttack(player).setMagic(), damage + playerLevel / 5F);
//            else
                closestTarget.hurt(DamageSource.playerAttack(player).setMagic(), damage/* + playerLevel / 10F*/);
            if(effect != null && player.getRandom().nextFloat() < .25F)
                closestTarget.addEffect(effect.get());

            return true;
        } else return false;
    }

    private static class NoviceMagicEffect extends MagicAttackRightClickEffect {
        NoviceMagicEffect(int distance, int damage, Supplier<MobEffectInstance> effect, Supplier<SoundEvent> sound, float pitch, @Nullable MagicHitTargetUtil.Type type) {
            super(distance, damage, effect, sound, pitch, type);
        }

        @Override
        protected void targetEffect(ServerPlayer player) {
            Vec3 randomFacingVecPos = new Vec3(player.getX() + player.getRandom().nextInt(10) - 5, player.getY() + player.getRandom().nextInt(10) - 5, player.getZ() + player.getRandom().nextInt(10) - 5);
            player.lookAt(player.createCommandSourceStack().getAnchor(), randomFacingVecPos);
        }
    }

    private static class TyroMagicEffect extends MagicAttackRightClickEffect
    {
        TyroMagicEffect(int distance, int damage, Supplier<MobEffectInstance> effect, Supplier<SoundEvent> sound, float pitch, @Nullable MagicHitTargetUtil.Type type) {
            super(distance, damage, effect, sound, pitch, type);
        }

        @Override
        protected void targetEffect(ServerPlayer player) {
            BlockPos playerEyePos = new BlockPos(player.getX(), player.getEyeY(), player.getZ());
            LivingEntity closestVisibleTarget = player.level.getNearestEntity(LivingEntity.class, visiblePredicate, player, player.getX(), player.getEyeY(), player.getZ(), new AABB(playerEyePos).inflate(11));
            if(closestVisibleTarget != null)
                player.lookAt(EntityAnchorArgument.Anchor.EYES, closestVisibleTarget, EntityAnchorArgument.Anchor.EYES);
        }
    }
}