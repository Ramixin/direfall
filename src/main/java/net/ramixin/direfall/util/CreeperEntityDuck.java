package net.ramixin.direfall.util;

public interface CreeperEntityDuck {

    void direfall$attemptToSetCharged(float chance);

    static CreeperEntityDuck get(Object creeperEntity) {
        return (CreeperEntityDuck) creeperEntity;
    }
}
