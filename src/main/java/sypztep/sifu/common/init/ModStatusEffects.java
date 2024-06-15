package sypztep.sifu.common.init;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import sypztep.sifu.Sifu;
import sypztep.sifu.common.statuseffect.CooldownEffect;

public class ModStatusEffects {
    public static RegistryEntry<StatusEffect> STYGIA_COOLDOWN;


    public static void initEffects() {
        STYGIA_COOLDOWN = init("stygia_cooldown", new CooldownEffect(StatusEffectCategory.NEUTRAL));
    }

    public static RegistryEntry<StatusEffect> init(String name, StatusEffect effect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Sifu.id(name), effect);
    }
}
