package net.ramixin.direfall.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;
import net.ramixin.direfall.Direfall;
import net.ramixin.direfall.util.CreeperEntityDuck;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SpawnHelper.class)
public class SpawnHelperMixin {

    @WrapOperation(method = "createMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityType;create(Lnet/minecraft/world/World;Lnet/minecraft/entity/SpawnReason;)Lnet/minecraft/entity/Entity;"))
    private static <T extends Entity> T allowCreepersToSpawnCharged(EntityType<T> instance, World world, SpawnReason reason, Operation<T> original) {
        T entity = original.call(instance, world, reason);
        if(!(entity instanceof CreeperEntity creeper)) return entity;
        if(!(world instanceof ServerWorld serverWorld)) return entity;
        if(Direfall.isIntensityAbove(serverWorld, 1))
            CreeperEntityDuck.get(creeper).direfall$attemptToSetCharged(0.15f * (Direfall.getIntensity(serverWorld).ordinal() - 1));
        return entity;
    }


}
