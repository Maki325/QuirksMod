package maki325.bnha.quirks.explosion.skilltree.skills;

import maki325.bnha.api.skilltree.Skill;
import maki325.bnha.util.Reference;
import net.minecraft.entity.player.EntityPlayerMP;

public class SkillExplosion extends Skill {

	public SkillExplosion() {
		super("explosion", Reference.MOD_ID);
		
		setMaxCooldown(300);
		setMoneyMake(20);
	}

	@Override
	public void onActivate(EntityPlayerMP player) {
		player.world.createExplosion(player, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 2, true);
	}

}
