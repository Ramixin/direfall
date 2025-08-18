package net.ramixin.direfall.mixins;

import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.ai.goal.CreeperIgniteGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.server.world.ServerWorld;
import net.ramixin.direfall.Direfall;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.EnumSet;
import java.util.List;

@Mixin(CreeperIgniteGoal.class)
public class CreeperIgniteGoalMixin {

    @Shadow @Final private CreeperEntity creeper;

    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/CreeperIgniteGoal;setControls(Ljava/util/EnumSet;)V"))
    private void removeMovementControlFromGoal(CreeperIgniteGoal instance, EnumSet<? extends Enum<?>> enumSet, Operation<Void> original) {
        if(!(this.creeper.getWorld() instanceof ServerWorld serverWorld)) {
            original.call(instance, enumSet);
            return;
        }
        if(Direfall.getIntensity(serverWorld).ordinal() == 0) {
            original.call(instance, enumSet);
            return;
        }
        // CURSED or above
        @SuppressWarnings("rawtypes") List list = enumSet.stream().filter(enumeration -> enumeration != Goal.Control.MOVE).toList();
        if (!list.isEmpty())
            //noinspection unchecked
            original.call(instance, EnumSet.copyOf(list));
    }

    @WrapOperation(method = "start", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/pathing/EntityNavigation;stop()V"))
    private void allowCreepersToContinueMovingWhenIgniting(EntityNavigation instance, Operation<Void> original) {
        // prevent call
    }

    @Expression("9.0")
    @ModifyExpressionValue(method = "canStart", at = @At("MIXINEXTRAS:EXPRESSION"))
    private double increaseCreeperIgnitionRadius(double original) {
        if(!(this.creeper.getWorld() instanceof ServerWorld serverWorld)) return original;
        if(Direfall.getIntensity(serverWorld).ordinal() == 0) return original;
        return 25;
    }

    @Expression("49.0")
    @ModifyExpressionValue(method = "tick", at = @At("MIXINEXTRAS:EXPRESSION"))
    private double decreaseCreeperIgnitionCancelRadius(double original) {
        if(!(this.creeper.getWorld() instanceof ServerWorld serverWorld)) return original;
        if(Direfall.getIntensity(serverWorld).ordinal() == 0) return original;
        return 32;
    }

    @Inject(method = "start", at = @At("HEAD"))
    private void increaseCreeperMovementSpeedWhenPrimed(CallbackInfo ci) {
        if(!(this.creeper.getWorld() instanceof ServerWorld serverWorld)) return;
        if(Direfall.getIntensity(serverWorld).ordinal() > 0)
            this.creeper.getNavigation().setSpeed(1.15);
    }

    @Inject(method = "stop", at = @At("HEAD"))
    private void decreaseCreeperMovementSpeedWhenCancelled(CallbackInfo ci) {
        if(!(this.creeper.getWorld() instanceof ServerWorld serverWorld)) return;
        if(Direfall.getIntensity(serverWorld).ordinal() > 0)
            this.creeper.getNavigation().setSpeed(1);
    }

}
