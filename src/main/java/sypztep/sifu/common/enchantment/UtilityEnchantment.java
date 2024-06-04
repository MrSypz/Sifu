package sypztep.sifu.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import sypztep.sifu.common.util.EnchantmentUtiller;

public class UtilityEnchantment extends Enchantment {
    public UtilityEnchantment(Properties properties) {
        super(properties);
    }
    @Override
    protected boolean canAccept(Enchantment other) {
        return super.canAccept(other) &&
        EnchantmentUtiller.isEnchantmentDisallow(other);
    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return false;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }
}
