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
                LootPool.Builder lootPoolkillByPlayer = LootPool.builder().rolls(BinomialLootNumberProvider.create(1, 0.05f)).conditionally(KilledByPlayerLootCondition.builder());
                if (EntityType.WARDEN.getLootTableId().equals(id)) {
                    lootPoolkillByPlayer.with(ItemEntry.builder(ModItems.WARDENRITE_FRAGMENT));
                    tableBuilder.pool(lootPoolkillByPlayer);
                }
            }
        });
    }
}
