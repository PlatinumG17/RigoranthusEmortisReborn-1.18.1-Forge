package com.platinumg17.rigoranthusemortisreborn.items.armor.armorsets;

import com.platinumg17.rigoranthusemortisreborn.config.Config;
import com.platinumg17.rigoranthusemortisreborn.core.init.Registration;
import com.platinumg17.rigoranthusemortisreborn.magica.common.network.Networking;
import com.platinumg17.rigoranthusemortisreborn.magica.common.network.PacketIncorporealDoubleJump;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class IncorporealChestplateItem extends IncorporealArmor {

    public IncorporealChestplateItem(EquipmentSlot slot) {
        super(slot);
        MinecraftForge.EVENT_BUS.register(new DoubleJumpHandler());
        setRegistryName("incorporeal_netherite_chestplate");
        addListener(EventPriority.HIGHEST, LivingFallEvent.class, this::onLivingFall);
    }
    public boolean isEquippedBy(@Nullable LivingEntity entity) {
        if (entity instanceof Player && Config.enableArmorSetBonuses.get()) {
            ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
            ItemStack legs = entity.getItemBySlot(EquipmentSlot.LEGS);
            ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
            ItemStack helm = entity.getItemBySlot(EquipmentSlot.HEAD);
            return boots.getItem() == Registration.INCORPOREAL_N_BOOTS && legs.getItem() == Registration.INCORPOREAL_N_LEGS && chest.getItem() == Registration.INCORPOREAL_N_CHEST.get() && helm.getItem() == Registration.INCORPOREAL_N_HELMET;
        }
        else return false;
    }
    protected <T extends Event, S extends LivingEntity> void addListener(EventPriority priority, Class<T> eventClass, BiConsumer<T, S> listener, Function<T, S> wearerSupplier) {
        MinecraftForge.EVENT_BUS.addListener(priority, true, eventClass, event -> {
            S wearer = wearerSupplier.apply(event);
            if (isEquippedBy(wearer)) {
                listener.accept(event, wearer);
            }
        });
    }

    private void onLivingFall(LivingFallEvent event, LivingEntity wearer) {
        event.setDistance(Math.max(0, event.getDistance() - 6));
    }

    protected <T extends Event, S extends LivingEntity> void addListener(Class<T> eventClass, BiConsumer<T, S> listener, Function<T, S> wearerSupplier) {
        addListener(EventPriority.NORMAL, eventClass, listener, wearerSupplier);
    }

    protected <T extends LivingEvent> void addListener(EventPriority priority, Class<T> eventClass, BiConsumer<T, LivingEntity> listener) {
        addListener(priority, eventClass, listener, LivingEvent::getEntityLiving);
    }

    protected <T extends LivingEvent> void addListener(Class<T> eventClass, BiConsumer<T, LivingEntity> listener) {
        addListener(EventPriority.NORMAL, eventClass, listener);
    }


    public void jump(Player player) {
        if (Config.enableArmorSetBonuses.get()) {
            player.fallDistance = 0;
            double upwardsMotion = 0.5;
            if (player.hasEffect(MobEffects.JUMP)) {
                // noinspection ConstantConditions
                upwardsMotion += 0.1 * (player.getEffect(MobEffects.JUMP).getAmplifier() + 1);
            }
            upwardsMotion *= player.isSprinting() ? 2 : 1;

            Vec3 motion = player.getDeltaMovement();
            double motionMultiplier = player.isSprinting() ? 2 : 0;
            float direction = (float) (player.yRot * Math.PI / 180);
            player.setDeltaMovement(player.getDeltaMovement().add(
                    -Mth.sin(direction) * motionMultiplier,
                    upwardsMotion - motion.y,
                    Mth.cos(direction) * motionMultiplier)
            );
            player.hasImpulse = true;
            net.minecraftforge.common.ForgeHooks.onLivingJump(player);

            player.awardStat(Stats.JUMP);
            if (player.isSprinting()) {
                player.causeFoodExhaustion(0.1F);
            } else {
                player.causeFoodExhaustion(0.025F);
            }
            player.playSound(SoundEvents.WOOL_FALL, 1, 0.9F + player.getRandom().nextFloat() * 0.2F);
        }
    }

    private class DoubleJumpHandler {

        @OnlyIn(Dist.CLIENT)
        private boolean canDoubleJump;

        @OnlyIn(Dist.CLIENT)
        private boolean hasReleasedJumpKey;

        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        @SuppressWarnings("unused")
        public void onClientTick(TickEvent.ClientTickEvent event) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (Config.enableArmorSetBonuses.get()) {
                if (event.phase == TickEvent.Phase.END && player != null && player.input != null) {
                    if ((player.isOnGround() || player.onClimbable()) && !player.isInWater()) {
                        hasReleasedJumpKey = false;
                        canDoubleJump = true;
                    } else if (!player.input.jumping) {
                        hasReleasedJumpKey = true;
                    } else if (!player.abilities.flying && canDoubleJump && hasReleasedJumpKey) {
                        canDoubleJump = false;
                        if (isEquippedBy(player)) {
                            Networking.INSTANCE.sendToServer(new PacketIncorporealDoubleJump());
                            jump(player);
                        }
                    }
                }
            }
        }
    }
}