package sypztep.sifu.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import sypztep.sifu.Sifu;
import sypztep.sifu.api.IGrapHook;
import sypztep.sifu.client.payload.AddWardenriteArmorParticlesPayload;
import sypztep.sifu.client.render.entity.HookEntityRenderer;
import sypztep.sifu.client.render.entity.NeedleEntityRenderer;
import sypztep.sifu.client.render.entity.PortalizeEntityRenderer;
import sypztep.sifu.client.render.entity.ShadowShardsEntityRenderer;
import sypztep.sifu.client.render.model.ShadowShardModel;
import sypztep.sifu.common.init.ModEntityTypes;
import sypztep.sifu.common.init.ModItems;
import sypztep.sifu.common.item.GraphookItem;

public class SifuClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        initPayloads();
        EntityRendererRegistry.register(ModEntityTypes.NEEDLE, NeedleEntityRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.SHADOW_SHARDS, ShadowShardsEntityRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.PORTALIZE, PortalizeEntityRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.HOOK, HookEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ShadowShardModel.MODEL_LAYER, ShadowShardModel::getTexturedModelData);
    }

    private void initPayloads() {
        ClientPlayNetworking.registerGlobalReceiver(AddWardenriteArmorParticlesPayload.ID, new AddWardenriteArmorParticlesPayload.Receiver());
    }
}
