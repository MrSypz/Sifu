package sypztep.sifu.mixin.hikehook;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import sypztep.sifu.common.util.IHikeHook;
import sypztep.sifu.common.entity.projectile.HookEntity;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements IHikeHook {
    @Unique
    public HookEntity hookEntity;
    @Shadow
    public abstract boolean hasNoDrag();

    @Shadow protected abstract void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition);

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Unique
    private double q;
    @Unique
    private float f;
    @Unique
    private Vec3d vec3d6;

    @ModifyVariable(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;hasNoDrag()Z", shift = At.Shift.AFTER), ordinal = 1, index = 10)
    private double captureDouble(double originalQ) {
        return q = originalQ;
    }

    @ModifyVariable(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;hasNoDrag()Z", shift = At.Shift.AFTER), ordinal = 1, index = 8)
    private float captureFloat(float originalF) {
        return f = originalF;
    }

    @ModifyVariable(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;hasNoDrag()Z"), ordinal = 1, index = 9)
    private Vec3d captureVec3d(Vec3d originalVec3d) {
        return vec3d6 = originalVec3d;
    }

    @ModifyExpressionValue(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;hasNoDrag()Z"))
    private boolean modifyVelocityExpression(boolean original) {
        if (this.hookEntity != null) {
            slipy(vec3d6, q, f);
        }
        return original;
    }


    @Unique
    protected void slipy(Vec3d vec3d, double d, float f) {
        if (this.hasNoDrag()) {
            this.setVelocity(vec3d.x, d, vec3d.z);
        } else {
            this.setVelocity(vec3d.x * (double) f, this instanceof Flutterer ? d * (double) f : d * 0.98, vec3d.z * (double) f);
        }
    }
    @Override
    public HookEntity getHikeHook() {
        return hookEntity;
    }
    @Override
    public void setHikeHook(HookEntity hookEntity) {
        this.hookEntity = hookEntity;
    }
}
