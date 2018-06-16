package maki325.bnha.net;

import maki325.bnha.BnHA;
import maki325.bnha.capability.IQuirk;
import maki325.bnha.capability.providers.QuirkProvider;
import maki325.bnha.net.messages.MessageActivate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageHandlerActivateClient implements IMessageHandler<MessageActivate, IMessage> {

	@Override
	public IMessage onMessage(MessageActivate message, MessageContext ctx) {
		
		if (ctx.side != Side.CLIENT) {
			System.err.println("MessageActivate received on wrong side:" + ctx.side);
			return null;
		}
	    
		Minecraft minecraft = Minecraft.getMinecraft();
	    final WorldClient worldClient = minecraft.world;
	    
	    minecraft.addScheduledTask(new Runnable() {
	    	public void run() {
	    		
	    		EntityPlayer player = BnHA.proxy.getPlayer(ctx);
	    		IQuirk iquirk = player.getCapability(QuirkProvider.QUIRK_CAP, null);
	    		iquirk.getQuirks().get(0).onClient(worldClient, player.posX, player.posY, player.posZ);
	    		
	    	}
	    });
	    
		return null;
	}


}
