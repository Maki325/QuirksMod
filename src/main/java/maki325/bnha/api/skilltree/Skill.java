package maki325.bnha.api.skilltree;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import maki325.bnha.BnHA;
import maki325.bnha.api.Quirk;
import maki325.bnha.api.QuirkSkill;
import maki325.bnha.gui.hud.GuiHud;
import maki325.bnha.proxy.ClientProxy;
import maki325.bnha.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class Skill {

	private final ResourceLocation id;
	private Skill parent;
	private List<Skill> children;
	private boolean isActive;
	private int maxCooldown;
	private int price;
	private int makes;
	
	public Skill(String name, String modId) {
		id = new ResourceLocation(modId, name);
		parent = null;
		children = new ArrayList<Skill>();
		isActive = false;
		maxCooldown = 0;
		price = 0;
		makes = 10;
		SkillRegistry.addSkill(this);
	}
	
	/**
	 * Function called when player fires a {@link Quirk} with this {@link Skill}.
	 * Should contain just the code for actions.
	 * Cooldown is already covered by {@link QuirkSkill} tick function. You can set the cooldown
	 * with the .setMaxCooldown(int maxCooldown) function.
	 * 
	 * Will add support for tick function for the {@link Skill} but the cooldown function
	 * would still be done by {@link QuirkSkill}
	 * 
	 * @param player - a {@link EntityPlayerMP} that activated the {@link Skill}
	 */
	public abstract void onActivate(EntityPlayerMP player);
	
	public void setMaxCooldown(int maxCooldown) {
		this.maxCooldown = maxCooldown;
	}
	
	public void setMoneyMake(int make) {
		this.makes = make;
	}
	
	public int getMoneyMake() {
		return this.makes;
	}

	public int getMaxCooldown() {
		return this.maxCooldown;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public String getName() {
		return id.getResourcePath();
	}

	public String getModId() {
		return id.getResourceDomain();
	}
	
	public ResourceLocation getID() {
		return id;
	}
	
	public boolean setParent(Skill parent) {
		this.parent = parent;
		parent.addChild(this);
		return true;	
	}
	
	private void addChild(Skill child) {
		children.add(child);
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public void setActive(boolean active) {
		this.isActive = active;
	}
	
	public List<Skill> getRoot() {
		List<Skill> ret = new ArrayList<Skill>();
		if(parent == null) {
			ret.add(this);
			return ret;
		}
		
		ret.addAll(parent.getRoot());
		
		return ret;
	}
	
	public List<Skill> getChildren() {
		return children;
	}
	
	public Skill getParent() {
		return parent;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Skill) || obj == null || this == null)
			return false;
		
		Skill skill = (Skill) obj;
		
		if(skill.getParent() == null && this.getParent() == null) {
			if(skill.getID().equals(this.getID()) && skill.isActive() == this.isActive() && skill.getChildren().equals(this.getChildren()))
				return true;
			
			return false;
		}
		
		if(skill.getParent() == null || this.getParent() == null) {
			return false;
		}
		
		if(skill.getID().equals(this.getID()) && skill.isActive() == this.isActive() && skill.getChildren().equals(this.getChildren()) && skill.getParent() == this.getParent()) {
			return true;
		}
		
		return false;
	}
	
}
