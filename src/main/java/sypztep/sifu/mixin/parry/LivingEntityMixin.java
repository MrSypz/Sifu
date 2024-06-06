package sypztep.sifu.mixin.parry;

import com.llamalad7.mixinextras.sugar.Local;
import com.sypztep.common.util.EnchantmentUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sypztep.sifu.common.init.ModEnchantments;
import sypztep.sifu.common.init.ModSoundEvents;
import sypztep.sifu.common.init.ModStatusEffects;

@Mixin(value = LivingEntity.class, priority = 999)
public abstract class LivingEntityMixin extends Entity {
    @Shadow
    protected ItemStack activeItemStack;
    @Unique
    private DamageSource cachedSource;

    @Shadow public abstract boolean isUsingItem();

    @Shadow public abstract boolean blockedByShield(DamageSource source);

    @Shadow public abstract ItemStack getActiveItem();

    @Unique
    private boolean fakeBlocking = false;

    @Unique
    private boolean isParrying() {
        return EnchantmentHelper.getLevel(ModEnchantments.PARRY, this.activeItemStack) > 0;
    }
    @Inject(at = @At(value = "HEAD"), method = "isBlocking", cancellable = true)
    public void parry$fakeShieldBlocking(CallbackInfoReturnable<Boolean> cir) {
        if(isParrying()) {
            cir.setReturnValue(fakeBlocking);
        }
    }
    @Inject(at = @At(value = "HEAD"), method = "damage")
    public void cacheDamageSource(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        this.cachedSource = source;
    }
    @ModifyArg(method="handleStatus(B)V", at=@At(value="INVOKE", target="Lnet/minecraft/entity/LivingEntity;playSound(Lnet/minecraft/sound/SoundEvent;FF)V", ordinal=2))
    private SoundEvent RiposteSound (SoundEvent soundEvent) {
        if (isParrying()) return ModSoundEvents.ENTITY_PARRY;
        return soundEvent;
    }

    @ModifyVariable(method = "damage", at = @At("HEAD"), index = 2, argsOnly = true)
    private float parry$applySwordBlockProtection(float oldDamage, DamageSource source) {
        boolean hasRiposte = EnchantmentUtil.hasEnchantment(ModEnchantments.RIPOSTE, getActiveItem());
        fakeBlocking = true;
        if (isParrying() && ((LivingEntity) (Object) this) instanceof PlayerEntity player) {
            if (!source.isIndirect() && source.getAttacker() instanceof LivingEntity attacker) {
                int parryLevel = EnchantmentHelper.getLevel(ModEnchantments.PARRY, player.getActiveItem());
                if (this.isUsingItem() && this.blockedByShield(cachedSource) && !hasRiposte) oldDamage /= parryLevel;
                int lvl = EnchantmentHelper.getLevel(ModEnchantments.RIPOSTE, player.getActiveItem());
                if (this.distanceTo(attacker) <= 4.5f && lvl > 0) {
                    player.getItemCooldownManager().set(this.activeItemStack.getItem(), 2);
                    player.spawnSweepAttackParticles();
                    player.stopUsingItem();
                    attacker.addStatusEffect(new StatusEffectInstance(ModStatusEffects.STUN, 20 * lvl, lvl, true, true));
                }
                this.activeItemStack.damage(1, player, LivingEntity.getSlotForHand(player.getActiveHand())); // Damage item
            }
        }
        if (hasRiposte) {
            return 0;
        }
        fakeBlocking = false;
        return oldDamage;
    }
    protected LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }
}
