package sypztep.sifu.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import sypztep.sifu.client.event.EnchantedChestplatesIncreaseAirMobilityEvent;
import sypztep.sifu.client.event.HealthBarRenderEvent;
import sypztep.sifu.client.event.MoonStepRenderEvent;
import sypztep.sifu.client.payload.AddCarveSoulParticlePayload;
import sypztep.sifu.client.payload.AddMoonStepParticlesPayload;
import sypztep.sifu.client.render.ShaderRenderer;
import sypztep.sifu.client.render.entity.NeedleEntityRenderer;
import sypztep.sifu.client.render.entity.ShadowShardsEntityRenderer;
import sypztep.sifu.client.render.model.ShadowShardModel;
import sypztep.sifu.common.init.ModEntityTypes;

public class SifuClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> TooltipItem.onTooltipRender(stack,lines,context));
        ShaderRenderer.init();
        initPayloads();
        HudRenderCallback.EVENT.register(new HealthBarRenderEvent());
        HudRenderCallback.EVENT.register(new MoonStepRenderEvent());
        ItemTooltipCallback.EVENT.register(new EnchantedChestplatesIncreaseAirMobilityEvent());
        EntityRendererRegistry.register(ModEntityTypes.NEEDLE, NeedleEntityRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.SHADOW_SHARDS, ShadowShardsEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ShadowShardModel.MODEL_LAYER, ShadowShardModel::getTexturedModelData);

    }

    private void initPayloads() {
        ClientPlayNetworking.registerGlobalReceiver(AddCarveSoulParticlePayload.ID, new AddCarveSoulParticlePayload.Receiver());
        ClientPlayNetworking.registerGlobalReceiver(AddMoonStepParticlesPayload.ID, new AddMoonStepParticlesPayload.Receiver());
    }
}
