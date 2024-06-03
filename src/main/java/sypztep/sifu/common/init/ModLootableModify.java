package sypztep.sifu.common.init;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.EnchantRandomlyLootFunction;
import net.minecraft.loot.provider.number.BinomialLootNumberProvider;

public class ModLootableModify {
    public static void init() {
        LootTableEvents.MODIFY.register((id, tableBuilder, source) -> {
            if (source.isBuiltin()) {
                if (LootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY.equals(id)) {
                    LootPool.Builder lootPool = LootPool.builder().rolls(BinomialLootNumberProvider.create(1, 0.05f));
                    lootPool.with(ItemEntry.builder(Items.BOOK).apply(new EnchantRandomlyLootFunction.Builder().add(ModEnchantments.THOUSAND_NEEDLE)));
                    tableBuilder.pool(lootPool);
                }
            }
        });
    }
}
