package sypztep.sifu.common.enchantment;

import com.sypztep.common.api.EnchantmentAccesor;
import com.sypztep.common.api.EnchantmentItemAction;
import net.minecraft.enchantment.Enchantment;

public abstract class SpecialEnchantment extends Enchantment implements EnchantmentAccesor, EnchantmentItemAction {
    public SpecialEnchantment(Properties properties) {
        super(properties);
    }

    @Override
    public Enchantment getEnchantment() {
        return this;
    }
}
