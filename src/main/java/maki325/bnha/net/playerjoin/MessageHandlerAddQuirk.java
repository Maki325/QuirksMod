package maki325.bnha.net.playerjoin;

import maki325.bnha.capability.quirk.IQuirk;
import maki325.bnha.capability.quirk.providers.QuirkProvider;
import maki325.bnha.gui.hud.GuiHud;
import maki325.bnha.net.playerjoin.messages.MessageAddQuirk;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageHandlerAddQuirk implements IMessageHandler<MessageAddQuirk, IMessage> {

	@Override
	public IMessage onMessage(MessageAddQuirk message, MessageContext ctx) {
		
		if (ctx.side != Side.CLIENT) {
			System.err.println("MessageAddQuirk received on wrong side:" + ctx.side);
			return null;
		}
		
		if(!message.isValid()) {
			System.err.println("MessageAddQuirk is not valid " + message.toString());
			return null;
		}
	    
		Minecraft minecraft = Minecraft.getMinecraft();
	    final WorldClient worldClient = minecraft.world;
	    
	    minecraft.addScheduledTask(new Runnable() {
	    	public void run() {
	    		
	    		IQuirk iquirk = minecraft.player.getCapability(QuirkProvider.QUIRK_CAP, null);
	    		iquirk.addQuirks(message.getQuirk());
	    		
	    	}
	    });
	    
		return null;
	}


}
