package sypztep.sifu.common.init;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import sypztep.sifu.Sifu;

public class ModItems {
    public static Item WARDENRITE_FRAGMENT;
    public static Item WARDENRITE_HELMET;
    public static Item WARDENRITE_CHESTPLATE;
    public static Item WARDENRITE_LEGGINGS;
    public static Item WARDENRITE_BOOTS;

    public static void init() {
        WARDENRITE_FRAGMENT = registerItem("wardenrite_fragment", new Item(new Item.Settings().maxCount(9)));

        WARDENRITE_HELMET = registerItem("wardenrite_helmet", new ArmorItem(ModArmorMaterials.WARDENRITE, ArmorItem.Type.HELMET, new Item.Settings().fireproof().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(40))));
        WARDENRITE_CHESTPLATE = registerItem("wardenrite_chestplate", new ArmorItem(ModArmorMaterials.WARDENRITE, ArmorItem.Type.CHESTPLATE, new Item.Settings().fireproof().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(40))));
        WARDENRITE_LEGGINGS = registerItem("wardenrite_leggings", new ArmorItem(ModArmorMaterials.WARDENRITE, ArmorItem.Type.LEGGINGS, new Item.Settings().fireproof().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(40))));
        WARDENRITE_BOOTS = registerItem("wardenrite_boots", new ArmorItem(ModArmorMaterials.WARDENRITE, ArmorItem.Type.BOOTS, new Item.Settings().fireproof().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(40))));
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Sifu.id(name), item);
    }
}
