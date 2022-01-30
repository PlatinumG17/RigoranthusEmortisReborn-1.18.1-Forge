package com.platinumg17.rigoranthusemortisreborn.core.events.other;

import com.platinumg17.rigoranthusemortisreborn.blocks.DecorativeOrStorageBlocks;
import com.platinumg17.rigoranthusemortisreborn.core.init.ItemInit;
import com.platinumg17.rigoranthusemortisreborn.entity.item.BoneArrowEntity;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.BlockRegistry;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.MagicItemsRegistry;
import com.teamabnormals.blueprint.core.util.DataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

import javax.annotation.Nonnull;

public class VanillaCompatRigoranthus {

    private static final DispenseItemBehavior BUCKET_DISPENSE_BEHAVIOR = new DefaultDispenseItemBehavior() {
        @Nonnull
        @Override
        public ItemStack execute(@Nonnull BlockSource source, @Nonnull ItemStack stack) {
            Level world = source.getLevel();
            BucketItem bucket = (BucketItem) stack.getItem();
            BlockPos pos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
            if (bucket.emptyContents(null, world, pos, null)) {
                bucket.checkExtraContent(null, world, stack, pos);
                return new ItemStack(Items.BUCKET);
            }
            return super.execute(source, stack);
        }
    };

    public static void registerDispenserBehaviors() {
        DispenserBlock.registerBehavior(ItemInit.BUCKET_OF_CADAVEROUS_ICHOR.get(), BUCKET_DISPENSE_BEHAVIOR);

        DispenserBlock.registerBehavior(MagicItemsRegistry.BONE_ARROW, new AbstractProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
                return new BoneArrowEntity(worldIn, position.x(), position.y(), position.z());
            }
        });
    }

    public static void registerCompostables() {
        // Compostable
        DataUtil.registerCompostable(BlockRegistry.AZULOREAL_LEAVES, 0.3F);
        DataUtil.registerCompostable(BlockRegistry.AZULOREAL_SAPLING, 0.3F);
        DataUtil.registerCompostable(DecorativeOrStorageBlocks.AZULOREAL_LEAF_CARPET.get(), 0.3F);

        DataUtil.registerCompostable(BlockRegistry.JESSIC_LEAVES, 0.3F);
        DataUtil.registerCompostable(BlockRegistry.JESSIC_SAPLING, 0.3F);
        DataUtil.registerCompostable(DecorativeOrStorageBlocks.JESSIC_LEAF_CARPET.get(), 0.3F);

        DataUtil.registerCompostable(BlockRegistry.AZULOREAL_ORCHID, 0.65F);
        DataUtil.registerCompostable(BlockRegistry.IRIDESCENT_SPROUTS, 0.65F);
        DataUtil.registerCompostable(BlockRegistry.LISIANTHUS, 0.65F);

        DataUtil.registerCompostable(BlockRegistry.SPECTABILIS_BUSH, 0.65F);
        DataUtil.registerCompostable(BlockRegistry.DOMINION_BERRY_BUSH, 0.65F);
    }
    public static void registerFlammables() {
        // Flammability
        DataUtil.registerFlammable(BlockRegistry.AZULOREAL_LEAVES, 30, 60);
        DataUtil.registerFlammable(BlockRegistry.AZULOREAL_LOG, 5, 5);
        DataUtil.registerFlammable(BlockRegistry.STRIPPED_AZULOREAL_LOG, 5, 5);
        DataUtil.registerFlammable(BlockRegistry.AZULOREAL_WOOD, 5, 5);
        DataUtil.registerFlammable(BlockRegistry.STRIPPED_AZULOREAL_WOOD, 5, 5);
        DataUtil.registerFlammable(DecorativeOrStorageBlocks.AZULOREAL_PLANKS.get(), 5, 20);
        DataUtil.registerFlammable(BlockRegistry.AZULOREAL_SLAB, 5, 20);
        DataUtil.registerFlammable(BlockRegistry.AZULOREAL_STAIRS, 5, 20);
        DataUtil.registerFlammable(BlockRegistry.AZULOREAL_FENCE, 5, 20);
        DataUtil.registerFlammable(BlockRegistry.AZULOREAL_FENCE_GATE, 5, 20);
        DataUtil.registerFlammable(DecorativeOrStorageBlocks.VERTICAL_AZULOREAL_PLANKS.get(), 5, 20);
        DataUtil.registerFlammable(DecorativeOrStorageBlocks.VERTICAL_AZULOREAL_SLAB.get(), 5, 20);
        DataUtil.registerFlammable(DecorativeOrStorageBlocks.AZULOREAL_POST.get(), 5, 20);
        DataUtil.registerFlammable(DecorativeOrStorageBlocks.STRIPPED_AZULOREAL_POST.get(), 5, 20);
        DataUtil.registerFlammable(DecorativeOrStorageBlocks.AZULOREAL_HEDGE.get(), 5, 20);
        DataUtil.registerFlammable(DecorativeOrStorageBlocks.AZULOREAL_BOOKSHELF.get(), 30, 20);

        DataUtil.registerFlammable(BlockRegistry.JESSIC_LEAVES, 30, 60);
        DataUtil.registerFlammable(BlockRegistry.JESSIC_LOG, 5, 5);
        DataUtil.registerFlammable(BlockRegistry.STRIPPED_JESSIC_LOG, 5, 5);
        DataUtil.registerFlammable(BlockRegistry.JESSIC_WOOD, 5, 5);
        DataUtil.registerFlammable(BlockRegistry.STRIPPED_JESSIC_WOOD, 5, 5);
        DataUtil.registerFlammable(DecorativeOrStorageBlocks.JESSIC_PLANKS.get(), 5, 20);
        DataUtil.registerFlammable(BlockRegistry.JESSIC_SLAB, 5, 20);
        DataUtil.registerFlammable(BlockRegistry.JESSIC_STAIRS, 5, 20);
        DataUtil.registerFlammable(BlockRegistry.JESSIC_FENCE, 5, 20);
        DataUtil.registerFlammable(BlockRegistry.JESSIC_FENCE_GATE, 5, 20);
        DataUtil.registerFlammable(DecorativeOrStorageBlocks.VERTICAL_JESSIC_PLANKS.get(), 5, 20);
        DataUtil.registerFlammable(DecorativeOrStorageBlocks.VERTICAL_JESSIC_SLAB.get(), 5, 20);
        DataUtil.registerFlammable(DecorativeOrStorageBlocks.JESSIC_POST.get(), 5, 20);
        DataUtil.registerFlammable(DecorativeOrStorageBlocks.STRIPPED_JESSIC_POST.get(), 5, 20);
        DataUtil.registerFlammable(DecorativeOrStorageBlocks.JESSIC_HEDGE.get(), 5, 20);
        DataUtil.registerFlammable(DecorativeOrStorageBlocks.JESSIC_BOOKSHELF.get(), 30, 20);
    }
}