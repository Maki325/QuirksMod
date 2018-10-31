package maki325.bnha.net.tag.remove;

import maki325.bnha.net.tag.add.MessageAddTag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageHandlerRemoveTagClient implements IMessageHandler<MessageRemoveTag, IMessage> {

	@Override
	public IMessage onMessage(MessageRemoveTag message, MessageContext ctx) {
		
		if (ctx.side != Side.CLIENT) {
			System.err.println("MessageRemoveTag received on wrong side:" + ctx.side);
			return null;
		}
		
		if(!message.isValid()) {
			System.err.println("MessageRemoveTag is not valid " + message.toString());
			return null;
		}
	    
		Minecraft minecraft = Minecraft.getMinecraft();
	    final WorldClient worldClient = minecraft.world;
	    
	    minecraft.addScheduledTask(new Runnable() {
	    	public void run() {
	    		
	    		if(message.getTag() != "")
	    			minecraft.player.removeTag(message.getTag());
	    		
	    	}
	    });
	    
		return null;
	}


}