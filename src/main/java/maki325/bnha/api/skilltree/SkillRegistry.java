package maki325.bnha.api.skilltree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import maki325.bnha.api.Pair;
import maki325.bnha.api.Quirk;

public class SkillRegistry {

	public static List<Skill> SKILLS = new ArrayList<Skill>();
	private static HashMap<Skill, Pair<Integer, Integer>> UVS = new HashMap<Skill, Pair<Integer, Integer>>();
	
	public static void addSkill(Skill s) {
		if(!SKILLS.contains(s))
			SKILLS.add(s);
	}
	
	public static List<Skill> getSkills() {
		return SKILLS;
	}
	
	public static void setUV(Skill s, int u, int v) {
		UVS.put(s, new Pair<Integer, Integer>(u, v));
	}
	
	public static Pair<Integer, Integer> getUV(Skill s) {
		return UVS.get(s);
	}
	
}
