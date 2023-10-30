package com.vigilant.tools;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod implements ModInitializer {
	public static final String ModID = "projectiletutorial";

	public static final Logger LOGGER = LoggerFactory.getLogger(ModID);

	
	//Glow Stick
	public static final EntityType<GlowStickEntity> GlowStickEntityType = Registry.register(
		Registries.ENTITY_TYPE,
		new Identifier(ModID, "glow_stick"),
		FabricEntityTypeBuilder.<GlowStickEntity>create(SpawnGroup.MISC, GlowStickEntity::new)
			.dimensions(EntityDimensions.fixed(0.25F, 0.25F)) // dimensions in Minecraft units of the projectile
			.trackRangeBlocks(10).trackedUpdateRate(10) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
			.build()
	);
	public static final Item GLOW_STICK = Registry.register(
		Registries.ITEM,
		new Identifier("tutorial", "custom_item"),
		new GlowStick(new FabricItemSettings())
	);
	public static final Block GLOW_STICK_BLOCK = Registry.register(
		Registries.BLOCK,
		new Identifier("tutorial", "example_block"),
		new GlowStickBlock(FabricBlockSettings.create().strength(0.1f, 1.0f).nonOpaque().luminance(14))
	);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
	}
}