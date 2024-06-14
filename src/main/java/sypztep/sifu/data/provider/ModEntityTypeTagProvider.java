package sypztep.sifu.data.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalEntityTypeTags;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.RegistryWrapper;
import sypztep.sifu.common.tag.ModEntityTypeTags;

import java.util.concurrent.CompletableFuture;

public class ModEntityTypeTagProvider extends FabricTagProvider.EntityTypeTagProvider {
	public ModEntityTypeTagProvider(FabricDataOutput output) {
		super(output, CompletableFuture.supplyAsync(BuiltinRegistries::createWrapperLookup));
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
		getOrCreateTagBuilder(ModEntityTypeTags.MODIFY_EXTRA)
				.addOptionalTag(ConventionalEntityTypeTags.BOSSES)
				.add(EntityType.WARDEN)
				.add(EntityType.BLAZE)
				.add(EntityType.CREEPER)
				.add(EntityType.ELDER_GUARDIAN)
				.add(EntityType.ENDER_DRAGON)
				.add(EntityType.ENDERMITE)
				.add(EntityType.EVOKER)
				.add(EntityType.GHAST)
				.add(EntityType.GUARDIAN)
				.add(EntityType.HOGLIN)
				.add(EntityType.HUSK)
				.add(EntityType.IRON_GOLEM)
				.add(EntityType.ILLUSIONER)
				.add(EntityType.MAGMA_CUBE)
				.add(EntityType.PHANTOM)
				.add(EntityType.PIGLIN_BRUTE)
				.add(EntityType.PILLAGER)
				.add(EntityType.RAVAGER)
				.add(EntityType.SHULKER)
				.add(EntityType.SILVERFISH)
				.add(EntityType.SKELETON)
				.add(EntityType.SLIME)
				.add(EntityType.STRAY)
				.add(EntityType.VEX)
				.add(EntityType.VINDICATOR)
				.add(EntityType.WITCH)
				.add(EntityType.WITHER)
				.add(EntityType.WITHER_SKELETON)
				.add(EntityType.ZOGLIN)
				.add(EntityType.ZOMBIE)
				.add(EntityType.ZOMBIE_VILLAGER);
	}
}
