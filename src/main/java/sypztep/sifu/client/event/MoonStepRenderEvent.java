package sypztep.sifu.client.event;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import sypztep.sifu.Sifu;
import sypztep.sifu.common.init.ModEntityComponents;

public class MoonStepRenderEvent implements HudRenderCallback {
	private static final Identifier[] TEXTURES = new Identifier[16];

	static {
		for (int i = 0; i < TEXTURES.length; i++) {
			TEXTURES[i] = Sifu.id("hud/gale_" + i);
		}
	}

	@Override
	public void onHudRender(DrawContext drawContext, float tickDelta) {
		ModEntityComponents.MOONSTEP.maybeGet(MinecraftClient.getInstance().cameraEntity).ifPresent(component -> {
			if (component.hasMoonStep()) {
				int jumpsLeft = component.getJumpsLeft();
				if (jumpsLeft < component.getMoonStepLevel()) {
					RenderSystem.enableBlend();
					Identifier first = getTexture(jumpsLeft + 1);
					Identifier second = getTexture(jumpsLeft);
					int x = drawContext.getScaledWindowWidth() / 2 - 5, y = drawContext.getScaledWindowHeight() / 2 + 27;
					if (component.getMoonstepCooldown() < component.getLastmoonstepCooldown()) {
						drawContext.drawGuiTexture(first, x, y, 9, 9);
						drawContext.drawGuiTexture(second, 9, 9, 0, 0, x, y, 9, (int) ((component.getMoonstepCooldown() / (float) component.getLastmoonstepCooldown()) * 9));
					} else {
						drawContext.drawGuiTexture(second, x, y, 9, 9);
					}
					drawContext.setShaderColor(1, 1, 1, 1);
					RenderSystem.disableBlend();
				}
			}
		});
	}

	private static Identifier getTexture(int i) {
		i %= TEXTURES.length;
		if (i < 0) {
			i += TEXTURES.length;
		}
		return TEXTURES[i];
	}
}
