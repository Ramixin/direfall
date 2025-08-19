package net.ramixin.direfall.mixins;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.ramixin.direfall.Direfall;
import net.ramixin.direfall.goals.BackAwayOrFleeGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSkeletonEntity.class)
public abstract class AbstractSkeletonEntityMixin extends HostileEntity {

    protected AbstractSkeletonEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals", at = @At("TAIL"))
    private void addPlayerEvasionGoal(CallbackInfo ci) {
        if(Direfall.isIntensityAbove(this, 0))
            this.goalSelector.add(1, new BackAwayOrFleeGoal<>(this, PlayerEntity.class, 6.0F, 1.0F, 1.2F));
    }

}
