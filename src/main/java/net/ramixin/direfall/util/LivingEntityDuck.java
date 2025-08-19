package net.ramixin.direfall.util;

import net.ramixin.direfall.Intensity;

public interface LivingEntityDuck {

    Intensity direfall$getSpawnIntensity();

    static LivingEntityDuck get(Object livingEntity) {
        return (LivingEntityDuck) livingEntity;
    }
}
