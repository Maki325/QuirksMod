package maki325.bnha.net.quirk.hud;

import maki325.bnha.api.Quirk;
import maki325.bnha.api.QuirkSkill;
import maki325.bnha.api.skilltree.Skill;
import maki325.bnha.capability.quirk.IQuirk;
import maki325.bnha.capability.quirk.providers.QuirkProvider;
import maki325.bnha.net.playerjoin.messages.MessageAddQuirk;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageHandlerChangeHudSkillClient implements IMessageHandler<MessageChangeHudSkill, IMessage> {

	@Override
	public IMessage onMessage(MessageChangeHudSkill message, MessageContext ctx) {
		
		if (ctx.side != Side.CLIENT) {
			System.err.println("MessageChangeHudSkill received on wrong side:" + ctx.side + ". Supposed to arrive on client side");
			return null;
		}
		
		if(!message.isValid()) {
			System.err.println("MessageChangePoints is not valid " + message.toString());
			return null;
		}
	    
		Minecraft minecraft = Minecraft.getMinecraft();
	    final WorldClient worldClient = minecraft.world;
	    
	    minecraft.addScheduledTask(new Runnable() {
	    	public void run() {
	    		
	    		IQuirk iquirk = minecraft.player.getCapability(QuirkProvider.QUIRK_CAP, null);

    			Quirk q = iquirk.getQuirks().get(0);
    			if(!(q instanceof QuirkSkill))
    				return;
    			
    			QuirkSkill qs = (QuirkSkill) q;
	    		switch(message.getChange()) {
		    		case SET:
		    			for(Skill s:qs.getSkilltree().getSkills()) {
		    				if(s.getName().equals(message.getSkillName())) {
		    					qs.getSkilltree().setHudSkill(s, message.getPosition());
		    					break;
		    				}
		    			}
		    			break;
		    		case GET:
		    			return;
	    			default:
	    				break;
	    		}
    		}
	    });
	    
		return null;
	}


}