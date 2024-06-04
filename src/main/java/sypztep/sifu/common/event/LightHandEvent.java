package sypztep.sifu.common.event;

import com.sypztep.common.util.EnchantmentUtil;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sypztep.sifu.common.init.ModEnchantments;
import sypztep.sifu.common.tag.ModBlockTags;
import sypztep.sifu.common.util.AbilityHelper;
import sypztep.sifu.mixin.util.ItemEntityAccessor;

import java.util.*;
import java.util.stream.Collectors;

public class LightHandEvent implements PlayerBlockBreakEvents.Before {
	static int maxExtractingBlocks;
	public static final Map<Block, Block> BASE_BLOCK_MAP = new HashMap<>();

	@Override
	public boolean beforeBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity) {
		if (!player.isSneaking()) {
			ItemStack stack = player.getMainHandStack();
			if (EnchantmentUtil.hasEnchantment(ModEnchantments.LIGHTHAND, stack) && state.isIn(ConventionalBlockTags.ORES) && player.canHarvest(state)) {
				Set<BlockPos> ores = gatherOres(new HashSet<>(), world, new BlockPos.Mutable().set(pos), state.getBlock());
				maxExtractingBlocks = EnchantmentHelper.getLevel(ModEnchantments.LIGHTHAND, stack) * 12;
				if (!ores.isEmpty() && ores.size() <= maxExtractingBlocks) {
					boolean[] broken = {false};
					stack.copy().damage(ores.size(), world.getRandom(), null, () -> broken[0] = true);
					if (!broken[0]) {
						BlockState replace = getBaseBlock(state).getDefaultState();
						List<ItemStack> drops = new ArrayList<>();
						ores.forEach(ore -> {
							BlockState oreState = world.getBlockState(ore);
							drops.addAll(Block.getDroppedStacks(oreState, (ServerWorld) world, ore, world.getBlockEntity(ore), player, stack));
							oreState.onStacksDropped((ServerWorld) world, player.getBlockPos(), stack, true);
							world.breakBlock(ore, false);
							world.setBlockState(ore, replace);
						});
						world.playSound(null, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK, SoundCategory.BLOCKS, 1, 1.5f);
						if (!drops.isEmpty()) {
							AbilityHelper.mergeItemEntities(drops.stream().map(drop -> new ItemEntity(world, player.getX(), player.getY() + 0.5, player.getZ(), drop)).collect(Collectors.toList())).forEach(world::spawnEntity);
						}
						return false;
					}
				}
			}
		}
		return true;
	}

	private static Set<BlockPos> gatherOres(Set<BlockPos> ores, World world, BlockPos.Mutable pos, Block original) {
		if (ores.size() < maxExtractingBlocks) {
			int originalX = pos.getX(), originalY = pos.getY(), originalZ = pos.getZ();
			for (int x = -1; x <= 1; x++) {
				for (int y = -1; y <= 1; y++) {
					for (int z = -1; z <= 1; z++) {
						BlockState state = world.getBlockState(pos.set(originalX + x, originalY + y, originalZ + z));
						if (state.isIn(ConventionalBlockTags.ORES) && !ores.contains(pos) && state.getBlock() == original) {
							ores.add(pos.toImmutable());
							gatherOres(ores, world, pos, original);
						}
					}
				}
			}
		}
		return ores;
	}

	private static Block getBaseBlock(BlockState state) {
		Block baseBlock = BASE_BLOCK_MAP.get(state.getBlock());
		if (baseBlock != null) {
			return baseBlock;
		}
		if (state.isIn(ModBlockTags.NETHER_ORES)) {
			return Blocks.NETHERRACK;
		}
		return Registries.BLOCK.getId(state.getBlock()).getPath().startsWith("deepslate") ? Blocks.DEEPSLATE : Blocks.STONE;
	}
}
