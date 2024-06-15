package sypztep.sifu.common.item.impl;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import sypztep.damned.common.init.ModParticles;
import sypztep.sifu.common.init.ModSoundEvents;
import sypztep.sifu.common.init.ModStatusEffects;
import sypztep.sifu.common.payload.WardenriteArmorPayload;

public class WardenriteArmor extends ArmorItem {

    public WardenriteArmor(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player) {
            int piecesWorn = getWardenritePiecesWorn(player);
            if (!world.isClient()) {
                if (piecesWorn == 4 && !player.hasStatusEffect(ModStatusEffects.STYGIA_COOLDOWN)) // Full set
                    applyStatusEffect(player, piecesWorn);

            } else {
                if (piecesWorn == 4 && player.age % 30 == 0) {
                    addParticle(player);
                    WardenriteArmorPayload.send(1);
                }
            }
            super.inventoryTick(stack, world, entity, slot, selected);
        }
    }

    public static void addParticle(Entity user) {
        if (MinecraftClient.getInstance().gameRenderer.getCamera().isThirdPerson() || user != MinecraftClient.getInstance().cameraEntity)
            for (int i = 0; i < 6; i++) {
                user.getWorld().addParticle(ParticleTypes.TRIAL_SPAWNER_DETECTION_OMINOUS, true, user.getParticleX(.75), user.getBodyY(.2), user.getParticleZ(.75), 0, 0, 0);
            }
    }

    @Override
    public TypedActionResult<ItemStack> equipAndSwap(Item item, World world, PlayerEntity user, Hand hand) {
        int piecesWorn = getWardenritePiecesWorn(user);
        if (piecesWorn == 3) { // Full set
            addWardenriteStygia(user);
            WardenriteArmorPayload.send(0);
        }
        return super.equipAndSwap(item, world, user, hand);
    }

    public static void addWardenriteStygia(Entity user) {
        if (MinecraftClient.getInstance().gameRenderer.getCamera().isThirdPerson() || user != MinecraftClient.getInstance().cameraEntity)
            user.getWorld().addParticle(ModParticles.SHOCKWAVE_VERTICAL, true, user.getX(), user.getEyeY(), user.getZ(), 0, 0, 0);
        user.getWorld().playSound(null, user.getX(), user.getY(), user.getZ(), ModSoundEvents.ENTITY_STYGIA, SoundCategory.PLAYERS, 3, 1.25f);
    }

    public static int getWardenritePiecesWorn(LivingEntity player) {
        int count = 0;
        for (EquipmentSlot slot : EquipmentSlot.values())
            if (slot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR) {
                ItemStack armorPiece = player.getEquippedStack(slot);
                if (armorPiece.getItem() instanceof WardenriteArmor) count++;
            }
        return count;
    }

    private void applyStatusEffect(PlayerEntity player, int piecesWorn) {
        StatusEffectInstance effect = new StatusEffectInstance(StatusEffects.ABSORPTION, 400, piecesWorn * 4 - 1, false, false, true);
        if (player.hasStatusEffect(StatusEffects.ABSORPTION)) return;
        player.addStatusEffect(effect);
    }
}
