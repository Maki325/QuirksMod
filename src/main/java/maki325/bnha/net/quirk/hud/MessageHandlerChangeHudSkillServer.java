package maki325.bnha.net.quirk.hud;

import maki325.bnha.BnHA;
import maki325.bnha.api.Quirk;
import maki325.bnha.api.QuirkSkill;
import maki325.bnha.api.skilltree.Skill;
import maki325.bnha.capability.quirk.IQuirk;
import maki325.bnha.capability.quirk.providers.QuirkProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageHandlerChangeHudSkillServer implements IMessageHandler<MessageChangeHudSkill, IMessage> {

	@Override
	public IMessage onMessage(MessageChangeHudSkill message, MessageContext ctx) {
		
		if (ctx.side != Side.SERVER) {
			System.err.println("MessageChangeHudSkill received on wrong side:" + ctx.side + ". Supposed to arrive on server side");
			return null;
		}
	    
	    final EntityPlayerMP sendingPlayer = ctx.getServerHandler().player;
	    if (sendingPlayer == null) {
	    	System.err.println("EntityPlayerMP was null when MessageChangeQuirk was received");
	    	return null;
	    }
	    
	    final WorldServer playerWorldServer = sendingPlayer.getServerWorld();
	    playerWorldServer.addScheduledTask(new Runnable() {
	    	public void run() {

	    		EntityPlayerMP player = sendingPlayer.mcServer.getPlayerList().getPlayerByUsername(message.getPlayeName());
	    		
	    		if(player == null) {
	    			sendingPlayer.sendMessage(new TextComponentString(TextFormatting.RED + "Player with the name " + message.getPlayeName() + " does not exist"));
	    			return;
	    		}
	    		
	    		IQuirk iquirk = player.getCapability(QuirkProvider.QUIRK_CAP, null);
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
		    			sendingPlayer.sendMessage(new TextComponentString("Player " + player.getName() + " has " + qs.getSkilltree().getHudSkill(message.getPosition()) + " skill at position " + message.getPosition()));
		    			return;
	    			default:
	    				break;
	    		}
	    		
	    		BnHA.proxy.simpleNetworkWrapper.sendTo(new MessageChangeHudSkill(message.getSkillName(), message.getPosition(), message.getChange(), message.getPlayeName()), player);
	    		
	    	}
	    });
	    
		return null;
	}

}
