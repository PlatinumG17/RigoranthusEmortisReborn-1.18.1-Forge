package com.platinumg17.rigoranthusemortisreborn.magica.common.items;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.item.ICasterTool;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.*;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.interfaces.ISpellCaster;
import com.platinumg17.rigoranthusemortisreborn.magica.client.renderer.item.AdonisRenderer;
import com.platinumg17.rigoranthusemortisreborn.magica.common.entity.EntitySpellArrow;
import com.platinumg17.rigoranthusemortisreborn.magica.common.spell.augment.AugmentSplit;
import com.platinumg17.rigoranthusemortisreborn.magica.common.spell.method.MethodProjectile;
import com.platinumg17.rigoranthusemortisreborn.magica.common.util.PortUtil;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.MagicItemsRegistry;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.IItemRenderProperties;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Adonis extends BowItem implements IAnimatable, ICasterTool {

    public Adonis(Item.Properties properties){
        super(properties);
    }

    public Adonis() {
        super(MagicItemsRegistry.defaultItemProperties().stacksTo(1));
        setRegistryName("adonis");
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IItemRenderProperties() {
            private final BlockEntityWithoutLevelRenderer renderer = new AdonisRenderer();

            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                return renderer;
            }
        });
    }

    public boolean canPlayerCastSpell(ItemStack bow, Player playerentity){
        ISpellCaster caster = getSpellCaster(bow);
        return new SpellResolver(new SpellContext(caster.getSpell(), playerentity)).withSilent(true).canCast(playerentity);
    }

    public ItemStack findAmmo(Player playerEntity, ItemStack shootable) {
        if (!(shootable.getItem() instanceof ProjectileWeaponItem)) {
            return ItemStack.EMPTY;
        } else {
            Predicate<ItemStack> predicate = ((ProjectileWeaponItem)shootable.getItem()).getSupportedHeldProjectiles()
                    .and(i -> !(i.getItem() instanceof SpellArrow) || (i.getItem() instanceof SpellArrow && canPlayerCastSpell(shootable, playerEntity)));
            ItemStack itemstack = ProjectileWeaponItem.getHeldProjectile(playerEntity, predicate);
            if (!itemstack.isEmpty()) {
                return itemstack;
            } else {
                predicate = ((ProjectileWeaponItem)shootable.getItem()).getAllSupportedProjectiles().and(i -> !(i.getItem() instanceof SpellArrow) || (i.getItem() instanceof SpellArrow && canPlayerCastSpell(shootable, playerEntity)));

                for(int i = 0; i < playerEntity.inventory.getContainerSize(); ++i) {
                    ItemStack itemstack1 = playerEntity.inventory.getItem(i);
                    if (predicate.test(itemstack1)) {
                        return itemstack1;
                    }
                }
                return playerEntity.abilities.instabuild ? new ItemStack(Items.ARROW) : ItemStack.EMPTY;
            }
        }
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        ISpellCaster caster = getSpellCaster(playerIn.getItemInHand(handIn));
        boolean hasAmmo = !findAmmo(playerIn, itemstack).isEmpty();

        InteractionResultHolder<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, hasAmmo);
        if (ret != null) return ret;
        if(hasAmmo || (caster.getSpell() != null && new SpellResolver(new SpellContext(caster.getSpell(), playerIn)).withSilent(true).canCast(playerIn))){
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }
        if (!playerIn.abilities.instabuild && !hasAmmo) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    public EntitySpellArrow buildSpellArrow(Level worldIn, Player playerentity, ISpellCaster caster, boolean isSpellArrow){
        EntitySpellArrow spellArrow = new EntitySpellArrow(worldIn, playerentity);
        spellArrow.spellResolver = new SpellResolver(new SpellContext(caster.getSpell(), playerentity).withColors(caster.getColor())).withSilent(true);
        spellArrow.setColors(caster.getColor().r, caster.getColor().g, caster.getColor().b);
        if(isSpellArrow)
            spellArrow.setBaseDamage(0);
        return spellArrow;
    }

    @Override
    public void releaseUsing(ItemStack bowStack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
        //Copied from BowItem so we can spawn arrows in case there are no items.
        if (!(entityLiving instanceof Player))
            return;
        Player playerentity = (Player)entityLiving;
        boolean isInfinity = playerentity.abilities.instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, bowStack) > 0;
        ItemStack arrowStack = findAmmo(playerentity, bowStack);

        int useTime = this.getUseDuration(bowStack) - timeLeft;
        useTime = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(bowStack, worldIn, playerentity, useTime, !arrowStack.isEmpty() || isInfinity);
        if (useTime < 0) return;
        boolean canFire = false;
        if (!arrowStack.isEmpty() || isInfinity) {
            if (arrowStack.isEmpty()) {
                arrowStack = new ItemStack(Items.ARROW);
            }
            canFire = true;
        }
        ISpellCaster caster = getSpellCaster(bowStack);
        boolean isSpellArrow = false;
        if(arrowStack.isEmpty() && caster.getSpell() != null && new SpellResolver(new SpellContext(caster.getSpell(), playerentity)).canCast(playerentity)){
            canFire = true;
            isSpellArrow = true;
        }
        if(!canFire)
            return;
        float f = getPowerForTime(useTime);
        if (((double)f >= 0.1D) && canFire) {
            boolean isArrowInfinite = playerentity.abilities.instabuild || (arrowStack.getItem() instanceof ArrowItem && ((ArrowItem)arrowStack.getItem()).isInfinite(arrowStack, bowStack, playerentity));
            if (!worldIn.isClientSide) {
                ArrowItem arrowitem = (ArrowItem)(arrowStack.getItem() instanceof ArrowItem ? arrowStack.getItem() : Items.ARROW);
                AbstractArrow abstractarrowentity = arrowitem.createArrow(worldIn, arrowStack, playerentity);
                abstractarrowentity = customArrow(abstractarrowentity);

                List<AbstractArrow> arrows = new ArrayList<>();
                boolean didCastSpell = false;
                if(arrowitem == Items.ARROW && caster.getSpell() != null && new SpellResolver(new SpellContext(caster.getSpell(), playerentity)).withSilent(true).canCast(playerentity)){
                    abstractarrowentity = buildSpellArrow(worldIn, playerentity, caster, isSpellArrow);
                    new SpellResolver(new SpellContext(caster.getSpell(), playerentity)).expendDominion(playerentity);
                    didCastSpell = true;
                }else if(arrowitem instanceof SpellArrow){
                    if(caster.getSpell() == null || !(new SpellResolver(new SpellContext(caster.getSpell(), playerentity)).canCast(playerentity))){
                        return;
                    }else if(new SpellResolver(new SpellContext(caster.getSpell(), playerentity)).canCast(playerentity)){
                        new SpellResolver(new SpellContext(caster.getSpell(), playerentity)).expendDominion(playerentity);
                        didCastSpell = true;
                    }
                }
                arrows.add(abstractarrowentity);
                if(caster.getSpell() != null && caster.getSpell().isValid() && didCastSpell){
                    int numSplits = caster.getSpell().getBuffsAtIndex(0, playerentity, AugmentSplit.INSTANCE);
                    if(abstractarrowentity instanceof EntitySpellArrow){
                        numSplits = ((EntitySpellArrow) abstractarrowentity).spellResolver.spell.getBuffsAtIndex(0, playerentity, AugmentSplit.INSTANCE);
                    }
                    for(int i =1; i < numSplits + 1; i++){
                        Direction offset = playerentity.getDirection().getClockWise();
                        if(i%2==0) offset = offset.getOpposite();
                        // Alternate sides
                        BlockPos projPos = playerentity.blockPosition().relative(offset, i);
                        projPos = projPos.offset(0, 1.5, 0);
                        EntitySpellArrow spellArrow = buildSpellArrow(worldIn, playerentity, caster, isSpellArrow);
                        spellArrow.setPos(projPos.getX(), spellArrow.blockPosition().getY(), projPos.getZ());
                        arrows.add(spellArrow);
                    }
                }
                for(AbstractArrow arr : arrows){
                    arr.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot, 0.0F, f * 3.0F, 1.0F);
                    if (f >= 1.0F) {
                        arr.setCritArrow(true);
                    }
                    addArrow(arr, bowStack, arrowStack, isArrowInfinite, playerentity);
                }
            }
            worldIn.playSound(null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), SoundEvents.ARROW_SHOOT/*RigoranthusSoundRegistry.SHOOT_BOW.get()*/, SoundSource.PLAYERS, 1.0F, 1.0F / (playerentity.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
            if (!isArrowInfinite && !playerentity.abilities.instabuild) {
                arrowStack.shrink(1);
            }
        }
    }

    public void addArrow(AbstractArrow abstractarrowentity, ItemStack bowStack,ItemStack arrowStack, boolean isArrowInfinite, Player playerentity){
        int power = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, bowStack);
        if (power > 0) {
            abstractarrowentity.setBaseDamage(abstractarrowentity.getBaseDamage() + (double)power * 0.5D + 0.5D);
        }
        int punch = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, bowStack);
        if (punch > 0) {
            abstractarrowentity.setKnockback(punch);
        }
        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, bowStack) > 0) {
            abstractarrowentity.setSecondsOnFire(100);
        }
        if (isArrowInfinite || playerentity.abilities.instabuild && (arrowStack.getItem() == Items.SPECTRAL_ARROW || arrowStack.getItem() == Items.TIPPED_ARROW)) {
            abstractarrowentity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
        }
        playerentity.level.addFreshEntity(abstractarrowentity);
    }
    /**
     * Get the predicate to match ammunition when searching the player's inventory, not their main/offhand
     */
    public Predicate<ItemStack> getAllSupportedProjectiles() {return ARROW_ONLY.or(i -> i.getItem() instanceof SpellArrow).or(i -> i.getItem() instanceof BoneArrow);}
    @Override public void registerControllers(AnimationData data) {}
    @Override public AbstractArrow customArrow(AbstractArrow arrow) {
        return super.customArrow(arrow);
    }
    public AnimationFactory factory = new AnimationFactory(this);
    @Override public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
        getInformation(stack, worldIn, tooltip2, flagIn);
        super.appendHoverText(stack, worldIn, tooltip2, flagIn);
    }
    @Override
    public boolean isScribedSpellValid(ISpellCaster caster, Player player, InteractionHand hand, ItemStack stack, Spell spell) {
        return spell.recipe.stream().noneMatch(s -> s instanceof AbstractCastMethod);
    }
    @Override
    public void sendInvalidMessage(Player player) {
        PortUtil.sendMessageNoSpam(player, new TranslatableComponent("rigoranthusemortisreborn.bow.invalid"));
    }

    @Override
    public boolean setSpell(ISpellCaster caster, Player player, InteractionHand hand, ItemStack stack, Spell spell) {
        ArrayList<AbstractSpellPart> recipe = new ArrayList<>();
        recipe.add(MethodProjectile.INSTANCE);
        recipe.addAll(spell.recipe);
        spell.recipe = recipe;
        return ICasterTool.super.setSpell(caster, player, hand, stack, spell);
    }
    @Override public int getEnchantmentValue() { return super.getEnchantmentValue(); }
    @Override public boolean isEnchantable(ItemStack stack) { return true; }
    @Override public boolean isBookEnchantable(ItemStack stack, ItemStack book) { return true; }
    @Override public int getDefaultProjectileRange() { return 22; }
}