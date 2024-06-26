package sypztep.sifu.data.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import sypztep.sifu.Sifu;
import sypztep.sifu.common.init.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.WARDENRITE_INGOT, 1)
                .pattern("EEE")
                .pattern("E#Z")
                .pattern("ZZZ")
                .input('#', Items.ECHO_SHARD)
                .input('E', Items.NETHERITE_INGOT)
                .input('Z', ModItems.WARDENRITE_FRAGMENT)
                .criterion(hasItem(ModItems.WARDENRITE_FRAGMENT), conditionsFromItem(ModItems.WARDENRITE_FRAGMENT))
                .offerTo(exporter, Sifu.id(getRecipeName(ModItems.WARDENRITE_FRAGMENT)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.WARDENRITE_UPGRADE_SMITHING_TEMPLATE, 1)
                .pattern("EWE")
                .pattern("ESE")
                .pattern("EEE")
                .input('S', Items.SCULK_CATALYST)
                .input('E', ModItems.WARDENRITE_INGOT)
                .input('W', ModItems.WARDENRITE_FRAGMENT)
                .criterion(hasItem(ModItems.WARDENRITE_INGOT), conditionsFromItem(ModItems.WARDENRITE_INGOT))
                .offerTo(exporter, Sifu.id(getRecipeName(ModItems.WARDENRITE_UPGRADE_SMITHING_TEMPLATE)));

        offerWardenriteUpgradeRecipe(exporter, Items.NETHERITE_HELMET, RecipeCategory.COMBAT, ModItems.WARDENRITE_HELMET);
        offerWardenriteUpgradeRecipe(exporter, Items.NETHERITE_CHESTPLATE, RecipeCategory.COMBAT, ModItems.WARDENRITE_CHESTPLATE);
        offerWardenriteUpgradeRecipe(exporter, Items.NETHERITE_LEGGINGS, RecipeCategory.COMBAT, ModItems.WARDENRITE_LEGGINGS);
        offerWardenriteUpgradeRecipe(exporter, Items.NETHERITE_BOOTS, RecipeCategory.COMBAT, ModItems.WARDENRITE_BOOTS);
        offerWardenriteUpgradeRecipe(exporter, Items.NETHERITE_PICKAXE, RecipeCategory.TOOLS, ModItems.WARDENRITE_PICKAXE);
        offerWardenriteUpgradeRecipe(exporter, Items.NETHERITE_SHOVEL, RecipeCategory.TOOLS, ModItems.WARDENRITE_SHOVEL);
        offerWardenriteUpgradeRecipe(exporter, Items.NETHERITE_HOE, RecipeCategory.TOOLS, ModItems.WARDENRITE_HOE);
        offerWardenriteUpgradeRecipe(exporter, Items.NETHERITE_AXE, RecipeCategory.TOOLS, ModItems.WARDENRITE_AXE);
    }
    public static void offerWardenriteUpgradeRecipe(RecipeExporter exporter, Item input, RecipeCategory category, Item result) {
        SmithingTransformRecipeJsonBuilder.create(Ingredient.ofItems(ModItems.WARDENRITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.ofItems(input), Ingredient.ofItems(ModItems.WARDENRITE_INGOT), category, result)
                .criterion(hasItem(ModItems.WARDENRITE_FRAGMENT), RecipeProvider.conditionsFromItem(ModItems.WARDENRITE_INGOT))
                .offerTo(exporter, RecipeProvider.getItemPath(result) + "_smithing");
    }
}
