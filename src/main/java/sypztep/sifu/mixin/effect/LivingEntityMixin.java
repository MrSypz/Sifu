package sypztep.sifu.mixin.effect;

import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.SharedLibrary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sypztep.sifu.common.init.ModStatusEffects;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow
    public float prevHeadYaw;
    @Unique
    float stuckYaw = 0;
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Shadow
    public abstract void setHeadYaw(float headYaw);

    @Shadow
    public abstract void setSprinting(boolean sprinting);

    @Shadow
    public abstract boolean damage(DamageSource source, float amount);

    @Shadow public abstract boolean hasStatusEffect(RegistryEntry<StatusEffect> effect);

    @Shadow public abstract @Nullable StatusEffectInstance getStatusEffect(RegistryEntry<StatusEffect> effect);

    @Shadow public abstract float getHeadYaw();

    @Shadow public abstract boolean removeStatusEffect(RegistryEntry<StatusEffect> effect);

    @Inject(method = "canSee", at = @At("HEAD"), cancellable = true)
    public void canSee(Entity entity, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (this.hasStatusEffect(ModStatusEffects.STUN)) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo callbackInfo) {
        if (this.hasStatusEffect(ModStatusEffects.STUN)) {
            this.setPitch(90);
            this.prevPitch = 90;
            this.setHeadYaw(stuckYaw);
            this.prevHeadYaw = stuckYaw;
            this.setYaw(stuckYaw);
            this.setSneaking(false);
            this.setSprinting(false);
        } else {
            this.stuckYaw = this.getYaw();
        }
    }

    @ModifyVariable(method = "damage", at = @At("HEAD"), argsOnly = true)
    private float multiplyDamageForVulnerability(float amount) {
        if (this.hasStatusEffect(ModStatusEffects.STUN)) {
            return amount + (amount * (0.50f * (this.getStatusEffect(ModStatusEffects.STUN).getAmplifier() + 1)));
        }
        return amount;
    }
}