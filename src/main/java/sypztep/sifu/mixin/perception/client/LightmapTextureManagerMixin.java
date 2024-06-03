package sypztep.sifu.mixin.perception.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import sypztep.sifu.common.init.ModEnchantments;

@Mixin(LightmapTextureManager.class)
public class LightmapTextureManagerMixin {
	@Shadow
	@Final
	private MinecraftClient client;

	@ModifyVariable(method = "update", at = @At("STORE"), ordinal = 6)
	private float perception(float value) {
		int level = EnchantmentHelper.getEquipmentLevel(ModEnchantments.PERCEPTION, client.player);
		if (level > 0) {
			float strength = 1;
			if (level == 1) {
				strength = 0.25F;
			}
			return Math.max(strength, value);
		}
		return value;
	}
}
