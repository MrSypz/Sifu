package sypztep.sifu.common.init;

import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.world.WorldComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.world.WorldComponentInitializer;
import sypztep.sifu.Sifu;
import sypztep.sifu.common.component.world.LumberjackComponent;

public class ModWorldComponents implements WorldComponentInitializer {
	//axe
	public static final ComponentKey<LumberjackComponent> LUMBERJACK = ComponentRegistry.getOrCreate(Sifu.id("lumberjack"), LumberjackComponent.class);

	@Override
	public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
		registry.register(LUMBERJACK, LumberjackComponent::new);
	}
}
