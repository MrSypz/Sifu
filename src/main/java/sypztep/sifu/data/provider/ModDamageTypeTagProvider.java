package sypztep.sifu.data.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.DamageTypeTags;
import sypztep.sifu.common.init.ModDamageTypes;
import sypztep.sifu.common.tag.ModDamageTags;

import java.util.concurrent.CompletableFuture;

public class ModDamageTypeTagProvider extends FabricTagProvider<DamageType> {
	public ModDamageTypeTagProvider(FabricDataOutput output) {
		super(output, RegistryKeys.DAMAGE_TYPE, CompletableFuture.supplyAsync(BuiltinRegistries::createWrapperLookup));
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
		getOrCreateTagBuilder(DamageTypeTags.BYPASSES_COOLDOWN)
				.addOptional(ModDamageTypes.NEEDLE);
		getOrCreateTagBuilder(DamageTypeTags.IS_PROJECTILE)
				.addOptional(ModDamageTypes.NEEDLE);
		getOrCreateTagBuilder(DamageTypeTags.NO_IMPACT)
				.addOptional(ModDamageTypes.NEEDLE);
		getOrCreateTagBuilder(DamageTypeTags.NO_KNOCKBACK)
				.addOptional(ModDamageTypes.NEEDLE);
		getOrCreateTagBuilder(ModDamageTags.BYPASSES_PARRY)
				.addOptional(DamageTypes.EXPLOSION);
	}
}
