package sypztep.sifu.mixin.veil.client;

import com.sypztep.common.util.EnchantmentUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sypztep.sifu.common.init.ModEnchantments;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity> {
	@Inject(method = "renderLabelIfPresent", at = @At("HEAD"), cancellable = true)
	private void veil(T entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float tickDelta, CallbackInfo ci) {
		if (MinecraftClient.getInstance().cameraEntity instanceof LivingEntity cameraEntity && entity instanceof LivingEntity living && !living.isGlowing() && EnchantmentUtil.hasEnchantment(ModEnchantments.VEIL, living)
				&& !EnchantmentUtil.hasEnchantment(ModEnchantments.PERCEPTION, cameraEntity) && !cameraEntity.canSee(living)) {
			ci.cancel();
		}
	}
}
