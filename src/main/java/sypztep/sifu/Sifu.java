package sypztep.sifu;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sypztep.sifu.client.payload.AddHookLandParticlesPayload;
import sypztep.sifu.client.payload.AddWardenriteArmorParticlesPayload;
import sypztep.sifu.common.init.*;
import sypztep.sifu.common.payload.HookLandPayload;
import sypztep.sifu.common.payload.WardenriteArmorPayload;


public class Sifu implements ModInitializer {
    public static final String MODID = "sifu";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public static Identifier id(String path) {
        return Identifier.of(MODID, path);
    }

    @Override
    public void onInitialize() {
        ModStatusEffects.initEffects();
        ModSoundEvents.init();
        ModEntityTypes.init();
        ModLootableModify.init();
        ModItems.init();
        ModItemGroups.init();
        initPayloads();

    }

    private void initPayloads() {
        PayloadTypeRegistry.playS2C().register(AddWardenriteArmorParticlesPayload.ID, AddWardenriteArmorParticlesPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(AddHookLandParticlesPayload.ID, AddHookLandParticlesPayload.CODEC);

        PayloadTypeRegistry.playC2S().register(WardenriteArmorPayload.ID, WardenriteArmorPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(HookLandPayload.ID, HookLandPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(WardenriteArmorPayload.ID, new WardenriteArmorPayload.Receiver());
        ServerPlayNetworking.registerGlobalReceiver(HookLandPayload.ID, new HookLandPayload.Receiver());
    }
}
