package sypztep.sifu.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import sypztep.sifu.common.util.EnchantmentUtiller;

public class NoSilkTouchEnchantment extends UtilityEnchantment {
    public NoSilkTouchEnchantment(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return super.canAccept(other) && other != Enchantments.SILK_TOUCH
                && EnchantmentUtiller.isEnchantmentDisallow(other);
    }
}
