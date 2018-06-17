package maki325.bnha.api;

import maki325.bnha.init.ModQuirks;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionAttackDamage;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

public abstract class Quirk {

	protected String name;
	protected boolean isUsable = true;

	protected static int cooldown = 0;
	protected static int maxCooldown = 0;

	protected static int act = 0;
	protected static int maxAct = 0;
	
	protected static boolean activated = false;
	protected static boolean aviable = true;
	
	protected static double xp = 0;
	protected static double nextXp = 0;
	protected static double xpPerTick = 0;
	
	protected static int level = 0;
	
	protected static double levelFactor = 0;
	protected static double levelMinimum = 0;
	
	protected static LevelUp levelUp = null;
	
	public Quirk(String name) {
		this.name = name;
		ModQuirks.QUIRKS.add(this);
	}
	
	public Quirk(NBTTagCompound quirk) {
		
	}

	@SubscribeEvent
	public abstract void onPlayerUse(EntityPlayer player);
	
	@SubscribeEvent
	public void onClient(WorldClient worldClient, double x, double y, double z) {}
	
	public String getName() {
		return name;
	}
	
	public static int xpToLevel(double xp) {
		double i = levelMinimum;
		int lvl = 1;
		if(xp < i) return 1;
		
		while(i <= xp) {
			lvl++;
			i *= levelFactor;
		}

		if(xp < i) {
			if(lvl - 1 == 0) {
				
				return 1;
			}
			return lvl - 1;
		}
		
		return lvl;
	}
	
	public void setXp(double xp) {
		this.xp = xp;
		this.level = xpToLevel(xp);
		System.out.println("LEVEL: " + level);
		init();
		if(xp == 0) {
			nextXp = levelMinimum;
		} else {
			nextXp = xp * levelFactor;
		}
	}
	
	public static void init() {
		if(levelUp == null || level == 0) return;
		
		maxCooldown = (int) (maxCooldown * Math.pow(levelUp.getCooldownMultiplier(), level));
		maxAct = (int) (maxAct * Math.pow(levelUp.getActivatedMultiplier(), level));
	}
	
	public void setMaxCooldown(int _maxCooldown) {
		this.maxCooldown = _maxCooldown;
	}
	
	public void setMaxActivatedTime(int _maxAct) {
		this.maxAct = _maxAct;
	}
	
	public void setLevelUp(LevelUp _levelUp) {
		this.levelUp = _levelUp;
	}
	
	public void setLevelFactor(double _levelFactor) {
		this.levelFactor = _levelFactor;
	}
	
	public void setLevelMinimum(double _levelMinimum) {
		this.levelMinimum = _levelMinimum;
	}
	
	public void setXpPerTick(double _xpPerTick) {
		this.xpPerTick = _xpPerTick;
	}
	
	public static double getXp() {
		return xp;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public static void addXp(double add) {
		xp += add;
	}
	
	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}
	
	public void setAct(int act) {
		this.act = act;
	}
	
	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	
	public void setAviable(boolean aviable) {
		this.aviable = aviable;
	}
	
	public boolean isUsable() {
		return isUsable;
	}
	
	public void setUsable(boolean usable) {
		isUsable = usable;
	}
	
	public NBTTagCompound toNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		
		tag.setString("name", name);
		
		tag.setBoolean("activated", activated);
		tag.setBoolean("aviable", aviable);
		
		//Level Stuff
		tag.setDouble("xp", xp);
		tag.setDouble("xpPerTick", xpPerTick);
		tag.setInteger("level", level);
		tag.setDouble("levelFactor", levelFactor);
		tag.setDouble("levelMinimum", levelMinimum);
		
		//Cooldown
		tag.setInteger("maxCooldown", maxCooldown);
		tag.setInteger("cooldown", cooldown);
		
		//Activation Time
		tag.setInteger("maxAct", maxAct);
		tag.setInteger("act", act);
		
		return tag;
	}
	
}
