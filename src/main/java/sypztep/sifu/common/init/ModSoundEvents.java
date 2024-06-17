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
	SoundEvent ENTITY_STYGIA = createSoundEvent("entity.stygia");
	SoundEvent ENTITY_HIKE = createSoundEvent("entity.hike");
	SoundEvent ENTITY_SHOT = createSoundEvent("entity.shot");
	SoundEvent ENTITY_FLYHOOK = createSoundEvent("entity.flyhook");
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
