package sypztep.sifu.common.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;

public class RaytraceUtil {

    public static LivingEntity raytraceForAimlock(LivingEntity player) {
        float range = 32.0f;
        Entity entity;
        Vec3d eyeVec = player.getCameraPosVec(0f);
        Vec3d dir = player.getRotationVec(0f);
        Vec3d rayEnd = eyeVec.add(dir.multiply(range));

        // Perform entity raycast
        HitResult hitResult = findCrosshairTarget(player, range, rayEnd, 0f);
        if (hitResult instanceof EntityHitResult entityHitResult) {
            entity = entityHitResult.getEntity();
        } else {
            entity = null;
        }

        if (entity instanceof LivingEntity livingEntity) {
            return livingEntity;
        }

        return null; // No entity hit
    }

    private static HitResult findCrosshairTarget(Entity camera, double blockInteractionRange, Vec3d entityInteractionRange, float tickDelta) {
        double maxRange = Math.max(blockInteractionRange, entityInteractionRange.length());
        double maxRangeSquared = MathHelper.square(maxRange);
        Vec3d cameraPosVec = camera.getCameraPosVec(tickDelta);
        HitResult hitResult = camera.raycast(maxRange, tickDelta, false);
        double hitDistanceSquared = hitResult.getPos().squaredDistanceTo(cameraPosVec);

        if (hitResult.getType() != HitResult.Type.MISS) {
            maxRangeSquared = hitDistanceSquared;
            maxRange = Math.sqrt(maxRangeSquared);
        }

        Vec3d cameraRotationVec = camera.getRotationVec(tickDelta);
        Vec3d rayEnd = cameraPosVec.add(cameraRotationVec.multiply(maxRange));
        Box boundingBox = camera.getBoundingBox().stretch(cameraRotationVec.multiply(maxRange)).expand(1.0, 1.0, 1.0);

        EntityHitResult entityHitResult = ProjectileUtil.raycast(
                camera,
                cameraPosVec,
                rayEnd,
                boundingBox,
                entity -> !entity.isSpectator() && entity.canHit(),
                maxRangeSquared
        );

        if (entityHitResult != null && entityHitResult.getPos().squaredDistanceTo(cameraPosVec) < hitDistanceSquared) {
            return ensureTargetInRange(entityHitResult, cameraPosVec, entityInteractionRange.length());
        }

        return ensureTargetInRange(hitResult, cameraPosVec, blockInteractionRange);
    }

    private static HitResult ensureTargetInRange(HitResult hitResult, Vec3d cameraPos, double interactionRange) {
        Vec3d hitPos = hitResult.getPos();
        if (!hitPos.isInRange(cameraPos, interactionRange)) {
            Direction direction = Direction.getFacing(hitPos.x - cameraPos.x, hitPos.y - cameraPos.y, hitPos.z - cameraPos.z);
            return BlockHitResult.createMissed(hitPos, direction, BlockPos.ofFloored(hitPos));
        }
        return hitResult;
    }
}
