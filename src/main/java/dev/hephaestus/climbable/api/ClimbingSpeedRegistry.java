package dev.hephaestus.climbable.api;

import net.minecraft.block.Block;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;

import java.util.HashMap;

public class ClimbingSpeedRegistry {
	private static final HashMap<Block, Double> BLOCK_MODIFIERS = new HashMap<>();
	private static final HashMap<Tag<Block>, Double> TAG_MODIFIERS = new HashMap<>();

	public static void registerClimbableTag(Tag<Block> tag) {
		registerClimbableTag(tag, 1D);
	}

	public static void registerClimbableTag(Tag<Block> tag, double climbingSpeedModifier) {
		TAG_MODIFIERS.put(tag, climbingSpeedModifier);
	}

	public static void registerClimbingSpeedModifier(Block block, double climbingSpeedModifier) {
		BLOCK_MODIFIERS.put(block, climbingSpeedModifier);
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

		for (Tag<Block> blockTag : TAG_MODIFIERS.keySet()) {
			if (block.isIn(blockTag)) {
				return true;
			}
		}

		return false;
	}
}
