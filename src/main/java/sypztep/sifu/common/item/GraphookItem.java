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
import sypztep.sifu.api.IGrapHook;
import sypztep.sifu.common.entity.projectile.HookEntity;

public class GraphookItem
        extends Item {
    public GraphookItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        IGrapHook grapHookPlayer = (IGrapHook) user;
        HookEntity hookEntity = grapHookPlayer.getGrapHook();
        if (hookEntity != null) {
            GraphookItem.how(world, user, hookEntity);
        } else {
            if (!world.isClient) {
                itemStack.damage(1, user, LivingEntity.getSlotForHand(hand));
            }
            this.what(world, user);
        }
        return TypedActionResult.success(itemStack, world.isClient);
    }

    private void what(World world, PlayerEntity player) {
        if (!world.isClient) {
            world.spawnEntity(new HookEntity(world, player));
        }
        player.incrementStat(Stats.USED.getOrCreateStat(this));
        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_FISHING_BOBBER_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
        player.emitGameEvent(GameEvent.ITEM_INTERACT_START);
    }

    private static void how(World world, PlayerEntity player, HookEntity lashingPotatoHookEntity) {
        IGrapHook grapHookPlayer = (IGrapHook) player;
        if (!world.isClient()) {
            lashingPotatoHookEntity.discard();
            grapHookPlayer.setGrapHook(null);
        }
        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_FISHING_BOBBER_RETRIEVE, SoundCategory.NEUTRAL, 1.0f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
        player.emitGameEvent(GameEvent.ITEM_INTERACT_FINISH);
    }
}
