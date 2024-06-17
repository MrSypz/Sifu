package sypztep.sifu.mixin.hikehook;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntityMixin {
    public PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    @Inject(method = "tickMovement", at = @At("TAIL"))
    private void hookmovement(CallbackInfo ci) {
        // Insert your custom code here
        if (this.hookEntity != null && this.hookEntity.isInBlock()) {
            this.onLanding();
            if (this.isLogicalSideForUpdatingMovement()) {
                Vec3d vec3d = this.hookEntity.getPos().subtract(this.getEyePos());
                float g = this.hookEntity.getLength();
                double d = vec3d.length();
                if (d > (double)g) {
                    double e = d / (double)g * 0.1;
                    this.addVelocity(vec3d.multiply(1.0 / d).multiply(e, e * 1.1, e));
                }
            }
        }
    }
    @Override
    protected void slipy(Vec3d vec3d, double d, float f) {
        if (this.hookEntity != null && this.hookEntity.isInBlock() && !this.isOnGround()) {
            this.setVelocity(vec3d.x * (double)0.99f, d * (double)0.995f, vec3d.z * (double)0.99f);
        }
        super.slipy(vec3d, d, f);
    }
}
