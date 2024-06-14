package sypztep.sifu.common.entity.projectile;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import sypztep.sifu.common.init.ModDamageTypes;
import sypztep.sifu.common.init.ModEntityTypes;
import sypztep.sifu.common.util.LivingEntityUtil;

public class ShadowShardsEntity extends PersistentProjectileEntity {
    private static final ParticleEffect PARTICLE = new ItemStackParticleEffect(ParticleTypes.ITEM, Items.SCULK_CATALYST.getDefaultStack());
    private final int upwardMovementDuration; // Duration to move upwards
    private float damage = 0;
    private LivingEntity target = null;
    public ShadowShardsEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
        this.upwardMovementDuration = 40; // Move up for 2 seconds (40 ticks)
    }

    public ShadowShardsEntity(World world, LivingEntity owner, float damage, LivingEntity target) {
        super(ModEntityTypes.SHADOW_SHARDS, owner, world, ItemStack.EMPTY, owner.getActiveItem());
        this.upwardMovementDuration = 40; // Move up for 2 seconds (40 ticks)
        this.damage = damage;
        this.target = target;
    }
    @Override
    protected ItemStack getDefaultItemStack() {
        return ItemStack.EMPTY;
    }

    @Override
    protected SoundEvent getHitSound() {
        return SoundEvents.BLOCK_SCULK_SHRIEKER_BREAK;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (!getWorld().isClient) {
            Entity entity = entityHitResult.getEntity();
            if (entity instanceof EnderDragonPart part) {
                entity = part.owner;
            }
            Entity owner = getOwner();
            if (LivingEntityUtil.shouldHurt(owner, entity) && entity.damage(ModDamageTypes.create(getWorld(), ModDamageTypes.NEEDLE, this, owner), damage)) {
                playSound(getHitSound(), 1, 1.2F / (random.nextFloat() * 0.2F + 0.9F));
                addParticles();
                discard();
            }
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        BlockState state = getWorld().getBlockState(blockHitResult.getBlockPos());
        state.onProjectileHit(getWorld(), state, blockHitResult, this);
        if (!getWorld().isClient) {
            playSound(getHitSound(), 2, 1.2F / (random.nextFloat() * 0.2F + 0.9F));
            addParticles();
            discard();
        }
    }

    public void addParticles() {
        ((ServerWorld) getWorld()).spawnParticles(PARTICLE, getX(), getY(), getZ(), 8, getWidth() / 2, getHeight() / 2, getWidth() / 2, 0);
    }


    @Override
    public void tick() {
        super.tick();
        this.age++; // Increment age each tick
        this.getWorld().addParticle(ParticleTypes.SCULK_SOUL,this.getX(), this.getY(), this.getZ(), this.getVelocity().getX(), this.getVelocity().getY(), this.getVelocity().getZ());

        if (this.age < upwardMovementDuration) {
            setVelocity(0, 0.125, 0);
        } else if (this.age < 80 && !getWorld().isClient) { // 2 more seconds for hovering
            setNoGravity(true);
            setVelocity(0, 0, 0);
        } else if (!this.inGround && this.isAlive() && !getWorld().isClient()) {
            if (this.target == null || !this.target.isAlive()) {
                this.target = findNearestTarget();
            }

            if (this.target != null) {
                double speed = this.getVelocity().length();
                Vec3d toTarget = this.target.getPos().add(0.0, 0.7, 0.0).subtract(this.getPos());
                Vec3d dirVelocity = this.getVelocity().normalize();
                Vec3d dirToTarget = toTarget.normalize();
                double dotProduct = dirVelocity.dotProduct(dirToTarget);

                // Ensure dotProduct is within [-1, 1] range to avoid NaN
                if (dotProduct >= -1 && dotProduct <= 1) {
                    double angle = Math.acos(dotProduct);
                    double newSpeed = 0.9 * speed + 0.1399999999999;
                    Vec3d newVelocity;
                    if (angle < 0.12) {
                        newVelocity = dirVelocity.multiply(newSpeed);
                    } else {
                        Vec3d newDir = dirVelocity.multiply((angle - 0.16) / angle).add(dirToTarget.multiply(0.16 / angle));
                        newDir = newDir.normalize();
                        newVelocity = newDir.multiply(newSpeed);
                    }
                    this.setVelocity(newVelocity);
                }
            }
        } else if (age > 600 && !getWorld().isClient) {
            discard();
        }
    }
    private LivingEntity findNearestTarget() {
        double nearestDistance = Double.MAX_VALUE;
        LivingEntity nearestEntity = null;

        for (LivingEntity livingEntities : getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(32))) {
            if (livingEntities != this.getOwner() && livingEntities.isAlive() && !livingEntities.isInvisible()) {
                if (livingEntities instanceof TameableEntity tameable && tameable.getOwner() == this.getOwner()) {
                    continue; //skip if target is pet
                }
                double distance = livingEntities.squaredDistanceTo(this);
                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    nearestEntity = livingEntities;
                }
            }
        }
        return nearestEntity;
    }
    @Override
    protected float getDragInWater() {
        return 0.99f;
    }
}
