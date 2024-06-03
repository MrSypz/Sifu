package sypztep.sifu.common.enchantment;

import com.sypztep.common.api.EnchantmentAttackHandler;
import com.sypztep.common.util.StatusEffectUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import sypztep.sifu.common.init.ModEnchantments;
import sypztep.sifu.common.init.ModSoundEvents;
import sypztep.sifu.common.init.ModStatusEffects;
import sypztep.sifu.common.payload.CarveSoulPayload;
import sypztep.sifu.common.util.AbilityHelper;
import sypztep.sifu.common.util.RenderHealAmount;
import sypztep.sifu.mixin.util.InGameHudAccessor;

public class CarveEnchantment extends SpecialEnchantment implements EnchantmentAttackHandler {
    private boolean soundPlayed = false;
    private static float HealAmount;

    public CarveEnchantment(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return super.canAccept(other) && other != Enchantments.SHARPNESS && other != ModEnchantments.PARRY;
    }

    public static float getHealAmount() {
        return HealAmount;
    }

    //todo: spawn a razer beam instead a flame particle
    @Override
    public void onFinishUsing(ItemStack stack, World world, LivingEntity user, int level) {
        int amp = StatusEffectUtil.getCount(user, ModStatusEffects.STALWART, level);
        double value = user.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        HealAmount = amp + AbilityHelper.getMissingHealth(user, 0.25f);
        if (StatusEffectUtil.dontHave(ModStatusEffects.STALWART_COOLDOWN, user)) {
            AbilityHelper.boxArea(user, user.getWorld().getDamageSources().playerAttack((PlayerEntity) user), amp, (float) (value * 1.5f), 1.0f); //150% Damage base on player attack damage
            user.heal(HealAmount);
            if (user.getWorld().isClient()) CarveSoulPayload.send(amp);
            carvesoulParticle(user, amp);
            user.removeStatusEffect(ModStatusEffects.STALWART);
            StatusEffectUtil.add(user, ModStatusEffects.STALWART_COOLDOWN, 240 - (level * 2));
        }
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
    }

    @Override
    public int maxUseTime(ItemStack stack) {
        return 20;
    }

    public static void carvesoulParticle(Entity entity, int radius) {
        for (int i = 0; i <= 360; i += 8) {
            double circle = Math.toRadians(i);
            double x = radius * 0.2 * Math.cos(circle) * 1.5d;
            double z = radius * 0.2 * Math.sin(circle) * 1.5d;

            double xVec = x * 0.25d;
            double zVec = z * 0.25d;

            entity.getWorld().addParticle(ParticleTypes.SOUL, entity.getX() + x, entity.getEyeY() + z, entity.getZ() + z, xVec, 0, zVec);
            entity.getWorld().addParticle(ParticleTypes.SOUL, entity.getX() + x, entity.getEyeY() + (z * -1), entity.getZ() + z, xVec, 0, zVec);
            entity.getWorld().addParticle(ParticleTypes.SOUL, entity.getX() + x, entity.getEyeY() + ((z * 2) * -1), entity.getZ() + z, xVec, 0, zVec);
            entity.getWorld().addParticle(ParticleTypes.SOUL, entity.getX() + x, entity.getEyeY() + ((z * 2)), entity.getZ() + z, xVec, 0, zVec);
        }
        entity.playSound(ModSoundEvents.ITEM_STALWART, 1, (float) (1 + ((LivingEntity) entity).getRandom().nextGaussian() / 20.0));
    }

    @Override
    public UseAction useAction(ItemStack stack) {
        return UseAction.CROSSBOW;
    }

    @Override
    public TypedActionResult<ItemStack> onUse(World world, PlayerEntity user, Hand hand, ItemStack stack) {
        if (StatusEffectUtil.dontHave(ModStatusEffects.STALWART_COOLDOWN, user) && StatusEffectUtil.have(ModStatusEffects.STALWART, user)) {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(stack);
        }
        return TypedActionResult.pass(stack);
    }

    @Override
    public void applyOnTarget(LivingEntity user, Entity target, int level) {
        int carvecount = 0;
        if (level != 0 && target.isAttackable() && !target.getWorld().isClient && user.distanceTo(target) <= 6 && target instanceof LivingEntity living) {
            if (living.getArmor() > 0) {
                StatusEffectInstance markInstance = living.getStatusEffect(ModStatusEffects.CARVE);

                if (markInstance != null) {
                    carvecount = Math.min(markInstance.getAmplifier() + 1, level);

                    if (carvecount == level && !soundPlayed) {
                        target.playSound(ModSoundEvents.ITEM_CARVE, 1, (float) (1 + living.getRandom().nextGaussian() / 10.0));
                        soundPlayed = true; // Set the flag to true once the sound is played
                        StatusEffectUtil.add(living, StatusEffects.SLOWNESS, 40 + level * 4, level);
                        ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.ENCHANTED_HIT, target.getX(), target.getBodyY(0.5D), target.getZ(), 18, 0.3, 0.6, 0.3, 0.01D);
                    }
                } else soundPlayed = false;

                StatusEffectUtil.add(living, ModStatusEffects.CARVE, 40 + level * 4, carvecount);
                ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.SCULK_SOUL, target.getX(), target.getBodyY(0.5D), target.getZ(), 18, 0.3, 0.6, 0.3, 0.01D);
            }
        }
    }

    @Override
    public void applyOnUser(LivingEntity user, int level) {
        if (level != 0) {
            int hitcount = StatusEffectUtil.getCount(user, ModStatusEffects.STALWART, level);
            StatusEffectUtil.add(user, ModStatusEffects.STALWART, 20 + level * 12, hitcount);
        }
    }
}
