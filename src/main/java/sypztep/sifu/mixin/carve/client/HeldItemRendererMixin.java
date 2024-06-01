package sypztep.sifu.mixin.carve.client;

import com.sypztep.common.util.EnchantmentUtil;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sypztep.sifu.common.init.ModEnchantments;

@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {
    @Inject(method="renderFirstPersonItem", at=@At(value="INVOKE", target="Lnet/minecraft/client/util/math/MatrixStack;push()V", shift=At.Shift.AFTER))
    private void flipBlade (AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo info) {
        if (EnchantmentUtil.hasEnchantment(ModEnchantments.CARVE,item) && swingProgress == 0) {
            Arm arm = hand == Hand.MAIN_HAND ? player.getMainArm() : player.getMainArm().getOpposite();
            float sideMultiplier = arm == Arm.RIGHT ? -1f : 1f;
            if (player.isUsingItem()) {
                matrices.translate(-sideMultiplier * 0.08 + player.getItemUseTime() * 0.01,-0.28,-0.45);
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90));
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(4.5f * -player.getItemUseTime()));
            }

        }
    }
}
