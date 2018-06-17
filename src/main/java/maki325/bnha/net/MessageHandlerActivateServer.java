package maki325.bnha.net;

import maki325.bnha.BnHA;
import maki325.bnha.api.Quirk;
import maki325.bnha.capability.IQuirk;
import maki325.bnha.capability.providers.QuirkProvider;
import maki325.bnha.net.messages.MessageActivate;
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
	    		if(q.isUsable()) {
	    		q.onPlayerUse(sendingPlayer);
	    		BnHA.proxy.simpleNetworkWrapper.sendToDimension(new MessageActivate(), sendingPlayer.dimension);
	    		}
	    		
	    	}
	    });
	    
		return null;
	}

}
