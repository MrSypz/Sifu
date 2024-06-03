package sypztep.sifu.client;

import com.sypztep.common.util.EnchantmentUtil;
import com.sypztep.common.util.StatusEffectUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import sypztep.sifu.Sifu;
import sypztep.sifu.common.init.ModEnchantments;
import sypztep.sifu.common.init.ModStatusEffects;
import sypztep.sifu.common.util.AbilityHelper;

import java.util.List;


@Environment(EnvType.CLIENT)
public class TooltipItem {
    public static void onTooltipRender(ItemStack stack, List<Text> lines, Item.TooltipContext context) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (EnchantmentUtil.hasEnchantment(ModEnchantments.CARVE, stack)) {
            addCarveTooltip(lines, player);
        }
    }

    private static void addCarveTooltip(List<Text> lines, ClientPlayerEntity client) {
        if (client != null) {
            double amount = StatusEffectUtil.getAmp(ModStatusEffects.STALWART, client) + AbilityHelper.getMissingHealth(client, 0.25f);
            addFormattedTooltip(lines, String.format("%.2f",amount), "carve");
        }
    }

    private static void addFormattedTooltip(List<Text> lines, String value, String key, String... extraKeys) {
        Text tooltip = Text.translatable(Sifu.MODID + ".modifytooltip." + key).formatted(Formatting.GRAY)
                .append(Text.literal(value).formatted(Formatting.RED));

        for (String extraKey : extraKeys)
            tooltip = tooltip.copy().append(Text.translatable(Sifu.MODID + ".modifytooltip." + extraKey).formatted(Formatting.GRAY));

        lines.add(tooltip);
    }

}
