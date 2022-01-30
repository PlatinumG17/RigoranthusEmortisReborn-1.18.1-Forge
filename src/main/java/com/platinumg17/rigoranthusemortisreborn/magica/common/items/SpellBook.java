package com.platinumg17.rigoranthusemortisreborn.magica.common.items;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.item.ICasterTool;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.SpellCaster;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.SpellTier;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.interfaces.ISpellCaster;
import com.platinumg17.rigoranthusemortisreborn.magica.client.gui.GuiRadialMenu;
import com.platinumg17.rigoranthusemortisreborn.magica.client.renderer.item.SpellBookRenderer;
import com.platinumg17.rigoranthusemortisreborn.magica.common.capability.CapabilityRegistry;
import com.platinumg17.rigoranthusemortisreborn.magica.common.capability.IPlayerCap;
import com.platinumg17.rigoranthusemortisreborn.magica.common.capability.REPlayerDataCap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class SpellBook extends Item implements IAnimatable, ICasterTool {

    public SpellTier tier;

    public SpellBook(SpellTier tier){
        super(new Item.Properties().stacksTo(1).tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP));
        this.tier = tier;
    }

    public SpellBook(Properties properties, SpellTier tier) {
        super(properties);
        this.tier = tier;
    }

    @Override
    public boolean canBeDepleted() {
        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);

        CapabilityRegistry.getDominion(playerIn).ifPresent(iDominion -> {
            if(iDominion.getBookTier() < this.tier.value){
                iDominion.setBookTier(this.tier.value);
            }
            IPlayerCap cap = CapabilityRegistry.getPlayerDataCap(playerIn).orElse(new REPlayerDataCap());
            if(iDominion.getGlyphBonus() < cap.getKnownGlyphs().size()){
                iDominion.setGlyphBonus(cap.getKnownGlyphs().size());
            }
        });
        ISpellCaster caster = getSpellCaster(stack);

        return caster.castSpell(worldIn, playerIn, handIn, new TranslatableComponent("rigoranthusemortisreborn.invalid_spell"));
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, LevelReader world, BlockPos pos, Player player) {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(final ItemStack stack, @Nullable final Level world, final List<Component> tooltip, final TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
//        tooltip.add(new TranslatableComponent("rigoranthusemortisreborn.spell_book.select", KeyMapping.createNameSupplier(REKeyBindings.OPEN_RADIAL_HUD.getName()).get()));
//        tooltip.add(new TranslatableComponent("rigoranthusemortisreborn.spell_book.craft", KeyMapping.createNameSupplier(REKeyBindings.OPEN_BOOK.getName()).get()));
        tooltip.add(new TranslatableComponent("tooltip.rigoranthusemortisreborn.caster_level", getTier().value).setStyle(Style.EMPTY.withColor(ChatFormatting.BLUE)));
    }

    public SpellTier getTier() {
        return this.tier;
    }

    @Override
    public void registerControllers(AnimationData data) {}

    AnimationFactory factory = new AnimationFactory(this);

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @NotNull
    @Override
    public ISpellCaster getSpellCaster(ItemStack stack) {
        return new BookCaster(stack);
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IItemRenderProperties() {
            private final BlockEntityWithoutLevelRenderer renderer = new SpellBookRenderer();

            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                return renderer;
            }
        });
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onOpenBookMenuKeyPressed(ItemStack stack, Player player) {
//        GuiSpellBook.open(stack, ((SpellBook) stack.getItem()).getTier().value);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onRadialKeyPressed(ItemStack stack, Player player) {
//        Minecraft.getInstance().setScreen(new GuiRadialMenu(stack));
    }

    public static class BookCaster extends SpellCaster{

        public BookCaster(ItemStack stack){
            super(stack);
        }

        public BookCaster(CompoundTag tag){
            super(tag);
        }

        @Override
        public int getMaxSlots() {
            return 10;
        }
    }
}