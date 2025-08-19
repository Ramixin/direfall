package net.ramixin.direfall.mixins;

import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.UnmodifiableLevelProperties;
import net.ramixin.direfall.Intensity;
import net.ramixin.direfall.util.LevelPropertiesDuck;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(UnmodifiableLevelProperties.class)
public class UnmodifiableLevelPropertiesMixin implements LevelPropertiesDuck {
    @Shadow @Final private ServerWorldProperties worldProperties;

    @Override
    public Intensity direfall$getIntensity() {
        return LevelPropertiesDuck.get(worldProperties).direfall$getIntensity();
    }

    @Override
    public void direfall$setIntensity(Intensity intensity) {
        LevelPropertiesDuck.get(worldProperties).direfall$setIntensity(intensity);
    }
}
