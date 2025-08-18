package net.ramixin.direfall.mixins;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.ramixin.direfall.Direfall;
import net.ramixin.direfall.Intensity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends HostileEntity {

    @Shadow @Final private static TrackedData<Boolean> CHARGED;

    protected CreeperEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void allowCreepersToSpawnCharged(EntityType<?> entityType, World world, CallbackInfo ci) {
        if(!(getWorld() instanceof ServerWorld serverWorld)) return;
        Intensity intensity = Direfall.getIntensity(serverWorld);
        if(intensity.ordinal() <= 1) return;
        if(!getDataTracker().get(CHARGED))
            getDataTracker().set(CHARGED, this.random.nextFloat() < 0.2f * (intensity.ordinal() - 1));
    }

}
