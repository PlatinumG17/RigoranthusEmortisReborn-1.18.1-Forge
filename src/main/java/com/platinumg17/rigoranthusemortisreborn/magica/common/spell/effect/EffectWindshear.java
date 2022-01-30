package com.platinumg17.rigoranthusemortisreborn.magica.common.spell.effect;

import com.platinumg17.rigoranthusemortisreborn.api.RigoranthusEmortisRebornAPI;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.*;
import com.platinumg17.rigoranthusemortisreborn.magica.common.lib.GlyphLib;
import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.ParticleUtil;
import com.platinumg17.rigoranthusemortisreborn.magica.common.spell.augment.AugmentAmplify;
import com.platinumg17.rigoranthusemortisreborn.magica.common.spell.augment.AugmentDampen;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public class EffectWindshear extends AbstractEffect {

    public static EffectWindshear INSTANCE = new EffectWindshear();

    private EffectWindshear() {
        super(GlyphLib.EffectWindshearID, "Wind Shear");
    }

    @Override
    public void onResolveEntity(EntityHitResult rayTraceResult, Level world, @Nullable LivingEntity shooter, SpellStats spellStats, SpellContext spellContext) {
        super.onResolveEntity(rayTraceResult, world, shooter, spellStats, spellContext);
        if(!rayTraceResult.getEntity().isOnGround()){
            int numBlocks = 0;
            BlockPos pos = rayTraceResult.getEntity().blockPosition();
            while(!world.getBlockState(pos.below()).getMaterial().blocksMotion() && numBlocks <= 10){
                pos = pos.below();
                numBlocks++;
            }
            dealDamage(world, shooter, (float) (DAMAGE.get() + numBlocks), spellStats, rayTraceResult.getEntity(), DamageSource.FALL);
            Vec3 vec = rayTraceResult.getEntity().position;
            for(int i = 0; i < 10; i++){
                ((ServerLevel)world).sendParticles(ParticleTypes.SWEEP_ATTACK, vec.x + ParticleUtil.inRange(-0.2, 0.2), vec.y +0.5 + ParticleUtil.inRange(-0.2, 0.2), vec.z + ParticleUtil.inRange(-0.2, 0.2),30,
                        ParticleUtil.inRange(-0.2, 0.2), ParticleUtil.inRange(-0.2, 0.2),ParticleUtil.inRange(-0.2, 0.2), 0.3);
            }
        }
    }

    @Override
    public void buildConfig(ForgeConfigSpec.Builder builder) {
        super.buildConfig(builder);
        addDamageConfig(builder, 5);
        addAmpConfig(builder, 2.5f);
        addGenericDouble(builder, 0.75, "Damage per block in the air", "airDamage");
    }

    @Override
    public String getBookDescription() {
        return "Deals damage to targets in the air, with an increasing amount based on how high the target is off the ground, up to 10 blocks. Targets on the ground take no damage.";
    }

    @Override
    public int getDefaultDominionCost() {
        return 50;
    }

    @Nullable
    @Override
    public Item getCraftingReagent() {
        return RigoranthusEmortisRebornAPI.getInstance().getGlyphItem(EffectKnockback.INSTANCE);
    }

    @Nonnull
    @Override
    public Set<SpellSchool> getSchools() {
        return setOf(SpellSchools.ELEMENTAL_AIR);
    }

    @Override
    public SpellTier getTier() {
        return SpellTier.TWO;
    }

    @Nonnull
    @Override
    public Set<AbstractAugment> getCompatibleAugments() {
        return augmentSetOf(AugmentDampen.INSTANCE, AugmentAmplify.INSTANCE);
    }
}