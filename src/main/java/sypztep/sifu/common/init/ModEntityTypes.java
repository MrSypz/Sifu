package sypztep.sifu.common.init;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import sypztep.sifu.Sifu;
import sypztep.sifu.common.entity.projectile.NeedleEntity;
import sypztep.sifu.common.entity.projectile.ShadowShardsEntity;

public class ModEntityTypes {
	public static final EntityType<NeedleEntity> NEEDLE = EntityType.Builder.<NeedleEntity>create(NeedleEntity::new, SpawnGroup.MISC).dimensions(EntityType.ARROW.getWidth(), EntityType.ARROW.getHeight()).build();
	public static final EntityType<ShadowShardsEntity> SHADOW_SHARDS  = EntityType.Builder.<ShadowShardsEntity>create(ShadowShardsEntity::new, SpawnGroup.MISC).dimensions(EntityType.ARROW.getWidth(), EntityType.ARROW.getHeight()).build();

	public static void init() {
		Registry.register(Registries.ENTITY_TYPE, Sifu.id("needle"), NEEDLE);
		Registry.register(Registries.ENTITY_TYPE, Sifu.id("shadow_shards"), SHADOW_SHARDS);
	}
}
