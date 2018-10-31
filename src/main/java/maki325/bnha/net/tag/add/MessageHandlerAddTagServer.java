package maki325.bnha.net.tag.add;

import maki325.bnha.BnHA;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageHandlerAddTagServer implements IMessageHandler<MessageAddTag, IMessage> {

	@Override
	public IMessage onMessage(MessageAddTag message, MessageContext ctx) {
		
		if (ctx.side != Side.SERVER) {
			System.err.println("MessageAddTag received on wrong side:" + ctx.side);
			return null;
		}
		
		if(!message.isValid()) {
			System.err.println("MessageAddTag is not valid " + message.toString());
			return null;
		}
	    
	    final EntityPlayerMP sendingPlayer = ctx.getServerHandler().player;
	    if (sendingPlayer == null) {
	    	System.err.println("EntityPlayerMP was null when MessageAddTag was received");
	    	return null;
	    }
	    
	    final WorldServer playerWorldServer = sendingPlayer.getServerWorld();
	    playerWorldServer.addScheduledTask(new Runnable() {
	    	public void run() {

	    		EntityPlayerMP player = sendingPlayer.mcServer.getPlayerList().getPlayerByUsername(message.getPlayer());
	    		BnHA.proxy.simpleNetworkWrapper.sendTo(message, player);
	    		player.addTag(message.getTag());
	    		
	    	}
	    });
	    
		return null;
	}

}
