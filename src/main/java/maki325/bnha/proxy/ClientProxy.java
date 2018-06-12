package maki325.bnha.proxy;

import maki325.bnha.net.MessageHandlerOnClient;
import maki325.bnha.net.MessageHandlerQuirkOnClient;
import maki325.bnha.net.messages.MessageClientStuff;
import maki325.bnha.net.messages.MessageParticle;
import maki325.bnha.net.messages.MessageQuirk;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {
	
	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}
	
	@Override
	public void preInit() {
		super.preInit();
		
		CommonProxy.simpleNetworkWrapper.registerMessage(MessageHandlerQuirkOnClient.class, MessageClientStuff.class, CommonProxy.PARTICLES_MESSAGE_ID, Side.CLIENT);
		
	}
	
}

