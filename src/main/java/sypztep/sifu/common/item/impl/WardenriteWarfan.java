package sypztep.sifu.common.item.impl;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import sypztep.sifu.Sifu;
import sypztep.sifu.common.entity.projectile.PortalizeEntity;
import sypztep.sifu.common.entity.projectile.ShadowShardsEntity;
import sypztep.sifu.common.item.Warfan;
import sypztep.sifu.common.util.RaytraceUtil;

import java.util.List;

public class WardenriteWarfan extends Warfan {
    private static final String SOUL_KEY = Sifu.MODID + "Soul";
    private static final int MAX_SOUL_POINTS = 20;
    private static final int PARTICLE_COUNT = 12;
    private static final int SHARD_COUNT = 9;

    public WardenriteWarfan(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }


    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        NbtCompound nbt = getNbtCompound(stack);
        int soulCount = nbt.getInt(SOUL_KEY);

        tooltip.add(Text.literal("Soul Collector: ").formatted(Formatting.YELLOW)
                .append(Text.literal(String.valueOf(soulCount)).formatted(Formatting.RED))
                .formatted(Formatting.ITALIC));
    }

    @Override
    public void onCraftByPlayer(ItemStack stack, World world, PlayerEntity player) {
        stack.apply(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT, comp -> comp.apply(itemnbt -> itemnbt.putInt(SOUL_KEY, 0)));
        super.onCraftByPlayer(stack, world, player);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        NbtCompound nbt = getNbtCompound(stack);

        if (nbt.getInt(SOUL_KEY) <= 0) {
            return TypedActionResult.pass(user.getStackInHand(hand));
        }

        LivingEntity target = RaytraceUtil.raytraceForAimlock(user);

        if (world.isClient()) {
            targetParticle(world, target);
            float f = (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.2F + 1.0F;
            user.playSound(SoundEvents.BLOCK_TRIAL_SPAWNER_SPAWN_ITEM_BEGIN, 1f, f);
        } else {
            spawnShards(world, user);
            user.getItemCooldownManager().set(stack.getItem(), 10);
            decreaseSoulPoints(stack, 1);
            stack.damage(1, user, LivingEntity.getSlotForHand(hand));
        }

        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.distanceTo(target) <= 7.5 && target.isDead() && target.deathTime < 40) {
            addSoulPoints(stack, 1);
        }
        return super.postHit(stack, target, attacker);
    }

    private void targetParticle(World world, LivingEntity target) {
        if (target != null) {
            for (int i = 0; i < PARTICLE_COUNT; i++) {
                Vec3d vec3d = new Vec3d(
                        target.getX() + (MathHelper.sin((target.age) * 0.75f + i * 45) * target.getWidth() * 1.2),
                        target.getBodyY(0.5f),
                        target.getZ() + (MathHelper.cos((target.age) * 0.75f + i * 45) * target.getWidth() * 1.2)
                );
                world.addParticle(ParticleTypes.SCULK_SOUL, vec3d.getX(), vec3d.getY(), vec3d.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    private void spawnShards(World world, PlayerEntity user) {
        for (int i = 0; i < SHARD_COUNT; i++) {
            ShadowShardsEntity shardsEntity = new ShadowShardsEntity(world, user, 6, null);
            shardsEntity.setOwner(user);
            PortalizeEntity entity = PortalizeEntity.create(world, shardsEntity, user);
            double angle = 2 * Math.PI * i / SHARD_COUNT;
            double radius = 2.0;
            double xOffset = radius * Math.cos(angle);
            double zOffset = radius * Math.sin(angle);
            entity.setPosition(user.getX() + xOffset, user.getY(), user.getZ() + zOffset);
            shardsEntity.setPosition(user.getX() + xOffset, user.getY(), user.getZ() + zOffset);
            world.spawnEntity(entity);
        }
    }

    public static void addSoulPoints(ItemStack stack, int points) {
        updateSoulPoints(stack, points);
    }

    public static void decreaseSoulPoints(ItemStack stack, int points) {
        updateSoulPoints(stack, -points);
    }

    private static void updateSoulPoints(ItemStack stack, int points) {
        NbtCompound nbt = getNbtCompound(stack);
        int currentPoints = nbt.getInt(SOUL_KEY);
        int newPoints = Math.min(currentPoints + points, MAX_SOUL_POINTS);
        stack.apply(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT, comp -> comp.apply(itemnbt -> {
            itemnbt.putInt(SOUL_KEY, newPoints);
        }));
    }

    public static NbtCompound getNbtCompound(ItemStack stack) {
        NbtCompound value = new NbtCompound();
        @Nullable var data = stack.get(DataComponentTypes.CUSTOM_DATA);
        if (data != null)
            value = data.copyNbt();
        return value;
    }
}
