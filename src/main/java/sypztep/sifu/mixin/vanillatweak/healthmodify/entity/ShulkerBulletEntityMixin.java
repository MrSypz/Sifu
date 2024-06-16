package sypztep.sifu.mixin.vanillatweak.healthmodify.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.server.world.ServerWorld;
import sypztep.sifu.api.MobAttributeModify;

@Mixin(ShulkerBulletEntity.class)
public abstract class ShulkerBulletEntityMixin {

    @ModifyConstant(method = "onEntityHit", constant = @Constant(floatValue = 4.0f), require = 0)
    private float onEntityHitMixin(float original) {
        if (((ProjectileEntity) (Object) this).getWorld() instanceof ServerWorld) {
            return original * (float) MobAttributeModify.getDamageFactor((ProjectileEntity) (Object) this);
        }
        return original;
    }
}
