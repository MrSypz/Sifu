package sypztep.sifu.common.event;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import sypztep.sifu.ModConfig;
import sypztep.sifu.api.event.MultiplyMovementSpeedEvent;
import sypztep.sifu.common.component.entity.AirMobilityComponent;
import sypztep.sifu.common.init.ModEntityComponents;

public class EnchantedChestplateAirMobilityEvent implements MultiplyMovementSpeedEvent {
	@Override
	public float multiply(float currentMultiplier, World world, LivingEntity living) {
		if (ModConfig.enchantedChestplatesIncreaseAirMobility && !living.isOnGround()) {
			AirMobilityComponent airMobilityComponent = ModEntityComponents.AIR_MOBILITY.getNullable(living);
			if (airMobilityComponent != null && airMobilityComponent.getTicksInAir() > 10) {
				return currentMultiplier * 2;
			}
		}
		return currentMultiplier;
	}

	@Override
	public int getPriority() {
		return 1001;
	}
}
