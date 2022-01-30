package com.platinumg17.rigoranthusemortisreborn.magica;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = EmortisConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@OnlyIn(Dist.CLIENT)
public class TextureEvent {

    @SubscribeEvent
    public static void textEvent(TextureStitchEvent.Pre event){
        if(event.getAtlas().location().toString().equals("minecraft:textures/atlas/chest.png")) {
            ResourceLocation jessicNormal = RigoranthusEmortisReborn.rl("entity/chest/jessic/normal");
            ResourceLocation jessicNormalLeft = RigoranthusEmortisReborn.rl("entity/chest/jessic/normal_left");
            ResourceLocation jessicNormalRight = RigoranthusEmortisReborn.rl("entity/chest/jessic/normal_right");
            ResourceLocation jessicTrapped = RigoranthusEmortisReborn.rl("entity/chest/jessic/trapped");
            ResourceLocation jessicTrappedLeft = RigoranthusEmortisReborn.rl("entity/chest/jessic/trapped_left");
            ResourceLocation jessicTrappedRight = RigoranthusEmortisReborn.rl("entity/chest/jessic/trapped_right");
            ResourceLocation azulorealNormal = RigoranthusEmortisReborn.rl("entity/chest/azuloreal/normal");
            ResourceLocation azulorealNormalLeft = RigoranthusEmortisReborn.rl("entity/chest/azuloreal/normal_left");
            ResourceLocation azulorealNormalRight = RigoranthusEmortisReborn.rl("entity/chest/azuloreal/normal_right");
            ResourceLocation azulorealTrapped = RigoranthusEmortisReborn.rl("entity/chest/azuloreal/trapped");
            ResourceLocation azulorealTrappedLeft = RigoranthusEmortisReborn.rl("entity/chest/azuloreal/trapped_left");
            ResourceLocation azulorealTrappedRight = RigoranthusEmortisReborn.rl("entity/chest/azuloreal/trapped_right");
            event.addSprite(jessicNormal);
            event.addSprite(jessicNormalLeft);
            event.addSprite(jessicNormalRight);
            event.addSprite(jessicTrapped);
            event.addSprite(jessicTrappedLeft);
            event.addSprite(jessicTrappedRight);
            event.addSprite(azulorealNormal);
            event.addSprite(azulorealNormalLeft);
            event.addSprite(azulorealNormalRight);
            event.addSprite(azulorealTrapped);
            event.addSprite(azulorealTrappedLeft);
            event.addSprite(azulorealTrappedRight);
        }
        if(event.getAtlas().location().toString().equals("minecraft:textures/atlas/signs.png")) {
            ResourceLocation jessicSign = RigoranthusEmortisReborn.rl("entity/signs/jessic");
            ResourceLocation azulorealSign = RigoranthusEmortisReborn.rl("entity/signs/jessic");
            event.addSprite(jessicSign);
            event.addSprite(azulorealSign);
        }
    }
}
/*
        jei:textures/atlas/gui.png-atlas
        minecraft:textures/atlas/blocks.png-atlas
        minecraft:textures/atlas/signs.png-atlas
        minecraft:textures/atlas/banner_patterns.png-atlas
        minecraft:textures/atlas/shield_patterns.png-atlas
        minecraft:textures/atlas/chest.png-atlas
        minecraft:textures/atlas/beds.png-atlas
        minecraft:textures/atlas/particles.png-atlas
        minecraft:textures/atlas/paintings.png-atlas
        minecraft:textures/atlas/mob_effects.png-atlas
*/