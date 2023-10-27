package com.example;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class GlowStickEntity extends ThrownItemEntity {
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
		return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
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
 
	// protected void onEntityHit(EntityHitResult entityHitResult) { // called on entity hit.
	// 	super.onEntityHit(entityHitResult);
	// 	Entity entity = entityHitResult.getEntity(); // sets a new Entity instance as the EntityHitResult (victim)
	// 	int i = entity instanceof BlazeEntity ? 3 : 0; // sets i to 3 if the Entity instance is an instance of BlazeEntity
	// 	entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), (float)i); // deals damage
 
	// 	if (entity instanceof LivingEntity livingEntity) { // checks if entity is an instance of LivingEntity (meaning it is not a boat or minecart)
	// 		livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.BLINDNESS, 20 * 3, 0))); // applies a status effect
	// 		livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * 3, 2))); // applies a status effect
	// 		livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.POISON, 20 * 3, 1))); // applies a status effect
	// 		livingEntity.playSound(SoundEvents.AMBIENT_CAVE, 2F, 1F); // plays a sound for the entity hit only
	// 	}
	// }
 
	protected void onCollision(HitResult hitResult) { // called on collision with a block
		super.onCollision(hitResult);
    World world = this.getWorld();

		if (!world.isClient) { // checks if the world is client
			world.sendEntityStatus(this, (byte)3); // particle?
			this.kill(); // kills the projectile
		}
 
	}
  
}
