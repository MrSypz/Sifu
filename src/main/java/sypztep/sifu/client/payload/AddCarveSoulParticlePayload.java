package sypztep.sifu.client.payload;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import sypztep.sifu.Sifu;
import sypztep.sifu.common.enchantment.CarveEnchantment;

public record AddCarveSoulParticlePayload(int id,int amp) implements CustomPayload {
    public static final Id<AddCarveSoulParticlePayload> ID = CustomPayload.id(Sifu.id("add_carvesoul_particle").toString());
    public static final PacketCodec<PacketByteBuf, AddCarveSoulParticlePayload> CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT,
            AddCarveSoulParticlePayload::id,
            PacketCodecs.VAR_INT,
            AddCarveSoulParticlePayload::amp,
            AddCarveSoulParticlePayload::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void send(ServerPlayerEntity player,int id, int amp) {
        ServerPlayNetworking.send(player, new AddCarveSoulParticlePayload(id,amp));
    }

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<AddCarveSoulParticlePayload> {
        @Override
        public void receive(AddCarveSoulParticlePayload payload, ClientPlayNetworking.Context context) {
            Entity entity = context.player().getWorld().getEntityById(payload.id());
            if (entity != null)
                CarveEnchantment.carvesoulParticle(entity, payload.amp());
        }
    }
}
