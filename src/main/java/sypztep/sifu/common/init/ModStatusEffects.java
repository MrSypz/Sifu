package sypztep.sifu.common.init;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import sypztep.sifu.Sifu;
import sypztep.sifu.common.statuseffect.CooldownEffect;
import sypztep.sifu.common.statuseffect.MarkEffect;
import sypztep.sifu.common.statuseffect.StunEffect;

public class ModStatusEffects {
    public static RegistryEntry<StatusEffect> STUN;
    public static RegistryEntry<StatusEffect> STALWART;
    public static RegistryEntry<StatusEffect> STALWART_COOLDOWN;
    public static RegistryEntry<StatusEffect> CARVE;
    public static RegistryEntry<StatusEffect> THOUSANDNEEDLE;
    public static RegistryEntry<StatusEffect> WARDENRITE_STYGIA_COOLDOWN;


    public static void initEffects() {
        STUN = init("stun", new StunEffect());
        STALWART = init("stalwart", new CooldownEffect(StatusEffectCategory.BENEFICIAL)
                .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, Sifu.id("stalwart_speed"), 0.02D, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        STALWART_COOLDOWN = init("stalwart_cooldown", new CooldownEffect(StatusEffectCategory.NEUTRAL));
        CARVE = init("carve", new MarkEffect(StatusEffectCategory.HARMFUL)
                .addAttributeModifier(EntityAttributes.GENERIC_ARMOR, Sifu.id("carve_armor"), -0.08D, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        THOUSANDNEEDLE = init("thousandneedle", new CooldownEffect(StatusEffectCategory.NEUTRAL));
        WARDENRITE_STYGIA_COOLDOWN = init("wardenrite_stygia_cooldown", new CooldownEffect(StatusEffectCategory.NEUTRAL));
    }

    public static RegistryEntry<StatusEffect> init(String name, StatusEffect effect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Sifu.id(name), effect);
    }
}
