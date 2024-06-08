package sypztep.sifu.common.init;

import com.mojang.serialization.Codec;
import net.minecraft.component.DataComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import sypztep.sifu.Sifu;

public class ModDataComponentTypes {
	public static final DataComponentType<Boolean> TOGGLEABLE_PASSIVE = new DataComponentType.Builder<Boolean>().codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL).build();
	public static final DataComponentType<Boolean> TOGGLEABLE_FAN = new DataComponentType.Builder<Boolean>().codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL).build();

	public static void init() {
		Registry.register(Registries.DATA_COMPONENT_TYPE, Sifu.id("toggleable_passive"), TOGGLEABLE_PASSIVE);
		Registry.register(Registries.DATA_COMPONENT_TYPE, Sifu.id("toggleable_fan"), TOGGLEABLE_FAN);
	}
}
