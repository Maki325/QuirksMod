package maki325.bnha.quirks.explosion.skilltree;

import maki325.bnha.api.skilltree.Skill;
import maki325.bnha.api.skilltree.Skilltree;
import maki325.bnha.quirks.explosion.skilltree.skills.SkillExplosion;
import maki325.bnha.quirks.explosion.skilltree.skills.SkillPunch;
import maki325.bnha.util.Reference;

public class SkilltreeExplosive extends Skilltree {

	public final Skill punch;
	public final Skill explosion;
	
	public SkilltreeExplosive() {
		super("explosive", Reference.MOD_ID);

		explosion = new SkillExplosion();
		setRoot(explosion);
		addSkill(explosion);
		
		punch = new SkillPunch();
		punch.setParent(explosion);
		punch.setPrice(150);
		addSkill(punch);
		
	}
	
}
