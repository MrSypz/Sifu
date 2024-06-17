package sypztep.sifu.common.init;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import sypztep.sifu.Sifu;
import sypztep.sifu.common.entity.projectile.HookEntity;
import sypztep.sifu.common.entity.projectile.NeedleEntity;
import sypztep.sifu.common.entity.projectile.PortalizeEntity;
import sypztep.sifu.common.entity.projectile.ShadowShardsEntity;

public class ModEntityTypes {
	public static final EntityType<NeedleEntity> NEEDLE = EntityType.Builder.<NeedleEntity>create(NeedleEntity::new, SpawnGroup.MISC).dimensions(EntityType.ARROW.getWidth(), EntityType.ARROW.getHeight()).build();
	public static final EntityType<ShadowShardsEntity> SHADOW_SHARDS  = EntityType.Builder.<ShadowShardsEntity>create(ShadowShardsEntity::new, SpawnGroup.MISC).dimensions(EntityType.ARROW.getWidth(), EntityType.ARROW.getHeight()).maxTrackingRange(32).build();
	public static final EntityType<PortalizeEntity> PORTALIZE = EntityType.Builder.<PortalizeEntity>create(PortalizeEntity::new, SpawnGroup.MISC).dimensions(0.25f,0.25f).maxTrackingRange(8).build();
	public static final EntityType<HookEntity> HOOK = EntityType.Builder.<HookEntity>create(HookEntity::new, SpawnGroup.MISC).disableSaving().disableSummon().dimensions(0.25f,0.25f).maxTrackingRange(8).trackingTickInterval(5).build();

	public static void init() {
		Registry.register(Registries.ENTITY_TYPE, Sifu.id("needle"), NEEDLE);
		Registry.register(Registries.ENTITY_TYPE, Sifu.id("shadow_shards"), SHADOW_SHARDS);
		Registry.register(Registries.ENTITY_TYPE, Sifu.id("portalize"), PORTALIZE);
		Registry.register(Registries.ENTITY_TYPE, Sifu.id("hook"), HOOK);
	}
}
