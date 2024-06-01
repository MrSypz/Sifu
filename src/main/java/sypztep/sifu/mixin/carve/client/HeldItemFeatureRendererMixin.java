package sypztep.sifu.mixin.carve.client;

import com.sypztep.common.util.EnchantmentUtil;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sypztep.sifu.common.init.ModEnchantments;

@Mixin(HeldItemFeatureRenderer.class)
public abstract class HeldItemFeatureRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T,M> {
    protected HeldItemFeatureRendererMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Inject(method="renderItem", at=@At(value="INVOKE", target="Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", shift=At.Shift.BEFORE))
    private void flipBlade (LivingEntity entity, ItemStack stack, ModelTransformationMode transformationMode, Arm arm, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo info) {
        if (EnchantmentUtil.hasEnchantment(ModEnchantments.CARVE,stack)) {
            float tick = entity.getItemUseTime() * 4.5f;
            if (entity.isUsingItem() && entity.getActiveItem() == stack) {
                float armMultiplier = arm == Arm.RIGHT ? -1f : 1f;
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(armMultiplier * - 90 ));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(armMultiplier * -90 + tick));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(armMultiplier * - 90));
            }
        }
    }
}
