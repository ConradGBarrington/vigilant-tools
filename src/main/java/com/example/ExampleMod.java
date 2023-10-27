package com.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
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
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String ModID = "projectiletutorial"; // This is just so we can refer to our ModID easier.

	public static final Logger LOGGER = LoggerFactory.getLogger(ModID);

	

	public static final EntityType<GlowStickEntity> GlowStickEntityType = Registry.register(
		Registries.ENTITY_TYPE,
		new Identifier(ModID, "glow_stick"),
		FabricEntityTypeBuilder.<GlowStickEntity>create(SpawnGroup.MISC, GlowStickEntity::new)
			.dimensions(EntityDimensions.fixed(0.25F, 0.25F)) // dimensions in Minecraft units of the projectile
			.trackRangeBlocks(10).trackedUpdateRate(10) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
			.build() // VERY IMPORTANT DONT DELETE FOR THE LOVE OF GOD PSLSSSSSS
	);

	public static final Item GLOW_STICK = Registry.register(Registries.ITEM, new Identifier("tutorial", "custom_item"), new GlowStick(new FabricItemSettings()));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
	}
}