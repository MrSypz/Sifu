package sypztep.sifu.client.payload;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import sypztep.sifu.Sifu;
import sypztep.sifu.common.component.entity.MoonStepComponent;

public record AddMoonStepParticlesPayload(int entityId) implements CustomPayload {
	public static final Id<AddMoonStepParticlesPayload> ID = CustomPayload.id(Sifu.id("add_gale_particles").toString());
	public static final PacketCodec<PacketByteBuf, AddMoonStepParticlesPayload> CODEC = PacketCodec.tuple(PacketCodecs.VAR_INT, AddMoonStepParticlesPayload::entityId, AddMoonStepParticlesPayload::new);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}

	public static void send(ServerPlayerEntity player, int id) {
		ServerPlayNetworking.send(player, new AddMoonStepParticlesPayload(id));
	}

	public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<AddMoonStepParticlesPayload> {
		@Override
		public void receive(AddMoonStepParticlesPayload payload, ClientPlayNetworking.Context context) {
			Entity entity = context.player().getWorld().getEntityById(payload.entityId());
			if (entity != null) {
				MoonStepComponent.addMoonStepParticles(entity);
			}
		}
	}
}
