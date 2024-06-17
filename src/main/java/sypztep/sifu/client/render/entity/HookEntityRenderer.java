package sypztep.sifu.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
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
    public void render(HookEntity lashingPotatoHookEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        PlayerEntity playerEntity = lashingPotatoHookEntity.getPlayer();
        if (playerEntity == null) {
            return;
        }
        matrixStack.push();
        this.itemRenderer.renderItem(new ItemStack(Items.IRON_PICKAXE), ModelTransformationMode.GROUND, i, OverlayTexture.DEFAULT_UV, matrixStack, vertexConsumerProvider, lashingPotatoHookEntity.getWorld(), lashingPotatoHookEntity.getId());
        Vec3d vec3d = HookEntityRenderer.getPlayerHandPos(playerEntity, g, ModItems.GRAPHOOK, this.dispatcher);
        Vec3d vec3d2 = new Vec3d(MathHelper.lerp(g, lashingPotatoHookEntity.prevX, lashingPotatoHookEntity.getX()), MathHelper.lerp((double)g, lashingPotatoHookEntity.prevY, lashingPotatoHookEntity.getY()) + (double)lashingPotatoHookEntity.getStandingEyeHeight(), MathHelper.lerp((double)g, lashingPotatoHookEntity.prevZ, lashingPotatoHookEntity.getZ()));
        float h = (float)lashingPotatoHookEntity.age + g;
        float j = h * 0.15f % 1.0f;
        Vec3d vec3d3 = vec3d.subtract(vec3d2);
        float k = (float)(vec3d3.length() + 0.1);
        vec3d3 = vec3d3.normalize();
        float l = (float)Math.acos(vec3d3.y);
        float m = (float)Math.atan2(vec3d3.z, vec3d3.x);
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((1.5707964f - m) * 57.295776f));
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(l * 57.295776f));
        float n = h * 0.05f * -1.5f;
        float o = 0.2f;
        float p = MathHelper.cos(n + (float)Math.PI) * 0.2f;
        float q = MathHelper.sin(n + (float)Math.PI) * 0.2f;
        float r = MathHelper.cos(n + 0.0f) * 0.2f;
        float s = MathHelper.sin(n + 0.0f) * 0.2f;
        float t = MathHelper.cos(n + 1.5707964f) * 0.2f;
        float u = MathHelper.sin(n + 1.5707964f) * 0.2f;
        float v = MathHelper.cos(n + 4.712389f) * 0.2f;
        float w = MathHelper.sin(n + 4.712389f) * 0.2f;
        float x = k;
        float y = 0.0f;
        float z = 0.4999f;
        float aa = -1.0f + j;
        float ab = k * 2.5f + aa;
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(GuardianEntityRenderer.EXPLOSION_BEAM_TEXTURE));
        MatrixStack.Entry entry = matrixStack.peek();
        HookEntityRenderer.vertex(vertexConsumer, entry, p, x, q, 0.4999f, ab);
        HookEntityRenderer.vertex(vertexConsumer, entry, p, 0.0f, q, 0.4999f, aa);
        HookEntityRenderer.vertex(vertexConsumer, entry, r, 0.0f, s, 0.0f, aa);
        HookEntityRenderer.vertex(vertexConsumer, entry, r, x, s, 0.0f, ab);
        HookEntityRenderer.vertex(vertexConsumer, entry, t, x, u, 0.4999f, ab);
        HookEntityRenderer.vertex(vertexConsumer, entry, t, 0.0f, u, 0.4999f, aa);
        HookEntityRenderer.vertex(vertexConsumer, entry, v, 0.0f, w, 0.0f, aa);
        HookEntityRenderer.vertex(vertexConsumer, entry, v, x, w, 0.0f, ab);
        matrixStack.pop();
        super.render(lashingPotatoHookEntity, f, g, matrixStack, vertexConsumerProvider, i);
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
        double k = 0.8;
        if (dispatcher.gameOptions != null && !dispatcher.gameOptions.getPerspective().isFirstPerson() || player != MinecraftClient.getInstance().player) {
            float l = player.isInSneakingPose() ? -0.1875f : 0.0f;
            return new Vec3d(MathHelper.lerp((double)tickDelta, player.prevX, player.getX()) - e * j - d * 0.8, player.prevY + (double)player.getStandingEyeHeight() + (player.getY() - player.prevY) * (double)tickDelta - 0.45 + (double)l, MathHelper.lerp((double)tickDelta, player.prevZ, player.getZ()) - d * j + e * 0.8);
        }
        double m = 960.0 / (double)dispatcher.gameOptions.getFov().getValue().intValue();
        Vec3d vec3d = dispatcher.camera.getProjection().getPosition((float)i * 0.525f, -0.1f);
        vec3d = vec3d.multiply(m);
        vec3d = vec3d.rotateY(g * 0.5f);
        vec3d = vec3d.rotateX(-g * 0.7f);
        return new Vec3d(MathHelper.lerp(tickDelta, player.prevX, player.getX()) + vec3d.x, MathHelper.lerp((double)tickDelta, player.prevY, player.getY()) + vec3d.y + (double)player.getStandingEyeHeight(), MathHelper.lerp((double)tickDelta, player.prevZ, player.getZ()) + vec3d.z);
    }

    private static void vertex(VertexConsumer vertexConsumer, MatrixStack.Entry matrix, float x, float y, float z, float u, float v) {
        vertexConsumer.vertex(matrix, x, y, z).color(128, 255, 128, 255).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(LightmapTextureManager.MAX_LIGHT_COORDINATE).normal(0.0f, 1.0f, 0.0f);
    }

    @Override
    public Identifier getTexture(HookEntity lashingPotatoHookEntity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}
