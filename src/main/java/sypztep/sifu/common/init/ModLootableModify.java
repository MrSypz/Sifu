package sypztep.sifu.common.init;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.KilledByPlayerLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.EnchantRandomlyLootFunction;
import net.minecraft.loot.provider.number.BinomialLootNumberProvider;

public class ModLootableModify {
    public static void init() {
        LootTableEvents.MODIFY.register((id, tableBuilder, source) -> {
            if (source.isBuiltin()) {
                LootPool.Builder lootPool = LootPool.builder().rolls(BinomialLootNumberProvider.create(1, 0.05f));
                LootPool.Builder lootPoolkillByPlayer = LootPool.builder().rolls(BinomialLootNumberProvider.create(1, 0.05f)).conditionally(KilledByPlayerLootCondition.builder());
                if (LootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY.equals(id)) {
                    lootPool.with(ItemEntry.builder(Items.BOOK).apply(new EnchantRandomlyLootFunction.Builder().add(ModEnchantments.THOUSAND_NEEDLE)));
                    tableBuilder.pool(lootPool);
                } else if (LootTables.ANCIENT_CITY_CHEST.equals(id)) {
                    lootPool.with(ItemEntry.builder(Items.BOOK).apply(new EnchantRandomlyLootFunction.Builder().add(ModEnchantments.LIGHTHAND)));
                    lootPool.with(ItemEntry.builder(Items.BOOK).apply(new EnchantRandomlyLootFunction.Builder().add(ModEnchantments.VEIL)));
                    lootPool.with(ItemEntry.builder(Items.BOOK).apply(new EnchantRandomlyLootFunction.Builder().add(ModEnchantments.PERCEPTION)));
                    tableBuilder.pool(lootPool);
                } else if (LootTables.WOODLAND_MANSION_CHEST.equals(id)) {
                    lootPool.with(ItemEntry.builder(Items.BOOK).apply(new EnchantRandomlyLootFunction.Builder().add(ModEnchantments.LUMBERJACK)));
                    tableBuilder.pool(lootPool);
                } else if (EntityType.WARDEN.getLootTableId().equals(id)) {
                    lootPoolkillByPlayer.with(ItemEntry.builder(ModItems.WARDENRITE_FRAGMENT));
                    tableBuilder.pool(lootPoolkillByPlayer);
                } else if (LootTables.END_CITY_TREASURE_CHEST.equals(id)) {
                    lootPool.with(ItemEntry.builder(Items.DIAMOND_BOOTS).apply(new EnchantRandomlyLootFunction.Builder().add(ModEnchantments.MOONSTEP)));
                }
            }
        });
    }
}
