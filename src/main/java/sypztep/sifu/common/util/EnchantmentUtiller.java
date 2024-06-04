package sypztep.sifu.common.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import sypztep.sifu.common.tag.ModEnchantmentTags;

public class EnchantmentUtiller {
    public static boolean isEnchantmentDisallow(Enchantment enchantment) {
        RegistryEntry<Enchantment> entry = Registries.ENCHANTMENT.getEntry(enchantment);
        return !entry.isIn(ModEnchantmentTags.UTILITY);
    }
}
