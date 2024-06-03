package sypztep.sifu.client.event;

import com.sypztep.common.util.EnchantmentUtil;
import com.sypztep.common.util.StatusEffectUtil;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import sypztep.sifu.common.enchantment.CarveEnchantment;
import sypztep.sifu.common.init.ModEnchantments;
import sypztep.sifu.common.init.ModStatusEffects;
import sypztep.sifu.common.util.AbilityHelper;
import sypztep.sifu.common.util.RenderHealAmount;
import sypztep.sifu.mixin.util.InGameHudAccessor;

public class HealthBarRenderEvent implements HudRenderCallback {
    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MinecraftClient minecraft = MinecraftClient.getInstance();
        ClientPlayerEntity player = minecraft.player;
        int scaledWidth = minecraft.getWindow().getScaledWidth(),
                scaledHeight = minecraft.getWindow().getScaledHeight();
        if (player == null) //ออก ถ้าผู้เล่นไม่เจอผู้เล่น
            return;
        int stalwart = StatusEffectUtil.getAmp(ModStatusEffects.STALWART, player);

        if (EnchantmentUtil.hasEnchantment(ModEnchantments.CARVE, player.getMainHandStack()))
            if (stalwart > 0 && StatusEffectUtil.dontHave(ModStatusEffects.STALWART_COOLDOWN, player))
                ((InGameHudAccessor) minecraft.inGameHud).sifu$renderHealthBar(drawContext, minecraft.player, (scaledWidth / 2) - 25, (scaledHeight / 2) + 25, 1, -1, (float) Math.ceil(CarveEnchantment.getHealAmount()),
                        (int) (stalwart + AbilityHelper.getMissingHealth(player, 0.1f)),
                        (int) (stalwart + AbilityHelper.getMissingHealth(player, 0.1f)),
                        0, false);
    }
}
