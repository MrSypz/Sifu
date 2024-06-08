package sypztep.sifu.common.entity.projectile;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
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
import net.minecraft.world.World;
import sypztep.sifu.common.init.ModDamageTypes;
import sypztep.sifu.common.init.ModEntityTypes;
import sypztep.sifu.common.util.LivingEntityUtil;

public class NeedleEntity extends PersistentProjectileEntity {
	private static final ParticleEffect PARTICLE = new ItemStackParticleEffect(ParticleTypes.ITEM, Items.DRIPSTONE_BLOCK.getDefaultStack());

	public NeedleEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
		super(entityType, world);
	}
	private float damage = 0;
	public NeedleEntity(World world, LivingEntity owner, float damage) {
		super(ModEntityTypes.NEEDLE, owner, world, ItemStack.EMPTY);
		this.damage = damage;
	}

	@Override
	protected ItemStack getDefaultItemStack() {
		return ItemStack.EMPTY;
	}

	@Override
	public void tick() {
		super.tick();
		if (!getWorld().isClient && age > 400) {
			playSound(getHitSound(), 1, 1.2F / (random.nextFloat() * 0.2F + 0.9F));
			addParticles();
			discard();
		}
	}

	@Override
	protected SoundEvent getHitSound() {
		return SoundEvents.BLOCK_DRIPSTONE_BLOCK_HIT;
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		if (!getWorld().isClient) {
			Entity entity = entityHitResult.getEntity();
			if (entity instanceof EnderDragonPart part) {
				entity = part.owner;
			}
			Entity owner = getOwner();
			if (LivingEntityUtil.shouldHurt(owner, entity) && entity.damage(ModDamageTypes.create(getWorld(), ModDamageTypes.NEEDLE, this, owner), this.damage)) {
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
			playSound(getHitSound(), 1, 1.2F / (random.nextFloat() * 0.2F + 0.9F));
			addParticles();
			discard();
		}
	}

	public void addParticles() {
		((ServerWorld) getWorld()).spawnParticles(PARTICLE, getX(), getY(), getZ(), 8, getWidth() / 2, getHeight() / 2, getWidth() / 2, 0);
	}
}

