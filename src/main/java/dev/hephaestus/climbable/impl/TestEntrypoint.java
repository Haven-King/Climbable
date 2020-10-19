package dev.hephaestus.climbable.impl;

import dev.hephaestus.climbable.api.ClimbingSpeedRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Blocks;

public class TestEntrypoint implements ModInitializer {
	@Override
	public void onInitialize() {
		if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
			ClimbingSpeedRegistry.register(Blocks.COBBLESTONE, 0.5);
		}
	}
}
