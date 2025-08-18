package net.ramixin.direfall;

import net.fabricmc.api.ModInitializer;
import net.minecraft.server.world.ServerWorld;
import org.slf4j.Logger;

public class Direfall implements ModInitializer {

    public static final String MOD_ID = "direfall";
    public static final String MOD_NAME = "Direfall";
    public static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MOD_NAME);

    @Override
    public void onInitialize() {
        LOGGER.info("initializing (1/1)");
    }

    public static Intensity getIntensity(ServerWorld world) {
        return LevelPropertiesDuck.get(world.getLevelProperties()).direfall$getIntensity();
    }

}
