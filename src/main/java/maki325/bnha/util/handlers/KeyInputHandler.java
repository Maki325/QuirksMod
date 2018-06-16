package maki325.bnha.util.handlers;

import java.util.Random;

import maki325.bnha.BnHA;
import maki325.bnha.api.Quirk;
import maki325.bnha.capability.IQuirk;
import maki325.bnha.capability.providers.QuirkProvider;
import maki325.bnha.init.KeyBindings;
import maki325.bnha.init.ModQuirks;
import maki325.bnha.net.messages.MessageActivate;
import maki325.bnha.quirks.QuirkNone;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class KeyInputHandler {

	@SubscribeEvent
	public static void onKeyInput(PlayerTickEvent event) {
		if(KeyBindings.activate.isPressed()){
			System.out.println("KEY F");
			BnHA.proxy.simpleNetworkWrapper.sendToServer(new MessageActivate());
		}
	}
	
	@SubscribeEvent
	public static void onPlayerJoin(PlayerLoggedInEvent event) {
		EntityPlayer player = event.player;
		IQuirk iquirk = player.getCapability(QuirkProvider.QUIRK_CAP, null);
		System.out.println("IS_EMPTY: " + iquirk.getQuirks().isEmpty());
		if(iquirk.getQuirks().isEmpty()) {
			int i = new Random().nextInt(ModQuirks.QUIRKS.size()*3+1);
			
			if(i < ModQuirks.QUIRKS.size()*3) {
				Quirk q = ModQuirks.QUIRKS.get(i%ModQuirks.QUIRKS.size());
				q.setLevel(0);
				q.setXp(0);
				q.init();
				
				try {
					iquirk.addQuirks(q.getClass().newInstance());
				} catch (InstantiationException | IllegalAccessException e) {
					iquirk.addQuirks(q);
				}
				player.sendMessage(new TextComponentString("You got a quirk.It's " + q.getName()));
				
			} else {
				Quirk q = new QuirkNone();
				iquirk.addQuirks(q);
				event.player.sendMessage(new TextComponentString("Bad luck. You got no quirks. Maybe you can find some in the wild"));
			}
			
			System.out.println("QUIRK: " + iquirk.getQuirks().get(0));
			System.out.println("IS_EMPTY: " + iquirk.getQuirks().isEmpty());
		}
		iquirk.getQuirks().get(0).init();
		MinecraftForge.EVENT_BUS.register(iquirk.getQuirks().get(0).getClass());
	}
	
	@SubscribeEvent
	public static void onPlayerLeave(PlayerLoggedOutEvent event) {
		EntityPlayer player = event.player;
		IQuirk iquirk = player.getCapability(QuirkProvider.QUIRK_CAP, null);
		MinecraftForge.EVENT_BUS.unregister(iquirk.getQuirks().get(0).getClass());
	}
	
	
}
