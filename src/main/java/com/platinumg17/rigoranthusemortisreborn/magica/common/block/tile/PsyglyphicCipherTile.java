package com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile;

import com.platinumg17.rigoranthusemortisreborn.magica.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class PsyglyphicCipherTile extends BlockEntity implements IAnimatable {//, ITooltipProvider {
//    public int tickCounter;
//    public boolean hasBeenUsed;
//    public static final BooleanProperty UTILIZED = BooleanProperty.create("transcribed");

    public PsyglyphicCipherTile(BlockPos pos, BlockState state) {
        super(BlockRegistry.PSYGLYPHIC_TILE, pos, state);
    }
//    @Override
//    public void tick() {
//        if (level.isClientSide)
//            return;
//        if (!hasBeenUsed) {
//            transcriptionEffect();
//            return;
//        }
//    }
//    public void transcriptionEffect() {
//        tickCounter++;
//    }

    AnimationFactory manager = new AnimationFactory(this);
    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "float_controller", 0, this::idlePredicate));
    }

    private <E extends BlockEntity & IAnimatable > PlayState idlePredicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("float", true));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return manager;
    }

//    @Override
//    public List<String> getTooltip() {
//        List<String> tooltips = new ArrayList<>();
//            tooltips.add("Psyglyphic Cipher");
//            if (this.hasBeenUsed) {
//                tooltips.add(new TranslatableComponent("rigoranthusemortisreborn.tooltip.cipher_used").getString());
//            } else {
//                tooltips.add(new TranslatableComponent("rigoranthusemortisreborn.tooltip.cipher_not_used").getString());
//            }
//        return tooltips;
//    }

//    @Override
//    public void load(BlockState state, CompoundTag tag) {
//        super.load(state, tag);
//        hasBeenUsed = tag.getBoolean("transcribed");
//    }
//
//    @Override
//    public void saveAdditional(CompoundTag tag) {
//        tag.putBoolean("transcribed", hasBeenUsed);
//        return super.save(tag);
//    }

//    @SubscribeEvent
//    public void rightClick(PlayerInteractEvent.RightClickBlock event) {
//        if (!(event.getWorld().getBlockEntity(event.getPos()) instanceof PsyglyphicCipherTile))
//            return;
//
//        Level world = event.getWorld();
//        BlockPos pos = event.getPos();
//
//        if (world.getBlockState(pos).getBlock() instanceof CipherBlock) {
//            BlockRegistry.PSYGLYPHIC_CIPHER.use(world.getBlockState(pos), world, pos, event.getPlayer(), event.getHand(), null);
//            event.setCanceled(true);
//        }
//    }
    @Override
    @Nullable
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag);
        return tag;
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getTag() == null ? new CompoundTag() : pkt.getTag());
    }

//    @Override
//    public void getUpdatePacket() {
//        super.getUpdatePacket();
//
//        if(this.level != null)
//            level.markAndNotifyBlock(worldPosition, level.getChunkAt(worldPosition), level.getBlockState(worldPosition).getBlock().defaultBlockState(), level.getBlockState(worldPosition), 3, 3);
//    }
//    @Override
//    public void tick() {
//        if (level.isClientSide)
//            return;
//        if (level.getGameTime() % 20 == 0 && !this.hasBeenUsed) {
//            for(ItemEntity i : level.getEntitiesOfClass(ItemEntity.class, new AABB(worldPosition).inflate(1.0))){
//                ItemStack containerItem = i.getItem().getContainerItem();
//                i.getItem().shrink(1);
//                this.hasBeenUsed = true;
//                this.setChanged();
//                if(!containerItem.isEmpty()){
//                    level.addFreshEntity(new ItemEntity(level, i.getX(), i.getY(), i.getZ(), containerItem));
//                }
//                Networking.sendToNearby(level, getBlockPos(),
//                        new PacketREEffect(PacketREEffect.MobEffectCategory.BURST, i.blockPosition(), new ParticleColor.IntWrapper(255, 0, 0)));
//                return;
//            }
//        }
//    }
}