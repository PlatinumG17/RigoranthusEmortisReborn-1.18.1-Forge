package com.platinumg17.rigoranthusemortisreborn.api.apicanis.entity;

import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.EnumGender;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class AbstractCanisEntity extends TamableAnimal implements ICanis, IAnimatable {

    protected AbstractCanisEntity(EntityType<? extends TamableAnimal> type, Level worldIn) {
        super(type, worldIn);
    }

    public void setAttributeModifier(Attribute attribute, UUID modifierUUID, BiFunction<AbstractCanisEntity, UUID, AttributeModifier> modifierGenerator) {
        AttributeInstance attributeInst = this.getAttribute(attribute);
        AttributeModifier currentModifier = attributeInst.getModifier(modifierUUID);
        if (currentModifier != null) {
            attributeInst.removeModifier(modifierUUID);
        }
        AttributeModifier newModifier = modifierGenerator.apply(this, modifierUUID);
        if (newModifier != null) {
            attributeInst.addTransientModifier(newModifier);
        }
    }
    public void removeAttributeModifier(Attribute attribute, UUID modifierUUID) {this.getAttribute(attribute).removeModifier(modifierUUID);}
    @Override public AbstractCanisEntity getCanis() {
        return this;
    }
    @Override public float getSoundVolume() {
        return super.getSoundVolume();
    }
    @Override public void spawnTamingParticles(boolean play) {
        super.spawnTamingParticles(play);
    }

    public void consumeItemFromStack(@Nullable Entity entity, ItemStack stack) {
        if (entity instanceof Player) {
            stack.shrink(1);
        } else {
            stack.shrink(1);
        }
    }
    public abstract TranslatableComponent getTranslationKey(Function<EnumGender, String> function);
    public TranslatableComponent getGenderPronoun() {return this.getTranslationKey(EnumGender::getUnlocalisedPronoun);}
    public TranslatableComponent getGenderSubject() {return this.getTranslationKey(EnumGender::getUnlocalisedSubject);}
    public TranslatableComponent getGenderTitle() {
        return this.getTranslationKey(EnumGender::getUnlocalisedTitle);
    }
    public TranslatableComponent getGenderTip() {
        return this.getTranslationKey(EnumGender::getUnlocalisedTip);
    }
    public TranslatableComponent getGenderName() {
        return this.getTranslationKey(EnumGender::getUnlocalisedName);
    }
}