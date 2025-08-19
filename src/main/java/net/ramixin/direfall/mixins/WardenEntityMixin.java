package net.ramixin.direfall.mixins;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.world.World;
import net.ramixin.direfall.util.WardenEntityDuck;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WardenEntity.class)
public abstract class WardenEntityMixin extends HostileEntity implements WardenEntityDuck {

    @Unique
    private ServerBossBar bossBar;

    protected WardenEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void addBossBar(EntityType<?> entityType, World world, CallbackInfo ci) {
        this.bossBar = (ServerBossBar)(new ServerBossBar(getDisplayName(), BossBar.Color.PURPLE, BossBar.Style.PROGRESS)).setDarkenSky(true);
    }

    @Inject(method = "readCustomData", at = @At("TAIL"))
    private void setBossBarNameOnReadIfCustom(ReadView view, CallbackInfo ci) {
        if(hasCustomName())
            this.bossBar.setName(getDisplayName());
    }

    @Override
    public ServerBossBar direfall$getBossBar() {
        return this.bossBar;
    }

    @Inject(method = "mobTick", at = @At("TAIL"))
    public void updateBossBarOnMobTick(ServerWorld world, CallbackInfo ci) {
        this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
    }
}
