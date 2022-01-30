package com.platinumg17.rigoranthusemortisreborn.magica.common.spell.effect;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.*;
import com.platinumg17.rigoranthusemortisreborn.magica.common.lib.GlyphLib;
import com.platinumg17.rigoranthusemortisreborn.magica.common.entity.ModEntities;
import com.platinumg17.rigoranthusemortisreborn.magica.common.entity.SummonHorse;
import com.platinumg17.rigoranthusemortisreborn.magica.common.spell.augment.AugmentAOE;
import com.platinumg17.rigoranthusemortisreborn.magica.common.spell.augment.AugmentDurationDown;
import com.platinumg17.rigoranthusemortisreborn.magica.common.spell.augment.AugmentExtendTime;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public class EffectSummonSteed extends AbstractEffect {
    public static EffectSummonSteed INSTANCE = new EffectSummonSteed();


    private EffectSummonSteed() {
        super(GlyphLib.EffectSummonSteedID, "Summon Steed");
    }

    @Override
    public void onResolve(HitResult rayTraceResult, Level world, @Nullable LivingEntity shooter, SpellStats spellStats, SpellContext spellContext) {

        if(!canSummon(shooter))
            return;

        int ticks = (int) (20 * (GENERIC_INT.get() +  EXTEND_TIME.get() * spellStats.getDurationMultiplier()));
        Vec3 hit = rayTraceResult.getLocation();
        for(int i = 0; i < 1 + spellStats.getBuffCount(AugmentAOE.INSTANCE); i++){
            SummonHorse horse = new SummonHorse(ModEntities.SUMMON_HORSE, world);
            horse.setPos(hit.x(), hit.y(), hit.z());
            horse.ticksLeft = ticks;
            horse.tameWithName((Player) shooter);
            world.addFreshEntity(horse);
            horse.getHorseInventory().setItem(0, new ItemStack(Items.SADDLE));
            horse.setOwnerID(shooter.getUUID());
            horse.setDropChance(EquipmentSlot.CHEST, 0.0F);
        }
        applySummoningSickness(shooter, 30 * 20);
    }


    @Override
    public void buildConfig(ForgeConfigSpec.Builder builder) {
        super.buildConfig(builder);
        addExtendTimeConfig(builder, 120);
        addGenericInt(builder, 300, "Base duration in seconds", "duration");
    }

    @Override
    public int getDefaultDominionCost() {
        return 100;
    }

    @Override
    public SpellTier getTier() {
        return SpellTier.ONE;
    }

    @Nonnull
    @Override
    public Set<AbstractAugment> getCompatibleAugments() {
        return augmentSetOf(
                AugmentExtendTime.INSTANCE, AugmentDurationDown.INSTANCE, AugmentAOE.INSTANCE
        );
    }

    @Override
    public String getBookDescription() {
        return "Summons a saddled horse that will vanish after a few minutes. AOE will increase the amount summoned, while Extend Time will increase the duration of the summon. Applies Summoning Sickness to the caster, and cannot be cast while afflicted by this Sickness.";
    }

    @Nullable
    @Override
    public Item getCraftingReagent() {
        return Items.LEATHER;
    }

    @Nonnull
    @Override
    public Set<SpellSchool> getSchools() {
        return setOf(SpellSchools.CONJURATION);
    }
}