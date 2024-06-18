package sypztep.sifu.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.model.loading.v1.FabricBakedModelManager;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.resource.ResourceType;
import sypztep.sifu.Sifu;
import sypztep.sifu.client.payload.AddWardenriteArmorParticlesPayload;
import sypztep.sifu.client.render.entity.HookEntityRenderer;
import sypztep.sifu.client.render.entity.NeedleEntityRenderer;
import sypztep.sifu.client.render.entity.PortalizeEntityRenderer;
import sypztep.sifu.client.render.entity.ShadowShardsEntityRenderer;
import sypztep.sifu.client.render.item.CustomItemRenderer;
import sypztep.sifu.client.render.item.CustomRendererImlp;
import sypztep.sifu.client.render.model.ShadowShardModel;
import sypztep.sifu.client.sound.HookSoundInstance;
import sypztep.sifu.common.entity.projectile.HookEntity;
import sypztep.sifu.common.init.ModEntityTypes;
import sypztep.sifu.common.init.ModItems;

import java.util.List;

public class SifuClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        initPayloads();
        ModelLoadingPlugin.register(new CustomRendererImlp());
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
    }
}
