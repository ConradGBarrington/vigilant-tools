package com.vigilant.tools;

import com.vigilant.tools.glowstick.GlowStickEntity;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class VigilantToolsModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		EntityRendererRegistry.register(VigilantToolsMod.GlowStickEntityType, (context) ->
				 new FlyingItemEntityRenderer<GlowStickEntity>(context));

		BlockRenderLayerMap.INSTANCE.putBlock(VigilantToolsMod.GLOW_STICK_BLOCK, RenderLayer.getCutout());
	}
}