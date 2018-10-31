package maki325.bnha.net.quirk;

import maki325.bnha.BnHA;
import maki325.bnha.api.Quirk;
import maki325.bnha.capability.quirk.IQuirk;
import maki325.bnha.capability.quirk.providers.QuirkProvider;
import maki325.bnha.net.quirk.messages.MessageActivate;
import maki325.bnha.net.quirk.messages.MessageActivateClient;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageHandlerActivateServer implements IMessageHandler<MessageActivate, IMessage> {

	@Override
	public IMessage onMessage(MessageActivate message, MessageContext ctx) {
		
		if (ctx.side != Side.SERVER) {
			System.err.println("MessageActivate received on wrong side:" + ctx.side);
			return null;
		}
	    
	    final EntityPlayerMP sendingPlayer = ctx.getServerHandler().player;
	    if (sendingPlayer == null) {
	    	System.err.println("EntityPlayerMP was null when MessageActivate was received");
	    	return null;
	    }
	    
	    final WorldServer playerWorldServer = sendingPlayer.getServerWorld();
	    playerWorldServer.addScheduledTask(new Runnable() {
	    	public void run() {

	    		IQuirk iquirk = sendingPlayer.getCapability(QuirkProvider.QUIRK_CAP, null);
	    		Quirk q = iquirk.getQuirks().get(0);
	    		if(q == null) {
		    		System.out.println("QUIRK IS NULL");
		    		return;
	    		}
	    		if(q.isUsable()) {
		    		q.onPlayerUse(sendingPlayer);
		    		BnHA.proxy.simpleNetworkWrapper.sendToDimension(new MessageActivateClient(sendingPlayer.posX, sendingPlayer.posY, sendingPlayer.posZ, q), sendingPlayer.dimension);
	    		}
	    		
	    	}
	    });
	    
		return null;
	}

}
