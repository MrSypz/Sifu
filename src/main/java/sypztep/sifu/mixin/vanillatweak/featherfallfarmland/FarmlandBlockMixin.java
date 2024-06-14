package sypztep.sifu.mixin.vanillatweak.featherfallfarmland;

import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sypztep.sifu.common.init.ModEnchantments;

@Mixin(FarmlandBlock.class)
public class FarmlandBlockMixin {

    @Inject(method = "onLandedUpon", at = @At("HEAD"), cancellable = true)
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance, CallbackInfo ci) {
        if (entity instanceof LivingEntity livingEntity) {
            DynamicRegistryManager dynamicRegistryManager = world.getRegistryManager();
            RegistryEntry<Enchantment> enchant = dynamicRegistryManager.get(RegistryKeys.ENCHANTMENT).getEntry(Identifier.ofVanilla("feather_falling")).get();
            if (livingEntity instanceof PlayerEntity player && EnchantmentHelper.getLevel(enchant, player.getEquippedStack(EquipmentSlot.FEET)) > 0) {
                ci.cancel();
            }
        }
    }
}