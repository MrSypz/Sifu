package sypztep.sifu.common.init;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import sypztep.sifu.Sifu;
import sypztep.sifu.common.statuseffect.StunEffect;

public class ModStatusEffects {
    public static RegistryEntry<StatusEffect> STUN;
    public static void initEffects() {
        STUN = registerEffect("stun", new StunEffect());
    }
    public static RegistryEntry<StatusEffect> registerEffect(String name, StatusEffect effect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Sifu.id(name), effect);
    }

}
