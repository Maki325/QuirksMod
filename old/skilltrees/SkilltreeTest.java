package maki325.bnha.skilltrees;

import maki325.bnha.api.skilltree.Skill;
import maki325.bnha.api.skilltree.Skilltree;
import maki325.bnha.util.Reference;

public class SkilltreeTest extends Skilltree {

	public final Skill explode;
	
	public final Skill teleport;
	public final Skill teleport2;

	public final Skill explode2;
	public final Skill teleport3;

	public final Skill explode3;
	public final Skill explode4;
	
	public SkilltreeTest() {
		super("Test Skilltree", Reference.MOD_ID);
		
		explode = new SkillExplode();
		addSkill(explode);
		setRoot(explode);

		teleport = new SkillTeleport();
		teleport.setParent(explode);
		addSkill(teleport);

		teleport2 = new SkillTeleport();
		teleport2.setParent(explode);
		addSkill(teleport2);

		explode2 = new SkillExplode();
		explode2.setParent(teleport2);
		addSkill(explode2);

		teleport3 = new SkillTeleport();
		teleport3.setParent(teleport);
		addSkill(teleport3);

		explode3 = new SkillExplode();
		explode3.setParent(teleport3);
		addSkill(explode3);

		explode4 = new SkillExplode();
		explode4.setParent(teleport3);
		addSkill(explode4);
	}

	
	
}
