package sypztep.sifu.common.item;

import net.minecraft.client.item.TooltipType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import sypztep.sifu.common.entity.projectile.ShadowShardsEntity;
import sypztep.sifu.common.util.RaytraceUtil;

import java.util.List;

public class WardenriteWarfan extends Warfan {
    public static Entity GlowEntity = null;
    public WardenriteWarfan(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        tooltip.addLast(Text.literal("  In shadows deep, the warfan weeps,").formatted(Formatting.GRAY).formatted(Formatting.ITALIC));
        tooltip.addLast(Text.literal("A soul ensnared in chaos keeps.").formatted(Formatting.GRAY).formatted(Formatting.ITALIC));
        tooltip.addLast(Text.literal("Gods watch above as death draws near").formatted(Formatting.GRAY).formatted(Formatting.ITALIC));
        tooltip.addLast(Text.literal("A cursed breath, eternal fear.").formatted(Formatting.GRAY).formatted(Formatting.ITALIC));
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        LivingEntity target = RaytraceUtil.raytraceForAimlock(user);
        GlowEntity = target;
        if (world.isClient()) {
            if (target != null) {
                for (int i = 0; i < 12; i++) {
                    Vec3d vec3d = new Vec3d(
                            target.getX() + (MathHelper.sin((target.age) * 0.75f + i * 45) * target.getWidth() * 1.2),
                            target.getBodyY(0.5f),
                            target.getZ() + (MathHelper.cos((target.age) * 0.75f + i * 45) * target.getWidth() * 1.2));
                    world.addParticle(ParticleTypes.SCULK_SOUL, vec3d.getX(), vec3d.getY(), vec3d.getZ(), 0.0, 0.0, 0.0);
                }
            }
        }
        if (!world.isClient()) {
            for (int i = 0; i < 9; i++) {
                ShadowShardsEntity shardsEntity = spawnShadowShards(world, user, i,target);
                world.spawnEntity(shardsEntity);
            }
            user.getItemCooldownManager().set(user.getStackInHand(hand).getItem(),100);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPYGLASS;
    }

    @NotNull
    private static ShadowShardsEntity spawnShadowShards(World world, LivingEntity user, int i,LivingEntity target) {
        ShadowShardsEntity shardsEntity = new ShadowShardsEntity(world, user, 6,target);
        shardsEntity.setOwner(user);

        // Calculate the position in a circle above the player's head
        double angle = 2 * Math.PI * i / 9;
        double radius = 2.0;
        double xOffset = radius * Math.cos(angle);
        double zOffset = radius * Math.sin(angle);
        shardsEntity.setPosition(user.getX() + xOffset, user.getY(), user.getZ() + zOffset);
        return shardsEntity;
    }
}
