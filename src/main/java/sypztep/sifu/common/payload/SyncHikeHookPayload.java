package sypztep.sifu.common.payload;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import sypztep.sifu.client.payload.AddWardenriteArmorParticlesPayload;

public record SyncHikeHookPayload(boolean isholdhikehook) implements CustomPayload {
    public static final Id<SyncHikeHookPayload> ID = CustomPayload.id("sync_hike_hook");
    public static final PacketCodec<PacketByteBuf, SyncHikeHookPayload> CODEC = PacketCodec.tuple(PacketCodecs.BOOL, SyncHikeHookPayload::isholdhikehook, SyncHikeHookPayload::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
    public static void send(boolean isholdhikehook) {
        ClientPlayNetworking.send(new SyncHikeHookPayload(isholdhikehook));
    }
    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<SyncHikeHookPayload> {
        @Override
        public void receive(SyncHikeHookPayload payload, ServerPlayNetworking.Context context) {
            if (context.player() != null)
                PlayerLookup.tracking(context.player()).forEach(foundPlayer -> {
                });
        }
    }
}
