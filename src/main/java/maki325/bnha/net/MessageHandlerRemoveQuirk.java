package maki325.bnha.net;

import maki325.bnha.BnHA;
import maki325.bnha.api.Quirk;
import maki325.bnha.capability.IQuirk;
import maki325.bnha.capability.providers.QuirkProvider;
import maki325.bnha.net.messages.MessageRemoveQuirk;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageHandlerRemoveQuirk implements IMessageHandler<MessageRemoveQuirk, IMessage> {

	@Override
	public IMessage onMessage(MessageRemoveQuirk message, MessageContext ctx) {
		
		if (ctx.side != Side.SERVER) {
			System.err.println("AirstrikeMessageToServer received on wrong side:" + ctx.side);
			return null;
	    }
	    if (!message.isMessageValid()) {
	    	System.err.println("AirstrikeMessageToServer was invalid" + message.toString());
	    	return null;
	    }

	    final EntityPlayerMP sendingPlayer = ctx.getServerHandler().player;
	    if (sendingPlayer == null) {
	    	System.err.println("EntityPlayerMP was null when AirstrikeMessageToServer was received");
	    	return null;
	    }
	    
	    final WorldServer playerWorldServer = sendingPlayer.getServerWorld();
	    playerWorldServer.addScheduledTask(new Runnable() {
	    	public void run() {
	    		EntityPlayer player = playerWorldServer.getPlayerEntityByName(message.getPlayerName());
	    		IQuirk iquirk = player.getCapability(QuirkProvider.QUIRK_CAP, null);
	    		message.getQuirks().forEach(q -> q.setUsable(!q.isUsable()));
	    	}
	    });
		
		return null;
	}

}
