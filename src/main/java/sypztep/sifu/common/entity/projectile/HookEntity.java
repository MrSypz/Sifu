package sypztep.sifu.common.entity.projectile;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.EntityTrackerEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import sypztep.sifu.common.init.ModEntityTypes;
import sypztep.sifu.common.init.ModItems;
import sypztep.sifu.common.init.ModSoundEvents;
import sypztep.sifu.common.util.IHikeHook;

public class HookEntity extends ProjectileEntity {
    public static final TrackedData<Boolean> IN_BLOCK = DataTracker.registerData(HookEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Float> LENGTH = DataTracker.registerData(HookEntity.class, TrackedDataHandlerRegistry.FLOAT);

    public HookEntity(EntityType<? extends HookEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
    }

    public HookEntity(World world, PlayerEntity player) {
        super(ModEntityTypes.HOOK, world);
        this.setOwner(player);
        this.setPosition(player.getX(), player.getEyeY() - 0.1, player.getZ());
        this.refreshPositionAndAngles(player.getX(), player.getEyeY(), player.getZ(), player.getYaw(), player.getPitch());
        this.setVelocity(player, player.getPitch(), player.getYaw(), 0, 2.75f, 0);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(IN_BLOCK, false);
        builder.add(LENGTH, 0.0f);
    }

    @Override
    public boolean shouldRender(double distance) {
        return true;
    }

    @Override
    public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps) {

    }

    @Override
    public void tick() {
        super.tick();
        PlayerEntity playerEntity = this.getPlayer();
        if (playerEntity == null || !this.getWorld().isClient() && this.discardIfInvalid(playerEntity)) {
            this.discard();
            return;
        }
        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        if (hitResult.getType() != HitResult.Type.MISS) {
            this.onCollision(hitResult);
        }
        this.setPosition(hitResult.getPos());
        this.checkBlockCollision();
    }

    private boolean discardIfInvalid(PlayerEntity player) {
        if (player.isRemoved() || !player.isAlive() || !player.isHolding(ModItems.HIKEHOOK) || this.squaredDistanceTo(player) > 10000.0) {
            this.discard();
            return true;
        }
        return false;
    }

    @Override
    protected boolean canHit(Entity entity) {
        return false;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        BlockPos blockPos = blockHitResult.getBlockPos();
        BlockState blockState = this.getWorld().getBlockState(blockPos);
        int i = (int) MathHelper.clamp(50.0F * this.getVelocity().length(), 0.0F, 200.0F);
        this.setVelocity(Vec3d.ZERO);
        this.setInBlock(true);
        this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), ModSoundEvents.ENTITY_HIKE, SoundCategory.PLAYERS, 3.5f, 1.0f / (this.getWorld().getRandom().nextFloat() * 0.4f + 0.8f));
        addParticle(this, i, blockState);
        PlayerEntity playerEntity = this.getPlayer();
        if (playerEntity != null) {
            double d = playerEntity.getEyePos().subtract(blockHitResult.getPos()).length();
            this.setLength(Math.max((float) d * 0.5f - 3.0f, 1.5f));
        }
    }


    public static void addParticle(HookEntity entity, int i, BlockState state) {
        for (int j = 0; j < i; j++)
            entity.getWorld().addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, state), true, entity.getX(), entity.getY(), entity.getZ(), 0.30000001192092896, 0.30000001192092896, 0.15000000596046448);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putBoolean("in_block", this.isInBlock());
        nbt.putFloat("length", this.getLength());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        this.setInBlock(nbt.getBoolean("in_block"));
        this.setLength(nbt.getFloat("length"));
    }

    private void setInBlock(boolean inBlock) {
        this.getDataTracker().set(IN_BLOCK, inBlock);
    }

    private void setLength(float length) {
        this.getDataTracker().set(LENGTH, length);
    }

    public boolean isInBlock() {
        return this.getDataTracker().get(IN_BLOCK);
    }

    public float getLength() {
        return this.getDataTracker().get(LENGTH);
    }

    @Override
    protected Entity.MoveEffect getMoveEffect() {
        return Entity.MoveEffect.NONE;
    }

    @Override
    public void remove(Entity.RemovalReason reason) {
        this.setHookForPlayer(null);
        super.remove(reason);
    }

    @Override
    public void onRemoved() {
        this.setHookForPlayer(null);
    }

    @Override
    public void setOwner(@Nullable Entity entity) {
        super.setOwner(entity);
        this.setHookForPlayer(this);
    }

    private void setHookForPlayer(@Nullable HookEntity hookEntity) {
        PlayerEntity playerEntity = this.getPlayer();
        IHikeHook grapHookPlayer = (IHikeHook) playerEntity;
        if (playerEntity != null) {
            grapHookPlayer.setHikeHook(hookEntity);
        }
    }

    @Nullable
    public PlayerEntity getPlayer() {
        Entity entity = this.getOwner();
        return entity instanceof PlayerEntity ? (PlayerEntity) entity : null;
    }

    @Override
    public boolean canUsePortals(boolean allowVehicles) {
        return false;
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket(EntityTrackerEntry entityTrackerEntry) {
        Entity entity = this.getOwner();
        return new EntitySpawnS2CPacket(this, entityTrackerEntry, entity == null ? this.getId() : entity.getId());
    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);
        if (this.getPlayer() == null) {
            this.kill();
        }
    }
}
