package sypztep.sifu.common.statuseffect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectCategory;
import sypztep.sifu.common.init.ModStatusEffects;

public class StunEffect extends CooldownEffect {
    public StunEffect() {
        super(StatusEffectCategory.HARMFUL);
    }

    @Override
    public void onEntityDamage(LivingEntity entity, int amplifier, DamageSource source, float amount) {
        entity.removeStatusEffect(ModStatusEffects.STUN);
    }
}
