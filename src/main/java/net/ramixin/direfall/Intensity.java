package net.ramixin.direfall;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringIdentifiable;

public enum Intensity implements StringIdentifiable {
    MORTAL("mortal"),
    CURSED("cursed"),
    DAMNED("damned"),
    TORMENTED("tormented")

    ;

    private final String id;

    Intensity(String id) {
        this.id = id;
    }

    @Override
    public String asString() {
        return id;
    }

    public String getName() {
        return id.substring(0, 1).toUpperCase() + id.substring(1);
    }

    public static final Codec<Intensity> CODEC = StringIdentifiable.createCodec(Intensity::values);

    public static Intensity fromString(String name) {
        return switch(name) {
            case "mortal" -> MORTAL;
            case "cursed" -> CURSED;
            case "damned" -> DAMNED;
            case "tormented" -> TORMENTED;
            default -> throw new IllegalArgumentException("Unknown intensity: " + name);
        };
    }
}
