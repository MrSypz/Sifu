package sypztep.sifu.api;

import com.terraformersmc.modmenu.util.mod.Mod;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import sypztep.sifu.ModConfig;
import sypztep.sifu.mixin.util.DefaultAttributeRegistryAccess;

import java.util.HashMap;
import java.util.Random;

import static sypztep.sifu.ModConfig.startingFactor;

public class MobAttributeModify {
    private final static Random random = new Random();

    // Use only on ServerWorld
    public static void changeAttributes(MobEntity mobEntity, World world) {
        if (!ModConfig.excludedEntity.contains(mobEntity.getType().toString().replace("entity.", "").replace(".", ":"))) {

            if (mobEntity.isBaby() && mobEntity instanceof PassiveEntity) {
                return;
            }

            // Factor
            double mobHealthFactor = startingFactor;
            double mobDamageFactor = startingFactor;
            double mobProtectionFactor = startingFactor;
            double mobSpeedFactor = startingFactor;

            // Entity Values
            double mobHealth = mobEntity.getAttributeBaseValue(EntityAttributes.GENERIC_MAX_HEALTH);
            // Check if hasAttributes necessary
            double mobDamage = 0.0F;
            double mobProtection = 0.0F;
            double mobSpeed = 0.0F;
            boolean hasAttackDamageAttribute = mobEntity.getAttributes().hasAttribute(EntityAttributes.GENERIC_ATTACK_DAMAGE);
            boolean hasArmorAttribute = mobEntity.getAttributes().hasAttribute(EntityAttributes.GENERIC_ARMOR);
            boolean hasMovementSpeedAttribute = mobEntity.getAttributes().hasAttribute(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            if (hasAttackDamageAttribute) {
                mobDamage = mobEntity.getAttributeBaseValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
            }
            if (hasArmorAttribute) {
                mobProtection = mobEntity.getAttributeBaseValue(EntityAttributes.GENERIC_ARMOR);
            }
            if (hasMovementSpeedAttribute) {
                mobSpeed = mobEntity.getAttributeBaseValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);

                if (mobHealthFactor < ModConfig.maxFactorHealth) {
                    mobHealthFactor = ModConfig.maxFactorHealth;
                }
                if (mobDamageFactor < ModConfig.maxFactorDamage) {
                    mobDamageFactor = ModConfig.maxFactorDamage;
                }
                if (mobProtectionFactor < ModConfig.maxFactorProtection) {
                    mobProtectionFactor = ModConfig.maxFactorProtection;
                }
                if (mobSpeedFactor < ModConfig.maxFactorSpeed) {
                    mobSpeedFactor = ModConfig.maxFactorSpeed;
                }
                // round factor
                mobHealthFactor = Math.round(mobHealthFactor * 100.0D) / 100.0D;
                mobProtectionFactor = Math.round(mobProtectionFactor * 100.0D) / 100.0D;
                mobDamageFactor = Math.round(mobDamageFactor * 100.0D) / 100.0D;
                mobSpeedFactor = Math.round(mobSpeedFactor * 1000.0D) / 1000.0D;

                // Setter
                mobHealth *= mobHealthFactor;
                mobDamage *= mobDamageFactor;
                mobProtection *= mobProtectionFactor;
                mobSpeed *= mobSpeedFactor;

                // Randomness
                if (ModConfig.allowRandomValues) {
                    float randomFactor = ModConfig.randomFactor / 100F;
                    mobHealth = mobHealth * (1 - randomFactor + (random.nextDouble() * randomFactor * 2F));
                    mobDamage = mobDamage * (1 - randomFactor + (random.nextDouble() * randomFactor * 2F));

                    // round value
                    mobHealth = Math.round(mobHealth * 100.0D) / 100.0D;
                    mobDamage = Math.round(mobDamage * 100.0D) / 100.0D;
                }
                DefaultAttributeContainer mobEntityDefaultAttributes = DefaultAttributeRegistryAccess.getRegistry().get(mobEntity.getType());

                // Test purpose
                if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
                    System.out.println(Registries.ENTITY_TYPE.getId(mobEntity.getType()).toString() + "; HealthFactor: " + mobHealthFactor + "; DamageFactor: " + mobDamageFactor + "; Health: "
                            + mobHealth + ";  Old Health: " + mobEntity.getHealth() + "; Default HP: "
                            + (mobEntityDefaultAttributes != null ? mobEntityDefaultAttributes.getBaseValue(EntityAttributes.GENERIC_MAX_HEALTH) : "-"));
                }

                // Check if mob already has increased strength
                if (mobEntityDefaultAttributes != null && mobHealth != mobEntityDefaultAttributes.getBaseValue(EntityAttributes.GENERIC_MAX_HEALTH)) {
                    // Set Values
                    mobEntity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(mobHealth);
                    mobEntity.heal(mobEntity.getMaxHealth());
                    if (hasAttackDamageAttribute) {
                        mobEntity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(mobDamage);
                    }
                    if (hasArmorAttribute) {
                        mobEntity.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).setBaseValue(mobProtection);
                    }
                    if (hasMovementSpeedAttribute) {
                        mobEntity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(mobSpeed);
                    }
                    setMobHealthMultiplier(mobEntity, (float) mobHealthFactor);
                }
            }
        }
    }

    public static void changeOnlyDamageAttribute(MobEntity mobEntity, Entity entity, boolean changeMobEntityValue) {
        if (!ModConfig.excludedEntity.contains(mobEntity.getType().toString().replace("entity.", "").replace(".", ":"))) {

            double mobDamageFactor = ModConfig.startingFactor;

            double maxFactor = ModConfig.maxFactorDamage;
            if (mobDamageFactor < maxFactor) {
                mobDamageFactor = maxFactor;
            }

            // round factor
            mobDamageFactor = Math.round(mobDamageFactor * 100.0D) / 100.0D;

            if (entity instanceof PersistentProjectileEntity persistentProjectileEntity) {
                persistentProjectileEntity.setDamage(persistentProjectileEntity.getDamage() * mobDamageFactor);
            }
            if (changeMobEntityValue) {
                boolean hasAttackDamageAttribute = mobEntity.getAttributes().hasAttribute(EntityAttributes.GENERIC_ATTACK_DAMAGE);
                if (hasAttackDamageAttribute) {
                    double mobDamage = mobEntity.getAttributeBaseValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
                    mobDamage *= mobDamageFactor;
                    mobEntity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(mobDamage);
                }
            }
        }
    }

    public static double getDamageFactor(Entity entity) {
        if (!ModConfig.excludedEntity.contains(entity.getType().toString().replace("entity.", "").replace(".", ":"))) {

            double mobDamageFactor = ModConfig.startingFactor;

            double maxFactor = ModConfig.maxFactorDamage;

            if (mobDamageFactor < maxFactor) {
                mobDamageFactor = maxFactor;
            }
            mobDamageFactor *= ModConfig.creeperExplosionFactor;
            if (mobDamageFactor < 1.0F)
                mobDamageFactor = 1.0F;
            // round factor
            mobDamageFactor = Math.round(mobDamageFactor * 100.0D) / 100.0D;

            return mobDamageFactor;
        }
        return 1.0D;
    }

    public static int getXpToDropAddition(MobEntity mobEntity, ServerWorld world, int original) {
        float xpFactor = getMobHealthMultiplier(mobEntity);

        float maxXPFactor = ModConfig.maxXPFactor;
        if (xpFactor > maxXPFactor) {
            xpFactor = maxXPFactor;
        }
        return (int) (original * xpFactor);
    }

    public static void setMobHealthMultiplier(MobEntity mobEntity, float multiplier) {
        ((EntityAccess) mobEntity).setMobHealthMultiplier(multiplier);
    }

    public static float getMobHealthMultiplier(MobEntity mobEntity) {
        return ((EntityAccess) mobEntity).getMobHealthMultiplier();
    }
}

