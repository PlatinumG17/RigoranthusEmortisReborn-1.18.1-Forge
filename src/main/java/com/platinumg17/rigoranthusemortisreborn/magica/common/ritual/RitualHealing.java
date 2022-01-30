package com.platinumg17.rigoranthusemortisreborn.magica.common.ritual;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.ritual.AbstractRitual;
import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.ParticleColor;
import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.ParticleUtil;
import com.platinumg17.rigoranthusemortisreborn.magica.common.lib.RitualLib;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.AABB;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

import java.util.List;
import java.util.Optional;

public class RitualHealing extends AbstractRitual {
    @Override
    protected void tick() {
        if(getWorld().isClientSide){
            ParticleUtil.spawnRitualAreaEffect(getPos(), getWorld(), rand, getCenterColor(), 5);
        }else{
            if(getWorld().getGameTime() % 100 == 0){
                List<LivingEntity> entities = getWorld().getEntitiesOfClass(LivingEntity.class, new AABB(getPos()).inflate(5));
                Optional<LivingEntity> player = entities.stream().filter(e -> e instanceof Player).findFirst();

                boolean didWorkOnce = false;
                for(LivingEntity a : entities){
                    if(a.getHealth() < a.getMaxHealth() || a.isInvertedHealAndHarm()) {
                        if(a.isInvertedHealAndHarm()){
                            FakePlayer player1 = FakePlayerFactory.getMinecraft((ServerLevel) getWorld());
                            a.hurt(DamageSource.playerAttack(player1).setMagic(), 10.0f);
                        }else{
                            a.heal(10.0f);
                        }
                        didWorkOnce = true;
                    }
                }
                if(didWorkOnce)
                    setNeedsDominion(true);
            }
        }
    }

    @Override
    public String getID() {
        return RitualLib.RESTORATION;
    }

    @Override
    public ParticleColor getCenterColor() {
        return ParticleColor.makeRandomColor(20, 240, 240, rand);
    }

    @Override
    public String getLangName() {
        return "Restoration";
    }

    @Override
    public String getLangDescription() {
        return "Heals nearby entities or harms undead over time. This ritual consumes Dominion.";
    }

    @Override
    public int getDominionCost() {
        return 200;
    }
}