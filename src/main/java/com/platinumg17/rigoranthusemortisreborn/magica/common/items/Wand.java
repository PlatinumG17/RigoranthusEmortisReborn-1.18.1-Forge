package com.platinumg17.rigoranthusemortisreborn.magica.common.items;

//import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
//import com.platinumg17.rigoranthusemortisreborn.api.apimagic.item.ICasterTool;
//import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.AbstractCastMethod;
//import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.AbstractSpellPart;
//import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.interfaces.ISpellCaster;
//import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.Spell;
//import com.platinumg17.rigoranthusemortisreborn.magica.client.renderer.item.WandRenderer;
//import com.platinumg17.rigoranthusemortisreborn.magica.common.lib.LibItemNames;
//import com.platinumg17.rigoranthusemortisreborn.magica.common.spell.augment.AugmentAccelerate;
//import com.platinumg17.rigoranthusemortisreborn.magica.common.spell.method.MethodProjectile;
//import com.platinumg17.rigoranthusemortisreborn.magica.common.util.PortUtil;
//import net.minecraft.world.item.TooltipFlag;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.InteractionResult;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.network.chat.Component;
//import net.minecraft.network.chat.TranslatableComponent;
//import net.minecraft.world.level.Level;
//import software.bernie.geckolib3.core.IAnimatable;
//import software.bernie.geckolib3.core.PlayState;
//import software.bernie.geckolib3.core.builder.AnimationBuilder;
//import software.bernie.geckolib3.core.controller.AnimationController;
//import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
//import software.bernie.geckolib3.core.manager.AnimationData;
//import software.bernie.geckolib3.core.manager.AnimationFactory;
//
//import javax.annotation.Nullable;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Wand extends ModItem  implements IAnimatable, ICasterTool {
//    public AnimationFactory factory = new AnimationFactory(this);
//    public Wand() {
//        super(new Item.Properties().stacksTo(1).tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP).setISTER(() -> WandRenderer::new), LibItemNames.WAND);
//    }
//
//    private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
//        event.getController().setAnimation(new AnimationBuilder().addAnimation("wand_gem_spin", true));
//        return PlayState.CONTINUE;
//    }
//
//    @Override
//    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
//        ItemStack stack = playerIn.getItemInHand(handIn);
//        ISpellCaster caster = getSpellCaster(stack);
//        return caster.castSpell(worldIn, playerIn, handIn, new TranslatableComponent("rigoranthusemortisreborn.wand.invalid"));
//    }
//
//    @Override
//    public void registerControllers(AnimationData data) {
//        data.addAnimationController(new AnimationController(this, "controller", 20, this::predicate));
//    }
//
//    @Override
//    public AnimationFactory getFactory()
//    {
//        return this.factory;
//    }
//
//    @Override
//    public boolean isScribedSpellValid(ISpellCaster caster, Player player, InteractionHand hand, ItemStack stack, Spell spell) {
//        return spell.recipe.stream().noneMatch(s -> s instanceof AbstractCastMethod);
//    }
//
//    @Override
//    public void sendInvalidMessage(Player player) {
//        PortUtil.sendMessageNoSpam(player, new TranslatableComponent("rigoranthusemortisreborn.wand.invalid"));
//    }
//
//    @Override
//    public boolean setSpell(ISpellCaster caster, Player player, InteractionHand hand, ItemStack stack, Spell spell) {
//        ArrayList<AbstractSpellPart> recipe = new ArrayList<>();
//        recipe.add(MethodProjectile.INSTANCE);
//        recipe.add(AugmentAccelerate.INSTANCE);
//        recipe.addAll(spell.recipe);
//        spell.recipe = recipe;
//        return ICasterTool.super.setSpell(caster, player, hand, stack, spell);
//    }
//
//    @Override
//    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
//        getInformation(stack, worldIn, tooltip2, flagIn);
//        super.appendHoverText(stack, worldIn, tooltip2, flagIn);
//    }
//}