package maki325.bnha.util.handlers;

import java.util.Random;

import maki325.bnha.BnHA;
import maki325.bnha.api.Quirk;
import maki325.bnha.api.QuirkRegistry;
import maki325.bnha.capability.quirk.IQuirk;
import maki325.bnha.capability.quirk.providers.QuirkProvider;
import maki325.bnha.net.playerjoin.messages.MessageAddQuirk;
import maki325.bnha.net.points.MessageChangePoints;
import maki325.bnha.net.points.MessageChangePoints.Change;
import maki325.bnha.quirks.QuirkNone;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;

public class EventHandlerServer {

	@SubscribeEvent
	public static void onPlayerJoin(PlayerLoggedInEvent event) {
		EntityPlayer player = event.player;
		IQuirk iquirk = player.getCapability(QuirkProvider.QUIRK_CAP, null);
		if(iquirk.getQuirks().isEmpty()) {
			int i = new Random().nextInt(QuirkRegistry.getQuirks().size()*3+1);
			
			if(i < QuirkRegistry.getQuirks().size()*3) {
				Quirk q = QuirkRegistry.getQuirks().get(i%3);
				q.reset();
				try {
					iquirk.addQuirks(q.getClass().newInstance());
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | SecurityException e) {
					iquirk.addQuirks(q);
				}
				player.sendMessage(new TextComponentString("You got a quirk.It's " + q.getId().getResourcePath()));
				
			} else {
				Quirk q = new QuirkNone();
				iquirk.addQuirks(q);
				event.player.sendMessage(new TextComponentString("Bad luck. You got no quirks. Maybe you can find some in the wild"));
			}
		}
		iquirk.getQuirks().get(0).check();
		iquirk.getQuirks().get(0).init();
		
		MinecraftForge.EVENT_BUS.register(iquirk.getQuirks().get(0));
		
		player.sendMessage(new TextComponentString("Your quirk is " + iquirk.getQuirks().get(0).getId().getResourcePath() + ", level: " + iquirk.getQuirks().get(0).getLevel()));
		
		BnHA.proxy.simpleNetworkWrapper.sendTo(new MessageAddQuirk(iquirk.getQuirks().get(0)), (EntityPlayerMP) player);
		BnHA.proxy.simpleNetworkWrapper.sendTo(new MessageChangePoints(iquirk.getPoints(), Change.SET, player.getName()), (EntityPlayerMP) player);
	}
	
	@SubscribeEvent
	public static void onPlayerLeave(PlayerLoggedOutEvent event) {
		EntityPlayer player = event.player;
		IQuirk iquirk = player.getCapability(QuirkProvider.QUIRK_CAP, null);
		if(!iquirk.getQuirks().isEmpty())
			MinecraftForge.EVENT_BUS.unregister(iquirk.getQuirks().get(0));
	}
	
	@SubscribeEvent 
	public static void onPlayerClone(PlayerEvent.Clone event) {
		if(!event.isWasDeath()) return;
		EntityPlayer player = event.getEntityPlayer();
		IQuirk qurik = player.getCapability(QuirkProvider.QUIRK_CAP, null); 
		IQuirk oldQuirk = event.getOriginal().getCapability(QuirkProvider.QUIRK_CAP, null); 
	
		oldQuirk.getQuirks().forEach(q -> qurik.addQuirks(q));
	}
	
}
