package maki325.bnha.api.skilltree;

import java.util.ArrayList;
import java.util.List;

import maki325.bnha.util.Utils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

public abstract class Skilltree {

	private List<Skill> skills;
	
	private Skill root;
	
	private ResourceLocation id;
	
	private Skill hudSkills[];
	
	/**
	 * 
	 * @param name - name of the {@link Skilltree}
	 * @param modId - id of the {@link Mod} that creates this {@link Skilltree}
	 */
	public Skilltree(String name, String modId) {
		skills = new ArrayList<Skill>();
		id = new ResourceLocation(modId, name);
		hudSkills = new Skill[5];
	}
	
	/**
	 * Initializes the {@link Skilltree}
	 */
	public void init() {
		int empty = Utils.isArrayEmpty(hudSkills);
		if(empty == -1) {
			if(getRoot() != null)
				hudSkills[0] = getRoot();
			return;
		}
		if(empty == 0)
			return;
		
		hudSkills[0] = hudSkills[empty];
		hudSkills[empty] = null;
	}
	
	/**
	 * 
	 * @param skill - {@link Skill} to be added
	 * @return <code>true</code> if {@link Skill} can be added
	 * 			and <code>false</code> if it can't
	 */
	protected boolean addSkill(Skill skill) {
		if(skills.contains(skill))
			return false;
		
		skills.add(skill);
		return true;
	}
	
	/**
	 * Test if the root of the {@link Skilltree} is null
	 * @return <code>true</code> if root is not <code>null</code> and false if it is
	 */
	public boolean hasRoot() {
		return root != null;
	}
	
	/**
	 * Loads the {@link Skilltree} info from the {@link NBTTagCompound}
	 * @param tag {@link NBTTagCompound} from which the info is loaded
	 */
	public void load(NBTTagCompound tag) {
		if(tag == new NBTTagCompound()) return;
		for(int i = 0;i < skills.size();i++) {
			skills.get(i).setActive(tag.getBoolean("skill_" + i));
			if(skills.get(i).equals(root)) {
				skills.get(i).setActive(true);
			}
		}
		
		for(int i = 0;i < 5;i++) {
			int q = i;
			if(!tag.getString("skill_hud_" + i).equals("null") && !tag.getString("skill_hud_" + i).equals(""))
				skills.forEach(skill -> { if(skill.getName().equals(tag.getString("skill_hud_" + q).trim())) { hudSkills[q] = skill; } });
		}
		
	}
	
	/**
	 * Saves the {@link Skilltree} info to a {@link NBTTagCompound}
	 * @return {@link NBTTagCompound} in which the info is saved
	 */
	public NBTTagCompound save() {
		NBTTagCompound tag = new NBTTagCompound();
		
		for(int i = 0;i < skills.size();i++) {
			tag.setBoolean("skill_" + i, skills.get(i).isActive());
		}
		
		for(int i = 0;i < 5;i++) {
			Skill sk = hudSkills[i];
			if(sk == null) {
				tag.setString("skill_hud_" + i, "null");
				continue;
			}
			tag.setString("skill_hud_" + i, sk.getName());
		}
		
		return tag;
	}
	
	/**
	 * Gets the price of the {@link Skill} in points
	 * @param skill - {@link Skill} we get the price of
	 * @return the price of the {@link Skill}
	 */
	public int getPrice(Skill skill) {
		return skill.getPrice();
	}
	
	protected void setPrice(Skill skill, int price) {
		skill.setPrice(price);
	}
	
	protected void setRoot(Skill skill) {
		if(root != null)
			return;
		
		root = skill;
		root.setActive(true);
	}
	
	public Skill getRoot() {
		return root;
	}
	
	public List<Skill> getSkills() {
		return skills;
	}
	
	public void printTree(String stuff) {
		printTree(stuff, root);
	}
	
	public void printTree(String stuff, Skill skill) {
		System.out.println(stuff + skill.getName());
		skill.getChildren().forEach(c -> printTree(stuff+stuff, c));
	}
	
	/**
	 * 
	 * @param position - position to put it in(start from <b>0</b>)
	 * @return the {@link Skill} at the requested positiom
	 */
	public Skill getHudSkill(int position) {
		return hudSkills[position];
	}

	/**
	 * 
	 * @param skill - Skill to put in the HUD
	 * @param position - position to put it in(start from <b>0</b>)
	 */
	public void setHudSkill(Skill skill, int position) {
		hudSkills[position] = skill;
	}
	
	public Skill[] getHudSkills() {
		return hudSkills;
	}
	
	public void setHudSkills(Skill[] hudSkills) {
		for(int i = 0;i < 5;i++) {
			this.hudSkills[i] = hudSkills[i];
		}
	}
	
	/**
	 * 
	 * @return the id of the {@link Mod} that made the {@link Skilltree}
	 */
	public String getModID() {
		return id.getResourceDomain();
	}
	
	/**
	 * 
	 * @return name of the {@link Skilltree}
	 */
	public String getName() {
		return id.getResourcePath();
	}
	
	/**
	 * 
	 * @return an id of the {@link Skilltree} represented as a {@link ResourceLocation}
	 */
	public ResourceLocation getID() {
		return id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Skilltree))
			return false;
		
		Skilltree st2 = (Skilltree) obj;
		if(this.getSkills().size() != st2.getSkills().size())
			return false;

		for(int i = 0;i < this.getSkills().size();i++) {
			if(!this.getSkills().get(i).equals(st2.getSkills().get(i))) {
				return false;
			}
		}
		
		return true;
	}
	
}
