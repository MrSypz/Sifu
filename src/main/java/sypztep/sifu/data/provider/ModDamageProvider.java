package sypztep.sifu.data.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.entity.damage.DamageScaling;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryWrapper;
import sypztep.sifu.common.init.ModDamageTypes;

import java.util.concurrent.CompletableFuture;

public class ModDamageProvider extends FabricDynamicRegistryProvider {
    public ModDamageProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        entries.add(ModDamageTypes.SHADOW_SHARD,new DamageType("shadowconsume", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER,0.1F));
        entries.add(ModDamageTypes.NEEDLE,new DamageType("needle", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER,0.1F));
    }

    @Override
    public String getName() {
        return "Damage";
    }
}
