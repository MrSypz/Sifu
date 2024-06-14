package sypztep.sifu.client.render.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import sypztep.sifu.Sifu;
import sypztep.sifu.client.render.model.ShadowShardModel;
import sypztep.sifu.common.entity.projectile.ShadowShardsEntity;

public class ShadowShardsEntityRenderer extends EntityRenderer<ShadowShardsEntity> {
	private final ShadowShardModel model;
	private static final Identifier TEXTURE = Sifu.id("textures/entity/projectiles/shadow_shard.png");

	public ShadowShardsEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
        this.model = new ShadowShardModel(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(ShadowShardModel.MODEL_LAYER));
		this.shadowRadius = 0.15F;
    }

	@Override
	public void render(ShadowShardsEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		if (!entity.isInvisible()) {
			matrices.push();
			matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()) - 90.0f));
			matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch()) + 90.0f));
			Identifier texture = getTexture(entity);
			VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(texture));
			this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, Colors.WHITE);
			matrices.pop();
			super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
		}	}

	@Override
	public Identifier getTexture(ShadowShardsEntity entity) {
		return TEXTURE;
	}
}
