package sypztep.sifu.data.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Items;
import sypztep.sifu.common.init.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.registerArmor((ArmorItem) ModItems.WARDENRITE_HELMET);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.WARDENRITE_CHESTPLATE);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.WARDENRITE_LEGGINGS);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.WARDENRITE_BOOTS);

        itemModelGenerator.register(ModItems.WARDENRITE_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.WARDENRITE_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.WARDENRITE_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.WARDENRITE_HOE, Models.HANDHELD);

        itemModelGenerator.register(ModItems.WARDENRITE_FRAGMENT, Models.GENERATED);
        itemModelGenerator.register(ModItems.WARDENRITE_INGOT, Models.GENERATED);

        itemModelGenerator.register(ModItems.WARDENRITE_UPGRADE_SMITHING_TEMPLATE, Models.GENERATED);
    }
}
