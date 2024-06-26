package sypztep.sifu.common.item.combat;

import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sypztep.sifu.Sifu;
import sypztep.sifu.common.init.ModItems;
import sypztep.sifu.common.util.CustomHitParticleItem;
import sypztep.sifu.common.util.CustomHitSoundItem;

import java.util.List;

public abstract class Warfan
        extends ToolItem
        implements CustomHitParticleItem, CustomHitSoundItem {
    public static final Identifier BASE_ATTACK_RANGE_MODIFIER_ID = Sifu.id("base_attack_range");
    public Warfan(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings.component(DataComponentTypes.TOOL, Warfan.createToolComponent()));
    }
    public static ToolComponent createToolComponent() {
        return new ToolComponent(List.of(), 1.0f, 2);
    }
    public static AttributeModifiersComponent createAttributeModifiers(ToolMaterial material, int baseAttackDamage, float attackSpeed, double attackrange) {
        return AttributeModifiersComponent.builder()
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE,
                new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, (float)baseAttackDamage + material.getAttackDamage(), EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED,
                new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
                new EntityAttributeModifier(BASE_ATTACK_RANGE_MODIFIER_ID, attackrange, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).build();
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.isOf(ModItems.WARDENRITE_FRAGMENT);
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);
        return true;
    }
    @Override
    public void spawnHitParticles(PlayerEntity user) {

    }

    @Override
    public void playHitSound(PlayerEntity user) {

    }
}
