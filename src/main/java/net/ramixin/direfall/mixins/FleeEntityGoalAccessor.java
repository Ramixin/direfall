package net.ramixin.direfall.mixins;

import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FleeEntityGoal.class)
public interface FleeEntityGoalAccessor {

    @Accessor("withinRangePredicate")
    TargetPredicate getRangePredicate();

    @Accessor("fastSpeed")
    double getFastSpeed();
}
