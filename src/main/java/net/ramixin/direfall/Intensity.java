package net.ramixin.direfall;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringIdentifiable;

public enum Intensity implements StringIdentifiable {
    MORTAL("mortal"),
    CURSED("cursed"),
    TORMENTED("tormented"),
    DAMNED("damned")

    ;

    private final String id;

    Intensity(String id) {
        this.id = id;
    }

    @Override
    public String asString() {
        return id;
    }

    public static final Codec<Intensity> CODEC = StringIdentifiable.createCodec(Intensity::values);
}
