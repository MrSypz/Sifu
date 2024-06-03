package sypztep.sifu.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import sypztep.sifu.data.provider.ModDamageTypeTagProvider;
import sypztep.sifu.data.provider.ModEnchantmentTagProvider;
import sypztep.sifu.data.provider.ModEntityTypeTagProvider;

public class ModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModDamageTypeTagProvider::new);
		pack.addProvider(ModEnchantmentTagProvider::new);
		pack.addProvider(ModEntityTypeTagProvider::new);
	}
}
