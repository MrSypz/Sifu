package sypztep.sifu;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sypztep.sifu.api.event.MultiplyMovementSpeedEvent;
import sypztep.sifu.client.payload.AddCarveSoulParticlePayload;
import sypztep.sifu.client.payload.AddMoonStepParticlesPayload;
import sypztep.sifu.common.event.EnchantedChestplateAirMobilityEvent;
import sypztep.sifu.common.event.LightHandEvent;
import sypztep.sifu.common.event.LumberjackEvent;
import sypztep.sifu.common.init.*;
import sypztep.sifu.common.payload.CarveSoulPayload;
import sypztep.sifu.common.payload.DefendMeterPayload;
import sypztep.sifu.common.payload.MoonStepPayload;
import sypztep.sifu.common.reload.LightHandBaseBlockReloadListener;

public class Sifu implements ModInitializer {
    public static final String MODID = "sifu";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public static Identifier id (String path) {
        return new Identifier(MODID,path);
    }
    @Override
    public void onInitialize() {
        ModDataComponentTypes.init();
        ModEnchantments.init();
        ModStatusEffects.initEffects();
        ModSoundEvents.init();
        ModEntityTypes.init();
        ModLootableModify.init();
        ModItems.init();
        initPayloads();

        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new LightHandBaseBlockReloadListener());

        PlayerBlockBreakEvents.BEFORE.register(new LightHandEvent());
        PlayerBlockBreakEvents.BEFORE.register(new LumberjackEvent());
        MultiplyMovementSpeedEvent.EVENT.register(new EnchantedChestplateAirMobilityEvent());
    }
    private void initPayloads() {
        PayloadTypeRegistry.playS2C().register(AddCarveSoulParticlePayload.ID, AddCarveSoulParticlePayload.CODEC);
        PayloadTypeRegistry.playS2C().register(AddMoonStepParticlesPayload.ID, AddMoonStepParticlesPayload.CODEC);

        PayloadTypeRegistry.playC2S().register(CarveSoulPayload.ID, CarveSoulPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(MoonStepPayload.ID, MoonStepPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(DefendMeterPayload.ID, DefendMeterPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(CarveSoulPayload.ID, new CarveSoulPayload.Receiver());
        ServerPlayNetworking.registerGlobalReceiver(MoonStepPayload.ID, new MoonStepPayload.Receiver());
        ServerPlayNetworking.registerGlobalReceiver(DefendMeterPayload.ID, new DefendMeterPayload.Receiver());
    }
}
