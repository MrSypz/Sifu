package sypztep.sifu.mixin.parry;

import java.util.List;

import net.minecraft.entity.effect.StatusEffectInstance;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import sypztep.sifu.common.init.ModEnchantments;
import sypztep.sifu.common.init.ModSoundEvents;
import sypztep.sifu.common.init.ModStatusEffects;
import sypztep.sifu.common.tag.ModDamageTags;
import sypztep.sifu.common.tag.ModEnchantmentTags;

@Mixin(value=LivingEntity.class, priority=999)
public abstract class LivingEntityMixin extends Entity {
    protected LivingEntityMixin (EntityType<?> type, World world) {
        super(type, world);
    }
    @Shadow
    protected ItemStack activeItemStack;
    @Shadow
    protected int itemUseTimeLeft;
    @Unique
    private boolean isParrying () {
        return EnchantmentHelper.getLevel(ModEnchantments.PARRY, this.activeItemStack) > 0;
    }
    // Shields take 5 ticks to become active, swords should be faster than that.
    @Inject(method="isBlocking", at=@At(value="INVOKE", target="Lnet/minecraft/item/Item;getMaxUseTime(Lnet/minecraft/item/ItemStack;)I", shift=At.Shift.BEFORE), cancellable=true)
    private void quickParry (CallbackInfoReturnable<Boolean> cir) {
        if (isParrying()) cir.setReturnValue(this.activeItemStack.getMaxUseTime() - this.itemUseTimeLeft >= 2);
    }

    @Inject(method="blockedByShield", at=@At("RETURN"), cancellable=true)
    private void preventParry (DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ() && isParrying()) {
            if (source.isIn(ModDamageTags.BYPASSES_PARRY)) cir.setReturnValue(false);
            else if (!source.isIndirect() && source.getAttacker() instanceof LivingEntity living && living.disablesShield()) cir.setReturnValue(false);
        }
    }

    @ModifyArg(method="handleStatus(B)V", at=@At(value="INVOKE", target="Lnet/minecraft/entity/LivingEntity;playSound(Lnet/minecraft/sound/SoundEvent;FF)V", ordinal=2))
    private SoundEvent playParrySound (SoundEvent soundEvent) {
        if (isParrying()) return ModSoundEvents.ENTITY_PARRY;
        return soundEvent;
    }

    @Inject(method="damage", at=@At("TAIL"))
    private void combatCallbacks (DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir, @Local(ordinal=0) boolean blocked) {
        if (blocked && isParrying() && (LivingEntity)(Object)this instanceof PlayerEntity player) {
            if (!source.isIndirect() && source.getAttacker() instanceof LivingEntity attacker) {
                int lvl = EnchantmentHelper.getLevel(ModEnchantments.PARRY,player.getActiveItem());
                if (this.distanceTo(attacker) <= 4.5f) {
                    attacker.addStatusEffect(new StatusEffectInstance(ModStatusEffects.STUN,40 + 20 * lvl,lvl,false,false));
                }
                this.activeItemStack.damage(1, player, LivingEntity.getSlotForHand(player.getActiveHand())); // Do not use posthit, it ONLY breaks the mainhand.
            }
            player.getItemCooldownManager().set(this.activeItemStack.getItem(), 2);
            player.spawnSweepAttackParticles();
            player.stopUsingItem();
        }
    }
}
