package com.platinumg17.rigoranthusemortisreborn.magica.common.items;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.dominion.IDominionCap;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.item.ICasterTool;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.*;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.interfaces.ISpellCaster;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.magica.common.capability.CapabilityRegistry;
import com.platinumg17.rigoranthusemortisreborn.magica.common.entity.EntitySpellArrow;
import com.platinumg17.rigoranthusemortisreborn.magica.common.spell.augment.AugmentPierce;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.MagicItemsRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class SpellArrow extends ArrowItem {

    public AbstractSpellPart part;
    public int numParts;

    public SpellArrow(String registryName, AbstractAugment augment, int numParts) {
        super(MagicItemsRegistry.defaultItemProperties());
        setRegistryName(EmortisConstants.MOD_ID, registryName);
        this.part = augment;
        this.numParts = numParts;
    }

    public void modifySpell(Spell spell){
        for(int i = 0; i < numParts; i++){
            spell.recipe.add(part);
        }
    }

    @Override
    public AbstractArrow createArrow(Level world, ItemStack stack, LivingEntity shooter) {
        IDominionCap dominion = CapabilityRegistry.getDominion(shooter).orElse(null);
        if(dominion == null)
            return new Arrow(world, shooter);
        EntitySpellArrow spellArrow = new EntitySpellArrow(world, shooter);
        if(!(shooter instanceof Player) || !(( shooter).getMainHandItem().getItem() instanceof ICasterTool))
            return super.createArrow(world, stack, shooter);
        Player entity = (Player)shooter;
        ICasterTool caster = (ICasterTool) entity.getMainHandItem().getItem();
        ISpellCaster spellCaster = caster.getSpellCaster(entity.getMainHandItem());
        Spell spell = spellCaster.getSpell();
        modifySpell(spell);
        spell.setCost(spell.getCastingCost() - part.getDefaultDominionCost() * numParts);
        spellArrow.spellResolver = new SpellResolver(new SpellContext(spell, entity)).withSilent(true);
        spellArrow.pierceLeft = spell.getBuffsAtIndex(0, shooter, AugmentPierce.INSTANCE);
        return spellArrow;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(new TranslatableComponent("rigoranthusemortisreborn.spell_arrow.desc"));
        Spell spell = new Spell();
        for(int i = 0; i < numParts; i++){
            spell.recipe.add(part);
        }
        tooltip.add(new TextComponent(spell.getDisplayString()));
    }
}