package net.ramixin.direfall.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.ramixin.direfall.mixins.FleeEntityGoalAccessor;

import java.util.EnumSet;

public class BackAwayOrFleeGoal<T extends LivingEntity> extends FleeEntityGoal<T> {

    protected Path backAwayPath;

    public BackAwayOrFleeGoal(PathAwareEntity mob, Class<T> fleeFromType, float distance, double slowSpeed, double fastSpeed) {
        super(mob, fleeFromType, distance, slowSpeed, fastSpeed);
        this.setControls(EnumSet.noneOf(Control.class));
    }

    @Override
    public void start() {
        if(this.backAwayPath != null)
            this.fleeingEntityNavigation.startMovingAlong(this.fleePath, ((FleeEntityGoalAccessor)(this)).getFastSpeed());
        else
            super.start();
    }

    @Override
    public void stop() {
        this.backAwayPath = null;
        super.stop();
        this.setControls(EnumSet.noneOf(Control.class));
    }

    @Override
    public boolean canStart() {
        this.targetEntity = getServerWorld(this.mob).getClosestEntity(this.mob.getWorld().getEntitiesByClass(this.classToFleeFrom, this.mob.getBoundingBox().expand(this.fleeDistance, 3.0F, this.fleeDistance), (livingEntity) -> true), ((FleeEntityGoalAccessor)(this)).getRangePredicate(), this.mob, this.mob.getX(), this.mob.getY(), this.mob.getZ());
        if(this.targetEntity == null) return false;
        if(this.targetEntity.squaredDistanceTo(this.mob) < 12) {
            this.setControls(EnumSet.of(Control.MOVE));
            return super.canStart();
        }
        HitResult backAwayResult = this.mob.raycast(this.fleeDistance * this.fleeDistance, 1.0F, false);
        if(backAwayResult.squaredDistanceTo(this.mob) < 9) return super.canStart();
        Vec3d diff = backAwayResult.getPos().subtract(this.mob.getPos());
        Vec3d away = this.mob.getPos().subtract(diff);
        this.backAwayPath = this.mob.getNavigation().findPathTo(away.getX(), away.getY(), away.getZ(), 1);
        if(this.backAwayPath == null) return super.canStart();
        return true;
    }
}
