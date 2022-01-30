package com.platinumg17.rigoranthusemortisreborn.canis.common.entity.serializers;

import com.platinumg17.rigoranthusemortisreborn.api.RigoranthusEmortisRebornAPI;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.SkillInstance;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import java.util.ArrayList;
import java.util.List;

public class SkillListSerializer implements EntityDataSerializer<List<SkillInstance>> {

    @Override
    public void write(FriendlyByteBuf buf, List<SkillInstance> value) {
        buf.writeInt(value.size());
        for (SkillInstance inst : value) {
            buf.writeRegistryIdUnsafe(RigoranthusEmortisRebornAPI.SKILLS, inst.getSkill());
            inst.writeToBuf(buf);
        }
    }

    @Override
    public List<SkillInstance> read(FriendlyByteBuf buf) {
        int size = buf.readInt();
        List<SkillInstance> newInst = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            SkillInstance inst = buf.readRegistryIdUnsafe(RigoranthusEmortisRebornAPI.SKILLS).getDefault();
            inst.readFromBuf(buf);
            newInst.add(inst);
        }
        return newInst;
    }

    @Override
    public EntityDataAccessor<List<SkillInstance>> createAccessor(int id) {
        return new EntityDataAccessor<>(id, this);
    }

    @Override
    public List<SkillInstance> copy(List<SkillInstance> value) {
        List<SkillInstance> newInst = new ArrayList<>(value.size());

        for (SkillInstance inst : value) {
        newInst.add(inst.copy());
        }
        return newInst;
    }
}