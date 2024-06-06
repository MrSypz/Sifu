package sypztep.sifu.common.enchantment;

import com.sypztep.common.util.EnchantmentUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import sypztep.sifu.common.init.ModEnchantments;

public class ParryEnchantment extends SpecialEnchantment {
    public ParryEnchantment(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return super.canAccept(other)
                && other != (Enchantments.SWEEPING_EDGE)
                && other != Enchantments.FIRE_ASPECT
                && other != (ModEnchantments.CARVE);
    }

    @Override
    public void onFinishUsing(ItemStack stack, World world, LivingEntity user, int level) {
        if (user instanceof PlayerEntity player)
            player.getItemCooldownManager().set(stack.getItem(), 120 + 20 * getMaxLevel());
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity player) {
            if (EnchantmentHelper.getLevel(ModEnchantments.PARRY, stack) > 0) {
                if (!player.getItemCooldownManager().isCoolingDown(stack.getItem())) {
                    player.getItemCooldownManager().set(
                            stack.getItem(),
                            (int) ((30 * (1f - ((float) remainingUseTicks / (float) maxUseTime(stack)))))
                    );
                }
            }
        }
    }

    @Override // max 9 sec
    public int maxUseTime(ItemStack stack) {
        return EnchantmentUtil.hasEnchantment(ModEnchantments.RIPOSTE,stack) ? 10 * getMaxLevel() : 60 + 40 * getMaxLevel();
    }

    @Override
    public UseAction useAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    @Override
    public TypedActionResult<ItemStack> onUse(World world, PlayerEntity user, Hand hand, ItemStack stack) {
        user.setCurrentHand(hand);
        return TypedActionResult.consume(stack);
    }
}
