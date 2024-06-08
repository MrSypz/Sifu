package sypztep.sifu.common.util;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class RaytraceUtil {
    public static LivingEntity raytraceForAimlock(LivingEntity player) {
        float range = 32.0f;
        Entity entity;
        Vec3d eyeVec = player.getCameraPosVec(0f);
        Vec3d dir = player.getRotationVec(0f);
        Vec3d rayEnd = eyeVec.add(dir.x * range, dir.y * range, dir.z * range);

        // Perform entity raycast
        HitResult hitResult = findCrosshairTarget(player, 32, rayEnd, range);
        if (hitResult instanceof EntityHitResult entityHitResult) {
            entity = entityHitResult.getEntity();
        } else {
            entity = null;
        }

        if (entity instanceof LivingEntity l) {
            return l;
        }

        return null; // No entity hit
    }
    private static HitResult findCrosshairTarget(Entity camera, double blockInteractionRange, Vec3d entityInteractionRange, float tickDelta) {
        double d = Math.max(blockInteractionRange, entityInteractionRange.length());
        double e = MathHelper.square((double)d);
        Vec3d vec3d = camera.getCameraPosVec(tickDelta);
        HitResult hitResult = camera.raycast(d, tickDelta, false);
        double f = hitResult.getPos().squaredDistanceTo(vec3d);
        if (hitResult.getType() != HitResult.Type.MISS) {
            e = f;
            d = Math.sqrt(e);
        }
        Vec3d vec3d2 = camera.getRotationVec(tickDelta);
        Vec3d vec3d3 = vec3d.add(vec3d2.x * d, vec3d2.y * d, vec3d2.z * d);
        float g = 1.0f;
        Box box = camera.getBoundingBox().stretch(vec3d2.multiply(d)).expand(1.0, 1.0, 1.0);
        EntityHitResult entityHitResult = ProjectileUtil.raycast((Entity)camera, (Vec3d)vec3d, (Vec3d)vec3d3, (Box)box, entity -> !entity.isSpectator() && entity.canHit(), (double)e);
        if (entityHitResult != null && entityHitResult.getPos().squaredDistanceTo(vec3d) < f) {
            return ensureTargetInRange((HitResult)entityHitResult, vec3d, entityInteractionRange.length());
        }
        return ensureTargetInRange(hitResult, vec3d, blockInteractionRange);
    }
    private static HitResult ensureTargetInRange(HitResult hitResult, Vec3d cameraPos, double interactionRange) {
        Vec3d vec3d = hitResult.getPos();
        if (!vec3d.isInRange((Position)cameraPos, interactionRange)) {
            Vec3d vec3d2 = hitResult.getPos();
            Direction direction = Direction.getFacing((double)(vec3d2.x - cameraPos.x), (double)(vec3d2.y - cameraPos.y), (double)(vec3d2.z - cameraPos.z));
            return BlockHitResult.createMissed((Vec3d)vec3d2, (Direction)direction, (BlockPos)BlockPos.ofFloored((Position)vec3d2));
        }
        return hitResult;
    }
}