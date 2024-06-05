package sypztep.sifu.common.init;

import net.minecraft.entity.LivingEntity;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;
import sypztep.sifu.Sifu;
import sypztep.sifu.common.component.entity.AirMobilityComponent;
import sypztep.sifu.common.component.entity.MoonStepComponent;

public class ModEntityComponents implements EntityComponentInitializer {

	public static final ComponentKey<AirMobilityComponent> AIR_MOBILITY = ComponentRegistry.getOrCreate(Sifu.id("air_mobility"), AirMobilityComponent.class);
	public static final ComponentKey<MoonStepComponent> MOONSTEP = ComponentRegistry.getOrCreate(Sifu.id("moonstep"), MoonStepComponent.class);


	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerFor(LivingEntity.class, AIR_MOBILITY, AirMobilityComponent::new);
		registry.registerForPlayers(MOONSTEP, MoonStepComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
	}
}
