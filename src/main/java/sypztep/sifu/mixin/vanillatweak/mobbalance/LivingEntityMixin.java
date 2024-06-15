package sypztep.sifu.mixin.vanillatweak.mobbalance;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sypztep.sifu.ModConfig;
import sypztep.sifu.common.tag.ModEntityTypeTags;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Unique
    public abstract float getMaxHealth();

    @Unique
    public abstract void setHealth(float health);

    @Unique
    @Nullable
    public abstract EntityAttributeInstance getAttributeInstance(RegistryEntry<EntityAttribute> attribute);

    @Inject(at = @At("RETURN"), method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;)V")
    private void modifyHealth(EntityType<?> entityType, World world, CallbackInfo ci) {
        if (entityType.isIn(ModEntityTypeTags.MODIFY_EXTRA)) {
            EntityAttributeInstance health = getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            EntityAttributeInstance attack = getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
            assert health != null;
            health.setBaseValue(health.getBaseValue() * ModConfig.entityHealthFactor);
            assert attack != null;
            attack.setBaseValue(attack.getBaseValue() * ModConfig.entityAttackFactor);
            setHealth(getMaxHealth());
        }
    }
}