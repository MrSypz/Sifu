package sypztep.sifu.data.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalEntityTypeTags;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EntityTypeTags;
import sypztep.sifu.common.tag.ModEntityTypeTags;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.util.Identifier.tryParse;

public class ModEntityTypeTagProvider extends FabricTagProvider.EntityTypeTagProvider {
	public ModEntityTypeTagProvider(FabricDataOutput output) {
		super(output, CompletableFuture.supplyAsync(BuiltinRegistries::createWrapperLookup));
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
		getOrCreateTagBuilder(ModEntityTypeTags.VEIL_IMMUNE)
				.addOptionalTag(ConventionalEntityTypeTags.BOSSES)
				.add(EntityType.ELDER_GUARDIAN)
				.add(EntityType.WARDEN);
	}
}
