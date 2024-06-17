package sypztep.sifu.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import sypztep.sifu.client.payload.AddHookLandParticlesPayload;
import sypztep.sifu.client.payload.AddWardenriteArmorParticlesPayload;
import sypztep.sifu.client.render.entity.HookEntityRenderer;
import sypztep.sifu.client.render.entity.NeedleEntityRenderer;
import sypztep.sifu.client.render.entity.PortalizeEntityRenderer;
import sypztep.sifu.client.render.entity.ShadowShardsEntityRenderer;
import sypztep.sifu.client.render.model.ShadowShardModel;
import sypztep.sifu.client.sound.HookSoundInstance;
import sypztep.sifu.common.entity.projectile.HookEntity;
import sypztep.sifu.common.init.ModEntityTypes;

public class SifuClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        initPayloads();
        EntityRendererRegistry.register(ModEntityTypes.NEEDLE, NeedleEntityRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.SHADOW_SHARDS, ShadowShardsEntityRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.PORTALIZE, PortalizeEntityRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.HOOK, HookEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ShadowShardModel.MODEL_LAYER, ShadowShardModel::getTexturedModelData);

        ClientEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof HookEntity hookEntity) {
                MinecraftClient.getInstance().getSoundManager().playNextTick(new HookSoundInstance(hookEntity));
            }
        });
    }

    private void initPayloads() {
        ClientPlayNetworking.registerGlobalReceiver(AddWardenriteArmorParticlesPayload.ID, new AddWardenriteArmorParticlesPayload.Receiver());
        ClientPlayNetworking.registerGlobalReceiver(AddHookLandParticlesPayload.ID, new AddHookLandParticlesPayload.Receiver());
    }
}
