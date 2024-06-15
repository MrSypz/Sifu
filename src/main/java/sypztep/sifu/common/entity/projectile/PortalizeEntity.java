package sypztep.sifu.common.entity.projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;
import sypztep.sifu.common.init.ModEntityTypes;

public class PortalizeEntity extends ProjectileEntity {
    private PersistentProjectileEntity customProjectile;
    private long spawnProjectileAfterTicks;


    public PortalizeEntity(EntityType<? extends PortalizeEntity> entityType, World world) {
        super(entityType, world);
        this.noClip = true;
    }
    public PortalizeEntity(World world, PersistentProjectileEntity projectile,LivingEntity owner) {
        super(ModEntityTypes.PORTALIZE, world);
        this.noClip = true;
        this.customProjectile = projectile;
    }
    public static PortalizeEntity create(World world, PersistentProjectileEntity projectile,LivingEntity owner) {
        PortalizeEntity portalizeEntity = new PortalizeEntity(world,projectile,owner);
        portalizeEntity.setOwner(owner);
        portalizeEntity.spawnProjectileAfterTicks = world.random.nextBetween(30, 50);
        return portalizeEntity;
    }


    public void tick() {
        super.tick();
        if (this.getWorld().isClient) {
            this.tickClient();
        } else {
            this.tickServer();
        }
    }

    private void tickServer() {
        if ((long)this.age == this.spawnProjectileAfterTicks - 36L) {
            this.getWorld().playSound(null, this.getBlockPos(), SoundEvents.BLOCK_TRIAL_SPAWNER_ABOUT_TO_SPAWN_ITEM, SoundCategory.NEUTRAL);
        }

        if ((long)this.age >= this.spawnProjectileAfterTicks) {
            this.spawnProjectile();
            this.kill();
        }

    }

    private void tickClient() {
        this.setNoGravity(true);
        if (this.getWorld().getTime() % 5L == 0L) {
            this.addParticles();
        }

    }

    private void spawnProjectile() {
        World world = this.getWorld();
        if (customProjectile != null) {
            ProjectileEntity portalize = new PortalizeEntity(world,customProjectile, (LivingEntity) this.getOwner()).getCustomProjectile();
            customProjectile.setOwner(portalize.getOwner());
            world.spawnEntity(customProjectile);
            world.syncWorldEvent(WorldEvents.OMINOUS_ITEM_SPAWNER_SPAWNS_ITEM, this.getBlockPos(), 1);
            world.emitGameEvent(customProjectile, GameEvent.ENTITY_PLACE, this.getPos());
        }
    }
    public void addParticles() {
        Vec3d vec3d = this.getPos();
        int i = this.random.nextBetween(1, 3);

        for(int j = 0; j < i; ++j) {
            Vec3d vec3d2 = new Vec3d(this.getX() + 0.4 * (this.random.nextGaussian() - this.random.nextGaussian()), this.getY() + 0.4 * (this.random.nextGaussian() - this.random.nextGaussian()), this.getZ() + 0.4 * (this.random.nextGaussian() - this.random.nextGaussian()));
            Vec3d vec3d3 = vec3d.relativize(vec3d2);
            this.getWorld().addParticle(ParticleTypes.OMINOUS_SPAWNING, vec3d.getX(), vec3d.getY(), vec3d.getZ(), vec3d3.getX(), vec3d3.getY(), vec3d3.getZ());
        }
    }

    public PersistentProjectileEntity getCustomProjectile() {
        return customProjectile;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {

    }

}
