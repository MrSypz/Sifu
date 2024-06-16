package sypztep.sifu.mixin.vanillatweak.healthmodify;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import sypztep.sifu.ModConfig;
import sypztep.sifu.api.MobAttributeModify;
import sypztep.sifu.mixin.util.DefaultAttributeRegistryAccess;

@Mixin(PassiveEntity.class)
public abstract class PassiveEntityMixin extends PathAwareEntity {

    public PassiveEntityMixin(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "onGrowUp", at = @At(value = "HEAD"))
    protected void onGrowUpMixin(CallbackInfo info) {
        if (!ModConfig.enableHealthModify)
            return;
        if (!this.getWorld().isClient() && getBreedingAge() == 0 && this.getWorld() instanceof ServerWorld
                && DefaultAttributeRegistryAccess.getRegistry().get(this.getType()) != null
                && Math.abs(DefaultAttributeRegistryAccess.getRegistry().get(this.getType()).getBaseValue(EntityAttributes.GENERIC_MAX_HEALTH)
                        - this.getAttributeBaseValue(EntityAttributes.GENERIC_MAX_HEALTH)) <= 0.0001D) {
            MobAttributeModify.changeAttributes(this, this.getWorld());
        }
    }

    @Shadow
    public int getBreedingAge() {
        return 0;
    }
}
