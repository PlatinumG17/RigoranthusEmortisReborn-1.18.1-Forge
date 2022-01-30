package com.platinumg17.rigoranthusemortisreborn.magica.common.event;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.magica.client.ClientInfo;
import com.platinumg17.rigoranthusemortisreborn.magica.common.command.DataDumpCommand;
import com.platinumg17.rigoranthusemortisreborn.magica.common.command.PathCommand;
import com.platinumg17.rigoranthusemortisreborn.magica.common.command.ResetCommand;
import com.platinumg17.rigoranthusemortisreborn.magica.common.compat.CaelusHandler;
import com.platinumg17.rigoranthusemortisreborn.magica.common.potions.ModPotions;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.MagicItemsRegistry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EmortisConstants.MOD_ID)
public class EventHandler {

//    @SubscribeEvent(priority= EventPriority.LOWEST)
//    public static void itemPickupEvent( EntityItemPickupEvent event) {
//        Player player = event.getPlayer();
//        ItemStack pickingUp = event.getItem().getItem();
//    }

    @SubscribeEvent
    public static void livingEntityUseItemEvent(LivingEntityUseItemEvent.Finish e) {
        if(!e.getEntityLiving().level.isClientSide && e.getEntityLiving() instanceof Player) {
            if(((Player) e.getEntityLiving()).getUseItem() == MagicItemsRegistry.BOTTLE_OF_ICHOR.getDefaultInstance()) {
                ((Player) e.getEntityLiving()).addEffect(new MobEffectInstance(ModPotions.NECROTIZING_FASCIITIS_EFFECT, 100, 1));
            }
        }
    }

//    @SubscribeEvent
//    public static void livingHurtEvent(LivingHurtEvent e){
//        if(!e.getEntityLiving().level.isClientSide && e.getEntityLiving() instanceof Player && e.getEntityLiving().isBlocking()){
//            if(e.getEntityLiving().isHolding(MagicItemsRegistry.LUSTERIC_SHIELD)){
//                e.getEntityLiving().addEffect(new MobEffectInstance(ModPotions.DOMINION_REGEN_EFFECT, 200, 1));
//            }
//        }
//    }

//    @SubscribeEvent
//    public static void livingAttackEvent(LivingAttackEvent e){
//        if(e.getSource() == DamageSource.HOT_FLOOR && e.getEntityLiving() /* != null */ instanceof Player && !e.getEntity().getCommandSenderWorld().isClientSide) {
//            Level world = e.getEntity().level;
//            if(world.getBlockState(e.getEntityLiving().blockPosition()).getBlock() instanceof OpulentMagmaBlock){
//                e.setCanceled(true);
//            }
//        }
//    }

    @SubscribeEvent
    public static void jumpEvent(LivingEvent.LivingJumpEvent e) {
        if(e.getEntityLiving() == null  || e.getEntityLiving().getEffect(ModPotions.SNARE_EFFECT) == null)
            return;
        e.getEntityLiving().setDeltaMovement(0,0,0);
    }

//    @SubscribeEvent
//    public static void playerLogin(PlayerEvent.PlayerLoggedInEvent e) {
//        if(e.getEntityLiving().getCommandSenderWorld().isClientSide || !Config.SPAWN_BOOK.get())
//            return;
//        CompoundTag tag = e.getPlayer().getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
//        String book_tag = "re_book_";
//        if(tag.getBoolean(book_tag))
//            return;
//        LivingEntity entity = e.getEntityLiving();
//        e.getEntityLiving().getCommandSenderWorld().addFreshEntity(new ItemEntity(entity.level, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(MagicItemsRegistry.emorticOrigins)));
//        tag.putBoolean(book_tag, true);
//        e.getPlayer().getPersistentData().put(Player.PERSISTED_NBT_TAG, tag);
//    }

    @SubscribeEvent
    public static void clientTickEnd(TickEvent.ClientTickEvent event) {
        if(event.phase == TickEvent.Phase.END) {
            ClientInfo.ticksInGame++;
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onGlideTick(TickEvent.PlayerTickEvent event) {
        if(RigoranthusEmortisReborn.caelusLoaded && event.player.hasEffect(ModPotions.GLIDER_EFFECT)) {
            CaelusHandler.setFlying(event.player);
        }
    }

    @SubscribeEvent
    public static void playerDamaged(LivingHurtEvent e) {
        if(e.getEntityLiving() != null && e.getEntityLiving().getActiveEffectsMap().containsKey(ModPotions.SHIELD_POTION)
                && (e.getSource() == DamageSource.MAGIC || e.getSource() == DamageSource.GENERIC || e.getSource() instanceof EntityDamageSource)) {
            float damage = e.getAmount() - (1.0f + 0.5f * e.getEntityLiving().getActiveEffectsMap().get(ModPotions.SHIELD_POTION).getAmplifier());
            e.setAmount(Math.max(0, damage));
        }
    }

    @SubscribeEvent
    public static void entityHurt(LivingHurtEvent e) {
        if(e.getEntityLiving() != null && e.getSource() == DamageSource.LIGHTNING_BOLT && e.getEntityLiving().getEffect(ModPotions.SHOCKED_EFFECT) != null) {
            float damage = e.getAmount() + 3.0f + 3.0f * e.getEntityLiving().getEffect(ModPotions.SHOCKED_EFFECT).getAmplifier();
            e.setAmount(Math.max(0, damage));
        }
        LivingEntity entity = e.getEntityLiving();
        if(entity != null  && entity.getEffect(ModPotions.HEX_EFFECT) != null &&
                (entity.getEffect(MobEffects.POISON) != null || entity.getEffect(MobEffects.WITHER) != null || entity.isOnFire() || entity.getEffect(ModPotions.SHOCKED_EFFECT) != null)) {
            e.setAmount(e.getAmount() + 0.5f + 0.33f*entity.getEffect(ModPotions.HEX_EFFECT).getAmplifier());
        }
    }

    @SubscribeEvent
    public static void entityHeal(LivingHealEvent e){
        LivingEntity entity = e.getEntityLiving();
        if(entity != null  && entity.getEffect(ModPotions.HEX_EFFECT) != null){
            e.setAmount(e.getAmount()/2.0f);
        }
    }

    @SubscribeEvent
    public static void commandRegister(RegisterCommandsEvent event){
        ResetCommand.register(event.getDispatcher());
//        DataDumpCommand.register(event.getDispatcher());
//        PathCommand.register(event.getDispatcher());
    }
    private EventHandler(){}
}