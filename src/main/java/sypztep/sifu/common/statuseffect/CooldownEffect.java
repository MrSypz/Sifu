package sypztep.sifu.common.statuseffect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class CooldownEffect extends StatusEffect {
    public CooldownEffect(StatusEffectCategory category) {
        super(category, 0);
    }
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % 20 == 0;
    }
}
