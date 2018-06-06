package maki325.bnha.init;

import java.util.ArrayList;
import java.util.List;

import maki325.bnha.api.Quirk;
import maki325.bnha.quirks.QuirkExplosive;
import maki325.bnha.quirks.QuirkFly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.EventBus;

public class ModQuirks {

	public static List<Quirk> QUIRKS = new ArrayList<Quirk>();
	
	//Quirks
	public static Quirk explosive;
	public static Quirk fly;

	public static void init() {
		explosive = new QuirkExplosive();
		explosive = new QuirkFly();
	}
	
	public static void registerEvents(EventBus bus) {
		for(Quirk q:QUIRKS) {
			bus.register(q.getClass());
		}
	}
	
	public static Quirk getQuirkByName(String name) {
		for(Quirk q:QUIRKS) {
			if(q.getName() == name) {
				return q;
			}
		}
		return null;
	}

	public static void useByName(String quirk, EntityPlayer player) {
		quirk = quirk.replaceAll(" ", "");
		for(Quirk q:QUIRKS) {
			if(q.getName() == quirk || quirk == q.getName() || (q.getName().indexOf(quirk) == 0 && quirk.indexOf(q.getName()) == 0)) {
				q.onPlayerUse(player);
			}
		}
	}
	
}
