package sypztep.sifu.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import sypztep.sifu.common.entity.projectile.HookEntity;
import sypztep.sifu.common.init.ModSoundEvents;
import sypztep.sifu.common.util.IHikeHook;

public class GraphookItem
        extends Item {
    public GraphookItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        IHikeHook grapHookPlayer = (IHikeHook) user;
        HookEntity hookEntity = grapHookPlayer.getHikeHook();
        if (hookEntity != null) {
            GraphookItem.rellback(world, user, hookEntity);
        } else {
            if (!world.isClient) {
                itemStack.damage(1, user, LivingEntity.getSlotForHand(hand));
            }
            this.shot(world, user);
        }
        return TypedActionResult.success(itemStack, world.isClient);
    }

    private void shot(World world, PlayerEntity player) {
        if (!world.isClient) {
            world.spawnEntity(new HookEntity(world, player));
        }
        player.incrementStat(Stats.USED.getOrCreateStat(this));
        world.playSound(null, player.getX(), player.getY(), player.getZ(), ModSoundEvents.ENTITY_SHOT, SoundCategory.NEUTRAL, 1.0f, 1.6f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
        player.emitGameEvent(GameEvent.ITEM_INTERACT_START);
    }

    private static void rellback(World world, PlayerEntity player, HookEntity lashingPotatoHookEntity) {
        IHikeHook grapHookPlayer = (IHikeHook) player;
        if (!world.isClient()) {
            lashingPotatoHookEntity.discard();
            grapHookPlayer.setHikeHook(null);
        }
        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_LEASH_KNOT_PLACE, SoundCategory.NEUTRAL, 1.0f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
        player.emitGameEvent(GameEvent.ITEM_INTERACT_FINISH);
    }
}
