package maki325.bnha.api.quirk;

import maki325.bnha.api.ResourceIdentifier;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.WorldServer;

public class LevelableQuirk extends Quirk {

	private int xp = 0, nextXp = 10, level = 0;
	private int xpPerUse = 10;
	
	public LevelableQuirk(ResourceIdentifier id) {
		super(id);
	}

	public LevelableQuirk(String name, String modId) {
		super(new ResourceIdentifier(modId, name));
	}

	@Override
	public void onPlayerUse(EntityPlayerMP player, WorldServer world) {
		this.xp += this.xpPerUse;
		if(this.xp >= this.nextXp) { 
			this.xp = 0;
			this.level++;
			this.nextXp *= this.level;
			player.sendMessage(new TextComponentString("You leveled up: " + level));
		}
	}

	@Override
	public NBTTagCompound save() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("xp", this.xp);
		tag.setInteger("nextXp", this.nextXp);
		tag.setInteger("level", this.level);
		tag.setInteger("xpPerUse", this.xpPerUse);
		return tag;
	};
	
	@Override
	public void load(NBTTagCompound tag) {
		this.xp = tag.getInteger("xp");
		this.nextXp = tag.getInteger("nextXp");
		this.level = tag.getInteger("level");
		this.xpPerUse = Math.max(tag.getInteger("xpPerUse"), 10);
	};

	public void setXpPerUse(int xp) { this.xpPerUse = Math.max(xp, 10); }
	public int getXpPerUse() { return this.xpPerUse; }
	
	public int getXp() { return this.xp; }
	public int getNextXp() { return this.nextXp; }
	public int getLevel() { return this.level; }
	
	@Override
	public String toString() {
		return "Quirk - ID: " + getId() + ", Level: " + level + ", XP: " + xp;
	}

}
