package net.ramixin.direfall.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.ramixin.direfall.Direfall;
import net.ramixin.direfall.Intensity;
import net.ramixin.direfall.util.LivingEntityDuck;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements LivingEntityDuck {

    @Shadow public abstract AttributeContainer getAttributes();

    @Shadow public abstract void setHealth(float health);

    @Shadow public abstract float getMaxHealth();

    @SuppressWarnings("WrongEntityDataParameterClass")
    @Unique
    private static final TrackedData<String> SPAWN_INTENSITY = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.STRING);

    @Unique
    private static final Identifier INTENSITY_HEALTH_MODIFIER_ID = Direfall.id("intensity_health");

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void saveSpawnIntensityToDataTracker(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(SPAWN_INTENSITY, "mortal");
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void writeSpawnIntensityToEntity(EntityType<?> entityType, World world, CallbackInfo ci) {
        if(!(world instanceof ServerWorld serverWorld)) return;
        getDataTracker().set(SPAWN_INTENSITY, Direfall.getIntensity(serverWorld).asString());
    }

    @Override
    public Intensity direfall$getSpawnIntensity() {
        return Intensity.fromString(this.dataTracker.get(SPAWN_INTENSITY));
    }


    @Inject(method = "<init>", at = @At("TAIL"))
    public void increaseMobHealthBasedOnIntensity(EntityType<?> entityType, World world, CallbackInfo ci) {
        if(entityType.isIn(Direfall.IGNORES_INTENSITY_HEALTH)) return;
        if(Direfall.isIntensityAbove((LivingEntity)(Object)this, 0)) {
            EntityAttributeInstance attributeInstance = getAttributes().getCustomInstance(EntityAttributes.MAX_HEALTH);
            if(attributeInstance == null) return;
            if(attributeInstance.hasModifier(INTENSITY_HEALTH_MODIFIER_ID)) return;
            attributeInstance.addPersistentModifier(new EntityAttributeModifier(INTENSITY_HEALTH_MODIFIER_ID, direfall$getSpawnIntensity().ordinal() * 0.5, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE));
            setHealth(getMaxHealth());
        }
    }
}
