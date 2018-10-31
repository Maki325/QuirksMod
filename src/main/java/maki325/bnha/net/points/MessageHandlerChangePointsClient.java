package maki325.bnha.net.points;

import maki325.bnha.capability.quirk.IQuirk;
import maki325.bnha.capability.quirk.providers.QuirkProvider;
import maki325.bnha.net.playerjoin.messages.MessageAddQuirk;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageHandlerChangePointsClient implements IMessageHandler<MessageChangePoints, IMessage> {

	@Override
	public IMessage onMessage(MessageChangePoints message, MessageContext ctx) {
		
		System.out.println("WHY THO");
		
		if (ctx.side != Side.CLIENT) {
			System.err.println("MessageChangePoints received on wrong side:" + ctx.side + ". Supposed to arrive on client side");
			return null;
		}
		
		if(!message.isValid()) {
			System.err.println("MessageChangePoints is not valid " + message.toString());
			return null;
		}
	    
		Minecraft minecraft = Minecraft.getMinecraft();
	    final WorldClient worldClient = minecraft.world;
	    
	    minecraft.addScheduledTask(new Runnable() {
	    	public void run() {
	    		
	    		IQuirk iquirk = minecraft.player.getCapability(QuirkProvider.QUIRK_CAP, null);

	    		switch(message.getChange()) {
		    		case ADD:
		    			iquirk.addPoints(message.getPoints());
		    			break;
		    		case REMOVE:
		    			iquirk.removePoints(message.getPoints());
		    			break;
		    		case SET:
		    			iquirk.setPoints(message.getPoints());
		    			break;
		    		case RESET:
		    			iquirk.addPoints(0);
		    			break;
		    		case GET:
		    			break;
	    			default:
	    				break;
	    		}
	    		
	    	}
	    });
	    
		return null;
	}


}