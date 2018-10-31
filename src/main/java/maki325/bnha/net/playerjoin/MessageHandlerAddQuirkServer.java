package maki325.bnha.net.playerjoin;

import maki325.bnha.capability.quirk.IQuirk;
import maki325.bnha.capability.quirk.providers.QuirkProvider;
import maki325.bnha.gui.hud.GuiHud;
import maki325.bnha.net.playerjoin.messages.MessageAddQuirk;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageHandlerAddQuirkServer implements IMessageHandler<MessageAddQuirk, IMessage> {

	@Override
	public IMessage onMessage(MessageAddQuirk message, MessageContext ctx) {
		
		System.out.println("Recived MessageAddQuirk packed on the server?");
		
		return null;
	}


}
