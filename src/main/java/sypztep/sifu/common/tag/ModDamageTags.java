package sypztep.sifu.common.tag;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import sypztep.sifu.Sifu;

public class ModDamageTags {
    public static final TagKey<DamageType> BYPASSES_PARRY = TagKey.of(RegistryKeys.DAMAGE_TYPE, Sifu.id("bypasses_parry"));

}
