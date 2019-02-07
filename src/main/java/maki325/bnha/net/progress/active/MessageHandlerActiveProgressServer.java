package maki325.bnha.net.progress.active;

import maki325.bnha.api.net.MessageHandlerClient;
import maki325.bnha.api.net.MessageHandlerServer;
import maki325.bnha.capability.quirk.IQuirk;
import maki325.bnha.capability.quirk.providers.QuirkProvider;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageHandlerActiveProgressServer extends MessageHandlerServer<MessageActiveProgress, MessageActiveProgress> {

	@Override
	public void processMessage(MessageActiveProgress message, MessageContext ctx) {
		
		System.out.println("Active Progress Server!?");
		
	}

}
