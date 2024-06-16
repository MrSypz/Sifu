package sypztep.sifu;

import eu.midnightdust.lib.config.MidnightConfig;

import java.util.ArrayList;
import java.util.List;

public class ModConfig extends MidnightConfig {
    @Entry public static boolean enableHealthModify = true;
    @Entry public static double maxFactorHealth = 4.0D;
    @Entry public static double maxFactorDamage = 3.5D;
    @Entry public static double maxFactorProtection = 3.5D;
    @Entry public static double maxFactorSpeed = 1.15D;
    @Entry public static float maxXPFactor = 1.5F;
    @Entry public static boolean allowRandomValues = true;
    @Entry public static float randomFactor = 10;
    @Entry public static double startingFactor = 1.0D;
    @Entry public static double creeperExplosionFactor = 2.5D;

    public static ArrayList<String> excludedEntity = new ArrayList<>(List.of("the_bumblezone:cosmic_crystal_entity"));


    static {
        MidnightConfig.init(Sifu.MODID, ModConfig.class);
    }
}
