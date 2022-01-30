package com.platinumg17.rigoranthusemortisreborn.canis.common.entity.serializers;

//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraft.network.datasync.IDataSerializer;
//import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.CanisStage;

//public class EvolutionSerializer implements IDataSerializer<CanisStage> {
//    @Override
//    public void write(FriendlyByteBuf buf, CanisStage value) {
//        buf.writeInt(value.getStage(CanisStage.Stage.CHORDATA.getStageNum()));
//        buf.writeInt(value.getStage(CanisStage.Stage.KYPHOS));
//        buf.writeInt(value.getStage(CanisStage.Stage.CAVALIER));
//        buf.writeInt(value.getStage(CanisStage.Stage.HOMINI));
//    }
//
//    @Override
//    public CanisStage read(FriendlyByteBuf buf) {
//        return new CanisStage(buf.readEnum(CanisStage.Stage.class));
//    }
//
//    @Override
//    public CanisStage copy(CanisStage value) {
//        return value.copy();
//    }
//}

