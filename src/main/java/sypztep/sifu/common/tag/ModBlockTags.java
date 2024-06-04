package sypztep.sifu.common.tag;

import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import sypztep.sifu.Sifu;

public class ModBlockTags {
	public static final TagKey<Block> NETHER_ORES = TagKey.of(Registries.BLOCK.getKey(), Sifu.id("nether_ores"));
}
