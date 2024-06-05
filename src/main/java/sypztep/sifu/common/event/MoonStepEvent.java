package sypztep.sifu.common.event;

import com.sypztep.common.util.EnchantmentUtil;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import sypztep.sifu.common.component.entity.MoonStepComponent;
import sypztep.sifu.common.init.ModEnchantments;
import sypztep.sifu.common.init.ModEntityComponents;

public class MoonStepEvent implements ServerEntityEvents.EquipmentChange {
	@Override
	public void onChange(LivingEntity livingEntity, EquipmentSlot equipmentSlot, ItemStack previousStack, ItemStack currentStack) {
		if (EnchantmentUtil.hasEnchantment(ModEnchantments.MOONSTEP, currentStack)) {
			MoonStepComponent component = ModEntityComponents.MOONSTEP.getNullable(livingEntity);
			if (component != null) {
				component.setMoonstepCooldown(MoonStepComponent.DEFAULT_MOONSTEP_COOLDOWN);
				component.setJumpsLeft(0);
				component.sync();
			}
		}
	}
}
