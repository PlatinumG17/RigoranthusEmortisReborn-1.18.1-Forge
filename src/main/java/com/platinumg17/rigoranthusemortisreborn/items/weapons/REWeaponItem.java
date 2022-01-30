package com.platinumg17.rigoranthusemortisreborn.items.weapons;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.platinumg17.rigoranthusemortisreborn.core.registry.effects.weapons.InventoryTickEffect;
import com.platinumg17.rigoranthusemortisreborn.core.registry.effects.weapons.OnHitEffect;
import com.platinumg17.rigoranthusemortisreborn.core.registry.effects.weapons.RightClickBlockEffect;
import com.platinumg17.rigoranthusemortisreborn.items.itemeffects.DestroyBlockEffect;
import com.platinumg17.rigoranthusemortisreborn.items.itemeffects.FinishUseItemEffect;
import com.platinumg17.rigoranthusemortisreborn.items.itemeffects.ItemRightClickEffect;
import com.platinumg17.rigoranthusemortisreborn.items.tooltypes.REToolTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class REWeaponItem extends TieredItem {
    private final float efficiency;
    private final boolean disableShield;
//    private static final HashMap<ToolType, Set<Material>> toolMaterials = new HashMap<>();

    @Nullable private final REToolTypes toolType;
    private final List<OnHitEffect> onHitEffects;
    @Nullable private final DestroyBlockEffect destroyBlockEffect;
    @Nullable private final RightClickBlockEffect rightClickBlockEffect;
    @Nullable private final ItemRightClickEffect itemRightClickEffect;
    private final int useDuration;
    private final UseAnim useAction;
    private final List<FinishUseItemEffect> itemUsageEffects;
    private final List<InventoryTickEffect> tickEffects;
    //Item attributes that are applied when the weapon is in the main hand.
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    @Deprecated
    public REWeaponItem(Tier tier, int attackDamage, float attackSpeed, float efficiency, @Nullable REToolTypes toolType, Properties properties) {
        this(new Builder(tier, attackDamage, attackSpeed).efficiency(efficiency).set(toolType), properties);
    }

    public REWeaponItem(Builder builder, Properties properties) {
        super(builder.tier, properties);
        toolType = builder.toolType;
        efficiency = builder.efficiency;
        disableShield = builder.disableShield;
        onHitEffects = ImmutableList.copyOf(builder.onHitEffects);
        destroyBlockEffect = builder.destroyBlockEffect;
        rightClickBlockEffect = builder.rightClickBlockEffect;
        itemRightClickEffect = builder.itemRightClickEffect;
        useDuration = builder.useDuration;
        useAction = builder.useAction;
        itemUsageEffects = ImmutableList.copyOf(builder.itemUsageEffects);
        tickEffects = ImmutableList.copyOf(builder.tickEffects);

        ImmutableMultimap.Builder<Attribute, AttributeModifier> modifiers = ImmutableMultimap.builder();
        modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double) builder.attackDamage + getTier().getAttackDamageBonus(), AttributeModifier.Operation.ADDITION));
        modifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", builder.attackSpeed, AttributeModifier.Operation.ADDITION));
        attributeModifiers = modifiers.build();
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
//        for(ToolType tool : getToolTypes(stack))
//            if(state.isToolEffective(tool))
//                return efficiency;
        if(toolType != null && toolType.canHarvest(state))
            return efficiency;
        return super.getDestroySpeed(stack, state);
    }

//    public boolean canAttackBlock(BlockState state, Level worldIn, BlockPos pos, Player player) {
//        ToolType blockTool = state.getHarvestTool();
//        Set<ToolType> itemTools = getToolTypes(new ItemStack(this));
//        if(blockTool != null && itemTools.contains(blockTool)) {
//            return true;
//        }
//        return !player.isCreative();
//    }

    @Override
    public boolean isCorrectToolForDrops(BlockState blockIn) {
//        ToolType blockTool = blockIn.getHarvestTool();
//        Set<ToolType> itemTools = getToolTypes(new ItemStack(this));
//        int blockHarvestLevel = blockIn.getHarvestLevel();
//        int toolHarvestLevel = getHarvestLevel(new ItemStack(this), /*blockTool,*/ null, blockIn);

//        if(blockTool != null && itemTools.contains(/*blockTool*/)) {return toolHarvestLevel >= blockHarvestLevel;}
//        else {   //  No specific harvestTool is specified, so any harvestTool efficiency is defined in the harvestTool itself. This also means that there's no harvestTool *level* specified, so any harvestTool of that class is sufficient.
            Material mat = blockIn.getMaterial();

            if(toolType != null) {
                if(toolType.getHarvestMaterials().contains(mat)/* && toolHarvestLevel >= blockHarvestLevel*/)
                    return true;
            }
            return super.isCorrectToolForDrops(blockIn);
//        }
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if(destroyBlockEffect != null)
            destroyBlockEffect.onDestroyBlock(stack, worldIn, state, pos, entityLiving);
        if(state.getDestroySpeed(worldIn, pos) != 0.0F) {
            int damage = 2;

//            if(getToolTypes(stack).contains(state.getHarvestTool()))
//                damage = 1;
            if(toolType != null) {
                if(toolType.getHarvestMaterials().contains(state.getMaterial()))
                    damage = 1;

            }
            stack.hurtAndBreak(damage, entityLiving, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        return true;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if(rightClickBlockEffect != null)
            return rightClickBlockEffect.onClick(context);
        else return super.useOn(context);
    }

    @Override public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {return disableShield;}

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if(itemRightClickEffect != null)
            return itemRightClickEffect.onRightClick(worldIn, playerIn, handIn);
        else return super.use(worldIn, playerIn, handIn);
    }

    @Override public int getUseDuration(ItemStack stack) {return useDuration;}
    @Override public UseAnim getUseAnimation(ItemStack stack) {return useAction;}

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        for(FinishUseItemEffect effect : itemUsageEffects)
            stack = effect.onItemUseFinish(stack, worldIn, entityLiving);
        return super.finishUsingItem(stack, worldIn, entityLiving);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        for(InventoryTickEffect effect : tickEffects)
            effect.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, (Player) -> Player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        onHitEffects.forEach(effect -> effect.onHit(stack, target, attacker));
        return true;
    }

//    @Override
//    public int getHarvestLevel(ItemStack stack, @Nullable Player player, @Nullable BlockState blockState) {
//        int harvestLevel = super.getHarvestLevel(stack, tool, player, blockState);
//        if(harvestLevel == -1 && getToolTypes(stack).contains(tool))
//            return getTier().getLevel();
//        return harvestLevel;
//    }

//    @Override
//    public Set<ToolType> getToolTypes(ItemStack stack) {
//        if(toolType == null)
//            return super.getToolTypes(stack);
//        else {
//            Set<ToolType> types = new HashSet<>();
//            types.addAll(toolType.getToolTypes());
//            types.addAll(super.getToolTypes(stack));
//            return types;
//        }
//    }
    @Override public boolean isEnchantable(ItemStack stack) {return canBeDepleted() || (toolType != null && !toolType.getEnchantments().isEmpty());}

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if(canBeDepleted() && enchantment.category == EnchantmentCategory.BREAKABLE)
            return true;
        if(toolType == null)
            return false;
        return toolType.getEnchantments().contains(enchantment);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return slot == EquipmentSlot.MAINHAND ? attributeModifiers : super.getAttributeModifiers(slot, stack);
    }
//    @Nullable public REToolTypes getToolType() {return toolType;}
    public float getEfficiency() {return efficiency;}
    public boolean getDisableShield() {return disableShield;}

    public static class Builder {
        private final Tier tier;
        private final int attackDamage;
        private final float attackSpeed;
        @Nullable private REToolTypes toolType;
        private float efficiency;
        private boolean disableShield;
        private final List<OnHitEffect> onHitEffects = new ArrayList<>();
        @Nullable private DestroyBlockEffect destroyBlockEffect = null;
        @Nullable private RightClickBlockEffect rightClickBlockEffect = null;
        @Nullable private ItemRightClickEffect itemRightClickEffect;
        private int useDuration = 0;
        private UseAnim useAction = UseAnim.NONE;
        private final List<FinishUseItemEffect> itemUsageEffects = new ArrayList<>();
        private final List<InventoryTickEffect> tickEffects = new ArrayList<>();

        public Builder(Tier tier, int attackDamage, float attackSpeed) {
            this.tier = tier;
            this.attackDamage = attackDamage;
            this.attackSpeed = attackSpeed;
            efficiency = tier.getSpeed();
        }

        public Builder set(@Nullable REToolTypes toolType) {
            this.toolType = toolType;
            return this;
        }

        public Builder set(DestroyBlockEffect effect) {
            if(rightClickBlockEffect != null)
                throw new IllegalStateException("Destroy block effect has already been set");
            destroyBlockEffect = effect;
            return this;
        }

        public Builder set(RightClickBlockEffect effect) {
            if(rightClickBlockEffect != null)
                throw new IllegalStateException("Right click block effect has already been set");
            rightClickBlockEffect = effect;
            return this;
        }

        public Builder set(ItemRightClickEffect effect) {
            if(itemRightClickEffect != null)
                throw new IllegalStateException("Item right click effect has already been set");
            itemRightClickEffect = effect;
            return this;
        }

        public Builder efficiency(float efficiency) {
            this.efficiency = efficiency;
            return this;
        }

        public Builder disableShield() {
            disableShield = true;
            return this;
        }

        public Builder add(OnHitEffect... effects) {
            onHitEffects.addAll(Arrays.asList(effects));
            return this;
        }

        public Builder add(InventoryTickEffect... effects) {
            tickEffects.addAll(Arrays.asList(effects));
            return this;
        }

        public Builder setEating(FinishUseItemEffect... effects) {
            return addItemUses(32, UseAnim.EAT, effects);
        }

        public Builder addItemUses(int duration, UseAnim action, FinishUseItemEffect... effects) {
            useDuration = duration;
            useAction = action;
            itemUsageEffects.addAll(Arrays.asList(effects));
            set(ItemRightClickEffect.ACTIVE_HAND);
            return this;
        }
    }
}