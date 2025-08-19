package net.ramixin.direfall;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.ramixin.direfall.util.LevelPropertiesDuck;
import net.ramixin.direfall.util.LivingEntityDuck;
import org.slf4j.Logger;

public class Direfall implements ModInitializer {

    public static final String MOD_ID = "direfall";
    public static final String MOD_NAME = "Direfall";
    public static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MOD_NAME);

    public static final TagKey<EntityType<?>> IGNORES_INTENSITY_HEALTH = TagKey.of(RegistryKeys.ENTITY_TYPE, id("ignores_intensity_health"));

    @Override
    public void onInitialize() {
        LOGGER.info("initializing (1/1)");
        CommandRegistrationCallback.EVENT.register((commandDispatcher, commandRegistryAccess, registrationEnvironment) -> ModCommands.register(commandDispatcher.getRoot()));
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }

    public static Intensity getIntensity(ServerWorld world) {
        return LevelPropertiesDuck.get(world.getLevelProperties()).direfall$getIntensity();
    }

    public static boolean isIntensityAbove(LivingEntity entity, int ordinal) {
        return LivingEntityDuck.get(entity).direfall$getSpawnIntensity().ordinal() > ordinal;
    }

    public static boolean isIntensityAbove(ServerWorld world, int ordinal) {
        return getIntensity(world).ordinal() > ordinal;
    }

}
