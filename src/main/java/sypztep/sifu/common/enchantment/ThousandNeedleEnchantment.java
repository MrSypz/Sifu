package sypztep.sifu.common.enchantment;

import com.sypztep.common.api.EnchantmentAttackHandler;
import com.sypztep.common.util.StatusEffectUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import sypztep.sifu.common.entity.projectile.NeedleEntity;
import sypztep.sifu.common.init.ModStatusEffects;

public class ThousandNeedleEnchantment extends SpecialEnchantment implements EnchantmentAttackHandler {
    public ThousandNeedleEnchantment(Properties properties) {
        super(properties);
    }
    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof HoeItem;
    }
    @Override
    public UseAction useAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public void applyOnUser(LivingEntity user, int level) {
        if (level != 0) {
            int hitcount = StatusEffectUtil.getCount(user, ModStatusEffects.THOUSANDNEEDLE, level);
            StatusEffectUtil.add(user,ModStatusEffects.THOUSANDNEEDLE, 30 + level * 12, hitcount);
        }
    }

    @Override
    public void onFinishUsing(ItemStack stack, World world, LivingEntity user, int level) {
        int amp = StatusEffectUtil.getCount(user, ModStatusEffects.THOUSANDNEEDLE, level);
        if (amp <= 0)
            return;
        if (!world.isClient()) {
            for (int i = 0; i < Math.pow(3, amp); i++) {
                NeedleEntity needleEntity = new NeedleEntity(world, user);
                needleEntity.setOwner(user);
                needleEntity.setPosition(user.getX(), user.getEyeY() + 2, user.getZ());

                double x = -Math.sin(Math.toRadians(user.getYaw())) * Math.cos(Math.toRadians(user.getPitch()));
                double y = -Math.sin(Math.toRadians(user.getPitch() + 15));
                double z = Math.cos(Math.toRadians(user.getYaw())) * Math.cos(Math.toRadians(user.getPitch()));

                double length = Math.sqrt(x * x + y * y + z * z);
                x /= length;
                y /= length;
                z /= length;

                needleEntity.setVelocity(new Vec3d(x + user.getRandom().nextGaussian(), y, z + user.getRandom().nextGaussian() / 2).multiply(level * 0.9f));
                user.getWorld().spawnEntity(needleEntity);
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> onUse(World world, PlayerEntity user, Hand hand, ItemStack stack) {
        int amp = StatusEffectUtil.getCount(user, ModStatusEffects.THOUSANDNEEDLE, getMaxLevel());
        if (amp > 0) {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(stack);
        }
        return TypedActionResult.pass(stack);
    }

    @Override
    public int maxUseTime(ItemStack stack) {
        return 30 / getMaxLevel();
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }
    @Override
    public boolean isTreasure() {
        return true;
    }
    @Override
    public boolean isAvailableForRandomSelection() {
        return false;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {

    }
    @Override
    public void applyOnTarget(LivingEntity user, Entity target, int level) {

    }
}
