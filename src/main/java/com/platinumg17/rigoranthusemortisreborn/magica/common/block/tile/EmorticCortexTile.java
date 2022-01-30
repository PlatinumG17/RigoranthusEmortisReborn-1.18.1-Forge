package com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile;

import com.platinumg17.rigoranthusemortisreborn.magica.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class EmorticCortexTile extends BlockEntity implements IAnimatable, ISyncable {
    AnimationFactory manager = new AnimationFactory(this);
    public String brainController = "brain";
    public String gemController = "gem";
    public EmorticCortexTile(BlockPos pos, BlockState state) {
        super(BlockRegistry.EMORTIC_CORTEX_TILE, pos, state);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, brainController, 0, this::idlePredicate));
        data.addAnimationController(new AnimationController(this, gemController, 0, this::floatPredicate));
    }
    private <P extends IAnimatable> PlayState idlePredicate(AnimationEvent<P> pAnimationEvent) {
        pAnimationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("brain_float", true));
        return PlayState.CONTINUE;
    }
    private <P extends IAnimatable> PlayState floatPredicate(AnimationEvent<P> pAnimationEvent) {
        pAnimationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("gem_float", true));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return manager;
    }

    @Override
    public void onAnimationSync(int id, int state) {
        final AnimationController<?> controllerG = GeckoLibUtil.getControllerForID(this.manager, id, gemController);
        final AnimationController<?> controllerB = GeckoLibUtil.getControllerForID(this.manager, id, brainController);
        if (controllerG.getAnimationState() == AnimationState.Stopped) {
            controllerG.markNeedsReload();
            controllerG.setAnimation(new AnimationBuilder().addAnimation("gem_float", true));
        }
        if (controllerB.getAnimationState() == AnimationState.Stopped) {
            controllerB.markNeedsReload();
            controllerB.setAnimation(new AnimationBuilder().addAnimation("brain_float", true));
        }
    }

//    @Override
//    public void tick() {
//        if(level.isClientSide) {
//            ParticleColor randColor = new ParticleColor(level.random.nextInt(255), level.random.nextInt(255), level.random.nextInt(255));
//            for (int i = 0; i < 6; i++) {
//                level.addParticle(
//                        GlowParticleData.createData(randColor),
//                        worldPosition.getX() + 0.5 + ParticleUtil.inRange(-0.3, 0.3), worldPosition.getY() + 0.5 + ParticleUtil.inRange(-0.3, 0.3), worldPosition.getZ() + 0.5 + ParticleUtil.inRange(-0.3, 0.3),
//                        0, 0, 0);
//            }
//        }
//    }
}