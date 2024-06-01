package sypztep.sifu.mixin.effect.stun.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sypztep.sifu.common.init.ModStatusEffects;

@Mixin(Mouse.class)
public abstract class MouseMixin {
    @Inject(method = "onMouseButton", at = @At("HEAD"), cancellable = true)
    private void onMouseButton(long window, int button, int action, int mods, CallbackInfo callbackInfo) {
        if (MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().player.hasStatusEffect(ModStatusEffects.STUN) && !MinecraftClient.getInstance().isPaused()) {
            KeyBinding.unpressAll();
            callbackInfo.cancel();
        }
    }

    @Inject(method = "onMouseScroll", at = @At("HEAD"), cancellable = true)
    private void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo callbackInfo) {
        if (MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().player.hasStatusEffect(ModStatusEffects.STUN) && !MinecraftClient.getInstance().isPaused()) {
            KeyBinding.unpressAll();
            callbackInfo.cancel();
        }
    }
    @Inject(method = "updateMouse", at = @At("HEAD"),cancellable = true)
    private void updateMouse(double timeDelta, CallbackInfo ci) {
        if (MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().player.hasStatusEffect(ModStatusEffects.STUN) && !MinecraftClient.getInstance().isPaused()) {
            KeyBinding.unpressAll();
            ci.cancel();
        }
    }
}
