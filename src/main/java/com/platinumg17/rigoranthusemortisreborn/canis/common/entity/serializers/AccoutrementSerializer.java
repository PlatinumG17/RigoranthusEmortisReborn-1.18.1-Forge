package com.platinumg17.rigoranthusemortisreborn.canis.common.entity.serializers;

import net.minecraft.network.FriendlyByteBuf;
import com.platinumg17.rigoranthusemortisreborn.api.RigoranthusEmortisRebornAPI;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.Accoutrement;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.AccoutrementInstance;
import net.minecraft.network.syncher.EntityDataSerializer;

import java.util.ArrayList;
import java.util.List;

public class AccoutrementSerializer implements EntityDataSerializer<List<AccoutrementInstance>> {

    @Override
    public void write(FriendlyByteBuf buf, List<AccoutrementInstance> value) {
        buf.writeInt(value.size());
        for (AccoutrementInstance inst : value) {
            buf.writeRegistryIdUnsafe(RigoranthusEmortisRebornAPI.ACCOUTERMENTS, inst.getAccoutrement());
            inst.getAccoutrement().write(inst, buf);
        }
    }

    @Override
    public List<AccoutrementInstance> read(FriendlyByteBuf buf) {
        int size = buf.readInt();
        List<AccoutrementInstance> newInst = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Accoutrement type = buf.readRegistryIdUnsafe(RigoranthusEmortisRebornAPI.ACCOUTERMENTS);
            newInst.add(type.createInstance(buf));
        }
        return newInst;
    }

    @Override
    public List<AccoutrementInstance> copy(List<AccoutrementInstance> value) {
        List<AccoutrementInstance> newInst = new ArrayList<>(value.size());
        for (AccoutrementInstance inst : value) {
            newInst.add(inst.copy());
        }
        return newInst;
    }
}
