package maki325.bnha.net.messages;

import maki325.bnha.net.messages.packets.DataPacket;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ResponsiveClientMessageHandler extends ResponsiveSidedMessageHandler<ResponsiveClientMessage, ServerSideMessage> {
	
	@Override
	public ServerSideMessage onMessage(ResponsiveClientMessage message, MessageContext ctx) {
		EntityPlayerMP player = ctx.getServerHandler().player;
        WorldServer world = player.getServerWorld();
        world.addScheduledTask(new Runnable() {
        	public void run() {
        		if(message.dataPacket != null && message.dataPacket.processMessageData != null)
						message.dataPacket.processMessageData.accept(message.dataPacket.prepareMessageData, world, message.pos, player);
        	}
        });
        
        DataPacket respPacket = new DataPacket(message.dataPacket.prepareResponseData, message.dataPacket.processResponseData);
        return new ServerSideMessage(message.name, respPacket, message.pos);
	}
	
}