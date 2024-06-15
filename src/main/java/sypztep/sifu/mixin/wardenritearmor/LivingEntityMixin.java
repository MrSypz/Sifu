package sypztep.sifu.mixin.wardenritearmor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sypztep.sifu.common.init.ModStatusEffects;
import sypztep.sifu.common.item.impl.WardenriteArmor;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }
    @Shadow
    public abstract void setStatusEffect(StatusEffectInstance effect, @Nullable Entity source);
    @Inject(at = @At("HEAD"), method = "damage")
    private void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity user = ((LivingEntity) ((Object) this));
        if (WardenriteArmor.getWardenritePiecesWorn(user) == 4) {
//				if (!this.hasStatusEffect(ModStatusEffects.WARDENRITE_STYGIA_COOLDOWN) && this.getWorld() instanceof ServerWorld)
//					((ServerWorld) this.getWorld()).spawnParticles(CombatEnchants.SHIELD_PARTICLE, this.getX(), this.getBodyY(0.5D), this.getZ(), 3, 0.3D, 0.3D, 0.3D, 0.0D);
            this.setStatusEffect(new StatusEffectInstance(ModStatusEffects.STYGIA_COOLDOWN, 200), null);
        }
    }
}
