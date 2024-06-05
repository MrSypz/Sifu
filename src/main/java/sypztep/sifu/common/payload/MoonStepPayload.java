package sypztep.sifu.common.payload;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import sypztep.sifu.Sifu;
import sypztep.sifu.client.payload.AddMoonStepParticlesPayload;
import sypztep.sifu.common.component.entity.MoonStepComponent;
import sypztep.sifu.common.init.ModEntityComponents;

public record MoonStepPayload() implements CustomPayload {
	public static final Id<MoonStepPayload> ID = CustomPayload.id(Sifu.id("gale").toString());
	public static final PacketCodec<PacketByteBuf, MoonStepPayload> CODEC = PacketCodec.unit(new MoonStepPayload());

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}

	public static void send() {
		ClientPlayNetworking.send(new MoonStepPayload());
	}

	public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<MoonStepPayload> {
		@Override
		public void receive(MoonStepPayload payload, ServerPlayNetworking.Context context) {
			MoonStepComponent component = ModEntityComponents.MOONSTEP.get(context.player());
			if (component.hasMoonStep() && component.canUse()) {
				component.use();
				PlayerLookup.tracking(context.player()).forEach(foundPlayer -> AddMoonStepParticlesPayload.send(foundPlayer, context.player().getId()));
			}
		}
	}
}
