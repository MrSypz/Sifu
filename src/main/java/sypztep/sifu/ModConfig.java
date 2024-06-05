package sypztep.sifu;

import eu.midnightdust.lib.config.MidnightConfig;

public class ModConfig extends MidnightConfig {
    @Entry
    public static boolean enchantedChestplatesIncreaseAirMobility = true;

    static {
        MidnightConfig.init(Sifu.MODID, ModConfig.class);
    }
}
