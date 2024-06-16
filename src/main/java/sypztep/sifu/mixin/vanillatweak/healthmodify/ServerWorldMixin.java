package sypztep.sifu.mixin.vanillatweak.healthmodify;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.server.world.ServerWorld;
import sypztep.sifu.api.MobAttributeModify;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {

    @Inject(method = "spawnEntity", at = @At("HEAD"))
    private void spawnEntityMixin(Entity entity, CallbackInfoReturnable<Boolean> info) {
        if (entity instanceof MobEntity) {
                MobAttributeModify.changeAttributes((MobEntity) entity, entity.getWorld());
        }
        if (entity instanceof PersistentProjectileEntity) {
            if (((PersistentProjectileEntity) entity).getOwner() != null && ((PersistentProjectileEntity) entity).getOwner() instanceof MobEntity)
                MobAttributeModify.changeOnlyDamageAttribute((MobEntity) ((PersistentProjectileEntity) entity).getOwner(), entity, false);
        }
    }
}
