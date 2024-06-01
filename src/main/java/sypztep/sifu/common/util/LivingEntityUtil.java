package sypztep.sifu.common.util;

import net.minecraft.entity.Entity;
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
}
