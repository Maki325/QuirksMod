package maki325.bnha;

import java.util.Iterator;
import java.util.Random;

import maki325.bnha.api.Quirk;
import maki325.bnha.init.KeyBindings;
import maki325.bnha.init.ModQuirks;
import maki325.bnha.net.MessageQuirk;
import maki325.bnha.proxy.CommonProxy;
import maki325.bnha.util.Reference;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = "required-after:forge@[14.23.4.2705,)", acceptedMinecraftVersions = "[1.12,1.12.2]")
public class BnHA {

	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	public static CommonProxy proxy;
	
	public static String quirks = "";
	public static double xp = -1;
	
	@Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
		
		KeyBindings.init();
		
		ModQuirks.init();
		
		proxy.preInit();
	}
	
	@Mod.EventHandler
    public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
		ModQuirks.registerEvents(MinecraftForge.EVENT_BUS);
	}

	@Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

	}
	
	@SubscribeEvent
	public void onPlayerJoin(PlayerLoggedInEvent event) {
		String tag = "done_" + event.player.world.getSaveHandler().getWorldDirectory().getName();
		if(!event.player.getTags().contains(tag)) {
			event.player.addTag(tag);
			int i = new Random().nextInt(ModQuirks.QUIRKS.size()+1);
			if(i < ModQuirks.QUIRKS.size()) {
				Quirk q = ModQuirks.QUIRKS.get(i);
				event.player.addTag("quirk_" + q.getName());
				event.player.addTag("qxp_0");
				quirks = q.getName();
				event.player.sendMessage(new TextComponentString("You got a quirk.It's " + q.getName()));
			} else {
				event.player.addTag("quirk_none");
				event.player.addTag("qxp_0");
				quirks = "none";
				event.player.sendMessage(new TextComponentString("Bad luck. You got no quirks. Maybe you can find some in the wild"));
			}
			xp = 0;
		} else {
			for(String s:event.player.getTags()) {
				if(s.startsWith("quirk_")) {
					quirks = s.split("_")[1];
				}
			}
			if(quirks == "") {
				quirks = "none";
			} else {
				for(String s:event.player.getTags()) {
					if(s.startsWith("qxp_")) {
						xp = Double.parseDouble(s.split("_")[1]);
						ModQuirks.getQuirkByName(quirks.replaceAll(" ", "")).setXp(xp);
					}
				}
				if(xp == -1) {
					event.player.addTag("qxp_0");
					xp = 0;
				}
			}
		}
	}

	@SubscribeEvent
	public void leave(PlayerLoggedOutEvent event) {
		for(Iterator<String> it = event.player.getTags().iterator(); it.hasNext();){
		    String s = it.next();
			if(s.startsWith("qxp_")) {
				it.remove();
			}
		}
		event.player.addTag("qxp_" + ModQuirks.getQuirkByName(quirks).getXp());
		xp = -1;
		quirks = "";
	}
	
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if(KeyBindings.activate.isPressed()){
			String quirk = quirks;
			
			if(quirk != "none") {
				proxy.simpleNetworkWrapper.sendToServer(new MessageQuirk(quirk));
			}
		}
	}

}
