package sypztep.sifu.common.init;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;

public class ModItemGroups {
    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> {
            content.addAfter(Items.TRIDENT, ModItems.WARDENRITE_WARFAN);

            content.addAfter(Items.NETHERITE_BOOTS, ModItems.WARDENRITE_HELMET);
            content.addAfter(ModItems.WARDENRITE_HELMET, ModItems.WARDENRITE_CHESTPLATE);
            content.addAfter(ModItems.WARDENRITE_CHESTPLATE, ModItems.WARDENRITE_LEGGINGS);
            content.addAfter(ModItems.WARDENRITE_LEGGINGS, ModItems.WARDENRITE_BOOTS);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(content -> {
            content.addAfter(Items.DISC_FRAGMENT_5, ModItems.WARDENRITE_FRAGMENT);
            content.addAfter(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, ModItems.WARDENRITE_UPGRADE_SMITHING_TEMPLATE);
            content.addAfter(Items.NETHERITE_INGOT, ModItems.WARDENRITE_INGOT);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.addAfter(Items.NETHERITE_HOE, ModItems.WARDENRITE_SHOVEL);
            content.addAfter(ModItems.WARDENRITE_SHOVEL, ModItems.WARDENRITE_PICKAXE);
            content.addAfter(ModItems.WARDENRITE_PICKAXE, ModItems.WARDENRITE_AXE);
            content.addAfter(ModItems.WARDENRITE_AXE, ModItems.WARDENRITE_HOE);
        });
    }
}
