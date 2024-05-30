package sypztep.sifu;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import sypztep.sifu.common.init.ModEnchantments;
import sypztep.sifu.common.init.ModStatusEffects;

import java.util.List;

public class Sifu implements ModInitializer {
    public static final String MODID = "sifu";
    public static Identifier id (String path) {
        return new Identifier(MODID,path);
    }
    @Override
    public void onInitialize() {
        ModEnchantments.init();
        ModStatusEffects.initEffects();
    }
}
