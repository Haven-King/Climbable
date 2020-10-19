package dev.hephaestus.climbable.api;

import net.minecraft.block.Block;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;

import java.util.HashMap;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class ClimbingSpeedRegistry {
	private static final DoubleSupplier DEFAULT = () -> 1D;
	private static final BooleanSupplier ALWAYS = () -> true;

	private static final HashMap<Block, DoubleSupplier> BLOCK_MODIFIERS = new HashMap<>();
	private static final HashMap<Tag<Block>, DoubleSupplier> TAG_MODIFIERS = new HashMap<>();
	private static final HashMap<Block, BooleanSupplier> BLOCK_CLIMBABLE = new HashMap<>();
	private static final HashMap<Tag<Block>, BooleanSupplier> TAG_CLIMBABLE = new HashMap<>();

	public static void registerClimbableTag(Tag<Block> tag) {
		registerClimbableTag(tag, ALWAYS);
	}

	public static void registerClimbableTag(Tag<Block> tag, double climbingSpeedModifier) {
		registerClimbableTag(tag, climbingSpeedModifier, ALWAYS);
	}

	public static void registerClimbableTag(Tag<Block> tag, DoubleSupplier climbingSpeedModifier) {
		registerClimbableTag(tag, climbingSpeedModifier, ALWAYS);
	}

	public static void registerClimbableTag(Tag<Block> tag, BooleanSupplier canClimb) {
		registerClimbableTag(tag, 1D, canClimb);
	}

	public static void registerClimbableTag(Tag<Block> tag, double climbingSpeedModifier, BooleanSupplier canClimb) {
		registerClimbableTag(tag, () -> climbingSpeedModifier, canClimb);
	}

	public static void registerClimbableTag(Tag<Block> tag, DoubleSupplier climbingSpeedModifier, BooleanSupplier canClimb) {
		TAG_MODIFIERS.put(tag, climbingSpeedModifier);
		TAG_CLIMBABLE.put(tag, canClimb);
	}

	public static void registerClimbingSpeedModifier(Block block, BooleanSupplier canClimb) {
		registerClimbingSpeedModifier(block, 1D, canClimb);
	}

	public static void registerClimbingSpeedModifier(Block block, double climbingSpeedModifier) {
		registerClimbingSpeedModifier(block, climbingSpeedModifier, ALWAYS);
	}

	public static void registerClimbingSpeedModifier(Block block, DoubleSupplier climbingSpeedModifier) {
		registerClimbingSpeedModifier(block, climbingSpeedModifier, ALWAYS);
	}

	public static void registerClimbingSpeedModifier(Block block, double climbingSpeedModifier, BooleanSupplier canClimb) {
		registerClimbingSpeedModifier(block, () -> climbingSpeedModifier, canClimb);
	}

	public static void registerClimbingSpeedModifier(Block block, DoubleSupplier climbingSpeedModifier, BooleanSupplier canClimb) {
		BLOCK_MODIFIERS.put(block, climbingSpeedModifier);
		BLOCK_CLIMBABLE.put(block, canClimb);
	}

	public static double getModifier(Block block) {
		double speed = 1D;

		if (BLOCK_MODIFIERS.containsKey(block)) {
			return BLOCK_MODIFIERS.getOrDefault(block, DEFAULT).getAsDouble();
		} else {
			for (Tag<Block> blockTag : TAG_MODIFIERS.keySet()) {
				if (block.isIn(blockTag)) {
					speed = speed * TAG_MODIFIERS.get(blockTag).getAsDouble();
				}
			}
		}

		return speed;
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
