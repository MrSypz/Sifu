package sypztep.sifu.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;
import sypztep.sifu.Sifu;
import sypztep.sifu.common.entity.projectile.NeedleEntity;
import sypztep.sifu.common.entity.projectile.ShadowShardsEntity;

public class ShadowShardsEntityRenderer extends ProjectileEntityRenderer<ShadowShardsEntity> {
	private static final Identifier TEXTURE = Sifu.id("textures/entity/projectiles/shadow_shard.png");

	public ShadowShardsEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
	}

	@Override
	public Identifier getTexture(ShadowShardsEntity entity) {
		return TEXTURE;
	}
}
