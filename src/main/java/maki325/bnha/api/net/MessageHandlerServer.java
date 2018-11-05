package maki325.bnha.api.net;

import maki325.bnha.BnHA;
import maki325.bnha.capability.quirk.IQuirk;
import maki325.bnha.capability.quirk.providers.QuirkProvider;
import maki325.bnha.net.points.MessageChangePoints;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public abstract class MessageHandlerServer<REQ extends BnHAMessage, RES extends BnHAMessage> implements IMessageHandler<REQ, RES> {
	
	@Override
	public RES onMessage(REQ message, MessageContext ctx) {

		if (ctx.side != Side.SERVER) {
			System.err.println(message.getClass().getName() + " received on wrong side:" + ctx.side + ". Supposed to arrive on server side");
			return null;
		}
	    
		if(!message.isValid()) {
			System.err.println(message.getClass().getName() + " is not valid " + message.toString() + ". on Server Side");
			return null;
		}
		
	    final EntityPlayerMP sendingPlayer = ctx.getServerHandler().player;
	    if (sendingPlayer == null) {
	    	System.err.println("EntityPlayerMP was null when " + message.getClass().getName() + " was received");
	    	return null;
	    }
	    
	    final WorldServer playerWorldServer = sendingPlayer.getServerWorld();
	    playerWorldServer.addScheduledTask(new Runnable() {
	    	public void run() {

	    		processMessage(message, ctx);
	    		
	    	}
	    });
		
		return null;
	}
	
	public abstract void processMessage(REQ message, MessageContext ctx);
	
	public EntityPlayerMP getSender(MessageContext ctx) {
		return ctx.getServerHandler().player;
	}
	
	public EntityPlayerMP getPlayerFromName(MessageContext ctx, String name) {
		final EntityPlayerMP sendingPlayer = getSender(ctx);
	    if (sendingPlayer == null) {
	    	System.err.println("EntityPlayerMP was null when message was received");
	    	return null;
	    }
		return sendingPlayer.mcServer.getPlayerList().getPlayerByUsername(name);
	}

}
