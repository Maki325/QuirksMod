package maki325.bnha.api;

import maki325.bnha.init.ModQuirks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionAttackDamage;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

public abstract class Quirk {

	protected String name;

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

	@SubscribeEvent
	public abstract void onPlayerUse(EntityPlayer player);
	
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
		init();
		if(xp == 0) {
			nextXp = levelMinimum;
		} else {
			nextXp = xp * levelFactor;
		}
	}
	
	protected static void init() {
		if(levelUp == null) return;
		
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
	
	public static void addXp(double add) {
		xp += add;
	}
	
}
