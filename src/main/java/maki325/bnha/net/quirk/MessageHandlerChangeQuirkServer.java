package maki325.bnha.net.quirk;

import maki325.bnha.BnHA;
import maki325.bnha.api.Quirk;
import maki325.bnha.capability.quirk.IQuirk;
import maki325.bnha.capability.quirk.providers.QuirkProvider;
import maki325.bnha.net.quirk.messages.MessageActivateClient;
import maki325.bnha.net.quirk.messages.MessageChangeQuirk;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageHandlerChangeQuirkServer implements IMessageHandler<MessageChangeQuirk, IMessage> {

	@Override
	public IMessage onMessage(MessageChangeQuirk message, MessageContext ctx) {
		
		if (ctx.side != Side.SERVER) {
			System.err.println("MessageChangeQuirk received on wrong side:" + ctx.side);
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
	    		if(!iquirk.getQuirks().isEmpty()) {
	    			MinecraftForge.EVENT_BUS.unregister(iquirk.getQuirks().get(0));
	    			iquirk.getQuirks().set(0, message.getQuirk());
	    			MinecraftForge.EVENT_BUS.register(iquirk.getQuirks().get(0));
	    		} else {
	    			iquirk.addQuirks(message.getQuirk());
	    			MinecraftForge.EVENT_BUS.register(iquirk.getQuirks().get(0));
	    		}
	    			
    			sendingPlayer.sendMessage(new TextComponentString(TextFormatting.GREEN + "You changed the quirk to " + TextFormatting.BOLD + message.getQuirk().getName()));
	    		
	    		BnHA.proxy.simpleNetworkWrapper.sendTo(new MessageChangeQuirk(message.getQuirk(), message.getPlayeName(), message.isQuiet()), player);
	    		
	    	}
	    });
	    
		return null;
	}

}
