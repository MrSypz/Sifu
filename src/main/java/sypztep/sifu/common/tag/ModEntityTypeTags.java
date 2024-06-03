package sypztep.sifu.common.tag;

import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import sypztep.sifu.Sifu;

public class ModEntityTypeTags {
	public static final TagKey<EntityType<?>> VEIL_IMMUNE = TagKey.of(Registries.ENTITY_TYPE.getKey(), Sifu.id("veil_immune"));
}
