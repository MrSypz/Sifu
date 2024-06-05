package sypztep.sifu.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public interface MultiplyMovementSpeedEvent {
	Event<MultiplyMovementSpeedEvent> EVENT = EventFactory.createArrayBacked(MultiplyMovementSpeedEvent.class, listeners -> (currentMultiplier, world, living) -> {
		List<MultiplyMovementSpeedEvent> events = new ArrayList<>(Arrays.asList(listeners));
		events.sort(Comparator.comparingInt(MultiplyMovementSpeedEvent::getPriority));
		for (MultiplyMovementSpeedEvent event : events) {
			currentMultiplier = event.multiply(currentMultiplier, world, living);
		}
		return currentMultiplier;
	});

	float multiply(float currentMultiplier, World world, LivingEntity living);

	default int getPriority() {
		return 1000;
	}
}
