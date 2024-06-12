package sypztep.sifu.data.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import sypztep.sifu.common.init.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        //Items
        getOrCreateTagBuilder(ItemTags.SHOVELS)
                .add(ModItems.WARDENRITE_SHOVEL);
        getOrCreateTagBuilder(ItemTags.AXES)
                .add(ModItems.WARDENRITE_AXE);
        getOrCreateTagBuilder(ItemTags.HOES)
                .add(ModItems.WARDENRITE_HOE);
        getOrCreateTagBuilder(ItemTags.PICKAXES)
                .add(ModItems.WARDENRITE_PICKAXE);


        //Armor
        getOrCreateTagBuilder(ItemTags.HEAD_ARMOR)
                .add(ModItems.WARDENRITE_HELMET);
        getOrCreateTagBuilder(ItemTags.CHEST_ARMOR)
                .add(ModItems.WARDENRITE_CHESTPLATE);
        getOrCreateTagBuilder(ItemTags.LEG_ARMOR)
                .add(ModItems.WARDENRITE_LEGGINGS);
        getOrCreateTagBuilder(ItemTags.FOOT_ARMOR)
                .add(ModItems.WARDENRITE_BOOTS);

        //Trimable
        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.WARDENRITE_LEGGINGS,ModItems.WARDENRITE_CHESTPLATE, ModItems.WARDENRITE_BOOTS,ModItems.WARDENRITE_HELMET);
    }
}
