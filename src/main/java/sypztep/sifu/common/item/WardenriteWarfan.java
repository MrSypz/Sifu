package sypztep.sifu.common.item;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sypztep.sifu.Sifu;
import sypztep.sifu.common.entity.projectile.ShadowShardsEntity;
import sypztep.sifu.common.util.ItemDescriptionHandler;
import sypztep.sifu.common.util.RaytraceUtil;

import java.util.List;

public class WardenriteWarfan extends Warfan {
    private static final String SOUL_KEY = Sifu.MODID + "Soul";
    private static final int MAX_SOUL_POINTS = 20;
    private static final int PARTICLE_COUNT = 12;
    private static final int SHARD_COUNT = 9;
    private final ItemDescriptionHandler descriptionHandler = new ItemDescriptionHandler();

    public WardenriteWarfan(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
        setupDescriptions();
    }

    private void setupDescriptions() {
        descriptionHandler.addDescription("This item provides a passive effect.", ItemDescriptionHandler.DescriptionType.PASSIVE);
        descriptionHandler.addDescription("This item triggers a special effect on hit.", ItemDescriptionHandler.DescriptionType.ON_HIT);
        descriptionHandler.addDescription("This item has a unique special ability.", ItemDescriptionHandler.DescriptionType.SPECIAL);
        descriptionHandler.addDescription("Lore:  In shadows deep, the warfan weeps,", ItemDescriptionHandler.DescriptionType.LORE);
        descriptionHandler.addDescription("A soul ensnared in chaos keeps.", ItemDescriptionHandler.DescriptionType.LORE);
        descriptionHandler.addDescription("Gods watch above as death draws near", ItemDescriptionHandler.DescriptionType.LORE);
        descriptionHandler.addDescription("A cursed breath, eternal fear.", ItemDescriptionHandler.DescriptionType.LORE);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        NbtCompound nbt = getNbtCompound(stack);
        int soulCount = nbt.getInt(SOUL_KEY);

        // Show lore only when Shift is held
        if (Screen.hasShiftDown()) {
            tooltip.addAll(descriptionHandler.getDescriptions());
        } else {
            tooltip.add(Text.literal("Press Shift for detail").formatted(Formatting.GRAY).formatted(Formatting.ITALIC));
            tooltip.add(Text.literal("Soul Collector: ").formatted(Formatting.YELLOW)
                    .append(Text.literal(String.valueOf(soulCount)).formatted(Formatting.RED))
                    .formatted(Formatting.ITALIC));
        }
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
            return TypedActionResult.pass(stack);
        }

        LivingEntity target = RaytraceUtil.raytraceForAimlock(user);

        if (world.isClient()) {
            spawnParticles(world, target);
            user.playSound(SoundEvents.ENTITY_WARDEN_DIG, 0.4f, 1.5f);
        } else {
            spawnShards(world, user, target);
            user.getItemCooldownManager().set(stack.getItem(), 10);
            decreaseSoulPoints(stack, 1);
            stack.damage(1, user, LivingEntity.getSlotForHand(hand));
        }

        return TypedActionResult.success(stack);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.distanceTo(target) <= 7.5 && target.isDead() && target.deathTime < 40) {
            addSoulPoints(stack, 1);
        }
        return super.postHit(stack, target, attacker);
    }

    @NotNull
    private static ShadowShardsEntity spawnShadowShards(World world, LivingEntity user, int index, LivingEntity target) {
        ShadowShardsEntity shardsEntity = new ShadowShardsEntity(world, user, 6, target);
        shardsEntity.setOwner(user);

        // Calculate the position in a circle above the player's head
        double angle = 2 * Math.PI * index / SHARD_COUNT;
        double radius = 2.0;
        double xOffset = radius * Math.cos(angle);
        double zOffset = radius * Math.sin(angle);
        shardsEntity.setPosition(user.getX() + xOffset, user.getY(), user.getZ() + zOffset);

        return shardsEntity;
    }

    private void spawnParticles(World world, LivingEntity target) {
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

    private void spawnShards(World world, PlayerEntity user, LivingEntity target) {
        for (int i = 0; i < SHARD_COUNT; i++) {
            ShadowShardsEntity shardsEntity = spawnShadowShards(world, user, i, target);
            world.spawnEntity(shardsEntity);
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
