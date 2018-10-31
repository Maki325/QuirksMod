package maki325.bnha.net.quirk;

import maki325.bnha.net.quirk.messages.MessageActivateClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageHandlerActivateClient implements IMessageHandler<MessageActivateClient, IMessage> {

	@Override
	public IMessage onMessage(MessageActivateClient message, MessageContext ctx) {
		
		if (ctx.side != Side.CLIENT) {
			System.err.println("MessageActivateClient received on wrong side:" + ctx.side);
			return null;
		}
		
		if(!message.isValid()) {
			System.err.println("MessageActivateClient is not valid " + message.toString());
			return null;
		}
	    
		Minecraft minecraft = Minecraft.getMinecraft();
	    final WorldClient worldClient = minecraft.world;
	    
	    minecraft.addScheduledTask(new Runnable() {
	    	public void run() {
	    		
	    		message.getQuirk().onClient(worldClient, message.getX(), message.getY(), message.getZ());
	    		
	    	}
	    });
	    
		return null;
	}


}
