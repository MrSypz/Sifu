package sypztep.sifu;

import eu.midnightdust.lib.config.MidnightConfig;

public class ModConfig extends MidnightConfig {
    static {
        MidnightConfig.init(Sifu.MODID, ModConfig.class);
    }
}
