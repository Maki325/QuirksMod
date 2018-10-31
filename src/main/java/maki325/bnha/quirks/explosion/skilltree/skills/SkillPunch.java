package maki325.bnha.quirks.explosion.skilltree.skills;

import maki325.bnha.api.skilltree.Skill;
import maki325.bnha.util.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SkillPunch extends Skill {

	public SkillPunch() {
		super("punch", Reference.MOD_ID);
		
		setMaxCooldown(200);
		setMoneyMake(15);
	}

	@Override
	public void onActivate(EntityPlayerMP player) {
		World world = player.world;
		Vec3d look = player.getLookVec();
		EntityLargeFireball fireball = new EntityLargeFireball(world);
		fireball.setPosition(player.posX + look.x * 5, player.posY + look.y * 5, player.posZ + look.z * 5);
		fireball.accelerationX = look.x * 0.4;
		fireball.accelerationY = look.y * 0.4;
		fireball.accelerationZ = look.z * 0.4;
		world.spawnEntity(fireball);
	}

}
