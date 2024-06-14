// Made with Blockbench 4.10.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package sypztep.sifu.client.render.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import sypztep.sifu.Sifu;

public class ShadowShardModel extends Model {
	public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(Sifu.id("shadow_shard"), "main");
	private final ModelPart shadowShard;
	public ShadowShardModel(ModelPart root) {
		super(RenderLayer::getEntitySolid);
		this.shadowShard = root;
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData shadow_shard = modelPartData.addChild("shadow_shard", ModelPartBuilder.create().uv(4, 6).cuboid(-0.5F, -8.0F, -0.5F, 1.0F, 8.0F, 1.0F, new Dilation(0.0F))
		.uv(4, 0).cuboid(-1.5F, -11.0F, -1.5F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F))
		.uv(13, 0).cuboid(1.5F, -9.0F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(8, 6).cuboid(2.5F, -10.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(-0.5F, -39.0F, -0.5F, 1.0F, 28.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        shadow_shard.addChild("cube_r1", ModelPartBuilder.create().uv(13, 0).cuboid(1.5F, 5.5F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(8, 6).cuboid(2.5F, 4.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -14.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

        shadow_shard.addChild("cube_r2", ModelPartBuilder.create().uv(8, 6).cuboid(2.5F, 4.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(13, 0).cuboid(1.5F, 5.5F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -14.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

        shadow_shard.addChild("cube_r3", ModelPartBuilder.create().uv(13, 0).cuboid(1.5F, 5.5F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(8, 6).cuboid(2.5F, 4.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -14.5F, 0.0F, 0.0F, 3.1416F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		shadowShard.render(matrices,vertices,light,overlay,light);
	}
}