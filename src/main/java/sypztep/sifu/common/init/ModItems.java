package sypztep.sifu.common.init;

import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.Util;
import sypztep.sifu.Sifu;
import sypztep.sifu.common.item.WardenriteWarfan;
import sypztep.sifu.common.item.Warfan;

import java.util.List;

public class ModItems {
    public static Item WARDENRITE_FRAGMENT;
    public static Item WARDENRITE_HELMET;
    public static Item WARDENRITE_CHESTPLATE;
    public static Item WARDENRITE_LEGGINGS;
    public static Item WARDENRITE_BOOTS;

    public static Item WARDENRITE_SHOVEL;
    public static Item WARDENRITE_PICKAXE;
    public static Item WARDENRITE_AXE;
    public static Item WARDENRITE_HOE;

    public static Item WARDENRITE_INGOT;

    public static Item WARDENRITE_UPGRADE_SMITHING_TEMPLATE;
    public static Item WARDENRITE_WARFAN;

    public static void init() {
        WARDENRITE_FRAGMENT = registerItem("wardenrite_fragment", new Item(new Item.Settings().maxCount(9).rarity(Rarity.EPIC)));
        WARDENRITE_INGOT = registerItem("wardenrite_ingot", new Item(new Item.Settings().maxCount(64).rarity(Rarity.EPIC).fireproof()));

        WARDENRITE_HELMET = registerItem("wardenrite_helmet", new ArmorItem(ModArmorMaterials.WARDENRITE, ArmorItem.Type.HELMET, new Item.Settings().fireproof().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(40)).rarity(Rarity.EPIC)));
        WARDENRITE_CHESTPLATE = registerItem("wardenrite_chestplate", new ArmorItem(ModArmorMaterials.WARDENRITE, ArmorItem.Type.CHESTPLATE, new Item.Settings().fireproof().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(40)).rarity(Rarity.EPIC)));
        WARDENRITE_LEGGINGS = registerItem("wardenrite_leggings", new ArmorItem(ModArmorMaterials.WARDENRITE, ArmorItem.Type.LEGGINGS, new Item.Settings().fireproof().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(40)).rarity(Rarity.EPIC)));
        WARDENRITE_BOOTS = registerItem("wardenrite_boots", new ArmorItem(ModArmorMaterials.WARDENRITE, ArmorItem.Type.BOOTS, new Item.Settings().fireproof().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(40)).rarity(Rarity.EPIC)));

        WARDENRITE_SHOVEL = registerItem("wardenrite_shovel", new ShovelItem(ModToolMaterials.WARDENRITE, new Item.Settings().fireproof().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolMaterials.WARDENRITE, 1.5f, -3.0f))));
        WARDENRITE_PICKAXE = registerItem("wardenrite_pickaxe", new PickaxeItem(ModToolMaterials.WARDENRITE, new Item.Settings().fireproof().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.WARDENRITE, 1.0f, -2.8f))));
        WARDENRITE_AXE = registerItem("wardenrite_axe", new AxeItem(ModToolMaterials.WARDENRITE, new Item.Settings().fireproof().attributeModifiers(AxeItem.createAttributeModifiers(ModToolMaterials.WARDENRITE, 5.0f, -3.0f))));
        WARDENRITE_HOE = registerItem("wardenrite_hoe", new HoeItem(ModToolMaterials.WARDENRITE, new Item.Settings().fireproof().attributeModifiers(HoeItem.createAttributeModifiers(ModToolMaterials.WARDENRITE, -4.0f, 0.0f))));

        WARDENRITE_UPGRADE_SMITHING_TEMPLATE = registerItem("wardenrite_upgrade_smithing_template", createWardenriteUpgradeSmithingTemplate());

        WARDENRITE_WARFAN = registerItem("wardenrite_warfan", new WardenriteWarfan(ModToolMaterials.WARDENRITE, new Item.Settings().attributeModifiers(Warfan.createAttributeModifiers(ModToolMaterials.WARDENRITE, 3, -2)).rarity(Rarity.EPIC)));
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Sifu.id(name), item);
    }

    private static Item createWardenriteUpgradeSmithingTemplate() {
        return new SmithingTemplateItem(
                Text.translatable(Util.createTranslationKey("item", new Identifier(Sifu.MODID, "smithing_template.wardenrite_upgrade.applies_to"))).formatted(Formatting.BLUE),
                Text.translatable(Util.createTranslationKey("item", new Identifier(Sifu.MODID, "smithing_template.wardenrite_upgrade.ingredients"))).formatted(Formatting.BLUE),
                Text.translatable(Util.createTranslationKey("upgrade", new Identifier(Sifu.MODID, "wardenrite_upgrade"))).formatted(Formatting.GRAY),
                Text.translatable(Util.createTranslationKey("item", new Identifier(Sifu.MODID, "smithing_template.wardenrite_upgrade.base_slot_description"))),
                Text.translatable(Util.createTranslationKey("item", new Identifier(Sifu.MODID, "smithing_template.wardenrite_upgrade.additions_slot_description"))), getWardenriteBaseSlotTextures(), getWardenriteAdditionsSlotTextures());
    }

    private static List<Identifier> getWardenriteBaseSlotTextures() {
        return List.of(new Identifier("item/empty_armor_slot_helmet"),
                new Identifier("item/empty_armor_slot_chestplate"),
                new Identifier("item/empty_armor_slot_leggings"),
                new Identifier("item/empty_armor_slot_boots"),
                new Identifier("item/empty_slot_pickaxe"),
                new Identifier("item/empty_slot_axe"),
                new Identifier("item/empty_slot_shovel"),
                new Identifier("item/empty_slot_hoe"));
    }

    private static List<Identifier> getWardenriteAdditionsSlotTextures() {
        return List.of(new Identifier(Sifu.MODID, "item/empty_slot_wardenrite_ingot"));
    }
}
