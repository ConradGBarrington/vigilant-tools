package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GlowStickEntity extends ThrownItemEntity {

	public static final String ModID = "projectiletutorial"; // This is just so we can refer to our ModID easier.

	public static final Logger LOGGER = LoggerFactory.getLogger(ModID);


  public GlowStickEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
		super(entityType, world);
	}
 
	public GlowStickEntity(World world, LivingEntity owner) {
		super(ExampleMod.GlowStickEntityType, owner, world);
	}
 
	public GlowStickEntity(World world, double x, double y, double z) {
		super(ExampleMod.GlowStickEntityType, x, y, z, world);
	}
  
  @Override
	protected Item getDefaultItem() {
		return ExampleMod.GLOW_STICK;
	}

  

	@Environment(EnvType.CLIENT)
	private ParticleEffect getParticleParameters() { // Not entirely sure, but probably has do to with the snowball's particles. (OPTIONAL)
		ItemStack itemStack = this.getItem();
		return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.ITEM_SLIME : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
	}
 
	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) { // Also not entirely sure, but probably also has to do with the particles. This method (as well as the previous one) are optional, so if you don't understand, don't include this one.
		if (status == 3) {
			ParticleEffect particleEffect = this.getParticleParameters();
 
			for(int i = 0; i < 8; ++i) {
				this.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
			}
		}
 
	}
 
	protected void onEntityHit(EntityHitResult entityHitResult) { // called on entity hit.
		super.onEntityHit(entityHitResult);
		World world = this.getWorld();

		if (!world.isClient) { // checks if the world is client

		}
	}
 
	protected void onCollision(HitResult hitResult) { // called on collision with a block
		super.onCollision(hitResult);
    World world = this.getWorld();

		if (!world.isClient) { // checks if the world is client
			if (hitResult instanceof BlockHitResult) {
				BlockHitResult blockHitResult = (BlockHitResult) hitResult;

				BlockPos blockPosition = blockHitResult.getBlockPos();

				switch (blockHitResult.getSide().asString()) {
					case "up":
						blockPosition = blockPosition.add(0, 1, 0);
						break;
					case "down":
						blockPosition = blockPosition.add(0, -1, 0);
						break;
					case "north":
						blockPosition = blockPosition.add(0, 0, -1);
						break;
					case "south":
						blockPosition = blockPosition.add(0, 0, 1);
						break;
					case "east":
						blockPosition = blockPosition.add(1, 0, 0);
						break;
					case "west":
						blockPosition = blockPosition.add(-1, 0, 0);
						break;
				}

				if (world.canPlace(world.getBlockState(blockPosition), blockPosition, ShapeContext.absent())) {
					world.setBlockState(blockPosition, ExampleMod.GLOW_STICK_BLOCK.getDefaultState());
				} else {
					drop(world);
				}

			} else {
				drop(world);
			}

			world.sendEntityStatus(this, (byte)3); // particle?
			this.kill(); // kills the projectile
		}
	}

	private void drop(World world) {
		Entity dropped_entity = new ItemEntity(
			world,
			this.getX(),
			this.getY(),
			this.getZ(),
			new ItemStack(getDefaultItem())
		);
		world.spawnEntity(dropped_entity);
	}
  
}
