package net.ramixin.direfall.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityAttributes.class)
public class EntityAttributesMixin {

    @WrapOperation(method = "<clinit>", at = @At(value = "NEW", target = "(Ljava/lang/String;DDD)Lnet/minecraft/entity/attribute/ClampedEntityAttribute;"))
    private static ClampedEntityAttribute modifyEntityAttributeInitParameters(String string, double fallback, double min, double max, Operation<ClampedEntityAttribute> original) {
        if(!string.equals("attribute.name.max_health")) return original.call(string, fallback, min, max);
        return original.call(string, fallback, min, Double.MAX_VALUE);
    }

}
