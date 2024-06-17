package sypztep.sifu.mixin.hikehook;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import sypztep.sifu.common.init.ModItems;
import sypztep.sifu.common.util.IHikeHook;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {
    @ModifyExpressionValue(method = "getArmPose" , at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private static boolean isHikeHookinhand(boolean original) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        IHikeHook hook = (IHikeHook) player;
        assert player != null;
        ItemStack itemStack = player.getMainHandStack();
        ItemStack itemStack2 = player.getOffHandStack();
        if (itemStack.isOf(ModItems.HIKEHOOK) && hook.getHikeHook() != null && itemStack2.isEmpty())
            return true;
        return original;
    }
    @ModifyExpressionValue(method = "getArmPose" , at = @At(value = "INVOKE", target = "Lnet/minecraft/item/CrossbowItem;isCharged(Lnet/minecraft/item/ItemStack;)Z"))
    private static boolean replace (boolean original) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        IHikeHook hook = (IHikeHook) player;
        assert player != null;
        ItemStack itemStack = player.getMainHandStack();
        ItemStack itemStack2 = player.getOffHandStack();
        if (itemStack.isOf(ModItems.HIKEHOOK)  && hook.getHikeHook() != null && itemStack2.isEmpty())
            return true;
        return original;
    }
}
