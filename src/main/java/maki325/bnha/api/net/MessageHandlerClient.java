package maki325.bnha.api.net;

import maki325.bnha.capability.quirk.IQuirk;
import maki325.bnha.capability.quirk.providers.QuirkProvider;
import maki325.bnha.net.points.MessageChangePoints;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public abstract class MessageHandlerClient<REQ extends BnHAMessage, RES extends BnHAMessage> implements IMessageHandler<REQ, RES>  {

	@Override
	public RES onMessage(REQ message, MessageContext ctx) {
		
		if (ctx.side != Side.CLIENT) {
			System.err.println(message.getClass().getName() + " received on wrong side:" + ctx.side + ". Supposed to arrive on client side");
			return null;
		}
		
		if(!message.isValid()) {
			System.err.println(message.getClass().getName() + " is not valid " + message.toString() + ". On client Side");
			return null;
		}
	    
		Minecraft minecraft = Minecraft.getMinecraft();
	    final WorldClient worldClient = minecraft.world;
	    
	    minecraft.addScheduledTask(new Runnable() {
	    	public void run() {
	    		
	    		processMessage(message, ctx);
	    		
	    	}
	    });
		
		return null;
	}
	
	public abstract void processMessage(REQ message, MessageContext ctx);
	
}
