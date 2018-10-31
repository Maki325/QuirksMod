package maki325.bnha.net.quirk;

import maki325.bnha.net.quirk.messages.MessageActivateClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageHandlerActivateClientServer implements IMessageHandler<MessageActivateClient, IMessage> {

	@Override
	public IMessage onMessage(MessageActivateClient message, MessageContext ctx) {
		
		System.out.println("MessageActivateClient on Server?");
	    
		return null;
	}


}
