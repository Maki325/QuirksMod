package maki325.bnha.net.messages;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientSideHandler extends BasicSidedMessageHandler<ClientSideMessage> {
	
	@Override
	public IMessage onMessage(ClientSideMessage message, MessageContext ctx) {
		Minecraft  mc = Minecraft.getMinecraft();
		World world = mc.world;
		EntityPlayerSP player = mc.player;
		mc.addScheduledTask(new Runnable() {
	    	public void run() {
	    		if(message.dataPacket != null && message.dataPacket.processMessageData != null)
					message.dataPacket.processMessageData.accept(message.dataPacket.prepareMessageData, world, message.pos, player);
	    	}
        });
		return null;
	}
}
