package sypztep.sifu.client.payload;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;

public record AddHookLandParticlesPayload(int entityId) implements CustomPayload {
    public static final Id<AddHookLandParticlesPayload> ID = CustomPayload.id("add_hookland_particles");
    public static final PacketCodec<PacketByteBuf, AddHookLandParticlesPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT,
            AddHookLandParticlesPayload::entityId,
			AddHookLandParticlesPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void send(ServerPlayerEntity player, int id) {
        ServerPlayNetworking.send(player, new AddHookLandParticlesPayload(id));
    }

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<AddHookLandParticlesPayload> {
        @Override
        public void receive(AddHookLandParticlesPayload payload, ClientPlayNetworking.Context context) {
            Entity entity = context.player().getWorld().getEntityById(payload.entityId());
            if (entity != null) {

            }
        }
    }
}
