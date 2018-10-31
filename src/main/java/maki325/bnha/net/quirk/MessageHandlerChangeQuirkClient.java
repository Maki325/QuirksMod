package maki325.bnha.net.quirk;

import maki325.bnha.capability.quirk.IQuirk;
import maki325.bnha.capability.quirk.providers.QuirkProvider;
import maki325.bnha.gui.hud.GuiHud;
import maki325.bnha.net.quirk.messages.MessageChangeQuirk;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageHandlerChangeQuirkClient implements IMessageHandler<MessageChangeQuirk, IMessage> {

	@Override
	public IMessage onMessage(MessageChangeQuirk message, MessageContext ctx) {
		
		if (ctx.side != Side.CLIENT) {
			System.err.println("MessageChangeQuirk received on wrong side:" + ctx.side);
			return null;
		}
		
		if(!message.isValid()) {
			System.err.println("MessageChangeQuirk is not valid " + message.toString());
			return null;
		}
	    
		Minecraft minecraft = Minecraft.getMinecraft();
	    final WorldClient worldClient = minecraft.world;
	    
	    minecraft.addScheduledTask(new Runnable() {
	    	public void run() {
	    		
	    		IQuirk iquirk = minecraft.player.getCapability(QuirkProvider.QUIRK_CAP, null);
	    		if(!iquirk.getQuirks().isEmpty())
	    			iquirk.getQuirks().set(0, message.getQuirk());
	    		else
	    			iquirk.addQuirks(message.getQuirk());

	    		if(!message.isQuiet())
	    			minecraft.player.sendMessage(new TextComponentString(TextFormatting.GREEN + "Your quirk got changed to " + TextFormatting.BOLD + message.getQuirk().getName()));
	    		
	    	}
	    });
	    
		return null;
	}


}