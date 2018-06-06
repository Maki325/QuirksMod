package maki325.bnha.net;

import maki325.bnha.init.ModQuirks;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageHandlerOnServer implements IMessageHandler<MessageQuirk, IMessage> {

	@Override
	public IMessage onMessage(MessageQuirk message, MessageContext ctx) {

		if (ctx.side != Side.SERVER) {
			System.err.println("QuirkMessage received on wrong side:" + ctx.side);
			return null;
		}
		
	    if (!message.isMessageValid()) {
			System.err.println("QuirkMessage was invalid " + message.toString());
			return null;
	    }
	    
	    final EntityPlayerMP sendingPlayer = ctx.getServerHandler().player;
	    if (sendingPlayer == null) {
	    	System.err.println("EntityPlayerMP was null when QuirkMessage was received");
	    	return null;
	    }
	    
	    final WorldServer playerWorldServer = sendingPlayer.getServerWorld();
	    playerWorldServer.addScheduledTask(new Runnable() {
	    	public void run() {
	    		processMessage(message, sendingPlayer);
	    	}
	    });
		
		return null;
	}

	void processMessage(MessageQuirk message, EntityPlayerMP sendingPlayer) {
		
		ModQuirks.useByName(message.getQuirkName(), sendingPlayer);
		
	}

}
