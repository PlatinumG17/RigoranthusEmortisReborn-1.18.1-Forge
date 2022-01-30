package com.platinumg17.rigoranthusemortisreborn.items.armor.armorsets;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.items.armor.models.DwellerThoraxModel;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;

import javax.annotation.Nullable;
import java.util.List;

import static com.platinumg17.rigoranthusemortisreborn.items.armor.RigoranthusArmorMaterial.DWELLER;

public class DwellerThoraxArmor extends ArmorItem {
//    private boolean previousEquip = false;

    public DwellerThoraxArmor(Properties properties) {
        super(DWELLER, EquipmentSlot.CHEST, properties);
    }

    public static Component newTip(String tip) {
        return new TranslatableComponent("tooltip.rigoranthusemortisreborn" + tip).setStyle(Style.EMPTY);
    }

    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tip, flagIn);
        if (Screen.hasShiftDown()) {
            tip.add(newTip(".dweller_thorax"));
            tip.add(newTip(".dweller_thorax2"));
            tip.add(newTip(".dweller_thorax3"));
            tip.add(newTip(".dweller_thorax4"));
        } else {
            tip.add(newTip(".hold_shift"));
        }
    }

    @Override public boolean isEnchantable(ItemStack stack) {
        return true;
    }
    @Override public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return true;
    }
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return RigoranthusEmortisReborn.MOD_ID + ":textures/models/armor/dweller_layer_1.png";
    }
    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(java.util.function.Consumer<net.minecraftforge.client.IItemRenderProperties> consumer) {

        consumer.accept(new IItemRenderProperties() {
            static DwellerThoraxModel model;

            @Override
            public DwellerThoraxModel getArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel _default) {
                if (model == null) model = new DwellerThoraxModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ClientProxy.DWELLER_THORAX_ARMOR_LAYER));
                float pticks = Minecraft.getInstance().getFrameTime();
                float f = Mth.rotLerp(pticks, entity.yBodyRotO, entity.yBodyRot);
                float f1 = Mth.rotLerp(pticks, entity.yHeadRotO, entity.yHeadRot);
                float netHeadYaw = f1 - f;
                float netHeadPitch = Mth.lerp(pticks, entity.xRotO, entity.getXRot());
                model.slot = slot;
                model.copyFromDefault(_default);
                model.setupAnim(entity, entity.animationPosition, entity.animationSpeed, entity.tickCount + pticks, netHeadYaw, netHeadPitch);
                return model;
            }
        });
    }
//    public void onArmorTick(ItemStack itemStack, Level world, Player player) {
//        if (Config.enableArmorSetBonuses.get()) {
//            ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
//            if (chest.getItem() == ItemInit.DWELLER_THORAX.get()) {
//                player.addEffect(new MobEffectInstance(ModPotions.DOMINION_REGEN_EFFECT, 5, 1));
//                this.previousEquip = true;
//            } else if (this.previousEquip) {
//                player.removeEffect(ModPotions.DOMINION_REGEN_EFFECT);
//                this.previousEquip = false;
//            }
//        }
//    }
}