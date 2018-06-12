package maki325.bnha.net;

import maki325.bnha.init.ModQuirks;
import maki325.bnha.net.messages.MessageClientStuff;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageHandlerQuirkOnClient implements IMessageHandler<MessageClientStuff, IMessage> {

	@Override
	public IMessage onMessage(MessageClientStuff message, MessageContext ctx) {

		if (ctx.side != Side.CLIENT) {
			System.err.println("QuirkMessage received on wrong side:" + ctx.side);
			return null;
		}
		
	    if (!message.isMessageValid()) {
			System.err.println("QuirkMessage was invalid " + message.toString());
			return null;
	    }
	    
	    Minecraft minecraft = Minecraft.getMinecraft();
	    final WorldClient worldClient = minecraft.world;
	    minecraft.addScheduledTask(new Runnable()
	    {
	      public void run() {
	        processMessage(worldClient, message);
	      }
	    });
		
		return null;
	}

	void processMessage(WorldClient worldClient, MessageClientStuff message) {
		ModQuirks.getQuirkByName(message.getQuirkName()).onClient(worldClient, message.getX(), message.getY(), message.getZ());
	}

}
