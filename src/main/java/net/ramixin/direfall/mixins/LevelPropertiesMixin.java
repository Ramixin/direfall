package net.ramixin.direfall.mixins;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.level.LevelInfo;
import net.minecraft.world.level.LevelProperties;
import net.ramixin.direfall.Intensity;
import net.ramixin.direfall.util.LevelPropertiesDuck;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelProperties.class)
public class LevelPropertiesMixin implements LevelPropertiesDuck {

    @Unique
    private Intensity intensity = Intensity.MORTAL;


    @SuppressWarnings("deprecation")
    @WrapMethod(method = "readProperties")
    private static <T> LevelProperties readWorldIntensity(Dynamic<T> dynamic, LevelInfo info, LevelProperties.SpecialProperty specialProperty, GeneratorOptions generatorOptions, Lifecycle lifecycle, Operation<LevelProperties> original) {
        LevelProperties properties = original.call(dynamic, info, specialProperty, generatorOptions, lifecycle);
        dynamic.get("direfall:intensity").read(Intensity.CODEC).ifSuccess(val -> LevelPropertiesDuck.get(properties).direfall$setIntensity(val));
        return properties;
    }

    @Inject(method = "updateProperties", at = @At("TAIL"))
    private void writeWorldIntensity(DynamicRegistryManager registryManager, NbtCompound levelNbt, NbtCompound playerNbt, CallbackInfo ci) {
        levelNbt.putString("direfall:intensity", LevelPropertiesDuck.get(this).direfall$getIntensity().asString());
    }

    @Override
    public Intensity direfall$getIntensity() {
        return intensity;
    }

    @Override
    public void direfall$setIntensity(Intensity intensity) {
        this.intensity = intensity;
    }
}
