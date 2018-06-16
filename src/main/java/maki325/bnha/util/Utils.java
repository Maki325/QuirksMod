package maki325.bnha.util;

import maki325.bnha.api.Quirk;
import maki325.bnha.init.ModQuirks;
import net.minecraft.nbt.NBTTagCompound;

public class Utils {

	public static Quirk nbtToQuirk(NBTTagCompound tag) {
		Quirk quirk = ModQuirks.getQuirkByName(tag.getString("name"));
		
		quirk.setActivated(tag.getBoolean("activated"));
		quirk.setAviable(tag.getBoolean("aviable"));
		
		//Level Stuff
		quirk.setXp(tag.getDouble("xp"));
		quirk.setXpPerTick(tag.getDouble("xpPerTick"));
		
		quirk.setLevel(tag.getInteger("level"));
		quirk.setLevelFactor(tag.getDouble("levelFactor"));
		quirk.setLevelMinimum(tag.getDouble("levelMinimum"));
		
		//Cooldown
		quirk.setMaxCooldown(tag.getInteger("maxCooldown"));
		quirk.setCooldown(tag.getInteger("cooldown"));

		//Activation Time
		quirk.setMaxActivatedTime(tag.getInteger("maxAct"));
		quirk.setAct(tag.getInteger("act"));
		
		return quirk;
	}
	
}
