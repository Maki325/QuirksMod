package maki325.bnha.api;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.world.WorldServer;

public class FunctionHelper {

	public static void createExplosion(WorldServer world, double x, double y, double z, float strength, boolean isSmoking) {
		world.createExplosion(null, x, y, z, strength, isSmoking);
	}
	
	public static void createExplosion(WorldServer world, EntityPlayerMP player, float strength, boolean isSmoking) {
		world.createExplosion(null, player.posX, player.posY, player.posZ, strength, isSmoking);
	}
	
	public static void createFireball(WorldServer world, double x, double y, double z, double accelerationX, double accelerationY, double accelerationZ) {
		EntityLargeFireball fireball = new EntityLargeFireball(world);
		fireball.setPosition(x, y, z);
		fireball.accelerationX = accelerationX;
		fireball.accelerationY = accelerationY;
		fireball.accelerationZ = accelerationZ;
		world.spawnEntity(fireball);
	}
	
	public static void createFireball(WorldServer world, double x, double y, double z, double acceleration) {
		EntityLargeFireball fireball = new EntityLargeFireball(world);
		fireball.setPosition(x, y, z);
		fireball.accelerationX = acceleration;
		fireball.accelerationY = acceleration;
		fireball.accelerationZ = acceleration;
		world.spawnEntity(fireball);
	}
	
	public static void createFireball(WorldServer world, EntityPlayerMP player, double acceleration) {
		EntityLargeFireball fireball = new EntityLargeFireball(world);
		fireball.setPosition(player.posX, player.posY+1, player.posZ);
		fireball.accelerationX = player.getLookVec().x * acceleration;
		fireball.accelerationY = player.getLookVec().y * acceleration;
		fireball.accelerationZ = player.getLookVec().z * acceleration;
		world.spawnEntity(fireball);
	}
	
}
