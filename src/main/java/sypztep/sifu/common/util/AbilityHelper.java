package sypztep.sifu.common.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import sypztep.sifu.mixin.util.ItemEntityAccessor;

import java.util.List;

public class AbilityHelper {
    private static int counts;
    public static void boxArea(LivingEntity user, DamageSource source, double range, float amount, float minamount) {
        List<LivingEntity> entities = user.getWorld().getNonSpectatingEntities(LivingEntity.class, user.getBoundingBox()
                .expand(range,3, range));
        counts = 0;
        for (LivingEntity target : entities) {
            if (target != user) {
                double distanceToEntity = target.squaredDistanceTo(user.getX(), user.getY(), user.getZ());
                double normalizedDistance = Math.sqrt(distanceToEntity) / range; // Adjust as needed for your range
                if (normalizedDistance > 0.0f)
                    counts++;
                else counts = 0;
                float damagebyArea = amount - (float) (normalizedDistance * (amount - minamount));
                target.damage(source, damagebyArea);
            }
        }
    }
    public static void boxArea(LivingEntity user, double range) {
        List<LivingEntity> entities = user.getWorld().getNonSpectatingEntities(LivingEntity.class, user.getBoundingBox()
                .expand(range,3, range));
        counts = 0;
        for (LivingEntity target : entities) {
            if (target != user) {
                double distanceToEntity = target.squaredDistanceTo(user.getX(), user.getY(), user.getZ());
                if (distanceToEntity > 0.0f)
                    counts++;
                else counts = 0;
            }
        }
    }
    public static int getEntityByArea(Entity user, double range) {
        return user.getWorld().getNonSpectatingEntities(LivingEntity.class, user.getBoundingBox()
                .expand(range, 0.25D, range)).size() - 1;
    }
    public static float getMissingHealth(LivingEntity living) {
        return living.getMaxHealth() - living.getHealth();
    }
    public static float getMissingHealth(LivingEntity living, float percentofmissinghealth) {
        return (living.getMaxHealth() - living.getHealth()) * percentofmissinghealth;
    }
    public static float getTargetMissingHealthPercent(LivingEntity living) {
        if (living != null) {
            if (living.getMaxHealth() > 0) {
                return living.getHealth() / living.getMaxHealth();
            } else {
                return 0.0f;
            }
        }
        return 0.0f;
    }
    public static boolean targetMissingHealthPercentBelow(LivingEntity living, float percentofmissinghealth) {
        // Check if the target's health is below 5%
        return living.getHealth() / living.getMaxHealth() <= percentofmissinghealth;
    }
    public static boolean targetMissingHealthPercentBelow(Entity entity, float percentofmissinghealth) {
        // Check if the target's health is below 5%
        return entity instanceof LivingEntity living && living.getHealth() / living.getMaxHealth() <= percentofmissinghealth;
    }
    public static List<ItemEntity> mergeItemEntities(List<ItemEntity> drops) {
        for (int i = drops.size() - 1; i >= 0; i--) {
            if (i < drops.size() - 1) {
                ItemEntity itemEntity = drops.get(i);
                ItemEntity other = drops.get(i + 1);
                ((ItemEntityAccessor) itemEntity).tryMergeitementity(other);
                if (itemEntity.getStack().isEmpty()) {
                    drops.remove(i);
                }
                if (other.getStack().isEmpty()) {
                    drops.remove(i + 1);
                }
            }
        }
        return drops;
    }
    public static int getHitAmount(){
        return counts;
    }
}
