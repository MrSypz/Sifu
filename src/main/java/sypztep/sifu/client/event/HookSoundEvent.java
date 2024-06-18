package sypztep.sifu.client.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import sypztep.sifu.client.sound.HookSoundInstance;
import sypztep.sifu.common.entity.projectile.HookEntity;

public class HookSoundEvent implements ClientEntityEvents.Load {
    @Override
    public void onLoad(Entity entity, ClientWorld world) {
        if (entity instanceof HookEntity hookEntity) {
            MinecraftClient.getInstance().getSoundManager().playNextTick(new HookSoundInstance(hookEntity));
        }
    }
}
