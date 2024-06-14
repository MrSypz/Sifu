package sypztep.sifu.common.payload;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import sypztep.sifu.Sifu;
import sypztep.sifu.client.payload.AddWardenriteArmorParticlesPayload;

public record WardenriteArmorPayload() implements CustomPayload {
    public static final Id<WardenriteArmorPayload> ID = CustomPayload.id("wardenrite_armor");
    public static final PacketCodec<PacketByteBuf, WardenriteArmorPayload> CODEC = PacketCodec.unit(new WardenriteArmorPayload());
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
    public static void send() {
        ClientPlayNetworking.send(new WardenriteArmorPayload());
    }
    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<WardenriteArmorPayload> {
        @Override
        public void receive(WardenriteArmorPayload payload, ServerPlayNetworking.Context context) {
            if (context.player() != null)
                PlayerLookup.tracking(context.player()).forEach(foundPlayer -> AddWardenriteArmorParticlesPayload.send(foundPlayer, context.player().getId()));
        }
    }
}
