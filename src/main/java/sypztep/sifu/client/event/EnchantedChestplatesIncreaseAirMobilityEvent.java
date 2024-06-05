package sypztep.sifu.client.event;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.item.TooltipType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import sypztep.sifu.ModConfig;
import sypztep.sifu.common.init.ModDataComponentTypes;

import java.util.List;

public class EnchantedChestplatesIncreaseAirMobilityEvent implements ItemTooltipCallback {
	@Override
	public void getTooltip(ItemStack stack, Item.TooltipContext tooltipContext, TooltipType tooltipType, List<Text> lines) {
		if (ModConfig.enchantedChestplatesIncreaseAirMobility && stack.hasEnchantments() && stack.isIn(ItemTags.CHEST_ARMOR_ENCHANTABLE) && stack.contains(ModDataComponentTypes.TOGGLEABLE_PASSIVE)) {
			MutableText icon = Text.literal("× ");
			Formatting formatting = Formatting.DARK_RED;
			if (stack.get(ModDataComponentTypes.TOGGLEABLE_PASSIVE)) {
				icon = Text.literal("✔ ");
				formatting = Formatting.DARK_GREEN;
			}
			lines.add(1, icon.append(Text.translatable("tooltip.enchancement.increases_air_mobility")).formatted(formatting));
		}
	}
}
