package sypztep.sifu.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import sypztep.sifu.common.entity.projectile.HookEntity;
import sypztep.sifu.common.init.ModItems;

@Environment(value= EnvType.CLIENT)
public class HookEntityRenderer
        extends EntityRenderer<HookEntity> {
    private final ItemRenderer itemRenderer;

    public HookEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(HookEntity hookEntity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
        PlayerEntity playerEntity = hookEntity.getPlayer();
        if (playerEntity == null) {
            return;
        }
        matrixStack.push();
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(tickDelta, hookEntity.prevYaw, hookEntity.getYaw()) - 90.0F));
        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.lerp(tickDelta, hookEntity.prevPitch, hookEntity.getPitch()) - 90.0F));
        matrixStack.scale(1.5f,1.5f,1.5f);
        this.itemRenderer.renderItem(new ItemStack(Items.IRON_PICKAXE), ModelTransformationMode.FIRST_PERSON_RIGHT_HAND, light, OverlayTexture.DEFAULT_UV, matrixStack, vertexConsumerProvider, hookEntity.getWorld(), hookEntity.getId());
        matrixStack.pop();

        matrixStack.push();
        Vec3d vec3d = HookEntityRenderer.getPlayerHandPos(playerEntity, tickDelta, ModItems.GRAPHOOK, this.dispatcher);
        Vec3d vec3d2 = new Vec3d(MathHelper.lerp(tickDelta, hookEntity.prevX, hookEntity.getX()), MathHelper.lerp(tickDelta, hookEntity.prevY, hookEntity.getY()) + (double)hookEntity.getStandingEyeHeight(), MathHelper.lerp(tickDelta, hookEntity.prevZ, hookEntity.getZ()));
        float h = 0;
        float j = h * 0.15f % 1.0f;
        Vec3d vec3d3 = vec3d.subtract(vec3d2);
        float k = (float)(vec3d3.length() + 0.1);
        vec3d3 = vec3d3.normalize();
        float l = (float)Math.acos(vec3d3.y);
        float m = (float)Math.atan2(vec3d3.z, vec3d3.x);
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((1.5707964f - m) * 57.295776f));
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(l * 57.295776f));
        float n = h * 0.05f * -1.5f;
        float p = MathHelper.cos(n + (float)Math.PI) * 0.2f;
        float q = MathHelper.sin(n + (float)Math.PI) * 0.2f;
        float r = MathHelper.cos(n + 0.0f) * 0.2f;
        float s = MathHelper.sin(n + 0.0f) * 0.2f;
        float t = MathHelper.cos(n + 1.5707964f) * 0.2f;
        float u = MathHelper.sin(n + 1.5707964f) * 0.2f;
        float v = MathHelper.cos(n + 4.712389f) * 0.2f;
        float w = MathHelper.sin(n + 4.712389f) * 0.2f;
        float aa = -1.0f + j;
        float ab = k * 2.5f + aa;
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getLeash());


        MatrixStack.Entry entry = matrixStack.peek();
        HookEntityRenderer.vertex(vertexConsumer, entry, p, k, q, 0.4999f, ab);
        HookEntityRenderer.vertex(vertexConsumer, entry, p, 0.0f, q, 0.4999f, aa);
        HookEntityRenderer.vertex(vertexConsumer, entry, r, 0.0f, s, 0.0f, aa);
        HookEntityRenderer.vertex(vertexConsumer, entry, r, k, s, 0.0f, ab);
        HookEntityRenderer.vertex(vertexConsumer, entry, t, k, u, 0.4999f, ab);
        HookEntityRenderer.vertex(vertexConsumer, entry, t, 0.0f, u, 0.4999f, aa);
        HookEntityRenderer.vertex(vertexConsumer, entry, v, 0.0f, w, 0.0f, aa);
        HookEntityRenderer.vertex(vertexConsumer, entry, v, k, w, 0.0f, ab);
        matrixStack.pop();
        super.render(hookEntity, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
    }
    public static Vec3d getPlayerHandPos(PlayerEntity player, float tickDelta, Item item, EntityRenderDispatcher dispatcher) {
        int i = player.getMainArm() == Arm.RIGHT ? 1 : -1;
        ItemStack itemStack = player.getMainHandStack();
        if (!itemStack.isOf(item)) {
            i = -i;
        }
        float f = player.getHandSwingProgress(tickDelta);
        float g = MathHelper.sin(MathHelper.sqrt(f) * (float)Math.PI);
        float h = MathHelper.lerp(tickDelta, player.prevBodyYaw, player.bodyYaw) * ((float)Math.PI / 180);
        double d = MathHelper.sin(h);
        double e = MathHelper.cos(h);
        double j = (double)i * 0.35;
        if (dispatcher.gameOptions != null && !dispatcher.gameOptions.getPerspective().isFirstPerson() || player != MinecraftClient.getInstance().player) {
            float l = player.isInSneakingPose() ? -0.1875f : 0.0f;
            return new Vec3d(MathHelper.lerp(tickDelta, player.prevX, player.getX()) - e * j - d * 0.8, player.prevY + (double)player.getStandingEyeHeight() + (player.getY() - player.prevY) * (double)tickDelta - 0.45 + (double)l, MathHelper.lerp(tickDelta, player.prevZ, player.getZ()) - d * j + e * 0.8);
        }
        double m = 960.0 / (double) dispatcher.gameOptions.getFov().getValue();
        Vec3d vec3d = dispatcher.camera.getProjection().getPosition((float)i * 0.525f, -0.1f);
        vec3d = vec3d.multiply(m);
        vec3d = vec3d.rotateY(g * 0.5f);
        vec3d = vec3d.rotateX(-g * 0.7f);
        return new Vec3d(MathHelper.lerp(tickDelta, player.prevX, player.getX()) + vec3d.x, MathHelper.lerp(tickDelta, player.prevY, player.getY()) + vec3d.y + (double)player.getStandingEyeHeight(), MathHelper.lerp(tickDelta, player.prevZ, player.getZ()) + vec3d.z);
    }
    private static void vertex(VertexConsumer vertexConsumer, MatrixStack.Entry matrix, float x, float y, float z, float u, float v) {
        vertexConsumer.vertex(matrix, x * 0.2f, y, z * 0.2f).color(Colors.BLACK).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(0).normal(0.0f, 1.0f, 0.0f);
    }

    @Override
    public Identifier getTexture(HookEntity lashingPotatoHookEntity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}
