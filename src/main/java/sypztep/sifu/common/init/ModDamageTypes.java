package sypztep.sifu.common.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import sypztep.sifu.Sifu;

public class ModDamageTypes {
    public static final RegistryKey<DamageType> NEEDLE = createType("needle");
    public static RegistryKey<DamageType> createType(String name) {
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Sifu.id(name));
    }

    public static DamageSource create(World world, RegistryKey<DamageType> key, @Nullable Entity source, @Nullable Entity attacker) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key), source, attacker);
    }
}
