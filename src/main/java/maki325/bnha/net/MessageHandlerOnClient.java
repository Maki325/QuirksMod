package maki325.bnha.net;

import maki325.bnha.net.messages.MessageParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageHandlerOnClient implements IMessageHandler<MessageParticle, IMessage>  {

	@Override
	public IMessage onMessage(MessageParticle message, MessageContext ctx) {

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
	
	void processMessage(WorldClient worldClient, MessageParticle message) {
		worldClient.spawnParticle(EnumParticleTypes.getParticleFromId(message.getParticleID()), message.getX(), message.getY(), message.getZ(), 0, 0, 0);
	}

}
