package sypztep.sifu.common.component.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;
import sypztep.sifu.common.init.ModEnchantments;
import sypztep.sifu.common.init.ModEntityComponents;
import sypztep.sifu.common.init.ModSoundEvents;
import sypztep.sifu.common.payload.MoonStepPayload;
import sypztep.sifu.common.util.LivingEntityUtil;
import sypztep.sifu.mixin.util.LivingEntityAccessor;

public class MoonStepComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final int DEFAULT_MOONSTEP_COOLDOWN = 10;

    private final PlayerEntity obj;
    private boolean shouldRefreshMoonStep = false;
    private int moonstepCooldown = DEFAULT_MOONSTEP_COOLDOWN, lastmoonstepCooldown = DEFAULT_MOONSTEP_COOLDOWN, jumpCooldown = 10, jumpsLeft = 0, ticksInAir = 0;

    private int moonstepLevel = 0;
    private boolean hasMoonStep = false;

    public MoonStepComponent(PlayerEntity obj) {
        this.obj = obj;
    }

    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        shouldRefreshMoonStep = tag.getBoolean("ShouldRefreshMoonStep");
        moonstepCooldown = tag.getInt("MoonStepCooldown");
        lastmoonstepCooldown = tag.getInt("LastMoonStepCooldown");
        jumpCooldown = tag.getInt("JumpCooldown");
        jumpsLeft = tag.getInt("JumpsLeft");
        ticksInAir = tag.getInt("TicksInAir");
    }

    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        tag.putBoolean("ShouldRefreshMoonStep", shouldRefreshMoonStep);
        tag.putInt("MoonStepCooldown", moonstepCooldown);
        tag.putInt("LastMoonStepCooldown", lastmoonstepCooldown);
        tag.putInt("JumpCooldown", jumpCooldown);
        tag.putInt("JumpsLeft", jumpsLeft);
        tag.putInt("TicksInAir", ticksInAir);
    }

    @Override
    public void tick() {
        moonstepLevel = EnchantmentHelper.getEquipmentLevel(ModEnchantments.MOONSTEP, obj);
        hasMoonStep = moonstepLevel > 0;
        if (hasMoonStep) {
            if (!shouldRefreshMoonStep) {
                if (obj.isOnGround()) {
                    shouldRefreshMoonStep = true;
                }
            } else if (moonstepCooldown > 0) {
                moonstepCooldown--;
                if (moonstepCooldown == 0 && jumpsLeft < moonstepLevel) {
                    jumpsLeft++;
                    setMoonstepCooldown(DEFAULT_MOONSTEP_COOLDOWN);
                }
            }
            if (jumpCooldown > 0) {
                jumpCooldown--;
            }
            if (obj.isOnGround()) {
                ticksInAir = 0;
            } else {
                ticksInAir++;
            }
        } else {
            shouldRefreshMoonStep = false;
            moonstepCooldown = DEFAULT_MOONSTEP_COOLDOWN;
            jumpCooldown = 0;
            jumpsLeft = 0;
            ticksInAir = 0;
        }
    }

    @Override
    public void clientTick() {
        tick();
        if (hasMoonStep && ((LivingEntityAccessor) obj).jumpingaccessor() && canUse()) {
            use();
            addMoonStepParticles(obj);
            MoonStepPayload.send();
        }
    }

    public void sync() {
        ModEntityComponents.MOONSTEP.sync(obj);
    }

    public void setMoonstepCooldown(int moonstepCooldown) {
        this.moonstepCooldown = moonstepCooldown;
        lastmoonstepCooldown = moonstepCooldown;
    }

    public int getMoonstepCooldown() {
        return moonstepCooldown;
    }

    public int getLastmoonstepCooldown() {
        return lastmoonstepCooldown;
    }

    public int getJumpsLeft() {
        return jumpsLeft;
    }

    public void setJumpsLeft(int jumpsLeft) {
        this.jumpsLeft = jumpsLeft;
    }

    public int getMoonStepLevel() {
        return moonstepLevel;
    }

    public boolean hasMoonStep() {
        return hasMoonStep;
    }

    public boolean canUse() {
        return jumpCooldown == 0 && jumpsLeft > 0 && ticksInAir >= (obj.getWorld().isClient ? 10 : 9) && !obj.isOnGround() && LivingEntityUtil.isGroundedOrAirborne(obj);
    }

    public void use() {
        obj.jump();
        obj.setVelocity(obj.getVelocity().getX(), obj.getVelocity().getY() * 1.5, obj.getVelocity().getZ());
        obj.playSound(ModSoundEvents.ENTITY_GENERIC_AIR_JUMP, 1, 1);
        setMoonstepCooldown(DEFAULT_MOONSTEP_COOLDOWN);
        shouldRefreshMoonStep = false;
        jumpCooldown = 10;
        jumpsLeft--;
    }

    public static void addMoonStepParticles(Entity entity) {
        if (MinecraftClient.getInstance().gameRenderer.getCamera().isThirdPerson() || entity != MinecraftClient.getInstance().cameraEntity) {
            for (int i = 0; i < 8; i++) {
                entity.getWorld().addParticle(ParticleTypes.CLOUD, entity.getParticleX(1), entity.getY(), entity.getParticleZ(1), 0, 0, 0);
            }
        }
    }
}
