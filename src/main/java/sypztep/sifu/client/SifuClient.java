package sypztep.sifu.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import sypztep.sifu.client.payload.AddWardenriteArmorParticlesPayload;
import sypztep.sifu.client.render.ShaderRenderer;
import sypztep.sifu.client.render.entity.NeedleEntityRenderer;
import sypztep.sifu.client.render.entity.ShadowShardsEntityRenderer;
import sypztep.sifu.client.render.model.ShadowShardModel;
import sypztep.sifu.common.init.ModEntityTypes;

public class SifuClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ShaderRenderer.init();
        initPayloads();
        EntityRendererRegistry.register(ModEntityTypes.NEEDLE, NeedleEntityRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.SHADOW_SHARDS, ShadowShardsEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ShadowShardModel.MODEL_LAYER, ShadowShardModel::getTexturedModelData);
    }

    private void initPayloads() {
        ClientPlayNetworking.registerGlobalReceiver(AddWardenriteArmorParticlesPayload.ID, new AddWardenriteArmorParticlesPayload.Receiver());
    }
}
