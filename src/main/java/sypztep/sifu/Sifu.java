package sypztep.sifu;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import sypztep.sifu.client.payload.AddCarveSoulParticlePayload;
import sypztep.sifu.common.init.*;
import sypztep.sifu.common.payload.CarveSoulPayload;

import java.util.List;

public class Sifu implements ModInitializer {
    public static final String MODID = "sifu";
    public static Identifier id (String path) {
        return new Identifier(MODID,path);
    }
    @Override
    public void onInitialize() {
        ModEnchantments.init();
        ModStatusEffects.initEffects();
        ModSoundEvents.init();
        ModEntityTypes.init();
        ModLootableModify.init();
        initPayloads();
    }
    private void initPayloads() {
        PayloadTypeRegistry.playS2C().register(AddCarveSoulParticlePayload.ID, AddCarveSoulParticlePayload.CODEC);

        PayloadTypeRegistry.playC2S().register(CarveSoulPayload.ID, CarveSoulPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(CarveSoulPayload.ID, new CarveSoulPayload.Receiver());
    }
}
