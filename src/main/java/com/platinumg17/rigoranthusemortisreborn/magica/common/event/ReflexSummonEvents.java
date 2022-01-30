package com.platinumg17.rigoranthusemortisreborn.magica.common.event;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.entity.mobs.SummonedCadaver;
import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.ParticleUtil;
import com.platinumg17.rigoranthusemortisreborn.magica.common.enchantment.EnchantmentRegistry;
import com.platinumg17.rigoranthusemortisreborn.magica.common.entity.ModEntities;
import com.platinumg17.rigoranthusemortisreborn.magica.common.network.Networking;
import com.platinumg17.rigoranthusemortisreborn.magica.common.network.PacketReflexSummon;
import com.platinumg17.rigoranthusemortisreborn.magica.common.potions.ModPotions;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.MagicItemsRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EmortisConstants.MOD_ID)
public class ReflexSummonEvents {
    @SubscribeEvent
    public static void livingHitEvent(LivingHurtEvent e) {
        LivingEntity entity = e.getEntityLiving();
        if(entity.getCommandSenderWorld().isClientSide || !(entity instanceof Player))
            return;
        if (entity.hasEffect(ModPotions.SUMMONING_COOLDOWN))
            return;
        for(ItemStack s : entity.getArmorSlots()) {
            castSummon((Player) entity, s);
        }
        if(entity.isBlocking()) {
            if (entity.isHolding(MagicItemsRegistry.LUSTERIC_SHIELD)) {
                castShieldSummon((Player) entity);
            }
        }
    }
    // TODO: Replace ray casting with unified casting on look vector
    public static void castSummon(Player playerIn, ItemStack s) {
        if (playerIn.level.isClientSide || !(playerIn.level instanceof ServerLevel))
            return;
        if (playerIn.hasEffect(ModPotions.SUMMONING_COOLDOWN))
            return;
        if(EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.REFLEX_SUMMON_ENCHANTMENT, s) * .015 >= Math.random()) {
            Entity entity = ModEntities.SUMMONED_CADAVER.spawn((ServerLevel) playerIn.level, s, playerIn, playerIn.blockPosition().offset(1, 0, 1), MobSpawnType.SPAWN_EGG, false, false);
            if (entity instanceof SummonedCadaver) {
                SummonedCadaver cadaver = (SummonedCadaver) entity;
                ParticleUtil.spawnPoof((ServerLevel) playerIn.level, entity.blockPosition());
                cadaver.setTame(true);
                cadaver.setCustomName(playerIn.getDisplayName().copy().append(new TranslatableComponent("entity.rigoranthusemortisreborn.familiar_cadaver")).setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_RED).withItalic(true)));
                cadaver.setOwnerUUID(playerIn.getUUID());
                playerIn.addEffect(new MobEffectInstance(ModPotions.SUMMONING_COOLDOWN, 5000, 0, false, false));
            }
        }
    }
    public static void castShieldSummon(Player playerIn) {
        if (playerIn.level.isClientSide || !(playerIn.level instanceof ServerLevel) )
            return;
        if (playerIn.hasEffect(ModPotions.SUMMONING_COOLDOWN))
            return;
        if(0.01 >= Math.random()) {
            Entity entity = ModEntities.SUMMONED_CADAVER.spawn((ServerLevel) playerIn.level, playerIn.getOffhandItem(), playerIn, playerIn.blockPosition().offset(1, 0, 1), MobSpawnType.SPAWN_EGG, false, false);
            if (entity instanceof SummonedCadaver) {
                SummonedCadaver cadaver = (SummonedCadaver) entity;
                ParticleUtil.spawnPoof((ServerLevel) playerIn.level, entity.blockPosition());
                cadaver.setTame(true);
                cadaver.setCustomName(playerIn.getDisplayName().copy().append(new TranslatableComponent("entity.rigoranthusemortisreborn.familiar_cadaver")).setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_RED).withItalic(true)));
                cadaver.setOwnerUUID(playerIn.getUUID());
                playerIn.addEffect(new MobEffectInstance(ModPotions.SUMMONING_COOLDOWN, 5000, 0, false, false));
            }
        }
    }
//    @SubscribeEvent
//    public static void playerAttackEntity(AttackEntityEvent e) {
//        LivingEntity entity = e.getEntityLiving();
//        if(entity == null || entity.getCommandSenderWorld().isClientSide || !(entity instanceof Player) && !(entity instanceof TamableAnimal))
//            return;
//        if (entity.hasEffect(ModPotions.SUMMONING_COOLDOWN))
//            return;
//        ItemStack s = e.getEntityLiving().getMainHandItem();
//        castSummon((Player) entity, s);
//    }
//    @SubscribeEvent
//    public static void leftClickAir(PlayerInteractEvent.LeftClickEmpty e) {
//        LivingEntity entity = e.getEntityLiving();
//        if(!(entity instanceof Player))
//            return;
//        if (entity.hasEffect(ModPotions.SUMMONING_COOLDOWN))
//            return;
//        if(EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.REFLEX_SUMMON_ENCHANTMENT, e.getItemStack()) > 0)
//            Networking.INSTANCE.sendToServer(new PacketReflexSummon());
//    }
}