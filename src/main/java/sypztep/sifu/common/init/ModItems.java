package sypztep.sifu.common.init;

import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.Util;
import sypztep.sifu.Sifu;
import sypztep.sifu.common.item.Warfan;
import sypztep.sifu.common.item.impl.WardenriteArmor;
import sypztep.sifu.common.item.impl.WardenriteSword;
import sypztep.sifu.common.item.impl.WardenriteWarfan;

import java.util.List;
import java.util.Set;

public class ModItems {
    public static final Set<Item> ALL_CUSTOM3D = new ReferenceOpenHashSet<>();
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
    public static Item WARDENRITE_SWORD;
    public static Item WARDENRITE_CLEAVER;
    public static Item WARDENRITE_SCYTHE;

    public static void init() {
        WARDENRITE_FRAGMENT = registerItem("wardenrite_fragment", new Item(new Item.Settings().maxCount(9).rarity(Rarity.EPIC)));
        WARDENRITE_INGOT = registerItem("wardenrite_ingot", new Item(new Item.Settings().maxCount(64).rarity(Rarity.EPIC).fireproof()));

        WARDENRITE_HELMET = registerItem("wardenrite_helmet", new WardenriteArmor(ModArmorMaterials.WARDENRITE, ArmorItem.Type.HELMET, new Item.Settings().fireproof().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(120)).rarity(Rarity.EPIC)));
        WARDENRITE_CHESTPLATE = registerItem("wardenrite_chestplate", new WardenriteArmor(ModArmorMaterials.WARDENRITE, ArmorItem.Type.CHESTPLATE, new Item.Settings().fireproof().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(120)).rarity(Rarity.EPIC)));
        WARDENRITE_LEGGINGS = registerItem("wardenrite_leggings", new WardenriteArmor(ModArmorMaterials.WARDENRITE, ArmorItem.Type.LEGGINGS, new Item.Settings().fireproof().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(120)).rarity(Rarity.EPIC)));
        WARDENRITE_BOOTS = registerItem("wardenrite_boots", new WardenriteArmor(ModArmorMaterials.WARDENRITE, ArmorItem.Type.BOOTS, new Item.Settings().fireproof().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(120)).rarity(Rarity.EPIC)));

        WARDENRITE_SHOVEL = registerItem("wardenrite_shovel", new ShovelItem(ModToolMaterials.WARDENRITE, new Item.Settings().fireproof().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolMaterials.WARDENRITE, 1.5f, -3.0f))));
        WARDENRITE_PICKAXE = registerItem("wardenrite_pickaxe", new PickaxeItem(ModToolMaterials.WARDENRITE, new Item.Settings().fireproof().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.WARDENRITE, 1.0f, -2.8f))));
        WARDENRITE_AXE = registerItem("wardenrite_axe", new AxeItem(ModToolMaterials.WARDENRITE, new Item.Settings().fireproof().attributeModifiers(AxeItem.createAttributeModifiers(ModToolMaterials.WARDENRITE, 5.0f, -3.0f))));
        WARDENRITE_HOE = registerItem("wardenrite_hoe", new HoeItem(ModToolMaterials.WARDENRITE, new Item.Settings().fireproof().attributeModifiers(HoeItem.createAttributeModifiers(ModToolMaterials.WARDENRITE, -4.0f, 0.0f))));

        WARDENRITE_UPGRADE_SMITHING_TEMPLATE = registerItem("wardenrite_upgrade_smithing_template", createWardenriteUpgradeSmithingTemplate());

        WARDENRITE_WARFAN = registerCustom3DItem("wardenrite_warfan", new WardenriteWarfan(ModToolMaterials.WARDENRITE, new Item.Settings().attributeModifiers(Warfan.createAttributeModifiers(ModToolMaterials.WARDENRITE, 2, -2,-0.5)).rarity(Rarity.EPIC)));
        WARDENRITE_SWORD = registerCustom3DItem("wardenrite_sword", new WardenriteSword(ModToolMaterials.WARDENRITE, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolMaterials.WARDENRITE, 5, -2.4f)).rarity(Rarity.EPIC)));
        WARDENRITE_CLEAVER = registerCustom3DItem("wardenrite_cleaver", new WardenriteSword(ModToolMaterials.WARDENRITE, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolMaterials.WARDENRITE, 7, -3.0f)).rarity(Rarity.EPIC)));
        WARDENRITE_SCYTHE = registerCustom3DItem("wardenrite_scythe", new WardenriteSword(ModToolMaterials.WARDENRITE, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolMaterials.WARDENRITE, 7, -3.0f)).rarity(Rarity.EPIC)));

    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Sifu.id(name), item);
    }

    public static <T extends Item> T registerCustom3DItem(String name, T item) {
        Registry.register(Registries.ITEM, Sifu.id(name), item);
        ALL_CUSTOM3D.add(item);
        return item;
    }

    private static Item createWardenriteUpgradeSmithingTemplate() {
        return new SmithingTemplateItem(
                Text.translatable(Util.createTranslationKey("item", Sifu.id("smithing_template.wardenrite_upgrade.applies_to"))).formatted(Formatting.BLUE),
                Text.translatable(Util.createTranslationKey("item", Sifu.id("smithing_template.wardenrite_upgrade.ingredients"))).formatted(Formatting.BLUE),
                Text.translatable(Util.createTranslationKey("upgrade", Sifu.id("wardenrite_upgrade"))).formatted(Formatting.GRAY),
                Text.translatable(Util.createTranslationKey("item", Sifu.id("smithing_template.wardenrite_upgrade.base_slot_description"))),
                Text.translatable(Util.createTranslationKey("item", Sifu.id("smithing_template.wardenrite_upgrade.additions_slot_description"))), getWardenriteBaseSlotTextures(), getWardenriteAdditionsSlotTextures());
    }

    private static List<Identifier> getWardenriteBaseSlotTextures() {
        return List.of(Identifier.ofVanilla("item/empty_armor_slot_helmet"),
                Identifier.ofVanilla("item/empty_armor_slot_chestplate"),
                Identifier.ofVanilla("item/empty_armor_slot_leggings"),
                Identifier.ofVanilla("item/empty_armor_slot_boots"),
                Identifier.ofVanilla("item/empty_slot_pickaxe"),
                Identifier.ofVanilla("item/empty_slot_axe"),
                Identifier.ofVanilla("item/empty_slot_shovel"),
                Identifier.ofVanilla("item/empty_slot_hoe"));
    }

    private static List<Identifier> getWardenriteAdditionsSlotTextures() {
        return List.of(Sifu.id("item/empty_slot_wardenrite_ingot"));
    }
}
