package sypztep.sifu.common.init;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import sypztep.sifu.Sifu;

import java.util.LinkedHashMap;
import java.util.Map;

public interface ModSoundEvents {
	Map<SoundEvent, Identifier> SOUND_EVENTS = new LinkedHashMap<>();
	//Sound
	SoundEvent ITEM_CARVE = createSoundEvent("item.carve");
	SoundEvent ITEM_STALWART = createSoundEvent("item.stalwart");
	SoundEvent ENTITY_PARRY = createSoundEvent("entity.parry");
	SoundEvent ENTITY_GENERIC_WARDENSPINE = createSoundEvent("entity.generic.wardenspine");


	static void init() {
		SOUND_EVENTS.keySet().forEach((soundEvent) -> {
			Registry.register(Registries.SOUND_EVENT, SOUND_EVENTS.get(soundEvent), soundEvent);
		});
	}
	private static SoundEvent createSoundEvent(String path) {
		SoundEvent soundEvent = SoundEvent.of(Sifu.id(path));
		SOUND_EVENTS.put(soundEvent, Sifu.id(path));
		return soundEvent;
	}
}
