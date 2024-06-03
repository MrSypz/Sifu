package sypztep.sifu.data.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalEnchantmentTags;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.RegistryWrapper;
import sypztep.sifu.common.init.ModEnchantments;

import java.util.concurrent.CompletableFuture;

public class ModEnchantmentTagProvider extends FabricTagProvider.EnchantmentTagProvider {
	public ModEnchantmentTagProvider(FabricDataOutput output) {
		super(output, CompletableFuture.supplyAsync(BuiltinRegistries::createWrapperLookup));
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
//		getOrCreateTagBuilder(ConventionalEnchantmentTags.ENTITY_AUXILIARY_MOVEMENT_ENHANCEMENTS)
//				.add(ModEnchantments.STRAFE)
//				.add(ModEnchantments.DASH)
//				.add(ModEnchantments.SLIDE)
//				.add(ModEnchantments.BUOY)
//				.add(ModEnchantments.GALE);
		getOrCreateTagBuilder(ConventionalEnchantmentTags.ENTITY_DEFENSE_ENHANCEMENTS)
				.add(ModEnchantments.VEIL);
//		getOrCreateTagBuilder(ConventionalEnchantmentTags.ENTITY_SPEED_ENHANCEMENTS)
//				.add(ModEnchantments.ADRENALINE)
//				.add(ModEnchantments.BUOY);
//		getOrCreateTagBuilder(ConventionalEnchantmentTags.INCREASE_ENTITY_DROPS)
//				.add(ModEnchantments.SCOOPING);
		getOrCreateTagBuilder(ConventionalEnchantmentTags.WEAPON_DAMAGE_ENHANCEMENTS)
				.add(ModEnchantments.CARVE)
				.add(ModEnchantments.PARRY);
	}
}
