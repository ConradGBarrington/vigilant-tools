package com.vigilant.tools.glowstick;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class GlowStick extends Item {

  public GlowStick(Settings settings) {
    super(settings);
  }


  @Override
  public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
    ItemStack stack = playerEntity.getStackInHand(hand);

    playerEntity.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1.0F, 1.0F);
    playerEntity.getItemCooldownManager().set(this, 5);

    if (!world.isClient) {
			GlowStickEntity glowstickEntity = new GlowStickEntity(world, playerEntity);
			glowstickEntity.setItem(stack);
			glowstickEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, 1.5F, 0F);
			world.spawnEntity(glowstickEntity); // spawns entity
		}

    playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
		if (!playerEntity.getAbilities().creativeMode) {
			stack.decrement(1); // decrements itemStack if user is not in creative mode
		}

    return TypedActionResult.success(stack);
  }
  
}
