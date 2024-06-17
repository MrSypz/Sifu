package sypztep.sifu.common.payload;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import sypztep.sifu.client.payload.AddHookLandParticlesPayload;

public record HookLandPayload() implements CustomPayload {
    public static final Id<HookLandPayload> ID = CustomPayload.id("hookland");
    public static final PacketCodec<PacketByteBuf, HookLandPayload> CODEC = PacketCodec.unit(new HookLandPayload());
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
    public static void send() {
        ClientPlayNetworking.send(new HookLandPayload());
    }
    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<HookLandPayload> {
        @Override
        public void receive(HookLandPayload payload, ServerPlayNetworking.Context context) {
            if (context.player() != null)
                PlayerLookup.tracking(context.player()).forEach(foundPlayer -> AddHookLandParticlesPayload.send(foundPlayer, context.player().getId()));
        }
    }
}
