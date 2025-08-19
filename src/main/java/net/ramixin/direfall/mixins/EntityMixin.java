package net.ramixin.direfall.mixins;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.ramixin.direfall.util.WardenEntityDuck;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Entity.class)
public class EntityMixin {

    @WrapMethod(method = "setCustomName")
    private void changeWardenBossBarWithCustomName(Text name, Operation<Void> original) {
        original.call(name);
        if(name == null) return;
        if(((Object)this) instanceof WardenEntity warden) WardenEntityDuck.get(warden).direfall$getBossBar().setName(name);
    }

    @WrapMethod(method = "onStartedTrackingBy")
    private void addPlayersToWardenBossBarOnStartTracking(ServerPlayerEntity player, Operation<Void> original) {
        original.call(player);
        if(((Object)this) instanceof WardenEntity warden) WardenEntityDuck.get(warden).direfall$getBossBar().addPlayer(player);
    }

    @WrapMethod(method = "onStoppedTrackingBy")
    private void removePlayersFromWardenBossBarOnStopTracking(ServerPlayerEntity player, Operation<Void> original) {
        original.call(player);
        if(((Object)this) instanceof WardenEntity warden) WardenEntityDuck.get(warden).direfall$getBossBar().removePlayer(player);
    }

}
