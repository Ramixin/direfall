package net.ramixin.direfall.util;

import net.ramixin.direfall.Intensity;

public interface LevelPropertiesDuck {

    Intensity direfall$getIntensity();

    void direfall$setIntensity(Intensity intensity);

    static LevelPropertiesDuck get(Object levelProperties) {
        return (LevelPropertiesDuck) levelProperties;
    }

}
