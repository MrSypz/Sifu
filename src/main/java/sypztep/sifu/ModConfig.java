package sypztep.sifu;

import eu.midnightdust.lib.config.MidnightConfig;

import java.util.HashSet;

public class ModConfig extends MidnightConfig {
    @Entry
    public static float entityHealthFactor = 2f;
    @Entry
    public static float entityAttackFactor = 1.75f;
    @Entry
    public static float entityArmorFactor = 2.5f;

    static {
        MidnightConfig.init(Sifu.MODID, ModConfig.class);
    }
}
