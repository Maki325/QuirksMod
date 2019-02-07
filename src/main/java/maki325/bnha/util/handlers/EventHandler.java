package maki325.bnha.util.handlers;

import maki325.bnha.BnHA;
import maki325.bnha.api.Quirk;
import maki325.bnha.capability.quirk.IQuirk;
import maki325.bnha.capability.quirk.providers.QuirkProvider;
import maki325.bnha.gui.skilltree.GuiHandlerST;
import maki325.bnha.net.quirk.messages.MessageActivate;
import maki325.bnha.util.KeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class EventHandler {

	@SubscribeEvent
	public static void onKeyInput(ClientTickEvent event) {
		
		if(KeyBindings.activate.isPressed()){
			BnHA.proxy.simpleNetworkWrapper.sendToServer(new MessageActivate());
		}

		/*if(KeyBindings.test.isPressed()){
	    	EntityPlayer p = inecraft.getMinecraft().player;
	    	p.openGui(BnHA.instance, GuiHandlerOFA.getGuiID(), Minecraft.getMinecraft().world, p.getPosition().getX(), p.getPosition().getY(), p.getPosition().getZ());
		}*/
		
		if(KeyBindings.skilltree.isPressed()){
	    	EntityPlayer p = Minecraft.getMinecraft().player;
	    	p.openGui(BnHA.instance, GuiHandlerST.getGuiID(), Minecraft.getMinecraft().world, p.getPosition().getX(), p.getPosition().getY(), p.getPosition().getZ());
		}
		
	}
	
}
