package sypztep.sifu.common.payload;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import sypztep.sifu.client.payload.AddWardenriteArmorParticlesPayload;

public record WardenriteArmorPayload(int i) implements CustomPayload {
    public static final Id<WardenriteArmorPayload> ID = CustomPayload.id("wardenrite_armor");
    public static final PacketCodec<PacketByteBuf, WardenriteArmorPayload> CODEC = PacketCodec.tuple(PacketCodecs.VAR_INT, WardenriteArmorPayload::i, WardenriteArmorPayload::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
    public static void send(int i) {
        ClientPlayNetworking.send(new WardenriteArmorPayload(i));
    }
    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<WardenriteArmorPayload> {
        @Override
        public void receive(WardenriteArmorPayload payload, ServerPlayNetworking.Context context) {
            if (context.player() != null)
                PlayerLookup.tracking(context.player()).forEach(foundPlayer -> AddWardenriteArmorParticlesPayload.send(foundPlayer, context.player().getId(),payload.i()));
        }
    }
}
