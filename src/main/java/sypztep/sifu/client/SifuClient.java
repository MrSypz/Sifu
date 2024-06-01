package sypztep.sifu.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import sypztep.sifu.client.payload.AddCarveSoulParticlePayload;

public class SifuClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        initPayloads();
    }
    private void initPayloads() {
        ClientPlayNetworking.registerGlobalReceiver(AddCarveSoulParticlePayload.ID, new AddCarveSoulParticlePayload.Receiver());
    }
}
