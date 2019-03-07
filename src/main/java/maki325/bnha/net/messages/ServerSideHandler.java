package maki325.bnha.net.messages;

import maki325.bnha.net.messages.packets.DataPacket;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ServerSideHandler extends BasicSidedMessageHandler<ServerSideMessage> {
	
	@Override
	public IMessage onMessage(ServerSideMessage message, MessageContext ctx) {
		EntityPlayerMP player = ctx.getServerHandler().player;
        WorldServer world = player.getServerWorld();
        world.addScheduledTask(new Runnable() {
        	public void run() {
        		if(message.dataPacket != null && message.dataPacket.processMessageData != null)
						message.dataPacket.processMessageData.accept(message.dataPacket.prepareMessageData, world, message.pos, player);
        	}
        });
        return null;
	}
}
