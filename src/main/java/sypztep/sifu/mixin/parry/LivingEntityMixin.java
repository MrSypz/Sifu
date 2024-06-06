package sypztep.sifu.mixin.parry;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sypztep.sifu.common.init.ModEnchantments;

@Mixin(value = LivingEntity.class, priority = 999)
public abstract class LivingEntityMixin extends Entity {
    @Shadow
    protected ItemStack activeItemStack;
    @Unique
    private DamageSource cachedSource;

    @Shadow public abstract boolean isUsingItem();

    @Shadow public abstract boolean blockedByShield(DamageSource source);
    @Unique
    private boolean mamy$appearBlocking = false;

    @Unique
    private boolean isParrying() {
        return EnchantmentHelper.getLevel(ModEnchantments.PARRY, this.activeItemStack) > 0;
    }
    @Inject(at = @At(value = "HEAD"), method = "isBlocking", cancellable = true)
    public void parry$fakeShieldBlocking(CallbackInfoReturnable<Boolean> cir) {
        if(isParrying()) {
            cir.setReturnValue(mamy$appearBlocking);
        }
    }
    @Inject(at = @At(value = "HEAD"), method = "damage")
    public void cacheDamageSource(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        this.cachedSource = source;
    }

    @ModifyVariable(method = "damage", at = @At("HEAD"), index = 2, argsOnly = true)
    private float parry$applySwordBlockProtection(float old) {
        mamy$appearBlocking = true;
        if (isParrying() && (LivingEntity) (Object) this instanceof PlayerEntity player) {
            int lvl = EnchantmentHelper.getLevel(ModEnchantments.PARRY, player.getActiveItem());
            if (this.isUsingItem() && this.blockedByShield(cachedSource)) {
                old /= lvl;
            }
            this.activeItemStack.damage(1, player, LivingEntity.getSlotForHand(player.getActiveHand())); // Damage item,
            player.spawnSweepAttackParticles();
            player.playSound(SoundEvents.ITEM_SHIELD_BLOCK);
        }
        mamy$appearBlocking = false;
        return old;
    }
    protected LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

}
