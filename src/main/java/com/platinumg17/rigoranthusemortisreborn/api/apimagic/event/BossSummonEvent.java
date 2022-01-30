package com.platinumg17.rigoranthusemortisreborn.api.apimagic.event;

//import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
//import com.platinumg17.rigoranthusemortisreborn.magica.ITimedEvent;
//import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.ParticleUtil;
//import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.NBTUtil;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.Mob;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.level.Level;
//
//import javax.annotation.Nullable;
//
//public class BossSummonEvent implements ITimedEvent {
//
//    int duration;
//    int phase;
//    Level world;
//    BlockPos pos;
//    int ownerID;
//    public BossSummonEvent(int duration, int phase, Level world, BlockPos pos, int ownerID){
//        this.duration = duration;
//        this.phase = phase;
//        this.world = world;
//        this.pos = pos;
//        this.ownerID = ownerID;
//    }
//
//    public static BossSummonEvent get(CompoundTag tag){
//        return new BossSummonEvent(tag.getInt("duration"),
//                tag.getInt("phase"), RigoranthusEmortisReborn.proxy.getClientWorld(), NBTUtil.getBlockPos(tag, "loc"), -1);
//    }
//
//    @Override
//    public void tick(boolean serverSide) {
////        duration--;
////        if(serverSide){
////            Entity owner = world.getEntity(ownerID);
////            if(!(owner instanceof EntityBoss)) {
////                duration = 0;
////                return;
////            }
////            EntityBoss boss = (EntityBoss) owner;
////            boolean summonedDweller = false;
////            if(duration % 20 ==0){
////                Random random = boss.getRandom();
////                SummonWolf wolf = new SummonWolf(ModEntities.SUMMON_WOLF, world);
////
////                wolf.setPos(getPos().getX(), getPos().getY(), getPos().getZ());
////                wolf.isHostileSummon = true;
////                wolf.ticksLeft = 100 + phase * 60;
////                summon(wolf, getPos(), boss.getTarget());
////
////                int randBound = 10 - boss.getPhase();
////                if(!summonedDweller && boss.hasWings() && boss.level.random.nextInt(randBound) == 0){
////                    SunderedCadaver cadaver = new SunderedCadaver(RigoranthusEntitiyTypes.SUNDERED_CADAVER, world);
////                    summon(cadaver, getPos(), boss.getTarget());
////                    summonedDweller = true;
////                }
////
////                if(boss.hasHorns() && boss.level.random.nextInt(randBound) == 0){
////                    NecrawFascii necraw = new NecrawFascii(RigoranthusEntitiyTypes.NECRAW_FASCII, world);
////                    summon(necraw, getPos(), boss.getTarget());
////                    summonedDweller = true;
////                }
////
////                if(!summonedDweller && boss.hasSpikes() && boss.level.random.nextInt(randBound) == 0){
////                    LanguidDweller dweller = new LanguidDweller(RigoranthusEntitiyTypes.LANGUID_DWELLER, world);
////                    summon(dweller, getPos(), boss.getTarget());
////                    summonedDweller = true;
////                }
////            }
////        }else{
////            ParticleUtil.spawnRitualAreaEffect(pos, world, world.random, ParticleUtil.defaultParticleColor(), 1 + phase * 2);
////        }
//    }
//
//    public void summon(Mob mob, BlockPos pos, @Nullable LivingEntity target){
//        mob.setPos(pos.getX(), pos.getY(), pos.getZ());
//        mob.setTarget(target);
//        mob.setAggressive(true);
//        mob.level.addFreshEntity(mob);
//    }
//
//    public BlockPos getPos(){
//        double spawnArea = 2.5 + phase *2;
//        return new BlockPos(pos.getX() + ParticleUtil.inRange(-spawnArea, spawnArea), pos.getY() + 2, pos.getZ() + ParticleUtil.inRange(-spawnArea, spawnArea));
//    }
//
//    @Override
//    public boolean isExpired() {
//        return duration <= 0;
//    }
//
//    @Override
//    public CompoundTag serialize(CompoundTag tag) {
//        ITimedEvent.super.serialize(tag);
//        tag.putInt("duration", duration);
//        tag.putInt("phase", phase);
//        NBTUtil.storeBlockPos(tag, "loc", pos);
//        return tag;
//    }
//
//    public static final String ID = "boss_name_here";
//    @Override
//    public String getID() {
//        return ID;
//    }
//}