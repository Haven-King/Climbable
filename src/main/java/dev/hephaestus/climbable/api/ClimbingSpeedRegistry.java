package dev.hephaestus.climbable.api;

import net.minecraft.block.Block;

import java.util.HashMap;

public class ClimbingSpeedRegistry {
	private static final HashMap<Block, Double> BLOCK_MODIFIERS = new HashMap<>();

	public static void register(Block block, double climbingSpeedModifier) {
		BLOCK_MODIFIERS.put(block, climbingSpeedModifier);
	}

	public static double getModifier(Block block) {
		return BLOCK_MODIFIERS.getOrDefault(block, 1D);
	}
}
