package net.ramixin.direfall;

public interface LevelPropertiesDuck {

    Intensity direfall$getIntensity();

    void direfall$setIntensity(Intensity intensity);

    static LevelPropertiesDuck get(Object levelProperties) {
        return (LevelPropertiesDuck) levelProperties;
    }

}
