package maki325.bnha.net.points;

import maki325.bnha.BnHA;
import maki325.bnha.capability.quirk.IQuirk;
import maki325.bnha.capability.quirk.providers.QuirkProvider;
import maki325.bnha.net.quirk.messages.MessageChangeQuirk;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageHandlerChangePointsServer implements IMessageHandler<MessageChangePoints, IMessage> {

	@Override
	public IMessage onMessage(MessageChangePoints message, MessageContext ctx) {

		System.out.println("PRTCtA");
		
		if (ctx.side != Side.SERVER) {
			System.err.println("MessageChangeQuirk received on wrong side:" + ctx.side + ". Supposed to arrive on server side");
			return null;
		}
	    
	    final EntityPlayerMP sendingPlayer = ctx.getServerHandler().player;
	    if (sendingPlayer == null) {
	    	System.err.println("EntityPlayerMP was null when MessageChangeQuirk was received");
	    	return null;
	    }
	    
	    final WorldServer playerWorldServer = sendingPlayer.getServerWorld();
	    playerWorldServer.addScheduledTask(new Runnable() {
	    	public void run() {

	    		EntityPlayerMP player = sendingPlayer.mcServer.getPlayerList().getPlayerByUsername(message.getPlayeName());
	    		
	    		if(player == null) {
	    			sendingPlayer.sendMessage(new TextComponentString(TextFormatting.RED + "Player with the name " + message.getPlayeName() + " does not exist"));
	    			return;
	    		}
	    		
	    		IQuirk iquirk = player.getCapability(QuirkProvider.QUIRK_CAP, null);
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
		    			sendingPlayer.sendMessage(new TextComponentString("Player " + player.getName() + " has " + iquirk.getPoints() + " points."));
		    			return;
	    			default:
	    				break;
	    		}
	    		
	    		BnHA.proxy.simpleNetworkWrapper.sendTo(new MessageChangePoints(message.getPoints(), message.getChange(), message.getPlayeName()), player);
	    		
	    	}
	    });
	    
		return null;
	}

}
