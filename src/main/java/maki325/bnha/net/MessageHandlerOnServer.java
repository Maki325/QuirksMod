package maki325.bnha.net;

import maki325.bnha.init.ModQuirks;
import maki325.bnha.net.messages.MessageClientStuff;
import maki325.bnha.net.messages.MessageParticle;
import maki325.bnha.net.messages.MessageQuirk;
import maki325.bnha.proxy.CommonProxy;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumParticleTypes;
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
		
		int dimension = sendingPlayer.dimension;
		MinecraftServer minecraftServer = sendingPlayer.mcServer;
		
		for (EntityPlayerMP player : minecraftServer.getPlayerList().getPlayers()) {
			MessageClientStuff msg = new MessageClientStuff(message.getQuirkName(), sendingPlayer.posX, sendingPlayer.posY, sendingPlayer.posZ);
			//MessageParticle msg = new MessageParticle(sendingPlayer.posX, sendingPlayer.posY, sendingPlayer.posZ, EnumParticleTypes.DRAGON_BREATH.getParticleID());
			if (dimension == player.dimension) {
				CommonProxy.simpleNetworkWrapper.sendTo(msg, player);
			}
	    }
		
	}

}
