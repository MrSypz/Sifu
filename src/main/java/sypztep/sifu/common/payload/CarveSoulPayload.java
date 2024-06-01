package sypztep.sifu.common.payload;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import sypztep.sifu.Sifu;
import sypztep.sifu.client.payload.AddCarveSoulParticlePayload;

public record CarveSoulPayload(int amp) implements CustomPayload {
    public static final Id<CarveSoulPayload> ID = CustomPayload.id(Sifu.id("carvesoul").toString());
    public static final PacketCodec<PacketByteBuf, CarveSoulPayload> CODEC = PacketCodec.tuple(PacketCodecs.VAR_INT, CarveSoulPayload::amp, CarveSoulPayload::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
    public static void send(int amp) {
        ClientPlayNetworking.send(new CarveSoulPayload(amp));
    }
    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<CarveSoulPayload> {
        @Override
        public void receive(CarveSoulPayload payload, ServerPlayNetworking.Context context) {
            if (context.player() != null)
                PlayerLookup.tracking(context.player()).forEach(foundPlayer -> AddCarveSoulParticlePayload.send(foundPlayer, context.player().getId(), payload.amp()));
        }
    }
}
