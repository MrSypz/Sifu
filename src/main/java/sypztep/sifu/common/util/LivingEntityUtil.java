package sypztep.sifu.common.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.player.PlayerEntity;

public class LivingEntityUtil {
    public static boolean shouldHurt(Entity attacker, Entity hitEntity) {
        if (attacker == null || hitEntity == null) {
            return true;
        }
        if (attacker == hitEntity || attacker.getVehicle() == hitEntity) {
            return false;
        }
        if (hitEntity instanceof PlayerEntity hitPlayer && attacker instanceof PlayerEntity attackingPlayer) {
            return attackingPlayer.shouldDamagePlayer(hitPlayer);
        } else if (hitEntity instanceof Ownable ownable) {
            return shouldHurt(attacker, ownable.getOwner());
        }
        return true;
    }
    public static boolean isGroundedOrAirborne(LivingEntity living, boolean allowWater) {
        if (living instanceof PlayerEntity player && player.getAbilities().flying) {
            return false;
        }
        if (!allowWater) {
            if (living.isTouchingWater() || living.isSwimming()) {
                return false;
            }
        }
        return !living.isFallFlying() && living.getVehicle() == null && !living.isClimbing();
    }

    public static boolean isGroundedOrAirborne(LivingEntity living) {
        return isGroundedOrAirborne(living, false);
    }
}
