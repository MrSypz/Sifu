package sypztep.sifu.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import sypztep.sifu.client.event.HealthBarRenderEvent;
import sypztep.sifu.client.payload.AddCarveSoulParticlePayload;
import sypztep.sifu.client.render.ShaderRenderer;
import sypztep.sifu.client.render.entity.NeedleEntityRenderer;
import sypztep.sifu.common.init.ModEntityTypes;

public class SifuClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        initPayloads();
    }

    private void initPayloads() {
        ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> TooltipItem.onTooltipRender(stack,lines,context));
        EntityRendererRegistry.register(ModEntityTypes.NEEDLE, NeedleEntityRenderer::new);
        ShaderRenderer.init();
        HudRenderCallback.EVENT.register(new HealthBarRenderEvent());
        ClientPlayNetworking.registerGlobalReceiver(AddCarveSoulParticlePayload.ID, new AddCarveSoulParticlePayload.Receiver());
    }
}
