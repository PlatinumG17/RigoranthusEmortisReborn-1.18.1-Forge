package com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile;

import com.platinumg17.rigoranthusemortisreborn.core.init.ItemInit;
import com.platinumg17.rigoranthusemortisreborn.entity.mobs.NecrawFasciiEntity;
import com.platinumg17.rigoranthusemortisreborn.entity.mobs.SunderedCadaverEntity;
import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.ParticleColor;
import com.platinumg17.rigoranthusemortisreborn.magica.common.entity.ModEntities;
import com.platinumg17.rigoranthusemortisreborn.magica.common.network.Networking;
import com.platinumg17.rigoranthusemortisreborn.magica.common.network.PacketREEffect;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.BlockRegistry;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.MagicItemsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ForgeHooks;
import software.bernie.geckolib3.core.IAnimatable;

import java.util.concurrent.atomic.AtomicReference;

public class IchorExtractorTile extends DominionTile implements IAnimatable {

    public IchorExtractorTile(BlockPos pos, BlockState state){
        super(BlockRegistry.ICHOR_EXTRACTOR_TILE, pos, state);
    }

    @Override
    public int getTransferRate() {
        return 5000;
    }

    @Override
    public void tick() {
        super.tick();
        if(level.isClientSide)
            return;
        if(level.getGameTime() % 20 == 0 && this.canAcceptDominion()) {

            for (ItemEntity i : level.getEntitiesOfClass(ItemEntity.class, new AABB(worldPosition).inflate(1.0))) {
                int ichor = getDominionValue(i.getItem());
                if (ichor > 0) {
                    this.addDominion(ichor);
                    ItemStack containerItem = i.getItem().getContainerItem();
                    i.getItem().shrink(1);
                    if (!containerItem.isEmpty()) {
                        level.addFreshEntity(new ItemEntity(level, i.getX(), i.getY(), i.getZ(), containerItem));
                    }
                    Networking.sendToNearby(level, getBlockPos(),
                            new PacketREEffect(PacketREEffect.EffectType.TIMED_HELIX, i.blockPosition(), new ParticleColor.IntWrapper(255, 0, 0)));
                    return;
                }
            }
            for (SplinteredPedestalTile i : getSurroundingPedestals()) {
                int ichorValue = getDominionValue(i.getItem(0));
                if (ichorValue > 0) {
                    this.addDominion(ichorValue);
                    ItemStack containerItem = i.getItem(0).getContainerItem();
                    i.removeItem(0, 1);
                    i.setItem(0, containerItem);
                    Networking.sendToNearby(level, getBlockPos(),
                            new PacketREEffect(PacketREEffect.EffectType.TIMED_HELIX, i.getBlockPos(), new ParticleColor.IntWrapper(255, 0, 0)));
                }
            }
        }
//        if(!level.isClientSide && level.getGameTime() % 20 == 0) {
//            int ichor = 100;
//            BlockPos ichorPos = findNearbyDominion(level, worldPosition);
//            DominionJarTile tile = (DominionJarTile) level.getBlockEntity(ichorPos);
//            if (ichorPos != null) { tile.removeDominion(100); }
////            if (DominionUtil.hasDominionNearby(this.worldPosition.below(), this.level, 0, ichor)) {
////                DominionUtil.takeDominionNearby(this.worldPosition.below(), this.level, 0, ichor);
////                this.addDominion(ichor);
////            }
//        }
    }

    public int getDominionValue(ItemStack i) {
        int ichor = 0;
        int progress = 0;
        int extractionTime = ForgeHooks.getBurnTime(i, null) ;
        if(extractionTime > 0) {
            ichor = extractionTime / 12; //TODO  --> Edit this value [12]
            progress = 1;
        }

        if(i.getItem().asItem() == MagicItemsRegistry.DWELLER_FLESH.asItem()) {
            ichor += 500;
            progress += 10; // 5 used to be highest
        }
        if(i.getItem().asItem() == ItemInit.BUCKET_OF_CADAVEROUS_ICHOR.get().asItem() || i.getItem().asItem() == ItemInit.BLIGHT_ICHOR.get().asItem()) {
            ichor += 400;
            progress += 10; // 5 used to be highest
        }
        if(i.getItem().asItem() == Items.PHANTOM_MEMBRANE) {
            ichor += 200; // was 100
            progress += 8;
        }
        if(i.getItem().asItem() == MagicItemsRegistry.BOTTLE_OF_ICHOR || i.getItem().asItem() == Items.FERMENTED_SPIDER_EYE) {
            ichor += 100; //was 50
            progress += 5;
        }
        if (i.getItem().asItem() == Items.SPIDER_EYE) {
            ichor += 75;
            progress += 5;
        }
        if (i.getItem().asItem() == Items.BONE_BLOCK) {
            ichor += 45;
            progress += 2;
        }
        if (i.getItem().asItem() == Items.BONE || i.getItem().asItem() == Items.ROTTEN_FLESH) {
            ichor += 15;
            progress += 2;
        }
        else if(i.getItem().asItem() == Items.BONE_MEAL || i.getItem().asItem() == ItemInit.BONE_FRAGMENT.get()) {
            ichor += 5;
            progress += 2;
        }

        this.progress += progress;
        return ichor;
    }


    public void doRandomAction() {
        if(level.isClientSide)
            return;
        BlockPos skullPos = getBlockInArea(BlockRegistry.hangingCadaverSkull, 2);
        BlockPos skullPos2 = getBlockInArea(BlockRegistry.CADAVER_SKULL, 2);
        if((skullPos != null || skullPos2 != null) && progress >= 200) {
            SunderedCadaverEntity sunderedCadaver = ModEntities.SUNDERED_CADAVER.create(level);
            SunderedCadaverEntity sunderedCadaver2 = ModEntities.SUNDERED_CADAVER.create(level);
                if (level instanceof ServerLevel) {

                    LightningBolt lightningBoltEntity = EntityType.LIGHTNING_BOLT.create(level);

                    lightningBoltEntity.absMoveTo(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), 0.0F, 0.0F);
                    lightningBoltEntity.setVisualOnly(true);

                    level.addFreshEntity(lightningBoltEntity);

                    sunderedCadaver.absMoveTo(worldPosition.getX() + 1, worldPosition.getY(), worldPosition.getZ() + 1, 0.0F, 0.0F);
                    sunderedCadaver2.absMoveTo(worldPosition.getX() - 1, worldPosition.getY(), worldPosition.getZ() - 1, 0.0F, 0.0F);

                    sunderedCadaver.finalizeSpawn((ServerLevel)level, level.getCurrentDifficultyAt(worldPosition), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                    sunderedCadaver2.finalizeSpawn((ServerLevel)level, level.getCurrentDifficultyAt(worldPosition), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);

                    level.addFreshEntity(sunderedCadaver);
                    level.addFreshEntity(sunderedCadaver2);

                    sunderedCadaver.setCustomName(new TranslatableComponent("entity.rigoranthusemortisreborn.summoned_servant").withStyle(Style.EMPTY.withItalic(true)));
                    sunderedCadaver2.setCustomName(new TranslatableComponent("entity.rigoranthusemortisreborn.summoned_servant").withStyle(Style.EMPTY.withItalic(true)));

                    sunderedCadaver.setCustomNameVisible(true);
                    sunderedCadaver2.setCustomNameVisible(true);
                    if ((Math.random() < 0.25)) {
                        NecrawFasciiEntity necrawFascii = ModEntities.NECRAW_FASCII.create(level);
                        necrawFascii.absMoveTo(worldPosition.getX() + 3, worldPosition.getY(), worldPosition.getZ() + 3, 0.0F, 0.0F);
                        necrawFascii.finalizeSpawn((ServerLevel)level, level.getCurrentDifficultyAt(worldPosition), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                        level.addFreshEntity(necrawFascii);
                        necrawFascii.setCustomName(new TranslatableComponent("entity.rigoranthusemortisreborn.summoned_servant").withStyle(Style.EMPTY.withItalic(true)));
                        necrawFascii.setCustomNameVisible(true);
                    }
                    if ((Math.random() < 0.25)) {
                        Zombie zombieEntity = EntityType.ZOMBIE.create(level);
                        Zombie zombieEntity2 = EntityType.ZOMBIE.create(level);
                        zombieEntity.absMoveTo(worldPosition.getX() - 5, worldPosition.getY(), worldPosition.getZ() - 5, 0.0F, 0.0F);
                        zombieEntity2.absMoveTo(worldPosition.getX() + 5, worldPosition.getY(), worldPosition.getZ() + 5, 0.0F, 0.0F);
                        zombieEntity.finalizeSpawn((ServerLevel)level, level.getCurrentDifficultyAt(worldPosition), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                        zombieEntity2.finalizeSpawn((ServerLevel)level, level.getCurrentDifficultyAt(worldPosition), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                        level.addFreshEntity(zombieEntity);
                        level.addFreshEntity(zombieEntity2);
                    }
                }
            progress -= 200;
            return;
        }
    }

    public BlockPos getBlockInArea(Block block, int range) {
        AtomicReference<BlockPos> posFound = new AtomicReference<>();
        BlockPos.betweenClosedStream(worldPosition.offset(5, -3, 5), worldPosition.offset(-5, 3, -5)).forEach(blockPos -> {
            blockPos = blockPos.immutable();
            if(posFound.get() == null && level.getBlockState(blockPos).getBlock() == block)
                posFound.set(blockPos);
        });
        return posFound.get();
    }
}