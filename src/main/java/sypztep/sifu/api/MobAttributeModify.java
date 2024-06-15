package sypztep.sifu.api;

import net.minecraft.entity.mob.MobEntity;

public class MobAttributeModify {
    public static void setMobHealthMultiplier(MobEntity mobEntity, float multiplier) {
        ((EntityAccess) mobEntity).setMobHealthMultiplier(multiplier);
    }
    public static float getMobHealthMultiplier(MobEntity mobEntity) {
        return ((EntityAccess) mobEntity).getMobHealthMultiplier();
    }
}
