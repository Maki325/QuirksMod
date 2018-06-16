package maki325.bnha.proxy;

import maki325.bnha.net.MessageHandlerActivateClient;
import maki325.bnha.net.messages.MessageActivate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {
	
	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}
	
	@Override
	public void preInit() {
		super.preInit();
		
		CommonProxy.simpleNetworkWrapper.registerMessage(MessageHandlerActivateClient.class, MessageActivate.class, ACTIVATE_CLIENT_MESSAGE_ID, Side.CLIENT);
	}
	
	@Override
	public void init() {
		super.init();
	}
	
	public EntityPlayer getPlayer(MessageContext ctx) {
		return ctx.side == Side.CLIENT ? Minecraft.getMinecraft().player : super.getPlayer(ctx);
	}
	
}

