package dev.hephaestus.climbable.api;

import net.minecraft.block.Block;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;

import java.util.HashMap;
import java.util.function.BooleanSupplier;

public class ClimbingSpeedRegistry {
	private static final BooleanSupplier ALWAYS = () -> true;

	private static final HashMap<Block, Double> BLOCK_MODIFIERS = new HashMap<>();
	private static final HashMap<Tag<Block>, Double> TAG_MODIFIERS = new HashMap<>();
	private static final HashMap<Block, BooleanSupplier> BLOCK_CLIMBABLE = new HashMap<>();
	private static final HashMap<Tag<Block>, BooleanSupplier> TAG_CLIMBABLE = new HashMap<>();

	public static void registerClimbableTag(Tag<Block> tag) {
		registerClimbableTag(tag, ALWAYS);
	}

	public static void registerClimbableTag(Tag<Block> tag, double climbingSpeedModifier) {
		registerClimbableTag(tag, climbingSpeedModifier, ALWAYS);
	}

	public static void registerClimbableTag(Tag<Block> tag, BooleanSupplier canClimb) {
		registerClimbableTag(tag, 1D, canClimb);
	}

	public static void registerClimbableTag(Tag<Block> tag, double climbingSpeedModifier, BooleanSupplier canClimb) {
		TAG_MODIFIERS.put(tag, climbingSpeedModifier);
		TAG_CLIMBABLE.put(tag, canClimb);
	}

	public static void registerClimbingSpeedModifier(Block block, BooleanSupplier canClimb) {
		registerClimbingSpeedModifier(block, 1D, canClimb);
	}

	public static void registerClimbingSpeedModifier(Block block, double climbingSpeedModifier) {
		registerClimbingSpeedModifier(block, climbingSpeedModifier, ALWAYS);
	}

	public static void registerClimbingSpeedModifier(Block block, double climbingSpeedModifier, BooleanSupplier canClimb) {
		BLOCK_MODIFIERS.put(block, climbingSpeedModifier);
		BLOCK_CLIMBABLE.put(block, canClimb);
	}

	public static double getModifier(Block block) {
		if (BLOCK_MODIFIERS.containsKey(block)) {
			return BLOCK_MODIFIERS.getOrDefault(block, 1D);
		} else {
			double speed = 1D;
			for (Tag<Block> blockTag : TAG_MODIFIERS.keySet()) {
				if (block.isIn(blockTag)) {
					speed = speed * TAG_MODIFIERS.get(blockTag);
				}
			}
		}

		return 1D;
	}

	public static boolean canClimb(Block block) {
		if (block.isIn(BlockTags.CLIMBABLE)) {
			return true;
		}

		if (BLOCK_CLIMBABLE.containsKey(block)) {
			return BLOCK_CLIMBABLE.get(block).getAsBoolean();
		}

		for (Tag<Block> blockTag : TAG_CLIMBABLE.keySet()) {
			if (block.isIn(blockTag)) {
				return TAG_CLIMBABLE.get(blockTag).getAsBoolean();
			}
		}

		return false;
	}
}
