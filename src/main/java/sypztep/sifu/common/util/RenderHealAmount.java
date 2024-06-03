package sypztep.sifu.common.util;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;

public interface RenderHealAmount {
    void renderHeal(DrawContext context, ClientPlayerEntity player);
}
