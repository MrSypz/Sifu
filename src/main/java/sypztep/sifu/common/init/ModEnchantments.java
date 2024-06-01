package sypztep.sifu.common.init;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import sypztep.sifu.Sifu;
import sypztep.sifu.common.enchantment.CarveEnchantment;
import sypztep.sifu.common.enchantment.ParryEnchantment;

public class ModEnchantments {
    public static final Enchantment PARRY = new ParryEnchantment(properties(ItemTags.SHARP_WEAPON_ENCHANTABLE,3,EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND));
    public static final Enchantment CARVE = new CarveEnchantment(properties(ItemTags.SHARP_WEAPON_ENCHANTABLE,5,EquipmentSlot.MAINHAND));

    public static void init() {
        registry("parry",PARRY);
        registry("carve",CARVE);
    }
    private static Enchantment.Properties properties(TagKey<Item> supportedItems, int maxLevel, EquipmentSlot... slots) {
        return Enchantment.properties(supportedItems, 5, maxLevel, Enchantment.leveledCost(5, 6), Enchantment.leveledCost(11, 6), 2, slots);
    }
    public static void registry(String name,Enchantment enchantment) {
        Registry.register(Registries.ENCHANTMENT, Sifu.id(name), enchantment);
    }
}
