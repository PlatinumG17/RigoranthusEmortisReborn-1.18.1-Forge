package com.platinumg17.rigoranthusemortisreborn.core.registry.effects.weapons;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EmortisConstants.MOD_ID, bus=Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEventHandler {
//    public static long lastDay;

//    @SubscribeEvent
//    public static void serverStarting(FMLServerStartingEvent event) {
//        EntityDialogue.serverStarting();
//
//        lastDay = event.getServer().overworld().getGameTime() / 24000L;
//    }

//    @SubscribeEvent
//    public static void serverStopped(FMLServerStoppedEvent event) {
//        IdentifierHandler.clear();
//    }

//    @SubscribeEvent
//    public static void onWorldTick(TickEvent.WorldTickEvent event) {
//        if(event.phase == TickEvent.Phase.END) {
//
//            if(/*!Config.hardMode.get() &&*/ event.world.dimension() == World.OVERWORLD) {
//                long time = event.world.getGameTime() / 24000L;
//                if(time != lastDay) {
//                    lastDay = time;
//                    DailyBonusHandler.resetGivenItems(event.world.getServer());
//                }
//            }
//            MinecraftServer server = event.world.getServer();
//            if(server != null)
//                REExtraData.get(server).executeEntryTasks(server);
//        }
//    }

//    @SubscribeEvent(priority=EventPriority.LOWEST, receiveCanceled=false)
//    public static void onEntityDeath(LivingDeathEvent event) {
//        if(event.getEntity() instanceof IMob && event.getSource().getEntity() instanceof ServerPlayer) {
//            ServerPlayer player = (ServerPlayer) event.getSource().getEntity();
//            int exp = 0;
//            if(event.getEntity() instanceof Zombie || event.getEntity() instanceof SkeletonEntity)
//                exp = 1;
//            else if(event.getEntity() instanceof CreeperEntity || event.getEntity() instanceof SpiderEntity || event.getEntity() instanceof SilverfishEntity)
//                exp = 2;
//            else if(event.getEntity() instanceof EndermanEntity || event.getEntity() instanceof BlazeEntity || event.getEntity() instanceof WitchEntity || event.getEntity() instanceof GuardianEntity)
//                exp = 3;
//            else if(event.getEntity() instanceof SlimeEntity)
//                exp = Math.min(((SlimeEntity) event.getEntity()).getSize() - 1, 9);
//
//            if(exp > 0)
//                AspectProgressBar.increaseProgress(player, exp);
//        }
////        if(event.getEntity() instanceof ServerPlayer) {
////            TitleSelectionHook.cancelSelection((ServerPlayer) event.getEntity());
////        }
//    }

    // Stores the crit result from the CriticalHitEvent, to be used during LivingHurtEvent to trigger special effects of any weapons.
    // This method is reliable only as long as LivingHurtEvent is posted only on the main thread and after a matching CriticalHitEvent
    private static boolean cachedCrit;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCrit(CriticalHitEvent event) {
        if(!event.getEntity().level.isClientSide)
            cachedCrit = event.getResult() == Event.Result.ALLOW || event.getResult() == Event.Result.DEFAULT && event.isVanillaCritical();
    }

    public static boolean wasLastHitCrit(LivingEntity entity) {
        return entity instanceof ServerPlayer && cachedCrit;
    }

//    @SubscribeEvent(priority=EventPriority.NORMAL)
//    public static void onEntityAttack(LivingHurtEvent event) {
//        if(event.getSource().getEntity() != null) {
//            if (event.getSource().getEntity() instanceof ServerPlayer) {
//                ServerPlayer player = (ServerPlayer) event.getSource().getEntity();
//                if (event.getEntityLiving() instanceof LanguidDwellerEntity) {
//                    double modifier = PlayerSavedData.getData(player).getAspectProgressBar().getDwellerDamageModifier();
//                    event.setAmount((float) (event.getAmount() * modifier));
//                }// Increase damage player deals against the specified mob
//            }
//            else if (event.getEntityLiving() instanceof ServerPlayer && event.getSource().getEntity() instanceof LanguidDwellerEntity) {
//                ServerPlayer player = (ServerPlayer) event.getEntityLiving();
//                double modifier = PlayerSavedData.getData(player).getAspectProgressBar().getDwellerProtectionModifier();
//                event.setAmount((float) (event.getAmount() * modifier));
//            }// Decrease the damage the mob inflicts upon the player
//        }
//    }
//
//    @SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = false)
//    public static void onEntityDamage(LivingHurtEvent event) {
//        if(event.getEntityLiving() instanceof LanguidDwellerEntity) {
//            ((LanguidDwellerEntity) event.getEntityLiving()).onEntityDamaged(event.getSource(), event.getAmount());
//        }
//    }
//
//    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = false)
//    public static void onPlayerInjured(LivingHurtEvent event) {
//        if(event.getEntityLiving() instanceof Player) {
//            Player injuredPlayer = ((Player) event.getEntity());
//            Title title = PlayerSavedData.getData((ServerPlayer) injuredPlayer).getTitle();
//            boolean isDoom = title != null && title.getHeroAspect() == EnumAspect.DOOM;
//            ItemStack handItem = injuredPlayer.getMainHandItem();
//            float activateThreshold = ((injuredPlayer.getMaxHealth() / (injuredPlayer.getHealth() + 1)) / injuredPlayer.getMaxHealth()); //fraction of players health that rises dramatically the more injured they are
//
//            if(handItem.getItem() == ModItems.HAMMER_OF_UNDYING) {
//                if(isDoom)
//                    activateThreshold = activateThreshold * 1.5F;
//
//                activateThreshold = activateThreshold + injuredPlayer.getRandom().nextFloat() * .9F;
//
//                if(activateThreshold >= 1.0F && injuredPlayer.getRandom().nextFloat() >= .75) {
//                    injuredPlayer.level.playSound(null, injuredPlayer.getX(), injuredPlayer.getY(), injuredPlayer.getZ(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 1.4F);
//                    injuredPlayer.setHealth(injuredPlayer.getHealth() + 3);
//                    injuredPlayer.addEffect(new MobEffectInstance(Effects.REGENERATION, 450, 0));
//                    if(isDoom) {
//                        injuredPlayer.addEffect(new MobEffectInstance(Effects.ABSORPTION, 100, 0));
//                        handItem.hurtAndBreak(100, injuredPlayer, playerEntity -> playerEntity.broadcastBreakEvent(InteractionHand.MAIN_HAND));
//                    }
//                    else {
//                        handItem.hurtAndBreak(250, injuredPlayer, playerEntity -> playerEntity.broadcastBreakEvent(InteractionHand.MAIN_HAND));
//                    }
//                }
//            }
//
//            if(handItem.getItem() == ModItems.CRUEL_FATE) {
//                activateThreshold = activateThreshold * 8 + injuredPlayer.getRandom().nextFloat() * .9F;
//
//                if((isDoom && activateThreshold >= 1.0F && injuredPlayer.getRandom().nextFloat() <= .2) || (!isDoom && activateThreshold >= 1.0F && injuredPlayer.getRandom().nextFloat() <= .05)) {
//                    AABB axisalignedbb = injuredPlayer.getBoundingBox().inflate(4.0D, 2.0D, 4.0D);
//                    List<LivingEntity> list = injuredPlayer.level.getEntitiesOfClass(LivingEntity.class, axisalignedbb);
//                    list.remove(injuredPlayer);
//                    if(!list.isEmpty()) {
//                        injuredPlayer.level.playSound(null, injuredPlayer.getX(), injuredPlayer.getY(), injuredPlayer.getZ(), SoundEvents.WITHER_HURT, SoundSource.PLAYERS, 0.5F, 1.6F);
//                        if(isDoom)
//                            handItem.hurtAndBreak(2, injuredPlayer, playerEntity -> playerEntity.broadcastBreakEvent(InteractionHand.MAIN_HAND));
//                        else
//                            handItem.hurtAndBreak(10, injuredPlayer, playerEntity -> playerEntity.broadcastBreakEvent(InteractionHand.MAIN_HAND));
//                        for(LivingEntity livingentity : list) {
//                            livingentity.addEffect(new MobEffectInstance(Effects.HARM, 1, 1));
//                        }
//                    }
//                }
//            }
//        }
//    }

//    @SubscribeEvent
//    public static void playerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {TitleSelectionHook.cancelSelection((ServerPlayer) event.getPlayer());}

//    @SubscribeEvent(priority=EventPriority.LOW, receiveCanceled=false)
//    public static void onServerChat(ServerChatEvent event) {
//        Modus modus = PlayerSavedData.getData(event.getPlayer()).getModus();
//        if(modus instanceof HashMapModus)
//            ((HashMapModus) modus).onChatMessage(event.getPlayer(), event.getMessage());
//    }

    //This functionality uses an event to maintain compatibility with mod items having hoe functionality but not extending ItemHoe, like TiCon mattocks.
/*    @SubscribeEvent
    public static void onPlayerUseHoe(UseHoeEvent event) {	//TODO --> replace by an extension to block.getToolModifiedState()
        if(event.getContext().getLevel().getBlockState(event.getContext().getClickedPos()).getBlock() == ModBlocks.CUSTOM_BLOCK) {
            event.getContext().getLevel().setBlockAndUpdate(event.getContext().getClickedPos(), Blocks.END_STONE.defaultBlockState());
            event.getContext().getLevel().playSound(null, event.getContext().getClickedPos(), SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 	1.0F);
            event.setResult(Event.Result.ALLOW);
        }
    }*/

/*    @SubscribeEvent
    public static void onGetItemBurnTime(FurnaceFuelBurnTimeEvent event) {
        if(event.getItemStack().getItem() == ModBlocks.CUSTOM_BLOCK.asItem())
            event.setBurnTime(50);
    }*/

//    @SubscribeEvent
//    public static void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
//        if(!event.player.level.isClientSide) {
//            PlayerData data = PlayerSavedData.getData((ServerPlayer) event.player);
//            if(data.getTitle() != null)
//                data.getTitle().handleAspectEffects((ServerPlayer) event.player);
//        }
//    }

/*    @SubscribeEvent
    public static void turnRotten(ItemExpireEvent event) {
        ItemEntity e = event.getEntityItem();
        if(e.getItem().getCount() == 1 && (e.getItem().getItem() == Items.BREAD)) {
            ItemEntity rottenItem = new ItemEntity(e.level, e.getX(), e.getY(), e.getZ(), new ItemStack(ModItems.ROTTEN_FOOD_ITEM));
            e.level.addFreshEntity(rottenItem);
        }
    }*/
}