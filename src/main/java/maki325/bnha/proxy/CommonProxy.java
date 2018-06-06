package maki325.bnha.proxy;

import maki325.bnha.net.MessageHandlerOnServer;
import maki325.bnha.net.MessageQuirk;
import maki325.bnha.util.Reference;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {

	public static SimpleNetworkWrapper simpleNetworkWrapper;
	
	public static final byte QUIRK_MESSAGE_ID = 35; 
	
	public void registerItemRenderer(Item item, int meta, String id) {}
	
	public void preInit() {
		simpleNetworkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
		
		simpleNetworkWrapper.registerMessage(MessageHandlerOnServer.class, MessageQuirk.class, QUIRK_MESSAGE_ID, Side.SERVER);
		
	}
	
}
