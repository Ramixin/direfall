package net.ramixin.direfall.util;

import net.minecraft.entity.boss.ServerBossBar;

public interface WardenEntityDuck {

    ServerBossBar direfall$getBossBar();

    static WardenEntityDuck get(Object wardenEntity) {
        return (WardenEntityDuck) wardenEntity;
    }
}
