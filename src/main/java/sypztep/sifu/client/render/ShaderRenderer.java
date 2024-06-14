package sypztep.sifu.client.render;
import net.minecraft.client.MinecraftClient;
import org.ladysnake.satin.api.event.ShaderEffectRenderCallback;
import org.ladysnake.satin.api.managed.ManagedShaderEffect;
import org.ladysnake.satin.api.managed.ShaderEffectManager;
import sypztep.sifu.Sifu;
import sypztep.sifu.common.init.ModStatusEffects;

public class ShaderRenderer {
    private static final ManagedShaderEffect DASHWARP = ShaderEffectManager.getInstance().manage(Sifu.id("shaders/post/stun.json"));

    private static void setFrequency(float value) {
        DASHWARP.setUniformValue("InverseAmount", value);
    }

    public static void init() {
        ShaderEffectRenderCallback.EVENT.register(tickDelta -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player != null && client.player.hasStatusEffect(ModStatusEffects.STUN) && !client.player.isSpectator()) {
                setFrequency(Math.min(0.75f, client.player.getStatusEffect(ModStatusEffects.STUN).getDuration() * 0.01f));
                DASHWARP.render(tickDelta);
            }
        });
    }
}
