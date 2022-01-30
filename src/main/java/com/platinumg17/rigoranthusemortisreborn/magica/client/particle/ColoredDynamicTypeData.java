package com.platinumg17.rigoranthusemortisreborn.magica.client.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.platinumg17.rigoranthusemortisreborn.core.registry.fluid.particles.EmortisParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

public class ColoredDynamicTypeData implements ParticleOptions {
    private ParticleType<ColoredDynamicTypeData> type;
    public ParticleColor color;
    float scale;
    int age;

    public static final Codec<ColoredDynamicTypeData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.FLOAT.fieldOf("r").forGetter(d -> d.color.getRed()),
                    Codec.FLOAT.fieldOf("g").forGetter(d -> d.color.getGreen()),
                    Codec.FLOAT.fieldOf("b").forGetter(d -> d.color.getBlue()),
                    Codec.FLOAT.fieldOf("scale").forGetter(d -> d.scale),
                    Codec.INT.fieldOf("age").forGetter(d -> d.age)
            ).apply(instance, ColoredDynamicTypeData::new));

    @Override
    public ParticleType<?> getType() {
        return type;
    }

    static final Deserializer<ColoredDynamicTypeData> DESERIALIZER = new Deserializer<ColoredDynamicTypeData>() {
        @Override
        public ColoredDynamicTypeData fromCommand(ParticleType<ColoredDynamicTypeData> type, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            return new ColoredDynamicTypeData(type, ParticleColor.deserialize(reader.readString()), reader.readFloat(), reader.readInt());
        }
        @Override
        public ColoredDynamicTypeData fromNetwork(ParticleType<ColoredDynamicTypeData> type, FriendlyByteBuf buffer) {
            return new ColoredDynamicTypeData(type, ParticleColor.deserialize(buffer.readUtf()), buffer.readFloat(), buffer.readInt());
        }
    };

    public ColoredDynamicTypeData(float r, float g, float b, float scale, int age){
        this.type = EmortisParticleTypes.LINE_TYPE;
        this.color = new ParticleColor(r, g, b);
        this.scale = scale;
        this.age = age;
    }

    public ColoredDynamicTypeData(ParticleType<ColoredDynamicTypeData> particleTypeData, ParticleColor color, float scale, int age){
        this.type = particleTypeData;
        this.color = color;
        this.scale = scale;
        this.age = age;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeUtf(color.serialize());
        buffer.writeFloat(scale);
        buffer.writeInt(age);
    }

    @Override
    public String writeToString() {
        return type.getRegistryName().toString() + " " + color.serialize() + " " + scale + " " + age;
    }
}