package sypztep.sifu.mixin.vanillatweak.crisptwing;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot var1);
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }
    @ModifyVariable(method = "modifyAppliedDamage", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getProtectionAmount(Ljava/lang/Iterable;Lnet/minecraft/entity/damage/DamageSource;)I"), argsOnly = true)
    private float crisptwing(float value, DamageSource source) {
        if (source.getSource() != null) {
            boolean isWearingElytra =  this.getEquippedStack(EquipmentSlot.BODY).isOf(Items.ELYTRA);
            if (isWearingElytra) {
                return value * 3.0F;
            }
        }
        return value;
    }
}
