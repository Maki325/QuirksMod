package maki325.bnha.proxy;

import maki325.bnha.net.messages.MessageFactory;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CommonProxy {

	public void registerItemRenderer(Item item, int meta, String id) {}

	public void preInit() {
		MessageFactory.initCommon();
	}
	
	public ModelBase getModel(int id) {
		return null;
	}

	public EntityPlayer getPlayer(MessageContext ctx) {
		return ctx.getServerHandler().player;
	}
	
}
