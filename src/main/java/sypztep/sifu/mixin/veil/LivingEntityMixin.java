package sypztep.sifu.mixin.veil;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import sypztep.sifu.common.init.ModEnchantments;
import sypztep.sifu.common.tag.ModEntityTypeTags;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@ModifyReturnValue(method = "getAttackDistanceScalingFactor", at = @At("RETURN"))
	private double veil(double original, Entity entity) {
		if (entity == null || !entity.getType().isIn(ModEntityTypeTags.VEIL_IMMUNE)) {
			int level = EnchantmentHelper.getEquipmentLevel(ModEnchantments.VEIL, (LivingEntity) (Object) this);
			if (level > 0) {
				return original / (level * 2);
			}
		}
		return original;
	}
}
