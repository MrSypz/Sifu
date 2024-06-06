package sypztep.sifu.common.payload;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import sypztep.sifu.Sifu;
import sypztep.sifu.common.init.ModEntityComponents;

public record DefendMeterPayload() implements CustomPayload {
	public static final Id<DefendMeterPayload> ID = CustomPayload.id(Sifu.id("dash").toString());
	public static final PacketCodec<PacketByteBuf, DefendMeterPayload> CODEC = PacketCodec.unit(new DefendMeterPayload());

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}

	public static void send() {
		ClientPlayNetworking.send(new DefendMeterPayload());
	}

	public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<DefendMeterPayload> {
		@Override
		public void receive(DefendMeterPayload payload, ServerPlayNetworking.Context context) {
			DefendMeterComponent component = ModEntityComponents.DEFEND_METER.get(context.player());
			component.handle();
		}
	}
}
