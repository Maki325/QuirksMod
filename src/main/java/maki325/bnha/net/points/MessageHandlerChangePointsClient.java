package maki325.bnha.net.points;

import maki325.bnha.api.net.MessageHandlerClient;
import maki325.bnha.capability.quirk.IQuirk;
import maki325.bnha.capability.quirk.providers.QuirkProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageHandlerChangePointsClient extends MessageHandlerClient<MessageChangePoints, MessageChangePoints> {

	@Override
	public void processMessage(MessageChangePoints message, MessageContext ctx) {
		IQuirk iquirk = Minecraft.getMinecraft().player.getCapability(QuirkProvider.QUIRK_CAP, null);

		switch(message.getChange()) {
    		case ADD:
    			iquirk.addPoints(message.getPoints());
    			break;
    		case REMOVE:
    			iquirk.removePoints(message.getPoints());
    			break;
    		case SET:
    			iquirk.setPoints(message.getPoints());
    			break;
    		case RESET:
    			iquirk.addPoints(0);
    			break;
    		case GET:
    			break;
			default:
				break;
		}
	}


}