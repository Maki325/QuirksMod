package maki325.bnha.net.progress.cooldown;

import maki325.bnha.api.net.MessageHandlerClient;
import maki325.bnha.capability.quirk.IQuirk;
import maki325.bnha.capability.quirk.providers.QuirkProvider;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageHandlerCooldownProgressClient extends MessageHandlerClient<MessageCooldownProgress, MessageCooldownProgress> {

	@Override
	public void processMessage(MessageCooldownProgress message, MessageContext ctx) {
		
		IQuirk iquirk = Minecraft.getMinecraft().player.getCapability(QuirkProvider.QUIRK_CAP, null);
		if(iquirk == null || iquirk.getQuirks().isEmpty() || iquirk.getQuirks().get(0) == null)
			return;
		
		iquirk.getQuirks().get(0).setCooldownProgress(message.getCooldownProgress());
		
	}

}
