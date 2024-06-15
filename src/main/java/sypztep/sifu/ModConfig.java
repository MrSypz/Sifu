package sypztep.sifu;

import eu.midnightdust.lib.config.MidnightConfig;

import java.util.HashSet;

public class ModConfig extends MidnightConfig {
    static {
        MidnightConfig.init(Sifu.MODID, ModConfig.class);
    }
}
