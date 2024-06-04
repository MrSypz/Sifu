package sypztep.sifu.common.tag;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import sypztep.sifu.Sifu;

public class ModEnchantmentTags {
	public static final TagKey<Enchantment> UTILITY = TagKey.of(Registries.ENCHANTMENT.getKey(), Sifu.id("utility"));

}
