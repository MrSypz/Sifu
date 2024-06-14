package sypztep.sifu.common.init;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import sypztep.sifu.Sifu;

public class ModEnchantments {
    //SWORD
//    public static final Enchantment PARRY = new ParryEnchantment(properties(ItemTags.SWORDS,3,EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND));
//    public static final Enchantment RIPOSTE = new Enchantment(properties(ItemTags.SWORDS,5,EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND));
//    public static final Enchantment CARVE = new CarveEnchantment(properties(ItemTags.SWORDS,5,EquipmentSlot.MAINHAND));
//    //HOE
//    public static final Enchantment THOUSAND_NEEDLE = new ThousandNeedleEnchantment(properties(ItemTags.HOES,3,EquipmentSlot.MAINHAND));
//    //HELMET
//    public static final Enchantment PERCEPTION = new UtilityEnchantment(properties(ItemTags.HEAD_ARMOR_ENCHANTABLE, 2, EquipmentSlot.HEAD));
//    public static final Enchantment VEIL = new UtilityEnchantment(properties(ItemTags.HEAD_ARMOR_ENCHANTABLE, 2, EquipmentSlot.HEAD));
//    //PICKAXE
//    public static final Enchantment LIGHTHAND = new NoSilkTouchEnchantment(properties(ItemTags.PICKAXES, 10, EquipmentSlot.MAINHAND));
//    //AXE
//    public static final Enchantment LUMBERJACK = new UtilityEnchantment(properties(ItemTags.AXES, 1, EquipmentSlot.MAINHAND));
//    //CHESTPLATE
//    public static final Enchantment CRYOBSIDER = new CryObsiderEnchantment(properties(ItemTags.CHEST_ARMOR_ENCHANTABLE, 2, EquipmentSlot.CHEST));
//    //BOOTS
//    public static final Enchantment MOONSTEP = new UtilityEnchantment(properties(ItemTags.FOOT_ARMOR_ENCHANTABLE, 5, EquipmentSlot.FEET));
//
//    public static void init() {
//        registry("parry",PARRY);
//        registry("carve",CARVE);
//        registry("thousandneedle",THOUSAND_NEEDLE);
//        registry("perception",PERCEPTION);
//        registry("veil",VEIL);
//        registry("lighthand",LIGHTHAND);
//        registry("lumberjack",LUMBERJACK);
//        registry("cryobsider",CRYOBSIDER);
//        registry("moonstep",MOONSTEP);
//        registry("riposte",RIPOSTE);
//    }
//    private static Enchantment.Properties properties(TagKey<Item> supportedItems, int maxLevel, EquipmentSlot... slots) {
//        return Enchantment.properties(supportedItems, 5, maxLevel, Enchantment.leveledCost(5, 8), Enchantment.leveledCost(11, 6), 5, slots);
//    }
//    public static void registry(String name,Enchantment enchantment) {
//        Registry.register(Registries., Sifu.id(name), enchantment);
//    }
}
