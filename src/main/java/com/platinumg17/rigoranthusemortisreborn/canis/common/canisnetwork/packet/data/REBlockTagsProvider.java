package com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class REBlockTagsProvider extends BlockTagsProvider {

    public REBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, EmortisConstants.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "RigoranthusEmortisReborn Block Tags";
    }

    @Override
    protected void addTags() {
    }
}