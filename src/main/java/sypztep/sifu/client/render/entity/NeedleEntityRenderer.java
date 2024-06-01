package sypztep.sifu.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;
import sypztep.sifu.Sifu;
import sypztep.sifu.common.entity.projectile.NeedleEntity;

public class NeedleEntityRenderer extends ProjectileEntityRenderer<NeedleEntity> {
	private static final Identifier TEXTURE = Sifu.id("textures/entity/projectiles/needle.png");

	public NeedleEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
	}

	@Override
	public Identifier getTexture(NeedleEntity entity) {
		return TEXTURE;
	}
}
