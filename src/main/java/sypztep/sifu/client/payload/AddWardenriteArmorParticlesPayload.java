package sypztep.sifu.client.payload;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import sypztep.sifu.common.item.combat.impl.WardenriteArmor;

public record AddWardenriteArmorParticlesPayload(int entityId, int i) implements CustomPayload {
    public static final Id<AddWardenriteArmorParticlesPayload> ID = CustomPayload.id("add_wardenrite_armor_particles");
    public static final PacketCodec<PacketByteBuf, AddWardenriteArmorParticlesPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT,
            AddWardenriteArmorParticlesPayload::entityId,
			PacketCodecs.VAR_INT,
			AddWardenriteArmorParticlesPayload::i,
			AddWardenriteArmorParticlesPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void send(ServerPlayerEntity player, int id,int i) {
        ServerPlayNetworking.send(player, new AddWardenriteArmorParticlesPayload(id, i));
    }

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<AddWardenriteArmorParticlesPayload> {
        @Override
        public void receive(AddWardenriteArmorParticlesPayload payload, ClientPlayNetworking.Context context) {
            Entity entity = context.player().getWorld().getEntityById(payload.entityId());
            if (entity != null) {
				switch (payload.i()) {
					case 0: WardenriteArmor.addWardenriteStygia(entity); break;
					case 1: WardenriteArmor.addParticle(entity); break;
				}
            }
        }
    }
}
