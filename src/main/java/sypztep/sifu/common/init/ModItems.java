package sypztep.sifu.common.init;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.Util;
import sypztep.sifu.Sifu;

import java.util.List;

public class ModItems {
    public static Item WARDENRITE_FRAGMENT;
    public static Item WARDENRITE_HELMET;
    public static Item WARDENRITE_CHESTPLATE;
    public static Item WARDENRITE_LEGGINGS;
    public static Item WARDENRITE_BOOTS;

    public static void init() {
        WARDENRITE_FRAGMENT = registerItem("wardenrite_fragment", new Item(new Item.Settings().maxCount(9)));

        WARDENRITE_HELMET = registerItem("wardenrite_helmet", new ArmorItem(ModArmorMaterials.WARDENRITE, ArmorItem.Type.HELMET, new Item.Settings().fireproof().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(40)).rarity(Rarity.EPIC)));
        WARDENRITE_CHESTPLATE = registerItem("wardenrite_chestplate", new ArmorItem(ModArmorMaterials.WARDENRITE, ArmorItem.Type.CHESTPLATE, new Item.Settings().fireproof().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(40)).rarity(Rarity.EPIC)));
        WARDENRITE_LEGGINGS = registerItem("wardenrite_leggings", new ArmorItem(ModArmorMaterials.WARDENRITE, ArmorItem.Type.LEGGINGS, new Item.Settings().fireproof().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(40)).rarity(Rarity.EPIC)));
        WARDENRITE_BOOTS = registerItem("wardenrite_boots", new ArmorItem(ModArmorMaterials.WARDENRITE, ArmorItem.Type.BOOTS, new Item.Settings().fireproof().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(40)).rarity(Rarity.EPIC)));
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Sifu.id(name), item);
    }
    private static Item createWardenriteUpgradeSmithingTemplate() {
        return new SmithingTemplateItem(
                Text.translatable(Util.createTranslationKey("item", new Identifier(Sifu.MODID, "smithing_template.warden_upgrade.applies_to"))).formatted(Formatting.BLUE),
                Text.translatable(Util.createTranslationKey("item", new Identifier(Sifu.MODID, "smithing_template.warden_upgrade.ingredients"))).formatted(Formatting.BLUE),
                Text.translatable(Util.createTranslationKey("upgrade", new Identifier(Sifu.MODID, "warden_upgrade"))).formatted(Formatting.GRAY),
                Text.translatable(Util.createTranslationKey("item", new Identifier(Sifu.MODID, "smithing_template.warden_upgrade.base_slot_description"))),
                Text.translatable(Util.createTranslationKey("item", new Identifier(Sifu.MODID, "smithing_template.warden_upgrade.additions_slot_description"))), getWardenriteBaseSlotTextures(), getWardenriteAdditionsSlotTextures());
    }
    private static List<Identifier> getWardenriteBaseSlotTextures() {
        return List.of(new Identifier("item/empty_armor_slot_helmet"),
                new Identifier("item/empty_armor_slot_chestplate"),
                new Identifier("item/empty_armor_slot_leggings"),
                new Identifier("item/empty_armor_slot_boots"),
                new Identifier("item/empty_slot_sword"),
                new Identifier("item/empty_slot_pickaxe"),
                new Identifier("item/empty_slot_axe"),
                new Identifier("item/empty_slot_shovel"),
                new Identifier("item/empty_slot_hoe"));
    }

    private static List<Identifier> getWardenriteAdditionsSlotTextures() {
        return List.of(new Identifier(Sifu.MODID, "item/empty_slot_reinforced_echo_shard"));
    }
}
