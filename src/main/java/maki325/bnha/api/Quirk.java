package maki325.bnha.api;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import maki325.bnha.BnHA;
import maki325.bnha.api.skilltree.Skilltree;
import maki325.bnha.net.progress.active.MessageActiveProgress;
import maki325.bnha.net.progress.cooldown.MessageCooldownProgress;
import maki325.bnha.net.quirk.messages.MessageChangeQuirk;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class Quirk {

	protected ResourceLocation id;
	protected boolean isUsable = true;

	protected EntityPlayerMP p = null;
	
	protected double cooldown = 0;
	protected double maxCooldown = 0;
	
	private int cooldownProgress = 0;
	private int cooldownProgressLast = 0;
	
	private int activeProgress = 0;
	private int activeProgressLast = 0;

	protected double act = 0;
	protected double maxAct = 0;
	
	protected boolean activated = false;
	protected boolean aviable = true;
	
	public double xp = 0;
	public double nextXp = 0;
	protected double xpPerTick = 0;
	
	public int level = 0;
	
	protected double levelFactor = 0;
	protected double levelMinimum = 0;
	
	protected static LevelUp levelUp = null;
	
	/**
	 * 
	 * @param name - name of the {@link Quirk}(small letters)
	 * @param modId - id of the mod
	 */
	public Quirk(String name, String modId) {
		this.id = new ResourceLocation(modId, name);
	}

	/**
	 * Function called every time player uses a quirk. Should call update function when changing the quirk(level, xp, etc)
	 * @param player - player whose quirk should be activated
	 */
	public abstract void onPlayerUse(EntityPlayerMP player);
	
	@SideOnly(Side.CLIENT)
	public void onClient(WorldClient worldClient, double x, double y, double z) {}
	
	@SubscribeEvent
	public void tickTock(ServerTickEvent event) {
		tick();
		
		if(p == null) {
			return;
		}
		
		if(!aviable) {
			cooldownTick();
		} else {
			cooldownProgress = 100;
			if(cooldownProgress != cooldownProgressLast) {
				cooldownProgressLast = cooldownProgress;
				BnHA.proxy.simpleNetworkWrapper.sendTo(new MessageCooldownProgress(cooldownProgress), p);
			}
		}
		
		if(activated) {
			activateTick();
		} else {
			activeProgress = 100;
			if(activeProgress != activeProgressLast) {
				activeProgressLast = activeProgress;
				BnHA.proxy.simpleNetworkWrapper.sendTo(new MessageActiveProgress(activeProgress), p);
			}
		}
		
		
	}
	
	private void activateTick() {
		if(act == 0) {
			return;
		}
		
		double tmp = (double) act/maxAct;
		activeProgress = (int) (tmp * 100);

		if(activeProgress == activeProgressLast) return;
		
		activeProgressLast = activeProgress;
		
		BnHA.proxy.simpleNetworkWrapper.sendTo(new MessageActiveProgress(activeProgress), p);
	}
	
	private void cooldownTick() {
		if(cooldown == 0) {
			return;
		}
		
		double tmp = (double) cooldown/maxCooldown;
		cooldownProgress = (int) (tmp * 100);

		if(cooldownProgress == cooldownProgressLast) return;
		
		cooldownProgressLast = cooldownProgress;
		
		BnHA.proxy.simpleNetworkWrapper.sendTo(new MessageCooldownProgress(cooldownProgress), p);
	}
	
	public abstract void tick();
	
	public int getCooldownProgress() {
		return cooldownProgress;
	}
	
	public void setCooldownProgress(int cooldownProgress) {
		this.cooldownProgress = cooldownProgress;
	}
	
	public int getActiveProgress() {
		return activeProgress;
	}
	
	public void setActiveProgress(int activeProgress) {
		this.activeProgress = activeProgress;
	}
	
	public ResourceLocation getId() {
		return id;
	}

	public String getName() {
		return id.getResourcePath();
	}
	
	public int xpToLevel(double xp) {
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
	
	protected void setXp(double xp) {
		this.xp = xp;
	}
	
	public double getLevelFactor() {
		return levelFactor;
	}
	
	public void init() {
		if(levelUp == null || level == 0) return;
		
		maxCooldown = (int) (maxCooldown * Math.pow(levelUp.getCooldownMultiplier(), level));
		maxAct = (int) (maxAct * Math.pow(levelUp.getActivatedMultiplier(), level));
	}
	
	public void reset() {
		level = 1;
		xp = 0;
		nextXp = levelMinimum;
		init();
	}
	
	public double getLevelMinimum() {
		return levelMinimum;
	}
	
	public void check() {
		if(nextXp == 0) {
			nextXp = 100;
			level = 1;
		}
		level = Math.max(1, level);
	}
	
	protected void setMaxCooldown(int _maxCooldown) {
		this.maxCooldown = _maxCooldown;
	}
	
	protected void setMaxActivatedTime(int _maxAct) {
		this.maxAct = _maxAct;
	}
	
	protected void setLevelUp(LevelUp _levelUp) {
		this.levelUp = _levelUp;
	}
	
	protected void setLevelFactor(double _levelFactor) {
		this.levelFactor = _levelFactor;
	}
	
	protected void setLevelMinimum(double _levelMinimum) {
		this.levelMinimum = _levelMinimum;
	}
	
	protected void setXpPerTick(double _xpPerTick) {
		this.xpPerTick = _xpPerTick;
	}
	
	public double getXp() {
		return xp;
	}
	
	protected void setLevel(int level) {
		this.level = level;
	}
	
	protected void addXp(double add) {
		xp += add;
	}
	
	protected void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}
	
	protected void setAct(int act) {
		this.act = act;
	}
	
	protected void setActivated(boolean activated) {
		this.activated = activated;
	}
	
	protected void setAviable(boolean aviable) {
		this.aviable = aviable;
	}
	
	public boolean isUsable() {
		return isUsable;
	}
	
	public void setUsable(boolean usable) {
		isUsable = usable;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public double getNextXP() {
		return this.nextXp;
	}
	
	public void setNextXP(double nextXp) {
		this.nextXp = nextXp;
	}
	
	public void setP(EntityPlayerMP p) {
		this.p = p;
	}
	
	protected abstract NBTTagCompound save();
	
	protected abstract void load(NBTTagCompound tag);
	
	public NBTTagCompound toNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		
		tag.setString("name", id.getResourcePath());
		
		tag.setBoolean("activated", activated);
		tag.setBoolean("aviable", aviable);
		
		//Level Stuff
		tag.setDouble("xp", xp);
		tag.setDouble("xpPerTick", xpPerTick);
		tag.setInteger("level", level);
		tag.setDouble("levelFactor", levelFactor);
		tag.setDouble("levelMinimum", levelMinimum);
		tag.setDouble("nextXp", nextXp);
		
		//Cooldown
		tag.setInteger("maxCooldown", (int) maxCooldown);
		tag.setInteger("cooldown", (int) cooldown);
		
		//Activation Time
		tag.setInteger("maxAct", (int) maxAct);
		tag.setInteger("act", (int) act);
		
		//Is type of Quirk
		//(q.getClass().getSuperclass() == QuirkRegistry.getQuirkInstances().get(0)
		/*for(Class<? extends Quirk> q:QuirkRegistry.getQuirkInstances()) {
			if(this.getClass().getSuperclass() == q) {
				tag.setBoolean("is" + q.getClass().getName(), true);
				try {
					Method m = this.getClass().getMethod("skillQuirkToNBT");
					System.out.println("METHOD INVOKED toNBT");
					tag.merge((NBTTagCompound) m.invoke(q));
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			} else {
				tag.setBoolean("is" + q.getClass().getName(), false);
			}
		}*/ 
		//tag.setBoolean("isSkill", (this instanceof QuirkSkill));
		
		tag.merge(save());
		
		return tag;
	}
	
	//Functions that need to be static or need Quirk Function
	
	public static Quirk nbtToQuirk(NBTTagCompound tag) {
		Quirk quirk = QuirkRegistry.getQuirkByName(tag.getString("name"));
		if(quirk == null) return null;
		
		/*for(Class<? extends Quirk> q:QuirkRegistry.getQuirkInstances()) {
			if(tag.getBoolean("is" + q.getClass().getName())) {
				try {
					Method m = quirk.getClass().getMethod("skillQuirkNbtToQuirk", NBTTagCompound.class);
					System.out.println("METHOD INVOKED");
					return (Quirk) m.invoke(quirk, tag);
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}*/
		/*if(tag.getBoolean("isSkill")) {
			return QuirkSkill.nbtToQuirk(tag);
		}*/
		
		quirk.setActivated(tag.getBoolean("activated"));
		quirk.setAviable(tag.getBoolean("aviable"));
		
		//Level Stuff
		quirk.setXp(tag.getDouble("xp"));
		quirk.setXpPerTick(tag.getDouble("xpPerTick"));
		
		quirk.setLevel(tag.getInteger("level"));
		quirk.setLevelFactor(tag.getDouble("levelFactor"));
		quirk.setLevelMinimum(tag.getDouble("levelMinimum"));
		quirk.setNextXP(tag.getDouble("nextXp"));
		
		//Cooldown
		quirk.setMaxCooldown(Math.max(tag.getInteger("maxCooldown"), 200));
		quirk.setCooldown(tag.getInteger("cooldown"));

		//Activation Time
		quirk.setMaxActivatedTime(Math.max(tag.getInteger("maxAct"), 200));
		quirk.setAct(tag.getInteger("act"));
		
		quirk.load(tag);
		
		return quirk;
	}
	
	public static Quirk quirkLevel(String name, int level) {
		Quirk q = QuirkRegistry.getQuirkByName(name);
		q.setLevel((level <= 0) ? 1 : level);
		
		return q;
	}
	
	public boolean isSimilar(Quirk q) {
		return getName().equalsIgnoreCase(q.getName());
	}
	
	public static boolean isSimilar(Quirk q1, Quirk q2) {
		return q1.getName().equalsIgnoreCase(q2.getName());
	}
	
	@Override
	public String toString() {
		return id.toString() + " - Level: " + level + ", XP: " + xp;
	}
	
}
