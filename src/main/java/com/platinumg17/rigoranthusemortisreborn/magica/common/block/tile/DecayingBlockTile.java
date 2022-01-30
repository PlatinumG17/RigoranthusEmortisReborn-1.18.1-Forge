package com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile;

import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.ParticleColor;
import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.ParticleUtil;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class DecayingBlockTile extends AnimatedTile implements IAnimatable {

    int age;
    public boolean isPermanent;
    public double lengthModifier;
    public ParticleColor color = ParticleUtil.defaultParticleColor();
    public DecayingBlockTile(BlockPos pos, BlockState state) {
        super(BlockRegistry.DECAYING_TILE, pos, state);
    }

    @Override
    public void tick() {
        if(isPermanent)
            return;
        if(!level.isClientSide){
            age++;
            //15 seconds
            if(age > (20 * 15 + 20 * 5 * lengthModifier)){
                level.destroyBlock(this.getBlockPos(), false);
                level.removeBlockEntity(this.getBlockPos());
            }
        }
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.age = compound.getInt("age");
        this.color = ParticleColor.IntWrapper.deserialize(compound.getString("color")).toParticleColor();
        this.isPermanent = compound.getBoolean("permanent");
        this.lengthModifier = compound.getDouble("modifier");
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.put("age", IntTag.valueOf(age));
        compound.putString("color", color.toWrapper().serialize());
        compound.putBoolean("permanent", isPermanent);
        compound.putDouble("modifier", lengthModifier);
    }

    @Override
    public void registerControllers(AnimationData data) { }
    AnimationFactory factory = new AnimationFactory(this);
    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}