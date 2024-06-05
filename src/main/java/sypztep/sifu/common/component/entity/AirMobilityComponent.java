package sypztep.sifu.common.component.entity;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;
import sypztep.sifu.ModConfig;
import sypztep.sifu.common.init.ModDataComponentTypes;
import sypztep.sifu.common.util.LivingEntityUtil;

public class AirMobilityComponent implements CommonTickingComponent {
	private final LivingEntity obj;
	private int resetBypassTicks = 0, ticksInAir = 0;

	public AirMobilityComponent(LivingEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
		resetBypassTicks = tag.getInt("ResetBypassTicks");
		ticksInAir = tag.getInt("TicksInAir");
	}

	@Override
	public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
		tag.putInt("ResetBypassTicks", resetBypassTicks);
		tag.putInt("TicksInAir", ticksInAir);
	}

	@Override
	public void tick() {
		ItemStack stack = obj.getEquippedStack(EquipmentSlot.CHEST);
		if (ModConfig.enchantedChestplatesIncreaseAirMobility && stack.getOrDefault(ModDataComponentTypes.TOGGLEABLE_PASSIVE, false)) {
			if (!stack.hasEnchantments()) {
				stack.remove(ModDataComponentTypes.TOGGLEABLE_PASSIVE);
				return;
			}
			if (resetBypassTicks > 0) {
				resetBypassTicks--;
			}
			if (obj.isOnGround()) {
				if (resetBypassTicks == 0) {
					ticksInAir = 0;
				}
			} else if (LivingEntityUtil.isGroundedOrAirborne(obj) && obj.getWorld().raycast(new RaycastContext(obj.getPos(), obj.getPos().add(0, -1, 0), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.ANY, obj)).getType() == HitResult.Type.MISS) {
				ticksInAir++;
			}
		} else {
			resetBypassTicks = ticksInAir = 0;
		}
	}

	public int getTicksInAir() {
		return ticksInAir;
	}

	public void enableResetBypass() {
		resetBypassTicks = 3;
	}
}
