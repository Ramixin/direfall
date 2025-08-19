package net.ramixin.direfall.mixins;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.dimension.NetherPortal;
import net.ramixin.direfall.Direfall;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetherPortal.class)
public class NetherPortalMixin {

    @Inject(method = "createPortal", at = @At("HEAD"), cancellable = true)
    private void preventPortalsUntilCursedIntensity(WorldAccess world, CallbackInfo ci) {
        if(!(world instanceof ServerWorld serverWorld)) return;
        if(Direfall.isIntensityAbove(serverWorld, 0)) return;
        ci.cancel();
    }

}
