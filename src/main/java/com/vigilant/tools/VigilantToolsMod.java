package com.vigilant.tools;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vigilant.tools.glowstick.GlowStick;
import com.vigilant.tools.glowstick.GlowStickBlock;
import com.vigilant.tools.glowstick.GlowStickEntity;
import com.vigilant.weapons.dagger.DaggerItem;

public class VigilantToolsMod implements ModInitializer {
	public static final String ModID = "vigilant-tools";

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
		new Identifier("vigilant-tools", "glow_stick_item"),
		new GlowStick(new FabricItemSettings())
	);
	public static final Block GLOW_STICK_BLOCK = Registry.register(
		Registries.BLOCK,
		new Identifier("vigilant-tools", "glow_stick_block"),
		new GlowStickBlock(
			FabricBlockSettings.create()
				.strength(0.1f, 1.0f)
				.nonOpaque()
				.luminance(14)
				.collidable(false)
		)
	);

	//Dagger
	public static final Item WOODEN_DAGGER = Registry.register(
		Registries.ITEM,
		new Identifier("vigilant-tools", "wooden_dagger_item"),
		new DaggerItem(ToolMaterials.WOOD, 2, 2.0f, new FabricItemSettings()) 
	);
	public static final Item STONE_DAGGER = Registry.register(
		Registries.ITEM,
		new Identifier("vigilant-tools", "stone_dagger_item"),
		new DaggerItem(ToolMaterials.STONE, 2, 2.0f, new FabricItemSettings()) 
	);
	public static final Item IRON_DAGGER = Registry.register(
		Registries.ITEM,
		new Identifier("vigilant-tools", "iron_dagger_item"),
		new DaggerItem(ToolMaterials.IRON, 2, 2.0f, new FabricItemSettings()) 
	);
	public static final Item GOLD_DAGGER = Registry.register(
		Registries.ITEM,
		new Identifier("vigilant-tools", "gold_dagger_item"),
		new DaggerItem(ToolMaterials.GOLD, 2, 2.0f, new FabricItemSettings()) 
	);
	public static final Item DIAMOND_DAGGER = Registry.register(
		Registries.ITEM,
		new Identifier("vigilant-tools", "diamond_dagger_item"),
		new DaggerItem(ToolMaterials.DIAMOND, 2, 2.0f, new FabricItemSettings()) 
	);
	public static final Item NETHERITE_DAGGER = Registry.register(
		Registries.ITEM,
		new Identifier("vigilant-tools", "netherite_dagger_item"),
		new DaggerItem(ToolMaterials.NETHERITE, 2, 2.0f, new FabricItemSettings()) 
	);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(content -> {
			content.addAfter(Items.TORCH, GLOW_STICK);
		});
	}
}