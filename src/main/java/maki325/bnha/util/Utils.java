package maki325.bnha.util;

import maki325.bnha.BnHA;
import maki325.bnha.api.Quirk;
import maki325.bnha.capability.IQuirk;
import maki325.bnha.capability.providers.QuirkProvider;
import maki325.bnha.init.ModQuirks;
import maki325.bnha.net.messages.MessageRemoveQuirk;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
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
	
	public static void removeQuirk(EntityPlayer player, Quirk... quirks) {
		BnHA.proxy.simpleNetworkWrapper.sendToServer(new MessageRemoveQuirk(player.getName(), quirks));
	}
	
	public static Quirk[] getPlayer(EntityPlayer player) {
		IQuirk iquirk = player.getCapability(QuirkProvider.QUIRK_CAP, null);
		
		return (Quirk[]) iquirk.getQuirks().toArray();
	}
	
	public static boolean isMouseInside(int mouseX, int mouseY, int x1, int y1, int x2, int y2) {
		return mouseX >= x1 && mouseX < x2 && mouseY >= y1 && mouseY < y2;
	}
	
}
