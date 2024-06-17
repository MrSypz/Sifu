package sypztep.sifu.client.sound;

import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import sypztep.sifu.common.entity.projectile.HookEntity;
import sypztep.sifu.common.init.ModSoundEvents;

public class HookSoundInstance extends MovingSoundInstance {
    private final HookEntity hookEntity;

    public HookSoundInstance(HookEntity hookEntity) {
        super(ModSoundEvents.ENTITY_FLYHOOK, SoundCategory.MASTER, Random.create());
        this.hookEntity = hookEntity;
        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = 1.0F;
        this.pitch = 1.0F + this.random.nextFloat() / 20.0F;
        this.x = (float) hookEntity.getX();
        this.y = (float) hookEntity.getY();
        this.z = (float) hookEntity.getZ();
    }

    @Override
    public boolean canPlay() {
        return !this.hookEntity.isSilent();
    }

    @Override
    public boolean shouldAlwaysPlay() {
        return true;
    }

    @Override
    public void tick() {

        if (this.hookEntity.getVelocity().length() >= 0.0) {
            this.volume =  MathHelper.clamp(this.volume + 0.06F, 0.0F, 1.0F);
        } else {
            this.volume = 0.0F;
        }
        if (this.hookEntity.isRemoved() || this.hookEntity.isInBlock()) {
            this.setDone();
        } else  {
            this.x = (float) this.hookEntity.getX();
            this.y = (float) this.hookEntity.getY();
            this.z = (float) this.hookEntity.getZ();
        }
    }
}
