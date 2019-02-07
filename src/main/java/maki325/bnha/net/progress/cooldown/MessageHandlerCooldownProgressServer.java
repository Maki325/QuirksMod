package maki325.bnha.net.progress.cooldown;

import maki325.bnha.api.net.MessageHandlerClient;
import maki325.bnha.api.net.MessageHandlerServer;
import maki325.bnha.capability.quirk.IQuirk;
import maki325.bnha.capability.quirk.providers.QuirkProvider;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageHandlerCooldownProgressServer extends MessageHandlerServer<MessageCooldownProgress, MessageCooldownProgress> {

	@Override
	public void processMessage(MessageCooldownProgress message, MessageContext ctx) {

		System.out.println("Cooldown Progress Server!?");
		
	}

}
